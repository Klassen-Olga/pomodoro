package com.openjfx.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MidnightScheduler implements PomodoroScheduler {

    private ScheduledExecutorService midnightScheduler = Executors.newScheduledThreadPool(1);
    private int numberOfPomodorosToday;
    private Map<LocalDate, Integer> pomodorosPerDate;

    public MidnightScheduler() {
        numberOfPomodorosToday = 0;
        pomodorosPerDate = new HashMap<>();
        pomodorosPerDate.put(LocalDate.now(), numberOfPomodorosToday);
    }

    public void scheduleCollectionOfPomodoros() {
        LocalTime currentTime = LocalTime.now();
        LocalTime nextRun = LocalTime.MIDNIGHT;
        java.time.Duration duration = java.time.Duration.between(currentTime, nextRun);
        long initialDelay = duration.getSeconds();

        midnightScheduler.scheduleAtFixedRate(() -> {
                    pomodorosPerDate.put(LocalDate.now(), numberOfPomodorosToday);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    numberOfPomodorosToday = 0;
                }, initialDelay, TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }

    @Override
    public void incrementNumberOfPomodoros() {
        numberOfPomodorosToday++;
        pomodorosPerDate.put(LocalDate.now(),numberOfPomodorosToday);
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
        numberOfPomodorosToday=0;
    }
}
