package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentBackHelper;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentCouponView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 优惠券界面
 * @author 朱永地
 *
 */
public class FragmentCoupon extends Fragment implements FragmentCouponView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;
	private TextView tv_unused, tv_used, tv_expired;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_coupon, container, false);
		initView();
		toFragmentCouponUnused();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentCoupon_ll_back);
		tv_unused = (TextView) rootView.findViewById(R.id.fragmentCoupon_tv_unused);
		tv_used = (TextView) rootView.findViewById(R.id.fragmentCoupon_tv_used);
		tv_expired = (TextView) rootView.findViewById(R.id.fragmentCoupon_tv_expired);

		ll_back.setOnClickListener(new ClickListener());
		tv_unused.setOnClickListener(new ClickListener());
		tv_used.setOnClickListener(new ClickListener());
		tv_expired.setOnClickListener(new ClickListener());
	}

	/**
	 * 点击事件内部类
	 * @author 朱永地
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.fragmentCoupon_ll_back:
				toFragmentMain();
				break;

			case R.id.fragmentCoupon_tv_used:
				toFragmentCouponUsed();
				break;

			case R.id.fragmentCoupon_tv_unused:
				toFragmentCouponUnused();
				break;

			case R.id.fragmentCoupon_tv_expired:
				toFragmentCouponExpired();
				break;

			}
		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentMain();
		return true;
	}

	@Override
	public void toFragmentMain() {
		String fragmentBackTag = (String) CacheUtil.get(getActivity(), "fragmentBackTag", "");
		if (null == fragmentBackTag) {
			fragmentBackTag = "FragmentMain";
		}
		Fragment fragment = FragmentController.getFragmentByTag(getActivity().getSupportFragmentManager(),
				fragmentBackTag);
		FragmentController.show(getActivity().getSupportFragmentManager(), fragmentBackTag, fragment,
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentCouponUsed() {

	}

	@Override
	public void toFragmentCouponUnused() {
		FragmentController.show(getChildFragmentManager(), "FragmentCouponUnused", new FragmentCouponUnused(),
				R.id.container_coupon, false, false);
	}

	@Override
	public void toFragmentCouponExpired() {

	}

}
