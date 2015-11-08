package com.mostybie.symtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the SQLite database that stores my surveys and answers.
 */
public class SurveyDatabaseHelper extends SQLiteOpenHelper {
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "SurveyDB";

	public SurveyDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Questions(" +
				"QuestionId INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Name TEXT NOT NULL," +
				"QuestionWording TEXT NOT NULL)");
		db.execSQL("CREATE TABLE Answers(" +
				"QuestionId INTEGER NOT NULL," +
				"Date TEXT NOT NULL," +
				"Answer INT NOT NULL," +
				"PRIMARY KEY(QuestionId, Date))");
		initializeQuestions(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	private static void initializeQuestions(SQLiteDatabase db) {
		insertQuestion(db, "Mood", "How was your mood today?");
		insertQuestion(db, "Sleep", "How was sleep last night?");
		insertQuestion(db, "Energy", "How was your energy level today?");
		insertQuestion(db, "Presence", "How was your presence?");
		insertQuestion(db, "Exercise", "How was your physical activity?");
	}

	public static Question insertQuestion(SQLiteDatabase db, String name, String questionWording) {
		ContentValues columnContent = new ContentValues();
		columnContent.put("Name", name);
		columnContent.put("QuestionWording", questionWording);
		long questionId = db.insert("Questions", null, columnContent);
		return new Question(questionId, name, questionWording);
	}

	public static void setAnswer(SQLiteDatabase db, AnswerRecord answer) {
		ContentValues columnContent = new ContentValues();
		columnContent.put("QuestionId", answer.getQuestion().getId());
		columnContent.put("Date", answer.getDay().toCanonicalString());
		columnContent.put("Answer", answer.getAnswer());
		db.insertWithOnConflict("Answers", null, columnContent, SQLiteDatabase.CONFLICT_REPLACE);
	}

	public static List<Question> getAllQuestions(SQLiteDatabase db) {
		List<Question> allQuestions = new ArrayList<>();
		Cursor results = db.query("Questions", new String[] { "QuestionId", "Name", "QuestionWording" },
			null, null, null, null, null);
		results.moveToFirst();
		while (!results.isAfterLast()) {
			allQuestions.add(new Question(results.getLong(0), results.getString(1), results.getString(2)));
			results.moveToNext();
		}
		results.close();
		return allQuestions;
	}
}
