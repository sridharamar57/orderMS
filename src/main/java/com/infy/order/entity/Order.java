package com.infy.order.entity;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orderdetails")
public class Order {
	
	@Id
	@Column(name = "orderid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer orderId;
	@Column(name = "buyerid", nullable = false)
	Integer buyerId;
	@Column( nullable = false)
	double amount;
	LocalDate date;
	@Column( nullable = false)
	String address;
	@Column( nullable = false)
	String status;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="orderid")
	List<ProdOrdered> orderDetails;
	public List<ProdOrdered> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<ProdOrdered> orderDetails) {
		this.orderDetails = orderDetails;
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
	
	
	
//	
//	@Id
//	@Column(name = "plan_id", nullable = false)
//	Integer planId;
//	@Column(name = "plan_name", nullable = false, length = 50)
//	String planName;
//	@Column(name = "national_rate", nullable = false)
//	Integer nationalRate;
//	@Column(name = "local_rate", nullable = false)
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
//	public Order() {
//		super();
//	}

	
}
