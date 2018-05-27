package com.zyd.widget;

import java.util.ArrayList;
import java.util.List;

import com.zyd.secbooks.R;
import com.zyd.utils.StringUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ���Ը������ݳ���,�Զ�����TextView��LinearLayout
 * @author 90450
 *
 */
public class AutoLineTextView extends LinearLayout {

	private int mTextSize; //TextView�����С
	private int mTextColor; //TextView������ɫ
	private int mTextStyle; //TextView�ķ��
	private int mTextPaddingLeft; //TextView��PaddingLeft
	private int mTextPaddingRight;//TextView��PaddingRight
	private int mTextPaddingTop;//TextView��PaddingTop
	private int mTextPaddingBottom;//TextView��PaddingBottom
	private int mTextMargin; //TextView��layout_margin
	private int mLinePaddingLeft; //һ��LinearLayout��PaddingLeft
	private int mLinePaddingRight; //һ��LinearLayout��PaddingRight
	private int mLinePaddingTop;// һ��LinearLayout��PaddingTop
	private int mLinePaddingBottom;// һ��LinearLayout��PaddingBottom

	private LinearLayout mChildLayout; //ÿһ�е�LinearLayout

	private List<String> mData;

	public AutoLineTextView(Context context) {
		this(context, null);
	}

	public AutoLineTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AutoLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewAutoLineText);
		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int index = typedArray.getIndex(i);
			switch (index) {
			case R.styleable.ViewAutoLineText_textSize:
				mTextSize = typedArray.getInteger(index, 13);
				break;

			case R.styleable.ViewAutoLineText_textColor:
				mTextColor = typedArray.getColor(index, Color.BLACK);
				break;
			case R.styleable.ViewAutoLineText_textStyle:
				mTextStyle = typedArray.getResourceId(index, 0);
				break;
			case R.styleable.ViewAutoLineText_textMargin:
				mTextMargin = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_textPaddingLeft:
				mTextPaddingLeft = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_textPaddingRight:
				mTextPaddingRight = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_textPaddingTop:
				mTextPaddingTop = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_textPaddingBottom:
				mTextPaddingBottom = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_linePaddingLeft:
				mLinePaddingLeft = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_linePaddingRight:
				mLinePaddingRight = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_linePaddingTop:
				mLinePaddingTop = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;
			case R.styleable.ViewAutoLineText_linePaddingBottom:
				mLinePaddingBottom = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
				break;

			}
		}
		typedArray.recycle();

	}

	/**
	 * ��������TextView�Ŀ��
	 */
	private void genView(List<String> dataList, int width) {
		for (int i = 0; i < dataList.size(); i++) {
			TextView tv = new TextView(mChildLayout.getContext());
			LayoutParams lp2 = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			lp2.setMargins(0, 0, mTextMargin, 0);
			tv.setLayoutParams(lp2);
			tv.setTextColor(mTextColor);
			tv.setTextSize(mTextSize);
			tv.setBackgroundResource(mTextStyle);
			tv.setPadding(mTextPaddingLeft, mTextPaddingTop, mTextPaddingRight, mTextPaddingBottom);
			tv.setText(dataList.get(i));
			mChildLayout.addView(tv);
			mChildLayout.measure(0, 0);
			if (mChildLayout.getMeasuredWidth() >= width) {
				mChildLayout.removeViewAt(i);
				addView(mChildLayout);
				initLine(width);
				genView(StringUtil.remove(dataList, i), width);
			}
		}
	}

	/**
	 * ��ʼ��LinearLayout
	 */
	private void initLine(int width) {
		mChildLayout = new LinearLayout(getContext());
		mChildLayout.setLayoutParams(
				new LayoutParams(width, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL));
		mChildLayout.setPadding(mLinePaddingLeft, mLinePaddingTop, mLinePaddingRight, mLinePaddingBottom);
	}

	public void setDataList(List<String> dataList) {
		this.mData = dataList;
	}

	/**
	 * ����View
	 */
	public void genView(int width) {
		initLine(width);
		genView(mData, width);
		addView(mChildLayout);
	}

	/**
	 * �õ����е�TextView
	 * @return
	 */
	public List<TextView> getAllTextView() {
		List<TextView> resultList = new ArrayList<TextView>();
		for (int i = 0; i < getChildCount(); i++) {
			LinearLayout ll = (LinearLayout) getChildAt(i);
			for (int j = 0; j < ll.getChildCount(); j++) {
				resultList.add((TextView) ll.getChildAt(j));
			}
		}
		return resultList;
	}
}
