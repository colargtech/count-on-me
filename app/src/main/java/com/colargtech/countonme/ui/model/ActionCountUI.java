package com.colargtech.countonme.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author gdfesta
 */

public class ActionCountUI implements Parcelable {
    private final String actionId;
    private final Date from;
    private final Date to;
    private final long count;

    public ActionCountUI(String actionId, Date from, Date to, long count) {
        this.actionId = actionId;
        this.from = from;
        this.to = to;
        this.count = count;
    }

    protected ActionCountUI(Parcel in) {
        actionId = in.readString();
        count = in.readLong();
        from = new Date(in.readLong());
        to = new Date(in.readLong());
    }

    public static final Creator<ActionCountUI> CREATOR = new Creator<ActionCountUI>() {
        @Override
        public ActionCountUI createFromParcel(Parcel in) {
            return new ActionCountUI(in);
        }

        @Override
        public ActionCountUI[] newArray(int size) {
            return new ActionCountUI[size];
        }
    };

    public String getActionId() {
        return actionId;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public long getCount() {
        return count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(actionId);
        parcel.writeLong(count);
        parcel.writeLong(from.getTime());
        parcel.writeLong(to.getTime());
    }
}
