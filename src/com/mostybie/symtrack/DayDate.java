package com.mostybie.symtrack;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A representation of just the day date (not time).
 */
public final class DayDate implements Parcelable {
	private final int _year;
	private final int _month;
	private final int _day;

	public DayDate(int year, int month, int day) {
		_year = year;
		_month = month;
		_day = day;
	}

	private DayDate(Parcel in) {
		_year = in.readInt();
		_month = in.readInt();
		_day = in.readInt();
	}

	public static final Creator<DayDate> CREATOR = new Creator<DayDate>() {
		@Override
		public DayDate createFromParcel(Parcel in) {
			return new DayDate(in);
		}

		@Override
		public DayDate[] newArray(int size) {
			return new DayDate[size];
		}
	};

	public static DayDate Today() {
		Calendar now = GregorianCalendar.getInstance();
		return new DayDate(now.get(GregorianCalendar.YEAR),
				now.get(GregorianCalendar.MONTH),
				now.get(GregorianCalendar.DAY_OF_MONTH));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_year);
		dest.writeInt(_month);
		dest.writeInt(_day);
	}
}
