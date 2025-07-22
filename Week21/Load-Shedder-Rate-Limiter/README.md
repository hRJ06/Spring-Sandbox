# Load Shedding Rate Limiter

This is a Spring Boot application that implement **Load Shedding** based on **system CPU usage**.

The application monitor system CPU load at runtime and shed load by rejecting incoming request.

<img width="1364" height="650" alt="LOAD-SHEDDER-RATE-LIMITER" src="https://github.com/user-attachments/assets/f5d48bc3-5db0-4d73-92b5-97f38be11d5b" />


## How It Works?

- The application use `OperatingSystemMXBean` to monitor real-time CPU usage.
- Each incoming request is evaluated against current system CPU usage.
- If CPU usage is **below** a configured threshold -
  - The request is allowed.
- If CPU usage is **above** the threshold -
  - The system checks whether the request is marked as **critical**.
    - If **critical** → request is allowed.
    - If **not critical** → request is rejected with `HTTP 503 Service Unavailable`.
