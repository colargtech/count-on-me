package com.colargtech.countonme.ui.action;

import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.ui.model.ActionUI;
import com.colargtech.countonme.ui.model.PeriodUI;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author juancho.
 */
public class ActionsRxMapper {

    public static Observable<ActionUI> fromActionToActionUI(Observable<Action> observable) {
        return observable.map(
                new Func1<Action, ActionUI>() {
                    @Override
                    public ActionUI call(Action action) {
                        ActionUI.Builder builder = new ActionUI.Builder(action.id, action.name, PeriodUI.valueOf(action.period.name()), action.incrementBy);
                        return builder.build();
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
