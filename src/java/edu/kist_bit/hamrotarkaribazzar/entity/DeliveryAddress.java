/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "delivery_address", catalog = "tarkaribazaar", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeliveryAddress.findAll", query = "SELECT d FROM DeliveryAddress d")
    , @NamedQuery(name = "DeliveryAddress.findById", query = "SELECT d FROM DeliveryAddress d WHERE d.id = :id")
    , @NamedQuery(name = "DeliveryAddress.findByFname", query = "SELECT d FROM DeliveryAddress d WHERE d.fname = :fname")
    , @NamedQuery(name = "DeliveryAddress.findByLname", query = "SELECT d FROM DeliveryAddress d WHERE d.lname = :lname")
    , @NamedQuery(name = "DeliveryAddress.findByAddress1", query = "SELECT d FROM DeliveryAddress d WHERE d.address1 = :address1")
    , @NamedQuery(name = "DeliveryAddress.findByAddress2", query = "SELECT d FROM DeliveryAddress d WHERE d.address2 = :address2")
    , @NamedQuery(name = "DeliveryAddress.findByEmail", query = "SELECT d FROM DeliveryAddress d WHERE d.email = :email")
    , @NamedQuery(name = "DeliveryAddress.findByPhone", query = "SELECT d FROM DeliveryAddress d WHERE d.phone = :phone")})
public class DeliveryAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fname")
    private String fname;
    @Basic(optional = false)
    @Column(name = "lname")
    private String lname;
    @Basic(optional = false)
    @Column(name = "address1")
    private String address1;
    @Basic(optional = false)
    @Column(name = "address2")
    private String address2;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @OneToMany(mappedBy = "deliveryAddId")
    private List<Orders> ordersList;

    public DeliveryAddress() {
    }

    public DeliveryAddress(Integer id) {
        this.id = id;
    }

    public DeliveryAddress(Integer id, String fname, String lname, String address1, String address2, String email, String phone) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.address1 = address1;
        this.address2 = address2;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryAddress)) {
            return false;
        }
        DeliveryAddress other = (DeliveryAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist_bit.hamrotarkaribazzar.entity.DeliveryAddress[ id=" + id + " ]";
    }
    
}
