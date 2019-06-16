package com.work.utils;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 비대칭 GridLayoutManager
 * Created by hmju on 2019-04-27.
 */
public class AsymmetricGridLayoutManager extends RecyclerView.LayoutManager {

    /**
     * 랜덤 레이아웃에 필요한 인터페이스
     *
     * @author hmju
     */
    public interface GridSpanLookUp {

    }

    public static class SpanInfo {
        public int columnSpan;
        public int rowSpan;

        public static SpanInfo create(int column,int row){
            return new SpanInfo(column,row);
        }

        public SpanInfo(int column,int row){
            columnSpan = column;
            rowSpan = row;
        }

        public int getColumn(){
            return columnSpan;
        }

        public int getRow(){
            return rowSpan;
        }
    }

    private GridSpanLookUp mListener;
    private int mMaxColumns;

    public AsymmetricGridLayoutManager(GridSpanLookUp listener, int maxColumns) {
        mListener = listener;
        mMaxColumns = maxColumns;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Logger.d("TEST:: onLayoutChildren ");
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Logger.d("TEST:: scrollVerticallyBy " + dy);
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    private void calculateCellPositions(RecyclerView.Recycler recycler,RecyclerView.State state){
        final int itemCount = state.getItemCount();


    }
}
