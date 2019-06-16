package com.work.itemDecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.utils.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 특정 헤더가 플로팅처럼 보이게 하는 RecyclerView.ItemDecoration Class
 * Created by hmju on 2019-03-24.
 */
public class HeaderItemDecoration extends RecyclerView.ItemDecoration {

    public enum TYPE {VERTICAL, HORIZONTAL}

    private TYPE mType;
    private int mDivHeight;

    private int mStickyHeaderHeight;
    private String mStickyFormat;
    private StickyHeaderInterface mListener;
    private BaseStickyListener mStickyListener;
    private int mStickyIndex = -1;

    class ViewInfo {
        int pos;
        View view;

        ViewInfo(int pos, View view) {
            this.pos = pos;
            this.view = view;
        }
    }

    public LinkedHashMap<Integer, ViewInfo> mStickyMap = new LinkedHashMap<>();


    public HeaderItemDecoration(@NonNull StickyHeaderInterface listener) {
        mListener = listener;
    }

    public HeaderItemDecoration(@NonNull BaseStickyListener listener) {
        mStickyListener = listener;
    }

    /**
     * set 스티커 높이값
     *
     * @param height
     * @return
     * @author hmju
     */
    public HeaderItemDecoration setHeaderHeight(int height) {
        mStickyHeaderHeight = height;
        return this;
    }

    /**
     * set 스티커를 표시하기 위한 테그값 패턴
     *
     * @param tagFormat tag_resize_%s
     * @return
     * @author hmju
     */
    public HeaderItemDecoration setStickyTag(String tagFormat) {
        mStickyFormat = tagFormat;
        return this;
    }

    /**
     * setter 수직 여백
     *
     * @param vertical
     * @return
     * @author hmju
     */
    public HeaderItemDecoration setVertical(int vertical) {
        mType = TYPE.VERTICAL;
        mDivHeight = vertical;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        // 마지막이 아닌경우 아래 여백
//        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
//            if (mType == TYPE.VERTICAL) {
//                outRect.bottom = mDivHeight;
//            } else {
//                outRect.right = mDivHeight;
//            }
//        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        View topChild = parent.getChildAt(0);
        // 유효성 체크.
        if (topChild == null) {
            return;
        }
        // 포지션 유효성 체크.
        int topPos = parent.getChildAdapterPosition(topChild);
        if (topPos == RecyclerView.NO_POSITION) {
            return;
        }

        // 다시 맨위로 올렸을때 맨위 스티커 없애기 위한 조건문
        if (mStickyListener.isHeaderView(topPos) && topPos == 0) {
            return;
        }

        View currentHeader = null;
        // 스티커 포멧 형태의 뷰가 있는 경우.
        if (topChild.getTag() != null && topChild.getTag().toString().contains(mStickyFormat)) {
            currentHeader = getStickyView(topPos, topChild.getTag().toString(), parent);
        }
        // 스티커 포멧 형태의 뷰가 없는 경우
        else {
            currentHeader = getStickyView(topPos, parent);
        }

        fixLayoutSize(parent, currentHeader);
        int contactPoint = currentHeader.getBottom();
        View childInContact = getChildInContact(parent, contactPoint);

        if (childInContact == null) {
            return;
        }

        if (mStickyListener.isHeaderView(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, currentHeader, childInContact);
            return;
        }

        drawHeader(c, currentHeader);
    }

    /**
     * get 스티커 뷰 Default Type
     *
     * @param pos    ItemPosition
     * @param parent RecyclerView
     * @return View
     * @author hmju
     */
    private View getStickyView(int pos, RecyclerView parent) {
        // 해당 포지션에 맞게 스티커 Id 값 가져오기.
        int layoutResId = mStickyListener.getStickyResId(pos);
        // View 확장.
        View sticky = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        mStickyListener.bindSticky(pos, sticky);
        return sticky;
    }

    /**
     * get 스티커 뷰
     * └테그 값으로 뷰를 그리는 타입.
     *
     * @param pos    ItemPosition
     * @param tag    String
     * @param parent RecyclerView
     * @return View
     * @author hmju
     */
    private View getStickyView(int pos, String tag, RecyclerView parent) {
        // 해당 포지션에 맞게 스티커 Id 값 가져오기.
        int layoutResId = mStickyListener.getStickyResId(pos);
        // View 확장.
        View sticky = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        mStickyListener.bindSticky(pos, (Object) tag, sticky);
        return sticky;
    }

    private View getHeaderViewForItem(int itemPosition, RecyclerView parent) {
        int headerPosition = mListener.getHeaderPositionForItem(itemPosition);
        int layoutResId = mListener.getHeaderLayout(headerPosition);
        View header = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        mListener.bindHeaderData(header, headerPosition);
        return header;
    }

    private void drawHeader(Canvas c, View header) {
        c.save();
        c.translate(0, 0);
        header.draw(c);
        c.restore();
    }

    /**
     * Sticky Move 함수.
     *
     * @param c
     * @param currentHeader
     * @param nextHeader
     */
    private void moveHeader(Canvas c, View currentHeader, View nextHeader) {
        c.save();
        Logger.d("Value\t" + (nextHeader.getTop() - currentHeader.getHeight()));
        c.translate(0, nextHeader.getTop() - currentHeader.getHeight());
        currentHeader.draw(c);
        c.restore();
    }

    private View getChildInContact(RecyclerView parent, int contactPoint) {
        View childInContact = null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child.getBottom() > contactPoint) {
                if (child.getTop() <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child;
                    break;
                }
            }
        }
        return childInContact;
    }

    /**
     * Properly measures and layouts the top sticky header.
     *
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private void fixLayoutSize(ViewGroup parent, View view) {

        // Specs for parent (RecyclerView)
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // Specs for children (headers)
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);

        view.measure(childWidthSpec, childHeightSpec);

        view.layout(0, 0, view.getMeasuredWidth(), mStickyHeaderHeight = view.getMeasuredHeight());
    }

    public interface BaseStickyListener {
        // get Sticky View Layout Resource Id
        int getStickyResId(int pos);

        // bindData.. Default Type.
        void bindSticky(int pos, View view);

        // bindData.. Object Type.
        void bindSticky(int pos, Object obj, View view);

        boolean isHeaderView(int pos);
    }

    public interface StickyHeaderInterface {

        void setHeaderMap(HashMap<Integer, String> tmpMap);

        /**
         * This method gets called by {@link HeaderItemDecoration} to fetch the position of the header item in the adapter
         * that is used for (represents) item at specified position.
         *
         * @param itemPosition int. Adapter's position of the item for which to do the search of the position of the header item.
         * @return int. Position of the header item in the adapter.
         */
        int getHeaderPositionForItem(int itemPosition);

        /**
         * This method gets called by {@link HeaderItemDecoration} to get layout resource id for the header item at specified adapter's position.
         *
         * @param headerPosition int. Position of the header item in the adapter.
         * @return int. Layout resource id.
         */
        int getHeaderLayout(int headerPosition);

        /**
         * This method gets called by {@link HeaderItemDecoration} to setup the header View.
         *
         * @param header         View. Header to set the data on.
         * @param headerPosition int. Position of the header item in the adapter.
         */
        void bindHeaderData(View header, int headerPosition);

        /**
         * This method gets called by {@link HeaderItemDecoration} to verify whether the item represents a header.
         *
         * @param itemPosition int.
         * @return true, if item at the specified adapter's position represents a header.
         */
        boolean isHeader(int itemPosition);
    }
}
