package com.infy.order.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.infy.order.dto.BuyerDTO;
import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;

import com.infy.order.dto.ProdOrderedDTO;
import com.infy.order.dto.ProductDTO;
import com.infy.order.entity.Order;
import com.infy.order.entity.PlaceOrder;
import com.infy.order.entity.ProdOrdered;
import com.infy.order.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping(value="/api")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderService planService;
	
	@Value("${user.uri}")
	String userUri;
	
	@Value("${product.uri}")
	String productUri;
	@Autowired
	private Environment environment;
	
	// Fetches all order details
	@GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getAllOrders() throws Exception {
		logger.info("Fetching all orders");
		ResponseEntity<List<OrderDTO>> resEntity=null;
		List<OrderDTO> o=null;
		try {
			o=planService.getAllOrderDetails();
			resEntity=new ResponseEntity<>(o,HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
			
		}
		return resEntity;
	}
	@GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> getSpecificPlans(@PathVariable Integer orderId)throws Exception {
		logger.info("Fetching details of order {}", orderId);
		ResponseEntity<OrderDTO> resEntity=null;
		try {
			OrderDTO oDTO=planService.getSpecificOrder(orderId);
			List<ProdOrdered> pl=oDTO.getProductsOrdered();
			resEntity=new ResponseEntity<>(oDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()), e);
		}
		return resEntity;
	}
	@DeleteMapping(value = "/orders/{orderId}")
	public ResponseEntity<String> deleteSpecificOrder(@PathVariable Integer orderId) throws Exception{
		logger.info("Deleting details of order {}", orderId);
		ResponseEntity<String> resEntity=null;
		try {
		planService.deleteSpecificOrder(orderId);
		String msg="Deleted Successfully";
		resEntity=new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			resEntity=new ResponseEntity<>(environment.getProperty(e.getMessage()),HttpStatus.BAD_REQUEST);
		}
		return resEntity;
	}
	@PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<String> addOrderDetails(@RequestBody Order order) {
		logger.info("Adding orderDetails");
		ResponseEntity<String> resEntity=null;
		try {
		planService.addOrderDetails(order);
		String msg="Inserted Successfully";
		resEntity=new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			resEntity=new ResponseEntity<>("Insertion Unsuccessfull",HttpStatus.NOT_ACCEPTABLE);
		}
		return resEntity;
	}
	@PutMapping(value ="/orders/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateProduct(@PathVariable Integer productId, @RequestBody OrderDTO orderDTO) {
		planService.updateOrder(productId, orderDTO);
	}
	
	// Fetches all productOrdered details
	@GetMapping(value = "/prodorders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProdOrderedDTO>> getAllProductsOrdered() {
		logger.info("Fetching all ProductsOrdered");
		ResponseEntity<List<ProdOrderedDTO>> resEntity=null;
		List<ProdOrderedDTO> o=null;
		try {
			o=planService.getAllProdsOrdered();
			resEntity=new ResponseEntity<>(o,HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
		return resEntity;
		
	}
	@GetMapping(value = "/prodorders/{orderId}/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProdOrderedDTO> getSpecificProdOrderedDTO(@PathVariable Integer orderId,@PathVariable Integer productId) throws Exception{
		logger.info("Fetching details of order {}", orderId);
		ResponseEntity<ProdOrderedDTO> resEntity=null;
		try {
			ProdOrderedDTO oDTO=planService.getSpecificProductOrdered(orderId, productId);
			resEntity=new ResponseEntity<>(oDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()),e);
		}
		return resEntity;
	}			
	@DeleteMapping(value = "/prodorders/{orderId}/{productId}")
	public ResponseEntity<String> deleteSpecificProdOrder(@PathVariable Integer orderId,@PathVariable Integer productId) {
		logger.info("Deleting details of order {}", orderId);
		ResponseEntity<String> resEntity=null;
		try {
		planService.deleteSpecificProductOrdered(orderId,productId);
		String msg="Deleted Successfully";
		resEntity=new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			resEntity=new ResponseEntity<>("Deletion Unsuccessfull",HttpStatus.BAD_REQUEST);
		}
	    return resEntity;
	}	
	@PostMapping(value = "/prodorders", consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<String> addProductOrderedDetails(@RequestBody ProdOrdered order) {
		logger.info("Adding orderDetails");
		ResponseEntity<String> resEntity=null;
		try {
			planService.addProdOrderedDetails(order);
			String msg="Inserted Successfully";
			resEntity=new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
			}
			catch(Exception e) {
				resEntity=new ResponseEntity<>("Insertion Unsuccessfull",HttpStatus.NOT_ACCEPTABLE);
			}
		return resEntity;
		
	}
	@PutMapping(value ="/prodorders/{orderId}/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateProductOrdered(@PathVariable Integer orderId,@PathVariable Integer productId, @RequestBody ProdOrdered prodorderedDTO) {
		planService.updateProductOrdered(orderId,productId, prodorderedDTO);
	}
	
	
	@PostMapping(value = "/orders/placeorder",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> placeOrder(@RequestBody PlaceOrder placeorder) {
		ResponseEntity<String> res=null;
		if(placeorder.getAddress().length()==0) {
			res=new ResponseEntity<>("Please enter Address ", HttpStatus.NOT_ACCEPTABLE);
			return res;
		}
		if(placeorder.getAddress().length()>100) {
			res=new ResponseEntity<>("Address is greater than 100 digits", HttpStatus.BAD_REQUEST);
			return res;
		}
		
		String address=placeorder.getAddress();
		int buyerId=placeorder.getBuyerId();
		BuyerDTO buyerdto=new RestTemplate().getForObject(userUri+"buyer/"+buyerId,BuyerDTO.class);
		int shippingcost=0;
		if(buyerdto.getIsPrivileged()==0) {
			System.out.println("Since you are not a privileged Customer. You can order only minimum of Quantity= 50 for a specific item and Shipping cost=50");
			shippingcost=50;
		}
		else {
			System.out.println("Since you are privileged Customer. You can order infinite Quantity for a specific item and Shipping cost is free");
			shippingcost=0;
		}
		
		List<CartDTO> cartDTO1=new RestTemplate().getForObject(userUri+"cart/"+buyerId,List.class);
		//System.out.println(type(CartDTO1)); 
//		cartDTO=cartDTO1;
//		for(CartDTO prod:cartDTO1) {
//			System.out.println(prod.getProId());
//		}
//		Object[] cartDTO1=new RestTemplate().getForObject(userUri+"cart/"+buyerId,Object[].class);
//		//cartDTO=Arrays.asList(cartDTO1);
//		System.out.println(cartDTO1);
//		for(Object prod:cartDTO1) {
//			System.out.println(prod);
//		}
		//System.out.println(cartDTO);
		List<ProductDTO> prodDTO=new ArrayList<>();
		List<ProductDTO> p=new RestTemplate().getForObject(productUri+"products",List.class);
		
		ProductDTO pp=new RestTemplate().getForObject(productUri+"products/orders/"+1,ProductDTO.class);
		
		prodDTO.add(pp);
		ProductDTO pp1=new RestTemplate().getForObject(productUri+"products/orders/"+3,ProductDTO.class);
		
		prodDTO.add(pp1);
		ProductDTO pp2=new RestTemplate().getForObject(productUri+"products/orders/"+2,ProductDTO.class);
		
		prodDTO.add(pp2);
		List<CartDTO> cartdto=new ArrayList<>();
		CartDTO cart1=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+1,CartDTO.class);
		cartdto.add(cart1);
		CartDTO cart2=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+2,CartDTO.class);
		cartdto.add(cart2);
		CartDTO cart3=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+3,CartDTO.class);
		cartdto.add(cart3);
		OrderDTO order=new OrderDTO();
		order.setAddress(address);
		this.method(buyerId,order, true,"ORDER-PLACED", prodDTO, cartdto);
		res=new ResponseEntity<>("Order placed Successfully", HttpStatus.OK);
		return res;	
	}
	@PostMapping(value = "/orders/packing/{orderId}/{buyerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> packing(@PathVariable Integer orderId,@PathVariable Integer buyerId) {
		ResponseEntity<String> res=null;
		OrderDTO order=null;
		try {
		order=planService.getSpecificOrder(orderId);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
		if(order!=null) {
			if(order.getBuyerId().equals(buyerId)) {
				order.setStatus("PACKING");
				planService.updateOrder(order.getOrderId(), order);
				List<ProdOrdered> listorder=order.getProductsOrdered();
				if(!listorder.isEmpty()) {
					for(ProdOrdered p:listorder) {
						p.setStatus("PACKING");
						planService.updateProductOrdered(p.getOrderId(), p.getProductId(), p);
					}
					String msg="Packing is Successful  ";
					return new ResponseEntity<>(msg, HttpStatus.OK);
				}
			}
			else {
				String msg="Enter valid OrderId and BuyerId  ";
				return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return res;
	}
	@PostMapping(value = "/orders/dispatch/{orderId}/{buyerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dispatch(@PathVariable Integer orderId,@PathVariable Integer buyerId) {
		ResponseEntity<String> res=null;
		OrderDTO order=null;
		try {
		order=planService.getSpecificOrder(orderId);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
		if(order!=null) {
			if(order.getBuyerId().equals(buyerId)) {
				order.setStatus("DISPATCHED");
				planService.updateOrder(order.getOrderId(), order);
				List<ProdOrdered> listorder=order.getProductsOrdered();
				if(!listorder.isEmpty()) {
					for(ProdOrdered p:listorder) {
						p.setStatus("DISPATCHED");
						planService.updateProductOrdered(p.getOrderId(), p.getProductId(), p);
					}
					String msg="Dispatch is Successful  ";
					return new ResponseEntity<>(msg, HttpStatus.OK);
				}
			}
			else {
				String msg="Enter valid OrderId and BuyerId  ";
				return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return res;
	}
	@PostMapping(value = "/orders/deliver/{orderId}/{buyerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delivered(@PathVariable Integer orderId,@PathVariable Integer buyerId) {
		ResponseEntity<String> res=null;
		OrderDTO order=null;
		try {
			order=planService.getSpecificOrder(orderId);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
		if(order!=null) {
			if(order.getBuyerId().equals(buyerId)) {
				order.setStatus("DELIVERED");
				planService.updateOrder(order.getOrderId(), order);
				List<ProdOrdered> listorder=order.getProductsOrdered();
				if(!listorder.isEmpty()) {
					for(ProdOrdered p:listorder) {
						p.setStatus("DELIVERED");
						planService.updateProductOrdered(p.getOrderId(), p.getProductId(), p);
					}
					String msg="Delivery is Successful  ";
					return new ResponseEntity<>(msg, HttpStatus.OK);
				}
			}
			else {
				String msg="Enter valid OrderId and BuyerId  ";
				return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return res;
	}
	@PostMapping(value = "/orders/reorder/{orderId}/{buyerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reorder(@PathVariable Integer orderId,@PathVariable Integer buyerId) {
		ResponseEntity<String> res=null;
		OrderDTO order=null;
		try {
		order=planService.getSpecificOrder(orderId);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
		if(order!=null) {
		if(order.getBuyerId().equals(buyerId)) {
			List<ProductDTO> prodDTO=new ArrayList<>();
			List<ProdOrdered> listorder=order.getProductsOrdered();
			for(ProdOrdered po:listorder) {
				ProductDTO pp1=new RestTemplate().getForObject(productUri+"products/orders/"+po.getProductId(),ProductDTO.class);
				prodDTO.add(pp1);
			}
			System.out.println(prodDTO);
			List<CartDTO> cartdto=new ArrayList<>();
			for(ProdOrdered po:listorder) {
				CartDTO c=new CartDTO();
				c.setBuyerId(buyerId);
				c.setProId(po.getProductId());
				c.setQuantity(po.getQuantity());
				cartdto.add(c);
			}
			this.method(buyerId,order,false,"RE-ORDER PLACED",prodDTO,cartdto);
				String msg="Reorder is Successful  ";
				return new ResponseEntity<>(msg, HttpStatus.OK);
//			}
		}
		else {
			String msg="Enter valid OrderId and BuyerId  ";
			return new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
		}
		}
		return res;
		
	}
	
	public void method(int buyerId,OrderDTO order,boolean bool,String msg,List<ProductDTO> prodDTO,List<CartDTO> cartdto) {
		BuyerDTO buyerdto=new RestTemplate().getForObject(userUri+"buyer/"+buyerId,BuyerDTO.class);
		int shippingcost=0;
		if(buyerdto.getIsPrivileged()==0) {
			System.out.println("Since you are not a privileged Customer. You can order only minimum of Quantity= 50 for a specific item and Shipping cost=50");
			shippingcost=50;
		}
		else {
			System.out.println("Since you are privileged Customer. You can order infinite Quantity for a specific item and Shipping cost is free");
			shippingcost=0;
		}
		
		double totalprice=planService.calculateTotalPrice(buyerdto.getIsPrivileged(),cartdto,prodDTO);
		double totalpriceafterdiscount=planService.calculateAmount(buyerdto.getRewardPoints(), totalprice);
		int rewardPoints=planService.calculateRewardPoints(totalpriceafterdiscount);
		totalpriceafterdiscount=totalpriceafterdiscount+shippingcost;
		if(rewardPoints>=10000) {
			buyerdto.setIsPrivileged(1);
		}
		else {
			buyerdto.setIsPrivileged(0);
		}
		buyerdto.setRewardPoints(1001);
		new RestTemplate().put(userUri+"buyer/"+buyerId, buyerdto);
		Order o=new Order();
		o.setAddress(order.getAddress());
		o.setAmount(totalpriceafterdiscount);
		o.setBuyerId(buyerId);
		o.setDate(LocalDate.now());
		o.setStatus("RE-ORDER PLACED");
		
		try {
		planService.addOrderDetails(o);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
	
		List<OrderDTO> ol=new ArrayList<OrderDTO>();
		try {
		ol=planService.getAllOrderDetails();
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
		}
		int orderid=0;
		for(OrderDTO orderlist:ol) {
			orderid=orderlist.getOrderId();
		}
		
		if(orderid!=0) {
			if(buyerdto.getIsPrivileged()==1) {
				for(CartDTO prod:cartdto) {
					for(ProductDTO pr:prodDTO) {
						if(prod.getProId().equals(pr.getProductId())) {
							if(prod.getQuantity()<pr.getStock()) {
								ProdOrdered po=new ProdOrdered();
								po.setOrderId(orderid);
								po.setPrice(pr.getPrice()*prod.getQuantity());
								po.setProductId(prod.getProId());
								po.setQuantity(prod.getQuantity());
								po.setSellerid(pr.getSellerId());
								po.setStatus(msg);
								if(bool) {
									new RestTemplate().delete(userUri+"cart/"+buyerId+"/"+pr.getProductId());
								}
								int stock=pr.getStock()-prod.getQuantity();
								pr.setStock(100);
								new RestTemplate().put(productUri+"products/"+pr.getProductId(), pr);
								try {
									planService.addProdOrderedDetails(po);
								}
								catch(Exception ex) {
									throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(ex.getMessage()), ex);
								}
							}
						}
					}
					
				}
			}
			else {
				for(CartDTO prod:cartdto) {
					for(ProductDTO pr:prodDTO) {
						if(prod.getProId().equals(pr.getProductId())) {
							if(prod.getQuantity()<pr.getStock()) {
							if(prod.getQuantity()<=50) {
								ProdOrdered po=new ProdOrdered();
								po.setOrderId(orderid);
								po.setPrice(pr.getPrice()*prod.getQuantity());
								po.setProductId(prod.getProId());
								po.setQuantity(prod.getQuantity());
								po.setSellerid(pr.getSellerId());
								po.setStatus(msg);
								if(bool) {
									new RestTemplate().delete(userUri+"cart/"+buyerId+"/"+pr.getProductId());
								}
								int stock=pr.getStock()-prod.getQuantity();
								pr.setStock(100);
								new RestTemplate().put(productUri+"products/"+pr.getProductId(), pr);
								try {
									planService.addProdOrderedDetails(po);
								}
								catch(Exception ex) {
									throw new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(ex.getMessage()), ex);
								}
							}
						}
					}
				}
					
				}
			}
		}

	}
}
