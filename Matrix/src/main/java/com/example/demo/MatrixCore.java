package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MatrixCore {
    public GridPane gPane;//声明网格布局，存放文本域，来填写行列式各元素的值

    public int n;//获取需要计算几阶行列式
    public TextField[][] textFields;   //声明文本域组件
    @FXML
    public Pane pane;
    @FXML
    public Button compute;
    @FXML
    public TextField showAns;

    public int[][] matrix;//存放矩阵的数组

    public int ans;//计算结果

    //  写一个初始化方法，渲染新的界面
    public void initialize() {
        n=App.n;
        gPane=new GridPane();//创建网格布局
        textFields=new TextField[n][n]; //创建文本域数组

        //通过一个for循环实现将文本域添加到网格布局中
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                textFields[i][j] = new TextField();
                textFields[i][j].setMaxSize(60,60);
                gPane.add(textFields[i][j],i,j,1,1);
                GridPane.setHalignment(textFields[i][j], HPos.CENTER);
            }
        }
        //将网格布局添加到根节点上
        pane.getChildren().add(gPane);
        showAns.setDisable(true);
    }

    //获取到文本域中的矩阵并进行计算
    public void compute(){
        //获取矩阵
        matrix=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                matrix[i][j]=Integer.parseInt(textFields[j][i].getText());
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        //开始计算
        ans=calculateMatrix(matrix);

        showAns.setText(ans+"");

    }

    //通过递归无限展开矩阵计算矩阵
    public int calculateMatrix(int[][] matrix){
        // 检查矩阵是否为方阵
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("输入的矩阵不是方阵");
        }

        // 如果矩阵是2*2的，直接计算行列式的值
        if (matrix.length == 2) {
            int det = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
            return det;
        }

        // 计算行列式的值
        int det = 0;
        for (int i = 0; i < matrix.length; i++) {
            // 创建子矩阵
            int[][] subMatrix = new int[matrix.length - 1][matrix.length - 1];
            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix.length; k++) {
                    if (k < i) {
                        subMatrix[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        subMatrix[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            // 递归计算子矩阵的行列式值
            int subDet = calculateMatrix(subMatrix);

            // 根据行列式的计算公式，计算行列式的值
            det += Math.pow(-1, i) * matrix[0][i] * subDet;
        }

        return det;
    }


}
