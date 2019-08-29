package com.example.springrestapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

// Errors라는 객체를 시리얼라이즈 할때 아래의 @JsonComponent가 붙어있으면
// ObjectMapper에서 알아서 사용하게 됨
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        errors.getFieldErrors().stream().forEach(e -> {
            try{
            gen.writeStartObject();
            gen.writeStringField("field", e.getField());
            gen.writeStringField("objectName", e.getObjectName());
            gen.writeStringField("code", e.getCode());
            gen.writeStringField("defaultMessage", e.getDefaultMessage());
            Object rejectedValue = e.getRejectedValue();
            if(rejectedValue != null){
                gen.writeStringField("rejectedValue", rejectedValue.toString());
            }
            gen.writeEndObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        errors.getGlobalErrors().forEach(e -> {
            try{
                gen.writeStartObject();
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                gen.writeEndObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        gen.writeEndArray();
    }
}
