package com.yys.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xyr on 2017/10/20.
 */
@Slf4j
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
                    log.error("【String转Date】出错：", e);
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;

    }
}
