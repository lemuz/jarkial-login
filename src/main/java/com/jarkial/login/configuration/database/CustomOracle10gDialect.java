package com.jarkial.login.configuration.database;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class CustomOracle10gDialect extends Oracle10gDialect{
    
    public CustomOracle10gDialect(){
        super();
        registerFunction("", new StandardSQLFunction("", new StringType()));
    }
}
