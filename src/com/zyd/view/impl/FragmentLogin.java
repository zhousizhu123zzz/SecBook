package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentBackHelper;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentLoginView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class FragmentLogin extends Fragment implements FragmentLoginView, FragmentBackHandler {

	private View rootView;

	private ProgressBar pb_loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_login, container, false);
		initView();
		toFragmentLoginLogin();
		return rootView;
	}

	/**
	 * ≥ı ºªØView
	 */
	private void initView() {
		pb_loading = (ProgressBar) rootView.findViewById(R.id.fragmentLogin_pb_loading);
	}

	@Override
	public boolean onBackPressed() {
		return FragmentBackHelper.handleBackPress(this);
	}

	@Override
	public void toFragmentLoginLogin() {
		FragmentController.show(getChildFragmentManager(), "FragmentLoginLogin", new FragmentLoginLogin(),
				R.id.container_login, false, false);
	}

	@Override
	public void loading(int states) {
		if (states == 1) {
			pb_loading.setVisibility(View.VISIBLE);
		} else {
			pb_loading.setVisibility(View.GONE);
		}
	}

}
