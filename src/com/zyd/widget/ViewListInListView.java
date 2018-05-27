package com.zyd.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ViewListInListView extends ListView {

	public ViewListInListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewListInListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
