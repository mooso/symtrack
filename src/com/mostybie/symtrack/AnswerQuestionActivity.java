package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.*;

/**
 * Activity for answering a question.
 */
public class AnswerQuestionActivity extends Activity {
	private SurveyDatabaseHelper _databaseHelper;
	private List<Question> _allQuestions;
	private int _questionPosition;
	private DayDate _dayInQuestion;

	public final static String QUESTION_POSITION = "QuestionPosition";
	public final static String DAY_IN_QUESTION = "DayInQuestion";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer);
		_questionPosition = getIntent().getIntExtra(QUESTION_POSITION, 0);
		_dayInQuestion = getIntent().getParcelableExtra(DAY_IN_QUESTION);
		_databaseHelper = new SurveyDatabaseHelper(this);
		new LoadQuestionsTask().execute();
	}

	private final class LoadQuestionsTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			_allQuestions = SurveyDatabaseHelper.getAllQuestions(_databaseHelper.getReadableDatabase());
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			getQuestionTextView().setText(getMyQuestion().getQuestionWording());
			getQuestionPositionTextView().setText("Question: " + (_questionPosition + 1) + "/" +
					_allQuestions.size());

			RadioGroup answerRadioGroup = getAnswerRadioGroup();
			for (int i = 0; i < 5; i++) {
				RadioButton newButton = new RadioButton(AnswerQuestionActivity.this);
				int buttonAnswer = i + 1;
				newButton.setText("" + buttonAnswer);
				newButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						setAnswer(buttonAnswer);
					}
				});
				answerRadioGroup.addView(newButton);
			}
			new ShowPreviousAnswerTask().execute();
		}
	}

	private class ShowPreviousAnswerTask extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected Integer doInBackground(Void... params) {
			return SurveyDatabaseHelper.getExistingAnswer(_databaseHelper.getReadableDatabase(),
					getMyQuestion().getId(), _dayInQuestion);
		}

		@Override
		protected void onPostExecute(Integer existingAnswer) {
			if (existingAnswer != null) {
				RadioGroup answerGroup = getAnswerRadioGroup();
				for (int i = 0; i < answerGroup.getChildCount(); i++) {
					RadioButton currentButton = (RadioButton)answerGroup.getChildAt(i);
					if (currentButton.getText().equals(existingAnswer.toString())) {
						currentButton.toggle();
						return;
					}
				}
			}
		}
	}

	public void setAnswer(int answer) {
		Log.d(AnswerQuestionActivity.class.getName(), "Setting answer: " + answer);
		new SetAnswerTask().execute(answer);
		int newQuestionPosition = _questionPosition + 1;
		if (newQuestionPosition >= _allQuestions.size()) {
			Intent mainViewIntent = new Intent(this, MainActivity.class);
			startActivity(mainViewIntent);
		} else {
			Intent newAnswerIntent = new Intent(this, AnswerQuestionActivity.class);
			newAnswerIntent.putExtra(QUESTION_POSITION, newQuestionPosition);
			newAnswerIntent.putExtra(DAY_IN_QUESTION, _dayInQuestion);
			startActivity(newAnswerIntent);
		}
	}

	public final class SetAnswerTask extends AsyncTask<Integer, Void, Void>
	{
		@Override
		protected Void doInBackground(Integer... params) {
			SurveyDatabaseHelper.setAnswer(_databaseHelper.getWritableDatabase(),
					new AnswerRecord(getMyQuestion(), _dayInQuestion, params[0]));
			return null;
		}
	}

	private Question getMyQuestion() {
		return _allQuestions.get(_questionPosition);
	}

	private TextView getQuestionTextView() {
		return (TextView)findViewById(R.id.questionView);
	}

	private TextView getQuestionPositionTextView() {
		return (TextView)findViewById(R.id.questionPositionText);
	}

	private RadioGroup getAnswerRadioGroup() {
		return (RadioGroup)findViewById(R.id.answerRadioGroup);
	}
}