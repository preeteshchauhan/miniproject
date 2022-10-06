package com.example.miniproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class logincontroller extends NullPointerException{

        @FXML
        private TextField Userid;
        @FXML
        private PasswordField password;
        @FXML
        private TextField setuserid;

        @FXML
        private Label errorPassword;

        @FXML
        private Label erroruserid;


        private Parent root;
        private Stage stage;
        private Scene scene;

        public void onLoginButtonClick(ActionEvent event) throws IOException {
            if (!Userid.getText().isBlank() && !password.getText().isBlank()){
                validateLogin(event);
            }
            else if (Userid.getText().length()==0 && password.getText().length()==0) {
                erroruserid.setText("⚠ Please enter username!");
                Userid.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
                errorPassword.setText("⚠ Please enter password!");
                password.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
            }
            else if (Userid.getText().length()==0) {
                erroruserid.setText("⚠ Please enter username!");
                Userid.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
                errorPassword.setText("");
                password.setStyle("");
            }
            else if (password.getText().length()==0) {
                erroruserid.setText("");
                Userid.setStyle("");
                errorPassword.setText("⚠ Please enter password!");
                password.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
            }
        }

        private void validateLogin(ActionEvent event) {
            mydatabase connectnow = new mydatabase();
            Connection connectdb = connectnow.connectDb();
            String verifylogin = "select count(1) from user_table where userid = '" + Userid.getText() + "' and user_password  = '" + password.getText() + "'";

            Statement statement = null;
            try {
                statement = connectdb.createStatement();
                ResultSet queryResult = statement.executeQuery(verifylogin);
                while(queryResult.next()){
                    if (queryResult.getInt(1)==1){
                        try {
                            String u = (Userid.getText());
                            root = FXMLLoader.load(getClass().getResource("searchpage.fxml")); //pass scene name here
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
                        erroruserid.setText("");
                        Userid.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
                        Userid.setText("");
                        errorPassword.setText("⚠ Invalid User!");
                        password.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 15px");
                        password.setText("");
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        public void switchToForgotPassword(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("forgot_password.fxml")); //pass scene name here
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.show();
            stage.setScene(scene);
            stage.show();
        }

        public void switchToSignUp(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("sign-up.fxml")); //pass scene name here
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

