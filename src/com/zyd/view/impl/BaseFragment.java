package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.FragmentController;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private Object transmissionData;

	/**
	 * ������ؽ���
	 */
	public void toFragmentLoading() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentLoading", new FragmentLoading(),
				R.id.container_base, false, true);
	}

	/**
	 * ����������쳣����
	 */
	public void toFragmentServerEx() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentServerEx", new FragmentServerEx(),
				R.id.container_base, false, false);
	}

	/**
	 * �������������ӽ���
	 */
	public void toFragmentNoNet() {

	}

	/**
	 * ������Ҫ���ݵ�����
	 * @param o
	 */
	public void setTransmissionData(Object o) {
		this.transmissionData = o;
	}

	/**
	 * ��ȡ��Ҫ���ݵ�����
	 * @return
	 */
	public Object getTransmissionData() {
		return transmissionData;
	}

}
