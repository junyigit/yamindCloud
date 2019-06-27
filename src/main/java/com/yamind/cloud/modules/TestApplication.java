package com.yamind.cloud.modules;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.LocalDateTime;


public class TestApplication {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void stringTest() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("hello", "redis");
        System.out.println("useRedisDao = " + valueOperations.get("hello"));
    }


    private static void loop() {
        for (Long i = 0L; i < Integer.MAX_VALUE; i++) {}
    }

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        loop();
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("耗时：" + duration.getSeconds() + "s");
    }

}