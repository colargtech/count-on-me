package com.colargtech.countonme.ui.count.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.ui.model.ActionUI;

import java.util.Date;

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
public class CountPresenter extends MvpRxBasePresenter<CountView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionUpdateSubject;
    private String actionId;

    @Inject
    public CountPresenter(CountOnMeDBManager countOnMeDBManager, @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.actionUpdateSubject = actionUpdateSubject;
    }

    @Override
    public void onCreate(CountView view) {
        super.onCreate(view);
        this.actionId = view.getActionId();
        addSubscription(subscribe(countOnMeDBManager.getAction(actionId)));
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO fix this because we are receiving again while resubscribe
        addSubscription(subscribe(actionUpdateSubject));
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        this.actionId = null;
    }

    public void increaseOne() {
        countOnMeDBManager.addCount(actionId, new Date(), 1L);
    }

    public void increaseCustom() {
        countOnMeDBManager.addCustom(actionId, new Date());
    }

    public void decreaseOne() {
        countOnMeDBManager.removeCounts(actionId, new Date(), 1L);
    }

    public void decreaseCustom() {
        countOnMeDBManager.decreaseCustom(actionId, new Date());
    }

    private Subscription subscribe(Observable<Action> observable) {
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached() && actionId.equals(actionUI.getId())) {
                            getView().updateView(actionUI);
                        }
                    }
                });
    }
}
