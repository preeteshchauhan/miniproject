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

public class HoldingController implements Initializable {

    @FXML
    private TableView<Userholding> tableView;

    @FXML
    private TableColumn<Userholding, Float> TotalPromotingHoldingcoloumn;

    @FXML
    private TableColumn<Userholding, Float> MutualFundscoloumn;

    @FXML
    private TableColumn<Userholding, Float> ForeignInstitutescoloumn;

    @FXML
    private TableColumn<Userholding, Float> DomesticInstitutescoloumn;

    @FXML
    private TableColumn<Userholding, Float> Retailandotherscoloumn;
    @FXML
    private TableColumn<Userholding, String> Companycoloumn;

    @FXML
    private TextField SearchStock;

    private Parent root;
    private  Stage stage;
    private Scene scene;

    ObservableList<Userholding> userholdingObservableList = FXCollections.observableArrayList();

    public HoldingController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mydatabase connectnow = new mydatabase();
        Connection connectdb = connectnow.connectDb();
        String stockviewQuery = "SELECT total_promoting_holding, mutual_funds, foreign_institutes, domestic_institutes, retail_and_other, company FROM holdings;";

        try {

            Statement statement = connectdb.createStatement();
            ResultSet queryOutput = statement.executeQuery(stockviewQuery);

            while (queryOutput.next()){
                float querytotalpromotingholding = queryOutput.getFloat("total_promoting_holding");
                float querymutualfunds = queryOutput.getFloat("mutual_funds");
                float queryforeigninstitutes = queryOutput.getFloat("foreign_institutes");
                float querydomesticintitutes = queryOutput.getFloat("domestic_institutes");
                float queryretalandothers = queryOutput.getFloat("retail_and_other");
                String querycompany = queryOutput.getString("company");


                userholdingObservableList.add(new Userholding(querycompany, querytotalpromotingholding, querymutualfunds, queryforeigninstitutes, querydomesticintitutes, queryretalandothers));
            }

            TotalPromotingHoldingcoloumn.setCellValueFactory(new PropertyValueFactory<>("totalpromotingholding"));
            MutualFundscoloumn.setCellValueFactory(new PropertyValueFactory<>("mutualfunds"));
            ForeignInstitutescoloumn.setCellValueFactory(new PropertyValueFactory<>("foreigninstitutes"));
            DomesticInstitutescoloumn.setCellValueFactory(new PropertyValueFactory<>("domesticinstitutes"));
            Retailandotherscoloumn.setCellValueFactory(new PropertyValueFactory<>("retailandother"));
            Companycoloumn.setCellValueFactory(new PropertyValueFactory<>("company"));

            tableView.setItems(userholdingObservableList);

            FilteredList<Userholding> filterData = new FilteredList<>(userholdingObservableList, b -> true);

            SearchStock.textProperty().addListener((observable, oldValue, newValue)  -> {
                filterData.setPredicate(user -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String search = newValue.toLowerCase();

                    if (user.getCompany().toLowerCase().indexOf(search) > -1){
                        return true;
                    } else if(user.getTotalpromotingholding().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getMutualfunds().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getDomesticinstitutes().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getForeigninstitutes().toString().indexOf(search) > -1) {
                        return true;
                    }else if(user.getRetailandother().toString().indexOf(search) > -1) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<Userholding> sortedData = new SortedList<>(filterData);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            
            tableView.setItems(sortedData);


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

    public void switchTostatisticstable(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("table.fxml"))); //pass scene name here
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
