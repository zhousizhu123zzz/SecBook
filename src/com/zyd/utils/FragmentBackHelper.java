package com.zyd.utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Fragment返回工具类
 * @author Administrator
 *
 */
public class FragmentBackHelper {

	/**
	 * 在FragmentActivity中先进行拦截
	 * @param activity
	 * @return
	 */
	public static boolean handleBackPress(FragmentActivity activity) {
		return handleBackPress(activity.getSupportFragmentManager());
	}

	/**
	 * 在Fragment中进行拦截
	 * @param fragment
	 * @return
	 */
	public static boolean handleBackPress(Fragment fragment) {
		return handleBackPress(fragment.getChildFragmentManager());
	}

	/**
	 * 遍历判断当前Activity下所有Fragment是否处理了onBackPressed
	 * @param manager
	 * @return
	 */
	public static boolean handleBackPress(FragmentManager manager) {
		List<Fragment> fragmentList = manager.getFragments(); //获取FragmentManager中所有的Fragment

		if (fragmentList == null)
			return false; //如果当前FragmentManager下没有Fragment则返回false交给Activity处理

		for (Fragment fragment : fragmentList) {
			if (isFragmentBackHandled(fragment)) //进入isFragmentBackHandled判断Fragment是否被处理,处理了返回true,不交给当前Activity处理
				return true;
		}

		return false;
	}

	/**
	 * 判断Fragment的onBackPressed中是否处理了
	 * @param fragment
	 * @return
	 */
	public static boolean isFragmentBackHandled(Fragment fragment) {
		return fragment != null && fragment.isVisible() //判断是哪个Fragment点击了返回
				&& fragment.getUserVisibleHint() //ViewPager
				&& fragment instanceof FragmentBackHandler //Fragment需要实现FragmentBackHandler接口
				&& ((FragmentBackHandler) fragment).onBackPressed(); //Fragment需要在onBackPressed返回true,即处理过了
	}
}
