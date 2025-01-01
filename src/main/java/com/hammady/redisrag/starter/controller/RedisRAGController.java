package com.hammady.redisrag.starter.controller;

import com.hammady.redisrag.starter.dto.RedisRAGPrompt;
import com.hammady.redisrag.starter.service.ChatBotService;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("redisrag")
@Slf4j
public class RedisRAGController {

    private static Logger logger = LoggerFactory.getLogger(RedisRAGController.class);
    private final ChatBotService chatBotService;

    public RedisRAGController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    // constructor

    @CrossOrigin
    @PostMapping("/ask")
    public String askRedisRag(@RequestBody RedisRAGPrompt redisRAGPrompt){
        logger.info("calling askRedisRag");
        String generate = chatBotService.chat(redisRAGPrompt.prompt());
        logger.info("completed askRedisRag");
        return generate;
    }

}
