package com.zyd.handle;

import com.zyd.model.Edge;

import android.graphics.RectF;
import android.support.annotation.NonNull;

public enum CropWindowEdgeSelector {
	//左上角：此时是控制裁剪框最上边和最左边的两条边
	TOP_LEFT(new CropWindowScaleHelper(Edge.TOP, Edge.LEFT)),

	//右上角：此时是控制裁剪框最上边和最右边的两条边
	TOP_RIGHT(new CropWindowScaleHelper(Edge.TOP, Edge.RIGHT)),

	//左下角：此时是控制裁剪框最下边和最左边的两条边
	BOTTOM_LEFT(new CropWindowScaleHelper(Edge.BOTTOM, Edge.LEFT)),

	//右下角：此时是控制裁剪框最下边和最右边的两条边
	BOTTOM_RIGHT(new CropWindowScaleHelper(Edge.BOTTOM, Edge.RIGHT)),

	//////////////////图C/////////////

	//仅控制裁剪框左边线
	LEFT(new CropWindowScaleHelper(null, Edge.LEFT)),

	//仅控制裁剪框右边线
	TOP(new CropWindowScaleHelper(Edge.TOP, null)),

	//仅控制裁剪框上边线
	RIGHT(new CropWindowScaleHelper(null, Edge.RIGHT)),

	//仅控制裁剪框下边线
	BOTTOM(new CropWindowScaleHelper(Edge.BOTTOM, null)),

	//////////////图B//////////////

	//中间位置
	CENTER(new CropWindowMoveHelper());

	private CropWindowScaleHelper mHelper;

	CropWindowEdgeSelector(CropWindowScaleHelper helper) {
		mHelper = helper;
	}

	public void updateCropWindow(float x, float y, @NonNull RectF imageRect) {

		mHelper.updateCropWindow(x, y, imageRect);
	}

}
