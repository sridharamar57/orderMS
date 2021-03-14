package com.infy.order.dto;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

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
	

//Integer planId;
//	
//	String planName;
//
//	Integer nationalRate;
//
//	Integer localRate;
//	public Integer getPlanId() {
//		return planId;
//	}
//	public void setPlanId(Integer planId) {
//		this.planId = planId;
//	}
//	public String getPlanName() {
//		return planName;
//	}
//	public void setPlanName(String planName) {
//		this.planName = planName;
//	}
//
//	public Integer getNationalRate() {
//		return nationalRate;
//	}
//	public void setNationalRate(Integer nationalRate) {
//		this.nationalRate = nationalRate;
//	}
//	public Integer getLocalRate() {
//		return localRate;
//	}
//	public void setLocalRate(Integer localRate) {
//		this.localRate = localRate;
//	}
//	public OrderDTO() {
//		super();
//	}
//
//	// Converts Entity into DTO
//	public static OrderDTO valueOf(Order plan) {
//		OrderDTO planDTO= new OrderDTO();
//		planDTO.setLocalRate(plan.getLocalRate());
//		planDTO.setNationalRate(plan.getNationalRate());
//		planDTO.setPlanId(plan.getPlanId());
//		planDTO.setPlanName(plan.getPlanName());
//		return planDTO;
//	}
//	@Override
//	public String toString() {
//		return "PlanDTO [planId=" + planId + ", planName=" + planName + ", nationalRate=" + nationalRate
//				+ ", localRate=" + localRate + "]";
//	}
	
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
