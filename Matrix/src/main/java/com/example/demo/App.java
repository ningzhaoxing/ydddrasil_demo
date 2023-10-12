package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private int width=600,height=400;
    public static void main(String args){
        launch(args);
    }
    @FXML
    public TextField textN = new TextField();

    @FXML
    public Button commit;

    public static int n;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("App.fxml"));
        primaryStage.setTitle("行列式计算器");
        primaryStage.setScene(new Scene(root,width,height));
        primaryStage.show();

    }

    //跳转到填写行列式结算页面
    public void commit(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("MatrixCalPage.fxml"));
        commit.getScene().setRoot(root);
//        MatrixCore mx = new MatrixCore();

    }
    //实时监控文本区域n的值，并获取
    public void setTextN(){
        try {
            n = Integer.parseInt(textN.getText());
        }catch(NumberFormatException e){
        }
    }



}
