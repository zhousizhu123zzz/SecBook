package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.FragmentController;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private Object transmissionData;

	/**
	 * 进入加载界面
	 */
	public void toFragmentLoading() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentLoading", new FragmentLoading(),
				R.id.container_base, false, true);
	}

	/**
	 * 进入服务器异常界面
	 */
	public void toFragmentServerEx() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentServerEx", new FragmentServerEx(),
				R.id.container_base, false, false);
	}

	/**
	 * 进入无网络连接界面
	 */
	public void toFragmentNoNet() {

	}

	/**
	 * 设置需要传递的数据
	 * @param o
	 */
	public void setTransmissionData(Object o) {
		this.transmissionData = o;
	}

	/**
	 * 获取需要传递的数据
	 * @return
	 */
	public Object getTransmissionData() {
		return transmissionData;
	}

}
