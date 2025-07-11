package com.Hindol.Rate_Limiter.Provider.Implementation;

import com.Hindol.Rate_Limiter.Provider.SystemMetricProvider;
import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@Component
public class DefaultMetricProvider implements SystemMetricProvider {

    Logger log = LoggerFactory.getLogger(DefaultMetricProvider.class);

    @Override
    public double getCPULoad() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double load = osBean.getCpuLoad();
        log.info("CPU LOAD - {}", load);
        return load;
    }
}
