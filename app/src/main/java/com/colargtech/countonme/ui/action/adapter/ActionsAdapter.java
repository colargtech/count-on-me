package com.colargtech.countonme.ui.action.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;

import com.colargtech.adapters.BaseDelegateAdapter;
import com.colargtech.countonme.ui.adapter.AdapterConstants;
import com.colargtech.countonme.ui.model.ActionUI;

/**
 * @author juancho.
 */
public class ActionsAdapter extends BaseDelegateAdapter {

    public ActionsAdapter(ActionDelegateAdapter.ActionAdapterActions actionAdapterActions) {
        delegateAdapters = new SparseArrayCompat<>(1);
        delegateAdapters.put(AdapterConstants.ACTION, new ActionDelegateAdapter(actionAdapterActions));
    }

    public void addAction(ActionUI actionUI) {
        int position = this.addAction(this.items.size(), actionUI);
        if (position != RecyclerView.NO_POSITION) {
            notifyItemInserted(position);
        }
    }

    public void updateActionUI(ActionUI actionUI) {
        int position = removeActionUI(actionUI);
        if (position != RecyclerView.NO_POSITION) {
            position = addAction(position, actionUI);
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(position);
            }
        }
    }

    private int removeActionUI(ActionUI actionUI) {
        int position = this.items.indexOf(actionUI);
        if (position != RecyclerView.NO_POSITION) {
            this.items.remove(actionUI);
        }
        return position;
    }

    private int addAction(int position, ActionUI actionUI) {
        int newPosition = RecyclerView.NO_POSITION;
        if (!items.contains(actionUI)) {
            newPosition = position;
            items.add(position, actionUI);
        }
        return newPosition;
    }
}
