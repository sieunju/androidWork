package com.work.interfaces;

import android.view.View;

import com.work.R;
import com.work.itemDecoration.HeaderItemDecoration;

import java.util.LinkedHashMap;

/**
 * RecyclerView 스티커 인터페이스.
 * Created by hmju on 2019-06-06.
 */
public abstract class CustomStickyInterface implements HeaderItemDecoration.BaseStickyListener {

    private String mTxtPrevious = "";
    private LinkedHashMap<Integer, View> mStickyMap = new LinkedHashMap<>();

    /**
     * 테그 값으로 스티커 뷰 Bind 하는 타입.
     *
     * @param view StickyView
     * @param tag  String Tag
     */
    public abstract void bindData(View view, String tag);

    @Override
    public int getStickyResId(int pos) {
        return R.layout.view_sticker_header;
    }

    @Override
    public void bindSticky(int pos, View view) {
        bindData(view, mTxtPrevious);
    }

    @Override
    public void bindSticky(int pos, Object obj, View view) {
        bindData(view, (String) obj);
        mTxtPrevious = (String) obj;
        mStickyMap.put(pos, view);
    }

    @Override
    public boolean isHeaderView(int pos) {
        // 헤더 뷰이다.
        if (mStickyMap.containsKey(pos)) {
            return true;
        }
        // 헤더 뷰가 아니다.
        else {
            return false;
        }
    }
}
