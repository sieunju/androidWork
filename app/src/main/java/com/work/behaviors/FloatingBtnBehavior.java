package com.work.behaviors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.work.utils.Logger;
import com.work.views.ToolBarView;

/**
 * Floating Button Behavior Class
 * Created by hmju on 2019-03-21.
 */
@SuppressWarnings("unused")
public class FloatingBtnBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private RecyclerView recyclerView;

    public FloatingBtnBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 특정 뷰들이 변화가 있을때 마다 동작 한다. -> true 반환 할것
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        Logger.d("TEST:: layoutDependsOn\t" + dependency.toString());
        return dependency instanceof SwipeRefreshLayout || dependency instanceof ToolBarView;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull LinearLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (target instanceof RecyclerView) {
            recyclerView = (RecyclerView) target;
            int offset = recyclerView.computeVerticalScrollOffset();
            setAction(child, offset);
        }
    }

    private void setAction(LinearLayout view, int offset) {
//        Logger.d("TEST:: offset\t" + offset);
        // 탑버튼 노출
        if (offset >= 800) {
            View topButton = view.getChildAt(0);
            topButton.setVisibility(View.VISIBLE);
        }
        // 탑버튼 미 노출
        else {
            View topButton = view.getChildAt(0);
            topButton.setVisibility(View.GONE);
        }
    }
}
