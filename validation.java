package com.example.miniproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {
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

    private ActionEvent event;

    public void done(ActionEvent event) throws IOException, SQLException {
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
            alert.setHeaderText("null");
            alert.setContentText("password should have at least one(digit,lowercase,uppercase and special charecter and should be minimun 6-15 charecters long");
            alert.showAndWait();

        }

    }
    private String ValidateInputs() {

        Pattern p = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}");
        Matcher m = p.matcher(email_address.getText());
        boolean valid_email = false;
        if (m.find()){
            valid_email = true;
        }

        //return "Valid";
        if((password.getText().length()==0)||(reenter_password.getText().length()==0)){
            return "Password cannot be empty";
        }
        else if(userid.getText().length()==0) {
            return "Username cannot be empty";
        }
        else if(name.getText().length()==0) {
            return "Name cannot be empty";
        }
        else if(email_address.getText().length()==0) {
            return "E-mail cannot be empty";
        }
        else if(!(password.getText().equals(reenter_password.getText()))){
            return "Passwords must be the same";
        }
        else if(!valid_email){
            return "Please enter valid email";
        }
        else{
            return "Valid";
        }


    }

}