package com.zyd.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决ListView嵌套GridView只能显示一行数据的问题
 * @author 90450
 *
 */
public class ViewGridInListView extends GridView {

	public ViewGridInListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ViewGridInListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
