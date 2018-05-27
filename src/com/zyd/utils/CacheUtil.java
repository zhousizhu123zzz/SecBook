package com.zyd.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class CacheUtil {

	private static final String SP_NAME = "SPDATA";

	/**
	 * 保存信息,包含了Android自带的各种数据类型，和我们自定义的实体类型，不过需要序列化实体类
	 */
	@SuppressWarnings("unchecked")
	public static void put(Context context, String key, Object value) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof Set) {
			editor.putStringSet(key, (Set<String>) value);
		} else if (value instanceof Serializable) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(value);
				String string64 = new String(Base64.encode(baos.toByteArray(), 0));
				editor.putString(key, string64);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("the obj may not appropriate data type!");
		}
		editor.commit();
	}

	/**
	 * 获取信息
	 */
	@SuppressWarnings("unchecked")
	public static Object get(Context context, String key, Object defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		if (defaultValue instanceof String) {
			return sp.getString(key, (String) defaultValue);
		} else if (defaultValue instanceof Integer) {
			return sp.getInt(key, (Integer) defaultValue);
		} else if (defaultValue instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultValue);
		} else if (defaultValue instanceof Float) {
			return sp.getFloat(key, (Float) defaultValue);
		} else if (defaultValue instanceof Long) {
			return sp.getLong(key, (Long) defaultValue);
		} else if (defaultValue instanceof Set) {
			return sp.getStringSet(key, (Set<String>) defaultValue);
		} else if (defaultValue instanceof Serializable) {
			String base64 = sp.getString(key, "");
			try {
				byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
				ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
				if (bais.available() != 0) {
					ObjectInputStream ois = new ObjectInputStream(bais);
					return ois.readObject();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** 
	 * 保存List 
	 * @param tag 
	 * @param datalist 
	 */
	public static <T> void setDataList(Context context, String tag, List<T> datalist) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		if (null == datalist || datalist.size() <= 0)
			return;

		Gson gson = new Gson();
		//转换成json数据，再保存  
		String strJson = gson.toJson(datalist);
		editor.putString(tag, strJson);
		editor.commit();

	}

	/** 
	 * 获取List 
	 * @param tag 
	 * @return 
	 */
	public static <T> List<T> getDataList(Context context, String tag, Type type) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		List<T> datalist = new ArrayList<T>();
		String strJson = sp.getString(tag, null);
		if (null == strJson) {
			return datalist;
		}
		Gson gson = new Gson();
		datalist = gson.fromJson(strJson, type);
		return datalist;

	}

	/**
	 * GSON方式保存数据
	 * @param context
	 * @param tag
	 * @param o
	 */
	public static void setEntityData(Context context, String tag, Object o) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		Gson gson = new Gson();
		//转换成json数据，再保存  
		String strJson = gson.toJson(o);
		editor.putString(tag, strJson);
		editor.commit();
	}

	public static Object getEntityData(Context context, String tag, Type type) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		String strJson = sp.getString(tag, null);
		if (null == strJson) {
			return null;
		}
		Gson gson = new Gson();
		return gson.fromJson(strJson, type);
	}
}
