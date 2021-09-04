
package com.fantasyapps.darmahealthcare.models.SalesPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sale_id")
    @Expose
    private Integer saleId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("total_received")
    @Expose
    private Integer totalReceived;
    @SerializedName("return_qty")
    @Expose
    private Integer returnQty;
    @SerializedName("unit_price")
    @Expose
    private Integer unitPrice;
    @SerializedName("sub_total")
    @Expose
    private Integer subTotal;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(Integer totalReceived) {
        this.totalReceived = totalReceived;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
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
