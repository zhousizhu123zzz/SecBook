package com.zyd.biz.impl;

import java.util.Map;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.utils.HClientUtil;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

public class MainBizImpl implements MainBiz {

	@Override
	public void connect(final String url, final Map<String, String> params, final MainListener mainListener) {
		final Handler handler = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {

				switch (msg.what) {

				case 1:
					mainListener.success((String) msg.obj);
					break;

				case -1:
					mainListener.serverEx();
					break;
				}
				return false;//����return true,����HandlerԴ���֪return true����ִ�д�����Ϣ
			}
		});

		new Thread() {

			@Override
			public void run() {
				try {
					String jsonData = HClientUtil.addEntity(url, params);
					if (jsonData == null) {
						handler.sendEmptyMessage(-1);
					} else {
						Message msg = handler.obtainMessage();
						msg.what = 1;
						msg.obj = jsonData;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(-1);
				}
			}

		}.start();

	}
}
