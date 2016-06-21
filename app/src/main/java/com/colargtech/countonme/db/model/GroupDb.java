package com.colargtech.countonme.db.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * @author juancho.
 */

public class GroupDb implements GroupModel {

    private static final Factory<GroupDb> GROUP_DB_FACTORY = new Factory<>(new Creator<GroupDb>() {
        @Override
        public GroupDb create(long _id, String name) {
            return new GroupDb(_id, name);
        }
    });
    private final long id;
    private final String name;

    public GroupDb(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public long _id() {
        return id;
    }

    @NonNull
    @Override
    public String name() {
        return name;
    }

    public static ContentValues marshal(String groupName) {
        return new GroupModel.Marshal()
                .name(groupName)
                .asContentValues();
    }

    public static GroupDb mapper(Cursor cursor) {
        return new GroupModel.Mapper<>(GROUP_DB_FACTORY).map(cursor);
    }

}
