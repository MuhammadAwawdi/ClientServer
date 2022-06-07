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
