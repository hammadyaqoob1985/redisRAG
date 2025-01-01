package com.hammady.redisrag.starter.config;

import org.apache.commons.compress.utils.Lists;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.JedisPooled;

@Configuration
public class RedisConfiguration {

    @Primary
    @Bean
    public RedisVectorStore vectorStore(EmbeddingModel embeddingModel,
                                        @Value("${redis.host}") String redisHost,
                                        @Value("${redis.port}") Integer redisPort,
                                        @Value("${redis.username}") String redisUsername,
                                        @Value("${redis.password}") String redisPassword
                                        ) {
        RedisVectorStore.RedisVectorStoreConfig config = RedisVectorStore.RedisVectorStoreConfig.builder()
                .withIndexName("faqs")
                .withPrefix("faq:")
                .withEmbeddingFieldName("embedding")
                .withVectorAlgorithm(RedisVectorStore.Algorithm.HSNW)
                .withMetadataFields(Lists.newArrayList()).build();
        return new RedisVectorStore(config,embeddingModel,new JedisPooled(redisHost, redisPort, redisUsername, redisPassword), true);


    }
}
