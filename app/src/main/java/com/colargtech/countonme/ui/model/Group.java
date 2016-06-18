package com.colargtech.countonme.ui.model;

import com.colargtech.adapters.ViewType;
import com.colargtech.countonme.ui.adapter.AdapterConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdfesta
 */
public class Group implements ViewType {
    private final long id;
    private final String name;
    private final List<Action> actions;

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
        this.actions = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void removeAction(Action action) {
        this.actions.remove(action);

    }

    @Override
    public int getViewType() {
        return AdapterConstants.GROUP;
    }
}
