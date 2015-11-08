package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.*;

public class MainActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void trackToday(View trackTodayButton) {
		Intent newAnswerIntent = new Intent(this, AnswerQuestionActivity.class);
		newAnswerIntent.putExtra(AnswerQuestionActivity.QUESTION_POSITION, 0);
		newAnswerIntent.putExtra(AnswerQuestionActivity.DAY_IN_QUESTION, DayDate.Today());
		startActivity(newAnswerIntent);
	}
}
