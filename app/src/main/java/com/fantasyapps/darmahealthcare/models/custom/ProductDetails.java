package com.fantasyapps.darmahealthcare.models.custom;

import java.io.Serializable;

public class ProductDetails implements Serializable {
    private String pName;
    private String pDesc;
    private Double pPrice;
    private Integer pQty;
    private Double pAmount;
    private long pId;



    public ProductDetails(String pName, String pDesc, Double pPrice, Integer pQty, Double pAmount, long pId) {
        this.pName = pName;
        this.pDesc = pDesc;
        this.pPrice = pPrice;
        this.pQty = pQty;
        this.pAmount = pAmount;
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public Double getpPrice() {
        return pPrice;
    }

    public void setpPrice(Double pPrice) {
        this.pPrice = pPrice;
    }

    public Integer getpQty() {
        return pQty;
    }

    public void setpQty(Integer pQty) {
        this.pQty = pQty;
    }

    public Double getpAmount() {
        return pAmount;
    }

    public void setpAmount(Double pAmount) {
        this.pAmount = pAmount;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
