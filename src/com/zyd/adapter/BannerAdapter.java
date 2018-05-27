package com.zyd.adapter;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.zyd.widget.ViewBannerCircle;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * ͼƬ�ֲ���PagerAdapter
 * @author 90450
 *
 */
public class BannerAdapter extends PagerAdapter {

	private Context context;
	private ViewPager viewPager;
	private ViewBannerCircle circleView;

	private List<ImageView> imageViewList;
	private List<String> urlList;

	private int currentPosition;
	private final int changeInterval = 3000;//�Զ�����ʱ����
	private boolean isTouched = false;//�Ƿ񱻴���
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			handler.postDelayed(this, changeInterval);
			if (currentPosition == imageViewList.size() - 1) {
				viewPager.setCurrentItem(2, true);
			} else {
				viewPager.setCurrentItem(currentPosition + 1, true);
			}
		}
	};

	public BannerAdapter(ViewPager viewPager, List<String> urlList, ViewBannerCircle circleView) {
		this.viewPager = viewPager;
		this.urlList = urlList;
		context = ((RelativeLayout) viewPager.getParent()).getContext();
		this.circleView = circleView;
		initView();
		viewPager.setOnPageChangeListener(new BannerChangeListener()); //���û�������
		viewPager.setOnTouchListener(new BannerTouchListener()); //���ô�������
	}

	/**
	 * ��ʼ�Զ��ֲ�
	 */
	public void startViewPager() {
		if (!isTouched && viewPager != null) {
			handler.postDelayed(runnable, changeInterval);
			isTouched = true;
		}
	}

	/**
	 * �ر��Զ��ֲ�
	 */
	public void stopViewPager() {
		if (isTouched && viewPager != null) {
			handler.removeCallbacks(runnable);
			isTouched = false;
		}
	}

	/**
	 * ��ʼ��ͼƬ
	 */
	private void initView() {
		imageViewList = new ArrayList<ImageView>();
		for (int i = 0; i < urlList.size() + 2; i++) {
			String url;
			if (i == 0) {
				url = urlList.get(urlList.size() - 1);
			} else if (i == urlList.size() + 1) {
				url = urlList.get(0);
			} else {
				url = urlList.get(i - 1);
			}
			ImageView iv = new ImageView(context);
			iv.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			Picasso.with(context).load(url).fit().into(iv);
			imageViewList.add(iv);
		}
	}

	@Override
	public int getCount() {
		return imageViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(imageViewList.get(position));
		return imageViewList.get(position);
	}

	/**
	 * �ֲ����������ڲ���
	 * @author 90450
	 *
	 */
	private class BannerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			/* ������arg0==1,2,����ֹͣ==0 */
			if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
				/* �����������һҳ,���õ�ǰitem�޶���(�û��������κη�Ӧ)���������ڶ��� */
				if (currentPosition == 0) {
					viewPager.setCurrentItem(imageViewList.size() - 2, false);
				}
				/* ������������һҳ,���õ�ǰitem�޶���(�û��������κη�Ӧ)���صڶ��� */
				else if (currentPosition == imageViewList.size() - 1) {
					viewPager.setCurrentItem(1, false);
				}
			}
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			currentPosition = arg0;
			if (arg0 == 0 || arg0 == imageViewList.size() - 2) {
				circleView.setSelectedCircle(0);
				return;
			}
			if (arg0 == 1 || arg0 == imageViewList.size() - 1) {
				circleView.setSelectedCircle(1);
				return;
			}
			circleView.setSelectedCircle(arg0);
		}

	}

	/**
	 * �ֲ����������ڲ���
	 * @author 90450
	 *
	 */
	private class BannerTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: //��ָ����,ֹͣ����
			case MotionEvent.ACTION_MOVE: //��ָ�ƶ�,ֹͣ����
				isTouched = true;
				stopViewPager();
				break;
			case MotionEvent.ACTION_UP: //��ָ̧��,��ʼ����
				view.performClick();
				isTouched = false;
				startViewPager();
				break;
			}
			return false; //����falseֻ��ִ��һ�δ��¼�
		}

	}
}
