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
    int compID;

}


