package com.zyd.presenter;

import java.util.Map;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.utils.JSONUtils;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentBookBookView;
import com.zyd.view.FragmentOrderDetailsView;

public class CouponPresenter {

	private MainBiz mainBiz;
	private FragmentBookBookView bookBookView;
	private FragmentOrderDetailsView orderDetailsView;

	public <T> CouponPresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentBookBookView) {
			this.bookBookView = (FragmentBookBookView) t;
		}
		if (t instanceof FragmentOrderDetailsView) {
			this.orderDetailsView = (FragmentOrderDetailsView) t;
		}
	}

	public void listCoupon() {
		Map<String, String> params = null;
		if (bookBookView != null) {
			params = StringUtil.getUserMap(bookBookView.getCurrentUser());
		}
		if (orderDetailsView != null) {
			params = StringUtil.getUserMap(orderDetailsView.getCurrentUser());
		}
		mainBiz.connect(Constant.net.NET_COUPON_LIST, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (bookBookView != null) {
					bookBookView.setCouponList(JSONUtils.parseCouponList(jsonData));
					bookBookView.setCouponText();
				}
				if (orderDetailsView != null) {
					orderDetailsView.setCouponList(JSONUtils.parseCouponList(jsonData));
					orderDetailsView.setCouponText();
				}
			}

			@Override
			public void serverEx() {

			}
		});
	}

}
