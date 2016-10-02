package com.colargtech.countonme.ui.action.create_action.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Period;
import com.colargtech.countonme.ui.action.create_action.CreateActionView;
import com.colargtech.countonme.ui.model.ActionUI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * @author juancho.
 */
public class CreateActionPresenter extends MvpRxBasePresenter<CreateActionView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionCreateSubject;

    @Inject
    public CreateActionPresenter(CountOnMeDBManager countOnMeDBManager, @Named("ActionCreate") Subject<Action, Action> actionCreateSubject) {
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

        List<String> periods = new ArrayList<>();
        for (Period period : Period.values()) {
            periods.add(period.name());
        }

        getView().setPeriodValues(periods);

        List<Integer> maxPerDay = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            maxPerDay.add(i);
        }
        getView().setMaxPerDayValues(maxPerDay);
    }

    public void createAction(String groupId, String name, Period period, int incrementBy, int maxPerDay) {
        countOnMeDBManager.createAction(groupId, name, incrementBy, maxPerDay);
    }

    private Subscription subscribe(Observable<Action> observable) {
        return fromActionToActionUI(observable)
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached()) {
                            getView().onActionCreated(actionUI.getId());
                        }
                    }
                });
    }

    private Observable<ActionUI> fromActionToActionUI(Observable<Action> observable) {
        return observable.map(
                new Func1<Action, ActionUI>() {
                    @Override
                    public ActionUI call(Action action) {
                        ActionUI.Builder builder = new ActionUI.Builder(action.id, action.name, action.period, action.incrementBy);
                        return builder.build();
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
