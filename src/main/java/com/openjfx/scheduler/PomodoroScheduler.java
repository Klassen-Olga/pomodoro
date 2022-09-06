package com.openjfx.scheduler;

import java.time.LocalDate;
/**
 * Interface for a scheduler, which collects pomodoros every day at certain points
 */
public interface PomodoroScheduler {

    void scheduleCollectionOfPomodoros();

    void incrementNumberOfPomodoros();

    void resetNumberOfPomodoros();

    int getNumberOfPomodoros(LocalDate date);

}
