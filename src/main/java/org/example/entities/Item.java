package org.example.entities;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="item")

public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double price;
    private String kind;

    public Item(){}
    public Item(String name,double price, String kind){
        this.name=name;
        this.price=price;
        this.kind=kind;
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public String getKind(){
        return this.kind;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void setKind(String kind){
        this.kind= kind;
    }

}
