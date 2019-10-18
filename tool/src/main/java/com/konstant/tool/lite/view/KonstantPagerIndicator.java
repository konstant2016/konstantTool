
package com.konstant.tool.lite.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.konstant.tool.lite.R;

/**
 * An ink inspired widget for indicating pages in a {@link ViewPager}.
 */
public class KonstantPagerIndicator extends View implements ViewPager.OnPageChangeListener {

    // defaults
    private static final int DEFAULT_DOT_SIZE = 8;                      // dp
    private static final int DEFAULT_GAP = 12;                          // dp
    private static final int DEFAULT_UNSELECTED_COLOUR = 0x80ffffff;    // 50% white
    private static final int DEFAULT_SELECTED_COLOUR = 0xffffffff;      // 100% white


    // configurable attributes
    private int dotDiameter;
    private int gap;
    private int unselectedColour;
    private int selectedColour;

    // derived from attributes
    private float dotRadius;
    private float dotCenterY;

    // ViewPager
    private ViewPager viewPager;

    // state
    private int pageCount;
    private int currentPage;
    private float selectedDotX;
    private float[] dotCenterXList;

    // drawing
    private final Paint unselectedPaint;
    private final Paint selectedPaint;


    public KonstantPagerIndicator(Context context) {
        this(context, null, 0);
    }

    public KonstantPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KonstantPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final int density = (int) context.getResources().getDisplayMetrics().density;

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.KonstantPageIndicator, defStyle, 0);

        dotDiameter = a.getDimensionPixelSize(R.styleable.KonstantPageIndicator_dotDiameter, DEFAULT_DOT_SIZE * density);
        dotRadius = dotDiameter / 2;
        gap = a.getDimensionPixelSize(R.styleable.KonstantPageIndicator_dotGap, DEFAULT_GAP * density);
        unselectedColour = a.getColor(R.styleable.KonstantPageIndicator_pageIndicatorColor, DEFAULT_UNSELECTED_COLOUR);
        selectedColour = a.getColor(R.styleable.KonstantPageIndicator_currentPageIndicatorColor, DEFAULT_SELECTED_COLOUR);

        a.recycle();

        unselectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unselectedPaint.setColor(unselectedColour);
        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(selectedColour);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        setPageCount(viewPager.getAdapter().getCount());
        viewPager.getAdapter().registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                setPageCount(KonstantPagerIndicator.this.viewPager.getAdapter().getCount());
            }
        });
        setCurrentPageImmediate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        setCurrentPageImmediate();
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setPageCount(int pages) {
        pageCount = pages;
        requestLayout();
    }

    // 计算出所有点的位置坐标
    private void calculateDotPositions(int width) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = width - getPaddingRight();

        int requiredWidth = getRequiredWidth();
        float startLeft = left + ((right - left - requiredWidth) / 2) + dotRadius;

        dotCenterXList = new float[pageCount];
        for (int i = 0; i < pageCount; i++) {
            dotCenterXList[i] = startLeft + i * (dotDiameter + gap);
        }

        dotCenterY = top + dotRadius;

        setCurrentPageImmediate();
    }

    // 设置当前选中点的坐标
    private void setCurrentPageImmediate() {
        if (viewPager != null) {
            currentPage = viewPager.getCurrentItem();
        } else {
            currentPage = 0;
        }
        if (dotCenterXList != null && dotCenterXList.length > 0) {
            selectedDotX = dotCenterXList[currentPage];
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredHeight = getDesiredHeight();
        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(desiredHeight, MeasureSpec.getSize(heightMeasureSpec));
                break;
            default: // MeasureSpec.UNSPECIFIED
                height = desiredHeight;
                break;
        }

        int desiredWidth = getDesiredWidth();
        int width;
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(desiredWidth, MeasureSpec.getSize(widthMeasureSpec));
                break;
            default: // MeasureSpec.UNSPECIFIED
                width = desiredWidth;
                break;
        }
        setMeasuredDimension(width, height);
        calculateDotPositions(width);
    }

    private int getDesiredHeight() {
        return getPaddingTop() + dotDiameter + getPaddingBottom();
    }

    private int getRequiredWidth() {
        return pageCount * dotDiameter + (pageCount - 1) * gap;
    }

    private int getDesiredWidth() {
        return getPaddingLeft() + getRequiredWidth() + getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (viewPager == null || pageCount == 0) return;
        drawALLDot(canvas);
    }

    // 绘制所有的点，如果位置是选中的，则绘制选中点的颜色，否则，绘制未选中点的颜色
    private void drawALLDot(Canvas canvas) {
        for (int i = 0; i < pageCount; i++) {
            if (i == currentPage) {
                canvas.drawCircle(selectedDotX, dotCenterY, dotRadius, selectedPaint);
            } else {
                canvas.drawCircle(dotCenterXList[i], dotCenterY, dotRadius, unselectedPaint);
            }
        }
    }
}
