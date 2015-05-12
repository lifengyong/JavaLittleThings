package cn.lifengyong.java.little.things.gson;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * <P>Description: Json转换工具</P>
 * 
 * @author lify
 * @version 1.0.0
 */
public class GsonUtil {
	
	private static Gson gson;
	//未JSON处理类注入适配器
	static{
		if(gson == null){
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Date.class, new GsonDateTypeAdapter())
			.registerTypeAdapter(Integer.class, new GsonIntegerTypeAdapter())
			.serializeNulls();	
			gson = builder.create();
		}
	}
	
	public static Gson getInstance() {
		return gson;
	}
	
	/**
	 * 返回可以排除某些字段不进行json转换的Gson对象
	 * 
	 * @param exclusionStrategy 排除策略
	 * @return
	 */
	public static Gson createGsonExclusionStrategy(GsonExclusionStrategy exclusionStrategy ){
	    return new GsonBuilder().setExclusionStrategies(exclusionStrategy).
	    		serializeNulls().create();
	}
	
	/**
	 * 转成json数据为java对象
	 * 
	 * @param json 需要转换的json字符串
	 * @param clasz 期望转换的对象类
	 * @return Object
	 */
	public static <T> T jsonToPojo(String json,Class<T> clasz)  {
		return (T)gson.fromJson(json, clasz);
	}
	
	/**
	 * 将java对象装换成JSON字符串
	 * 
	 * @param obj 需要转换的对象
	 * @return JSON字符串
	 */
	public static String pojoToJson(Object obj){
		String json = gson.toJson(obj);
		json = StringUtils.replace(json, "null", "\"\"");
		return json;
	}
	
}
