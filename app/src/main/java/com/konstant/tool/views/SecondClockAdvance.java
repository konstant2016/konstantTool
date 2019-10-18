package com.konstant.tool.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by konstant on 2017/6/14.
 */

public class SecondClockAdvance extends View {

    private Paint mPaint;               // 画笔

    private Calendar mCalendar;         // 系统时间

    private float mHour;                // 时
    private float mMinute;              // 分
    private float mSecond;              // 秒
    private float mMillisecond;         // 毫秒

    private float mHourAngle;           // 时针角度
    private float mMinuteAngle;         // 分针角度
    private float mSecondAngle;         // 秒针角度
    private float mMillisecondAngle;    // 毫秒角度

    private float mCenterX;               // 表盘中心点横向坐标点
    private float mCenterY;               // 表盘中心点纵向坐标点
    private float mRadius;                // 表盘半径

    public SecondClockAdvance(Context context) {
        this(context, null);
    }

    public SecondClockAdvance(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondClockAdvance(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SecondClockAdvance(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.UNSPECIFIED || heightMode != MeasureSpec.UNSPECIFIED) {
            int size = Math.min(widthSize, heightSize);
            mCenterX = mCenterY = size / 2;
        } else {
            mCenterX = mCenterY = 100f;          // 100像素
        }
        mRadius = mCenterX - 5;                 // 半径略小一些，防止绘制结果出现切边
        setMeasuredDimension((int) mCenterX * 2, (int) mCenterY * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制外圆
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);


        // 绘制刻度值
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(5);
                canvas.drawLine(mCenterX, 5, mCenterX, 40, mPaint);
            } else {
                mPaint.setStrokeWidth(3);
                canvas.drawLine(mCenterX, 5, mCenterX, 30, mPaint);
            }
            canvas.rotate(360 / 60, mCenterX, mCenterY);                // 共有60个刻度，每次旋转6°
        }

        // 绘制数字
        mPaint.setTextSize(35);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        float textSize = mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;
        float distance = mRadius - 40 - 35;
        float siteX, siteY;
        for (int i = 0; i < 12; i++) {
            siteX = (float) (distance * Math.sin(Math.PI * i * 30 / 180) + mCenterX);
            siteY = (float) (mCenterY - distance * Math.cos(Math.PI * i * 30 / 180));
            if (i == 0) {
                canvas.drawText("12", siteX, siteY + textSize / 2, mPaint);
            } else {
                canvas.drawText(String.valueOf(i), siteX, siteY + textSize / 2, mPaint);
            }
        }

        // 获取当前系统时间
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mSecond = mCalendar.get(Calendar.SECOND);
        mMillisecond = mCalendar.get(Calendar.MILLISECOND);
//        Log.d("time",mHour+"*"+mMinute+"*"+mSecond+"*"+mMillisecond);

        // 获取时分秒针的旋转角度
        mMillisecondAngle = mMillisecond / 1000 * 360;
        mSecondAngle = mSecond / 60 * 360 + mMillisecond / 1000 * 360 / 60;
        mMinuteAngle = mMinute / 60 * 360 + mSecond / 60 * 360 / 60;                                    // 分针所在分针刻度 + 相对秒针偏移量
        mHourAngle = mHour / 12 * 360 + mMinute / 60 * 360 / 12 + mSecond / 3600 * 360 / 12;            // 时针所在时针刻度 + 相对分针偏移量 + 相对秒针偏移量

        // 绘制时针
        mPaint.setStrokeWidth(13);
        canvas.save();                      //  因为涉及到canvas的旋转，为防止影响后面的绘制，需要先保存画布状态
        canvas.rotate(mHourAngle, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY - mRadius * 2 / 3, mCenterX, mCenterY, mPaint);
        canvas.restore();                   // 恢复保存的状态

        // 绘制分针
        mPaint.setStrokeWidth(9);
        canvas.save();
        canvas.rotate(mMinuteAngle, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY - mRadius * 3 / 4, mCenterX, mCenterY, mPaint);
        canvas.restore();

        // 绘制秒针
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.rotate(mSecondAngle, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY - mRadius * 4 / 5, mCenterX, mCenterY, mPaint);
        canvas.restore();

        // 绘制毫秒针
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.GREEN);
        canvas.save();
        canvas.rotate(mMillisecondAngle, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY - mRadius * 5 / 6, mCenterX, mCenterY, mPaint);
        canvas.restore();

        // 绘制内圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mCenterX, mCenterY, 20, mPaint);

        postInvalidateDelayed(1);
    }
}
