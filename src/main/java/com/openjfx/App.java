package com.openjfx;

import com.openjfx.scheduler.MidnightScheduler;
import com.openjfx.scheduler.PomodoroScheduler;
import com.openjfx.timer.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/*
* Starting point of JavaFx application
* Schould be called via Launcher class
* */
public class App extends Application {

    final int workingMinutes = 25;
    final int smallPauseMinutes = 5;
    final int longPauseMinutes = 15;

    private PomodoroScheduler midnightScheduler = new MidnightScheduler();

    @Override
    public void start(Stage stage) {

        var timer = new Timer(workingMinutes, smallPauseMinutes, longPauseMinutes, Timer.Unit.MINUTES, midnightScheduler);
        timer.run();

        var scene = new Scene(timer);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}