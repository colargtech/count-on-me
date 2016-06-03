package com.colargtech.countonme.ui.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdfesta
 */
public class Action {
    private final String name;
    private final Period period;
    private final int incrementBy;
    private final Map<Date, Integer> countsByDate;
    private int maxPerPeriod;

    protected Action(Builder builder) {
        name = builder.name;
        period = builder.period;
        incrementBy = builder.incrementBy;
        maxPerPeriod = builder.maxPerPeriod;
        countsByDate = new HashMap<>();
    }

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
        public Action build() {
            return new Action(this);
        }
    }
}
