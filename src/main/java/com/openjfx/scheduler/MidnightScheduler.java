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
        numberOfPomodorosToday=0;
        pomodorosPerDate=new HashMap<>();
    }
    @Override
    public void scheduleCollectionOfPomodoros() {
        LocalTime currentTime=LocalTime.now();
        LocalTime nextRun=LocalTime.MIDNIGHT;
        java.time.Duration duration = java.time.Duration.between(currentTime, nextRun);
        long initialDelay = duration.getSeconds();

        midnightScheduler.scheduleAtFixedRate(()->{
                    pomodorosPerDate.put(LocalDate.now(), numberOfPomodorosToday);
                    numberOfPomodorosToday=0;
                }, initialDelay, TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }
    @Override
    public void incrementNumberOfPomodoros(){
        numberOfPomodorosToday++;
    }
    @Override
    public int getNumberOfPomodoros(){
        return numberOfPomodorosToday;
    }

    @Override
    public void resetNumberOfPomodoros() {
        this.numberOfPomodorosToday=0;
    }
}
