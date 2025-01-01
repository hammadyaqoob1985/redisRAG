package com.hammady.redisrag.starter.config;


import com.hammady.redisrag.starter.service.RedisDataLoaderService;
import groovy.util.logging.Slf4j;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final RedisDataLoaderService redisDataLoaderService;

    public DataLoader(RedisDataLoaderService redisDataLoaderService) {
        this.redisDataLoaderService = redisDataLoaderService;
    }

    @PostConstruct
    public void init() {
        redisDataLoaderService.load();
    }
    @PreDestroy
    public void clearData() {
        logger.info("calling clear data");
        redisDataLoaderService.deleteAll("faq*");
    }
}
