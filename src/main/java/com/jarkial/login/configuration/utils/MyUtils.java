package com.jarkial.login.configuration.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

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

    public static String getClassName(){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String className = stackTrace[2].getClassName();
        return className;
    }

    public static String getStackTrace(Throwable exception){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }
}
