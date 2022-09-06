package com.openjfx.timer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Main layout of the timer class, contains all ui elements
 */
public class TimerLayout extends BorderPane {

    private final Label timeLabel;
    private final Button startButton;
    private final Button pauseButton;
    private final Button restartButton;
    private final Label pomodorosNumberLabel;


    public TimerLayout(String labelValue, DatePicker datePicker) {
        pomodorosNumberLabel = new Label("");
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        restartButton = new Button("Restart");
        timeLabel = new Label(labelValue);

        HBox hBox = new HBox(startButton, pauseButton, restartButton);
        hBox.setAlignment(Pos.CENTER);
        VBox vBoxCentral = new VBox(timeLabel, hBox, pomodorosNumberLabel);
        vBoxCentral.setAlignment(Pos.CENTER);
        VBox vBox1TopRight = new VBox(datePicker, pomodorosNumberLabel);

        this.setCenter(vBoxCentral);
        this.setTop(vBox1TopRight);
        this.setMinHeight(450);
        this.setMinWidth(800);


        startButton.getStyleClass().addAll("custom-button");
        pauseButton.getStyleClass().addAll("custom-button");
        restartButton.getStyleClass().addAll("custom-button");
        timeLabel.getStyleClass().addAll("time-label");
        pomodorosNumberLabel.getStyleClass().addAll("number-label");


    }

    public void setTimeLabelValue(String label) {
        this.timeLabel.setText(label);
    }

    public void setPomodoroNumberLabelValue(String label) {
        this.pomodorosNumberLabel.setText(label);
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }
}
