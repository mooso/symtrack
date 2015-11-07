package com.mostybie.symtrack;

import java.util.*;

/**
 * Tracks an experience of a symptom for a set amount of time.
 */
public class SymptomRecord {
	private final String _symptom;
	private final Date _startTime;
	private Date _endTime;

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

	public String getSymptom() { return _symptom; }
	@SuppressWarnings("unused")
	public Date getStartTime() { return _startTime; }
	@SuppressWarnings("unused")
	public Date getEndTime() { return _endTime; }

	public void setEndToNow() {
		_endTime = Calendar.getInstance().getTime();
	}

	public boolean isTimeInWindow(Date time) {
		return _startTime.compareTo(time) <= 0 &&
				_endTime.compareTo(time) > 0;
	}

	/**
	 * Checks if tracking for this symptom has JUST started (i.e. within the last minute).
	 * @return true iff just started. False if started a long time ago or hasn't started.
	 */
	public boolean didJustStart() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.setTime(_startTime);
		Date windowStart = calendar.getTime();
		calendar.add(GregorianCalendar.MINUTE, 1);
		Date windowEnd = calendar.getTime();
		return windowStart.compareTo(now) <= 0 &&
				windowEnd.compareTo(now) > 0;
	}
}
