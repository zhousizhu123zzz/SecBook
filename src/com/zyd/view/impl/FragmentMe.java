package com.zyd.view.impl;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.constant.Constant;
import com.zyd.model.Book;
import com.zyd.model.MData;
import com.zyd.model.User;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentMeView;
import com.zyd.widget.CircleImageView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的界面
 * @author 朱永地
 *
 */
public class FragmentMe extends BaseFragment implements FragmentMeView, FragmentBackHandler {

	private View rootView;

	private LinearLayout ll_userInfo, ll_grade;

	private CircleImageView circleImageView_userPic;

	@SuppressWarnings("unused")
	private TextView tv_userName, tv_grade, tv_gradeName, tv_point, tv_balance, tv_interest, tv_myStore, tv_coupon;

	private LinearLayout ll_point; //积分-上
	private LinearLayout ll_balance; //余额-下
	private LinearLayout ll_interest;//关注
	private LinearLayout ll_myStore;

	private TextView tv_viewMoreOrders;//查看更多订单
	private LinearLayout ll_orderAll; //全部订单
	private LinearLayout ll_wait4pay; //待付款
	private LinearLayout ll_wait4Diliver; //待发货
	private LinearLayout ll_wait4receipt; //待收货

	private RelativeLayout rl_coupon; //优惠券
	private RelativeLayout rl_address; //地址

	private User currentUser;

