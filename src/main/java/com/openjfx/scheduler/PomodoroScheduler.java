package com.openjfx.scheduler;

public interface PomodoroScheduler {

    void scheduleCollectionOfPomodoros();
    void incrementNumberOfPomodoros();
    int getNumberOfPomodoros();
    void resetNumberOfPomodoros();

}
