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
import java.io.IOException;
import java.sql.*;

public class ModifyStage {
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
    private int id = AdminTable.selectUser.getId();//获取选中行角色的id

    public void initialize() throws Exception {
        /*
        初始化信息
         */
        username.setText(AdminTable.selectUser.getUsername());
        password.setText(AdminTable.selectUser.getPassword());
        country.setText(AdminTable.selectUser.getCountry());
        skillEleEl.setText(AdminTable.selectUser.getEleEl());
        skillEleWf.setText(AdminTable.selectUser.getEleWf());
        skillThump.setText(AdminTable.selectUser.getThump());
        gender.setValue(AdminTable.selectUser.getGender());
        age.setValue(Integer.valueOf(AdminTable.selectUser.getAge()));
        goe.setValue(AdminTable.selectUser.getGoe());

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

    public void commit(ActionEvent event) throws Exception {
        /*
        对修改后的角色信息进行封装
         */
        //修改缓存集合中的数据
        for (int i = 0; i < Login.users.size(); i++) {
            User user = Login.users.get(i);
            if(user.getId()==id){
                //重新对user进行封装
                user.setUsername(username.getText());
                user.setPassword(password.getText());
                user.setGender(gender.getValue());
                user.setCountry(country.getText());
                user.setAge(age.getValue().toString());
                user.setGoe(goe.getValue());
                user.setThump(skillThump.getText());
                user.setEleEl(skillEleWf.getText());
                user.setEleWf(skillEleEl.getText());
            }
        }

        //修改数据库数据
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
        String sql1 = "update general_role set `username` = ?, `password` = ?, `gender` = ?, `country` = ?, `age` = ?, `goe` = ? where `roleIndex` = ?";
        String sql2 = "update skill set `thump` = ?, `eleWf` = ?, `eleEl` = ? where `id` = ?";

        PreparedStatement stat2 = conn.prepareStatement(sql2);
        PreparedStatement stat1 = conn.prepareStatement(sql1);

        stat1.setString(1,username.getText());
        stat1.setString(2,password.getText());
        stat1.setString(3,gender.getValue());
        stat1.setString(4,country.getText());
        stat1.setString(5,age.getValue().toString());
        stat1.setString(6,goe.getValue());
        stat1.setInt(7,id);

        stat2.setString(1,skillThump.getText());
        stat2.setString(2,skillEleWf.getText());
        stat2.setString(3,skillEleEl.getText());
        stat2.setInt(4,id);

        int result2 = stat2.executeUpdate();
        int result1 = stat1.executeUpdate();



        if(result1>0&&result2>0){
            JOptionPane.showMessageDialog(null,"修改成功!","提示",JOptionPane.OK_OPTION);
            Parent root = FXMLLoader.load(getClass().getResource("AdminTable.fxml"));
            commit.getScene().setRoot(root);
        }
//
//        conn.close();
//        stat1.close();
//        stat2.close();
    }

    public void exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdminTable.fxml"));
        exit.getScene().setRoot(root);
    }

}
