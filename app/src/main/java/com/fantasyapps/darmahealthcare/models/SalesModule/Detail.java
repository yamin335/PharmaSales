
package com.fantasyapps.darmahealthcare.models.SalesModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("sale_id")
    @Expose
    private long saleId;
    @SerializedName("product_id")
    @Expose
    private long productId;
    @SerializedName("qty")
    @Expose
    private long qty;
    @SerializedName("total_received")
    @Expose
    private long totalReceived;
    @SerializedName("return_qty")
    @Expose
    private long returnQty;
    @SerializedName("unit_price")
    @Expose
    private long unitPrice;
    @SerializedName("sub_total")
    @Expose
    private long subTotal;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("product")
    @Expose
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public long getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(long totalReceived) {
        this.totalReceived = totalReceived;
    }

    public long getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(long returnQty) {
        this.returnQty = returnQty;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(long subTotal) {
        this.subTotal = subTotal;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
