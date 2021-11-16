package com.charan.ratelimiter.buckets;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserBucketRepository {
    private final Map<String, Bucket> all_buckets;

    private static final int capacity = 50;

    UserBucketRepository() {
        this.all_buckets = new ConcurrentHashMap<>();
    }

    private Bucket getNewBucket() {
        return Bucket4j
                .builder()
                .addLimit(
                        Bandwidth.classic(
                                capacity,
                                Refill.intervally(
                                        capacity,
                                        Duration.ofMinutes(60)
                                )
                        )
                )
                .build();
    }

    public Bucket getBucketByAPIKey(String apiKey) {
        return this.all_buckets.computeIfAbsent(apiKey, key -> getNewBucket());
    }
}
