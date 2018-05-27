package com.zyd.presenter;

import java.util.HashMap;
import java.util.Map;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.utils.JSONUtils;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentBookBookView;
import com.zyd.view.FragmentBookView;
import com.zyd.view.FragmentOrderView;
import com.zyd.view.FragmentShoppingCartView;

import android.view.View;

public class OrderPresenter {

	private MainBiz mainBiz;
	private FragmentBookView bookView;
	private FragmentBookBookView bookBookView;
	private FragmentShoppingCartView shoppingCartView;
	private FragmentOrderView orderView;

	public <T> OrderPresenter(T t) {
		this.mainBiz = new MainBizImpl();
		if (t instanceof FragmentBookBookView) {
			this.bookBookView = (FragmentBookBookView) t;
		}
		if (t instanceof FragmentShoppingCartView) {
			this.shoppingCartView = (FragmentShoppingCartView) t;
		}
		if (t instanceof FragmentBookView) {
			this.bookView = (FragmentBookView) t;
		}
		if (t instanceof FragmentOrderView) {
			this.orderView = (FragmentOrderView) t;
		}
	}

	public void list() {
		Map<String, String> params = new HashMap<String, String>();
		if (bookBookView != null) {
			params.put("userId", String.valueOf(bookBookView.getCurrentUser().getUserId()));
		}
		if (shoppingCartView != null) {
			params.put("userId", String.valueOf(shoppingCartView.getCurrentUser().getUserId()));
		}
		if (bookView != null) {
			params.put("userId", String.valueOf(bookView.getCurrentUser().getUserId()));
		}
		if (orderView != null) {
			orderView.setProgressBarVisible(View.VISIBLE);
			params.put("userId", String.valueOf(orderView.getCurrentUser().getUserId()));
		}
		mainBiz.connect(Constant.net.NET_ORDER_LIST, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (bookBookView != null) {
					bookBookView.setOrderList(JSONUtils.parseOrderList(jsonData));
				}
				if (shoppingCartView != null) {
					shoppingCartView.setOrderList(JSONUtils.parseOrderList(jsonData));
					shoppingCartView.setLVAdapter();
				}
				if (bookView != null) {
					bookView.setOrderList(JSONUtils.parseOrderList(jsonData));
					bookView.setShoppingCartCircle();
				}
				if (orderView != null) {
					orderView.setProgressBarVisible(View.GONE);
					orderView.setOrderList(JSONUtils.parseOrderList(jsonData));
					orderView.setVPAdapter();
				}
			}

			@Override
			public void serverEx() {
				if (bookBookView != null) {
					bookBookView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (shoppingCartView != null) {
					shoppingCartView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (bookView != null) {
					bookView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (orderView != null) {
					orderView.setProgressBarVisible(View.GONE);
					orderView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
			}
		});
	}

	public void add() {
		Map<String, String> params = new HashMap<String, String>();
		if (bookView != null) {
			params = StringUtil.getOrderMap(bookView.getOrder());
		}
		mainBiz.connect(Constant.net.NET_ORDER_ADD, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseIsSuccess(jsonData)) {
					if (bookView != null) {
						bookView.showErrorMsg("加入购物车成功!");
					}
				} else {
					if (bookView != null) {
						bookView.showErrorMsg("添加失败！请检查您是否登录！");
					}
				}
			}

			@Override
			public void serverEx() {
				if (bookView != null) {
					bookView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
			}
		});
	}

}
