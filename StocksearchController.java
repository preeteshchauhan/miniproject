package com.example.miniproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class StocksearchController {
    @FXML
    private TextField searchstock;
    @FXML
    private Button searchbutton;
    @FXML
    private Label errorstock;
    private Parent root;
    private Stage stage;
    private Scene scene;

    public void onSearchbuttonClick(ActionEvent event) throws IOException {
        if (!searchstock.getText().isBlank() ){
            validateserachstock(event);
        }
        else if (searchstock.getText().length()==0 ) {
            errorstock.setText("⚠ Please enter Stockname!");

        }
    }

    public void validateserachstock(ActionEvent event){
        mydatabase connectnow = new mydatabase();
        Connection connectdb = connectnow.connectDb();
        String verifystock = "select count(1) from statistics where company = '" + searchstock.getText() + "'";
        Statement statement = null;
        try {
            statement = connectdb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifystock);
            while(queryResult.next()){
                if (queryResult.getInt(1)==1){
                    try {
                        String u = (searchstock.getText());
                        root = FXMLLoader.load(getClass().getResource("statistics.fxml")); //pass scene name here
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        System.out.println(u);
                        // setuserid(String.valueOf(u));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else {
                    errorstock.setText("");
                    // Userid.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
                    errorstock.setText("Please enter a nift 50 Stock!");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void switchTologin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginpage.fxml"))); //pass scene name here
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}