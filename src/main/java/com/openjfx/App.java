package com.openjfx;

import com.openjfx.scheduler.MidnightScheduler;
import com.openjfx.scheduler.PomodoroScheduler;
import com.openjfx.timer.Timer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private PomodoroScheduler midnightScheduler = new MidnightScheduler();

    @Override
    public void start(Stage stage) {

        var timer = new Timer(2, 4, 6, midnightScheduler);
        timer.run();

        var scene = new Scene(timer);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}