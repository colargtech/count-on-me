package com.colargtech.countonme.ui.action.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Group;
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
 * @author gdfesta.
 */
public class ActionPresenter extends MvpRxBasePresenter<ActionView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionUpdateSubject;

    @Inject
    public ActionPresenter(CountOnMeDBManager countOnMeDBManager,
            @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.actionUpdateSubject = actionUpdateSubject;
    }

    @Override
    public void onResume() {
        super.onResume();
        addSubscription(subscribeUpdate(actionUpdateSubject));
    }

    public Observable<Void> deleteGroup(String groupID) {
        return this.countOnMeDBManager.deleteGroup(groupID);
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
                        return builder.build();
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
