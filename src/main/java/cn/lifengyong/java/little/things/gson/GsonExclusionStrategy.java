package cn.lifengyong.java.little.things.gson;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


/**
 * <P>Description: Gson排除某些类和字段不进行转换处理</P>
 * 
 * @author lify
 * @version 1.0.0
 */
public class GsonExclusionStrategy implements ExclusionStrategy {  
    private final Class<?> typeToExclude;

    public GsonExclusionStrategy(Class<?> clazz){
        this.typeToExclude = clazz;
    }

    /**
     * 排除不进行转换的对象
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return ( this.typeToExclude != null && this.typeToExclude == clazz )
                    || clazz.getAnnotation(GsonExclude.class) != null;
    }

    /**
     * 排除不进行转换的字段
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(GsonExclude.class) != null;
    }
}
