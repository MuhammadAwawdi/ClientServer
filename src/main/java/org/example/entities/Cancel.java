package org.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Cancel {

    @Id
    @GeneratedValue
    int cancelId;
    int orderID;
    int clientID;
    double returnedVal;

    public Cancel() {
    }

    public Cancel(int orderID, int clientID, double returnedVal) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.returnedVal = returnedVal;
    }

    public int getCancelId() {
        return cancelId;
    }

    public void setCancelId(int cancelId) {
        this.cancelId = cancelId;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public double getReturnedVal() {
        return returnedVal;
    }

    public void setReturnedVal(double returnedVal) {
        this.returnedVal = returnedVal;
    }
}
