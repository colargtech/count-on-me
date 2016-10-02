package com.colargtech.countonme.ui.model;

import com.colargtech.countonme.R;

/**
 * @author juancho.
 */
public enum PeriodTypeUI {
    MONTH(R.string.action_period_month),
    DAY(R.string.action_period_day),
    WEEK(R.string.action_period_week),
    YEAR(R.string.action_period_year);

    private final int resValue;

    PeriodTypeUI(int resValue) {
        this.resValue = resValue;
    }

    public int getResValue() {
        return resValue;
    }
}
