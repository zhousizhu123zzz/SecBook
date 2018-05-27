package com.zyd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HClientUtil {

	/**
	 * 基于HClient4.2.1的post请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String addEntity(String url, Map<String, String> data) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		if (data != null) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String key : data.keySet()) {
				params.add(new BasicNameValuePair(key, data.get(key)));
			}
			UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(requestEntity);
		}
		HttpResponse response = client.execute(post);
		HttpEntity responseEntity = response.getEntity();
		String result = EntityUtils.toString(responseEntity);
		if (JSONUtils.isJSON(result))
			return result;
		return null;
	}

}
