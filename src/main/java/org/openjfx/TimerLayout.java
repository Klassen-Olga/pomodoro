package org.openjfx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TimerLayout extends Pane {
    private Label label;
    private Button startButton;
    private Button pauseButton;
    private Button restartButton;
    private HBox hBox;
    private VBox vBox;

    public TimerLayout(String labelValue) {
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        restartButton = new Button("Restart");
        label=new Label(labelValue);
        hBox = new HBox(startButton, pauseButton, restartButton);
        vBox=new VBox(label, hBox);
        getChildren().addAll(vBox);
    }

    public void setLabelValue(String label) {
        this.label.setText(label);
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
