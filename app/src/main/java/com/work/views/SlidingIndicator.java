package com.work.views;

import com.work.R;
import com.work.utils.Logger;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * androidwork Class: SlidingIndicator Created by jsieu on 2020-02-29.
 * <p>
 * Description: 슬라이딩 애니메이션 효과 인디케이터 View Class
 */
public class SlidingIndicator extends ConstraintLayout {

    private final Context mContext;
    private ConstraintLayout mClForeground;
    private float mWidth;
    private ConstraintLayout.LayoutParams mLayoutParams =
            new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
    private int mViewPagerSize;
    private float mRatioWidth;
    private float mRootWidth;
    private int mTop;
    private int mBottom;

    private boolean mIsNoAni = false;   // 애니메이션 효과 유무

    public SlidingIndicator(Context ctx) {
        this(ctx, null);
    }

    public SlidingIndicator(@NonNull Context ctx, @Nullable AttributeSet attrs) {
        super(ctx, attrs);
        mContext = ctx;
        initView(attrs);
    }

    private void initView(@Nullable AttributeSet attrs) {
        if (isInEditMode()) return;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return;

        final View rootView = inflater.inflate(R.layout.view_sliding_indicator, this, false);

        mClForeground = rootView.findViewById(R.id.cl_foreground);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                mTop = rootView.getTop();
                mBottom = rootView.getBottom();
                mRootWidth = rootView.getWidth();
                setRatioWidth(rootView.getWidth());
//                bindIndicator(1.F);
            }
        });
        // 화면 보이게.
        addView(rootView);
    }

    public void setViewPagerSize(int size) {
        mViewPagerSize = size;
    }

    private void setRatioWidth(int width) {
        // RootView 의 맞게 비율 계산
        float ratioWidth = (1.F / (float) mViewPagerSize) * width;
        Logger.d("Ratio Width\t" + ratioWidth);
        mRatioWidth = ratioWidth;
        mLayoutParams.width = Math.round(mRatioWidth);  // 반올림 계산
        mClForeground.setLayoutParams(mLayoutParams);
    }

    /**
     * Scroll Offset 에 따라서 Indicator 처리.
     *
     * @param offset Scroll Offset..
     */
    public void bindIndicator(float offset) {
        if (!mIsNoAni) {
            float ratio = (offset / (float) mViewPagerSize) * mRootWidth;
//            Logger.d("bindIndicator ratio\t" + ratio);
            int left = Math.round(Math.abs(ratio - mRatioWidth));
            int right = Math.round(mRatioWidth + left);
            Logger.d("bindIndicator Left\t" + left);
            mClForeground.layout(left, mTop, right, mBottom);
        }
    }

    /**
     * 인디게이터 애니메이션 X
     *
     * @param pos 1 Or ViewPagerSize
     */
    public void bindIndicatorNoAni(int pos) {
        // 1. 5
        float leftRatio = (pos / (float) mViewPagerSize) * mRootWidth;
        float leftPos = Math.abs(leftRatio - mRatioWidth);

        int left = Math.round(leftPos);
        int right = Math.round(mRatioWidth + left);
//        mIsNoAni = true;
//        mClForeground.animate().translationX(leftPos).withLayer();
//        mClForeground.setAlpha(0);
//        mClForeground.animate().alpha(1).withLayer();
        mClForeground.layout(left, mTop, right, mBottom);
        Logger.d("TEST:: bindIndicatorNoAni Left\t" + left + "\tRight\t" + right);
    }
}
