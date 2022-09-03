package org.openjfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX App
 */
public class App extends Application {

    int second=60;

    @Override
    public void start(Stage stage) {

        var timer = new Timer(2, 4, 6);
        timer.run();

        var scene = new Scene(new AnchorPane(timer));

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}