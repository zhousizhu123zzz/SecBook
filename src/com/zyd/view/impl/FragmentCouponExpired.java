package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.view.FragmentCouponExpiredView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class FragmentCouponExpired extends Fragment implements FragmentCouponExpiredView {

	private View rootView;

	private ListView lv_content;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_coupon_expired, container, false);
		initView();
		return rootView;
	}

	/**
	 * ≥ı ºªØview
	 */
	private void initView() {
		lv_content = (ListView) rootView.findViewById(R.id.fragmentCouponExpired_lv_content);

		lv_content.setAdapter(new ExpiredLVAdapter());
	}

	@Override
	public void toFragmentCouponUnused() {

	}

	@Override
	public void toFragmentCouponUsed() {

	}

	private class ExpiredLVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

	}

}
