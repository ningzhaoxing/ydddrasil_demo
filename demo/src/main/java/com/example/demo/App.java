package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private int width=600,height=400;
    public static void main(String args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root= FXMLLoader.load(getClass().getResource("App.fxml"));
        primaryStage.setTitle("行列式计算器");
        primaryStage.setScene(new Scene(root,width,height));
        primaryStage.show();
    }
}
