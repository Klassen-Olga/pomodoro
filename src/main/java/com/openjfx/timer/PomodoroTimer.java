package com.openjfx.timer;

/**
 * Interface, which runs the timer and notifies user whenever an event has occurred
 */
public interface PomodoroTimer {
    void run();

    void notifyUser();
}
