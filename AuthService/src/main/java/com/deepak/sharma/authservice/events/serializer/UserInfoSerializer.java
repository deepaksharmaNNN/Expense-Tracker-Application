package com.deepak.sharma.authservice.events.serializer;

import com.deepak.sharma.authservice.dto.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class UserInfoSerializer implements Serializer<UserInfoDto> {
    @Override
    public byte[] serialize(String key, UserInfoDto value) {
        byte[] serializedValue = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            serializedValue = objectMapper.writeValueAsString(value).getBytes();
        }catch (Exception e){
            log.error("Error in serializing object: {}", e.getMessage());
        }
        return serializedValue;
    }
}
