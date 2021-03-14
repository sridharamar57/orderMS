package com.infy.order.dto;

public class CartDTO {

	int buyerId;
	int proId;
	Integer quantity;
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getProId() {
		return proId;
	}
	public void setProId(int proId) {
		this.proId = proId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "CartDTO [buyerId=" + buyerId + ", prodId=" + proId + ", quantity=" + quantity + "]";
	}
}
