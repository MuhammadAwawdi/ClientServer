package org.example.client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

import org.example.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
//import org.greenrobot.eventbus.EventBus;

public class OrderCancelControl implements Initializable {

    public static ObservableList<Order> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        if (Client.orderList != null) {
            List<Order> Orderslist = Client.orderList;
            loadData(Orderslist);
            System.out.println("done initialize");
            System.out.println("...");
        } else {
            loadData(null);
            System.out.println("done initialize");
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Order> CancelTable;

    @FXML
    private TableColumn<Order, String> MovieCol;

    @FXML
    private TableColumn<Order, String> DateCol;

    @FXML
    private TableColumn<Order, Integer> costCol;

    @FXML
    private TableColumn<Order, String> VisaCol;

    @FXML
    private TextField EmailText;

    @FXML
    private Button CancelButton;

    @FXML
    private Button ShowMovies;

    @FXML
    private Button goHome;

    @FXML
    private Label OrderDetailsLabel;


    @FXML
    void CancelBtn(ActionEvent event) {

        int index = CancelTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        } else {
            Order selctedOrder = CancelTable.getSelectionModel().getSelectedItem();

            AdvancedMsg msg = new AdvancedMsg();
//
            if (selctedOrder.getClass().equals(Order.class)) {

                msg.addobject((Order) selctedOrder);
                msg.setMsg("#deleteOrder");
                LocalTime lt = LocalTime.now();

                if (LocalDate.now().isBefore(selctedOrder.getOrderDate())) {
                    Warning newwarning = new Warning("You will get a full refund(" + selctedOrder.getTotalCost() + ") an email will be sent");
                    //  EventBus.getDefault().post(new WarningEvent((Warning) newwarning));
                    msg.addobject(1.0);
                } else if (LocalDate.now().equals(selctedOrder.getOrderDate())) {

                    long remaininghours = lt.until(((Order) selctedOrder).getOrderArriveTime(), ChronoUnit.HOURS);

                    if (remaininghours >= 1 && remaininghours <= 3) {
                        Warning newwarning = new Warning("You will get a %50 refund (" + selctedOrder.getTotalCost() / 2 + ") an email will be sent");
                        // EventBus.getDefault().post(new WarningEvent((Warning) newwarning));
                        msg.addobject(0.5);


                    } else {
                        Warning newwarning = new Warning("You won't get a refund an email will be sent");
                        //EventBus.getDefault().post(new WarningEvent((Warning) newwarning));
                        msg.addobject(0.0);
                    }
                }

                try {
                    Client.getClient().sendToServer(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
//
        }
    }

    public static void showStage(String Text1) {
        Stage newStage = new Stage();
        VBox comp = new VBox();
        TextField nameField = new TextField(Text1);
        comp.getChildren().add(nameField);

        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
    }

    @FXML
    void ShowBtn(ActionEvent event) {
        if (EmailText.getText().isEmpty()) {
            return;
        }
        String BuyerEmail = EmailText.getText();
        msgObject Message = new msgObject("#getOrders", BuyerEmail);
        try {
            Client.getClient().sendToServer(Message);
            System.out.println("getting the user Orders");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadData(List<Order> OrderList) {
        try {
            if (OrderList != null) {
                list.clear();
                for (Order t : OrderList) {
                    list.add(t);
                }
            } else {
                list.clear();
                CancelTable.getItems().clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        CancelTable.setItems(list);
    }

    public void initCol() {
        MovieCol.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("screeningDate"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        VisaCol.setCellValueFactory(new PropertyValueFactory<>("visaNumber"));

    }

    public static void autoResizeColumns(TableView<?> table)//method to reszie columns taken from StackOverFlow
    {
        //Set the right policy
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach((column) ->
        {
            //Minimal width = columnheader
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < table.getItems().size(); i++) {
                //cell must not be empty
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth(max + 10.0d);
        });
    }

    @FXML
    void goHome(ActionEvent event) {
        if (Client.orderList != null) {
            Client.orderList.clear();
            list.clear();
            CancelTable.getItems().clear();
        }
        try {
            msgObject msg = new msgObject("#getAllOrders");
            Client.getClient().sendToServer(msg);
            System.out.println("message sent to server to get all orders");
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    @FXML
    void ShowDetails(MouseEvent event) {
        String str = "";
        int index = CancelTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        } else {
            Order selctedOrder = CancelTable.getSelectionModel().getSelectedItem();
            if (selctedOrder.getClass().equals(Order.class)) {
                Order tkit = (Order) selctedOrder;
                str = tkit.toString();
                OrderDetailsLabel.setText(str);
            }
        }
    }
}

