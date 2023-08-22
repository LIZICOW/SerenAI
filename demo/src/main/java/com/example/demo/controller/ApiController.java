package com.example.demo.controller;

import com.example.demo.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/api")
@RestController
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/sendClaudeRequest")
    public String sendPostRequestController(@RequestParam(name = "question") String question,
                                            @RequestParam(name = "conversationId", defaultValue = "0") String conversationId) {
        String apiUrl = "https://mvdng0.laf.run/claude-chat"; // Replace with the actual API URL
        return apiService.sendPostRequest(apiUrl, question, conversationId);
    }
}
