package com.work.holders;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.work.R;
import com.work.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * BaseViewHolder Class
 * Created by hmju on 2019-02-20.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public abstract void onBindView(int pos, @NonNull T data);

    protected Context mContext;
    protected View mRootView;
    protected T mData;

    @Nullable
    @BindView(R.id.root_rl_layout)
    protected RelativeLayout mRootLayout;

    @Nullable
    @BindView(R.id.root_tv_title)
    protected TextView mRootTitleTextView;

    @Nullable
    @BindView(R.id.root_tv_msg)
    protected TextView mRootMsgTextView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mRootView = itemView;
        ButterKnife.bind(this, mRootView);
    }

    /**
     * 공통 클릭 함수.
     *
     * @param id
     * @author hmju
     */
    @Optional
    @OnClick({R.id.root_rl_layout, R.id.root_tv_title, R.id.root_tv_msg})
    public void commonClick(View id) {
        switch (id.getId()) {
            case R.id.root_rl_layout:
                Logger.d("전체 레이아웃 클릭!");
                break;
            case R.id.root_tv_title:
                Logger.d("타이틀 텍스트 클릭!");
                break;
            case R.id.root_tv_msg:
                Logger.d("메시지 텍스트 클릭!");
                break;
        }
    }
}
