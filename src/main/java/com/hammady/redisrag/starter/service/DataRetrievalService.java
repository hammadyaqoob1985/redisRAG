package com.hammady.redisrag.starter.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRetrievalService {

    private final VectorStore vectorStore;

    public DataRetrievalService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> searchData(String query) {
        return vectorStore.similaritySearch(query);
    }
}
