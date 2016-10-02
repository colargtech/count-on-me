package com.colargtech.countonme.ui.model;

/**
 * @author juancho.
 */
public class PeriodModelUI {

    private final String value;
    private final PeriodUI typeUI;

    public PeriodModelUI(String value, PeriodUI typeUI) {
        this.value = value;
        this.typeUI = typeUI;
    }

    public String getValue() {
        return value;
    }

    public PeriodUI getTypeUI() {
        return typeUI;
    }

    @Override
    public String toString() {
        return value;
    }
}
