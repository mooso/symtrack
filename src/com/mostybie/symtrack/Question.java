package com.mostybie.symtrack;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A question that can be rated on our 5-point scale.
 */
public final class Question implements Parcelable {
	private final long _id;
	private final String _name;
	private final String _questionWording;

	public Question(long id, String name, String questionWording) {
		_id = id;
		_name = name;
		_questionWording = questionWording;
	}

	@SuppressWarnings("unused")
	public long getId() { return _id; }
	@SuppressWarnings("unused")
	public String getName() { return _name; }
	@SuppressWarnings("unused")
	public String getQuestionWording() { return _questionWording; }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(_id);
		dest.writeString(_name);
		dest.writeString(_questionWording);
	}

	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
		public Question createFromParcel(Parcel in) {
			return new Question(in);
		}

		@Override
		public Question[] newArray(int size) {
			return new Question[size];
		}
	};

	private Question(Parcel in) {
		_id = in.readLong();
		_name = in.readString();
		_questionWording = in.readString();
	}
}
