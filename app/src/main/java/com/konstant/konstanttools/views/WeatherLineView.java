package com.konstant.konstanttools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.konstant.konstanttools.R;


public class WeatherLineView extends View {

    /**
     * 默认最小宽度50dp
     */
    private static final int defaultMinWidth = 100;

    /**
     * 默认最小高度80dp
     */
    private static final int defaultMinHeight = 80;

    /**
     * 字体最小默认16dp
     */
    private int mTemperTextSize = 16;

    /**
     * 文字颜色
     */
    private int mWeaTextColor = Color.BLACK;

    /**
     * 线的宽度
     */
    private int mWeaLineWidth = 1;

    /**
     * 圆点的宽度
     */
    private int mWeaDotRadius = 5;

    /**
     * 文字和点的间距
     */
    private int mTextDotDistance = 5;

    /**
     * 画文字的画笔
     */
    private TextPaint mTextPaint;

    /**
     * 文字的FontMetrics
     */
    private Paint.FontMetrics mTextFontMetrics;

    /**
     * 画点的画笔
     */
    private Paint mDotPaint;

    /**
     * 画线的画笔
     */
    private Paint mLinePaint;

    /**
     * 15天最低温度的数据
     */
    private int mLowestTemperData;

    /**
     * 15天最高温度的数据
     */
    private int mHighestTemperData;

    /**
     * 分别代表最左边的，中间的，右边的三个当天最低温度值
     */
    private int mLowTemperData[];

    private int mHighTemperData[];

    public WeatherLineView(Context context) {
        this(context, null);
    }

    public WeatherLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 设置当天的三个低温度数据，中间的数据就是当天的最低温度数据，
     * 第一个数据是当天和前天的数据加起来的平均数，
     * 第二个数据是当天和明天的数据加起来的平均数
     *
     * @param low  最低温度
     * @param high 最高温度
     */
    public void setLowHighData(int low[], int high[]) {
        mLowTemperData = low;
        mHighTemperData = high;
        invalidate();
    }

    /**
     * 设置15天里面的最低和最高的温度数据
     *
     * @param low  最低温度
     * @param high 最高温度
     */
    public void setLowHighestData(int low, int high) {
        mLowestTemperData = low;
        mHighestTemperData = high;
        invalidate();
    }

