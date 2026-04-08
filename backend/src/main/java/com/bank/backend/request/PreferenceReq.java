package com.bank.backend.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 喜好設定請求
 */
public class PreferenceReq {

	private int sn;

	@NotNull(message = "產品編號必填")
	private int productNo;

	@NotNull(message = "購買數量必填")
	@Min(value = 1, message = "購買數量必須大於等於1")
	private int purchaseQuantity;

	@NotBlank(message = "帳號必填")
	private String account;

	// Getters and Setters
	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
