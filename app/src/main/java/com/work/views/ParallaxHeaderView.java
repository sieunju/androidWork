package com.work.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.work.R;

/**
 * Created by hmju on 2019-02-25.
 */
public class ParallaxHeaderView extends CoordinatorLayout {

    protected final Context mContext;
    CollapsingToolbarLayout mRootView;
    ImageView mHeaderImageView;
    Toolbar mHeaderToolBar;

    // [s] Attr Define
    final int ID_IMAGE_ENABLED = R.styleable.ParallaxHeaderView_image_view_enable;
    // [e] Attr Define

    private boolean mImageViewVisible = true;

    public ParallaxHeaderView(Context context) {
        this(context, null);
    }

    public ParallaxHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        // 속성 값 셋팅
        if (attrs != null) {
            TypedArray attrArray = mContext.obtainStyledAttributes(attrs, R.styleable.ParallaxHeaderView);
            mImageViewVisible = attrArray.getBoolean(ID_IMAGE_ENABLED, true);
            attrArray.recycle();
        }

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(infService);
        if (layoutInflater == null) return;

        View view = layoutInflater.inflate(R.layout.parallax_header_view, this, false);

        mRootView = view.findViewById(R.id.header_layout);
        mHeaderImageView = view.findViewById(R.id.header_image_view);
        mHeaderToolBar = view.findViewById(R.id.header_toolbar);

        addView(view);

        mHeaderImageView.setVisibility((mImageViewVisible) ? VISIBLE : GONE);
    }
}
