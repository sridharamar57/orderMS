package com.infy.order.dto;

import com.infy.order.entity.ProdOrdered;

public class ProdOrderedDTO {

	Integer orderId;
	Integer productId;
	String status;
	Integer sellerid;
	Integer quantity;
	double price;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getSellerid() {
		return sellerid;
	}
	public void setSellerid(Integer sellerid) {
		this.sellerid = sellerid;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public static ProdOrderedDTO valueOf(ProdOrdered prodOrdered) {
		ProdOrderedDTO orderDTO= new ProdOrderedDTO();
		orderDTO.setSellerid(prodOrdered.getSellerid());
		
		orderDTO.setOrderId(prodOrdered.getOrderId());
		orderDTO.setPrice(prodOrdered.getPrice());
		orderDTO.setProductId(prodOrdered.getProductId());
		orderDTO.setQuantity(prodOrdered.getQuantity());
		orderDTO.setStatus(prodOrdered.getStatus());
		
		return orderDTO;
	}
	@Override
	public String toString() {
		return "ProdOrderedDTO [OrderId=" + orderId + ",ProductId=" + productId +",SellerId=" + sellerid + ",Price=" + price +",Status=" + status + ",Quantity=" + quantity + "]";
	}
}
