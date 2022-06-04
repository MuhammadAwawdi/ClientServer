package org.example.client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

import javafx.application.Platform;
import org.example.App;
import org.example.entities.Item;
import org.example.entities.Message;
import org.example.ocsf.client.AbstractClient;

public class Client extends AbstractClient {
    private static final Logger LOGGER =
            Logger.getLogger(Client.class.getName());

    private static Client client = null;
    public static  Object data;



    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public Client(String host, int port) {
        super(host, port);
    }

    @Override
    protected void connectionEstablished() {
        super.connectionEstablished();
        LOGGER.info("Connected to server.");
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Message.class)) {
            Message myMsg = (Message) msg;
            System.out.println("message received from server");
            switch (myMsg.getMsg()) {
                case Message.recieveAllItems:
                    data = myMsg.getObject();
                    try {
                            App.setRoot("Catalog");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                case Message.deleteProductResponse:
                    System.out.println("Product has been deleted");
                    RefreshCatalog();
                break;
                case Message.addProductResponse:
                    System.out.println("Product has been added");
                    RefreshCatalog();
            }
        }
        else{
            System.out.println("ERROR!!!!");
        }
    }

    @Override
    protected void connectionClosed() {
        super.connectionClosed();
        this.closeConnection();
    }

    public void closeConnection() {
        LOGGER.info("Connection closed.");
        System.exit(0);
    }

    public void sendMessageToServer(Message message) {
        try {
            this.sendToServer(message);
        } catch (IOException e) {
            System.out.println("Lost connection with server.");
        }
    }
    public static Client getClient() {
        if (client == null) {
            client = new Client("localhost", 3000);
        }
        return client;
    }
    private void RefreshCatalog(){
        Message new_msg=new Message(Message.getAllItems);
        try {
            Client.getClient().sendToServer(new_msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("message sent to server to refresh the catalog page");
    }

}
