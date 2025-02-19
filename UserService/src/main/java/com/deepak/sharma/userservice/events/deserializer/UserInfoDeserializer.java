package com.deepak.sharma.userservice.events.deserializer;

import com.deepak.sharma.userservice.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class UserInfoDeserializer implements Deserializer<UserInfo> {
    @Override
    public UserInfo deserialize(String key, byte[] value) {
        ObjectMapper mapper = new ObjectMapper();
        UserInfo userInfo = null;
        try {
            userInfo = mapper.readValue(value, UserInfo.class);
        } catch (Exception e) {
            log.error("Error while deserializing UserInfo", e);
        }
        return userInfo;
    }
}
