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
 * �Զ���ͼƬ�ֲ�����СԲȦ
 * @author 90450
 *
 */
public class ViewBannerCircle extends View {

	private int index;// ��Ҫ�����ɫ��circle������

	private int circle_color = 0xff0000FF; // xml����
	private Bitmap circle_pic; // xml����

	private float alpha;// ͸����

	private int circleCount;// СԲȦ������,���Դ�banner����
	private Integer[] circle_position;

	public ViewBannerCircle(Context context) {
		this(context, null);
	}

	public ViewBannerCircle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * ��ʼ��BannerCircleView��xml�ļ��д����Ĳ���
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
				circle_pic = EqupUtil.resizeBitmap(circle_pic, 15, 15);//����СԲȦͼ��Ĵ�С
				break;
			}
		}
		typeArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setCirclePosition(getPaddingLeft());
		/* ����view��wrap_content */
		setMeasuredDimension(circle_pic.getWidth() * circleCount + getPaddingLeft() * circleCount * 2,
				getPaddingTop() * 2 + circle_pic.getHeight());
	}

	/**
	 * ����СԲȦ
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/* ѭ�����Ƹ����ɱ�ɫ��СԲȦ */
		for (int i = 0; i < circleCount; i++) {

			/* ���ø���СԲȦ������λ�� */
			Rect mRect = new Rect(circle_position[i], getPaddingTop(), circle_position[i] + circle_pic.getWidth(),
					getPaddingTop() + circle_pic.getHeight());
			canvas.drawBitmap(circle_pic, null, mRect, null);// ����û�б�ɫ��СԲȦ

			Bitmap mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
			Canvas mCanvas = new Canvas(mBitmap);
			Paint mPaint = new Paint();
			if (i == index) {
				alpha = 1f;
			} else {
				alpha = 0;
			}
			alpha = (float) Math.ceil(alpha * 255);
			mPaint.setColor(circle_color);// ����СԲȦ�����ɫ
			mPaint.setDither(true);// ������
			mPaint.setAntiAlias(true);// �����
			mPaint.setAlpha((int) alpha);// �������ɫ��͸����
			mCanvas.drawRect(mRect, mPaint);// ����һ������,���ں�СԲȦ�����ʾ
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));// ���ģʽ
			mPaint.setAlpha(255);// ��СԲȦԴͼ�����͸����
			mCanvas.drawBitmap(circle_pic, null, mRect, mPaint);// ���Ƹ���͸���ȿɱ�ɫ��СԲȦ

			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
	}

	/**
	 * ���ݵ�һ��СԲȦ�ĺ�����λ��(��СԲȦͼ��Ŀ�Ⱥ�padding)���ֱ���������СԲȦ�ĺ����� �����겻������,��Ϊ�Ǻ�������,��������ͬ
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
	 * banner����ʱ��̬����index��СԲȦ�������ɫ
	 * 
	 * @param index
	 */
	public void setSelectedCircle(int index) {
		this.index = index;
		invalidateCircle();
	}

	/**
	 * UI�߳�invalidate,��UI�߳�postInvalidate
	 */
	private void invalidateCircle() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	/**
	 * ����СԲȦ������
	 * @param circleCounts
	 */
	public void setCircleCount(int circleCounts) {
		this.circleCount = circleCounts;
	}
}
