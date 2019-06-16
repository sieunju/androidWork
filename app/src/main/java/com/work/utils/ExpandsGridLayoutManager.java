package com.work.utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 확장 가능한 RecyclerView 사용되는 LayoutManager Class
 * Created by hmju on 2019-04-06.
 */
public class ExpandsGridLayoutManager extends GridLayoutManager {

    public ExpandsGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }
}
