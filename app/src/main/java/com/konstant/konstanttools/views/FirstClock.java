package com.konstant.konstanttools.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.konstant.konstanttools.R;

import java.util.Calendar;

/**
 * Created by konstant on 2017/6/13.
 */

public class FirstClock extends View {

    private Calendar mCalendar;     // 系统时间

    private Drawable mHourHand;     // 时针
    private Drawable mMinuteHand;   // 分针
    private Drawable mDial;         // 表盘

    private int mDialWidth;         // 表盘宽度
    private int mDialHeight;        // 表盘高度

    private boolean isAttached;     // 是否在window中显示

    private float mHour;            // 当前小时
    private float mMinutes;         // 当前分钟

    private boolean isChanged;      // 属性是否发生变化

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_TIME_CHANGED.equals(intent.getAction())){
                // 这里面监听到的是：时区发生变化
            }
            onTimeChanged();
            invalidate();
            Log.d("aaaa","*************");
        }
    };


    public FirstClock(Context context) {
        this(context, null);
    }

    public FirstClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public FirstClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr , 0);
    }

    public FirstClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (mDial == null) {
            mDial = ContextCompat.getDrawable(context, R.drawable.clock_dial);
        }
        if(mHourHand == null){
            mHourHand = ContextCompat.getDrawable(context,R.drawable.clock_hand_hour);
        }
        if(mMinuteHand == null){
            mMinuteHand = ContextCompat.getDrawable(context,R.drawable.clock_hand_minute);
        }
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR);
        mMinutes = mCalendar.get(Calendar.MINUTE);

        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 等比缩放，防止变形
        float widthScale = 1.0f;
        float heightScale = 1.0f;

        if(widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth){
            widthScale = (float) widthSize / (float) mDialWidth;
        }
        if(heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialWidth){
            heightScale = (float) heightSize / (float) mDialHeight;
        }
        float scale = Math.min(widthScale , heightScale);

        mDialWidth = (int) (mDialWidth * scale);
        mDialHeight = (int) (mDialHeight * scale);

        setMeasuredDimension(mDialWidth , mDialHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isChanged = true;
    }

    private void onTimeChanged(){
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR);
        mMinutes = mCalendar.get(Calendar.MINUTE);
        isChanged = true;
        Log.d("time",mHour+"**"+mMinutes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!isAttached){
            isAttached = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            getContext().registerReceiver(receiver,filter);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(isAttached){
            getContext().unregisterReceiver(receiver);
            isAttached = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean changed = isChanged;
        if(changed){
            isChanged = false;
        }

        // 获取父控件给分配的可见宽高
        int availableWidth = super.getRight() - super.getLeft();
        int availableHeight = super.getBottom() - super.getTop();

        // 确定绘制图像的中心点
        int x = availableWidth / 2;
        int y = availableHeight / 2;

        // 获取表盘，之所以不直接引用全局是因为要为此表盘动态设置边界
        Drawable dial = mDial;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();

        // 设置缩放标识符，用于下面做判断时使用
        boolean scaled = false;

        // 全局缩放
        if(availableWidth < w || availableHeight < h){
            scaled = true;
            float scaleW = (float) availableWidth / w;        // 宽度方向上的缩放比例
            float scaleH = (float)availableHeight / h;        // 高度方向的缩放比例
            float scale = Math.min(scaleW, scaleH);           // 选取宽高上的最小缩放值，保证缩放时不会造成拉伸
            canvas.save();                                    // 将之前绘制的图形保存下来
            canvas.scale(scale , scale , x , y );             // 告诉画布，执行全局缩放（宽度缩放比例，高度缩放比例，宽度中心点，高度中心点）
        }
        if(changed){
            dial.setBounds(x - w/2 , y - h/2 , x + w/2 , y + h/2);  // 给表盘设置边界
        }
        dial.draw(canvas);
        canvas.save();

        canvas.rotate( mHour / 12f * 360f + mMinutes / 60 * 360 / 12 , x, y);  // 根据时钟的时间，旋转画布，用以下面绘制时针

        // 获取表盘，之所以不直接引用全局是因为要为此表盘动态设置边界
        Drawable hourHand = mHourHand;
        if(changed){
            w = hourHand.getIntrinsicWidth();
            h = hourHand.getIntrinsicHeight();
            hourHand.setBounds(x - w/2 , y - h/2 , x + w/2 , y + h/2);  // 给时针设置边界
        }
        hourHand.draw(canvas);                                  // 绘制时针
        canvas.restore();                                       // 重置画布，用以绘制分针
        canvas.save();                                          // 将之前的绘制结果保存

        canvas.rotate( mMinutes / 60f * 360f , x , y );         // 画布旋转到当前分针对应的角度
        Drawable minuteHand = mMinuteHand;
        if(changed){
            w = minuteHand.getIntrinsicWidth();
            h = minuteHand.getIntrinsicHeight();
            minuteHand.setBounds(x - w/2 , y - h/2 , x + w/2 , y + h/2);
        }
        minuteHand.draw(canvas);
        canvas.restore();
    }
}
