package com.openjfx.timer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TimerLayout extends StackPane {
    private Label timeLabel;
    private Button startButton;
    private Button pauseButton;
    private Button restartButton;
    private HBox hBox;
    private VBox vBoxCentral;
    private VBox vBox1TopRight;
    private DatePicker datePicker;
    private Label pomodorosNumberLabel;


    public TimerLayout(String labelValue, DatePicker datePicker) {
        pomodorosNumberLabel = new Label("");
        this.datePicker = datePicker;
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        restartButton = new Button("Restart");
        timeLabel = new Label(labelValue);
        hBox = new HBox(startButton, pauseButton, restartButton);
        hBox.setAlignment(Pos.CENTER);
        vBoxCentral = new VBox(timeLabel, hBox, pomodorosNumberLabel);
        vBoxCentral.setAlignment(Pos.CENTER);
        vBox1TopRight = new VBox(datePicker, pomodorosNumberLabel);


        this.getChildren().addAll(vBoxCentral, vBox1TopRight);
        setAlignment(vBox1TopRight, Pos.TOP_RIGHT);
        this.setMinHeight(450);
        this.setMinWidth(800);

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
