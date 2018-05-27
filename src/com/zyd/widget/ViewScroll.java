package com.zyd.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ViewScroll extends ScrollView {

	private ScrollViewChangeListener mChangeListener;

	public ViewScroll(Context context) {
		super(context);
	}

	public ViewScroll(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ViewScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public interface ScrollViewChangeListener {
		void onScrollChanged(int l, int t, int oldl, int oldt);
	}

	public void setScrollViewChangeListener(ScrollViewChangeListener scrollViewChangeListener) {
		this.mChangeListener = scrollViewChangeListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mChangeListener != null) {
			mChangeListener.onScrollChanged(l, t, oldl, oldt);
		}
	}

}
