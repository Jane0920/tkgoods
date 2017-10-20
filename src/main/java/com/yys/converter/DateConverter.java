package com.yys.converter;

import com.yys.util.LocalDateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by xyr on 2017/10/20.
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if(source != null){//如果从浏览器传入字符串不等于开始转换
            source = source.trim();//去除前后空格
            if(source.equals("")){
                source = null;
            }
            if(source!=null){//去除空格后不为空则开始转换
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return simpleDateFormat.parse(source);
                } catch (Exception e) {

                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;

    }
}
