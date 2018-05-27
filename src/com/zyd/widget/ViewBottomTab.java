package com.zyd.widget;

import com.zyd.secbooks.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * �Զ���ײ�������
 * @author 90450
 *
 */
public class ViewBottomTab extends View {
	private Bitmap tab_icon; // tabͼ��
	private int tab_color = 0xff45C01A; // tab��ɫ
	private String tab_text; // tab����
	private int tab_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
			getResources().getDisplayMetrics()); // tab���ݵĴ�С

	/**
	 * ���ڴ��л���ͼ��
	 */
	private Canvas iconCanvas; // ����
	private Bitmap iconBitmap; // ͼ��
	private Paint iconPaint; // ����
	private Rect iconRect; // ����

	/**
	 * ��������
	 */
	private Paint textPaint;// ����
	private Rect textRect; // ����

	private float alpha;// ͨ�������޸�͸������ʵ�ֶ�̬Ч��

	private final int TEXT_COLOR_FROM_ICON = 0xff555555;

	private final int PADDING = getPaddingTop();

	public ViewBottomTab(Context context) {
		this(context, null);
	}

	public ViewBottomTab(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewBottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewBottomTab);
		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int index = typedArray.getIndex(i);
			switch (index) {
			case R.styleable.ViewBottomTab_tab_icon:
				BitmapDrawable bitmapDrawable = (BitmapDrawable) typedArray.getDrawable(index);
				tab_icon = bitmapDrawable.getBitmap();
				break;
			case R.styleable.ViewBottomTab_tab_color:
				tab_color = typedArray.getColor(index, 0xff45C01A);
				break;
			case R.styleable.ViewBottomTab_tab_text:
				tab_text = typedArray.getString(index);
				break;
			case R.styleable.ViewBottomTab_tab_text_size:
				tab_size = (int) typedArray.getDimension(index,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics()));
			}
		}

		/**
		 * ��ʼ������
		 */
		textPaint = new Paint();
		textRect = new Rect();
		textPaint.setTextSize(tab_size);
		textPaint.setColor(TEXT_COLOR_FROM_ICON);
		textPaint.getTextBounds(tab_text, 0, tab_text.length(), textRect);
		/**
		 * ����
		 */
		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * ����ͼ��Ŀ��;����������� һ������ ������ƽ
		 */
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
				getMeasuredHeight() - PADDING * 3 - textRect.height());

		/**
		 * ������ʾ,��ȡ���Ͻǵ�����
		 */
		int position_x = getMeasuredWidth() / 2 - iconWidth / 2;
		int position_y = getPaddingTop();

		iconRect = new Rect(position_x, position_y, position_x + iconWidth, position_y + iconWidth);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/**
		 * ���ƿɱ�ɫͼ����̣� 1.����ԭͼ�ֻ꣬��ҪBitmap�ͻ��ࡣ2.����alpha.����һ���ɱ�ɫ��ͼ��
		 */
		canvas.drawBitmap(tab_icon, null, iconRect, null);
		int mAlpha = (int) Math.ceil(alpha * 255);
		setupIcon(mAlpha);
		canvas.drawBitmap(iconBitmap, 0, 0, null);

		drawSrcText(canvas, mAlpha);
		drawAlphaText(canvas, mAlpha);
	}

	/**
	 * ���ڴ��л��Ƹ���alpha�ɱ�ɫ��ͼ�� ע�⣬ȡXformodeȡ�������ֵ�ǰ���ǣ�ͼ������Ƿ�յģ�͸���ġ�
	 * 
	 * @param alpha
	 */
	private void setupIcon(int alpha) {
		/**
		 * 1.������Ҫ����,���ǻ��������������Ϊ��,��Ҫһ��Bitmap,���ȴ���һ��Bitmap
		 */
		iconBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		iconCanvas = new Canvas(iconBitmap);
		/**
		 * 2.������Ҫһ������
		 */
		iconPaint = new Paint();
		iconPaint.setColor(tab_color); // ���û��ʵ���ɫ
		iconPaint.setAntiAlias(true); // ���ÿ����
		iconPaint.setDither(true); // ���÷�����
		iconPaint.setAlpha(alpha);// ����͸����
		/**
		 * 3.���û����ڻ����ϻ���
		 */
		iconCanvas.drawRect(iconRect, iconPaint);
		iconPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); // ���ģʽ��ȡ��������

		iconPaint.setAlpha(255); // ��͸�������û���

		iconCanvas.drawBitmap(tab_icon, null, iconRect, iconPaint);

	}

	/**
	 * public--����͸����
	 * 
	 * @param alpha
	 */
	public void setIconAlpha(float alpha) {
		this.alpha = alpha;
		invalidateView();
	}

	/**
	 * ����ԭ�ı�
	 */
	private void drawSrcText(Canvas canvas, int alpha) {
		textPaint.setColor(TEXT_COLOR_FROM_ICON);
		textPaint.setAlpha(255 - alpha);
		int positionX = getMeasuredWidth() / 2 - textRect.width() / 2;
		int positionY = iconRect.bottom + PADDING * 3;
		canvas.drawText(tab_text, positionX, positionY, textPaint);
	}

	/**
	 * ���ƿɱ�ɫ���ı�
	 */
	private void drawAlphaText(Canvas canvas, int alpha) {
		textPaint.setColor(tab_color);
		textPaint.setAlpha(alpha);
		int positionX = getMeasuredWidth() / 2 - textRect.width() / 2;
		int positionY = iconRect.bottom + PADDING * 3;
		canvas.drawText(tab_text, positionX, positionY, textPaint);
	}

	private void invalidateView() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate(); // UI�̵߳Ļ����ػ���
		} else {
			postInvalidate();// ��UI�߳̾�postInvalidate
		}
	}
}
