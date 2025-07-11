package com.Hindol.Rate_Limiter.Service;

import com.Hindol.Rate_Limiter.Provider.SystemMetricProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadShedderService {
    private static final double MAX_CPU_LOAD = 0.1;

    @Autowired
    private SystemMetricProvider metricProvider;

    public boolean isOverLoaded() {
        double cpuLoad = metricProvider.getCPULoad();
        return cpuLoad > MAX_CPU_LOAD;
    }
}
