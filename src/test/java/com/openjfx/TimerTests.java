package com.openjfx;

import com.openjfx.scheduler.MidnightScheduler;
import com.openjfx.scheduler.PomodoroScheduler;
import com.openjfx.timer.Timer;
import com.openjfx.timer.TimerLayout;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

@ExtendWith(ApplicationExtension.class)
public class TimerTests {
    private PomodoroScheduler midnightScheduler = new MidnightScheduler();
    private Button button;
    private Timer timer;
    TimerLayout timerLayout;
    Timeline timerTimeline;

    @Start
    private void start(Stage stage) {
        timer = new Timer(2, 2, 3, Timer.Unit.SECONDS, midnightScheduler);
        timerLayout = timer.getTimerLayout();
        timer.run();
        timerTimeline = timer.getTimerTimeline();

        stage.setScene(new Scene(timer));
        stage.show();
    }

    @Test
    void timeLabelShouldHaveInitValue() {
        Label timeLabel = timerLayout.getTimeLabel();

        FxAssert.verifyThat(timeLabel, LabeledMatchers.hasText("Work: 00:00:02"));
    }

    @Test
    void afterWorkShouldBeSmallPause() throws InterruptedException {
        Label timeLabel = timerLayout.getTimeLabel();
        timer.startButtonAction(timerTimeline);
        Thread.sleep(4000);

        FxAssert.verifyThat(timeLabel, LabeledMatchers.hasText("Break: 00:00:01"));
    }

    @Test
    void timeLabelShouldBeTheSameOnPause() throws InterruptedException {
        Label timeLabel = timerLayout.getTimeLabel();
        timer.startButtonAction(timerTimeline);
        timer.pauseButtonAction(timerTimeline);
        Thread.sleep(2000);

        FxAssert.verifyThat(timeLabel, LabeledMatchers.hasText("Work: 00:00:02"));
    }

    @Test
    void timeLabelShouldBeTheSameOnReset() throws InterruptedException {
        Label timeLabel = timerLayout.getTimeLabel();
        timer.startButtonAction(timerTimeline);

        Thread.sleep(2000);
        Platform.runLater(
                () -> {
                    timer.resetButtonAction(timerTimeline);
                    FxAssert.verifyThat(timeLabel, LabeledMatchers.hasText("Work: 00:00:02"));
                });


    }

    @Test
    void onFourthBreakShouldBeLongPause() throws InterruptedException {
        Label timeLabel = timerLayout.getTimeLabel();

        timer.startButtonAction(timerTimeline);
        Thread.sleep(16000);

        Platform.runLater(() -> FxAssert.verifyThat(timeLabel, LabeledMatchers.hasText("Break: 00:00:02")));

    }
}
