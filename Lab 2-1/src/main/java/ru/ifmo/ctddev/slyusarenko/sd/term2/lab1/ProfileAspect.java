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

    private final Map<String, MethodStatistic> methodStatistics = new HashMap<>();
    private String parent = null;
    private Lock lock = new ReentrantLock();
    private ScheduledExecutorService executor;

    @Around("execution(* *(..)) && @annotation(Profileble)")
    public Object profileExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String prevParent = parent;
        parent = joinPoint.toShortString();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        parent = prevParent;
        long finishTime = System.currentTimeMillis();
        try {
            lock.lock();
            methodStatistics.putIfAbsent(joinPoint.toShortString(), new MethodStatistic(joinPoint.toShortString()));
            MethodStatistic methodStatistic = methodStatistics.get(joinPoint.toShortString());
            methodStatistic.addMeasurement(prevParent == null ? null : methodStatistics.get(parent), finishTime - startTime);
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
            for (MethodStatistic methodStatistic : methodStatistics.values()) {
                System.out.println(methodStatistic.toString());
            }
            System.out.println("-----------------------------------------------------");
        } finally {
            lock.unlock();
        }
    }
}
