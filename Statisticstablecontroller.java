package com.example.miniproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Statisticstablecontroller  implements Initializable {

    @FXML
    private TableView<User> Stocktableview;

    @FXML
    private TableColumn<User, Float> Bookvaluecoloumn;

    @FXML
    private TableColumn<User, Float> Cashcoloumn;

    @FXML
    private TableColumn<User, Float> Debtcoloumn;

    @FXML
    private TableColumn<User, Float> Dividendcoloumn;

    @FXML
    private TableColumn<User, Float> Facevaluecoloumn;

    @FXML
    private TableColumn<User, Float> Marketcapcoloumn;

    @FXML
    private TableColumn<User, Float> Noofsharescoloumn;

    @FXML
    private TableColumn<User, Float> Profitearningratiocoloumn;

    @FXML
    private TableColumn<User, Float> Weekhigh52coloumn;

    @FXML
    private TableColumn<User, Float> Weeklow52coloumn;

    @FXML
    private TableColumn<User, String> Companycoloumn;

    @FXML
    private TextField SearchStock;

    private Parent root;
    private  Stage stage;
    private Scene scene;

    ObservableList<User> usersObservableList = FXCollections.observableArrayList();

    public Statisticstablecontroller() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mydatabase connectnow = new mydatabase();
        Connection connectdb = connectnow.connectDb();
        String stockviewQuery = "SELECT 52_week_high,52_week_low,market_cap_in_CR,profit_earning_ratio,debt,book_values,face_values,no_of_shares_in_lacs,dividend,cash,company FROM statistics;";

        try {

            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(stockviewQuery);

            while (queryOutput.next()){
                float querybookvalue = queryOutput.getFloat("book_values");
                float querycash = queryOutput.getFloat("cash");
                float querydebt = queryOutput.getFloat("debt");
                float querydividend = queryOutput.getFloat("dividend");
                float queryfacevalue = queryOutput.getFloat("face_values");
                float querymarketcap = queryOutput.getFloat("market_cap_in_CR");
                float querynoofshares = queryOutput.getFloat("no_of_shares_in_lacs");
                float queryprofitearningratio = queryOutput.getFloat("profit_earning_ratio");
                float queryweekhigh52 = queryOutput.getFloat("52_week_high");
                float queryweeklow52 = queryOutput.getInt("52_week_low");
                String querycomapny = queryOutput.getString("company");


                usersObservableList.add(new User(querycomapny, queryweekhigh52, queryprofitearningratio, querycash, querybookvalue, querydividend , querynoofshares, queryweeklow52, querymarketcap, queryfacevalue, querydebt));
            }

            Bookvaluecoloumn.setCellValueFactory(new PropertyValueFactory<>("bookvalue"));
            Cashcoloumn.setCellValueFactory(new PropertyValueFactory<>("cash"));
            Debtcoloumn.setCellValueFactory(new PropertyValueFactory<>("debt"));
            Dividendcoloumn.setCellValueFactory(new PropertyValueFactory<>("dividend"));
            Facevaluecoloumn.setCellValueFactory(new PropertyValueFactory<>("facevalue"));
            Marketcapcoloumn.setCellValueFactory(new PropertyValueFactory<>("marketcap"));
            Noofsharescoloumn.setCellValueFactory(new PropertyValueFactory<>("noofshares"));
            Profitearningratiocoloumn.setCellValueFactory(new PropertyValueFactory<>("peratio"));
            Weekhigh52coloumn.setCellValueFactory(new PropertyValueFactory<>("week52high"));
            Weeklow52coloumn.setCellValueFactory(new PropertyValueFactory<>("weeklow52"));
            Companycoloumn.setCellValueFactory(new PropertyValueFactory<>("company"));

            Stocktableview.setItems(usersObservableList);

            FilteredList<User> filterData = new FilteredList<>(usersObservableList, b -> true);

            SearchStock.textProperty().addListener((observable, oldValue, newValue)  -> {
                filterData.setPredicate(user -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                    }

                    String search = newValue.toLowerCase();

                    if (user.getCompany().toLowerCase().indexOf(search) > -1){
                        return true;
                    } else if(user.getWeeklow52().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getDebt().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getWeek52high().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getCash().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getMarketcap().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getBookvalue().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getFacevalue().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getDividend().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getNoofshares().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getPeratio().toString().indexOf(search) > -1) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<User> sortedData = new SortedList<>(filterData);

            sortedData.comparatorProperty().bind(Stocktableview.comparatorProperty());

        }catch (SQLException e){
            Logger.getLogger(Statisticstablecontroller.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }
    public void switchTologinpage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginpage.fxml"))); //pass scene name here
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void switchholdingstable(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("holdingstable.fxml"))); //pass scene name here
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}