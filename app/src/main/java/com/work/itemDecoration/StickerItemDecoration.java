package com.work.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.R;
import com.work.utils.Logger;

/**
 * Created by hmju on 2019-03-23.
 */
public class StickerItemDecoration extends RecyclerView.ItemDecoration {

    public enum TYPE {VERTICAL, HORIZONTAL}

    private final Context mContext;
    private TYPE mType;
    private int mDivHeight;
    private View mStickerHeaderView;
    private TextView mStickerTextView;

    private String mTagSticker;

    public StickerItemDecoration(Context context) {
        super();
        mContext = context;
    }

    /**
     * set Sticker Tag
     *
     * @param tag
     * @author hmju
     */
    public void setStickerTag(String tag) {
        mTagSticker = tag;
    }

    /**
     * setter 수직 여백
     *
     * @param vertical
     * @return
     * @author hmju
     */
    public StickerItemDecoration setVertical(int vertical) {
        mType = TYPE.VERTICAL;
        mDivHeight = vertical;
        return this;
    }

    /**
     * setter 수평 여백
     *
     * @param horizontal
     * @return
     * @author hmju
     */
    public StickerItemDecoration setHorizontal(int horizontal) {
        mType = TYPE.HORIZONTAL;
        mDivHeight = horizontal;
        return this;
    }

    public StickerItemDecoration setType(TYPE type) {
        mType = type;
        return this;
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
//        View view = parent.getChildAt(0);
//        // 유효성 체크
//        if (view == null) {
//            return;
//        }

        // initHeaderView
        if (mStickerHeaderView == null) {
            mStickerHeaderView = initStickerView(parent);
            mStickerTextView = mStickerHeaderView.findViewById(R.id.sticker_text_view);
            fixLayoutSize(mStickerHeaderView, parent);
        }

        int size = parent.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = parent.getChildAt(i);
            if (view == null || view.getTag() == null) {
                continue;
            }

            Logger.d("TEST:: ChildView " + view.getTag());

            if (view.getTag() != null && view.getTag().toString().contains(mTagSticker)) {
                String title = view.getTag().toString().replace(mTagSticker, "");
                mStickerTextView.setText(title);
            }
        }

//        CharSequence previousHeader = "";
//        for (int i = 0; i < parent.getChildCount(); i++) {
//            View child = parent.getChildAt(i);
//            final int position = parent.getChildAdapterPosition(child);
//
//            CharSequence title = mSectionListener.getSectionHeader(position);
//            mStickerTextView.setText(title);
//            if (!previousHeader.equals(title) || mSectionListener.isSection(position)) {
//                previousHeader = title;
//            }
//        }
        drawHeader(canvas, mStickerHeaderView);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // 마지막이 아닌경우 아래 여백
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            if (mType == TYPE.VERTICAL) {
                outRect.bottom = mDivHeight;
            } else {
                outRect.right = mDivHeight;
            }
        }
    }

    private View initStickerView(RecyclerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.view_sticker_header, parent, false);
    }

    private void fixLayoutSize(View view, ViewGroup parent) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(),
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(),
                View.MeasureSpec.UNSPECIFIED);
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(),
                view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(),
                view.getLayoutParams().height);

        view.measure(childWidth, childHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    /**
     * Draw Sticker Header
     *
     * @param canvas
     * @param child
     * @param headerView
     * @author hmju
     */
    private void drawHeader(Canvas canvas, View child, View headerView) {
        canvas.save();
        canvas.translate(0, Math.max(0, child.getTop() - headerView.getHeight()));
        headerView.draw(canvas);
        canvas.restore();
    }

    private void drawHeader(Canvas canvas, View headerView) {
        canvas.save();
        canvas.translate(0, 0);
        headerView.draw(canvas);
        canvas.restore();
    }
}
