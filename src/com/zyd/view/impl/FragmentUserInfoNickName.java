package com.zyd.view.impl;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.User;
import com.zyd.presenter.UserPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentUserInfoNickNameView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户信息修改界面--修改昵称界面
 * @author 朱永地
 *
 */
public class FragmentUserInfoNickName extends Fragment implements FragmentUserInfoNickNameView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;
	private TextView tv_confirm;

	/**
	 * Body
	 */
	private EditText et_nickName;

	private UserPresenter userPresenter = new UserPresenter(this);

	private User currentUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_userinfo_nickname, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentUserInfoNickName_ll_back);
		tv_confirm = (TextView) rootView.findViewById(R.id.fragmentUserInfoNickName_tv_confirm);
		et_nickName = (EditText) rootView.findViewById(R.id.fragmentUserInfoNickName_et_nickName);

		setUser();
		et_nickName.setText(currentUser.getNickName());

		ll_back.setOnClickListener(new ClickListener());
		tv_confirm.setOnClickListener(new ClickListener());
		et_nickName.setOnClickListener(new ClickListener());
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

			case R.id.fragmentUserInfoNickName_ll_back:
				toFragmentUserInfo();
				break;

			case R.id.fragmentUserInfoNickName_tv_confirm:
				toFragmentUserInfo();
				break;

			case R.id.fragmentUserInfoNickName_et_nickName:

				break;

			}
		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentUserInfo();
		return true;
	}

	@Override
	public void toFragmentUserInfo() {
		currentUser.setNickName(getNickName());
		if (isInfoChanged()) {
			EqupUtil.showMyToast(getActivity(), "昵称改变了", 0, Toast.LENGTH_SHORT);
			userPresenter.save();
		}
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentUserInfo", new FragmentUserInfo(),
				R.id.container_base, true, true);
	}

	@Override
	public String getNickName() {
		return et_nickName.getText().toString();
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
		if (beforeUser.getNickName() != null && beforeUser.getNickName().equals(currentUser.getNickName()))
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
