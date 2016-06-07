package com.colargtech.countonme.commons.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author festa.
 */

public interface ViewTypeDelegateAdapter<VH extends RecyclerView.ViewHolder, V extends ViewType> {

    VH onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(VH holder, V item);
}
