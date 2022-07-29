package com.jarkial.login.configuration.utils;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyUtils extends MyUtilsConstant{

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    public static String cleanForLog(String message){
        String clean = message.replace('\n', '_').replace('\r','_');
        clean = StringEscapeUtils.escapeHtml(message);
        if(!StringUtils.equals(message, clean))
        clean += " (Encoded)";
        return clean;
    }

    public static final String getTransactionId(){
        return StringUtils.defaultString(MDC.get("transacionId"), UUID.randomUUID().toString());
    }

    public static String getBase64Encode(String cadena){
        return new String(Base64.encodeBase64(Base64.encodeBase64(cadena.getBytes())));
    }

    public static String getBase64Decode(String cadena){
        return new String(Base64.decodeBase64(Base64.decodeBase64(cadena.getBytes())));
    }
}
