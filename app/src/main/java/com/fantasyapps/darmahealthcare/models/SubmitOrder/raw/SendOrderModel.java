package com.fantasyapps.darmahealthcare.models.SubmitOrder.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendOrderModel {

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    @SerializedName("salesman_id")
    @Expose
    private int salesmanId;

    @SerializedName("branch_id")
    @Expose
    private int branchId;

    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("YourReference")
    @Expose
    private String yourReference;
    @SerializedName("OurReference")
    @Expose
    private String ourReference;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    /**
     * No args constructor for use in serialization
     *
     */
    public SendOrderModel() {
    }

    /**
     *
     * @param ourReference
     * @param customerId
     * @param tax
     * @param discountType
     * @param grandTotal
     * @param yourReference
     * @param subtotal
     * @param products
     * @param discount
     */
    public SendOrderModel(String customerId, String subtotal, String yourReference, String ourReference, Integer discount, String discountType, Integer tax, List<Product> products, String grandTotal) {
        super();
        this.customerId = customerId;
        this.subtotal = subtotal;
        this.yourReference = yourReference;
        this.ourReference = ourReference;
        this.discount = discount;
        this.discountType = discountType;
        this.tax = tax;
        this.products = products;
        this.grandTotal = grandTotal;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        this.salesmanId = salesmanId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getYourReference() {
        return yourReference;
    }

    public void setYourReference(String yourReference) {
        this.yourReference = yourReference;
    }

    public String getOurReference() {
        return ourReference;
    }

    public void setOurReference(String ourReference) {
        this.ourReference = ourReference;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

}