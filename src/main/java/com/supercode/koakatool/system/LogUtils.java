package com.supercode.koakatool.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

    public static void debug(String message){
        LOGGER.debug(message);
    }

    public static void info(String message){

        LOGGER.info(message);
    }

    public static void error(String message){
        LOGGER.error(message);
    }
}
