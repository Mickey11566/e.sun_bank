package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 商品資訊 DTO
 */
public class ProductDto {
    private int no;
    
    @NotBlank(message = "商品名稱不可為空")
    @JsonProperty("product_name")
    private String productName;
    
    @Min(value = 0, message = "價格不可為負數")
    private int price;
    
    @Min(value = 0, message = "費率不可為負數")
    @JsonProperty("fee_rate")
    private float feeRate;

    public ProductDto() {}

    public ProductDto(int no, String productName, int price, float feeRate) {
        this.no = no;
        this.productName = productName;
        this.price = price;
        this.feeRate = feeRate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
}
