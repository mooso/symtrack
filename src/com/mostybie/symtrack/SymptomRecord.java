package com.mostybie.symtrack;

import java.util.*;

/**
 * Tracks an experience of a symptom for a set amount of time.
 */
public class SymptomRecord {
	private final String _symptom;
	private final Date _startTime;
	private final Date _endTime;

	public SymptomRecord(String symptom, Date startTime, Date endTime) {
		_symptom = symptom;
		_startTime = startTime;
		_endTime = endTime;
	}

	public static SymptomRecord fromNowForXMinutes(String symptom, int numberOfMinutes) {
		Calendar calendar = Calendar.getInstance();
		Date startTime = calendar.getTime();
		calendar.add(GregorianCalendar.MINUTE, numberOfMinutes);
		Date endTime = calendar.getTime();
		return new SymptomRecord(symptom, startTime, endTime);
	}

	@SuppressWarnings("unused")
	public String getSymptom() { return _symptom; }
	@SuppressWarnings("unused")
	public Date getStartTime() { return _startTime; }
	@SuppressWarnings("unused")
	public Date getEndTime() { return _endTime; }
}
