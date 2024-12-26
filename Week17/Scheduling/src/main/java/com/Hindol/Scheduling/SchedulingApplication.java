package com.Hindol.Scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Instant;
import java.util.concurrent.*;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAsync
public class SchedulingApplication implements CommandLineRunner {
	@Autowired
	private TaskScheduler taskScheduler;
	@Override
	public void run(String... args) throws Exception {
		/* Dynamic Configuration */
		/*
			taskScheduler.schedule(() -> {
				log.info("Running after 2 sec");
			}, Instant.ofEpochSecond(2));
		 */
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,
				6, 2, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						log.info("Thread rejected. Retrying");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
						executor.submit(r);
                    }
				});
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(6,
				new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				log.info("");
				return new Thread(r, "Thread " + System.nanoTime());
			}
		});
		/*
			scheduledThreadPoolExecutor.schedule(new LongRunningTask("Schedule Task"), 4, TimeUnit.SECONDS);
			log.info("Starting Main Thread - {}", Thread.currentThread().getName());
		*/
		/*
			for(int i = 0; i<20; i++) {
				threadPoolExecutor.submit(new LongRunningTask(i + ""));
				Thread.sleep(1000);
			}
		*/
		/* log.info("Ending Main Thread - {}", Thread.currentThread().getName()); */
	}

	public static void main(String[] args) {
		SpringApplication.run(SchedulingApplication.class, args);
	}

}
