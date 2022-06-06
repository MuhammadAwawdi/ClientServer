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
    public static final int LoggingIn_S=9;
    public static final int LoggingIn_C=10;
    public static final int SignUp_S=11;
    public static final int SignUp_C=12;
    public static final int Add2Basket=13;
    public static final int Add2BasketRes=14;




    //////////////
    private final int msg;
    private String info_Msg;
    private Object object;


    public Message(int msg,String MsgInfo) {
        this.msg = msg;
        this.object=null;
        this.info_Msg=MsgInfo;
    }

    public Message(int msg) {
        this.msg = msg;
        this.info_Msg="-11";
        this.object=null;

    }
    public Message(int msg, Object object) {
        this.msg = msg;
        this.info_Msg="-11";
        this.object = object;

    }
    public Message(int msg, Object object,String MsgInfo) {
        this.msg = msg;
        this.info_Msg=MsgInfo;
        this.object = object;

    }

    public int getMsg() {
        return msg;
    }


    public Object getObject() {
        return object;
    }

}
