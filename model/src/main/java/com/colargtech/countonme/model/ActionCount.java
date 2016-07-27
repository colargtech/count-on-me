package com.colargtech.countonme.model;

import java.util.Date;

/**
 * @author gdfesta
 */

public class ActionCount {
    public final String actionId;
    public final Range range;
    public final long count;

    public ActionCount(String actionId, Range range, long count) {
        this.actionId = actionId;
        this.range = range;
        this.count = count;
    }

}
