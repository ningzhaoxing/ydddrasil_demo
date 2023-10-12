package com.example.yggdrasildemo;

import com.example.modle.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.*;

public class AddStage {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
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


    public void initialize() throws Exception {
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

        if(password.getText().equals("")||username.getText().equals("")){
            JOptionPane.showMessageDialog(null,"用户名或密码不能为空","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }

         /*
        获取文本域添加的信息
         */
        int addId = Login.users.size()+1;
        String addUsername = username.getText();
        String addPassword = password.getText();
        String addCountry = country.getText();
        String addSkillThump = skillThump.getText();
        String addSkillEleWf = skillEleWf.getText();
        String addSkillEleEl = skillEleEl.getText();
        String addGender = gender.getValue();
        String addGoe = goe.getValue();
        Integer addAge = age.getValue();

        //将添加的信息封装到User
        User user = new User(addId,addUsername,addGender,addAge.toString(),addGoe,addCountry,addPassword,addSkillThump,addSkillEleWf,addSkillEleEl);
        Login.users.add(user);

        /*
        bug：tableview传参时出现nullpoint异常。
        解决方案：在该类中修改临时存储信息的list，在admintable中实时刷新！
         */


        //获取数据库链接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
        String sql1 = "insert into general_role (roleIndex,username,password,gender,country,age,goe,skill_id) values (?,?,?,?,?,?,?,?)";
        String sql2 = "insert into skill (id,thump,eleWf,eleEl) values (?,?,?,?)";

        PreparedStatement stat2 = conn.prepareStatement(sql2);
        PreparedStatement stat1 = conn.prepareStatement(sql1);

        stat1.setInt(1,addId);
        stat1.setString(2,addUsername);
        stat1.setString(3,addPassword);
        stat1.setString(4,addGender);
        stat1.setString(5,addCountry);
        stat1.setInt(6,addAge);
        stat1.setString(7,addGoe);
        stat1.setInt(8,addId);

        stat2.setInt(1,addId);
        stat2.setString(2,addSkillThump);
        stat2.setString(3,addSkillEleWf);
        stat2.setString(4,addSkillEleEl);


        int result2 = stat2.executeUpdate();
        int result1 = stat1.executeUpdate();

        if(result1>0&&result2>0){
            JOptionPane.showMessageDialog(null,"添加成功!","提示",JOptionPane.OK_OPTION);
            Parent root = FXMLLoader.load(getClass().getResource("AdminTable.fxml"));
            commit.getScene().setRoot(root);
        }

//        conn.close();
//        stat1.close();
//        stat2.close();
    }

    /*
    退出
     */

    public void exit(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AdminTable.fxml"));
        exit.getScene().setRoot(root);
    }
}
