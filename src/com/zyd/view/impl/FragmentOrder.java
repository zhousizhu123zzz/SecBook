package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.Order;
import com.zyd.model.User;
import com.zyd.presenter.OrderPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentOrderView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOrder extends Fragment implements FragmentOrderView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * ProgressBar
	 */
	private ProgressBar pb_loading;

	/**
	 * Scroller
	 */
	private View v_scroller;
	private TextView tv_all, tv_pay, tv_send, tv_receipt;

	/**
	 * Content
	 */
	private ViewPager vp_content;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private int titleWidth, firstTitleX;

	private OrderPresenter orderPresenter = new OrderPresenter(this);
	private List<Order> orderList;
	private int indexVPFragmentPosition = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_order, container, false);
		initView();
		setIndexVPFragmentPositionFromCache();
		initScrollView();
		orderPresenter.list();
		return rootView;
	}

	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentOrder_ll_back);
		pb_loading = (ProgressBar) rootView.findViewById(R.id.fragmentOrder_pb_loading);
		v_scroller = rootView.findViewById(R.id.fragmentOrder_v_scroller);
		tv_all = (TextView) rootView.findViewById(R.id.fragmentOrder_tv_all);
		tv_pay = (TextView) rootView.findViewById(R.id.fragmentOrder_tv_wait4Pay);
		tv_send = (TextView) rootView.findViewById(R.id.fragmentOrder_tv_send);
		tv_receipt = (TextView) rootView.findViewById(R.id.fragmentOrder_tv_receipt);
		vp_content = (ViewPager) rootView.findViewById(R.id.fragmentOrder_vp_content);

		ll_back.setOnClickListener(new ClickListener());
		tv_all.setOnClickListener(new ClickListener());
		tv_pay.setOnClickListener(new ClickListener());
		tv_send.setOnClickListener(new ClickListener());
		tv_receipt.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.fragmentOrder_ll_back:
				toFragmentMain();
				break;
			case R.id.fragmentOrder_tv_all:
				toFragmentOrderAll();
				break;
			case R.id.fragmentOrder_tv_send:
				toFragmentOrderSend();
				break;
			case R.id.fragmentOrder_tv_wait4Pay:
				toFragmentOrderPay();
				break;
			case R.id.fragmentOrder_tv_receipt:
				toFragmentOrderReceipt();
				break;

			}
		}

	}

	/**
	 * ViewPager的适配器
	 * @author 朱永地
	 *
	 */
	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	/**
	 * ViewPager的滑动监听内部类
	 * @author 朱永地
	 *
	 */
	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			v_scroller.setX(firstTitleX + titleWidth * arg0);
		}

	}

	/**
	 * 初始化ViewPager的Fragment集合
	 */
	private void initFragmentList() {
		fragmentList.add(new FragmentOrderItem("fragmentOrderAll"));
		fragmentList.add(new FragmentOrderItem("fragmentOrderPay"));
		fragmentList.add(new FragmentOrderItem("fragmentOrderSend"));
		fragmentList.add(new FragmentOrderItem("fragmentOrderReceipt"));
	}

	private void initScrollView() {

		tv_all.post(new Runnable() {

			@Override
			public void run() {
				/**
				 * 初始化scrollview的宽度
				 */
				titleWidth = tv_all.getWidth();
				v_scroller.getLayoutParams().width = titleWidth;
				v_scroller.setLayoutParams(v_scroller.getLayoutParams());

				/**
				 * 初始化scrollview的坐标
				 */
				int titleX = (int) ((LinearLayout) tv_all.getParent()).getX();
				firstTitleX = titleX;
				v_scroller.setX(firstTitleX + titleWidth * indexVPFragmentPosition);
			}
		});

	}

	@Override
	public boolean onBackPressed() {
		toFragmentMain();
		return true;
	}

	@Override
	public void toFragmentMain() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentOrderAll() {
		vp_content.setCurrentItem(0, true);
	}

	@Override
	public void toFragmentOrderPay() {
		vp_content.setCurrentItem(1, true);
	}

	@Override
	public void toFragmentOrderSend() {
		vp_content.setCurrentItem(3, true);
	}

	@Override
	public void toFragmentOrderReceipt() {
		vp_content.setCurrentItem(2, true);
	}

	@Override
	public void setProgressBarVisible(int visibility) {
		pb_loading.setVisibility(visibility);
	}

	@Override
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public List<Order> getOrderList() {
		return orderList;
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setVPAdapter() {
		vp_content.setOffscreenPageLimit(3);
		initFragmentList();
		vp_content.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
		vp_content.setOnPageChangeListener(new PageChangeListener());
		vp_content.setCurrentItem(indexVPFragmentPosition, false);

	}

	@Override
	public User getCurrentUser() {
		return (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			setIndexVPFragmentPositionFromCache();
			initScrollView();
			vp_content.setAdapter(null);
			vp_content.setOnPageChangeListener(null);
			orderPresenter.list();
		}
	}

	@Override
	public void setIndexVPFragmentPositionFromCache() {
		indexVPFragmentPosition = (Integer) CacheUtil.get(getActivity(), "fragmentOrder_position", 0);
	}

}
