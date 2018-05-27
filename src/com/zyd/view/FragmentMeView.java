package com.zyd.view;

import com.zyd.model.User;

public interface FragmentMeView extends BaseFragmentView {

	public void toFragmentHome(); //跳转到主页界面

	public void toFragmentUserInfo(); //跳转到用户信息修改界面

	public void toFragmentCoupon(); //跳转到优惠券界面

	public void toFragmentAddress(); //跳转到地址管理界面

	public void toFragmentLogin(); //未登录，跳转到登录界面

	public void toFragmentMyStore(); //跳转到我的店铺界面

	public void toFragmentCollection();//跳转到我的收藏界面

	public void toFragmentOrder(); //跳转到我的订单界面

	public void validateLogin(); //验证是否登录

	public void setUser(); //获取用户信息,从缓存中获取

	public void showIsToLoginDialog(); //显示是否前往登录的提示框 

	public User getUser(); //从缓存中获取用户数据，作为跳转其他Fragment的参数

}
