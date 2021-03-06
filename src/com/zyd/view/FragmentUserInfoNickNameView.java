package com.zyd.view;

import com.zyd.model.User;

public interface FragmentUserInfoNickNameView {

	public void toFragmentUserInfo(); //返回用户信息修改界面

	public String getNickName(); //获取用户输入的昵称

	public void setUser(); //从缓存中设置用户信息

	public User getUser();

	public void saveUser(); //保存修改信息

	public void showErrorMsg(String errorMsg); //显示错误信息，比如无网络连接，服务器异常

	public boolean isInfoChanged(); //用户是否修改了昵称

	public void notifyFragmentUserInfo(); //通知FragmentUserInfo数据改变
}
