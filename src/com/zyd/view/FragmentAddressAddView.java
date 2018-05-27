package com.zyd.view;

import com.zyd.model.Address;

public interface FragmentAddressAddView {

	public void toFragmentAddress(); //�����ջ���ַ����

	public String getReceiptName(); //��ȡ�ջ���

	public String getTelNo(); //��ȡ�ջ��˵绰

	public String getAddress(); //��ȡ�ջ���ַ

	public boolean validate(); //��֤�Ƿ�Ϊ��

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ����֤��Ϣ����������Ϣ�ȵ�

	public Address getUploadAdress(); //��ȡ�ϴ��������Ĳ���

	public void setEditAddress(); //������Ҫ�༭�ĵ�ַ

}
