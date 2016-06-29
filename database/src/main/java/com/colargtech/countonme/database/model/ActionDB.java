package com.colargtech.countonme.database.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author gdfesta
 */

public class ActionDB extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String name;

    public ActionDB() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
