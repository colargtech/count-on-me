package com.colargtech.countonme.ui.home.presenter;

import android.database.Cursor;
import android.util.Log;

import com.colargtech.countonme.commons.mvp.MvpBaseRxPresenter;
import com.colargtech.countonme.db.model.GroupDb;
import com.colargtech.countonme.ui.home.view.HomeView;
import com.colargtech.countonme.ui.model.Group;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author juancho.
 */
public class HomePresenter extends MvpBaseRxPresenter<HomeView> {

    BriteDatabase db;

    public HomePresenter(BriteDatabase db) {
        this.db = db;
    }

    public void init() {
        registerShowGroups();
    }


    public void createGroup(String groupName) {
        db.insert(GroupDb.TABLE_NAME,
                new GroupDb.Marshal()
                        .name(groupName)
                        .asContentValues()
        );
    }

    private void registerShowGroups() {
        Observable<SqlBrite.Query> groups = db.createQuery(GroupDb.TABLE_NAME, GroupDb.SELECT_ALL);
        Subscription selectAllSubscription = groups
                .map(new Func1<SqlBrite.Query, Cursor>() {
                    @Override
                    public Cursor call(SqlBrite.Query query) {
                        return query.run();
                    }
                })
                .filter(new Func1<Cursor, Boolean>() {
                    @Override
                    public Boolean call(Cursor cursor) {
                        return cursor != null;
                    }
                })
                .map(new Func1<Cursor, List<Group>>() {
                    @Override
                    public List<Group> call(Cursor cursor) {
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            List<Group> groups = new ArrayList<>();
                            do {
                                GroupDb group = GroupDb.MAPPER.map(cursor);
                                groups.add(new Group(group._id(), group.name()));
                            } while (cursor.moveToNext());
                            cursor.close();
                            return groups;
                        } else {
                            cursor.close();
                            return new ArrayList<>();
                        }

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Group>>() {
                    @Override
                    public void call(List<Group> groups) {
                        if (isViewAttached()) {
                            getView().showGroups(groups);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("", "Error");
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        Log.d("", "Completed");
                    }
                });
        addSubs(selectAllSubscription);
    }
}
