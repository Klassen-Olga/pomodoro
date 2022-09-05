package com.openjfx.timer;

import com.openjfx.scheduler.MidnightScheduler;
import com.openjfx.scheduler.PomodoroScheduler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;

public class Timer extends Pane implements PomodoroTimer {

    private int seconds;
    private boolean isCurrentRoundWork;
    private int numberOfBreaks;
    private final int initialWorkingSeconds;
    private final int initialSmallPauseSeconds;
    private final int initialLongPauseSeconds;
    private PomodoroScheduler pomodoroScheduler;
    private static final Logger log = LogManager.getLogger(MidnightScheduler.class);

    private TimerLayout timerLayout;
    private Timeline timerTimeline;


    public enum Unit {
        SECONDS, MINUTES
    }

    public Timer(int workingUnits, int smallPause, int longPause, Timer.Unit unit,
                 PomodoroScheduler pomodoroScheduler) {

        this.pomodoroScheduler = pomodoroScheduler;
        numberOfBreaks = 0;
        isCurrentRoundWork = true;
        if (unit.equals(Unit.MINUTES)) {
            initialWorkingSeconds = workingUnits * 60;
            initialSmallPauseSeconds = smallPause * 60;
            initialLongPauseSeconds = longPause * 60;

        } else {
            initialWorkingSeconds = workingUnits;
            initialSmallPauseSeconds = smallPause;
            initialLongPauseSeconds = longPause;
        }
        seconds = initialWorkingSeconds;


        this.timerLayout = new TimerLayout(getTimeLabelValue(), getDatePicker());
        this.getChildren().addAll(timerLayout);
    }

    public void run() {

        pomodoroScheduler.scheduleCollectionOfPomodoros();

        timerTimeline =
                new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
                    seconds--;
                    //System.out.println(seconds);
                    timerLayout.setTimeLabelValue(getTimeLabelValue());
                }));

        timerTimeline.setCycleCount(seconds);

        timerTimeline.setOnFinished(actionEvent -> {
            notifyUser();
            switchBetweenWorkAndPause();
            timerTimeline.setCycleCount(seconds);
            timerTimeline.play();

        });

        setListenersOnButtons(timerTimeline);
    }

    @Override
    public void notifyUser() {
        String workOrBreak = isCurrentRoundWork ? "work" : "break";
        Notifications.create()
                .title("Info")
                .text("The " + workOrBreak + " is over")
                .showInformation();
    }


    private DatePicker getDatePicker() {
        var datePicker = new DatePicker();
        datePicker.setOnAction((event) -> {
            LocalDate date = datePicker.getValue();
            int numberOfPomodoros = pomodoroScheduler.getNumberOfPomodoros(date);
            setNumberOfPomodorosLabelValue(numberOfPomodoros);

        });

        return datePicker;
    }


    private void switchBetweenWorkAndPause() {
        if (isCurrentRoundWork) {
            pomodoroScheduler.incrementNumberOfPomodoros();
            log.info("Break time");
            isCurrentRoundWork = false;
            numberOfBreaks++;
            seconds = numberOfBreaks >= 4 ? initialLongPauseSeconds : initialSmallPauseSeconds;
            if (numberOfBreaks >= 4) {
                numberOfBreaks = 0;
            }
        } else {
            log.info("Work time");
            isCurrentRoundWork = true;
            seconds = initialWorkingSeconds;
        }
    }

    private String getTimeLabelValue() {
        String breakOrWork = isCurrentRoundWork ? "Work: " : "Break: ";
        return breakOrWork + String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    private void setNumberOfPomodorosLabelValue(int num) {
        timerLayout.setPomodoroNumberLabelValue("Number of pomodoros: " + num);
    }

    private void setListenersOnButtons(Timeline timerTimeline) {
        timerLayout.getStartButton().setOnAction(actionEvent -> startButtonAction(timerTimeline));
        timerLayout.getPauseButton().setOnAction(actionEvent -> pauseButtonAction(timerTimeline));
        timerLayout.getRestartButton().setOnAction(actionEvent -> resetButtonAction(timerTimeline));
    }

    public void resetButtonAction(Timeline timerTimeline) {
        timerTimeline.stop();

        seconds = initialWorkingSeconds;
        isCurrentRoundWork = true;
        numberOfBreaks = 0;

        pomodoroScheduler.resetNumberOfPomodoros();
        timerLayout.setTimeLabelValue(getTimeLabelValue());
        timerTimeline.setCycleCount(seconds);
        timerTimeline.play();
    }

    public void pauseButtonAction(Timeline timerTimeline) {
        timerTimeline.stop();
    }

    public void startButtonAction(Timeline timerTimeline) {
        timerTimeline.play();
    }

    public TimerLayout getTimerLayout() {
        return timerLayout;
    }

    public Timeline getTimerTimeline() {
        return timerTimeline;
    }
}
