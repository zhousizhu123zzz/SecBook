package com.zyd.presenter;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.utils.JSONUtils;
import com.zyd.view.FragmentHomeView;
import com.zyd.view.FragmentStartView;

public class HomePresenter {

	private MainBiz mainBiz;
	private FragmentStartView startView;
	private FragmentHomeView homeView;

	public <T> HomePresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentStartView) {
			this.startView = (FragmentStartView) t;
		}
		if (t instanceof FragmentHomeView) {
			this.homeView = (FragmentHomeView) t;
		}
	}

	/**
	 * 遍历有关主页所有的内容
	 */
	public void list() {
		mainBiz.connect(Constant.net.NET_HOME_LIST, null, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseMData(jsonData) != null) {
					if (startView != null) {
						startView.setTransmissionData(JSONUtils.parseMData(jsonData));
						startView.cacheData(JSONUtils.parseMData(jsonData));
						startView.validataUser();
					}
					if (homeView != null) {
						homeView.setBookList(JSONUtils.parseMData(jsonData).getBookList());
						homeView.setAdsList(JSONUtils.parseMData(jsonData).getAdvertiseList());
						homeView.setRefreshing(false);
					}
				} else {
				}
			}

			@Override
			public void serverEx() {
				if (startView != null) {
					startView.showErrorMsg(Constant.msg.ERROR_SERVER);
					startView.getParentActivity().finish();
				}
				if (homeView != null) {
					homeView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
			}
		});
	}

}
