package com.example.miniproject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registercontroller {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField name;
    @FXML
    private TextField email_address;
    @FXML
    private TextField phone_no;
    @FXML
    private TextField userid;
    @FXML
    private TextField password;
    @FXML
    private TextField reenter_password;
    @FXML
    private Button button_register;
    @FXML
    private Label errormessage;

    private ActionEvent event;


    @FXML
    public void validateSignup(ActionEvent event) throws SQLException {
        this.event = event;
        System.out.println("inside done");
        Pattern P = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
        Matcher m = P.matcher(password.getText());
        if (m.matches()) {
            String User_id = userid.getText();
            String Email_address = email_address.getText();
            String Phone_no = phone_no.getText();
            String Name = name.getText();
            String Password = password.getText();
            mydatabase connectnow = new mydatabase();
            Connection connectdb = connectnow.connectDb();
            PreparedStatement psinsert = null;
            PreparedStatement pscheck = null;
            ResultSet resultSet = null;
            psinsert = connectdb.prepareStatement("insert into user_table VALUES (?,?,?,?,?)");
            psinsert.setString(1, User_id);
            psinsert.setString(2, Email_address);
            psinsert.setString(3, Phone_no);
            psinsert.setString(4, Name);
            psinsert.setString(5, Password);


            psinsert.executeUpdate();
            System.out.println("inside scene change");

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception ep) {
                ep.printStackTrace();
            }
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error message");
            alert.setHeaderText("Error Message");
            alert.setContentText("password should have at least one(digit,lowercase,uppercase and special charecter and should be minimun 6-15 charecters long");
            alert.showAndWait();

        }


        String User_id = userid.getText();
        String Email_address = email_address.getText();
        String Phone_no = phone_no.getText();
        String Name = name.getText();
        String Password = password.getText();
        mydatabase connectnow = new mydatabase();
        Connection connectDb = connectnow.connectDb();
        PreparedStatement psinsert = null;
        PreparedStatement pscheck = null;
        ResultSet resultSet = null;
        if (!userid.getText().isBlank() && !email_address.getText().isBlank() && !phone_no.getText().isBlank() && !name.getText().isBlank() && !password.getText().isBlank()) {
            try {
                pscheck = connectDb.prepareStatement("select * from user_table where userid= ?");
                pscheck.setString(1, User_id);
                resultSet = pscheck.executeQuery();
                if (resultSet.isBeforeFirst()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                   // alert.setContentText("YOU CANNOT USE THIS USERNAME.");
                    // alert.show();
                } else {

                    psinsert = connectDb.prepareStatement("insert into user_table VALUES (?,?,?,?,?)");
                    psinsert.setString(1, User_id);
                    psinsert.setString(2, Email_address);
                    psinsert.setString(3, Phone_no);
                    psinsert.setString(4, Name);
                    psinsert.setString(5, Password);
                    psinsert.executeUpdate();


                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                        Parent root1 = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception ep) {
                        ep.printStackTrace();
                    }

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void switchTologin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("firstpage.fxml"))); //pass scene name here
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}
