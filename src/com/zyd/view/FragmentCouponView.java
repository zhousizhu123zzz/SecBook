package com.zyd.view;

public interface FragmentCouponView {

	void toFragmentMain(); //返回主界面

	void toFragmentCouponUsed();//跳转到优惠券--已使用界面

	void toFragmentCouponUnused(); //跳转到优惠券--未使用界面

	void toFragmentCouponExpired(); //跳转到优惠券--已过期界面

}
