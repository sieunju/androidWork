package com.work.behaviors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.work.utils.Logger;
import com.work.views.FloatingView;

/**
 * Created by hmju on 2019-03-21.
 */
@SuppressWarnings("unused")
public class ToolBarBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {

    public ToolBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 특정 뷰들이 변화가 있을때 마다 동작 한다. -> true 반환 할것
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RelativeLayout child, @NonNull View dependency) {
        Logger.d("TEST:: layoutDependsOn " + dependency);
        return dependency instanceof SwipeRefreshLayout || dependency instanceof FloatingView;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RelativeLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RelativeLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Logger.d("TEST:: onNestedPreScroll " + target.toString());
    }
}
