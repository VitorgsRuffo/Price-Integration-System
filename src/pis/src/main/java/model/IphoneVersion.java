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
    private String store;
    private String modelName;
    private int secondaryMemory;
    private Date date;
    private String cashPayment;
    private String installmentPayment;
    private double ratingAverage;
    private int ratingAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getSecondaryMemory() {
        return secondaryMemory;
    }

    public void setSecondaryMemory(int secondaryMemory) {
        this.secondaryMemory = secondaryMemory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
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
}