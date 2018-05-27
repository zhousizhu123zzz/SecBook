package com.zyd.widget;

import com.zyd.handle.CropWindowEdgeSelector;
import com.zyd.model.Edge;
import com.zyd.utils.CatchEdgeUtil;
import com.zyd.utils.EqupUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CropImageView extends ImageView {
	//�ü���߿򻭱�
	private Paint mBorderPaint;

	//�ü���Ź��񻭱�
	private Paint mGuidelinePaint;

	//���Ʋü��߿��ĸ��ǵĻ���
	private Paint mCornerPaint;

	//�ж���ָλ���Ƿ������Ųü���λ�õķ�Χ������ǵ���ָ�ƶ���ʱ��ü������Ӧ�ı仯��С
	//������ָ�ƶ���ʱ������϶��ü���ʹ֮������ָ�ƶ�
	private float mScaleRadius;

	private float mCornerThickness;

	private float mBorderThickness;

	//�ĸ���С�̱ߵĳ���
	private float mCornerLength;

	//������ʾͼƬ�߽�ľ���
	private RectF mBitmapRect = new RectF();

	//��ָλ�þ���ü����ƫ����
	private PointF mTouchOffset = new PointF();

	private CropWindowEdgeSelector mPressedCropWindowEdgeSelector;

	public CropImageView(Context context) {
		super(context);
		init(context);
	}

	public CropImageView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context);
	}

	/**
	 * �����ֵ��ʱд����Ҳ���Դ�AttributeSet����������
	 *
	 * @param context
	 */
	private void init(@NonNull Context context) {

		mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setStrokeWidth(EqupUtil.dip2px(context, 3));
		mBorderPaint.setColor(Color.parseColor("#AAFFFFFF"));

		mGuidelinePaint = new Paint();
		mGuidelinePaint.setStyle(Paint.Style.STROKE);
		mGuidelinePaint.setStrokeWidth(EqupUtil.dip2px(context, 1));
		mGuidelinePaint.setColor(Color.parseColor("#AAFFFFFF"));

		mCornerPaint = new Paint();
		mCornerPaint.setStyle(Paint.Style.STROKE);
		mCornerPaint.setStrokeWidth(EqupUtil.dip2px(context, 5));
		mCornerPaint.setColor(Color.WHITE);

		mScaleRadius = EqupUtil.dip2px(context, 24);
		mBorderThickness = EqupUtil.dip2px(context, 3);
		mCornerThickness = EqupUtil.dip2px(context, 5);
		mCornerLength = EqupUtil.dip2px(context, 20);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

		super.onLayout(changed, left, top, right, bottom);

		mBitmapRect = getBitmapRect();
		initCropWindow(mBitmapRect);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		//���ƾŹ���������
		drawGuidelines(canvas);
		//���Ʋü��߿�
		drawBorder(canvas);
		//���Ʋü��߿���ĸ���
		drawCorners(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (!isEnabled()) {
			return false;
		}

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			onActionDown(event.getX(), event.getY());
			return true;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			getParent().requestDisallowInterceptTouchEvent(false);
			onActionUp();
			return true;

		case MotionEvent.ACTION_MOVE:
			onActionMove(event.getX(), event.getY());
			getParent().requestDisallowInterceptTouchEvent(true);
			return true;

		default:
			return false;
		}
	}

	/**
	 * ��ȡ�ü��õ�BitMap
	 */
	public Bitmap getCroppedImage() {

		final Drawable drawable = getDrawable();
		if (drawable == null || !(drawable instanceof BitmapDrawable)) {
			return null;
		}

		final float[] matrixValues = new float[9];
		getImageMatrix().getValues(matrixValues);

		final float scaleX = matrixValues[Matrix.MSCALE_X];
		final float scaleY = matrixValues[Matrix.MSCALE_Y];
		final float transX = matrixValues[Matrix.MTRANS_X];
		final float transY = matrixValues[Matrix.MTRANS_Y];

		float bitmapLeft = (transX < 0) ? Math.abs(transX) : 0;
		float bitmapTop = (transY < 0) ? Math.abs(transY) : 0;

		final Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();

		final float cropX = (bitmapLeft + Edge.LEFT.getCoordinate()) / scaleX;
		final float cropY = (bitmapTop + Edge.TOP.getCoordinate()) / scaleY;

		final float cropWidth = Math.min(Edge.getWidth() / scaleX, originalBitmap.getWidth() - cropX);
		final float cropHeight = Math.min(Edge.getHeight() / scaleY, originalBitmap.getHeight() - cropY);

		return Bitmap.createBitmap(originalBitmap, (int) cropX, (int) cropY, (int) cropWidth, (int) cropHeight);

	}

	/**
	 * ��ȡͼƬImageView��Χ�ı߽���ɵ�RectF����
	 */
	private RectF getBitmapRect() {

		final Drawable drawable = getDrawable();
		if (drawable == null) {
			return new RectF();
		}

		final float[] matrixValues = new float[9];
		getImageMatrix().getValues(matrixValues);

		final float scaleX = matrixValues[Matrix.MSCALE_X];
		final float scaleY = matrixValues[Matrix.MSCALE_Y];
		final float transX = matrixValues[Matrix.MTRANS_X];
		final float transY = matrixValues[Matrix.MTRANS_Y];

		final int drawableIntrinsicWidth = drawable.getIntrinsicWidth();
		final int drawableIntrinsicHeight = drawable.getIntrinsicHeight();

		final int drawableDisplayWidth = Math.round(drawableIntrinsicWidth * scaleX);
		final int drawableDisplayHeight = Math.round(drawableIntrinsicHeight * scaleY);

		final float left = Math.max(transX, 0);
		final float top = Math.max(transY, 0);
		final float right = Math.min(left + drawableDisplayWidth, getWidth());
		final float bottom = Math.min(top + drawableDisplayHeight, getHeight());

		return new RectF(left, top, right, bottom);
	}

	/**
	 * ��ʼ���ü���
	 *
	 * @param bitmapRect
	 */
	private void initCropWindow(@NonNull RectF bitmapRect) {

		//�ü������ͼƬ���ҵ�paddingֵ
		final float horizontalPadding = 0.01f * bitmapRect.width();
		final float verticalPadding = 0.01f * bitmapRect.height();

		//��ʼ���ü�����������������
		Edge.LEFT.initCoordinate(bitmapRect.left + horizontalPadding);
		Edge.TOP.initCoordinate(bitmapRect.top + verticalPadding);
		Edge.RIGHT.initCoordinate(bitmapRect.right - horizontalPadding);
		Edge.BOTTOM.initCoordinate(bitmapRect.bottom - verticalPadding);
	}

	private void drawGuidelines(@NonNull Canvas canvas) {

		final float left = Edge.LEFT.getCoordinate();
		final float top = Edge.TOP.getCoordinate();
		final float right = Edge.RIGHT.getCoordinate();
		final float bottom = Edge.BOTTOM.getCoordinate();

		final float oneThirdCropWidth = Edge.getWidth() / 3;

		final float x1 = left + oneThirdCropWidth;
		//��������ֱ�����һ����
		canvas.drawLine(x1, top, x1, bottom, mGuidelinePaint);
		final float x2 = right - oneThirdCropWidth;
		//��������ֱ����ڶ�����
		canvas.drawLine(x2, top, x2, bottom, mGuidelinePaint);

		final float oneThirdCropHeight = Edge.getHeight() / 3;

		final float y1 = top + oneThirdCropHeight;
		//������ˮƽ�����һ����
		canvas.drawLine(left, y1, right, y1, mGuidelinePaint);
		final float y2 = bottom - oneThirdCropHeight;
		//������ˮƽ����ڶ�����
		canvas.drawLine(left, y2, right, y2, mGuidelinePaint);
	}

	private void drawBorder(@NonNull Canvas canvas) {

		canvas.drawRect(Edge.LEFT.getCoordinate(), Edge.TOP.getCoordinate(), Edge.RIGHT.getCoordinate(),
				Edge.BOTTOM.getCoordinate(), mBorderPaint);
	}

	private void drawCorners(@NonNull Canvas canvas) {

		final float left = Edge.LEFT.getCoordinate();
		final float top = Edge.TOP.getCoordinate();
		final float right = Edge.RIGHT.getCoordinate();
		final float bottom = Edge.BOTTOM.getCoordinate();

		//�򵥵���ѧ����

		final float lateralOffset = (mCornerThickness - mBorderThickness) / 2f;
		final float startOffset = mCornerThickness - (mBorderThickness / 2f);

		//���Ͻ�����Ķ���
		canvas.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + mCornerLength,
				mCornerPaint);
		//���Ͻ�����Ķ���
		canvas.drawLine(left - startOffset, top - lateralOffset, left + mCornerLength, top - lateralOffset,
				mCornerPaint);

		//���Ͻ�����Ķ���
		canvas.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + mCornerLength,
				mCornerPaint);
		//���Ͻ�����Ķ���
		canvas.drawLine(right + startOffset, top - lateralOffset, right - mCornerLength, top - lateralOffset,
				mCornerPaint);

		//���½�����Ķ���
		canvas.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - mCornerLength,
				mCornerPaint);
		//���½ǵײ��Ķ���
		canvas.drawLine(left - startOffset, bottom + lateralOffset, left + mCornerLength, bottom + lateralOffset,
				mCornerPaint);

		//���½�����Ķ���
		canvas.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - mCornerLength,
				mCornerPaint);
		//���½ǵײ��Ķ���
		canvas.drawLine(right + startOffset, bottom + lateralOffset, right - mCornerLength, bottom + lateralOffset,
				mCornerPaint);
	}

	/**
	 * ������ָ�����¼�
	 * @param x ��ָ����ʱˮƽ���������
	 * @param y ��ָ����ʱ��ֱ���������
	 */
	private void onActionDown(float x, float y) {

		//��ȡ�߿�����������ĸ�����������
		final float left = Edge.LEFT.getCoordinate();
		final float top = Edge.TOP.getCoordinate();
		final float right = Edge.RIGHT.getCoordinate();
		final float bottom = Edge.BOTTOM.getCoordinate();

		//��ȡ��ָ����λ��λ��ͼ���ֵ�A��B��C��Dλ������һ��
		mPressedCropWindowEdgeSelector = CatchEdgeUtil.getPressedHandle(x, y, left, top, right, bottom, mScaleRadius);

		if (mPressedCropWindowEdgeSelector != null) {
			//������ָ���µ�λ����ü����ƫ����
			CatchEdgeUtil.getOffset(mPressedCropWindowEdgeSelector, x, y, left, top, right, bottom, mTouchOffset);
			invalidate();
		}
	}

	private void onActionUp() {
		if (mPressedCropWindowEdgeSelector != null) {
			mPressedCropWindowEdgeSelector = null;
			invalidate();
		}
	}

	private void onActionMove(float x, float y) {

		if (mPressedCropWindowEdgeSelector == null) {
			return;
		}

		x += mTouchOffset.x;
		y += mTouchOffset.y;

		mPressedCropWindowEdgeSelector.updateCropWindow(x, y, mBitmapRect);
		invalidate();
	}
}
