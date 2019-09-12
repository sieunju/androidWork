package com.work.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.work.R;
import com.work.adapters.FruitAdapter;
import com.work.interfaces.CustomStickyInterface;
import com.work.itemDecoration.HeaderItemDecoration;
import com.work.response.BaseResponseData;
import com.work.utils.CommandUtil;
import com.work.utils.CustomLayoutManager;
import com.work.views.ParallaxHeaderView;

import java.util.HashMap;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Coordilayout 을 이용한 특정뷰 리사이징 Activity Class
 * Created by hmju on 2019-03-01
 */
public class ResizeRecyclerViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FruitAdapter mAdapter;
    private CustomLayoutManager mLayoutManager;
    private BaseResponseData mData;

    @BindView(R.id.header_view)
    ParallaxHeaderView mHeaderView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindColor(R.color.color_1CC22F)
    int mRefreshColor;
    @BindDimen(R.dimen.size_5)
    int mDivHeight;
    @BindString(R.string.str_tag_resize)
    String mHeaderTag;
    @BindDimen(R.dimen.size_35)
    int mHeaderStickerHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expands_recycler_view);
        ButterKnife.bind(this);

        setData();
        setAdapter();
        setRefresh();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     * set Dummy Data
     *
     * @author hmju
     */
    private void setData() {
        mData = CommandUtil.getInstance().getFileToJson(mContext, "json_resize_activity.txt", BaseResponseData.class);
    }

    /**
     * setAdapter
     *
     * @author hmju
     */
    private void setAdapter() {
        mAdapter = new FruitAdapter(mContext);
        mLayoutManager = new CustomLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        HeaderItemDecoration decoration = new HeaderItemDecoration(mStickyInterface);
        decoration.setHeaderHeight(mHeaderStickerHeight);
        decoration.setStickyTag(mHeaderTag);
        decoration.setVertical(mDivHeight);

        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mData, mStickyHeaderListener);
    }

    private CustomStickyInterface mStickyInterface = new CustomStickyInterface() {

        @Override
        public void bindData(View view, String tag) {
            TextView txtView = view.findViewById(R.id.sticker_text_view);
            tag = tag.replace(mHeaderTag, "");
            txtView.setText(tag);
        }
    };

    /**
     * 스티커 헤더 타이틀 값 동작 리스너.
     *
     * @author hmju
     */
    public HeaderItemDecoration.StickyHeaderInterface mStickyHeaderListener = new HeaderItemDecoration.StickyHeaderInterface() {
        String previousTitle = "";
        HashMap<Integer, String> mHeaderMap;

        @Override
        public void setHeaderMap(HashMap<Integer, String> tmpMap) {
            mHeaderMap = tmpMap;
        }

        @Override
        public int getHeaderPositionForItem(int itemPosition) {
            return itemPosition;
        }

        @Override
        public int getHeaderLayout(int headerPosition) {
            return R.layout.view_sticker_header;
        }

        @Override
        public void bindHeaderData(View header, int headerPosition) {
            TextView title = (TextView) header.findViewById(R.id.sticker_text_view);
            if (mHeaderMap.containsKey(headerPosition)) {
                String value = mHeaderMap.get(headerPosition);
                previousTitle = value;
                title.setText(value);
            } else {
                title.setText(previousTitle);
            }
        }

        @Override
        public boolean isHeader(int itemPosition) {
            if (mHeaderMap.containsKey(itemPosition)) {
                return true;
            } else {
                return false;
            }
        }
    };

    private RecyclerView.SimpleOnItemTouchListener mSampleListener = new RecyclerView.SimpleOnItemTouchListener(){
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            return super.onInterceptTouchEvent(rv, e);
        }
    };

    /**
     * setRefresh
     *
     * @author hmju
     */
    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(mRefreshColor);
    }
}
