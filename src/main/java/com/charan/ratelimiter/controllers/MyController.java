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

//    private final Bucket bucket;

//    public MyController() {
//        int capacity = 50;
//        Refill refill = Refill.intervally(capacity, Duration.ofMinutes(60));
//        Bandwidth limit = Bandwidth.classic(capacity, refill);
//        this.bucket = Bucket4j
//                .builder()
//                .addLimit(limit)
//                .build();
//        System.out.println(this.bucket.getAvailableTokens());
//
//    }

//    @GetMapping("/getRateLimitedData")
//    public ResponseEntity<String> getRateLimitedData() {
//        ConsumptionProbe consumptionProbe = this.bucket.tryConsumeAndReturnRemaining(1);
//        if(consumptionProbe.isConsumed()) {
//            return ResponseEntity
//                    .ok()
//                    .header("X-Rate-Limit-Remaining", Long.toString(consumptionProbe.getRemainingTokens()))
//                    .body("Number of tokens remaining are: " + consumptionProbe.getRemainingTokens());
//        }
//        return ResponseEntity
//                .status(HttpStatus.TOO_MANY_REQUESTS)
//                .header("X-Rate-Limit-Retry-After-Seconds", Long.toString(
//                        TimeUnit.NANOSECONDS.toMillis(consumptionProbe.getNanosToWaitForRefill())/1000
//                    )
//                )
//                .build();
//    }
}
