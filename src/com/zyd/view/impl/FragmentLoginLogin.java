package com.zyd.view.impl;

import com.zyd.model.User;
import com.zyd.presenter.UserPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentLoginLoginView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面
 * @author 朱永地
 *
 */
public class FragmentLoginLogin extends Fragment implements FragmentLoginLoginView {

	private View rootView;

	private EditText et_userName, et_password;

	private TextView tv_register, tv_retrieve;

	private Button btn_login;

	private UserPresenter loginPresenter = new UserPresenter(this);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_login_login, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		tv_register = (TextView) rootView.findViewById(R.id.fragmentLogin_tv_register);
		tv_retrieve = (TextView) rootView.findViewById(R.id.fragmentLogin_tv_retrievePwd);
		et_userName = (EditText) rootView.findViewById(R.id.fragmentLogin_et_userName);
		et_password = (EditText) rootView.findViewById(R.id.fragmentLogin_et_password);
		btn_login = (Button) rootView.findViewById(R.id.fragmentLogin_btn_login);

		tv_register.setOnClickListener(new CLickListener());
		tv_retrieve.setOnClickListener(new CLickListener());
		btn_login.setOnClickListener(new CLickListener());
	}

	@Override
	public void toFragmentLoginRegister() {
		FragmentController.show(getFragmentManager(), "FragmentLoginRegister", new FragmentLoginRegister(),
				R.id.container_login, false, true);
	}

	@Override
	public void toFragmentLoginRetrieve() {

	}

	@Override
	public void toFragmentMain() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, false, true);

	}

	@Override
	public String getUserName() {
		return et_userName.getText().toString();
	}

	@Override
	public String getPassword() {
		return et_password.getText().toString();
	}

	@Override
	public void showErrorInfo(String erroInfo) {
		EqupUtil.showMyToast(getActivity(), erroInfo, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void loading(int states) {
		((FragmentLogin) getParentFragment()).loading(states);
	}

	@Override
	public void saveCurrentUser(User user) {
		CacheUtil.setEntityData(getActivity(), "currentUser", user);
	}

	/**
	 * 点击事件监听内部类
	 * @author 朱永地
	 *
	 */
	private class CLickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {

			switch (view.getId()) {

			case R.id.fragmentLogin_tv_register:
				toFragmentLoginRegister();
				break;

			case R.id.fragmentLogin_tv_retrievePwd:
				toFragmentLoginRetrieve();
				break;

			case R.id.fragmentLogin_btn_login:
				loginPresenter.login();
				break;
			}
		}

	}

}
