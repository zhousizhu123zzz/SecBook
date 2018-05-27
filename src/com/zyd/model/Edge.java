package com.zyd.model;

import android.graphics.RectF;
import android.support.annotation.NonNull;

public enum Edge {
	LEFT, TOP, RIGHT, BOTTOM;

	//�ü������С��Ȼ��߸߶�
	static final int MIN_CROP_LENGTH_PX = 80;

	//�������ұ߽�ĵ�ֵ
	private float mCoordinate;

	public void initCoordinate(float coordinate) {
		mCoordinate = coordinate;
	}

	/**
	 * ������ָ���ƶ����ı�����ֵ
	 *
	 * @param distance
	 */
	public void offset(float distance) {
		mCoordinate += distance;
	}

	public float getCoordinate() {
		return mCoordinate;
	}

	/**
	 * ����ĳ���ߵ�����λ��
	 */
	public void updateCoordinate(float x, float y, @NonNull RectF imageRect) {

		switch (this) {
		case LEFT:
			mCoordinate = adjustLeft(x, imageRect);
			break;
		case TOP:
			mCoordinate = adjustTop(y, imageRect);
			break;
		case RIGHT:
			mCoordinate = adjustRight(x, imageRect);
			break;
		case BOTTOM:
			mCoordinate = adjustBottom(y, imageRect);
			break;
		}
	}

	/**
	 * ��ȡ���п�Ŀ�
	 */
	public static float getWidth() {
		return Edge.RIGHT.getCoordinate() - Edge.LEFT.getCoordinate();
	}

	/**
	 * ��ȡ���п�ĸ�
	 */
	public static float getHeight() {
		return Edge.BOTTOM.getCoordinate() - Edge.TOP.getCoordinate();
	}

	/**
	 * �жϲü����Ƿ�ԽͼƬָ���ı߽�
	 */
	public boolean isOutsideMargin(@NonNull RectF rect) {

		final boolean result;

		switch (this) {
		case LEFT:
			result = mCoordinate - rect.left < 0;
			break;
		case TOP:
			result = mCoordinate - rect.top < 0;
			break;
		case RIGHT:
			result = rect.right - mCoordinate < 0;
			break;
		default: // BOTTOM
			result = rect.bottom - mCoordinate < 0;
			break;
		}
		return result;
	}

	private static float adjustLeft(float x, @NonNull RectF imageRect) {

		final float resultX;
		if (x - imageRect.left < 0) {//���Խ��

			resultX = imageRect.left;
		} else {

			//��ֹ�ü�����߳����ұ߻�����С��Χ
			if ((x + MIN_CROP_LENGTH_PX) >= Edge.RIGHT.getCoordinate()) {
				x = Edge.RIGHT.getCoordinate() - MIN_CROP_LENGTH_PX;
			}

			resultX = x;
		}
		return resultX;
	}

	private static float adjustRight(float x, @NonNull RectF imageRect) {

		final float resultX;

		if (imageRect.right - x < 0) {

			resultX = imageRect.right;

		} else {

			//��ֹ�ü����ұ߳�����С��Χ
			if ((x - MIN_CROP_LENGTH_PX) <= Edge.LEFT.getCoordinate()) {
				x = Edge.LEFT.getCoordinate() + MIN_CROP_LENGTH_PX;
			}

			resultX = x;
		}
		return resultX;
	}

	private static float adjustTop(float y, @NonNull RectF imageRect) {

		final float resultY;

		if (y - imageRect.top < 0) {
			resultY = imageRect.top;
		} else {
			//��ֹ�ü����ϱ߳�����С��Χ����Խ�����±�
			if ((y + MIN_CROP_LENGTH_PX) >= Edge.BOTTOM.getCoordinate()) {
				y = Edge.BOTTOM.getCoordinate() - MIN_CROP_LENGTH_PX;

			}

			resultY = y;
		}
		return resultY;
	}

	private static float adjustBottom(float y, @NonNull RectF imageRect) {

		final float resultY;

		if (imageRect.bottom - y < 0) {
			resultY = imageRect.bottom;
		} else {

			if ((y - MIN_CROP_LENGTH_PX) <= Edge.TOP.getCoordinate()) {
				y = Edge.TOP.getCoordinate() + MIN_CROP_LENGTH_PX;
			}

			resultY = y;
		}
		return resultY;
	}
}
