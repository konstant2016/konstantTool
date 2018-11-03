package com.konstant.tool.lite.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.konstant.tool.lite.R;

public class KonstantRulerView extends SurfaceView implements SurfaceHolder.Callback {
    public float UNIT_MM;
	public float RULE_HEIGHT;
	public float RULE_SCALE;
	public int SCREEN_W;
	public int SCREEN_H;
	public float FONT_SIZE;
	public float PADDING;
	public float RADIUS_BIG;
	public float RADIUS_MEDIUM;
	public float RADIUS_SMALL;
	public float CYCLE_WIDTH;
	public float DISPLAY_SIZE_BIG;
	public float DISPLAY_SIZE_SMALL;

	private SurfaceHolder holder;
	boolean unlockLineCanvas = false;
	float lineX;
	float lineOffset;
	float startX;
	float lastX;
	int kedu;
	Paint paint;
	Paint linePaint;
	Paint fontPaint;

	private int mColor;

	public int getKedu() {
		return kedu;
	}

	public void setKedu(int kedu) {
		this.kedu = kedu;
		draw();
	}

	public float getLineX() {
		return lineX;
	}

	public void setLineX(float lineX) {
		this.lineX = lineX;
		draw();
	}

	public KonstantRulerView(Context context) {
		this(context,null);
	}

	public KonstantRulerView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public KonstantRulerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KonstantRulerView);
		mColor = array.getInt(R.styleable.KonstantRulerView_color_line, Color.BLUE);
		array.recycle();
		init(context);
	}

	private void init(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		RADIUS_BIG = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46,
				dm);
		RADIUS_MEDIUM = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				40, dm);
		RADIUS_SMALL = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				20, dm);
		CYCLE_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
				dm);
		DISPLAY_SIZE_BIG = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 40, dm);
		DISPLAY_SIZE_SMALL = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 20, dm);
		UNIT_MM = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1, dm);
		RULE_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				30, dm);
		FONT_SIZE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
				dm);
		PADDING = FONT_SIZE / 2;
		SCREEN_W = dm.widthPixels;
		SCREEN_H = dm.heightPixels;
		holder = getHolder();
		holder.addCallback(this);
		paint = new Paint();
		paint.setColor(mColor);
		linePaint = new Paint();
		linePaint.setColor(mColor);
		linePaint.setStrokeWidth(4);
		fontPaint = new Paint();
		fontPaint.setTextSize(FONT_SIZE);
		fontPaint.setAntiAlias(true);
		fontPaint.setColor(mColor);
		lineX = PADDING;
		kedu = 0;
	}


	// 画刻度
	private void draw() {
		Canvas canvas = null;
		try {
			canvas = holder.lockCanvas();
			canvas.drawColor(0xffffffff);
			float left = PADDING;
			for (int i = 0; SCREEN_W - PADDING - left > 0; i++) {
				RULE_SCALE = 0.5f;
				if (i % 5 == 0) {
					if ((i & 0x1) == 0) {
						// Å¼Êý
						RULE_SCALE = 1f;
						String txt = String.valueOf(i / 10);
						Rect bounds = new Rect();
						float txtWidth = fontPaint.measureText(txt);
						fontPaint.getTextBounds(txt, 0, txt.length(), bounds);
						canvas.drawText(txt, left - txtWidth / 2, RULE_HEIGHT
								+ FONT_SIZE / 2 + bounds.height(), fontPaint);
					} else {
						// ÆæÊý
						RULE_SCALE = 0.75f;
					}
				}
				RectF rect = new RectF();
				rect.left = left - 1;
				rect.top = 0;
				rect.right = left + 1;
				rect.bottom = rect.top + RULE_HEIGHT * RULE_SCALE;
				canvas.drawRect(rect, paint);
				left += UNIT_MM;
			}
			lastX = left - UNIT_MM;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		new Thread() {
			public void run() {
				draw();
			};
		}.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

}