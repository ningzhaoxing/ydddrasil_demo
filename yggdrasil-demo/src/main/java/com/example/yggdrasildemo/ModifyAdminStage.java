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

public class ModifyAdminStage {
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

    private int id = SuperAdminTable.selectAdmin.getId();//获取选中行管理员的id


    public void initialize() throws Exception{
        /*
        初始化信息
         */
        username.setText(SuperAdminTable.selectAdmin.getUsername());
        password.setText(SuperAdminTable.selectAdmin.getPassword());
        country.setText(SuperAdminTable.selectAdmin.getCountry());
        gender.setValue(SuperAdminTable.selectAdmin.getGender());
        age.setValue(Integer.valueOf(SuperAdminTable.selectAdmin.getAge()));

        //添加下拉框内容
        gender.getItems().add("男");
        gender.getItems().add("女");
        for(int i = 1 ; i<=1000 ; i++){
            age.getItems().add(i);
        }

    }


    public void commit(ActionEvent event) throws Exception{

         /*
        对修改后的管理员信息进行封装
         */
        //修改缓存集合中的数据
        for (int i = 0; i < Login.admins.size(); i++) {
            GeneralAdmin admin = Login.admins.get(i);
            if(admin.getId()==id){
                //重新对user进行封装
                admin.setUsername(username.getText());
                admin.setPassword(password.getText());
                admin.setGender(gender.getValue());
                admin.setCountry(country.getText());
                admin.setAge(age.getValue().toString());
            }
        }

        //修改数据库数据
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
        String sql = "update general_admin set `username` = ?, `password` = ?, `gender` = ?, `country` = ?, `age` = ? where `roleIndex` = ?";

        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1,username.getText());
        stat.setString(2,password.getText());
        stat.setString(3,gender.getValue());
        stat.setString(4,country.getText());
        stat.setString(5,age.getValue().toString());
        stat.setInt(6,id);

        int result = stat.executeUpdate();




        if(result>0){
            JOptionPane.showMessageDialog(null,"修改成功!","提示",JOptionPane.OK_OPTION);
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
