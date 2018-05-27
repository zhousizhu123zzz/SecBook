package com.zyd.presenter;

import java.util.List;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.model.Advertise;
import com.zyd.utils.JSONUtils;
import com.zyd.view.FragmentHomeView;
import com.zyd.view.FragmentStartView;

public class AdvertisePresenter {

	private MainBiz mainBiz;
	private FragmentHomeView fragmentHomeView;
	private FragmentStartView fragmentStartView;

	public <T> AdvertisePresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentHomeView) {
			this.fragmentHomeView = (FragmentHomeView) t;
		}
		if (t instanceof FragmentStartView) {
			this.fragmentStartView = (FragmentStartView) t;
		}
	}

	public void list() {
		mainBiz.connect(Constant.net.NET_ADVERTISE_LIST, null, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				List<Advertise> dataList = JSONUtils.parseAdsList(jsonData);
				if (dataList != null) {
					if (fragmentHomeView != null) {
						fragmentHomeView.setAdsList(dataList);
						fragmentHomeView.initBanner();
					}
					if (fragmentStartView != null) {
						fragmentStartView.setTransmissionData(dataList);
					}
				}
			}

			@Override
			public void serverEx() {

			}
		});
	}

}
