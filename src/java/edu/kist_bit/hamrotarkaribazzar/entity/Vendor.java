/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kist_bit.hamrotarkaribazzar.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "vendor", catalog = "tarkaribazaar", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendor.findAll", query = "SELECT v FROM Vendor v")
    , @NamedQuery(name = "Vendor.findById", query = "SELECT v FROM Vendor v WHERE v.id = :id")
    , @NamedQuery(name = "Vendor.findByName", query = "SELECT v FROM Vendor v WHERE v.name = :name")
    , @NamedQuery(name = "Vendor.findByAddress", query = "SELECT v FROM Vendor v WHERE v.address = :address")
    , @NamedQuery(name = "Vendor.findByCity", query = "SELECT v FROM Vendor v WHERE v.city = :city")
    , @NamedQuery(name = "Vendor.findByEmail", query = "SELECT v FROM Vendor v WHERE v.email = :email")
    , @NamedQuery(name = "Vendor.findByPhone", query = "SELECT v FROM Vendor v WHERE v.phone = :phone")})
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    public Vendor() {
    }

    public Vendor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendor)) {
            return false;
        }
        Vendor other = (Vendor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.kist_bit.hamrotarkaribazzar.entity.Vendor[ id=" + id + " ]";
    }
    
}
