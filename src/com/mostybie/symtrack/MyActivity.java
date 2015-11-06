package com.mostybie.symtrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.*;

public class MyActivity extends Activity {
	private List<String> _allSymptoms;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initializeSymptomList();
		initializeSymptomChooser();
	}

	/**
	 * Initializes the symptom chooser spinner from the symptom list.
	 */
	private void initializeSymptomChooser() {
		Spinner symptomChooser = ((Spinner)findViewById(R.id.symptomChooser));
		ArrayAdapter<String> symptomChooserAdapter =
				new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, _allSymptoms);
		symptomChooser.setAdapter(symptomChooserAdapter);
	}

	private void initializeSymptomList() {
		// TODO: Actual symptom list logic
		_allSymptoms = Arrays.asList("Sad", "Lonely");
	}
}
