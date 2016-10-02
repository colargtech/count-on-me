package com.colargtech.countonme.ui.action.create_action;

import com.colargtech.countonme.commons.mvp.MvpView;

import java.util.List;

/**
 * @author juancho.
 */
public interface CreateActionView extends MvpView {

    void setIncrementByValues(List<Integer> values);

    void setPeriodValues(List<String> values);

    void setMaxPerDayValues(List<Integer> values);

    void onActionCreated(String actionId);
}
