package com.konstant.tool.ui.activity.cardSwipe.demo02;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public interface ReItemTouchUIUtil {

     
    void onDraw(Canvas c, RecyclerView recyclerView, View view,
                float dX, float dY,float rotationY, int actionState, boolean isCurrentlyActive);

     
    void onDrawOver(Canvas c, RecyclerView recyclerView, View view,
                    float dX, float dY, int actionState, boolean isCurrentlyActive);

     
    void clearView(View view);

     
    void onSelected(View view);
}
