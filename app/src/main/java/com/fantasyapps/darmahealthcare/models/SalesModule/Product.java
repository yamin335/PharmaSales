
package com.fantasyapps.darmahealthcare.models.SalesModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("buying_price")
    @Expose
    private long buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private long sellingPrice;
    @SerializedName("mrp")
    @Expose
    private long mrp;
    @SerializedName("product_type_id")
    @Expose
    private long productTypeId;
    @SerializedName("expired_date")
    @Expose
    private String expiredDate;
    @SerializedName("re_order_level")
    @Expose
    private long reOrderLevel;
    @SerializedName("available_qty")
    @Expose
    private long availableQty;
    @SerializedName("actual_qty")
    @Expose
    private long actualQty;
    @SerializedName("category_id")
    @Expose
    private long categoryId;
    @SerializedName("unit_of_measure_id")
    @Expose
    private long unitOfMeasureId;
    @SerializedName("branch_id")
    @Expose
    private long branchId;
    @SerializedName("currency_id")
    @Expose
    private Object currencyId;
    @SerializedName("warehouse_id")
    @Expose
    private long warehouseId;
    @SerializedName("is_deleted")
    @Expose
    private long isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(long buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getMrp() {
        return mrp;
    }

    public void setMrp(long mrp) {
        this.mrp = mrp;
    }

    public long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public long getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(long reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public long getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(long availableQty) {
        this.availableQty = availableQty;
    }

    public long getActualQty() {
        return actualQty;
    }

    public void setActualQty(long actualQty) {
        this.actualQty = actualQty;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getUnitOfMeasureId() {
        return unitOfMeasureId;
    }

    public void setUnitOfMeasureId(long unitOfMeasureId) {
        this.unitOfMeasureId = unitOfMeasureId;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public Object getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Object currencyId) {
        this.currencyId = currencyId;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(long isDeleted) {
        this.isDeleted = isDeleted;
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

}
