package com.konstant.tool.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.konstant.tool.R;


/**
 * 时间：2019/1/2 16:50
 * 创建：吕卡
 * 描述：加载动画
 */

public class ArcView extends View {

    private int mWidth = 300, mHeight = 300;
    private float mStart, mOuterSweep = 270, mOuterWidth, mInnerWidth, mInnerSweep;
    private RectF mOuterRectF;
    private Paint mOuterPaint;

    private Paint mInnerPaint;
    private RectF mInnerRectF;

    private float mPadding;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AcrView);
        mPadding = array.getDimension(R.styleable.AcrView_arc_padding, 20);
        initOuterArc(array);
        initInnerArc(array);
        array.recycle();

    }

    private void initOuterArc(TypedArray array) {
        mOuterRectF = new RectF();
        mOuterPaint = new Paint();
        mOuterSweep = array.getInt(R.styleable.AcrView_outer_sweep, 270);
        mStart = array.getInt(R.styleable.AcrView_start, 0);
        int color = array.getColor(R.styleable.AcrView_outer_color, Color.RED);
        mOuterWidth = array.getDimension(R.styleable.AcrView_outer_width, 30);
        mOuterPaint.setColor(color);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mOuterWidth);
    }

    private void initInnerArc(TypedArray array) {
        mInnerRectF = new RectF();
        mInnerPaint = new Paint();
        mInnerSweep = array.getInt(R.styleable.AcrView_inner_sweep, 270);
        int color = array.getColor(R.styleable.AcrView_inner_color, Color.RED);
        mInnerWidth = array.getDimension(R.styleable.AcrView_inner_width, 30);
        mInnerPaint.setColor(color);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mInnerWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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

        mOuterRectF.left = mOuterWidth / 2;
        mOuterRectF.top = mOuterWidth / 2;
        mOuterRectF.right = mWidth - mOuterWidth / 2;
        mOuterRectF.bottom = mHeight - mOuterWidth / 2;

        mInnerRectF.left = mOuterRectF.left + mPadding;
        mInnerRectF.top = mOuterRectF.top + mPadding;
        mInnerRectF.right = mOuterRectF.right - mPadding;
        mInnerRectF.bottom = mOuterRectF.bottom - mPadding;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mOuterRectF, mStart, mOuterSweep, false, mOuterPaint);
        canvas.drawArc(mInnerRectF, 90 - mStart, mInnerSweep, false, mInnerPaint);
        mStart += 10;
        postInvalidateDelayed(1);
    }

}
