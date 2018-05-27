package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.zyd.constant.Constant;
import com.zyd.model.Address;
import com.zyd.model.User;
import com.zyd.presenter.AddressPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentAddressView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 收货地址界面
 * @author 朱永地
 *
 */
public class FragmentAddress extends BaseFragment implements FragmentAddressView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * ProgressBar
	 */
	private RelativeLayout rl_loading;

	/**
	 * Body
	 */
	private RelativeLayout rl_content;
	private ListView lv_content;

	/**
	 * Foot
	 */
	private LinearLayout ll_add;

	private Dialog dialog_isDelete; //是否删除的提示框

	/**
	 * Data
	 */
	private List<Address> addressList = new ArrayList<Address>();

	private AddressPresenter addressPresenter = new AddressPresenter(this);

	private int deletePosition = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_address, container, false);
		initView();
		addressPresenter.listAddress();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentAddress_ll_back);
		ll_add = (LinearLayout) rootView.findViewById(R.id.fragmentAddress_ll_add);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentAddress_lv_content);
		rl_loading = (RelativeLayout) rootView.findViewById(R.id.fragmentAddress_rl_loading);
		rl_content = (RelativeLayout) rootView.findViewById(R.id.fragmentAddress_rl_content);

	}

	/**
	 * 点击事件内部类
	 * @author 朱永地
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.fragmentAddress_ll_back:
				toFragmentBack();
				break;

			case R.id.fragmentAddress_ll_add:
				toFragmentAddressAdd();
				break;

			case R.id.viewer_tv_notToLogin:
				dialog_isDelete.cancel();
				break;

			case R.id.viewer_tv_toLogin:
				addressPresenter.delete();
				break;

			}
		}

	}

	/**
	 * 地址管理的ListView适配器
	 * @author 朱永地
	 *
	 */
	private class AddressLVAdapter extends BaseAdapter {

		final int NODATA = -1; //无收货地址
		final int HASDATA = 1; //有收货地址

		@Override
		public int getCount() {
			if (addressList.size() == 0) {
				return 1;
			} else {
				return addressList.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getItemViewType(int position) {
			if (addressList.size() == 0) {
				return NODATA;
			} else {
				return HASDATA;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (getItemViewType(position) == NODATA) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_coupon, null);
					holder.tv_title = (TextView) convertView.findViewById(R.id.pageNodata_tv_title);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.tv_title.setText(Constant.msg.ERROR_NOADDRESS);
			} else if (getItemViewType(position) == HASDATA) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_address_item, null);
					holder.tv_name = (TextView) convertView.findViewById(R.id.fragmentAddressItem_tv_name);
					holder.tv_telNo = (TextView) convertView.findViewById(R.id.fragmentAddressItem_tv_telNo);
					holder.tv_address = (TextView) convertView.findViewById(R.id.fragmentAddressItem_tv_address);
					holder.ll_setToIndex = (LinearLayout) convertView
							.findViewById(R.id.fragmentAddressItem_ll_setToIndex);
					holder.ll_edit = (LinearLayout) convertView.findViewById(R.id.fragmentAddressItem_ll_edit);
					holder.ll_delete = (LinearLayout) convertView.findViewById(R.id.fragmentAddressItem_ll_delete);
					holder.iv_selected = (ImageView) convertView.findViewById(R.id.fragmentAddressItem_iv_selected);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.position = position;
				if (addressList.get(position).isIndex()) {
					holder.iv_selected.setBackgroundResource(R.drawable.icon_selected_red);
				} else {
					holder.iv_selected.setBackgroundResource(R.drawable.icon_unselected_gray);
				}
				holder.tv_name.setText(addressList.get(position).getReceiptName());
				holder.tv_address.setText(addressList.get(position).getAddress());
				holder.tv_telNo.setText(addressList.get(position).getTelNo());
				holder.setOnClickListener();
			}
			return convertView;
		}

		class ViewHolder {
			int position = 0;
			TextView tv_title;//Nodata
			TextView tv_name, tv_telNo, tv_address;
			LinearLayout ll_setToIndex, ll_edit, ll_delete;
			ImageView iv_selected;

			void setOnClickListener() {
				ll_setToIndex.setOnClickListener(new ClickListener());
				ll_edit.setOnClickListener(new ClickListener());
				ll_delete.setOnClickListener(new ClickListener());
			}

			class ClickListener implements View.OnClickListener {

				@Override
				public void onClick(View v) {

					switch (v.getId()) {

					case R.id.fragmentAddressItem_ll_setToIndex:
						resetIndexAddress();
						addressList.get(position).setIndex(true);
						notifyDataSetChanged();
						break;
					case R.id.fragmentAddressItem_ll_delete:
						deletePosition = position;
						showDeleteDialog(position);
						break;

					case R.id.fragmentAddressItem_ll_edit:
						setTransmissionData(addressList.get(position));
						toFragmentAddressAdd();
						break;

					}
				}

			}

		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentBack();
		return true;
	}

	@Override
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	@Override
	public void toFragmentAddressAdd() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentAddressAdd",
				new FragmentAddressAdd(), R.id.container_base, false, true);
	}

	@Override
	public void showDeleteDialog(int position) {
		dialog_isDelete = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
		View dialogContentView = View.inflate(getActivity(), R.layout.viewer_tologin, null);
		TextView tv_title = (TextView) dialogContentView.findViewById(R.id.viewer_tv_title);
		TextView tv_content = (TextView) dialogContentView.findViewById(R.id.viewer_tv_content);
		TextView tv_delete = (TextView) dialogContentView.findViewById(R.id.viewer_tv_toLogin);
		TextView tv_notDelete = (TextView) dialogContentView.findViewById(R.id.viewer_tv_notToLogin);
		tv_title.setPadding(0, 40, 0, 20);
		tv_content.setVisibility(View.VISIBLE);
		tv_content.setPadding(0, 20, 0, 40);
		tv_title.setText("您确定要删除：");
		tv_content.setText(addressList.get(position).getReceiptName() + "    " + addressList.get(position).getTelNo()
				+ "    " + addressList.get(position).getAddress() + " ?");
		tv_delete.setText("删除");
		tv_notDelete.setText("取消");
		tv_notDelete.setOnClickListener(new ClickListener());
		tv_delete.setOnClickListener(new ClickListener());

		dialog_isDelete.setContentView(dialogContentView);
		Window dialogWindow = dialog_isDelete.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		dialog_isDelete.show();
	}

	public void resetIndexAddress() {
		Iterator<Address> it = addressList.iterator();
		while (it.hasNext()) {
			((Address) it.next()).setIndex(false);
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			addressPresenter.listAddress();
		}
	}

	@Override
	public Address getDeleteAddress() {
		Address address = new Address();
		address.setAddressId(addressList.get(deletePosition).getAddressId());
		return address;
	}

	@Override
	public void refresh() {
		dialog_isDelete.cancel();
		addressList.remove(deletePosition);
		((BaseAdapter) lv_content.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setTransmissionData(Object o) {
		super.setTransmissionData(o);
	}

	@Override
	public void toFragmentBack() {
		String fragmentBackTag = (String) CacheUtil.get(getActivity(), "fragmentBackTag", "");
		if (null == fragmentBackTag) {
			fragmentBackTag = "FragmentMain";
		}
		FragmentController.show(getActivity().getSupportFragmentManager(), fragmentBackTag,
				FragmentController.getFragmentByTag(getActivity().getSupportFragmentManager(), fragmentBackTag),
				R.id.container_base, true, true);
	}

	@Override
	public User getCurrentUser() {
		return (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public void setProgressBarVisible(int visibility) {
		if (visibility == View.GONE) {
			rl_loading.setVisibility(View.GONE);
			rl_content.setVisibility(View.VISIBLE);
			ll_add.setVisibility(View.VISIBLE);
		} else if (visibility == View.VISIBLE) {
			rl_loading.setVisibility(View.VISIBLE);
			rl_content.setVisibility(View.GONE);
			ll_add.setVisibility(View.GONE);
		}
	}

	@Override
	public void setViewText() {
		ll_back.setOnClickListener(new ClickListener());
		ll_add.setOnClickListener(new ClickListener());
		lv_content.setAdapter(new AddressLVAdapter());
	}

}
