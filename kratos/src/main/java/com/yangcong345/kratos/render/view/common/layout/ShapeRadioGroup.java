package com.yangcong345.kratos.render.view.common.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.yangcong345.kratos.render.view.common.builder.ShapeDrawableBuilder;
import com.yangcong345.kratos.render.view.common.styleable.ShapeRadioGroupStyleable;
import com.yangcong345.kratos.R;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/ShapeView
 *    time   : 2021/09/07
 *    desc   : 支持直接定义 Shape 背景的 RadioGroup
 */
public class ShapeRadioGroup extends RadioGroup {

    private static final ShapeRadioGroupStyleable STYLEABLE = new ShapeRadioGroupStyleable();

    private final ShapeDrawableBuilder mShapeDrawableBuilder;

    public ShapeRadioGroup(Context context) {
        this(context, null);
    }

    public ShapeRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeRadioGroup);
        mShapeDrawableBuilder = new ShapeDrawableBuilder(this, typedArray, STYLEABLE);
        typedArray.recycle();

        mShapeDrawableBuilder.intoBackground();
    }

    public ShapeDrawableBuilder getShapeDrawableBuilder() {
        return mShapeDrawableBuilder;
    }
}