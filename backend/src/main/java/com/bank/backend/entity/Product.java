package com.bank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@Column(name = "no")
	private int no;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "price")
	private int price;

	@Column(name = "fee_rate")
	private float feeRate;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(float feeRate) {
		this.feeRate = feeRate;
	}

}
