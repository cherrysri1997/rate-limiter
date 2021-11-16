package com.charan.ratelimiter.controllers;

import com.charan.ratelimiter.buckets.UserBucketRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("ALL")
@RestController
public class MyController {

    private final UserBucketRepository userBucketRepository;

    public MyController(UserBucketRepository userBucketRepository) {
        this.userBucketRepository = userBucketRepository;
    }

    @GetMapping("/getRateLimitedData")
    public String getRateLimitedData(HttpServletRequest request) {
        String api_key = request.getHeader("X-api-key");
        long availableTokens = this.userBucketRepository.getBucketByAPIKey(api_key).getAvailableTokens();
        return "Here is the rate limited data.... Your API Key is "
                + api_key
                + " and number of tokens available for you are: "
                + availableTokens;
    }
}
