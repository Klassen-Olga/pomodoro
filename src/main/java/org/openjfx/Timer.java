package org.openjfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Timer extends Pane {

    private int seconds;
    private boolean isCurrentRoundWork;
    private int numberOfBreaks;
    private final int initialWorkingSeconds;
    private final int initialSmallPauseSeconds;
    private final int initialLongPauseSeconds;

    private TimerLayout timerLayout;

    public Timer(int workingMinutes, int smallPauseMinutes, int longPauseMinutes) {
        numberOfBreaks=0;
        isCurrentRoundWork=true;
        //*60
        initialWorkingSeconds = workingMinutes;
        initialSmallPauseSeconds = smallPauseMinutes;
        initialLongPauseSeconds = longPauseMinutes;
        seconds = workingMinutes;
        //

        timerLayout=new TimerLayout(getLabelValue());
        getChildren().addAll(timerLayout);
    }

    public void run() {

        Timeline timerTimeline =
                new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
                    seconds--;
                    System.out.println(seconds);
                    timerLayout.setLabelValue(getLabelValue());
                }));
        timerTimeline.setCycleCount(seconds);

        timerTimeline.setOnFinished(actionEvent -> {
            // send native notifications
            switchBetweenWorkAndPause();
            timerTimeline.setCycleCount(seconds);
            timerTimeline.play();

        });
        setListenersToButtons(timerTimeline);
    }

    private void switchBetweenWorkAndPause(){
        if (isCurrentRoundWork){
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
    private String getLabelValue(){
        return String.valueOf(seconds);
        //return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    private void setListenersToButtons(Timeline timerTimeline){
        timerLayout.getStartButton().setOnAction(actionEvent -> timerTimeline.play());
        timerLayout.getPauseButton().setOnAction(actionEvent -> timerTimeline.stop());
        timerLayout.getRestartButton().setOnAction(actionEvent -> {
            System.out.println("restart");
            timerTimeline.stop();

            seconds=initialWorkingSeconds;
            isCurrentRoundWork=true;
            numberOfBreaks=0;

            timerLayout.setLabelValue(getLabelValue());
            timerTimeline.setCycleCount(seconds);
            timerTimeline.play();
        });
    }

}
