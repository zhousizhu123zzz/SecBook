package com.zyd.utils;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Fragment���ع�����
 * @author Administrator
 *
 */
public class FragmentBackHelper {

	/**
	 * ��FragmentActivity���Ƚ�������
	 * @param activity
	 * @return
	 */
	public static boolean handleBackPress(FragmentActivity activity) {
		return handleBackPress(activity.getSupportFragmentManager());
	}

	/**
	 * ��Fragment�н�������
	 * @param fragment
	 * @return
	 */
	public static boolean handleBackPress(Fragment fragment) {
		return handleBackPress(fragment.getChildFragmentManager());
	}

	/**
	 * �����жϵ�ǰActivity������Fragment�Ƿ�����onBackPressed
	 * @param manager
	 * @return
	 */
	public static boolean handleBackPress(FragmentManager manager) {
		List<Fragment> fragmentList = manager.getFragments(); //��ȡFragmentManager�����е�Fragment

		if (fragmentList == null)
			return false; //�����ǰFragmentManager��û��Fragment�򷵻�false����Activity����

		for (Fragment fragment : fragmentList) {
			if (isFragmentBackHandled(fragment)) //����isFragmentBackHandled�ж�Fragment�Ƿ񱻴���,�����˷���true,��������ǰActivity����
				return true;
		}

		return false;
	}

	/**
	 * �ж�Fragment��onBackPressed���Ƿ�����
	 * @param fragment
	 * @return
	 */
	public static boolean isFragmentBackHandled(Fragment fragment) {
		return fragment != null && fragment.isVisible() //�ж����ĸ�Fragment����˷���
				&& fragment.getUserVisibleHint() //ViewPager
				&& fragment instanceof FragmentBackHandler //Fragment��Ҫʵ��FragmentBackHandler�ӿ�
				&& ((FragmentBackHandler) fragment).onBackPressed(); //Fragment��Ҫ��onBackPressed����true,���������
	}
}
