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
 * 图片轮播器PagerAdapter
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
	private final int changeInterval = 3000;//自动滚动时间间隔
	private boolean isTouched = false;//是否被触摸
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
		viewPager.setOnPageChangeListener(new BannerChangeListener()); //设置滑动监听
		viewPager.setOnTouchListener(new BannerTouchListener()); //设置触摸监听
	}

	/**
	 * 开始自动轮播
	 */
	public void startViewPager() {
		if (!isTouched && viewPager != null) {
			handler.postDelayed(runnable, changeInterval);
			isTouched = true;
		}
	}

	/**
	 * 关闭自动轮播
	 */
	public void stopViewPager() {
		if (isTouched && viewPager != null) {
			handler.removeCallbacks(runnable);
			isTouched = false;
		}
	}

	/**
	 * 初始化图片
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
	 * 轮播滑动监听内部类
	 * @author 90450
	 *
	 */
	private class BannerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			/* 滑动中arg0==1,2,滑动停止==0 */
			if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
				/* 如果滑动到第一页,则让当前item无动画(用户看不到任何反应)跳到倒数第二项 */
				if (currentPosition == 0) {
					viewPager.setCurrentItem(imageViewList.size() - 2, false);
				}
				/* 如果滑动到最后一页,则让当前item无动画(用户看不到任何反应)跳回第二项 */
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
	 * 轮播触摸监听内部类
	 * @author 90450
	 *
	 */
	private class BannerTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: //手指按下,停止滑动
			case MotionEvent.ACTION_MOVE: //手指移动,停止滑动
				isTouched = true;
				stopViewPager();
				break;
			case MotionEvent.ACTION_UP: //手指抬起,开始滑动
				view.performClick();
				isTouched = false;
				startViewPager();
				break;
			}
			return false; //返回false只会执行一次此事件
		}

	}
}
