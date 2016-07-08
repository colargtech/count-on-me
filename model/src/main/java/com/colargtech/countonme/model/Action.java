package com.colargtech.countonme.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdfesta
 */
public class Action implements Parcelable {
    public final String id;
    public final String name;
    public final Period period;
    public final int incrementBy;
    public final Map<Date, Integer> countsByDate;
    public int maxPerPeriod;

    protected Action(Builder builder) {
        id = builder.id;
        name = builder.name;
        period = builder.period;
        incrementBy = builder.incrementBy;
        maxPerPeriod = builder.maxPerPeriod;
        countsByDate = builder.countsByDate;
    }

    protected Action(Parcel in) {
        id = in.readString();
        name = in.readString();
        incrementBy = in.readInt();
        maxPerPeriod = in.readInt();
        countsByDate = new HashMap<>();
        period = Period.DAY;
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(incrementBy);
        parcel.writeInt(maxPerPeriod);
    }

    public static final class Builder {
        private final String id;
        private final String name;
        private final Period period;
        private final int incrementBy;
        private int maxPerPeriod;
        private Map<Date, Integer> countsByDate;

        public Builder(@NonNull String id, @NonNull String name, @NonNull Period period, int incrementBy) {
            this.id = id;
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
        public Builder withCountForDate(Map<Date, Integer> countsByDate) {
            this.countsByDate = countsByDate;
            return this;
        }

        @NonNull
        public Action build() {
            return new Action(this);
        }
    }
}
