package com.colargtech.countonme.ui.action.create_action;

import com.colargtech.countonme.commons.mvp.MvpView;
import com.colargtech.countonme.ui.model.PeriodModelUI;

import java.util.List;

/**
 * @author juancho.
 */
public interface CreateActionView extends MvpView {

    void setIncrementByValues(List<Integer> values);

    void setPeriodValues(List<PeriodModelUI> values);

    void setMaxPerDayValues(List<Integer> values);

    void onActionCreated(String actionId);
}
