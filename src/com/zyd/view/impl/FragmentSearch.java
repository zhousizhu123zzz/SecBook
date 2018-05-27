package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.zyd.constant.Constant;
import com.zyd.model.Book;
import com.zyd.model.MData;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentSearchView;
import com.zyd.widget.AutoLineTextView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSearch extends Fragment implements FragmentSearchView, FragmentBackHandler {

	private View rootView;

	/**
	 * TitleBar
	 */
	private LinearLayout ll_back;
	private RelativeLayout rl_search;
	private EditText et_search;
	private ImageView iv_search;
	private TextView tv_search;

	/**
	 * HotSearch
	 */
	private AutoLineTextView autoLineTextView;

	/**
	 * HistorySearch
	 */
	private LinearLayout ll_history;
	private ListView lv_history;

	private List<MData> historySearchList = new ArrayList<MData>();
	private List<Book> hotBookList = new ArrayList<Book>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_search, container, false);
		initView();
		setHotSearchFromCache();
		setHistorySearchListFromCache();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentSearch_ll_back);
		rl_search = (RelativeLayout) rootView.findViewById(R.id.fragmentSearch_rl_search);
		et_search = (EditText) rootView.findViewById(R.id.fragmentSearch_et_search);
		iv_search = (ImageView) rootView.findViewById(R.id.fragmentSearch_iv_search);
		ll_history = (LinearLayout) rootView.findViewById(R.id.fragmentSearch_ll_historySearch);
		tv_search = (TextView) rootView.findViewById(R.id.fragmentSearch_tv_search);
		autoLineTextView = (AutoLineTextView) rootView.findViewById(R.id.fragmentSearch_hotSearch);
		lv_history = (ListView) rootView.findViewById(R.id.fragmentSearch_lv_history);

		ll_back.setOnClickListener(new ClickListener());
		rl_search.setOnClickListener(new ClickListener());
		et_search.setOnClickListener(new ClickListener());
		iv_search.setOnClickListener(new ClickListener());
		tv_search.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.fragmentSearch_ll_back:
				toFragmentMain();
				break;

			case R.id.fragmentSearch_rl_search:

				break;

			case R.id.fragmentSearch_iv_search:
				rl_search.performClick();
				break;

			case R.id.fragmentSearch_et_search:
				rl_search.performClick();
				break;

			case R.id.fragmentSearch_tv_search:
				toFragmentSearchList();
				break;

			}
		}

	}

	private class HistoryLVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return historySearchList.size();
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_item_history, null);
				holder.tv_history = (TextView) convertView.findViewById(R.id.fragmentSearch_tv_historyTitle);
				holder.rl_delete = (RelativeLayout) convertView.findViewById(R.id.fragmentSearch_rl_closeHistory);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_history.setText(String.valueOf(historySearchList.get(position).getName()));
			holder.setPosition(position);
			holder.setClickListener();
			return convertView;
		}

		class ViewHolder {
			int position;

			TextView tv_history;
			RelativeLayout rl_delete;

			void setPosition(int position) {
				this.position = position;
			}

			void setClickListener() {
				rl_delete.setOnClickListener(new DeleteClickListener());
			}

			class DeleteClickListener implements View.OnClickListener {

				@Override
				public void onClick(View v) {
					historySearchList.remove(position);
					notifyDataSetChanged();
					CacheUtil.setDataList(getActivity(), "historySearchList", historySearchList);
				}

			}

		}

	}

	@Override
	public void toFragmentMain() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentSearchList() {
		if (StringUtil.isEmpty(getSearchText())) {
			showErrorMsg(Constant.msg.ERROR_NOSEARCHTEXT);
		} else {
			CacheUtil.put(getActivity(), "fragmentSearch_tv_search", getSearchText());
			saveSearchTextToCache(getSearchText());
			FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentSearchList",
					new FragmentSearchList(), R.id.container_base, false, true);
		}
	}

	@Override
	public String getSearchText() {
		return et_search.getText().toString();
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public boolean onBackPressed() {
		toFragmentMain();
		return true;
	}

	@Override
	public void setHistorySearchListFromCache() {
		historySearchList = CacheUtil.getDataList(getActivity(), "historySearchList", new TypeToken<List<MData>>() {
		}.getType());
		if (historySearchList == null || historySearchList.size() == 0) {
			setHistorySearchVisible(View.GONE);
		} else {
			setHistorySearchVisible(View.VISIBLE);
			lv_history.setAdapter(new HistoryLVAdapter());
			lv_history.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					CacheUtil.put(getActivity(), "fragmentSearch_tv_search", historySearchList.get(position).getName());
					FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentSearchList",
							new FragmentSearchList(), R.id.container_base, false, true);
				}
			});
		}
	}

	@Override
	public void setHotSearchFromCache() {
		List<Book> allBookList = CacheUtil.getDataList(getActivity(), "searchHotBookList", new TypeToken<List<Book>>() {
		}.getType());
		this.hotBookList = StringUtil.limitBookList(allBookList, "hot");
		if (hotBookList != null) {
			autoLineTextView.post(new Runnable() {

				@Override
				public void run() {
					int measureWidth = autoLineTextView.getMeasuredWidth();
					autoLineTextView.setDataList(StringUtil.parseBookListToStringList(hotBookList)); //给AutoLineTextView设置数据
					autoLineTextView.genView(measureWidth); //生成AutoLineTextView
					List<TextView> tvList = autoLineTextView.getAllTextView();
					Iterator<TextView> it = tvList.iterator();
					while (it.hasNext()) {
						final TextView tv = it.next();
						tv.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								CacheUtil.put(getActivity(), "fragmentSearch_tv_search", String.valueOf(tv.getText()));
								saveSearchTextToCache(String.valueOf(tv.getText()));
								FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentSearchList",
										new FragmentSearchList(), R.id.container_base, false, true);
							}
						});
					}
				}
			});
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			setHistorySearchListFromCache();
		}
	}

	@Override
	public void saveSearchTextToCache(String searchText) {
		historySearchList = CacheUtil.getDataList(getActivity(), "historySearchList", new TypeToken<List<MData>>() {
		}.getType());
		if (historySearchList == null) {
			historySearchList = new ArrayList<MData>();
		}
		historySearchList.add(new MData(searchText));
		CacheUtil.setDataList(getActivity(), "historySearchList", historySearchList);
	}

	@Override
	public void setHistorySearchVisible(int visibility) {
		ll_history.setVisibility(visibility);
	}

}
