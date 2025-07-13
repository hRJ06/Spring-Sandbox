# Request Rate Limiter

This application demonstrate how to implement **API request rate limiting** using **Spring Cloud Gateway** and **Redis**.

It uses the built-in `RedisRateLimiter` filter to control the number of requests allowed per client within a fixed time window.

<img width="1217" height="612" alt="REQUEST-RATE-LIMITER" src="https://github.com/user-attachments/assets/946be370-328c-4db2-8fa7-ae5defc76e7a" />

## How It Works?

- Each incoming request passes through Spring Cloud Gateway.
- The `RequestRateLimiter` filter checks Redis to determine whether the client has exceeded their request quota.
- Redis maintains counters using a **token bucket algorithm**.
- If the client is **within the limit** - 
  - The request is forwarded to the backend service.
- If the client **exceeds the limit** - 
  - The request is rejected with an `HTTP 429 Too Many Requests` response.
