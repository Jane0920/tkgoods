package com.yys.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yys.util.LocalDateUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by xyr on 2017/10/19.
 */
public class LocalDate2StrSerialize extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value != null)
            gen.writeString(LocalDateUtil.toStr(value));
        else
            gen.writeString("");
    }
}
