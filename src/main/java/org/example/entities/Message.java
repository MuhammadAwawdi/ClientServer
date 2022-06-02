package org.example.entities;


import java.io.Serializable;

// this class is intended for sending messages between the server and the client.
public class Message implements Serializable {


    public static final int getAllItems = 1;
    public static final int recieveAllItems= 2;
    public static final int updateItem= 3;
    public static final int updateItemComplete= 4;

    //////////////
    private int msg;

    private Object object;

    public Message(int msg) {
        this.msg = msg;
        this.object=null;
    }

    public Message(int msg, Object object) {
        this.msg = msg;
        this.object = object;
    }

    public Message(String s, String buyerEmail) {
    }

    public Message(String s) {
    }

    public int getMsg() {
        return msg;
    }


    public Object getObject() {
        return object;
    }

}
