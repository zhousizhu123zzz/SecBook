package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentBackHelper;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentMainView;
import com.zyd.widget.ViewBottomTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * FragmentMain包含FragmentHome、FragmentMsg、FragmentShoppingCart、FragmentMe
 * @author Administrator
 *
 */
public class FragmentMain extends Fragment implements FragmentMainView, FragmentBackHandler {

	private View rootView;

	private ViewBottomTab tab_home, tab_menu, tab_shoppingCart, tab_me;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(com.zyd.secbooks.R.layout.fragment_main, container, false);
		initView();
		toFragmentHome(); //首先进入FragmentHome主页
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		tab_home = (ViewBottomTab) rootView.findViewById(R.id.fragmentMain_tab_home);
		tab_menu = (ViewBottomTab) rootView.findViewById(R.id.fragmentMain_tab_menu);
		tab_shoppingCart = (ViewBottomTab) rootView.findViewById(R.id.fragmentMain_tab_shoppingcart);
		tab_me = (ViewBottomTab) rootView.findViewById(R.id.fragmentMain_tab_me);

		setFragmentIconAlpha("FragmentHome"); //默认为给主页图标着色

		tab_home.setOnClickListener(new ClickListener());
		tab_menu.setOnClickListener(new ClickListener());
		tab_shoppingCart.setOnClickListener(new ClickListener());
		tab_me.setOnClickListener(new ClickListener());
	}

	/**
	 * 跳转到FragmentHome
	 */
	@Override
	public void toFragmentHome() {
		FragmentController.show(getChildFragmentManager(), "FragmentHome", new FragmentHome(), R.id.container_home,
				false, false);
		setFragmentIconAlpha("FragmentHome");
	}

	/**
	 * 跳转到FragmentMsg
	 */
	@Override
	public void toFragmentMenu() {
		FragmentController.show(getChildFragmentManager(), "FragmentMenu", new FragmentMenu(), R.id.container_home,
				false, false);
		setFragmentIconAlpha("FragmentMenu");
	}

	/**
	 * 跳转到FragmentShoppingCart
	 */
	@Override
	public void toFragmentShoppingCart() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentHome");
		FragmentController.show(getChildFragmentManager(), "FragmentShoppingCart", new FragmentShoppingCart(),
				R.id.container_home, false, false);
		setFragmentIconAlpha("FragmentShoppingCart");
	}

	/**
	 * 动画跳转到FragmentShoppingCart
	 */
	@Override
	public void toFragmentShoppingCartWithAnim() {
		FragmentController.show(getChildFragmentManager(), "FragmentShoppingCart", new FragmentShoppingCart(),
				R.id.container_home, false, true);
		setFragmentIconAlpha("FragmentShoppingCart");
	}

	/**
	 * 跳转到FragmentMe
	 */
	@Override
	public void toFragmentMe() {
		FragmentController.show(getChildFragmentManager(), "FragmentMe", new FragmentMe(), R.id.container_home, false,
				false);
		setFragmentIconAlpha("FragmentMe");
	}

	/**
	 * 点击事件内部类
	 * @author Administrator
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {

			switch (view.getId()) {

			case R.id.fragmentMain_tab_home:
				toFragmentHome();
				break;

			case R.id.fragmentMain_tab_menu:
				toFragmentMenu();
				break;

			case R.id.fragmentMain_tab_shoppingcart:
				toFragmentShoppingCart();
				break;

			case R.id.fragmentMain_tab_me:
				toFragmentMe();
				break;
			}
		}

	}

	/**
	 * 重置导航栏的颜色
	 */
	private void resetIconAlpha() {
		tab_home.setIconAlpha(0);
		tab_menu.setIconAlpha(0);
		tab_shoppingCart.setIconAlpha(0);
		tab_me.setIconAlpha(0);
	}

	@Override
	public boolean onBackPressed() {
		return FragmentBackHelper.handleBackPress(this);
	}

	@Override
	public void setFragmentIconAlpha(String tag) {
		resetIconAlpha();
		if ("FragmentHome".equals(tag)) {
			tab_home.setIconAlpha(1.0f);
		}
		if ("FragmentMenu".equals(tag)) {
			tab_menu.setIconAlpha(1.0f);
		}
		if ("FragmentShoppingCart".equals(tag)) {
			tab_shoppingCart.setIconAlpha(1.0f);
		}
		if ("FragmentMe".equals(tag)) {
			tab_me.setIconAlpha(1.0f);
		}
	}

}