    /**
     * 设置画笔信息
     */
    private void initPaint() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTemperTextSize);
        mTextPaint.setColor(mWeaTextColor);
        mTextFontMetrics = mTextPaint.getFontMetrics();

        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setColor(mWeaTextColor);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mWeaLineWidth);
        mLinePaint.setColor(mWeaTextColor);
    }

    /**
     * 获取自定义属性并赋初始值
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherLineView,
                defStyleAttr, 0);
        mTemperTextSize = (int) a.getDimension(R.styleable.WeatherLineView_temperTextSize,
                dp2px(context, mTemperTextSize));
        mWeaTextColor = a.getColor(R.styleable.WeatherLineView_weatextColor, Color.parseColor("#b07b5c"));
        mWeaLineWidth = (int) a.getDimension(R.styleable.WeatherLineView_weaLineWidth,
                dp2px(context, mWeaLineWidth));
        mWeaDotRadius = (int) a.getDimension(R.styleable.WeatherLineView_weadotRadius,
                dp2px(context, mWeaDotRadius));
        mTextDotDistance = (int) a.getDimension(R.styleable.WeatherLineView_textDotDistance,
                dp2px(context, mTextDotDistance));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = getSize(widthMode, widthSize, 0);
        int height = getSize(heightMode, heightSize, 1);
        setMeasuredDimension(width, height);
    }

    /**
     * @param mode Mode
     * @param size Size
     * @param type 0表示宽度，1表示高度
     * @return 宽度或者高度
     */
    private int getSize(int mode, int size, int type) {
        // 默认
        int result;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            if (type == 0) {
                // 最小不能低于最小的宽度
                result = dp2px(getContext(), defaultMinWidth) + getPaddingLeft() + getPaddingRight();
            } else {
                // 最小不能小于最小的宽度加上一些数据
                int textHeight = (int) (mTextFontMetrics.bottom - mTextFontMetrics.top);
                // 加上2个文字的高度
                result = dp2px(getContext(), defaultMinHeight) + 2 * textHeight +
                        // 需要加上两个文字和圆点的间距
                        getPaddingTop() + getPaddingBottom() + 2 * mTextDotDistance;
            }

            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mLowTemperData == null || mHighTemperData == null
                || mLowestTemperData == 0 || mHighestTemperData == 0) {
            return;
        }

        canvas.drawColor(Color.YELLOW);

        // 文本的高度
        int textHeight = (int) (mTextFontMetrics.bottom - mTextFontMetrics.top);

        // 一个基本的高度，由于最下面的时候，有文字和圆点和文字的宽度需要留空间
        int baseHeight = getHeight() - textHeight - mTextDotDistance;

        // 最低温度相关
        // 最低温度中间
        int calowMiddle = baseHeight - cacHeight(mLowTemperData[1]);
        canvas.drawCircle(getWidth() / 2, calowMiddle, mWeaDotRadius, mDotPaint);

        // 画温度文字
        String text = String.valueOf(mLowTemperData[1]) + "°";
        int baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(text) / 2);
        // mTextFontMetrics.top为负的
        // 需要加上文字高度和文字与圆点之间的空隙
        int baseY = (int) (calowMiddle - mTextFontMetrics.top) + mTextDotDistance;
        canvas.drawText(text, baseX, baseY, mTextPaint);

        if (mLowTemperData[0] != 0) {
            // 最低温度左边
            int calowLeft = baseHeight - cacHeight(mLowTemperData[0]);
            canvas.drawLine(0, calowLeft, getWidth() / 2, calowMiddle, mLinePaint);
        }

        if (mLowTemperData[2] != 0) {
            // 最低温度右边
            int calowRight = baseHeight - cacHeight(mLowTemperData[2]);
            canvas.drawLine(getWidth() / 2, calowMiddle, getWidth(), calowRight, mLinePaint);
        }

        // 最高温度相关
        // 最高温度中间
        int calHighMiddle = baseHeight - cacHeight(mHighTemperData[1]);
        canvas.drawCircle(getWidth() / 2, calHighMiddle, mWeaDotRadius, mDotPaint);

        // 画温度文字
        String text2 = String.valueOf(mHighTemperData[1]) + "°";
        int baseX2 = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(text2) / 2);
        int baseY2 = (int) (calHighMiddle - mTextFontMetrics.bottom) - mTextDotDistance;
        canvas.drawText(text2, baseX2, baseY2, mTextPaint);

        if (mHighTemperData[0] != 0) {
            // 最高温度左边
            int calHighLeft = baseHeight - cacHeight(mHighTemperData[0]);
            canvas.drawLine(0, calHighLeft, getWidth() / 2, calHighMiddle, mLinePaint);
        }

        if (mHighTemperData[2] != 0) {
            // 最高温度右边
            int calHighRight = baseHeight - cacHeight(mHighTemperData[2]);
            canvas.drawLine(getWidth() / 2, calHighMiddle, getWidth(), calHighRight, mLinePaint);
        }
    }

    private int cacHeight(int tem) {
        // 最低，最高温度之差
        int temDistance = mHighestTemperData - mLowestTemperData;
        int textHeight = (int) (mTextFontMetrics.bottom - mTextFontMetrics.top);
        // view的最高和最低之差，需要减去文字高度和文字与圆点之间的空隙
        int viewDistance = getHeight() - 2 * textHeight - 2 * mTextDotDistance;
        // 今天的温度和最低温度之间的差别
        int currTemDistance = tem - mLowestTemperData;
        return currTemDistance * viewDistance / temDistance;
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}