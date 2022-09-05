package com.openjfx;

import com.openjfx.scheduler.MidnightScheduler;
import com.openjfx.scheduler.PomodoroScheduler;
import com.openjfx.timer.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    final int workingMinutes=90;
    final int smallPauseMinutes=5;
    final int longPauseMinutes=15;

    private PomodoroScheduler midnightScheduler = new MidnightScheduler();

    @Override
    public void start(Stage stage) {

        var timer = new Timer(workingMinutes, smallPauseMinutes, longPauseMinutes, midnightScheduler);
        timer.run();

        var scene = new Scene(timer);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}