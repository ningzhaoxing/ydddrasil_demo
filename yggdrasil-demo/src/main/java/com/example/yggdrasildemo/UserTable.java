package com.example.yggdrasildemo;

import com.example.modle.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UserTable {

    @FXML
    private TableView<User> uTable;
    @FXML
    private TableColumn<User,Integer> id;
    @FXML
    private TableColumn<User,String> username;
    @FXML
    private TableColumn<User,String> gender;
    @FXML
    private TableColumn<User,String> goe;
    @FXML
    private TableColumn<User,String> age;
    @FXML
    private TableColumn<User,String> country;
    @FXML
    private TableColumn<User,String> thump;
    @FXML
    private TableColumn<User,String> eleWf;
    @FXML
    private TableColumn<User,String> eleEl;
    @FXML
    private Button exit;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    public void initialize(){

        /*
        使用ableColumn的setCellValueFactory方法将属性绑定
         */
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        goe.setCellValueFactory(new PropertyValueFactory<>("goe"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        thump.setCellValueFactory(new PropertyValueFactory<>("thump"));
        eleWf.setCellValueFactory(new PropertyValueFactory<>("eleWf"));
        eleEl.setCellValueFactory(new PropertyValueFactory<>("eleEl"));
        userList.addAll(Login.users);
        uTable.setItems(userList);
    }


    public void exit(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        exit.getScene().setRoot(root);
    }
}
