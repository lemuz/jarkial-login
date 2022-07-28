package com.jarkial.login.configuration.utils;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;

public class MyUtils extends MyUtilsConstant{
    
    public static String cleanForLog(String message){
        String clean = message.replace('\n', '_').replace('\r','_');
        clean = StringEscapeUtils.escapeHtml(message);
        if(!StringUtils.equals(message, clean))
        clean += " (Encoded)";
        return clean;
    }
}
