/**
 * Sample Skeleton for 'Catalog.fxml' Controller Class
 */

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

public class Catalogcontrol implements Initializable {

    ObservableList<Item> data = FXCollections.observableArrayList();
    @FXML //fx:id="CataButton"
    private Button CataButton;

    @FXML //fx:id="editButton"
    private Button editButton;

    @FXML // fx:id="homebutton"
    private Button homebutton; // Value injected by FXMLLoader

    @FXML // fx:id="ProductTable"
    private TableView<Item> ProductTable; // Value injected by FXMLLoader

    @FXML // fx:id="productName"
    private TableColumn<Item, String> productName; // Value injected by FXMLLoader

    @FXML // fx:id="productPrice"
    private TableColumn<Item, Double> productPrice; // Value injected by FXMLLoader

    @FXML // fx:id="showProductDetails"
    private Button showProductDetails; // Value injected by FXMLLoader

    @FXML // fx:id="productKind"
    private TableColumn<Item, String> productKind; // Value injected by FXMLLoader
    public static Item selectedItem=new Item();
    


    private  void initCol() {
        try {
            productName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productName.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

            productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            productPrice.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

            productKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
            productKind.prefWidthProperty().bind(ProductTable.widthProperty().multiply(.20));

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        List<Item> myList= (List<Item>) Client.data;
        loadData(myList);

    }
    public void loadData(List<Item> myItems) {
        try {
            data.clear();
            for(Item m: myItems) {
                data.add(m);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        ProductTable.setItems(data);
    }


    @FXML
    void goHome(ActionEvent event) {
        try {
            App.setRoot("cata");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }


    @FXML
    void showDetails(ActionEvent event) {
        selectedItem=ProductTable.getSelectionModel().getSelectedItem();
        Label firstLabel=new Label("Product Name");
        Label secondLabel=new Label("Product Price");
        Label thirdLabel=new Label("Product Kind");
        Label fourthLabel=new Label(selectedItem.getName());
        Label fifthLabel=new Label(selectedItem.getKind());
        Label sixthLabel=new Label(String.valueOf(selectedItem.getPrice()));
        StackPane secondaryLayOut = new StackPane();
        StackPane.setAlignment(fourthLabel,Pos.TOP_CENTER);
        StackPane.setAlignment(firstLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(secondLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(thirdLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(fifthLabel,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(sixthLabel,Pos.CENTER);
        secondaryLayOut.getChildren().addAll(firstLabel,secondLabel,thirdLabel,fourthLabel,fifthLabel,sixthLabel);
        Scene secondScene = new Scene(secondaryLayOut,230,100);

        Stage newWindow = new Stage();
        newWindow.setTitle("Product Details");
        newWindow.setScene(secondScene);
        newWindow.show();

    }
    @FXML
    void goEdit(ActionEvent event) {
        selectedItem=ProductTable.getSelectionModel().getSelectedItem();
        Button saveBtn = new Button("Save");

        Label firstLabel=new Label("Product Name");
        Label secondLabel=new Label("Product Price");
        Label thirdLabel=new Label("Product Kind");
        Label fourthLabel=new Label(selectedItem.getName());
        Label fifthLabel=new Label(selectedItem.getKind());
        TextField tf=new TextField(String.valueOf(selectedItem.getPrice()));
        tf.setMaxWidth(75);
        StackPane secondaryLayOut1 = new StackPane();
        StackPane.setAlignment(saveBtn,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(fourthLabel,Pos.TOP_CENTER);
        StackPane.setAlignment(firstLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(secondLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(thirdLabel, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(fifthLabel,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(tf,Pos.CENTER);
        secondaryLayOut1.getChildren().addAll(firstLabel,secondLabel,thirdLabel,fourthLabel,fifthLabel,tf,saveBtn);



        Scene secondScene1 = new Scene(secondaryLayOut1,230,100);
        Stage newWindow1 = new Stage();
        newWindow1.setTitle("Edit Page");
        newWindow1.setScene(secondScene1);
        newWindow1.show();

        saveBtn.setOnAction(e -> {
            selectedItem.setPrice(Double.parseDouble(tf.getText()));
            Message message1 = new Message(Message.updateItem, selectedItem);
            Client.getClient().sendMessageToServer(message1);
            newWindow1.close();
        });
    }
}
