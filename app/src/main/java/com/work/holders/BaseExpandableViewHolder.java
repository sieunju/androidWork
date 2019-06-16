package com.work.holders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * Created by hmju on 2019-04-07.
 */
public abstract class BaseExpandableViewHolder<T> extends RecyclerView.ViewHolder {

    public abstract void onBindView(int pos, @NonNull T data);

    protected Context mContext;
    protected View mRootView;
    protected T mData;

    public BaseExpandableViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mRootView = itemView;
        ButterKnife.bind(this, mRootView);
    }


}
