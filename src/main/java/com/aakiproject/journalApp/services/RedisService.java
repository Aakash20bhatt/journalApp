package com.aakiproject.journalApp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try{
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public void set(String key, Object value, Long expireTime) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,jsonValue,expireTime, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
