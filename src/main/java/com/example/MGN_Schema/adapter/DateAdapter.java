package com.example.MGN_Schema.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDateTime> {
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override//xml->java
    public LocalDateTime unmarshal(String s) throws Exception {  //unmarshal 成 LocalDateTime
        return LocalDateTime.parse(s, dateFormat);
    }

    @Override //java->xml
    public String marshal(LocalDateTime localDateTime) throws Exception { //marshall成String
        return localDateTime.format(dateFormat);
    }
}
