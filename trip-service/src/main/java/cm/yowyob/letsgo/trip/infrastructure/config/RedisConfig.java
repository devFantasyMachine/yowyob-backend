/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.config;/*
package cm.yowyob.letsgo.trip.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;


*/
/**
 * RedisConfig
 *//*

@Configuration 
public class RedisConfig {


    @Value("${spring.redis.host}")
    String host;

    @Value("${spring.redis.password}")
    String password;

    @Value("${spring.redis.port}")
    Integer port;

    

    @Bean
    @Primary
    RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
         
        return template;
    }  

 
 
   @Bean
    RedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration redisConfig){

    
    	return new JedisConnectionFactory(redisConfig);    	
    } 


    @Bean
    RedisStandaloneConfiguration redisConfiguration(){

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPassword(password);
        redisConfig.setPort(port);

        return redisConfig;
        
    }  


    @Bean
    RedisCacheConfiguration cacheConfiguration() {

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

    }

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {

        return (simpleDraftBuilder) -> simpleDraftBuilder
                .withCacheConfiguration("trips", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(10)))
                .withCacheConfiguration("places", RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(10)));

    }  

    
}*/
