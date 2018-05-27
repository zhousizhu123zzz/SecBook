package com.zyd.view.impl;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.User;
import com.zyd.presenter.UserPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentUserInfoSexView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 修改用户信息界面--修改性别界面
 * @author 朱永地
 *
 */
public class FragmentUserInfoSex extends Fragment implements FragmentUserInfoSexView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * Body
	 */
	private RelativeLayout rl_male, rl_female, rl_secret;
	private ImageView iv_selected_male, iv_selected_female, iv_selected_secret;

	private User currentUser;

	private UserPresenter userPresenter = new UserPresenter(this);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_userinfo_sex, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentUserInfoSex_ll_back);
		rl_male = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfoSex_rl_male);
		rl_female = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfoSex_rl_female);
		rl_secret = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfoSex_rl_secret);
		iv_selected_male = (ImageView) rootView.findViewById(R.id.fragmentUserInfoSex_iv_selected_male);
		iv_selected_female = (ImageView) rootView.findViewById(R.id.fragmentUserInfoSex_iv_selected_female);
		iv_selected_secret = (ImageView) rootView.findViewById(R.id.fragmentUserInfoSex_iv_selected_secret);

		setUser();
		initSelectedIcon();

		ll_back.setOnClickListener(new ClickListener());
		rl_male.setOnClickListener(new ClickListener());
		rl_female.setOnClickListener(new ClickListener());
		rl_secret.setOnClickListener(new ClickListener());
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

			case R.id.fragmentUserInfoSex_ll_back:
				toFragmentUserInfo();
				break;

			case R.id.fragmentUserInfoSex_rl_male:
				resetSelectedIcon();
				iv_selected_male.setVisibility(View.VISIBLE);
				currentUser.setSex("男");
				break;

			case R.id.fragmentUserInfoSex_rl_female:
				resetSelectedIcon();
				iv_selected_female.setVisibility(View.VISIBLE);
				currentUser.setSex("女");
				break;

			case R.id.fragmentUserInfoSex_rl_secret:
				resetSelectedIcon();
				iv_selected_secret.setVisibility(View.VISIBLE);
				currentUser.setSex("保密");
				break;
			}
		}

	}

	@Override
	public void toFragmentUserInfo() {
		userPresenter.save();
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentUserInfo", new FragmentUserInfo(),
				R.id.container_base, true, true);
	}

	@Override
	public boolean onBackPressed() {
		toFragmentUserInfo();
		return true;
	}

	/**
	 * 初始化图标
	 */
	public void initSelectedIcon() {
		iv_selected_male.setVisibility(View.INVISIBLE);
		iv_selected_female.setVisibility(View.INVISIBLE);
		iv_selected_secret.setVisibility(View.INVISIBLE);
		if ("男".equals(currentUser.getSex())) {
			iv_selected_male.setVisibility(View.VISIBLE);
		} else if ("女".equals(currentUser.getSex())) {
			iv_selected_female.setVisibility(View.VISIBLE);
		} else if ("保密".equals(currentUser.getSex())) {
			iv_selected_secret.setVisibility(View.VISIBLE);
		} else {
			iv_selected_male.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 重置所有的选中图标
	 */
	private void resetSelectedIcon() {
		iv_selected_male.setVisibility(View.INVISIBLE);
		iv_selected_female.setVisibility(View.INVISIBLE);
		iv_selected_secret.setVisibility(View.INVISIBLE);
	}

	@Override
	public void setUser() {
		this.currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public User getUser() {
		return currentUser;
	}

	@Override
	public void saveUser() {
		CacheUtil.setEntityData(getActivity(), "currentUser", currentUser);
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public boolean isInfoChanged() {
		User beforeUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
		if (beforeUser.getSex() != null && beforeUser.getSex().equals(currentUser.getSex()))
			return false;
		return true;
	}

	@Override
	public void notifyFragmentUserInfo() {
		FragmentUserInfo fragmentUserInfo = (FragmentUserInfo) FragmentController
				.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentUserInfo");
		fragmentUserInfo.onHiddenChanged(false);
	}

}
