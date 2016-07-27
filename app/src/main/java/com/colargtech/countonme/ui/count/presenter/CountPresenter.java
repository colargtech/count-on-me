package com.colargtech.countonme.ui.count.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.ActionCount;
import com.colargtech.countonme.model.Range;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author juancho.
 */
@Singleton
public class CountPresenter extends MvpRxBasePresenter<CountView> {

    private final CountOnMeDBManager countOnMeDBManager;

    @Inject
    public CountPresenter(CountOnMeDBManager countOnMeDBManager) {
        this.countOnMeDBManager = countOnMeDBManager;
    }

    @Override
    public void onCreate(CountView view) {
        super.onCreate(view);
        for (Range range : getView().getRanges()) {
            addSubscription(subscribeCount(countOnMeDBManager.getCountForAction(view.getActionUI(), range)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Range range : getView().getRanges()) {
            addSubscription(subscribeCount(countOnMeDBManager.subscribeUpdateOnActionCount(getView().getActionUI(), range)));
        }
    }

    public void increaseOne() {
        if (isViewAttached()) {
            countOnMeDBManager.addCount(getView().getActionUI(), new Date(), 1L);
        }
    }

    public void increaseCustom() {
        if (isViewAttached()) {
            countOnMeDBManager.addCustom(getView().getActionUI(), new Date());
        }
    }

    public void decreaseOne() {
        if (isViewAttached()) {
            countOnMeDBManager.removeCounts(getView().getActionUI(), new Date(), 1L);
        }
    }

    public void decreaseCustom() {
        if (isViewAttached()) {
            countOnMeDBManager.decreaseCustom(getView().getActionUI(), new Date());
        }
    }

    private Subscription subscribeCount(Observable<ActionCount> observable) {
        return observable
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ActionCount>() {
                    @Override
                    public void call(ActionCount actionCount) {
                        if (isViewAttached()) {
                            getView().updateActionCount(actionCount);
                        }
                    }
                });
    }
}
