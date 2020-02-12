/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.entity;

import edu.kist_bit.hamrotarkaribazzar.entity.Product;


/**
 *
 * @author User
 */
public class items {
    private Product p = new Product();
    private int quantity;
    
    public Product getP() {
        return p;
    }
    public void setP(Product P){
        this.p =p;
    }
    
    
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public items(Product p, int quantity){
        super();
        this.p = p;
        this.quantity = quantity;
    }
    public items(){
        super();
    }
}
