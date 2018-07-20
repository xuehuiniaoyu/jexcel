package org.xhny.jexcel.pj;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/7/20 0020.
 */
public class Table {
    private HashMap<String, String> hashMap = new HashMap<>();

    /**
     * 映射
     * @param mappings
     */
    public void mapping(Mapping ... mappings) {
        for(Mapping mapping : mappings) {
            hashMap.put(mapping.getKey(), mapping.getValue());
        }
    }

    /**
     * 映射
     * @param mapping
     * @return
     */
    public Table mapping(Mapping mapping) {
        hashMap.put(mapping.getKey(), mapping.getValue());
        return this;
    }

    /**
     * 映射
     * @param key
     * @param value
     * @return
     */
    public Table mapping(String key, String value) {
        hashMap.put(key, value);
        return this;
    }

    /**
     * 如果有映射关系，返回映射后字段，否则返回本身。
     * @param key
     * @return
     */
    public String tryToGetMappingValue(String key) {
        if(hashMap.containsKey(key))
            return hashMap.get(key);
        return key;
    }

    public String tryToGetMappingKey(String value) {
        for(String key : hashMap.keySet()) {
            if(value.equals(hashMap.get(key)))
                return key;
        }
        return value;
    }
}
