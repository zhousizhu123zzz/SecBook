package com.zyd.view.impl;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.MData;
import com.zyd.model.User;
import com.zyd.presenter.HomePresenter;
import com.zyd.presenter.UserPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentStartView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * º”‘ÿΩÁ√Ê
 * @author Administrator
 *
 */
public class FragmentStart extends BaseFragment implements FragmentStartView, FragmentBackHandler {

	private HomePresenter homePresenter = new HomePresenter(this);
	private UserPresenter userPresenter = new UserPresenter(this);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		homePresenter.list();
		return inflater.inflate(R.layout.fragment_start, container, false);
	}

	@Override
	public boolean onBackPressed() {
		return true;
	}

	@Override
	public void toFragmentMain() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, false, true);
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setTransmissionData(Object o) {
		super.setTransmissionData(o);
	}

	@Override
	public void toFragmentLogin() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentLogin", new FragmentLogin(),
				R.id.container_base, false, true);
	}

	@Override
	public void validataUser() {
		if (CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType()) == null) {
			toFragmentLogin();
		} else {
			userPresenter.login();
			toFragmentMain();
		}
	}

	@Override
	public Activity getParentActivity() {
		return getActivity();
	}

	@Override
	public void cacheData(Object o) {
		MData data = (MData) o;
		CacheUtil.setDataList(getActivity(), "searchHotBookList", data.getBookList());
	}

	@Override
	public void cacheUser(User user) {
		CacheUtil.setEntityData(getActivity(), "currentUser", user);
	}

	@Override
	public User getCurrentUser() {
		return (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

}
