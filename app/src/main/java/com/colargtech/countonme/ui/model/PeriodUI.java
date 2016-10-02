package com.colargtech.countonme.ui.model;

/**
 * @author juancho.
 */
public class PeriodUI {

    private final String value;
    private final PeriodTypeUI typeUI;

    public PeriodUI(String value, PeriodTypeUI typeUI) {
        this.value = value;
        this.typeUI = typeUI;
    }

    public String getValue() {
        return value;
    }

    public PeriodTypeUI getTypeUI() {
        return typeUI;
    }

    @Override
    public String toString() {
        return value;
    }
}
