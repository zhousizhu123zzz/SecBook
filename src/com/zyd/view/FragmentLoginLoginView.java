package com.zyd.view;

import com.zyd.model.User;

public interface FragmentLoginLoginView {

	public void toFragmentLoginRegister();

	public void toFragmentLoginRetrieve();

	public void toFragmentMain();

	public String getUserName();

	public String getPassword();

	public void showErrorInfo(String erroInfo);

	public void loading(int states);

	public void saveCurrentUser(User user); //���ֵ�ǰ��¼�û���Ϣ

}
