package com.infy.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.ProdOrderedDTO;
import com.infy.order.dto.ProductDTO;
import com.infy.order.entity.CompositeKey;
import com.infy.order.entity.Order;
import com.infy.order.entity.ProdOrdered;
import com.infy.order.repository.OrderRepository;
import com.infy.order.repository.ProdOrderedRepository;

@Service
public class OrderService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	ProdOrderedRepository prodRepo;

	// Fetches all order details
	public List<OrderDTO> getAllOrderDetails() throws Exception {
		List<Order> orders = orderRepo.findAll();		
		List<OrderDTO> orderDTOs = new ArrayList<>();
		for (Order plan : orders) {
			OrderDTO orderDTO = OrderDTO.valueOf(plan);
			orderDTOs.add(orderDTO);
		}
		
		try {
		if(orderDTOs.isEmpty()) {
			throw new Exception("OrderService.Orders_NOT_FOUND");
		}
		}
		catch(Exception e) {
			throw e;
		}
		logger.info("Order details : {}", orderDTOs);
		return orderDTOs;
	}
	public OrderDTO getSpecificOrder(int orderId) throws Exception {
		logger.info("Order details : {}", orderId);
		OrderDTO orderDTO = null;
		try {
		Optional<Order> optOrder = orderRepo.findById(orderId);
		if (optOrder.isPresent()) {
			Order plan = optOrder.get();
			orderDTO = OrderDTO.valueOf(plan);
		}
		else {
			throw new Exception("OrderService.Orders_NOT_FOUND");
		}
		}
		catch(Exception e) {
			throw e;
		}
		return orderDTO;
	}
	public String deleteSpecificOrder(int orderId) throws Exception{
		logger.info("Deleting Order details : {}", orderId);
		try {
		orderRepo.deleteById(orderId);
		//return "Success";
		}
		catch(Exception e) {
			throw e;
			//return "failure";
		}
		return "Success";
	}
	
	public String addOrderDetails(Order order) throws Exception {
		logger.info("Inserting Order details : {}");
		Order order1=null;
		try {
		order1=orderRepo.save(order);
		//Order b=
		//System.out.println(orderRepo.save(order));
		List<ProdOrdered> lp=order.getOrderDetails();
		//System.out.println(lp);
//		if(!lp.isEmpty()) {
//			for(ProdOrdered po:lp) {
//				prodRepo.save(po);
//			}
//		}
		if(order1==null) {
			throw new Exception("OrderService.ADD_INVALID");
		}
		
		}
		catch(Exception e) {
			throw e;
		}
		return "Success";
	}
	public void updateOrder(Integer orderId, OrderDTO order) {
		Order existingOrder = orderRepo.findById(orderId).orElse(null);
		if (existingOrder !=null) {
			existingOrder.setDate(LocalDate.now());
			existingOrder.setStatus(order.getStatus());
			orderRepo.save(existingOrder);
		}
	}
	//for productsOrdered table
	public List<ProdOrderedDTO> getAllProdsOrdered() throws Exception {
		List<ProdOrdered> orders = prodRepo.findAll();	
		List<ProdOrderedDTO> orderDTOs = new ArrayList<>();
		for (ProdOrdered plan : orders) {
			//System.out.println("Hello"+plan.getPrice());
			ProdOrderedDTO orderDTO = ProdOrderedDTO.valueOf(plan);
			orderDTOs.add(orderDTO);
		}
		try {
			if(orderDTOs.isEmpty()) {
				throw new Exception("ProductsOrdered.NOT_FOUND");
			}
			}
			catch(Exception e) {
				throw e;
			}
		logger.info("Order details : {}", orderDTOs);
		return orderDTOs;
	}
	public ProdOrderedDTO getSpecificProductOrdered(int orderId,int prodId) throws Exception{
		logger.info("Order details : {}", orderId);
		ProdOrderedDTO orderDTO = null;
		try {
		CompositeKey cm =new CompositeKey();
		cm.setOrderId(orderId);
		cm.setProductId(prodId);
		Optional<ProdOrdered> optOrder = prodRepo.findById(cm);
		if (optOrder.isPresent()) {
			ProdOrdered plan = optOrder.get();
			orderDTO = ProdOrderedDTO.valueOf(plan);
		}
		else {
			throw new Exception("ProductsOrdered.NOT_FOUND");
		}
		}
		catch(Exception e) {
			throw e;
		}
		return orderDTO;
	}
	public String deleteSpecificProductOrdered(int orderId,int productId) {
		logger.info("Deleting ProductOrdered details : {}", orderId,productId);
		CompositeKey cm =new CompositeKey();
		cm.setOrderId(orderId);
		cm.setProductId(productId);
		
		try {
			prodRepo.deleteById(cm);
		}
		catch(Exception e) {
			throw e;
		}
		return "Success";
	}
	public String addProdOrderedDetails(ProdOrdered order) throws Exception {
		//System.out.println(order);
		logger.info("Isertinging Product Ordered details : {}");
		
		try {
		ProdOrdered po= prodRepo.save(order);
		if(po==null) {
			throw new Exception("OrderService.ADD_INVALID");
		}
		}
		catch(Exception e) {
			throw e;
		}
		return "Success";
	}
	public void updateProductOrdered(Integer orderId,Integer prodId, ProdOrdered product) {
		CompositeKey ckey=new CompositeKey();
		ckey.setOrderId(orderId);
		ckey.setProductId(prodId);
		ProdOrdered existingOrder = prodRepo.findById(ckey).orElse(null);
		if (existingOrder !=null) {
			existingOrder.setPrice(product.getPrice());
			existingOrder.setQuantity(product.getQuantity());
			existingOrder.setSellerid(product.getSellerid());
			existingOrder.setStatus(product.getStatus());
			prodRepo.save(existingOrder);
		}
	}
	public double calculateTotalPrice(int isPrivileged,List<CartDTO> cart,List<ProductDTO> prod) {
		
		double totalprice=0.0;
		if(isPrivileged==1) {
		for(CartDTO c:cart) {
			int quantity=c.getQuantity();
			for(ProductDTO p:prod) {
				double price=p.getPrice();
				if(c.getProId().equals(p.getProductId())) {
					if(c.getQuantity()<p.getStock()) {
						
						totalprice=totalprice+(quantity*price);
						System.out.println("Product Ordered with Id :"+p.getProductId());
					}
					else {
						System.out.println("Product Not Ordered with Id :"+p.getProductId()+" Due to Empty Stock");
					}
				}
			}
		}
		}
		else {
			for(CartDTO c:cart) {
				int quantity=c.getQuantity();
				for(ProductDTO p:prod) {
					double price=p.getPrice();
					if(c.getProId().equals(p.getProductId())) {
						if(c.getQuantity()<p.getStock()) {
							if(c.getQuantity()<=50) {
							totalprice=totalprice+(quantity*price);
							System.out.println("Product Ordered with Id :"+p.getProductId());
							}
							else {
								System.out.println("Product Ordered with Quantity greater than 50");
							}
						}
						else {
							System.out.println("Product Not Ordered with Id :"+p.getProductId()+" Due to Empty Stock");
						}
					}
				}
			}
		}
		System.out.println(totalprice);
		return totalprice;
	}
	public double calculateAmount(int rewardpoints,double totalprice) {
		System.out.println("rew : "+rewardpoints);
		double discount=(double)rewardpoints/4.0;
		double amount=totalprice-discount;
		System.out.println(amount);
		return amount;
	}
	public int calculateRewardPoints(double price) {
		double rew=price/100.0;
		System.out.println(rew);
		return (int)rew;
	}

}
