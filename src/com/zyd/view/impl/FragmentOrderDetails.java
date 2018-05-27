package com.zyd.view.impl;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.Address;
import com.zyd.model.Coupon;
import com.zyd.model.Order;
import com.zyd.model.User;
import com.zyd.presenter.AddressPresenter;
import com.zyd.presenter.CouponPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentOrderDetailsView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOrderDetails extends Fragment implements FragmentOrderDetailsView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * Body
	 */
	private TextView tv_address, tv_coupon, tv_point;
	private TextView tv_allCost, tv_buy;

	/**
	 * Data
	 */
	private List<Address> addressList;
	private List<Coupon> couponList;
	private List<Order> orderList;
	private User currentUser;
	private AddressPresenter addressPresenter = new AddressPresenter(this);
	private CouponPresenter couponPresenter = new CouponPresenter(this);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_orderdetails, container, false);
		initView();
		setCurrentUser();
		addressPresenter.listAddress();
		couponPresenter.listCoupon();
		setPointText();
		setOrderListFromFragment();
		return rootView;
	}

	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentOrderDetails_ll_back);
		tv_address = (TextView) rootView.findViewById(R.id.fragmentOrderDetails_tv_address);
		tv_coupon = (TextView) rootView.findViewById(R.id.fragmentOrderDetails_tv_coupon);
		tv_point = (TextView) rootView.findViewById(R.id.fragmentOrderDetails_tv_point);
		tv_allCost = (TextView) rootView.findViewById(R.id.fragmentOrderDetails_tv_allCoas);
		tv_buy = (TextView) rootView.findViewById(R.id.fragmentOrderDetails_tv_buy);

		ll_back.setOnClickListener(new ClickListener());
		tv_address.setOnClickListener(new ClickListener());
		tv_coupon.setOnClickListener(new ClickListener());
		tv_point.setOnClickListener(new ClickListener());
		tv_buy.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentOrderDetails_tv_address:
				toFragmentAddress();
				break;

			case R.id.fragmentOrderDetails_tv_coupon:
				toFragmentCoupon();
				break;

			case R.id.fragmentOrderDetails_tv_point:
				double point = Double.valueOf(tv_point.getText().toString());
				if (point == 0) {
					showErrorMsg("您的积分为0！");
				} else {
					showErrorMsg("您的积分为" + point + "!");
				}
				break;

			case R.id.fragmentOrderDetails_ll_back:
				toFragmentBack();
				break;

			case R.id.fragmentOrderDetails_rl_buy:
				double allCost = Double.valueOf(tv_allCost.getText().toString());
				double balance = Double.valueOf(currentUser.getBalance());
				if (balance < allCost) {
					showErrorMsg("余额不足，请充值!");
				} else {
					showErrorMsg("购买成功!");
				}
				break;

			}
		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentBack();
		return true;
	}

	@Override
	public void toFragmentBack() {
		String fragmentBackTag = (String) CacheUtil.get(getActivity(), "fragmentBackTag", "");
		if ("FragmentBook".equals(fragmentBackTag)) {
			FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentBook", new FragmentBook(),
					R.id.container_base, true, true);
		} else {
			FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
					R.id.container_base, true, true);
		}
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentMain");
	}

	@Override
	public void setOrderListFromFragment() {
		String fragmentBackTag = (String) CacheUtil.get(getActivity(), "fragmentBackTag", "");
		if ("FragmentBook".equals(fragmentBackTag)) {
			FragmentBook fragmentBook = (FragmentBook) FragmentController
					.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentBook");
			orderList = fragmentBook.getOrderListToFragmentOrderDetails();
			double allCost = 0;
			for (int i = 0; i < orderList.size(); i++) {
				Double cost = Double.valueOf(orderList.get(i).getCost());
				allCost += cost;
			}
			Double point = Double.valueOf(currentUser.getPoint());
			tv_allCost.setText("还需支付:" + String.valueOf(allCost - point));
		} else {
			FragmentMain fragmentMain = (FragmentMain) FragmentController
					.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentMain");
			if (fragmentMain != null) {
				FragmentShoppingCart fragmentShoppingCart = (FragmentShoppingCart) FragmentController
						.getFragmentByTag(fragmentMain.getChildFragmentManager(), "FragmentShoppingCart");
				orderList = fragmentShoppingCart.getOrderList();
				double allCost = 0;
				for (int i = 0; i < orderList.size(); i++) {
					Double cost = Double.valueOf(orderList.get(i).getCost());
					allCost += cost;
				}
				Double point = Double.valueOf(currentUser.getPoint());
				tv_allCost.setText("还需支付:" + String.valueOf(allCost - point));
			}
		}
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void toFragmentAddress() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentOrderDetails");
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentAddress", new FragmentAddress(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentCoupon() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentOrderDetails");
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentCoupon", new FragmentCoupon(),
				R.id.container_base, false, true);
	}

	@Override
	public void setCurrentUser() {
		this.currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	@Override
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	@Override
	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}

	@Override
	public void setAddressText() {
		if (addressList != null && addressList.size() != 0) {
			if (addressList.size() == 1) {
				Address address = addressList.get(0);
				tv_address.setText(String.valueOf(address.getAddress()));
			} else {
				for (int i = 0; i < addressList.size(); i++) {
					Address address = new Address();
					if (address.isIndex()) {
						tv_address.setText(String.valueOf(address.getAddress()));
						return;
					}
					if (i == addressList.size() - 1) {
						tv_address.setText(String.valueOf(address.getAddress()));
					}
				}
			}
		} else {
			tv_address.setText("无收货地址，请添加收货地址!");
		}
	}

	@Override
	public void setCouponText() {
		if (couponList != null && couponList.size() != 0) {
			tv_coupon.setText("优惠:   " + couponList.get(0).getCouponPrice());
		} else {
			tv_coupon.setText("无可用优惠券");
		}
	}

	@Override
	public void setPointText() {
		if (currentUser != null) {
			tv_point.setText("优惠:   " + currentUser.getPoint());
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			setCurrentUser();
			addressPresenter.listAddress();
			couponPresenter.listCoupon();
			setPointText();
			setOrderListFromFragment();
		}
	}

}
