package com.infy.order.dto;
import java.time.LocalDate;
import java.util.List;



import com.infy.order.entity.Order;
import com.infy.order.entity.ProdOrdered;

public class OrderDTO {
	
	Integer orderId;
	Integer buyerId;
	double amount;
	LocalDate date;
	String address;
	String status;
	List<ProdOrdered> productsOrdered;
	
	public List<ProdOrdered> getProductsOrdered() {
		return productsOrdered;
	}
	public void setProductsOrdered(List<ProdOrdered> productsOrdered) {
		this.productsOrdered = productsOrdered;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static OrderDTO valueOf(Order order) {
		OrderDTO orderDTO= new OrderDTO();
		orderDTO.setAddress(order.getAddress());
		orderDTO.setAmount(order.getAmount());
		orderDTO.setBuyerId(order.getBuyerId());
		orderDTO.setDate(order.getDate());
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setStatus(order.getStatus());
		orderDTO.setProductsOrdered(order.getOrderDetails());
		return orderDTO;
	}
	@Override
	public String toString() {
		return "OrderDTO [OrderId=" + orderId + ",BuyerId=" + buyerId +",Date=" + date + ", Address=" + address + ", Amount=" + amount
				+ ", Status=" + status +", OrderDetails=" + productsOrdered + "]";
	}

}
