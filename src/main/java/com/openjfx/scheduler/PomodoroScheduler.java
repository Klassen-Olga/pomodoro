package com.openjfx.scheduler;

import java.time.LocalDate;

public interface PomodoroScheduler {

    void scheduleCollectionOfPomodoros();

    void incrementNumberOfPomodoros();

    void resetNumberOfPomodoros();

    int getNumberOfPomodoros(LocalDate date);

}
