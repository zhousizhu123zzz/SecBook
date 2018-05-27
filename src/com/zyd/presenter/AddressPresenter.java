package com.zyd.presenter;

import java.util.Map;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.utils.JSONUtils;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentAddressAddView;
import com.zyd.view.FragmentAddressView;
import com.zyd.view.FragmentBookBookView;
import com.zyd.view.FragmentOrderDetailsView;

import android.view.View;

public class AddressPresenter {

	private MainBiz mainBiz;
	private FragmentAddressView addressView;
	private FragmentAddressAddView addView;
	private FragmentBookBookView fragmentBookBookView;
	private FragmentOrderDetailsView orderDetailsView;

	public <T> AddressPresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentAddressAddView) {
			addView = (FragmentAddressAddView) t;
		}
		if (t instanceof FragmentAddressView) {
			addressView = (FragmentAddressView) t;
		}
		if (t instanceof FragmentBookBookView) {
			fragmentBookBookView = (FragmentBookBookView) t;
		}
		if (t instanceof FragmentOrderDetailsView) {
			orderDetailsView = (FragmentOrderDetailsView) t;
		}
	}

	/**
	 * 遍历所有收货地址
	 */
	public void listAddress() {
		Map<String, String> params = null;
		if (addressView != null) {
			addressView.setProgressBarVisible(View.VISIBLE);
			params = StringUtil.getUserMap(addressView.getCurrentUser());
		}
		if (fragmentBookBookView != null) {
			params = StringUtil.getUserMap(fragmentBookBookView.getCurrentUser());
		}
		if (orderDetailsView != null) {
			params = StringUtil.getUserMap(orderDetailsView.getCurrentUser());
		}
		mainBiz.connect(Constant.net.NET_ADDRESS_LIST, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (addressView != null) {
					addressView.setProgressBarVisible(View.GONE);
					addressView.setAddressList(JSONUtils.parseAddressList(jsonData));
					addressView.setViewText();
				}
				if (fragmentBookBookView != null) {
					fragmentBookBookView.setAddressList(JSONUtils.parseAddressList(jsonData));
					fragmentBookBookView.setAddressText();
				}
				if (orderDetailsView != null) {
					orderDetailsView.setAddressList(JSONUtils.parseAddressList(jsonData));
					orderDetailsView.setAddressText();
				}
			}

			@Override
			public void serverEx() {
				if (addressView != null) {
					addressView.setProgressBarVisible(View.GONE);
				}
			}
		});
	}

	/**
	 * 添加收货地址
	 */
	public void save() {
		mainBiz.connect(Constant.net.NET_ADDRESS_SAVE, StringUtil.getAddressMap(addView.getUploadAdress()),
				new MainListener() {

					@Override
					public <T> void success(String jsonData) {
						if (JSONUtils.parseIsSuccess(jsonData)) {
							addView.showErrorMsg(Constant.msg.SUCCESS_SAVE);
							addView.toFragmentAddress();
						} else {
							addView.showErrorMsg(Constant.msg.ERROR_SAVE);
						}
					}

					@Override
					public void serverEx() {
						addView.showErrorMsg(Constant.msg.ERROR_SERVER);
					}
				});
	}

	public void delete() {
		mainBiz.connect(Constant.net.NET_ADDRESS_DELETE, StringUtil.getAddressMap(addressView.getDeleteAddress()),
				new MainListener() {

					@Override
					public <T> void success(String jsonData) {
						if (JSONUtils.parseIsSuccess(jsonData)) {
							addressView.refresh();
							addressView.showErrorMsg(Constant.msg.SUCCESS_DELETE);
						} else {
							addressView.showErrorMsg(Constant.msg.ERROR_DELETE);
						}
					}

					@Override
					public void serverEx() {
						addressView.showErrorMsg(Constant.msg.ERROR_SERVER);
					}
				});
	}

}
