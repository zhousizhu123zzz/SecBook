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
 * ActivityMain:APP唯一的一个Activity，管理着所有的Fragment
 * @author 朱永地
 *
 */
public class ActivityMain extends FragmentActivity implements ActivityMainView {

	private boolean isExist = false; //两次点击返回按键退出

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toFragmentStart();
	}

	/**
	 * 1.设置连续点击两次提示退出程序
	 * 2.由于系统没有给Fragment相应的onBackPressed方法,所以，这里我们自己在Activity的onBackPressed中拦截返回监听事件,
	 *   让onBackPressed首先进入我们自定义的工具类中对Fragment进行判断，如果所有的Fragment都返回false,也就是
	 *   当前已经在主界面中了，才继续在此onBackPressed中进行两次的提示退出判断，否则就由拦截到的Fragment，也就是
	 *   返回true的Fragment的自定义onBackPressed方法来处理。
	 */
	@Override
	public void onBackPressed() {
		if (!FragmentBackHelper.handleBackPress(this)) {
			if (!isExist) {
				isExist = true;
				EqupUtil.showMyToast(getApplicationContext(), "确定要退出SecBook吗？", 0, Toast.LENGTH_SHORT);

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
