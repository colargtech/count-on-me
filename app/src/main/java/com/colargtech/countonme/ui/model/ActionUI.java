package com.colargtech.countonme.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.colargtech.countonme.model.Period;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdfesta
 */
public class ActionUI implements Parcelable {
    public static final String KEY = "ACTION_KEY";
    private final String name;
    private final Period period;
    private final int incrementBy;
    private final Map<Date, Integer> countsByDate;
    private int maxPerPeriod;

    protected ActionUI(Builder builder) {
        name = builder.name;
        period = builder.period;
        incrementBy = builder.incrementBy;
        maxPerPeriod = builder.maxPerPeriod;
        countsByDate = new HashMap<>();
    }

    protected ActionUI(Parcel in) {
        name = in.readString();
        incrementBy = in.readInt();
        maxPerPeriod = in.readInt();
        countsByDate = new HashMap<>();
        period = Period.DAY;
    }

    public static final Creator<ActionUI> CREATOR = new Creator<ActionUI>() {
        @Override
        public ActionUI createFromParcel(Parcel in) {
            return new ActionUI(in);
        }

        @Override
        public ActionUI[] newArray(int size) {
            return new ActionUI[size];
        }
    };

    public String getName() {
        return name;
    }

    public Period getPeriod() {
        return period;
    }

    public int getIncrementBy() {
        return incrementBy;
    }

    public int getMaxPerPeriod() {
        return maxPerPeriod;
    }

    public int getCountForDate(Date date) {
        return this.countsByDate.get(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(incrementBy);
        parcel.writeInt(maxPerPeriod);
    }

    public static final class Builder {
        private final String name;
        private final Period period;
        private final int incrementBy;
        private int maxPerPeriod;

        public Builder(@NonNull String name, @NonNull Period period, int incrementBy) {
            this.name = name;
            this.period = period;
            this.incrementBy = incrementBy;
        }

        @NonNull
        public Builder withMaxPerPeriod(int val) {
            maxPerPeriod = val;
            return this;
        }

        @NonNull
        public ActionUI build() {
            return new ActionUI(this);
        }
    }
}
