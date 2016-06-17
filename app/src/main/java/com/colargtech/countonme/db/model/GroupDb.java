package com.colargtech.countonme.db.model;

import android.support.annotation.NonNull;

/**
 * @author juancho.
 */

public class GroupDb implements GroupModel {

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

    public static final Mapper<GroupDb> MAPPER = new Mapper<>(new Mapper.Creator<GroupDb>() {
        @Override
        public GroupDb create(long _id, String name) {
            return new GroupDb(_id, name);
        }
    });

    public static final class Marshal extends GroupMarshal<Marshal> {
    }

    // usage example:
    //new GroupDb.Marshal()._id(1).name("").asContentValues();
    //GroupDb.MAPPER.map(cursor)
}
