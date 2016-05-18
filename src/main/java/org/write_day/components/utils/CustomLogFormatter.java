package org.write_day.components.utils;

import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

/**
 * Created by LucidMinds on 10.05.16.
 * package com.boltalka.components.utils;
 */
public class CustomLogFormatter extends Log4JdbcCustomFormatter {

    private LoggingType loggingType = LoggingType.DISABLED;

    private String margin = "";

    private String sqlPrefix = "SQL:";


    public int getMargin() {
        return margin.length();
    }

    public void setMargin(int n) {
        margin = String.format("%1$" + n + "s", "");
    }


    public CustomLogFormatter() {
    }

    public LoggingType getLoggingType() {
        return loggingType;
    }

    public void setLoggingType(LoggingType loggingType) {
        this.loggingType = loggingType;
    }

    public String getSqlPrefix()
    {
        return sqlPrefix;
    }

    public void setSqlPrefix(String sqlPrefix)
    {
        this.sqlPrefix = sqlPrefix;
    }

}
