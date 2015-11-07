package com.mostybie.symtrack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.*;

public class MyActivity extends Activity {
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
	}

	private Spinner getSymptomChooser() {
		return (Spinner)findViewById(R.id.symptomChooser);
	}

	private void initializeSymptomList() {
		// TODO: Actual symptom list logic
		_allSymptoms = Arrays.asList("Sad", "Lonely");
	}

	public void experienceForThirtyMinutes(View experienceButton) {
		String symptom = (String)getSymptomChooser().getSelectedItem();
		_records.add(SymptomRecord.fromNowForXMinutes(symptom, 30));
	}
}
