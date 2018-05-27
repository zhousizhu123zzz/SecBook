package com.zyd.presenter;

import java.util.HashMap;
import java.util.Map;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.model.User;
import com.zyd.utils.JSONUtils;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentCropImageView;
import com.zyd.view.FragmentLoginLoginView;
import com.zyd.view.FragmentStartView;
import com.zyd.view.FragmentUserInfoNickNameView;
import com.zyd.view.FragmentUserInfoSexView;
import com.zyd.view.FragmentUserInfoView;

public class UserPresenter {

	private FragmentLoginLoginView loginView;
	private FragmentUserInfoView userInfoView;
	private FragmentUserInfoSexView userInfoSexView;
	private FragmentUserInfoNickNameView userInfoNickNameView;
	private FragmentCropImageView cropImageView;
	private FragmentStartView startView;

	private MainBiz mainBiz;

	public <T> UserPresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentLoginLoginView) {
			this.loginView = (FragmentLoginLoginView) t;
		}
		if (t instanceof FragmentUserInfoView) {
			this.userInfoView = (FragmentUserInfoView) t;
		}
		if (t instanceof FragmentUserInfoSexView) {
			this.userInfoSexView = (FragmentUserInfoSexView) t;
		}
		if (t instanceof FragmentUserInfoNickNameView) {
			this.userInfoNickNameView = (FragmentUserInfoNickNameView) t;
		}
		if (t instanceof FragmentCropImageView) {
			this.cropImageView = (FragmentCropImageView) t;
		}
		if (t instanceof FragmentStartView) {
			this.startView = (FragmentStartView) t;
		}
	}

	/**
	 * 登录
	 */
	public void login() {
		Map<String, String> params = new HashMap<String, String>();
		if (loginView != null) {
			params.put("userName", loginView.getUserName());
			params.put("password", loginView.getPassword());
		}
		if (startView != null) {
			params.put("userName", startView.getCurrentUser().getUserName());
			params.put("password", startView.getCurrentUser().getPassword());
		}
		mainBiz.connect(Constant.net.NET_USER_LOGIN, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				User resultUser = JSONUtils.parseUser(jsonData);
				if (loginView != null) {
					loginView.loading(-1); //关闭进度条
					if (resultUser == null) {
						loginView.showErrorInfo(Constant.msg.ERROR_USERNAME_PASSWORD);
					} else {
						loginView.saveCurrentUser(resultUser);
						loginView.toFragmentMain();
					}
				}
				if (startView != null) {
					if (resultUser != null) {
						startView.cacheUser(resultUser);
					}
				}
			}

			@Override
			public void serverEx() {
				if (loginView != null) {
					loginView.loading(-1); //关闭进度条
					loginView.showErrorInfo(Constant.msg.ERROR_SERVER);
				}
			}

		});
	}

	/**
	 * 保存用户信息
	 */
	public void save() {
		Map<String, String> params = null;
		if (userInfoSexView != null) {
			if (userInfoSexView.isInfoChanged()) {
				params = StringUtil.getUserMap(userInfoSexView.getUser());
			} else {
				return;
			}
		} else if (userInfoNickNameView != null) {
			if (userInfoNickNameView.isInfoChanged()) {
				params = StringUtil.getUserMap(userInfoNickNameView.getUser());
			} else {
				return;
			}
		} else if (userInfoView != null) {
			params = StringUtil.getUserMap(userInfoView.getCurrentUser());
		} else if (cropImageView != null) {
			params = StringUtil.getUserMap(cropImageView.getCurrentUser());
		} else {
			return;
		}
		mainBiz.connect(Constant.net.NET_USER_SAVE, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseIsSuccess(jsonData)) {
					if (userInfoSexView != null) {
						userInfoSexView.saveUser();
						userInfoSexView.notifyFragmentUserInfo();
					}
					if (userInfoNickNameView != null) {
						userInfoNickNameView.saveUser();
						userInfoNickNameView.notifyFragmentUserInfo();
					}
					if (userInfoView != null) {
						userInfoView.saveUser();
					}
					if (cropImageView != null) {
						cropImageView.saveUser();
					}
				}
			}

			@Override
			public void serverEx() {
				if (userInfoSexView != null) {
					userInfoSexView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (userInfoNickNameView != null) {
					userInfoNickNameView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (userInfoView != null) {
					userInfoView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (cropImageView != null) {
					cropImageView.showErrorMsg("上传失败,请重试!");
					cropImageView.toFragmentUserInfo();
				}
			}
		});
	}

}
