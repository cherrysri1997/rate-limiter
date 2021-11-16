package com.charan.ratelimiter.interceptors;

import com.charan.ratelimiter.buckets.UserBucketRepository;
import io.github.bucket4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class PerUserBucketInterceptor implements HandlerInterceptor {

    private final UserBucketRepository userBucketRepository;

    public PerUserBucketInterceptor(UserBucketRepository userBucketRepository) {
        this.userBucketRepository = userBucketRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("X-api-key");
        if(apiKey == null || apiKey.isBlank()) {
            response.sendError(401, "No API Key Found in Request Headers.");
            return false;
        }
        Bucket bucket_for_user = this.userBucketRepository.getBucketByAPIKey(apiKey);
        ConsumptionProbe consumptionProbe = bucket_for_user.tryConsumeAndReturnRemaining(1);

        if(consumptionProbe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(consumptionProbe.getRemainingTokens()));
            return true;
        }

        response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(TimeUnit.NANOSECONDS.toMillis(consumptionProbe.getNanosToWaitForRefill())/1000));
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

        return false;
    }
}
