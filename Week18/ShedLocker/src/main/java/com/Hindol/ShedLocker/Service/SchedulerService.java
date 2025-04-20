package com.Hindol.ShedLocker.Service;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SchedulerService {
    @Scheduled(fixedRate = 2000)
    @SchedulerLock(name = "shedlock", lockAtLeastFor = "PT2S", lockAtMostFor = "PT5M")
    public void executeTask() {
        System.out.println("Executing Task at - " + new Date());
    }
}
