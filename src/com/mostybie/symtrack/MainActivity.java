package com.mostybie.symtrack;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

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

	public static class PreviousDayPickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Get yesterday's year/month/day for default/max value
			final Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
			dialog.getDatePicker().setMaxDate(c.getTime().getTime());
			return dialog;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			Intent newAnswerIntent = new Intent(getActivity(), AnswerQuestionActivity.class);
			newAnswerIntent.putExtra(AnswerQuestionActivity.QUESTION_POSITION, 0);
			newAnswerIntent.putExtra(AnswerQuestionActivity.DAY_IN_QUESTION,
					new DayDate(year, monthOfYear, dayOfMonth));
			startActivity(newAnswerIntent);
		}
	}

	public void trackPreviousDay(View trackPreviousDayButton) {
		new PreviousDayPickerFragment().show(getFragmentManager(), "previousDayPicker");
	}
}
