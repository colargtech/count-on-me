package com.colargtech.countonme.ui.model;

import com.colargtech.countonme.ui.adapter.AdapterConstants;
import com.colargtech.countonme.commons.adapter.ViewType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdfesta
 */
public class Group implements ViewType{
    private final String name;
    private final List<Action> actions;

    public Group(String name) {
        this.name = name;
        this.actions = new ArrayList<>();
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
