package com.zyd.handle;

import com.zyd.model.Edge;

import android.graphics.RectF;
import android.support.annotation.NonNull;

public class CropWindowScaleHelper {
	private Edge mHorizontalEdge;
	private Edge mVerticalEdge;

	CropWindowScaleHelper(Edge horizontalEdge, Edge verticalEdge) {
		mHorizontalEdge = horizontalEdge;
		mVerticalEdge = verticalEdge;
	}

	/**
	 * ������ָ���ƶ����ı�ü���Ĵ�С
	 *
	 * @param x         ��ָx�����λ��
	 * @param y         ��ָy�����λ��
	 * @param imageRect ������ʾͼƬ�߽�ľ���
	 */
	void updateCropWindow(float x, float y, @NonNull RectF imageRect) {

		if (mHorizontalEdge != null)
			mHorizontalEdge.updateCoordinate(x, y, imageRect);

		if (mVerticalEdge != null)
			mVerticalEdge.updateCoordinate(x, y, imageRect);
	}
}
