package com.mostybie.symtrack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MyActivity extends Activity implements AdapterView.OnItemSelectedListener {
	private List<String> _allSymptoms;
	private List<SymptomRecord> _records; // TODO: Persist those

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initializeSymptomList();
		initializeSymptomChooser();
		_records = new ArrayList<>(); // TODO: Actually get from state
	}

	/**
	 * Initializes the symptom chooser spinner from the symptom list.
	 */
	private void initializeSymptomChooser() {
		ArrayAdapter<String> symptomChooserAdapter =
				new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, _allSymptoms);
		getSymptomChooser().setAdapter(symptomChooserAdapter);
		getSymptomChooser().setOnItemSelectedListener(this);
	}

	private Spinner getSymptomChooser() {
		return (Spinner)findViewById(R.id.symptomChooser);
	}

	private Button getExperienceForThirtyMinutesButton() {
		return (Button)findViewById(R.id.trackForShortPeriodButton);
	}

	private Button getCancelTrackingButton() {
		return (Button)findViewById(R.id.cancelTrackingButton);
	}

	private Button getFinishTrackingButton() {
		return (Button)findViewById(R.id.finishTrackingButton);
	}

	private void initializeSymptomList() {
		// TODO: Actual symptom list logic
		_allSymptoms = Arrays.asList("Sad", "Lonely");
	}

	private String getSelectedSymptom() {
		return (String)getSymptomChooser().getSelectedItem();
	}

	public void experienceForThirtyMinutes(View experienceButton) {
		String selectedSymptom = getSelectedSymptom();
		_records.add(SymptomRecord.fromNowForXMinutes(selectedSymptom, 30));
		adjustButtonVisibility(selectedSymptom);
	}

	public void cancelTracking(View cancelTrackingButton) {
		String selectedSymptom = getSelectedSymptom();
		SymptomRecord currentRecord = getCurrentlyTrackingRecordForSymptom(selectedSymptom);
		if (currentRecord != null) {
			_records.remove(currentRecord);
			adjustButtonVisibility(selectedSymptom);
		}
	}

	public void finishTracking(View finishTrackingButton) {
		String selectedSymptom = getSelectedSymptom();
		SymptomRecord currentRecord = getCurrentlyTrackingRecordForSymptom(selectedSymptom);
		if (currentRecord != null) {
			currentRecord.setEndToNow();
			adjustButtonVisibility(selectedSymptom);
		}
	}

	private SymptomRecord getCurrentlyTrackingRecordForSymptom(String symptom) {
		Date now = Calendar.getInstance().getTime();
		for (SymptomRecord record : _records) {
			if (record.getSymptom().equalsIgnoreCase(symptom) &&
					record.isTimeInWindow(now)) {
				return record;
			}
		}
		return null;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String selectedSymptom = (String)parent.getItemAtPosition(position);
		adjustButtonVisibility(selectedSymptom);
	}

	private void adjustButtonVisibility(String selectedSymptom) {
		SymptomRecord currentRecord = getCurrentlyTrackingRecordForSymptom(selectedSymptom);
		if (currentRecord == null) {
			// Not currently tracking this symptom
			getExperienceForThirtyMinutesButton().setVisibility(View.VISIBLE);
			getCancelTrackingButton().setVisibility(View.INVISIBLE);
			getFinishTrackingButton().setVisibility(View.INVISIBLE);
		} else {
			// Currently tracking this symptom
			getExperienceForThirtyMinutesButton().setVisibility(View.INVISIBLE);
			getCancelTrackingButton().setVisibility(View.VISIBLE);
			if (currentRecord.didJustStart()) {
				getFinishTrackingButton().setVisibility(View.INVISIBLE);
			} else {
				getFinishTrackingButton().setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// Should never happen...
	}
}
