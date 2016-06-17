package com.colargtech.countonme.ui.home.view;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.colargtech.countonme.commons.ui.BaseActivity;
import com.colargtech.countonme.db.model.CountOnMeOpenHelper;
import com.colargtech.countonme.db.model.GroupDb;
import com.colargtech.countonme.ui.model.Group;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseActivity implements HomeFragment.HomeNavigation {

    SqlBrite sqlBrite;
    BriteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqlBrite = SqlBrite.create();
        db = sqlBrite.wrapDatabaseHelper(new CountOnMeOpenHelper(getApplicationContext()), Schedulers.io());

        Observable<SqlBrite.Query> groups = db.createQuery(GroupDb.TABLE_NAME, GroupDb.SELECT_ALL);
        groups.subscribe(new Action1<SqlBrite.Query>() {
            @Override
            public void call(SqlBrite.Query query) {
                Cursor c = query.run();
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    List<GroupDb> gs = new ArrayList<GroupDb>();
                    do {
                        gs.add(GroupDb.MAPPER.map(c));
                    } while (c.moveToNext());

                }
                c.close();
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

        int delete = db.delete(GroupDb.TABLE_NAME, "");

        //db.insert(GroupDb.TABLE_NAME, new GroupDb.Marshal().name("Group 2").asContentValues());


        if (savedInstanceState == null) {
            changeFragment(HomeFragment.newInstance());
        }
    }


    @Override
    public void createGroup() {
        // TODO
        db.insert(GroupDb.TABLE_NAME, new GroupDb.Marshal().name("Group Nuevo").asContentValues());
    }

    @Override
    public void showDetailGroup(Group group) {
        // TODO
        Toast.makeText(this, "Show Group with name: " + group.getName(), Toast.LENGTH_SHORT).show();
    }
}
