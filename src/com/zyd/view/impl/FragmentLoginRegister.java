package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentloginRegisterView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 注册界面
 * @author 朱永地
 *
 */
public class FragmentLoginRegister extends Fragment implements FragmentloginRegisterView, FragmentBackHandler {

	private View rootView;

	private RelativeLayout rl_back2Login;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_login_register, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		rl_back2Login = (RelativeLayout) rootView.findViewById(R.id.fragmentLoginRegister_rl_back2Login);

		rl_back2Login.setOnClickListener(new CLickListener());
	}

	@Override
	public void toFragmentLoginLogin() {
		FragmentController.show(getFragmentManager(), "FragmentLoginLogin", new FragmentLoginLogin(),
				R.id.container_login, true, true);
	}

	@Override
	public boolean onBackPressed() {
		toFragmentLoginLogin();
		return true;
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

			case R.id.fragmentLoginRegister_rl_back2Login:
				toFragmentLoginLogin();
				break;

			}
		}

	}
}
