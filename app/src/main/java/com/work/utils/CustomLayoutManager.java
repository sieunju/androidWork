package com.work.utils;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom RecyclerView LayoutManager Class
 * Created by hmju on 2019-02-24.
 */
public class CustomLayoutManager extends LinearLayoutManager {

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        return super.scrollVerticallyBy(dy, recycler, state);
    }



    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {

        return super.computeVerticalScrollOffset(state);
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.onFocusSearchFailed(focused, focusDirection, recycler, state);
    }

    @Override
    public void calculateItemDecorationsForChild(View child, Rect outRect) {
        if (child.getTag() != null) {
            Logger.d("TEST:: Child Tag " + child.getTag());
        }

        Logger.d("TEST:: Rect Top " + outRect.top + " : Right : " + outRect.right +
                ": Bottom :" + outRect.bottom + ": Left :" + outRect.left);
        super.calculateItemDecorationsForChild(child, outRect);
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        return super.computeScrollVectorForPosition(targetPosition);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    // LayoutManager Override [Start]
    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }



// LayoutManager Override [End]
}
