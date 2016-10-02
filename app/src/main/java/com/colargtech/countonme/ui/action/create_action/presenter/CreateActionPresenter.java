package com.colargtech.countonme.ui.action.create_action.presenter;

import android.content.Context;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Period;
import com.colargtech.countonme.ui.action.ActionsRxMapper;
import com.colargtech.countonme.ui.action.create_action.CreateActionView;
import com.colargtech.countonme.ui.model.ActionUI;
import com.colargtech.countonme.ui.model.PeriodUI;
import com.colargtech.countonme.ui.model.PeriodModelUI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * @author juancho.
 */
public class CreateActionPresenter extends MvpRxBasePresenter<CreateActionView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionCreateSubject;
    private final Context context;

    @Inject
    public CreateActionPresenter(Context context,
                                 CountOnMeDBManager countOnMeDBManager,
                                 @Named("ActionCreate") Subject<Action, Action> actionCreateSubject) {
        this.context = context;
        this.countOnMeDBManager = countOnMeDBManager;
        this.actionCreateSubject = actionCreateSubject;
    }

    @Override
    public void onResume() {
        super.onResume();
        addSubscription(subscribe(actionCreateSubject));

        // TODO get incrementBy possible values:
        List<Integer> incrementByValue = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            incrementByValue.add(i);
        }
        getView().setIncrementByValues(incrementByValue);

        // TODO: sort and show first default value.
        List<PeriodModelUI> periods = new ArrayList<>();
        for (Period period : Period.values()) {
            PeriodUI periodUI = PeriodUI.valueOf(period.name());
            PeriodModelUI periodModelUI = new PeriodModelUI(context.getString(periodUI.getResValue()), periodUI);
            periods.add(periodModelUI);
        }

        getView().setPeriodValues(periods);

        List<Integer> maxPerDay = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            maxPerDay.add(i);
        }
        getView().setMaxPerDayValues(maxPerDay);
    }

    public void createAction(String groupId, String name, PeriodModelUI period, int incrementBy, int maxPerDay) {
        countOnMeDBManager.createAction(groupId, name, Period.valueOf(period.getTypeUI().name()), incrementBy, maxPerDay);
    }

    private Subscription subscribe(Observable<Action> observable) {
        return ActionsRxMapper.fromActionToActionUI(observable)
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached()) {
                            getView().onActionCreated(actionUI.getId());
                        }
                    }
                });
    }
}
