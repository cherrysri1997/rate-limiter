package com.charan.ratelimiter;

import com.charan.ratelimiter.buckets.UserBucketRepository;
import com.charan.ratelimiter.interceptors.PerUserBucketInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RateLimiterApplication implements WebMvcConfigurer {

    private final UserBucketRepository userBucketRepository;

    public RateLimiterApplication(UserBucketRepository userBucketRepository) {
        this.userBucketRepository = userBucketRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerUserBucketInterceptor(userBucketRepository))
                .addPathPatterns("/*");
    }

    public static void main(String[] args) {
        SpringApplication.run(RateLimiterApplication.class, args);
    }

}
