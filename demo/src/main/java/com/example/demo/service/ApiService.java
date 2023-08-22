package com.example.demo.service;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

public interface ApiService {
    String sendPostRequest(String apiUrl, String question, String conversationId);
}
