package com.zyd.widget;

import com.zyd.secbooks.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义底部导航栏
 * @author 90450
 *
 */
public class ViewBottomTab extends View {
	private Bitmap tab_icon; // tab图标
	private int tab_color = 0xff45C01A; // tab颜色
	private String tab_text; // tab内容
	private int tab_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
			getResources().getDisplayMetrics()); // tab内容的大小

	/**
	 * 在内存中绘制图标
	 */
	private Canvas iconCanvas; // 画布
	private Bitmap iconBitmap; // 图画
	private Paint iconPaint; // 画笔
	private Rect iconRect; // 画距

	/**
	 * 绘制文字
	 */
	private Paint textPaint;// 画笔
	private Rect textRect; // 画距

	private float alpha;// 通过不断修改透明度来实现动态效果

	private final int TEXT_COLOR_FROM_ICON = 0xff555555;

	private final int PADDING = getPaddingTop();

	public ViewBottomTab(Context context) {
		this(context, null);
	}

	public ViewBottomTab(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewBottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewBottomTab);
		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int index = typedArray.getIndex(i);
			switch (index) {
			case R.styleable.ViewBottomTab_tab_icon:
				BitmapDrawable bitmapDrawable = (BitmapDrawable) typedArray.getDrawable(index);
				tab_icon = bitmapDrawable.getBitmap();
				break;
			case R.styleable.ViewBottomTab_tab_color:
				tab_color = typedArray.getColor(index, 0xff45C01A);
				break;
			case R.styleable.ViewBottomTab_tab_text:
				tab_text = typedArray.getString(index);
				break;
			case R.styleable.ViewBottomTab_tab_text_size:
				tab_size = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
			}
		}

		/**
		 * 初始化文字
		 */
		textPaint = new Paint();
		textRect = new Rect();
		textPaint.setTextSize(tab_size);
		textPaint.setColor(TEXT_COLOR_FROM_ICON);
		textPaint.getTextBounds(tab_text, 0, tab_text.length(), textRect);
		/**
		 * 回收
		 */
		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 测量图标的宽度;分两种情况。 一：长高 二：扁平
		 */
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
				getMeasuredHeight() - PADDING * 3 - textRect.height());

		/**
		 * 居中显示,获取左上角的坐标
		 */
		int position_x = getMeasuredWidth() / 2 - iconWidth / 2;
		int position_y = getPaddingTop();

		iconRect = new Rect(position_x, position_y, position_x + iconWidth, position_y + iconWidth);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/**
		 * 绘制可变色图标过程： 1.绘制原图标，只需要Bitmap和画距。2.设置alpha.绘制一个可变色的图标
		 */
		canvas.drawBitmap(tab_icon, null, iconRect, null);
		int mAlpha = (int) Math.ceil(alpha * 255);
		setupIcon(mAlpha);
		canvas.drawBitmap(iconBitmap, 0, 0, null);

		drawSrcText(canvas, mAlpha);
		drawAlphaText(canvas, mAlpha);
	}

	/**
	 * 在内存中绘制根据alpha可变色的图标 注意，取Xformode取交集部分的前提是，图标必须是封闭的，透明的。
	 * 
	 * @param alpha
	 */
	private void setupIcon(int alpha) {
		/**
		 * 1.首先需要画布,但是画布构造参数不能为空,需要一个Bitmap,首先创建一个Bitmap
		 */
		iconBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		iconCanvas = new Canvas(iconBitmap);
		/**
		 * 2.接着需要一根画笔
		 */
		iconPaint = new Paint();
		iconPaint.setColor(tab_color); // 设置画笔的颜色
		iconPaint.setAntiAlias(true); // 设置抗锯齿
		iconPaint.setDither(true); // 设置防抖动
		iconPaint.setAlpha(alpha);// 设置透明度
		/**
		 * 3.利用画笔在画布上绘制
		 */
		iconCanvas.drawRect(iconRect, iconPaint);
		iconPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); // 混合模式，取交集部分

		iconPaint.setAlpha(255); // 将透明度设置回来

		iconCanvas.drawBitmap(tab_icon, null, iconRect, iconPaint);

	}

	/**
	 * public--设置透明度
	 * 
	 * @param alpha
	 */
	public void setIconAlpha(float alpha) {
		this.alpha = alpha;
		invalidateView();
	}

	/**
	 * 绘制原文本
	 */
	private void drawSrcText(Canvas canvas, int alpha) {
		textPaint.setColor(TEXT_COLOR_FROM_ICON);
		textPaint.setAlpha(255 - alpha);
		int positionX = getMeasuredWidth() / 2 - textRect.width() / 2;
		int positionY = iconRect.bottom + PADDING * 3;
		canvas.drawText(tab_text, positionX, positionY, textPaint);
	}

	/**
	 * 绘制可变色的文本
	 */
	private void drawAlphaText(Canvas canvas, int alpha) {
		textPaint.setColor(tab_color);
		textPaint.setAlpha(alpha);
		int positionX = getMeasuredWidth() / 2 - textRect.width() / 2;
		int positionY = iconRect.bottom + PADDING * 3;
		canvas.drawText(tab_text, positionX, positionY, textPaint);
	}

	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate(); // UI线程的话就重绘制
		} else {
			postInvalidate();// 非UI线程就postInvalidate
		}
	}
}
