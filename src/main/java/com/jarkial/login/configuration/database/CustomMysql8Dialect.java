package com.jarkial.login.configuration.database;

import org.hibernate.dialect.MySQL8Dialect;
//import org.hibernate.dialect.function.StandardSQLFunction;
//import org.hibernate.type.StringType;

public class CustomMysql8Dialect extends MySQL8Dialect{
    
    public CustomMysql8Dialect(){
        super();
        //registerFunction("", new StandardSQLFunction("", new StringType()));
    }
}
