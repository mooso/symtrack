package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.*;

public class MainActivity extends Activity {
	private List<AnswerRecord> _records; // TODO: Persist those

	public static final String NEW_ANSWERS = "NewAnswers";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_records = new ArrayList<>(); // TODO: Actually get from state
		List<AnswerRecord> newAnswers = getIntent().getParcelableArrayListExtra(NEW_ANSWERS);
		if (newAnswers != null) {
			_records.addAll(newAnswers);
		}
	}

	public void trackToday(View trackTodayButton) {
		Intent newAnswerIntent = new Intent(this, AnswerQuestionActivity.class);
		newAnswerIntent.putExtra(AnswerQuestionActivity.QUESTION_POSITION, 0);
		newAnswerIntent.putExtra(AnswerQuestionActivity.DAY_IN_QUESTION, DayDate.Today());
		startActivity(newAnswerIntent);
	}
}
