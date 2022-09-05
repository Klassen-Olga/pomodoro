package com.openjfx.scheduler;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MidnightScheduler implements PomodoroScheduler {

    private ScheduledExecutorService midnightScheduler = Executors.newScheduledThreadPool(1);
    private Map<LocalDate, Integer> pomodorosPerDate;
    private static final Logger log= LogManager.getLogger(MidnightScheduler.class);

    private AtomicInteger numberOfPomodorosToday;

    public MidnightScheduler() {
        numberOfPomodorosToday=new AtomicInteger(0);
        pomodorosPerDate = new HashMap<>();
        pomodorosPerDate.put(LocalDate.now(), numberOfPomodorosToday.intValue());
    }

    public void scheduleCollectionOfPomodoros() {
        var currentTime = LocalTime.now();
        var nextRun = LocalTime.MIDNIGHT;
        java.time.Duration duration = java.time.Duration.between(currentTime, nextRun);
        var initialDelay = duration.getSeconds();

        midnightScheduler.scheduleAtFixedRate(() -> {
                    LocalDate now = LocalDate.now();
                    pomodorosPerDate.put(now, numberOfPomodorosToday.get());
                    log.info(numberOfPomodorosToday.get()+" pomodoros collected for "+now);
                    numberOfPomodorosToday.set(0);
                }, initialDelay, TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }

    @Override
    public void incrementNumberOfPomodoros() {
        numberOfPomodorosToday.incrementAndGet();
        pomodorosPerDate.put(LocalDate.now(),numberOfPomodorosToday.get());
    }


    @Override
    public int getNumberOfPomodoros(LocalDate date) {
        if (pomodorosPerDate.containsKey(date)) {
            return pomodorosPerDate.get(date);
        }
        return 0;

    }

    @Override
    public void resetNumberOfPomodoros() {
        numberOfPomodorosToday.set(0);
    }
}
