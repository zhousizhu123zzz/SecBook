package com.zyd.widget;

import com.zyd.secbooks.R;
import com.zyd.utils.EqupUtil;

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
import android.view.View;

/**
 * 自定义图片轮播器的小圆圈
 * @author 90450
 *
 */
public class ViewBannerCircle extends View {

	private int index;// 需要填充颜色的circle的索引

	private int circle_color = 0xff0000FF; // xml参数
	private Bitmap circle_pic; // xml参数

	private float alpha;// 透明度

	private int circleCount;// 小圆圈的数量,可以从banner传来
	private Integer[] circle_position;

	public ViewBannerCircle(Context context) {
		this(context, null);
	}

	public ViewBannerCircle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 初始化BannerCircleView从xml文件中传来的参数
	 */
	public ViewBannerCircle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ViewBannerCircle);
		for (int i = 0; i < typeArray.getIndexCount(); i++) {
			int index = typeArray.getIndex(i);
			switch (index) {
			case R.styleable.ViewBannerCircle_banner_circle_color:
				circle_color = typeArray.getColor(index, 0xff0000FF);
				break;
			case R.styleable.ViewBannerCircle_banner_circle_icon:
				BitmapDrawable bitmapDrawable = (BitmapDrawable) typeArray.getDrawable(index);
				circle_pic = bitmapDrawable.getBitmap();
				circle_pic = EqupUtil.resizeBitmap(circle_pic, 15, 15);//设置小圆圈图标的大小
				break;
			}
		}
		typeArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setCirclePosition(getPaddingLeft());
		/* 设置view的wrap_content */
		setMeasuredDimension(circle_pic.getWidth() * circleCount + getPaddingLeft() * circleCount * 2,
				getPaddingTop() * 2 + circle_pic.getHeight());
	}

	/**
	 * 绘制小圆圈
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/* 循环绘制各个可变色的小圆圈 */
		for (int i = 0; i < circleCount; i++) {

			/* 设置各个小圆圈的坐标位置 */
			Rect mRect = new Rect(circle_position[i], getPaddingTop(), circle_position[i] + circle_pic.getWidth(),
					getPaddingTop() + circle_pic.getHeight());
			canvas.drawBitmap(circle_pic, null, mRect, null);// 绘制没有变色的小圆圈

			Bitmap mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
			Canvas mCanvas = new Canvas(mBitmap);
			Paint mPaint = new Paint();
			if (i == index) {
				alpha = 1f;
			} else {
				alpha = 0;
			}
			alpha = (float) Math.ceil(alpha * 255);
			mPaint.setColor(circle_color);// 设置小圆圈的填充色
			mPaint.setDither(true);// 防抖动
			mPaint.setAntiAlias(true);// 抗锯齿
			mPaint.setAlpha((int) alpha);// 设置填充色的透明度
			mCanvas.drawRect(mRect, mPaint);// 绘制一个矩形,用于和小圆圈混合显示
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));// 混合模式
			mPaint.setAlpha(255);// 给小圆圈源图标设回透明度
			mCanvas.drawBitmap(circle_pic, null, mRect, mPaint);// 绘制根据透明度可变色的小圆圈

			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
	}

	/**
	 * 根据第一个小圆圈的横坐标位置(和小圆圈图标的宽度和padding)来分别设置所有小圆圈的横坐标 纵坐标不用设置,因为是横向排列,纵坐标相同
	 * 
	 * @param firstCirclePositionX
	 */
	private void setCirclePosition(int firstCirclePositionX) {
		circle_position = new Integer[circleCount];
		int width = 0;
		for (int i = 0; i < circleCount; i++) {
			circle_position[i] = firstCirclePositionX + width;
			width += circle_pic.getWidth() + getPaddingLeft() * 2;
		}
	}

	/**
	 * banner滑动时动态给第index的小圆圈设置填充色
	 * 
	 * @param index
	 */
	public void setSelectedCircle(int index) {
		this.index = index;
		invalidateCircle();
	}

	/**
	 * UI线程invalidate,非UI线程postInvalidate
	 */
	private void invalidateCircle() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	/**
	 * 设置小圆圈的数量
	 * @param circleCounts
	 */
	public void setCircleCount(int circleCounts) {
		this.circleCount = circleCounts;
	}
}
