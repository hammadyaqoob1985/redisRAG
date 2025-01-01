package com.hammady.redisrag.starter.service;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;

@Service
@Slf4j
public class RedisDataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(RedisDataLoaderService.class);

    private final Resource pdfResource;
    private final VectorStore vectorStore;


    public RedisDataLoaderService(@Value("classpath:/data/Employee_Handbook.pdf") Resource pdfResource, VectorStore vectorStore) {
        this.pdfResource = pdfResource;
        this.vectorStore = vectorStore;
    }

    public void load() {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(this.pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1)
                        .build());
        var tokenTextSplitter = new TokenTextSplitter();
        this.vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));
    }

    public void deleteAll(String keyPattern) {
        JedisPooled jedisPooled = ((RedisVectorStore)vectorStore).getJedis();
        ScanParams scanParams = new ScanParams().match(keyPattern);
        String cursor = scanParams.SCAN_POINTER_START;
        do {
            ScanResult<String> scanResult = jedisPooled.scan(cursor, scanParams);
            List<String> keys = scanResult.getResult();
            cursor = scanResult.getCursor();

            if (!keys.isEmpty()) {
                jedisPooled.del(keys.toArray(new String[0]));
                logger.info("Deleted keys: " + keys);
            }
        } while (!cursor.equals(ScanParams.SCAN_POINTER_START));
    }
}
