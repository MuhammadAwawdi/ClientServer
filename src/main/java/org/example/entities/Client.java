package org.example.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Clients")
public class Client extends User implements Serializable {
    private String ID;
    private long CardNum;
    private int cvv;
    private String AccountType;
    @OneToMany
    private List<Item> itemsList;

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }


    public void addItems(Item item)
    {
        this.itemsList.add(item);
    }
    @OneToMany
    private List<Order> ordersList;

    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    public Client(String id, String username, String password) {
        super(username, password);
        this.ID=id;
    }


    public Client() {

    }
    //__ paymentlist &&  cancellist


    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public long getCardNum() {
        return CardNum;
    }
    public void setCardNum(long cardNum) {
        CardNum = cardNum;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getAccountType() {
        return AccountType;
    }
    public void setAccountType(String accountType) {
        AccountType = accountType;
    }
}
