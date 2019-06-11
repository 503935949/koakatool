package com.supercode.koakatool.system;

import java.util.concurrent.ConcurrentHashMap;

public class Context {

    //投产用172.26.42.182  //测试用 47.92.132.65
    public static final String CARD_IP = "172.26.42.182";

    private static volatile ConcurrentHashMap<String,String> tokens = new ConcurrentHashMap<>();

    public static void refreshTokenCache(String key,String value){
        tokens.put(key,value);
    }

    public static String getTokenCache(String key){
        if(tokens.containsKey(key)){
            return tokens.get(key);
        }
        return "";
    }


}
