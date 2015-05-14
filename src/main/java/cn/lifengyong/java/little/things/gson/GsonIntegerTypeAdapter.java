package cn.lifengyong.java.little.things.gson;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <P>Description: 整数类型JSON转换适配器</P>
 * 
 * @author lify
 * @version 1.0.0
 */
public class GsonIntegerTypeAdapter implements JsonSerializer<Integer>,JsonDeserializer<Integer> {

    @Override
    public JsonElement serialize(Integer arg0, Type arg1, JsonSerializationContext arg2) {
        String dfString = String.valueOf(arg0);      
        return new JsonPrimitive(dfString);  
    }

    @Override
    public Integer deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        Integer n = 0;
        String str = arg0.getAsString();
        if(!"".equals(str)){
            n = Integer.parseInt(str);
        }
        return n;
    }

}
