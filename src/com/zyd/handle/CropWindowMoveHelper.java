package com.zyd.handle;

import com.zyd.model.Edge;

import android.graphics.RectF;
import android.support.annotation.NonNull;

public class CropWindowMoveHelper extends CropWindowScaleHelper {

	CropWindowMoveHelper() {
		super(null, null);
	}

	@Override
	void updateCropWindow(float x, float y, @NonNull RectF imageRect) {

		//��ȡ�ü�����ĸ�����λ��
		float left = Edge.LEFT.getCoordinate();
		float top = Edge.TOP.getCoordinate();
		float right = Edge.RIGHT.getCoordinate();
		float bottom = Edge.BOTTOM.getCoordinate();

		//��ȡ�ü��������λ��
		final float currentCenterX = (left + right) / 2;
		final float currentCenterY = (top + bottom) / 2;

		//�ж���ָ�ƶ��ľ���
		final float offsetX = x - currentCenterX;
		final float offsetY = y - currentCenterY;

		//���²ü��������ߵ�����
		Edge.LEFT.offset(offsetX);
		Edge.TOP.offset(offsetY);
		Edge.RIGHT.offset(offsetX);
		Edge.BOTTOM.offset(offsetY);

		//////////////�ü���Խ�紦��/////////////////

		if (Edge.LEFT.isOutsideMargin(imageRect)) {
			//��ȡ��ʱxԽ��ʱ������λ��
			float currentCoordinate = Edge.LEFT.getCoordinate();

			//����ָ����ߵ�ֵΪ��ʼֵ
			Edge.LEFT.initCoordinate(imageRect.left);

			//Խ��ľ���
			float offset = Edge.LEFT.getCoordinate() - currentCoordinate;

			//�������ұߵ�ƫ����
			Edge.RIGHT.offset(offset);
		} else if (Edge.RIGHT.isOutsideMargin(imageRect)) {

			float currentCoordinate = Edge.RIGHT.getCoordinate();

			Edge.RIGHT.initCoordinate(imageRect.right);

			float offset = Edge.RIGHT.getCoordinate() - currentCoordinate;

			Edge.LEFT.offset(offset);
		}

		if (Edge.TOP.isOutsideMargin(imageRect)) {

			float currentCoordinate = Edge.TOP.getCoordinate();

			Edge.TOP.initCoordinate(imageRect.top);

			float offset = Edge.TOP.getCoordinate() - currentCoordinate;

			Edge.BOTTOM.offset(offset);

		} else if (Edge.BOTTOM.isOutsideMargin(imageRect)) {

			float currentCoordinate = Edge.BOTTOM.getCoordinate();

			Edge.BOTTOM.initCoordinate(imageRect.bottom);

			float offset = Edge.BOTTOM.getCoordinate() - currentCoordinate;

			Edge.TOP.offset(offset);
		}
	}

}
