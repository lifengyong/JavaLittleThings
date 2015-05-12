package cn.lifengyong.java.little.things.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <P>Description: 标有该注解的字段，Gson将不对其进行处理</P>
 * 
 * @author lify
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface GsonExclude {
}
