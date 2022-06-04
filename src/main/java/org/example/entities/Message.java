package org.example.entities;


import java.io.Serializable;

// this class is intended for sending messages between the server and the client.
public class Message implements Serializable {


    public static final int getAllItems = 1;
    public static final int recieveAllItems= 2;
    public static final int updateItem= 3;
    public static final int addProduct=4;
    public static final int deleteProduct=5;
    public static final int deleteProductResponse=6;
    public static final int addProductResponse=7;
    public static final int updateAllItems=8;




    //////////////
    private final int msg;

    private Object object;

    public Message(int msg) {
        this.msg = msg;
        this.object=null;
    }

    public Message(int msg, Object object) {
        this.msg = msg;
        this.object = object;
    }

    public int getMsg() {
        return msg;
    }


    public Object getObject() {
        return object;
    }

}