	private Dialog dialog_isToLogin; //是否前往登录的提示框

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_me, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		ll_userInfo = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_userInfo);
		ll_grade = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_grade);
		tv_userName = (TextView) rootView.findViewById(R.id.fragmentMe_tv_userName);
		tv_grade = (TextView) rootView.findViewById(R.id.fragmentMe_tv_grade);
		circleImageView_userPic = (CircleImageView) rootView.findViewById(R.id.fragmentMe_circleImageView_userPic);
		tv_gradeName = (TextView) rootView.findViewById(R.id.fragmentMe_tv_gradeName);
		tv_point = (TextView) rootView.findViewById(R.id.fragmentMe_tv_point);
		tv_balance = (TextView) rootView.findViewById(R.id.fragmentMe_tv_balance);
		tv_interest = (TextView) rootView.findViewById(R.id.fragmentMe_tv_interest);
		tv_myStore = (TextView) rootView.findViewById(R.id.fragmentMe_tv_myStore);
		tv_coupon = (TextView) rootView.findViewById(R.id.fragmentMe_tv_coupon);
		ll_point = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_point);
		ll_balance = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_balance);
		ll_interest = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_interest);
		ll_myStore = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_myStore);
		tv_viewMoreOrders = (TextView) rootView.findViewById(R.id.fragmentMe_tv_viewMoreOrders);
		ll_wait4Diliver = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_wait4diliver);
		ll_orderAll = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_orderAll);
		ll_wait4pay = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_wait4pay);
		ll_wait4receipt = (LinearLayout) rootView.findViewById(R.id.fragmentMe_ll_wait4receipt);
		rl_coupon = (RelativeLayout) rootView.findViewById(R.id.fragmentMe_rl_coupon);
		rl_address = (RelativeLayout) rootView.findViewById(R.id.fragmentMe_rl_address);

		setUser(); //从缓存中获取当前用户信息
		validateLogin(); //验证是否登录

	}

	/**
	 * 点击事件内部类--未登录状态
	 * @author 朱永地
	 *
	 */
	private class NotLoginedClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.fragmentMe_ll_userInfo:
				toFragmentLogin();
				break;

			case R.id.fragmentMe_ll_point:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_balance:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_interest:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_myStore:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_tv_viewMoreOrders:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_wait4pay:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_orderAll:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_wait4diliver:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_ll_wait4receipt:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_rl_coupon:
				showIsToLoginDialog();
				break;

			case R.id.fragmentMe_rl_address:
				showIsToLoginDialog();
				break;

			case R.id.viewer_tv_toLogin:
				dialog_isToLogin.cancel();
				toFragmentLogin();
				break;

			case R.id.viewer_tv_notToLogin:
				dialog_isToLogin.cancel();
				break;
			}
		}

	}

	/**
	 * 点击事件内部类--已登录状态
	 * @author 朱永地
	 *
	 */
	private class LoginedCLickListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.fragmentMe_ll_userInfo:
				toFragmentUserInfo();
				break;
			case R.id.fragmentMe_ll_interest:
				toFragmentCollection();
				break;
			case R.id.fragmentMe_rl_coupon:
				toFragmentCoupon();
				break;
			case R.id.fragmentMe_rl_address:
				toFragmentAddress();
				break;
			case R.id.fragmentMe_ll_myStore:
				toFragmentMyStore();
				break;
			case R.id.fragmentMe_tv_viewMoreOrders:
				CacheUtil.put(getActivity(), "fragmentOrder_position", 0);
				toFragmentOrder();
				break;
			case R.id.fragmentMe_ll_wait4pay:
				CacheUtil.put(getActivity(), "fragmentOrder_position", 1);
				toFragmentOrder();
				break;
			case R.id.fragmentMe_ll_wait4diliver:
				CacheUtil.put(getActivity(), "fragmentOrder_position", 2);
				toFragmentOrder();
				break;
			case R.id.fragmentMe_ll_wait4receipt:
				CacheUtil.put(getActivity(), "fragmentOrder_position", 3);
				toFragmentOrder();
				break;
			case R.id.fragmentMe_ll_orderAll:
				CacheUtil.put(getActivity(), "fragmentOrder_position", 0);
				toFragmentOrder();
				break;
			}
		}
	}

	@Override
	public boolean onBackPressed() {
		toFragmentHome();
		return true;
	}

	@Override
	public void toFragmentHome() {
		FragmentController.show(getFragmentManager(), "FragmentHome", new FragmentHome(), R.id.container_home, true,
				true);
		((FragmentMain) getParentFragment()).setFragmentIconAlpha("FragmentHome");
	}

	@Override
	public void toFragmentUserInfo() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentUserInfo", new FragmentUserInfo(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentCoupon() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentCoupon", new FragmentCoupon(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentLogin() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentLogin", new FragmentLogin(),
				R.id.container_base, false, true);
	}

	@Override
	public void validateLogin() {
		if (currentUser == null) {
			ll_grade.setVisibility(View.GONE);
			tv_userName.setText(Constant.msg.ERROR_NOLOGIN);

			ll_userInfo.setOnClickListener(new NotLoginedClickListener());
			ll_point.setOnClickListener(new NotLoginedClickListener());
			ll_balance.setOnClickListener(new NotLoginedClickListener());
			ll_interest.setOnClickListener(new NotLoginedClickListener());
			ll_myStore.setOnClickListener(new NotLoginedClickListener());
			tv_viewMoreOrders.setOnClickListener(new NotLoginedClickListener());
			ll_wait4Diliver.setOnClickListener(new NotLoginedClickListener());
			ll_orderAll.setOnClickListener(new NotLoginedClickListener());
			ll_wait4pay.setOnClickListener(new NotLoginedClickListener());
			ll_wait4receipt.setOnClickListener(new NotLoginedClickListener());
			rl_coupon.setOnClickListener(new NotLoginedClickListener());
			rl_address.setOnClickListener(new NotLoginedClickListener());
		} else {
			MData mData = (MData) CacheUtil.getEntityData(getActivity(),
					"collection_bookList" + currentUser.getUserId(), new TypeToken<MData>() {
					}.getType());
			List<Integer> collectionBookList = null;
			if (mData != null) {
				collectionBookList = mData.getIntegerList();
				if (collectionBookList != null && collectionBookList.size() != 0) {
					tv_interest.setText(String.valueOf(collectionBookList.size()));
					ll_interest.setOnClickListener(new LoginedCLickListener());
				} else {
					ll_interest.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							EqupUtil.showMyToast(getActivity(), "您还没有任何收藏呢！", 0, Toast.LENGTH_SHORT);
						}
					});
				}
			} else {
				ll_interest.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						EqupUtil.showMyToast(getActivity(), "您还没有任何收藏呢！", 0, Toast.LENGTH_SHORT);
					}
				});
			}
			if (currentUser.getUserPic() != null) {
				setUserPic();
			}
			tv_userName.setText(currentUser.getUserName());
			tv_grade.setText(String.valueOf(currentUser.getGrade()));
			tv_gradeName.setText(currentUser.getGradeName());
			tv_point.setText(currentUser.getPoint());
			tv_balance.setText(currentUser.getBalance());
			List<Book> bookList = currentUser.getBookList();
			if (bookList != null) {
				tv_myStore.setText(String.valueOf(bookList.size()));
			}
			ll_wait4pay.setOnClickListener(new LoginedCLickListener());
			ll_wait4Diliver.setOnClickListener(new LoginedCLickListener());
			ll_wait4receipt.setOnClickListener(new LoginedCLickListener());
			tv_viewMoreOrders.setOnClickListener(new LoginedCLickListener());
			ll_orderAll.setOnClickListener(new LoginedCLickListener());
			ll_myStore.setOnClickListener(new LoginedCLickListener());
			ll_userInfo.setOnClickListener(new LoginedCLickListener());
			rl_coupon.setOnClickListener(new LoginedCLickListener());
			rl_address.setOnClickListener(new LoginedCLickListener());
		}
	}

	@Override
	public void setUser() {
		this.currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public User getUser() {
		return (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public void showIsToLoginDialog() {
		dialog_isToLogin = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
		View dialogContentView = View.inflate(getActivity(), R.layout.viewer_tologin, null);
		dialogContentView.findViewById(R.id.viewer_tv_toLogin).setOnClickListener(new NotLoginedClickListener());
		dialogContentView.findViewById(R.id.viewer_tv_notToLogin).setOnClickListener(new NotLoginedClickListener());

		dialog_isToLogin.setContentView(dialogContentView);
		Window dialogWindow = dialog_isToLogin.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		dialog_isToLogin.show();
	}

	@Override
	public void toFragmentAddress() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentMain");
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentAddress", new FragmentAddress(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentLoading() {
		super.toFragmentLoading();
	}

	@Override
	public void toFragmentServerEx() {
		super.toFragmentServerEx();
	}

	@Override
	public void toFragmentNoNet() {
		super.toFragmentNoNet();
	}

	@Override
	public void setTransmissionData(Object o) {
		super.setTransmissionData(o);
	}

	@Override
	public Object getTransmissionData() {
		return super.getTransmissionData();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			setUser();
			validateLogin();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			setUser();
			validateLogin();
		}
	}

	private void setUserPic() {
		if (currentUser != null && currentUser.getUserPic() != null) {
			Picasso.with(getActivity()).load(currentUser.getUserPic()).into(circleImageView_userPic);
		}
	}

	@Override
	public void toFragmentMyStore() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMyStore", new FragmentMyStore(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentCollection() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentCollection",
				new FragmentCollection(), R.id.container_base, false, true);
	}

	@Override
	public void toFragmentOrder() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentOrder", new FragmentOrder(),
				R.id.container_base, false, true);
	}

}
