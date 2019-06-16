package com.work.itemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView CardView 여백 클래스
 * Created by hmju on 2019-02-27.
 */
public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpace = 0;
    private int mHorizontalSpace = 0;
    private TYPE mType;

    public enum TYPE {VERTICAL, HORIZONTAL}

    public RecyclerItemDecoration() {
        super();
    }

    /**
     * setter 수직 여백
     *
     * @param vertical
     * @return
     * @author hmju
     */
    public RecyclerItemDecoration setVertical(int vertical) {
        mVerticalSpace = vertical;
        return this;
    }

    /**
     * setter 수평 여백
     *
     * @param horizontal
     * @return
     * @author hmju
     */
    public RecyclerItemDecoration setHorizontal(int horizontal) {
        mHorizontalSpace = horizontal;
        return this;
    }

    public RecyclerItemDecoration setType(TYPE type) {
        mType = type;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 마지막이 아닌경우 아래 여백
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            if (mType == TYPE.VERTICAL) {
                outRect.bottom = mVerticalSpace;
            } else {
                outRect.right = mHorizontalSpace;
            }
        }
    }
}
