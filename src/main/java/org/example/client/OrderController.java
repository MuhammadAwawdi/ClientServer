package org.example.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.App;
import org.example.entities.Item;
import org.example.entities.Message;
//import org.example.server.DataBase;
import org.example.server.Server;
import org.example.client.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class OrderController implements Initializable{
    double percentage = 1;
    ObservableList<Item> data = FXCollections.observableArrayList();
    @FXML
    private TextField CVVTF;

    @FXML
    private Button CancelB;

    @FXML
    private TextField CardNumberTF;

    @FXML
    private Button OrderB;

    @FXML
    private Button homebutton;

    @FXML
    private TableView<Item> ProductTable;

    @FXML
    private TableColumn<Item, String> imageCol;

    @FXML
    private TableColumn<Item, String> productName;

    @FXML
    private TableColumn<Item, String> productKind;

    @FXML
    private TableColumn<Item, Double> productPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        List<Item> myList = (List<Item>) Client.data;
        loadData(myList);

    }
    private void initCol() {
        try {
            imageCol.setCellValueFactory(new PropertyValueFactory<>("ImgURL"));
            imageCol.setCellFactory(param -> new ImageTableCell<>());
            imageCol.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.30));
            ;

            productName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productName.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.30));

            productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            productPrice.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

            productKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
            productKind.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void loadData(List<Item> myItems) {
        try {
            data.clear();
            for (Item m : myItems) {
                data.add(m);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ProductTable.setItems(data);
    }

    @FXML
    void CancelOrder(ActionEvent event) {

    }

    @FXML
    void Order(ActionEvent event) {

    }

    @FXML
    void goHome(ActionEvent event) {
        try {
            App.setRoot("cata");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}