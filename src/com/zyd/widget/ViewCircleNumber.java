package com.zyd.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class ViewCircleNumber extends TextView {

	private Paint mPaint;

	public ViewCircleNumber(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaint();
	}

	public ViewCircleNumber(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public ViewCircleNumber(Context context) {
		super(context);
		initPaint();
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFFFF0000);
		mPaint.setStyle(Paint.Style.FILL);
	}

	/**
	 * 如果高大于宽就将其设置为宽高相同
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int height = getMeasuredHeight();
		int width = getMeasuredWidth();
		if (height > width) {
			setMeasuredDimension(height, height);
		}
	}

	/**
	 * 在super.onDraw上一层绘制圆形或者圆弧矩形
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, mPaint);
		super.onDraw(canvas);
	}

}
