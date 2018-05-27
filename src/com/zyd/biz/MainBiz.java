package com.zyd.biz;

import java.util.Map;

public interface MainBiz {

	public abstract void connect(String url, Map<String, String> params, MainListener mainListener);//查询,传入查询参数T,回调接口

}
