/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author vitor
 */
public class IphoneVersion {
    private int id;
    private int storeId;
    private String modelName;
    private String color;
    private String secondaryMemory;
    private Date date;
    private Double cashPayment;
    private String installmentPayment;
    private double ratingAverage;
    private int ratingAmount;
    private String iphoneLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSecondaryMemory() {
        return secondaryMemory;
    }

    public void setSecondaryMemory(String secondaryMemory) {
        this.secondaryMemory = secondaryMemory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public String getInstallmentPayment() {
        return installmentPayment;
    }

    public void setInstallmentPayment(String installmentPayment) {
        this.installmentPayment = installmentPayment;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public int getRatingAmount() {
        return ratingAmount;
    }

    public void setRatingAmount(int ratingAmount) {
        this.ratingAmount = ratingAmount;
    }
    
    public String getIphoneLink() {
        return iphoneLink;
    }

    public void setIphoneLink(String iphoneLink) {
        this.iphoneLink = iphoneLink;
    }
}