package com.colargtech.countonme.ui.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.colargtech.adapters.ViewType;
import com.colargtech.countonme.model.Period;
import com.colargtech.countonme.ui.adapter.AdapterConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdfesta
 */
public class ActionUI implements Parcelable, ViewType {
    private final String id;
    private final String name;
    private final Period period;
    private final int incrementBy;
    private final Map<Date, Integer> countsByDate;
    private int maxPerPeriod;

    protected ActionUI(Builder builder) {
        id = builder.id;
        name = builder.name;
        period = builder.period;
        incrementBy = builder.incrementBy;
        maxPerPeriod = builder.maxPerPeriod;
        countsByDate = builder.countsByDate;
    }

    protected ActionUI(Parcel in) {
        id = in.readString();
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
        return this.countsByDate.values().size();
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

    @Override
    public int getViewType() {
        return AdapterConstants.ACTION;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionUI actionUI = (ActionUI) o;

        return id != null ? id.equals(actionUI.id) : actionUI.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
            this.countsByDate = new HashMap<>();
        }

        @NonNull
        public Builder withMaxPerPeriod(int val) {
            maxPerPeriod = val;
            return this;
        }

        public Builder withCountForDate(Map<Date, Integer> countsByDate) {
            this.countsByDate = countsByDate;
            return this;
        }

        @NonNull
        public ActionUI build() {
            return new ActionUI(this);
        }
    }
}