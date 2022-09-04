package com.openjfx.timer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TimerLayout extends StackPane {
    private Label timeLabel;
    private Label pomodorosNumberLabel;
    private Button startButton;
    private Button pauseButton;
    private Button restartButton;
    private HBox hBox;
    private VBox vBox;

    public TimerLayout(String labelValue, String pomodorosNumberValue) {
        pomodorosNumberLabel = new Label(pomodorosNumberValue);
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        restartButton = new Button("Restart");
        timeLabel =new Label(labelValue);
        hBox = new HBox(startButton, pauseButton, restartButton);
        hBox.setAlignment(Pos.CENTER);
        vBox=new VBox(timeLabel, hBox, pomodorosNumberLabel);
        vBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(vBox);
        this.setMinHeight(600);
        this.setMinWidth(1000);

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
}
