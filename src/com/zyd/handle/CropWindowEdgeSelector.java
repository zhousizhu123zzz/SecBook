package com.zyd.handle;

import com.zyd.model.Edge;

import android.graphics.RectF;
import android.support.annotation.NonNull;

public enum CropWindowEdgeSelector {
	//���Ͻǣ���ʱ�ǿ��Ʋü������ϱߺ�����ߵ�������
	TOP_LEFT(new CropWindowScaleHelper(Edge.TOP, Edge.LEFT)),

	//���Ͻǣ���ʱ�ǿ��Ʋü������ϱߺ����ұߵ�������
	TOP_RIGHT(new CropWindowScaleHelper(Edge.TOP, Edge.RIGHT)),

	//���½ǣ���ʱ�ǿ��Ʋü������±ߺ�����ߵ�������
	BOTTOM_LEFT(new CropWindowScaleHelper(Edge.BOTTOM, Edge.LEFT)),

	//���½ǣ���ʱ�ǿ��Ʋü������±ߺ����ұߵ�������
	BOTTOM_RIGHT(new CropWindowScaleHelper(Edge.BOTTOM, Edge.RIGHT)),

	//////////////////ͼC/////////////

	//�����Ʋü��������
	LEFT(new CropWindowScaleHelper(null, Edge.LEFT)),

	//�����Ʋü����ұ���
	TOP(new CropWindowScaleHelper(Edge.TOP, null)),

	//�����Ʋü����ϱ���
	RIGHT(new CropWindowScaleHelper(null, Edge.RIGHT)),

	//�����Ʋü����±���
	BOTTOM(new CropWindowScaleHelper(Edge.BOTTOM, null)),

	//////////////ͼB//////////////

	//�м�λ��
	CENTER(new CropWindowMoveHelper());

	private CropWindowScaleHelper mHelper;

	CropWindowEdgeSelector(CropWindowScaleHelper helper) {
		mHelper = helper;
	}

	public void updateCropWindow(float x, float y, @NonNull RectF imageRect) {

		mHelper.updateCropWindow(x, y, imageRect);
	}

}
