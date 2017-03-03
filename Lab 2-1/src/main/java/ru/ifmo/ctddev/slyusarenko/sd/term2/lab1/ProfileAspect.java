package ru.ifmo.ctddev.slyusarenko.sd.term2.lab1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Maxim Slyusarenko
 * @since 03.03.17
 */
@Component
@Aspect
public class ProfileAspect {

    private final Map<String, Integer> methodInvocationCount = new HashMap<>();
    private final Map<String, Long> methodAverageTime = new HashMap<>();
    private final Map<String, Long> methodSumTimes = new HashMap<>();
    private Lock lock = new ReentrantLock();
    private ScheduledExecutorService executor;

    @Around("execution(* *(..)) && @annotation(Profileble)")
    public Object profileExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long finishTime = System.currentTimeMillis();
        try {
            lock.lock();
            int newCount = methodInvocationCount.compute(joinPoint.toShortString(), (key, value) -> {
                if (value == null) {
                    return 1;
                } else {
                    return value + 1;
                }
            });
            long newSumTimes = methodSumTimes.compute(joinPoint.toShortString(), (key, value) -> {
                if (value == null) {
                    return finishTime - startTime;
                } else {
                    return value + (finishTime - startTime);
                }
            });
            methodAverageTime.put(joinPoint.toShortString(), (long) ((double) newSumTimes / (double) newCount));
        } finally {
            lock.unlock();
        }
        if (executor == null) {
            executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleWithFixedDelay(this::logSchedule, 0, 10, TimeUnit.SECONDS);
        }
        return result;
    }

    private void logSchedule() {
        try {
            lock.lock();
            System.out.println("-----------------------------------------------------");
            System.out.println("Method invocations: ");
            for (Map.Entry<String, Integer> entry : methodInvocationCount.entrySet()) {
                System.out.println("There were " + entry.getValue() + " invocations of method " + entry.getKey());
            }
            System.out.println("Average times: ");
            for (Map.Entry<String, Long> entry : methodAverageTime.entrySet()) {
                System.out.println("Average execution time of method " + entry.getKey() + " is " + entry.getValue() + " ms");
            }
            System.out.println("Sum times: ");
            for (Map.Entry<String, Long> entry : methodSumTimes.entrySet()) {
                System.out.println("Sum of execution times for method " + entry.getKey() + " is " + entry.getValue() + " ms");
            }
            System.out.println("-----------------------------------------------------");
        } finally {
            lock.unlock();
        }
    }
}
