package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
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
		}
	}

	public void setAnswer(View answerButton) {
		int answer = Integer.parseInt(((RadioButton)answerButton).getText().toString());
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
}