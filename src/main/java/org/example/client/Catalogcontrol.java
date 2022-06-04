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

import javax.annotation.Resource;
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
    double percentage=1;
    ObservableList<Item> data = FXCollections.observableArrayList();
    @FXML //fx:id="CataButton"
    private Button CataButton;

    @FXML //fx:id="editButton"
    private Button editButton;

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="discountButton"
    private Button discountButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelDiscountButton"
    private Button cancelDiscountButton; // Value injected by FXMLLoader


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
    public static Item itemByPercentage=new Item();

    @FXML
    void addDiscount(ActionEvent event) {
        Button applyDiscount = new Button("Apply");

        Label discountLabel = new Label("Discount Percentage");
        TextField discountTF = new TextField("0");
        discountTF.setMaxWidth(75);
        StackPane discountLayOut = new StackPane();
        StackPane.setAlignment(discountLabel, Pos.CENTER_LEFT);
        StackPane.setAlignment(discountTF, Pos.CENTER_RIGHT);
        StackPane.setAlignment(applyDiscount, Pos.BOTTOM_CENTER);
        discountLayOut.getChildren().addAll(discountLabel, discountTF,applyDiscount);


        Scene discountScene = new Scene(discountLayOut, 230, 100);
        Stage discountWindow = new Stage();
        discountWindow.setTitle("Add Discount");
        discountWindow.setScene(discountScene);
        discountWindow.show();

        applyDiscount.setOnAction(e -> {
            double percentage = (Double.parseDouble(discountTF.getText()));
            for(int i=0; i<ProductTable.getItems().size();i++){
               ProductTable.getItems().get(i).setPrice(percentage*ProductTable.getItems().get(i).getPrice()/100);
                System.out.println(ProductTable.getItems().get(i).getPrice());
                itemByPercentage = ProductTable.getItems().get(i);
                Message percentageMessage = new Message(Message.updateItem,itemByPercentage);
                Client.getClient().sendMessageToServer(percentageMessage);
            }
            handleRefresh(new ActionEvent());
            discountWindow.close();
        });
    }
    @FXML
    void addProduct(ActionEvent event){
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("addProduct.fxml"));
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(parent));
        stage.show();
//        stage.setOnHiding((e) -> {
//            handleRefresh(new ActionEvent());
//        });
    }

    @FXML
    void deleteProudct(ActionEvent event) {
        int index = ProductTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        selectedItem = ProductTable.getSelectionModel().getSelectedItem();
        Message msg = new Message(Message.deleteProduct, selectedItem);
        try {
            Client.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        TextField fourthLabel=new TextField(selectedItem.getName());
        fourthLabel.setMaxWidth(75);
        TextField fifthLabel=new TextField(selectedItem.getKind());
        fifthLabel.setMaxWidth(75);
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
            selectedItem.setName((fourthLabel.getText()));
            selectedItem.setKind((fifthLabel.getText()));
            selectedItem.setPrice(Double.parseDouble(tf.getText()));
            Message message1 = new Message(Message.updateItem, selectedItem);
            Client.getClient().sendMessageToServer(message1);
            newWindow1.close();
        });
    }
        private void handleRefresh(ActionEvent event) {
            try {
                Message msg=new Message(Message.getAllItems);
                Client.getClient().sendToServer(msg);
                System.out.println("message sent to server to get all products");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
}
