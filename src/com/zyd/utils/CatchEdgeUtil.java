package com.zyd.utils;

import com.zyd.handle.CropWindowEdgeSelector;

import android.graphics.PointF;
import android.support.annotation.NonNull;

/***
 * ������ָ�ڲü������һ����
 */
public class CatchEdgeUtil {
	/**
	* �ж���ָ�Ƿ��λ���Ƿ�����Ч������������������İ뾶ΪtargetRadius
	* ��������ʹָ���ü�����ĸ��ǶȻ��������ߣ�����ָλ�ô���ĳ����
	* ����ĳ���ߵ�ʱ����������ָ���ƶ��Բü���������Ų�����
	* �����ָλ�ڲü�����ڲ�����ü���������ָ���ƶ���ֻ�����ƶ�������
	* ��������ж���ָ����ü����Զ��ʲô������
	*/
	public static CropWindowEdgeSelector getPressedHandle(float x, float y, float left, float top, float right,
			float bottom, float targetRadius) {

		CropWindowEdgeSelector nearestCropWindowEdgeSelector = null;

		//�ж���ָ����ü�����һ�������

		//�������Ĭ���������
		float nearestDistance = Float.POSITIVE_INFINITY;
		//////////�ж���ָ�Ƿ���ͼ���ֵ�Aλ�ã��ĸ���֮һ/////////////////

		//������ָ�������Ͻǵľ���
		final float distanceToTopLeft = calculateDistance(x, y, left, top);
		if (distanceToTopLeft < nearestDistance) {
			nearestDistance = distanceToTopLeft;
			nearestCropWindowEdgeSelector = CropWindowEdgeSelector.TOP_LEFT;
		}

		//������ָ�������Ͻǵľ���
		final float distanceToTopRight = calculateDistance(x, y, right, top);
		if (distanceToTopRight < nearestDistance) {
			nearestDistance = distanceToTopRight;
			nearestCropWindowEdgeSelector = CropWindowEdgeSelector.TOP_RIGHT;
		}

		//������ָ�������½ǵľ���
		final float distanceToBottomLeft = calculateDistance(x, y, left, bottom);
		if (distanceToBottomLeft < nearestDistance) {
			nearestDistance = distanceToBottomLeft;
			nearestCropWindowEdgeSelector = CropWindowEdgeSelector.BOTTOM_LEFT;
		}

		//������ָ�������½ǵľ���
		final float distanceToBottomRight = calculateDistance(x, y, right, bottom);
		if (distanceToBottomRight < nearestDistance) {
			nearestDistance = distanceToBottomRight;
			nearestCropWindowEdgeSelector = CropWindowEdgeSelector.BOTTOM_RIGHT;
		}

		//�����ָѡ����һ������Ľǣ����������ŷ�Χ���򷵻������
		if (nearestDistance <= targetRadius) {
			return nearestCropWindowEdgeSelector;
		}

		//////////�ж���ָ�Ƿ���ͼ���ֵ�Cλ�ã��ĸ��ߵ�ĳ����/////////////////
		if (CatchEdgeUtil.isInHorizontalTargetZone(x, y, left, right, top, targetRadius)) {
			return CropWindowEdgeSelector.TOP;//˵����ָ�ڲü���top����
		} else if (CatchEdgeUtil.isInHorizontalTargetZone(x, y, left, right, bottom, targetRadius)) {
			return CropWindowEdgeSelector.BOTTOM;//˵����ָ�ڲü���bottom����
		} else if (CatchEdgeUtil.isInVerticalTargetZone(x, y, left, top, bottom, targetRadius)) {
			return CropWindowEdgeSelector.LEFT;//˵����ָ�ڲü���left����
		} else if (CatchEdgeUtil.isInVerticalTargetZone(x, y, right, top, bottom, targetRadius)) {
			return CropWindowEdgeSelector.RIGHT;//˵����ָ�ڲü���right����
		}

		//////////�ж���ָ�Ƿ���ͼ���ֵ�Bλ�ã��ü�����м�/////////////////
		if (isWithinBounds(x, y, left, top, right, bottom)) {
			return CropWindowEdgeSelector.CENTER;
		}

		////////��ָλ�ڲü����Dλ�ã���ʱ�ƶ���ָʲô������/////////////
		return null;
	}

	public static void getOffset(@NonNull CropWindowEdgeSelector cropWindowEdgeSelector, float x, float y, float left,
			float top, float right, float bottom, @NonNull PointF touchOffsetOutput) {

		float touchOffsetX = 0;
		float touchOffsetY = 0;

		switch (cropWindowEdgeSelector) {

		case TOP_LEFT:
			touchOffsetX = left - x;
			touchOffsetY = top - y;
			break;
		case TOP_RIGHT:
			touchOffsetX = right - x;
			touchOffsetY = top - y;
			break;
		case BOTTOM_LEFT:
			touchOffsetX = left - x;
			touchOffsetY = bottom - y;
			break;
		case BOTTOM_RIGHT:
			touchOffsetX = right - x;
			touchOffsetY = bottom - y;
			break;
		case LEFT:
			touchOffsetX = left - x;
			touchOffsetY = 0;
			break;
		case TOP:
			touchOffsetX = 0;
			touchOffsetY = top - y;
			break;
		case RIGHT:
			touchOffsetX = right - x;
			touchOffsetY = 0;
			break;
		case BOTTOM:
			touchOffsetX = 0;
			touchOffsetY = bottom - y;
			break;
		case CENTER:
			final float centerX = (right + left) / 2;
			final float centerY = (top + bottom) / 2;
			touchOffsetX = centerX - x;
			touchOffsetY = centerY - y;
			break;
		}

		touchOffsetOutput.x = touchOffsetX;
		touchOffsetOutput.y = touchOffsetY;
	}

	private static boolean isInHorizontalTargetZone(float x, float y, float handleXStart, float handleXEnd,
			float handleY, float targetRadius) {

		return (x > handleXStart && x < handleXEnd && Math.abs(y - handleY) <= targetRadius);
	}

	private static boolean isInVerticalTargetZone(float x, float y, float handleX, float handleYStart, float handleYEnd,
			float targetRadius) {

		return (Math.abs(x - handleX) <= targetRadius && y > handleYStart && y < handleYEnd);
	}

	private static boolean isWithinBounds(float x, float y, float left, float top, float right, float bottom) {
		return x >= left && x <= right && y >= top && y <= bottom;
	}

	/**
	 * ���� (x1, y1) �� (x2, y2)������ľ���
	 */
	private static float calculateDistance(float x1, float y1, float x2, float y2) {

		final float side1 = x2 - x1;
		final float side2 = y2 - y1;

		return (float) Math.sqrt(side1 * side1 + side2 * side2);
	}
}
