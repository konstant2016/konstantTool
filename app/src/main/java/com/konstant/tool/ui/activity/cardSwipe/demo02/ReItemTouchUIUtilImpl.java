package com.konstant.tool.ui.activity.cardSwipe.demo02;

import android.graphics.Canvas;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.konstant.tool.R;


class ReItemTouchUIUtilImpl {
    static class Lollipop extends Honeycomb {
        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, View view,
                           float dX, float dY, float rotationY,int actionState, boolean isCurrentlyActive) {
            if (isCurrentlyActive) {
                Object originalElevation = view.getTag(R.id.item_touch_helper_previous_elevation);
                if (originalElevation == null) {
                    originalElevation = ViewCompat.getElevation(view);
                    float newElevation = 1f + findMaxElevation(recyclerView, view);
                    ViewCompat.setElevation(view, newElevation);
                    view.setTag(R.id.item_touch_helper_previous_elevation, originalElevation);
                }
            }
            super.onDraw(c, recyclerView, view, dX, dY,rotationY, actionState, isCurrentlyActive);
        }

        private float findMaxElevation(RecyclerView recyclerView, View itemView) {
            final int childCount = recyclerView.getChildCount();
            float max = 0;
            for (int i = 0; i < childCount; i++) {
                final View child = recyclerView.getChildAt(i);
                if (child == itemView) {
                    continue;
                }
                final float elevation = ViewCompat.getElevation(child);
                if (elevation > max) {
                    max = elevation;
                }
            }
            return max;
        }

        @Override
        public void clearView(View view) {
            final Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag != null && tag instanceof Float) {
                ViewCompat.setElevation(view, (Float) tag);
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            super.clearView(view);
        }
    }

    static class Honeycomb implements ReItemTouchUIUtil {

        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, View view,
                           float dX, float dY,float rotationY, int actionState, boolean isCurrentlyActive) {
            ViewCompat.setTranslationX(view, dX);
            ViewCompat.setTranslationY(view, dY);
            ViewCompat.setRotationY(view,rotationY);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView recyclerView, View view, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        }

        @Override
        public void clearView(View view) {
            ViewCompat.setTranslationX(view, 0f);
            ViewCompat.setTranslationY(view, 0f);
        }

        @Override
        public void onSelected(View view) {

        }



    }

    static class Gingerbread implements ReItemTouchUIUtil {

        private void draw(Canvas c, RecyclerView parent, View view,
                          float dX, float dY) {
            c.save();
            c.translate(dX, dY);
            parent.drawChild(c, view, 0);
            c.restore();
        }

        @Override
        public void clearView(View view) {
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSelected(View view) {
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView recyclerView, View view,
                           float dX, float dY,float rotationY, int actionState, boolean isCurrentlyActive) {
            if (actionState != ReItemTouchHelper.ACTION_STATE_DRAG) {
                draw(c, recyclerView, view, dX, dY);
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView recyclerView,
                               View view, float dX, float dY,
                               int actionState, boolean isCurrentlyActive) {
            if (actionState == ReItemTouchHelper.ACTION_STATE_DRAG) {
                draw(c, recyclerView, view, dX, dY);
            }
        }
    }
}