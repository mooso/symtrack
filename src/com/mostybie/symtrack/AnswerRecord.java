package com.mostybie.symtrack;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A record for an answer to a question on a specific day.
 */
public final class AnswerRecord implements Parcelable {
	private final DayDate _day;
	private final Question _question;
	private final int _answer;

	public AnswerRecord(Question question, DayDate day, int answer) {
		_question = question;
		_day = day;
		_answer = answer;
	}

	public DayDate getDay() { return _day; }
	public Question getQuestion() { return _question; }
	public int getAnswer() { return _answer; }

	private AnswerRecord(Parcel in) {
		_day = in.readParcelable(DayDate.class.getClassLoader());
		_question = in.readParcelable(Question.class.getClassLoader());
		_answer = in.readInt();
	}

	public static final Creator<AnswerRecord> CREATOR = new Creator<AnswerRecord>() {
		@Override
		public AnswerRecord createFromParcel(Parcel in) {
			return new AnswerRecord(in);
		}

		@Override
		public AnswerRecord[] newArray(int size) {
			return new AnswerRecord[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(_day, flags);
		dest.writeParcelable(_question, flags);
		dest.writeInt(_answer);
	}
}
