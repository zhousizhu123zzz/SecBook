package com.zyd.view.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.User;
import com.zyd.presenter.UserPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentCropImageView;
import com.zyd.widget.CropImageView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FragmentCropImage extends Fragment implements FragmentBackHandler, FragmentCropImageView {

	private View rootView;

	private CropImageView cropImageView;

	/**
	 * Ok
	 */
	private Button btn_ok;

	/**
	 * Confirm
	 */
	private LinearLayout ll_confirm;
	private Button btn_confirm, btn_cancel;

	private Bitmap selectedBitmap;
	private Bitmap croppedBitmap;

	private UserPresenter userPresenter = new UserPresenter(this);
	private User currentUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_cropimage, container, false);
		setCurrentUser();
		getSelectedBitmap();
		initView();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		btn_ok = (Button) rootView.findViewById(R.id.fragmentCropImage_btn_ok);
		cropImageView = (CropImageView) rootView.findViewById(R.id.fragmentCropImage_cropImageView);
		ll_confirm = (LinearLayout) rootView.findViewById(R.id.fragmentCropImage_ll_confirm);
		btn_confirm = (Button) rootView.findViewById(R.id.fragmentCropImage_btn_confirm);
		btn_cancel = (Button) rootView.findViewById(R.id.fragmentCropImage_btn_cancel);

		ll_confirm.setVisibility(View.INVISIBLE);

		cropImageView.setImageBitmap(selectedBitmap);
		btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				croppedBitmap = cropImageView.getCroppedImage();
				cropImageView.setImageBitmap(croppedBitmap);
				showConfirmLayout();
			}
		});
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getUploadImage() != null) {
					currentUser.setUserPic(getUploadImage());
					CacheUtil.setEntityData(getActivity(), "user_userPic" + currentUser.getUserId(), selectedBitmap);
					toFragmentUserInfo();
					userPresenter.save();
				} else {
					showErrorMsg("上传失败！请重试!");
					showOkLayout();
					cropImageView.setImageBitmap(selectedBitmap);
				}
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showOkLayout();
				cropImageView.setImageBitmap(selectedBitmap);
			}
		});
	}

	@Override
	public boolean onBackPressed() {
		toFragmentUserInfo();
		return true;
	}

	@Override
	public void toFragmentUserInfo() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentUserInfo", new FragmentUserInfo(),
				R.id.container_base, true, true);
	}

	private void getSelectedBitmap() {
		FragmentUserInfo fragmentUserInfo = (FragmentUserInfo) FragmentController
				.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentUserInfo");
		selectedBitmap = fragmentUserInfo.getSelectedBitmap();
	}

	private void showConfirmLayout() {
		ll_confirm.setVisibility(View.VISIBLE);
		btn_ok.setVisibility(View.INVISIBLE);
	}

	private void showOkLayout() {
		ll_confirm.setVisibility(View.INVISIBLE);
		btn_ok.setVisibility(View.VISIBLE);
	}

	@Override
	public void setCurrentUser() {
		this.currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public void saveUser() {
		CacheUtil.setEntityData(getActivity(), "currentUser", currentUser);
	}

	@Override
	public String getUploadImage() {
		if (croppedBitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			//将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流  
			croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] buffer = baos.toByteArray();
			System.out.println("图片的大小：" + buffer.length);
			return Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
		} else {
			return null;
		}
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	@Override
	public Bitmap getCropBitmap() {
		return croppedBitmap;
	}

}
