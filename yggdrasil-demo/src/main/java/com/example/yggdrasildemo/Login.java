package com.example.yggdrasildemo;


import com.example.modle.GeneralAdmin;
import com.example.modle.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;


public class Login extends Application {
    private int width=600,height=430;
    private static void main(String args){launch(args);}

    @FXML
    private TextField userNameT;
    @FXML
    private TextField passWordT;
    @FXML
    private Button userLoginB;
    @FXML
    private Button AdminLoginB;
    @FXML
    private Button registerB;
    @FXML
    private Button SuperAdminLogin;
    private String userName;
    private String password;
    private Boolean isUser;
    private Boolean isSuper;
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<GeneralAdmin> admins = new ArrayList<>();

    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load((getClass().getResource("Login.fxml")));
        primaryStage.setTitle("提瓦特世界树");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root,width,height));
        primaryStage.show();
    }

    public void registerB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        userLoginB.getScene().setRoot(root);
    }


    /*
0          核对账号密码信息方法
     */
    public Boolean checkInformation() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //连接数据库
        String url="jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username="root";
        String password="*****";
        //获取connection连接
        Connection conn = DriverManager.getConnection(url,username,password);
        String sql1 = "select t1.roleIndex,t1.username,t1.password,t1.gender,t1.country,t1.age,t1.goe, t2.thump,t2.eleWf,t2.eleEl from general_role t1\n" +
                "join skill t2 on t1.skill_id = t2.id";
        String sql2 = "select * from general_admin";
        String sql3 = "select * from super_admin";
        Statement stat1 =conn.createStatement();
        Statement stat2 =conn.createStatement();
        Statement stat3 = conn.createStatement();
        ResultSet rs1;
        ResultSet rs2;
        ResultSet rs3;

        /*
        获取输入框的用户名和密码
         */
        String userName = userNameT.getText();
        String passWord = passWordT.getText();

        rs1 = stat1.executeQuery(sql1);//基本角色信息的结果集
        rs2 = stat2.executeQuery(sql2);//管理员信息的结果集
        rs3 = stat3.executeQuery(sql3);//超级管理员结果集


         /*
        判定是否登录成功
         */
        Boolean isLogin=false;

        while(rs1.next()){
            if(isUser) {
                if(rs1.getString("username").equals(userName) && rs1.getString("password").equals(passWord)) {
                    User aUser = new User(rs1.getInt("roleIndex"), rs1.getString("username"), rs1.getString("gender"), rs1.getString("age"), rs1.getString("goe"), rs1.getString("country"), rs1.getString("password"), rs1.getString("thump"), rs1.getString("eleWf"), rs1.getString("eleEl"));
                    Iterator iter = users.iterator();
                    while(iter.hasNext()){
                        iter.next();
                        iter.remove();
                    }
                    users.add(aUser);
                    isLogin=true;
                    break;
                }
            }else if(!isSuper&&!isUser){
                while(rs2.next()) {
                    if(rs2.getString("username").equals(userName) && rs2.getString("password").equals(passWord)){
                        isLogin=true;
                        break;
                    }
                }
            }else if(!isUser&&isSuper){
                while(rs3.next()){
                    if(rs3.getString("username").equals(userName)&&rs3.getString("password").equals(passWord)){
                        isLogin=true;
                    }
                }
                if(isLogin){
                    while(rs2.next()){
                        GeneralAdmin admin = new GeneralAdmin(rs2.getInt("roleIndex"),rs2.getString("username"),rs2.getString("password"),rs2.getString("gender"),rs2.getString("country"),rs2.getString("age"));
                        admins.add(admin);
                    }
                }
            }
            if(isLogin&&!isUser&&!isSuper){
                User aUser = new User(rs1.getInt("roleIndex"), rs1.getString("username"), rs1.getString("gender"), rs1.getString("age"), rs1.getString("goe"), rs1.getString("country"), rs1.getString("password"), rs1.getString("thump"), rs1.getString("eleWf"), rs1.getString("eleEl"));
                users.add(aUser);
            }
        }
        if(!isLogin){
            JOptionPane.showMessageDialog(null, "用户名或密码错误，请重新输入", "登录失败", JOptionPane.ERROR_MESSAGE);
            userNameT.setText("");
            passWordT.setText("");
            if(users.size()>0) {
                Iterator<User> iter = users.iterator();
                while (iter.hasNext()) {
                    iter.next();
                    iter.remove();
                }

            }else {
                Iterator<GeneralAdmin> iter = admins.iterator();
                while (iter.hasNext()) {
                    iter.next();
                    iter.remove();
                }
            }

        }

        return isLogin;
    }


    public void userLoginB(ActionEvent event) throws Exception {
        isUser=true;
        isSuper=false;

        if(checkInformation()){
            Parent root = FXMLLoader.load(getClass().getResource("UserTable.fxml"));
            userLoginB.getScene().setRoot(root);
        }
    }

    public void AdminLoginB(ActionEvent event) throws Exception {
        isUser=false;
        isSuper=false;
        if (checkInformation()){
            Parent root = FXMLLoader.load(getClass().getResource("AdminTable.fxml"));
            AdminLoginB.getScene().setRoot(root);
        }
    }

    public void SuperAdminLogin(ActionEvent event) throws Exception {
        isUser=false;
        isSuper=true;
        if(checkInformation()){
            Parent root = FXMLLoader.load(getClass().getResource("SuperAdminTable.fxml"));
            SuperAdminLogin.getScene().setRoot(root);
        }
    }

}
