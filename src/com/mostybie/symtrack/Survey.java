package com.mostybie.symtrack;

import java.util.*;

/**
 * The list of questions to ask.
 */
public final class Survey {
	private final List<Question> _allQuestions;

	// For now I'm implementing this as a singleton - may update later to support multiple surveys and so on...
	private final static Survey _theSurvey = new Survey();
	private Survey() {
		// TODO: Actual question logic where I can add/remove
		_allQuestions = Arrays.asList(
				new Question("Mood", "How was your mood today?"),
				new Question("Sleep", "How was sleep last night?"),
				new Question("Energy", "How was your energy level today?"),
				new Question("Presence", "How was your presence?"),
				new Question("Exercise", "How was your physical activity?")
		);
	}

	public static Survey getMainSurvey() {
		return _theSurvey;
	}

	public List<Question> getQuestions() {
		return _allQuestions;
	}
}
