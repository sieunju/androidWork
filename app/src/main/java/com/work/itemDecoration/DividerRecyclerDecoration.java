package com.work.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.work.R;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

/**
 * androidwork
 * Class: DividerRecyclerDecoration
 * Created by jsieu on 2019-09-04.
 * <p>
 * Description: 위에서 구분선 칠하도록 onDraw 에 설정함
 */
public class DividerRecyclerDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private final Rect mBounds = new Rect();

    public DividerRecyclerDecoration(Context ctx) {
        mDivider = ContextCompat.getDrawable(ctx, R.drawable.divider_vertical_size_1);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        canvas.save();
        final int left = 0;
        final int right = parent.getWidth();

        final int childCnt = parent.getChildCount();

        for (int i = 0; i < childCnt; i++) {
            View child = parent.getChildAt(i);

            int pos = parent.getChildAdapterPosition(child);

            // 첫번째 포지션이나, 맨마지막 포지션 아래 로직 Pass
            if (pos == 0 || pos == state.getItemCount() - 1) {
                continue;
            }

            // Divider Rect + ChildView Height 의 좌표값 -> mBounds
            parent.getDecoratedBoundsWithMargins(child, mBounds);

            // 구분선 기준은 위에서 칠한다.
            int top = mBounds.top;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int pos = parent.getChildAdapterPosition(view);

        // onDraw 함수에 맞게 첫번쨰와 맨 마지막 View 는 구분선을 그리지 않는다.
        if (pos == NO_POSITION || pos == 0 || pos == state.getItemCount() - 1) {
            outRect.setEmpty();
        }
        // 나머지 경우 구분선을 그린다.
        else {
            // 구부선은 위에서 칠하도록 함.
            outRect.set(0, mDivider.getIntrinsicHeight(), 0, 0);
        }
    }
}
