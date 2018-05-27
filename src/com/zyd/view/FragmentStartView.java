package com.zyd.view;

import com.zyd.model.User;

import android.app.Activity;

public interface FragmentStartView extends BaseFragmentView {

	public void toFragmentMain();

	public void toFragmentLogin();

	public void validataUser();

	public void cacheData(Object o);

	public void cacheUser(User user);

	public void showErrorMsg(String errorMsg);

	public Activity getParentActivity();

	public User getCurrentUser();

}
