package com.zyd.view;

import com.zyd.model.User;

import android.graphics.Bitmap;

public interface FragmentCropImageView {

	public void toFragmentUserInfo(); //返回用户信息界面

	public void setCurrentUser();

	public User getCurrentUser();

	public void saveUser();

	public String getUploadImage();

	public void showErrorMsg(String errorMsg);

	public Bitmap getCropBitmap();

}
