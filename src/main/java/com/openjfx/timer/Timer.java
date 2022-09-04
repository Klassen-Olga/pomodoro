package com.openjfx.timer;

import com.openjfx.scheduler.PomodoroScheduler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Timer extends Pane {

    private int seconds;
    private boolean isCurrentRoundWork;
    private int numberOfBreaks;
    private final int initialWorkingSeconds;
    private final int initialSmallPauseSeconds;
    private final int initialLongPauseSeconds;
    private PomodoroScheduler pomodoroScheduler;

    private TimerLayout timerLayout;

    public Timer(int workingMinutes, int smallPauseMinutes, int longPauseMinutes,
                 PomodoroScheduler pomodoroScheduler) {
        this.pomodoroScheduler=pomodoroScheduler;
        numberOfBreaks=0;
        isCurrentRoundWork=true;
        //*60
        initialWorkingSeconds = workingMinutes;
        initialSmallPauseSeconds = smallPauseMinutes;
        initialLongPauseSeconds = longPauseMinutes;
        seconds = workingMinutes;
        //

        this.timerLayout=new TimerLayout(getTimeLabelValue(),getNumberOfPomodorosValue());
        this.getChildren().addAll(timerLayout);
    }

    public void run() {

        pomodoroScheduler.scheduleCollectionOfPomodoros();

        Timeline timerTimeline =
                new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
                    seconds--;
                    System.out.println(seconds);
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



    private void notifyUser(){
        String workOrBreak=isCurrentRoundWork?"work":"break";
        Notifications.create()
                .title("Info")
                .text("The "+workOrBreak+" is over")
                .showInformation();
    }
    private void switchBetweenWorkAndPause(){
        if (isCurrentRoundWork){
            pomodoroScheduler.incrementNumberOfPomodoros();
            timerLayout.setPomodoroNumberLabelValue(getNumberOfPomodorosValue());
            System.out.println("now break");
            isCurrentRoundWork=false;
            numberOfBreaks++;
            seconds=numberOfBreaks>=4?initialLongPauseSeconds:initialSmallPauseSeconds;
            if (numberOfBreaks>=4){
                numberOfBreaks=0;
            }
        }
        else{
            System.out.println("now work");
            isCurrentRoundWork=true;
            seconds=initialWorkingSeconds;
        }
    }
    private String getTimeLabelValue(){
        String breakOrWork=isCurrentRoundWork?"Work: ":"Break: ";
        return breakOrWork+ seconds;
        //return breakOrWork+String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }
    private String getNumberOfPomodorosValue(){
        return "Number of pomodoros today: " + pomodoroScheduler.getNumberOfPomodoros();
    }

    private void setListenersOnButtons(Timeline timerTimeline){
        timerLayout.getStartButton().setOnAction(actionEvent -> timerTimeline.play());
        timerLayout.getPauseButton().setOnAction(actionEvent -> timerTimeline.stop());
        timerLayout.getRestartButton().setOnAction(actionEvent -> {

            timerTimeline.stop();

            seconds = initialWorkingSeconds;
            isCurrentRoundWork = true;
            numberOfBreaks = 0;

            pomodoroScheduler.resetNumberOfPomodoros();
            timerLayout.setTimeLabelValue(getNumberOfPomodorosValue());
            timerLayout.setTimeLabelValue(getTimeLabelValue());
            timerTimeline.setCycleCount(seconds);
            timerTimeline.play();
        });
    }

}