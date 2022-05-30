package org.example.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CancelOrderController {

    @FXML
    private TableColumn<?, ?> OrderPrice;

    @FXML
    private TableView<?> ProductTable;

    @FXML
    private Button cancelBottun;

    @FXML
    private Button homebutton;

    @FXML
    private TableColumn<?, ?> orderDate;

    @FXML
    private TableColumn<?, ?> orderId;

    @FXML
    void CancelOrder(ActionEvent event) {

    }

    @FXML
    void goHome(ActionEvent event) {

    }

}

