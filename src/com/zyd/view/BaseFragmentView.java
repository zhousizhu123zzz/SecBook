package com.zyd.view;

public interface BaseFragmentView {

	public void toFragmentLoading(); //��ת�����ؽ���

	public void toFragmentServerEx(); //��ת���������쳣����

	public void toFragmentNoNet(); //��ת�����������ӽ���

	public void setTransmissionData(Object o); //������Ҫ���ݵ�����

	public Object getTransmissionData();//��ȡ��Ҫ���ݵ�����

}
