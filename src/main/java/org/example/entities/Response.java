package org.example.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
public class Response  implements Serializable {

    @Id
    @GeneratedValue
    String respone;
    double financialComp;
    int complainID;

    public Response (){

    }

    public Response(String respone, double financialComp, int complainID) {
        this.respone = respone;
        this.financialComp = financialComp;
        this.complainID = complainID;
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }

    public double getFinancialComp() {
        return financialComp;
    }

    public void setFinancialComp(double financialComp) {
        this.financialComp = financialComp;
    }

    public int getComplainID() {
        return complainID;
    }

    public void setComplainID(int complainID) {
        this.complainID = complainID;
    }
}


