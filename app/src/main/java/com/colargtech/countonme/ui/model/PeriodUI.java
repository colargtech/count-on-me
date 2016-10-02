package com.colargtech.countonme.ui.model;

import com.colargtech.countonme.R;

/**
 * @author juancho.
 */
public enum PeriodUI {
    MONTH(R.string.action_period_month),
    DAY(R.string.action_period_day),
    WEEK(R.string.action_period_week),
    YEAR(R.string.action_period_year);

    private final int resValue;

    PeriodUI(int resValue) {
        this.resValue = resValue;
    }

    public int getResValue() {
        return resValue;
    }
}
