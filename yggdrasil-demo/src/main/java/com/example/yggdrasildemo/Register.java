package com.example.yggdrasildemo;


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
import java.sql.ResultSet;

public class Register {
    @FXML
    private TextField username;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;
    @FXML
    private TextField country;
    @FXML
    private TextField skillThump;
    @FXML
    private TextField skillEleWf;
    @FXML
    private TextField skillEleEl;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private ChoiceBox<Integer> age;
    @FXML
    private ChoiceBox<String> goe;
    @FXML
    private Button commit;
    @FXML
    private Button exit;

    public void initialize() throws Exception{
        //添加下拉框内容
        gender.getItems().add("男");
        gender.getItems().add("女");
        for(int i = 1 ; i<=1000 ; i++){
            age.getItems().add(i);
        }
        goe.getItems().add("火系");
        goe.getItems().add("风系");
        goe.getItems().add("草系");
        goe.getItems().add("雷系");
        goe.getItems().add("水系");
        goe.getItems().add("冰系");
        goe.getItems().add("岩系");
    }


    public void commit(ActionEvent event) throws Exception{
         /*
        获取文本框信息
         */
        String name = username.getText();
        String psd1 = password1.getText();
        String psd2 = password2.getText();

        if(name.equals("")||psd1.equals("")||psd2.equals("")){
            JOptionPane.showMessageDialog(null,"用户名或密码不能为空","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!psd1.equals(psd2)){
            JOptionPane.showMessageDialog(null,"两次输入的密码不一致","错误",JOptionPane.ERROR_MESSAGE);
            password1.setText("");
            password2.setText("");
        }else{
            /*
            封装注册的用户信息
             */
//            User user = new User(id,username.getText(),gender.getValue(),age.getValue().toString(),goe.getValue(),country.getText(),password1.getText(),skillThump.getText(),skillEleWf.getText(),skillEleEl.getText());
//            Login.users.add(user);
            //将注册的用户信息添加到数据库
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
            String sql1 = "insert into general_role (roleIndex,username,password,gender,country,age,goe,skill_id) values (?,?,?,?,?,?,?,?)";
            String sql2 = "insert into skill (id,thump,eleWf,eleEl) values (?,?,?,?)";
            String sql3 = "select * from general_role";

            PreparedStatement stat2 = conn.prepareStatement(sql2);
            PreparedStatement stat1 = conn.prepareStatement(sql1);
            PreparedStatement stat3 = conn.prepareStatement(sql3);

            ResultSet rs = stat3.executeQuery();
            int id=1;
            while(rs.next()){
                ++id;
            }

            stat1.setInt(1,id);
            stat1.setString(2,username.getText());
            stat1.setString(3,password1.getText());
            stat1.setString(4,gender.getValue());
            stat1.setString(5,country.getText());
            stat1.setInt(6,age.getValue());
            stat1.setString(7,goe.getValue());
            stat1.setInt(8,id);

            stat2.setInt(1,id);
            stat2.setString(2,skillThump.getText());
            stat2.setString(3,skillEleWf.getText());
            stat2.setString(4,skillEleEl.getText());


            int result2 = stat2.executeUpdate();
            int result1 = stat1.executeUpdate();



            if(result1>0&&result2>0){
                JOptionPane.showMessageDialog(null,"注册成功!","提示",JOptionPane.OK_OPTION);
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                commit.getScene().setRoot(root);
            }
        }
    }

    public void exit(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        exit.getScene().setRoot(root);
    }
}
