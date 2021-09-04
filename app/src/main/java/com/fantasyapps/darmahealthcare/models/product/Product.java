package com.fantasyapps.darmahealthcare.models.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("barcode")
    @Expose
    private Object barcode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("buying_price")
    @Expose
    private long buyingPrice;
    @SerializedName("total_buying_price")
    @Expose
    private long totalBuyingPrice;
    @SerializedName("selling_price")
    @Expose
    private long sellingPrice;
    @SerializedName("mrp")
    @Expose
    private long mrp;
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
    @SerializedName("product_type")
    @Expose
    private ProductType productType;
    @SerializedName("product_type_id")
    @Expose
    private long productTypeId;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("unit_of_measure")
    @Expose
    private UnitOfMeasure unitOfMeasure;
    @SerializedName("unit_of_measure_id")
    @Expose
    private long unitOfMeasureId;
    @SerializedName("branch")
    @Expose
    private Branch branch;
    @SerializedName("branch_id")
    @Expose
    private long branchId;
    @SerializedName("currency")
    @Expose
    private Object currency;
    @SerializedName("currency_id")
    @Expose
    private Object currencyId;
    @SerializedName("warehouse")
    @Expose
    private Warehouse warehouse;
    @SerializedName("warehouse_id")
    @Expose
    private long warehouseId;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

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

    public Object getBarcode() {
        return barcode;
    }

    public void setBarcode(Object barcode) {
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

    public long getTotalBuyingPrice() {
        return totalBuyingPrice;
    }

    public void setTotalBuyingPrice(long totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
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

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public long getUnitOfMeasureId() {
        return unitOfMeasureId;
    }

    public void setUnitOfMeasureId(long unitOfMeasureId) {
        this.unitOfMeasureId = unitOfMeasureId;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Object currencyId) {
        this.currencyId = currencyId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
