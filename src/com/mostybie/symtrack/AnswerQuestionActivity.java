package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activity for answering a question.
 */
public class AnswerQuestionActivity extends Activity {
	private int _questionPosition;
	private ArrayList<AnswerRecord> _answersSoFar;
	private DayDate _dayInQuestion;

	public final static String QUESTION_POSITION = "QuestionPosition";
	public final static String ANSWERS_SO_FAR = "AnswersSoFar";
	public final static String DAY_IN_QUESTION = "DayInQuestion";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer);
		_questionPosition = getIntent().getIntExtra(QUESTION_POSITION, 0);
		_answersSoFar = getIntent().getParcelableArrayListExtra(ANSWERS_SO_FAR);
		_dayInQuestion = getIntent().getParcelableExtra(DAY_IN_QUESTION);
		getQuestionTextView().setText(getMyQuestion().getQuestionWording());
		getQuestionPositionTextView().setText("Question: " + (_questionPosition + 1) + "/" +
				Survey.getMainSurvey().getQuestions().size());
	}

	public void setAnswer(View answerButton) {
		int answer = Integer.parseInt(((RadioButton)answerButton).getText().toString());
		_answersSoFar.add(new AnswerRecord(getMyQuestion(), _dayInQuestion, answer));
		int newQuestionPosition = _questionPosition + 1;
		if (newQuestionPosition == Survey.getMainSurvey().getQuestions().size()) {
			Intent mainViewIntent = new Intent(this, MainActivity.class);
			mainViewIntent.putExtra(MainActivity.NEW_ANSWERS, _answersSoFar);
			startActivity(mainViewIntent);
		} else {
			Intent newAnswerIntent = new Intent(this, AnswerQuestionActivity.class);
			newAnswerIntent.putExtra(QUESTION_POSITION, newQuestionPosition);
			newAnswerIntent.putExtra(ANSWERS_SO_FAR, _answersSoFar);
			newAnswerIntent.putExtra(DAY_IN_QUESTION, _dayInQuestion);
			startActivity(newAnswerIntent);
		}
	}

	private Question getMyQuestion() {
		return Survey.getMainSurvey().getQuestions().get(_questionPosition);
	}

	private TextView getQuestionTextView() {
		return (TextView)findViewById(R.id.questionView);
	}

	private TextView getQuestionPositionTextView() {
		return (TextView)findViewById(R.id.questionPositionText);
	}
}