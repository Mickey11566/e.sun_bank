package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 喜好設定 DTO
 */
public class PreferenceDto {
    private int sn;
    private String account;
    private String email;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_no")
    private int productNo;

    private int price;

    @JsonProperty("fee_rate")
    private float feeRate;

    @JsonProperty("purchase_quantity")
    private int purchaseQuantity;

    @JsonProperty("total_fee")
    private int totalFee;

    @JsonProperty("total_amount")
    private int totalAmount;

    public PreferenceDto() {}

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(float feeRate) {
        this.feeRate = feeRate;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
