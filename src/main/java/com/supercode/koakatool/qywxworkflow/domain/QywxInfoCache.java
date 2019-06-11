package com.supercode.koakatool.qywxworkflow.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QywxInfoCache {

    private static volatile HashMap<Long,Object> infos = new HashMap<>();

    public static void put(Long key,Object value){
        infos.put(key,value);
    }

    public static Object getInfos(Long key){
        if(infos.containsKey(key)){
            return infos.get(key);
        }
        return null;
    }

    public static void clearCache(){
        infos.clear();
    }

    public static Set keySet(){
        return infos.keySet();
    }

    public static Set<Map.Entry<Long,Object>> entrySet(){
        return infos.entrySet();
    }

}
