package com.zyd.view.impl;

import com.zyd.secbooks.R;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHelper;
import com.zyd.utils.FragmentController;
import com.zyd.view.ActivityMainView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * ActivityMain:APPΨһ��һ��Activity�����������е�Fragment
 * @author ������
 *
 */
public class ActivityMain extends FragmentActivity implements ActivityMainView {

	private boolean isExist = false; //���ε�����ذ����˳�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toFragmentStart();
	}

	/**
	 * 1.�����������������ʾ�˳�����
	 * 2.����ϵͳû�и�Fragment��Ӧ��onBackPressed����,���ԣ����������Լ���Activity��onBackPressed�����ط��ؼ����¼�,
	 *   ��onBackPressed���Ƚ��������Զ���Ĺ������ж�Fragment�����жϣ�������е�Fragment������false,Ҳ����
	 *   ��ǰ�Ѿ������������ˣ��ż����ڴ�onBackPressed�н������ε���ʾ�˳��жϣ�����������ص���Fragment��Ҳ����
	 *   ����true��Fragment���Զ���onBackPressed����������
	 */
	@Override
	public void onBackPressed() {
		if (!FragmentBackHelper.handleBackPress(this)) {
			if (!isExist) {
				isExist = true;
				EqupUtil.showMyToast(getApplicationContext(), "ȷ��Ҫ�˳�SecBook��", 0, Toast.LENGTH_SHORT);

				new Thread() {

					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							isExist = false;
						}
					}

				}.start();

			} else {
				System.exit(0);
			}
		}
	}

	@Override
	public void toFragmentStart() {
		FragmentController.show(getSupportFragmentManager(), "FragmentStart", new FragmentStart(), R.id.container_base,
				false, false);
	}

}
