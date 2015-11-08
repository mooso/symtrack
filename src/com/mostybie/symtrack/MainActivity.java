package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.*;

public class MainActivity extends Activity {
	private List<Question> _allQuestions;
	private List<AnswerRecord> _records; // TODO: Persist those

	public static final String NEW_ANSWERS = "NewAnswers";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initializeQuestions();
		_records = new ArrayList<>(); // TODO: Actually get from state
		List<AnswerRecord> newAnswers = getIntent().getParcelableArrayListExtra(NEW_ANSWERS);
		if (newAnswers != null) {
			_records.addAll(newAnswers);
		}
	}

	public void trackToday(View trackTodayButton) {
		Intent newAnswerIntent = new Intent(this, AnswerQuestionActivity.class);
		newAnswerIntent.putExtra(AnswerQuestionActivity.QUESTION_POSITION, 0);
		newAnswerIntent.putExtra(AnswerQuestionActivity.ALL_QUESTIONS, new ArrayList<>(_allQuestions));
		newAnswerIntent.putExtra(AnswerQuestionActivity.ANSWERS_SO_FAR, new ArrayList<AnswerRecord>());
		newAnswerIntent.putExtra(AnswerQuestionActivity.DAY_IN_QUESTION, DayDate.Today());
		startActivity(newAnswerIntent);
	}

	private void initializeQuestions() {
		// TODO: Actual question logic where I can add/remove
		_allQuestions = Arrays.asList(
				new Question("Mood", "How was your mood today?"),
				new Question("Sleep", "How was sleep last night?"),
				new Question("Energy", "How was your energy level today?"),
				new Question("Presence", "How was your presence?"),
				new Question("Exercise", "How was your physical activity?")
		);
	}
}
