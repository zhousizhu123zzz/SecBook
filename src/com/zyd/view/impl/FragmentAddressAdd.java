package com.zyd.view.impl;

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
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentAddressAddView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加收货地址界面
 * @author 朱永地
 *
 */
public class FragmentAddressAdd extends Fragment implements FragmentAddressAddView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * Body
	 */
	private EditText et_receiptName, et_telNo, et_address;

	/**
	 * Foot
	 */
	private TextView tv_save;

	private AddressPresenter addressPresenter = new AddressPresenter(this);

	private Address editAddress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_address_add, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentAddressAdd_ll_back);
		et_receiptName = (EditText) rootView.findViewById(R.id.fragmentAddressAdd_et_name);
		et_telNo = (EditText) rootView.findViewById(R.id.fragmentAddressAdd_et_telNo);
		et_address = (EditText) rootView.findViewById(R.id.fragmentAddressAdd_et_address);
		tv_save = (TextView) rootView.findViewById(R.id.fragmentAddressAdd_tv_save);

		setEditAddress();

		ll_back.setOnClickListener(new ClickListener());
		tv_save.setOnClickListener(new ClickListener());
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

			case R.id.fragmentAddressAdd_ll_back:
				toFragmentAddress();
				break;

			case R.id.fragmentAddressAdd_tv_save:
				if (!validate()) {
					showErrorMsg(Constant.msg.ERROR_NOFULL);
				} else {
					addressPresenter.save();
				}
				break;

			}
		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentAddress();
		return true;
	}

	@Override
	public void toFragmentAddress() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentAddress", new FragmentAddress(),
				R.id.container_base, true, true);
	}

	@Override
	public String getReceiptName() {
		return et_receiptName.getText().toString();
	}

	@Override
	public String getTelNo() {
		return et_telNo.getText().toString();
	}

	@Override
	public String getAddress() {
		return et_address.getText().toString();
	}

	@Override
	public boolean validate() {
		if (editAddress != null) {
			if (getReceiptName().equals(editAddress.getReceiptName()) && getTelNo().equals(editAddress.getTelNo())
					&& getAddress().equals(editAddress.getAddress()))
				return false;
		} else {
			if (StringUtil.isEmpty(getReceiptName()) || StringUtil.isEmpty(getTelNo())
					|| StringUtil.isEmpty(getAddress()))
				return false;
		}
		return true;
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public Address getUploadAdress() {
		User currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
		Address address = new Address();
		if (editAddress != null) {
			address.setAddressId(editAddress.getAddressId());
		}
		address.setUserId(currentUser.getUserId());
		address.setReceiptName(getReceiptName());
		address.setTelNo(getTelNo());
		address.setAddress(getAddress());
		return address;
	}

	@Override
	public void setEditAddress() {
		FragmentAddress fragmentAddress = (FragmentAddress) FragmentController
				.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentAddress");
		if (fragmentAddress.getTransmissionData() != null) {
			Address address = (Address) fragmentAddress.getTransmissionData();
			et_receiptName.setText(address.getReceiptName());
			et_telNo.setText(address.getTelNo());
			et_address.setText(address.getAddress());
			editAddress = address;
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (hidden == false) {
			setEditAddress();
		}
	}

}
