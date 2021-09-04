package com.fantasyapps.darmahealthcare.models.SubmitOrder.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("selectedProduct")
    @Expose
    private String selectedProduct;
    @SerializedName("product_id")
    @Expose
    private long productId;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param amount
     * @param price
     * @param selectedProduct
     * @param description
     * @param quantity
     * @param productName
     * @param productId
     */
    public Product(String productName, String description, Double price, Integer quantity, Double amount, String selectedProduct, long productId) {
        super();
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.selectedProduct = selectedProduct;
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(String selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}