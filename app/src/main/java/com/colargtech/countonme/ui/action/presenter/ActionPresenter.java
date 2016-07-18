package com.colargtech.countonme.ui.action.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.ui.model.ActionUI;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

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
@Singleton
public class ActionPresenter extends MvpRxBasePresenter<ActionView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionCreateSubject;
    private final Subject<Action, Action> actionUpdateSubject;


    @Inject
    public ActionPresenter(CountOnMeDBManager countOnMeDBManager, @Named("ActionCreate") Subject<Action, Action> actionCreateSubject,
                           @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.actionCreateSubject = actionCreateSubject;
        this.actionUpdateSubject = actionUpdateSubject;
    }

    @Override
    public void onCreate(ActionView view) {
        super.onCreate(view);
        addSubscription(subscribe(countOnMeDBManager.getAllActions(view.getGroupID())));
    }

    @Override
    public void onResume() {
        super.onResume();
        addSubscription(subscribe(actionCreateSubject));
        addSubscription(subscribeUpdate(actionUpdateSubject));
    }

    public void createAction(String groupId, String name) {
        countOnMeDBManager.createAction(groupId, name);
    }

    private Subscription subscribe(Observable<Action> observable) {
        return fromActionToActionUI(observable)
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI groupUI) {
                        if (isViewAttached()) {
                            getView().addActionUI(groupUI);
                        }
                    }
                });
    }

    private Subscription subscribeUpdate(Observable<Action> actionSub) {
        return fromActionToActionUI(actionSub)
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached()) {
                            getView().updateActionUI(actionUI);
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
                        builder.withCountForDate(action.countsByDate);
                        return builder.build();
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
