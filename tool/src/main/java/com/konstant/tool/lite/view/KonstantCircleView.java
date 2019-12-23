package com.konstant.tool.lite.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.konstant.tool.lite.R;

public class KonstantCircleView extends View {

    private int mWidth, mHeight, mLineWidth;
    private Paint mUpperPaint, mBackgroundPaint;
    private int mTotal, mCurrent;

    public KonstantCircleView(Context context) {
        this(context, null);
    }

    public KonstantCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KonstantCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        initViews(context, attrs, defStyleAttr);
    }

    private void initViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        int upperColor = typedArray.getColor(R.styleable.CircleView_upperColor, Color.RED);
        int backgroundColor = typedArray.getColor(R.styleable.CircleView_backgroundColor, Color.GRAY);
        mLineWidth = (int) typedArray.getDimension(R.styleable.CircleView_lineWidth, 10);
        mCurrent = typedArray.getInt(R.styleable.CircleView_current, 0);
        mTotal = typedArray.getInt(R.styleable.CircleView_total, 100);
        typedArray.recycle();
        mUpperPaint = new Paint();
        mUpperPaint.setAntiAlias(true);
        mUpperPaint.setStyle(Paint.Style.STROKE);
        mUpperPaint.setStrokeWidth(mLineWidth);
        mUpperPaint.setColor(upperColor);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(mLineWidth);
        mBackgroundPaint.setColor(backgroundColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.UNSPECIFIED) {
            mWidth = widthSize;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED) {
            mHeight = heightSize;
        }
        mWidth = mHeight = Math.min(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 系统默认是顺时针画圆，-90表示顶部开始画
     * 剩下的角度，用逆时针把360-sweepAngle得到的结果直接绘制即可
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = (float) mCurrent / mTotal * 360;
        // 先画上层圆
        canvas.drawArc(mLineWidth / 2, mLineWidth / 2, mWidth - mLineWidth / 2, mHeight - mLineWidth / 2, -90, sweepAngle, false, mUpperPaint);
        // 再画下层圆
        canvas.drawArc(mLineWidth / 2, mLineWidth / 2, mWidth - mLineWidth / 2, mHeight - mLineWidth / 2, -90, sweepAngle - 360, false, mBackgroundPaint);
    }

    public void setTotal(int total) {
        mTotal = total;
        invalidate();
    }

    public void setCurrent(int current) {
        mCurrent = current;
        invalidate();
    }
}
