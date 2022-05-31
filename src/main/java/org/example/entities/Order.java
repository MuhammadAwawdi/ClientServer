package org.example.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Order implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int OrderID;
    @Column( columnDefinition = "DATE")
    LocalDate OrderDate;
    LocalTime OrderTime;
    Boolean shipping;
    LocalTime OrderArriveTime;
    @ManyToMany
    List<Item> ProductList;
    @ManyToMany
    List<PersonalDesign> PersonalDesignList;
    Boolean Card;

    //constructors
    public Order(){

    }

    public Order(LocalDate orderDate, LocalTime orderTime, Boolean shipping, LocalTime orderArriveTime, List<Item> productList, List<PersonalDesign> personalDesignList, Boolean card) {
        OrderDate = orderDate;
        OrderTime = orderTime;
        this.shipping = shipping;
        OrderArriveTime = orderArriveTime;
        ProductList = productList;
        PersonalDesignList = personalDesignList;
        Card = card;
    }



    //getters and setters


    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
    }


    public LocalTime getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        OrderTime = orderTime;
    }


    public Boolean getShipping() {
        return shipping;
    }

    public void setShipping(Boolean shipping) {
        this.shipping = shipping;
    }

    public LocalTime getOrderArriveTime() {
        return OrderArriveTime;
    }

    public void setOrderArriveTime(LocalTime orderArriveTime) {
        OrderArriveTime = orderArriveTime;
    }

    public List<Item> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Item> productList) {
        ProductList = productList;
    }

    public Boolean getCard() {
        return Card;
    }

    public void setCard(Boolean card) {
        Card = card;
    }

    public List<PersonalDesign> getPersonalDesignList() {
        return PersonalDesignList;
    }

    public void setPersonalDesignList(List<PersonalDesign> personalDesignList) {
        PersonalDesignList = personalDesignList;
    }
}
