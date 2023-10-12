package com.example.yggdrasildemo;

import com.example.modle.GeneralAdmin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddAdminStage {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField country;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private ChoiceBox<Integer> age;
    @FXML
    private Button commit;
    @FXML
    private Button exit;

    public void initialize() throws Exception {
        //添加下拉框内容
        gender.getItems().add("男");
        gender.getItems().add("女");
        for(int i = 9999 ; i>=1 ; i--){
            age.getItems().add(i);
        }
    }

    public void commit(ActionEvent event) throws Exception{

        if(password.getText().equals("")||username.getText().equals("")){
            JOptionPane.showMessageDialog(null,"用户名或密码不能为空","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
         /*
        获取文本域添加的信息
         */
        int addId = Login.admins.size()+1;
        String addUsername = username.getText();
        String addPassword = password.getText();
        String addCountry = country.getText();
        String addGender = gender.getValue();
        Integer addAge = age.getValue();

        //将添加的信息封装到Admin
        GeneralAdmin admin = new GeneralAdmin(addId,addUsername,addPassword,addGender,addCountry,addAge.toString());
        Login.admins.add(admin);



        //获取数据库链接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
        String sql = "insert into general_admin (roleIndex,username,password,gender,country,age) values (?,?,?,?,?,?)";
        PreparedStatement stat = conn.prepareStatement(sql);

        stat.setInt(1,addId);
        stat.setString(2,addUsername);
        stat.setString(3,addPassword);
        stat.setString(4,addGender);
        stat.setString(5,addCountry);
        stat.setInt(6,addAge);

        int result = stat.executeUpdate();



        if(result>0){
            JOptionPane.showMessageDialog(null,"添加成功!","提示",JOptionPane.OK_OPTION);
            Parent root = FXMLLoader.load(getClass().getResource("SuperAdminTable.fxml"));
            commit.getScene().setRoot(root);
        }

//        conn.close();
//        stat.close();
    }

    public void exit(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SuperAdminTable.fxml"));
        exit.getScene().setRoot(root);
    }
}
