package com.colargtech.countonme.ui.action.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colargtech.adapters.ViewTypeDelegateAdapter;
import com.colargtech.countonme.R;
import com.colargtech.countonme.ui.model.ActionUI;

/**
 * @author juancho.
 */

public class ActionDelegateAdapter implements ViewTypeDelegateAdapter<ActionDelegateAdapter.ActionViewHolder, ActionUI> {

    public interface ActionAdapterActions {
        void showActionDetail(ActionUI actionUI);
    }

    private ActionAdapterActions actionAdapterActions;

    public ActionDelegateAdapter(ActionAdapterActions actionAdapterActions) {
        this.actionAdapterActions = actionAdapterActions;
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ActionViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, ActionUI item) {
        holder.bind(item);
    }

    class ActionViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        ActionViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_group_item, parent, false));
            name = (TextView) itemView.findViewById(R.id.group_name);
        }

        void bind(final ActionUI actionUI) {
            name.setText(actionUI.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionAdapterActions.showActionDetail(actionUI);
                }
            });
        }
    }
}
