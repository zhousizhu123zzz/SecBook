package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentServerExView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentServerEx extends Fragment implements FragmentServerExView, FragmentBackHandler {

	private View rootView;

	private TextView tv_clickToRefresh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.page_serverex, container, false);
		initView();
		return rootView;
	}

	/**
	 * ≥ı ºªØView
	 */
	public void initView() {
		tv_clickToRefresh = (TextView) rootView.findViewById(R.id.fragmentServerEx_tv_clickToRefresh);

		tv_clickToRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				refresh();
			}
		});
	}

	@Override
	public boolean onBackPressed() {
		toFragmentBack();
		return true;
	}

	@Override
	public void toFragmentBack() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public void refresh() {

	}

}
