package com.mostybie.symtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.*;

public class MainActivity extends Activity {
	private SurveyDatabaseHelper _databaseHelper;
	private DayDate _today;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_databaseHelper = new SurveyDatabaseHelper(this);
		_today = DayDate.Today();
		new CheckTodaysAnswer().execute();
	}

	private class CheckTodaysAnswer extends AsyncTask<Void, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(Void... params) {
			return SurveyDatabaseHelper.isDayAnswered(_databaseHelper.getReadableDatabase(), _today);
		}

		@Override
		protected void onPostExecute(Boolean dayAnswered) {
			if (!dayAnswered) {
				getTrackTodayButton().setText("How was today (" + _today.toCanonicalString() + ")?");
			} else {
				getTrackTodayButton().setText("Revise my answers for today (" + _today.toCanonicalString() + ")");
			}
		}
	}

	private Button getTrackTodayButton() {
		return (Button)findViewById(R.id.trackTodayButton);
	}

	public void trackToday(View trackTodayButton) {
		Intent newAnswerIntent = new Intent(this, AnswerQuestionActivity.class);
		newAnswerIntent.putExtra(AnswerQuestionActivity.QUESTION_POSITION, 0);
		newAnswerIntent.putExtra(AnswerQuestionActivity.DAY_IN_QUESTION, _today);
		startActivity(newAnswerIntent);
	}
}
