package com.example.yggdrasildemo;

import com.example.modle.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;

import java.sql.*;
import java.util.Iterator;

public class AdminTable {
    @FXML
    public  TableView<User> aTable;
    @FXML
    private TableColumn<User,Integer> id;
    @FXML
    private TableColumn<User,String> username;
    @FXML
    private TableColumn<User,String> password;
    @FXML
    private TableColumn<User,String> gender;
    @FXML
    private TableColumn<User,String> country;
    @FXML
    private TableColumn<User,String> age;
    @FXML
    private TableColumn<User,String> thump;
    @FXML
    private TableColumn<User,String> eleWf;
    @FXML
    private TableColumn<User,String> eleEl;
    @FXML
    private TextField name;
    @FXML
    private TextField cou;
    @FXML
    private Button check;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button exit;
    @FXML
    private Button modify;
    private ObservableList<User> adminList = FXCollections.observableArrayList();
    private ObservableList<User> selectUsers = FXCollections.observableArrayList();
    public static User selectUser;


    public void initialize() throws Exception {
        getRow();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        thump.setCellValueFactory(new PropertyValueFactory<>("thump"));
        eleWf.setCellValueFactory(new PropertyValueFactory<>("eleWf"));
        eleEl.setCellValueFactory(new PropertyValueFactory<>("eleEl"));

        adminList.addAll(Login.users);
        aTable.setItems(adminList);

    }

    /*
    查询业务逻辑功能
     */
    @FXML
    public void check(ActionEvent event) throws Exception {
        //获取文本框中的内容
        String selectName = name.getText();
        String selectCountry = cou.getText();

        aTable.getItems().clear();
        /*
        根据姓名和国家查询(包括模糊查询)
         */
        for (int i = 0; i < Login.users.size(); i++) {
            User user=Login.users.get(i);
            if (user.getUsername().contains(selectName)&&user.getCountry().contains(selectCountry)) {
                selectUsers.add(user);
            }
        }

        aTable.setItems(selectUsers);
        aTable.refresh();
    }

    /*
    增加功能
     */
    public void add(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddStage.fxml"));
        add.getScene().setRoot(root);
    }

    /*
    删除功能
     */
    public void delete(ActionEvent event) throws Exception{
        // 检查是否已选择行
        if (selectUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请先选择一个角色以删除。");
            alert.showAndWait();
            return;
        }

        Login.users.remove(selectUser);
        aTable.refresh();
        check(event);

        //连接数据库
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
        String sql1 = "delete from general_role where roleIndex = ?";
        String sql2 = "delete from skill where id = ?";

        PreparedStatement stat1 = conn.prepareStatement(sql1);
        PreparedStatement stat2 = conn.prepareStatement(sql2);

        stat1.setInt(1,selectUser.getId());
        stat2.setInt(1,selectUser.getId());

        int result1 = stat1.executeUpdate();
        int result2 = stat2.executeUpdate();

//        conn.close();
//        stat1.close();
//        stat2.close();
    }

    /*
    修改
     */

    public void modify(ActionEvent event) throws Exception{
        // 检查是否已选择行
        if (selectUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请先选择一个角色以修改。");
            alert.showAndWait();
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("ModifyStage.fxml"));
        modify.getScene().setRoot(root);
    }



    /*
    退出
     */
    public void exit(ActionEvent event) throws Exception {
        //清空表格内容
        aTable.getItems().clear();
        Iterator<User> iter = Login.users.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        exit.getScene().setRoot(root);

    }

    public void getRow(){
        aTable.setRowFactory(a->{
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(e->{
                if(e.getClickCount()==1&&(!row.isEmpty())){
                    selectUser = row.getItem();
                }
            });
            return row;
        });
    }

}
