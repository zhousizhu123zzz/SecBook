package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.view.FragmentCouponUnusedView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class FragmentCouponUnused extends Fragment implements FragmentCouponUnusedView {

	private View rootView;

	private ListView lv_content;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_coupon_unused, container, false);
		initView();
		return rootView;
	}

	/**
	 * ≥ı ºªØview
	 */
	private void initView() {
		lv_content = (ListView) rootView.findViewById(R.id.fragmentCouponUnused_lv_content);

		lv_content.setAdapter(new UnusedLVAdapter());
	}

	@Override
	public void toFragmentCouonUsed() {

	}

	@Override
	public void toFragmentCouponExpired() {

	}

	private class UnusedLVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 1;
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
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_coupon, null);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
		}

	}

}
