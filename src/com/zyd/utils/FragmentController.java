package com.zyd.utils;

import com.zyd.secbooks.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * FragmentController
 * @author 朱永地
 *
 */
public class FragmentController {

	/**
	 * 根据Tag获取指定的Fragment
	 * @param manager
	 * @param tag
	 * @return
	 */
	public static Fragment getFragmentByTag(FragmentManager manager, String tag) {
		if (manager.getFragments() != null) {
			for (Fragment fragment : manager.getFragments()) {
				if (tag.equals(fragment.getTag())) {
					return fragment;
				}
			}
		}
		return null;
	}

	/**
	 * 移除指定的Fragment
	 * @param manager
	 * @param tag
	 */
	public static void removeFragment(FragmentManager manager, String tag) {
		Fragment fragment = getFragmentByTag(manager, tag);
		if (fragment != null) {
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.remove(fragment);
			transaction.commitAllowingStateLoss();
		}
	}

	/**
	 * 获取当前显示的Fragment
	 * @param manager
	 * @return
	 */
	public static Fragment getFragmentVisible(FragmentManager manager) {
		if (manager.getFragments() != null) {
			for (Fragment fragment : manager.getFragments()) {
				if (fragment.isVisible()) {
					return fragment;
				}
			}
		}
		return null;
	}

	/**
	 * 显示Fragment
	 * @param manager
	 * @param hideTag
	 * @param showTag
	 * @param fragment
	 * @param containerId
	 * @param isBack
	 */
	public static void show(FragmentManager manager, String showTag, Fragment fragment, int containerId, boolean isBack,
			boolean isAnimated) {
		Fragment hideFragment = getFragmentVisible(manager);
		Fragment showFragment = manager.findFragmentByTag(showTag);
		/**
		 * 第一次进入Fragment
		 */
		if (showFragment == null) {
			/**
			 * 非第一次进入程序
			 */
			if (hideFragment != null) {
				if (isAnimated) {
					manager.beginTransaction()
							.setCustomAnimations(R.anim.fragment_slide_open_left, R.anim.fragment_slide_open_right)
							.hide(hideFragment).add(containerId, fragment, showTag).commitAllowingStateLoss();
				} else {
					manager.beginTransaction().hide(hideFragment).add(containerId, fragment, showTag)
							.commitAllowingStateLoss();
				}
			}
			/**
			 * 第一次进入程序
			 */
			else {
				manager.beginTransaction().add(containerId, fragment, showTag).commitAllowingStateLoss();
			}
		}
		/**
		 * 第二次进入Fragment
		 */
		else {
			if (hideFragment != null) {
				if (isAnimated) {
					if (isBack) {
						manager.beginTransaction()
								.setCustomAnimations(R.anim.fragment_slide_close_left,
										R.anim.fragment_slide_close_right)
								.hide(hideFragment).show(showFragment).addToBackStack(null).commitAllowingStateLoss();
					} else {
						manager.beginTransaction()
								.setCustomAnimations(R.anim.fragment_slide_open_left, R.anim.fragment_slide_open_right)
								.hide(hideFragment).show(showFragment).addToBackStack(null).commitAllowingStateLoss();
					}
				} else {
					manager.beginTransaction().hide(hideFragment).show(showFragment).addToBackStack(null)
							.commitAllowingStateLoss();
				}
			}
		}
	}

}
