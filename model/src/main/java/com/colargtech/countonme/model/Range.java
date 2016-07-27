package com.colargtech.countonme.model;

import java.util.Date;

/**
 * @author gdfesta
 */

public class Range {
    public final Date from;
    public final Date to;

    public Range(Date from, Date to) {
        this.from = from;
        this.to = to;
    }
}
