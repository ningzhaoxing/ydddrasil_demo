package com.example.yggdrasildemo;

import com.example.modle.GeneralAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;

public class SuperAdminTable {
    @FXML
    public TableView<GeneralAdmin> aTable;
    @FXML
    private TableColumn<GeneralAdmin,Integer> id;
    @FXML
    private TableColumn<GeneralAdmin,String> username;
    @FXML
    private TableColumn<GeneralAdmin,String> password;
    @FXML
    private TableColumn<GeneralAdmin,String> gender;
    @FXML
    private TableColumn<GeneralAdmin,String> country;
    @FXML
    private TableColumn<GeneralAdmin,String> age;
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
    private ObservableList<GeneralAdmin> adminList = FXCollections.observableArrayList();
    private ObservableList<GeneralAdmin> selectAdmins = FXCollections.observableArrayList();
    public static GeneralAdmin admins;
    public static GeneralAdmin selectAdmin;

    public void initialize(){
        getRow();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));

        adminList.addAll(Login.admins);
        aTable.setItems(adminList);

    }

    /*
    修改
     */

    public void modify(ActionEvent event) throws Exception{
        // 检查是否已选择行
        if (selectAdmin == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请先选择一个角色以修改。");
            alert.showAndWait();
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("ModifyAdminStage.fxml"));
        modify.getScene().setRoot(root);
    }


    /*
    退出
     */
    public void exit(ActionEvent event) throws Exception {
        //清空表格内容
        aTable.getItems().clear();
        Iterator<GeneralAdmin> iter = Login.admins.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove();
        }

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        exit.getScene().setRoot(root);

    }

    public void getRow(){
        aTable.setRowFactory(a->{
            TableRow<GeneralAdmin> row = new TableRow<>();
            row.setOnMouseClicked(e->{
                if(e.getClickCount()==1&&(!row.isEmpty())){
                    selectAdmin = row.getItem();
                }
            });
            return row;
        });
    }


    public void add(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddAdminStage.fxml"));
        add.getScene().setRoot(root);
    }

    public void check(ActionEvent event) {
        //获取文本框中的内容
        String selectName = name.getText();
        String selectCountry = cou.getText();

        aTable.getItems().clear();
        /*
        根据姓名和国家查询(包括模糊查询)
         */
        for (int i = 0; i < Login.admins.size(); i++) {
            GeneralAdmin admin = Login.admins.get(i);
            if (admin.getUsername().contains(selectName)&&admin.getCountry().contains(selectCountry)) {
                selectAdmins.add(admin);
            }
        }

        aTable.setItems(selectAdmins);
        aTable.refresh();

    }

    /*
   删除功能
    */
    public void delete(ActionEvent event) throws Exception{
        // 检查是否已选择行
        if (selectAdmin == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请先选择一个角色以删除。");
            alert.showAndWait();
            return;
        }

        Login.admins.remove(selectAdmin);
        aTable.refresh();
        check(event);

        //连接数据库
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yggdrasil_demo?&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","*****");
        String sql = "delete from general_admin where roleIndex = ?";

        PreparedStatement stat = conn.prepareStatement(sql);

        stat.setInt(1,selectAdmin.getId());

        int result = stat.executeUpdate();

//        conn.close();
//        stat.close();
    }
}
