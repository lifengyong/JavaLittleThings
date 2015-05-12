package cn.lifengyong.java.little.things.gson;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <P>Description: 日期格式的JSON转换适配器</P>
 * 
 * @author lify
 * @version 1.0.0
 */
public class GsonDateTypeAdapter implements JsonSerializer<Date>,JsonDeserializer<Date> {
	private final DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");   
	@Override
	public JsonElement serialize(Date arg0, Type arg1,
			JsonSerializationContext arg2) {
			String dfString = format.format(new Date(arg0.getTime()));      
			 return new JsonPrimitive(dfString);  
	}

	@Override
	public Date deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		Date date = new Date();
		String str = arg0.getAsString();
		if(!"".equals(str)){
			try {      				
	            date = format.parse(str);           
			} catch (Exception e) {      
				throw new JsonParseException(e);      
			}      
		}
	
		return date;
	}

}
