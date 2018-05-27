package com.zyd.view;

import java.util.List;

import com.zyd.model.Address;
import com.zyd.model.Coupon;
import com.zyd.model.User;

public interface FragmentOrderDetailsView {

	public void toFragmentBack();

	public void toFragmentAddress();

	public void toFragmentCoupon();

	public void setAddressList(List<Address> addressList);

	public void setCouponList(List<Coupon> couponList);

	public void setOrderListFromFragment();

	public void showErrorMsg(String errorMsg);

	public void setCurrentUser();

	public User getCurrentUser();

	public void setAddressText();

	public void setCouponText();

	public void setPointText();

}
