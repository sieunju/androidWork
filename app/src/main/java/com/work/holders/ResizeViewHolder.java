package com.work.holders;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.HashMap;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import com.work.R;
import com.work.structs.ResizeStruct;

/**
 * 원하는 위치에 맞게 이미지 사이즈가 확장되거나 줄어드는 뷰 홀더
 * Created by hmju on 2019-02-24.
 */
public class ResizeViewHolder extends BaseViewHolder<ResizeStruct>
        implements ViewTreeObserver.OnScrollChangedListener {

    ViewGroup mViewGroup;
    @BindView(R.id.resize_rl_layout)
    RelativeLayout mResizeLayout;
    @BindView(R.id.resize_center_rl_layout)
    RelativeLayout mResizeAlphaLayout;
    @BindView(R.id.resize_center_text_view)
    TextView mCenterTextView;
    @BindView(R.id.resize_bottom_text_view)
    TextView mBottomTextView;
    @BindDimen(R.dimen.size_100)
    int mMaxHeight;
    @BindDimen(R.dimen.size_50)
    int mMinHeight;
    @BindString(R.string.tag_resize)
    String mTagFormat;
    private ResizeStruct mData;

    private RelativeLayout.LayoutParams mLayoutParams =
            new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

    private int mDisplayY = 0;
    private int mCalculation;
    private double mEvtStartPosition;
    private double mEvtFinalPosition;
    private boolean mIsExpands = false;
    private int mPosition = 0;
    // 각 뷰 홀더 마다 높이 값을 저장해서 관리하는 map.
    private HashMap<Integer, Integer> mHeightMap = new HashMap<Integer, Integer>();

    public ResizeViewHolder(View itemView, ViewGroup parent) {
        super(itemView);
        mViewGroup = parent;
        initSize();
        setObserver();
    }

    /**
     * 여러 사이즈들 초기화 하는 부분.
     */
    private void initSize() {
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mDisplayY = size.y;
        // 1/6 지점 , 4/6 지점
        mEvtStartPosition = (double) mDisplayY - (mDisplayY * (5.0 / 10.0));
        mEvtFinalPosition = (double) mDisplayY * (2.0 / 10.0);
        mCalculation = mMaxHeight - mMinHeight;
    }

    /**
     * set Listener
     */
    private void setObserver() {
        mViewGroup.getViewTreeObserver().removeOnScrollChangedListener(this);
        mViewGroup.getViewTreeObserver().addOnScrollChangedListener(this);
    }

    public static ResizeViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_resize, parent, false);
        return new ResizeViewHolder(view, parent);
    }

    @Override
    public void onBindView(int pos, @NonNull ResizeStruct data) {
        mPosition = pos;
        mData = data;
        bindData();
    }

    private void bindData() {
        if (mData == null) {
            return;
        }

        if (mCenterTextView != null) {
            mCenterTextView.setText(mData.getTitle());
        }

        if (mBottomTextView != null) {
            mBottomTextView.setText(mData.getTitle());
        }
    }

    // 뷰 홀더 활성화
    public void onEnabled() {
        mIsExpands = true;

        // saveTag
        mRootView.setTag(String.format(mTagFormat, mData.getTitle()));

        // 해당 뷰 위치가 한번 사용했던 적이 있으면 맵에 저장되어 있는 값으로 높이값 셋팅
        if (mHeightMap.containsKey(mPosition)) {
            mLayoutParams.height = mHeightMap.get(mPosition);
        }
        // 해당 뷰 위치가 처음 사용한 경우 최소높이값으로 셋팅
        else {
            mLayoutParams.height = mMinHeight;
        }

        if (mResizeLayout != null) {
            mResizeLayout.setLayoutParams(mLayoutParams);
        }
    }

    // 뷰 홀더 비활성화
    public void onDisabled() {
        mIsExpands = false;
        // 비활성화시
        mHeightMap.put(mPosition, mLayoutParams.height);
    }

    @Override
    public void onScrollChanged() {
        if (mIsExpands) {
            View view = mViewGroup.getChildAt(mViewGroup.indexOfChild(mRootView));
            int viewPosition = (view.getBottom() + view.getTop()) / 2;
            if (viewPosition > 0 && viewPosition < mDisplayY) {
                if (mEvtStartPosition >= viewPosition) {
                    // 아래 1/10 지점부터 8/10 지점까지
                    double percentage = (mEvtStartPosition - viewPosition) / (mEvtStartPosition - mEvtFinalPosition);

                    int height = (int) (mCalculation * percentage);
                    height += mMinHeight;
                    // 높이 최대값이상 올라 갈경우 height 값 최대값으로 셋팅.
                    if (height >= mMaxHeight) {
                        height = mMaxHeight;
                    }
                    // 높치 최소값이하인경우 height 값 최소값으로 셋팅.
                    else if (height <= mMinHeight) {
                        height = mMinHeight;
                    }

                    // 높이값에 변화가 있다.
                    if (mLayoutParams.height != height) {
                        mLayoutParams.height = height;
                        if (mResizeLayout != null) {
                            mResizeLayout.setLayoutParams(mLayoutParams);
                        }
                    }

                    // Alpha 값 처리 하는 부분
                    setAlpha(percentage);
                }
            }
        }
    }

    /**
     * set Alpha 처리 해야 하는 부분 처리하는 함수.
     *
     * @param percentage 0.0 ~
     * @author hmju
     */
    private void setAlpha(double percentage) {
        // 가운데 텍스트 알파 값 셋팅.
        if (mCenterTextView == null) {
            return;
        }
        mCenterTextView.setTextColor(Color.parseColor(getPercentageAlpha(percentage)));

        // 아래 텍스트 알파 값 셋팅.
        if (mBottomTextView == null) {
            return;
        }

        if (percentage >= 1.0) {
            percentage = 1.0;
        }
        percentage = 1.0 - percentage;
        mBottomTextView.setTextColor(Color.parseColor(getPercentageAlpha(percentage)));
    }

    /**
     * 0% ~ 100 % 맞게 알파값 HexCode 로 변환
     *
     * @param tmpPercentage
     * @return
     * @author hmju
     */
    private String getPercentageAlpha(double tmpPercentage) {
        String hexCode = "";
        // 퍼센트가 100% 로 넘어가는 것을 방지하기 위한 코드
        if (tmpPercentage >= 1.0) {
            tmpPercentage = 1.0;
        }

        //점점 스크롤을 내릴수록 텍스트가 사라져야 하기때문에 0% ~ 100% 가 아닌
        //100% ~ 0% 로 hexCode 가 나와야 함.
        tmpPercentage = 1.0 - tmpPercentage;

        //HexCode 0~255 에 맞게 계산.
        int round = (int) Math.round(tmpPercentage * 255);
        hexCode = Integer.toHexString(round).toUpperCase();

        if (hexCode.length() == 1) {
            hexCode = "0" + hexCode;
        }

        //텍스트 색상이 흰색.
        hexCode = "#" + hexCode + "FFFFFF";
        return hexCode;
    }
}
