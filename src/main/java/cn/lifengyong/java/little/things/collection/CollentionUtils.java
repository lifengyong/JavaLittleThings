package cn.lifengyong.java.little.things.collection;

import java.util.Collection;

/**
 * <P>Description: 集合操作工具类</P>
 * 
 * @author lify
 * @version 1.0.0
 */
public class CollentionUtils {
    
    /**
     * 获取集合中的第一个元素
     * 
     * @param collection 集合
     * @return 集合中的第一个元素
     */
    public static <T> T getFirstElement(Collection<T> collection) {
        if(collection == null || collection.isEmpty()) {
            return null;
        }
        return collection.iterator().next();
    }
}
