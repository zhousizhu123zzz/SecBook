package com.zyd.view;

import com.zyd.model.User;

import android.graphics.Bitmap;

public interface FragmentUserInfoView {

	public void toFragmentMain(); //返回主界面

	public void toFragmentUserInfoSex(); //进入修改性别界面

	public void toFragmentUserInfoNickName(); //今日昵称修改界面

	public void toFragmentCropImage();//进入图片裁剪

	public void showChangePicDialog(); //显示更换头像的拍照选择、相册选择提示框

	public void showDatePicker();//显示日期控件

	public void saveUser();

	public void toFragmentLogin(); //退出登录,跳转到登录界面

	public void setUser(); //从缓存中设置当前用户

	public User getCurrentUser();

	public void showErrorMsg(String errorMsg);

	public Bitmap getSelectedBitmap();

}
