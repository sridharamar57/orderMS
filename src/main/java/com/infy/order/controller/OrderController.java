package com.infy.order.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.infy.order.dto.BuyerDTO;
import com.infy.order.dto.CartDTO;
import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.PlanDTO;
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
	
//	@Value("${plan.uri}")
//	String planUri;
	
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
			
			resEntity=new ResponseEntity<List<OrderDTO>>(o,HttpStatus.OK);
		}
		catch(Exception e) {
			ResponseStatusException rsc =new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
			throw rsc;
		}
		return resEntity;
	}
	// Fetch order details of a specific order
//	@GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public OrderDTO getSpecificPlans(@PathVariable Integer orderId) {
//		logger.info("Fetching details of order {}", orderId);
//		return planService.getSpecificOrder(orderId);
//	}
	@GetMapping(value = "/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> getSpecificPlans(@PathVariable Integer orderId)throws Exception {
		logger.info("Fetching details of order {}", orderId);
		ResponseEntity<OrderDTO> resEntity=null;
		try {
			OrderDTO oDTO=planService.getSpecificOrder(orderId);
			List<ProdOrdered> pl=oDTO.getProductsOrdered();
			System.out.println(pl.size());
			resEntity=new ResponseEntity<OrderDTO>(oDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			ResponseStatusException rsc =new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()), e);
			throw rsc;
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
		resEntity=new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			resEntity=new ResponseEntity<String>(environment.getProperty(e.getMessage()),HttpStatus.BAD_REQUEST);
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
		resEntity=new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			resEntity=new ResponseEntity<String>("Insertion Unsuccessfull",HttpStatus.NOT_ACCEPTABLE);
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
			resEntity=new ResponseEntity<List<ProdOrderedDTO>>(o,HttpStatus.OK);
		}
		catch(Exception e) {
			ResponseStatusException rsc =new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
			throw rsc;
		}
		return resEntity;
		
	}
	@GetMapping(value = "/prodorders/{orderId}/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProdOrderedDTO> getSpecificProdOrderedDTO(@PathVariable Integer orderId,@PathVariable Integer productId) throws Exception{
		logger.info("Fetching details of order {}", orderId);
		ResponseEntity<ProdOrderedDTO> resEntity=null;
		try {
			ProdOrderedDTO oDTO=planService.getSpecificProductOrdered(orderId, productId);
			resEntity=new ResponseEntity<ProdOrderedDTO>(oDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			//ResponseStatusException rsc =new ResponseStatusException(HttpStatus.BAD_REQUEST,environment.getProperty(e.getMessage()), e);
			//throw rsc;
			ResponseStatusException rsc1=new ResponseStatusException(HttpStatus.NOT_FOUND,environment.getProperty(e.getMessage()),e);
			throw rsc1;
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
		resEntity=new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			resEntity=new ResponseEntity<String>("Deletion Unsuccessfull",HttpStatus.BAD_REQUEST);
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
			resEntity=new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
			}
			catch(Exception e) {
				resEntity=new ResponseEntity<String>("Insertion Unsuccessfull",HttpStatus.NOT_ACCEPTABLE);
			}
		return resEntity;
		
	}
	@PutMapping(value ="/prodorders/{orderId}/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateProductOrdered(@PathVariable Integer orderId,@PathVariable Integer productId, @RequestBody ProdOrdered prodorderedDTO) {
		planService.updateProductOrdered(orderId,productId, prodorderedDTO);
	}
	
	@GetMapping(value = "/order/cart", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CartDTO> getAllCarts() {
		logger.info("Fetching all ProductsOrdered");
		List<CartDTO> cartDTO=new RestTemplate().getForObject(userUri+"cart",List.class);
		return cartDTO;
	}
	@GetMapping(value= "/order/cart/{buyerId}/{proId}",produces=MediaType.APPLICATION_JSON_VALUE)
	public CartDTO getSpecificCart(@PathVariable int buyerId,@PathVariable  int proId)
	{
		logger.info("Fetching details of cart {}", buyerId,proId);
		CartDTO cartDTO=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+proId,CartDTO.class);
		return cartDTO;
	}
	@DeleteMapping(value = "/order/cart/{buyerId}/{proId}")
		public void deleteSpecificCart(@PathVariable Integer buyerId,@PathVariable  int proId) {
		logger.info("Detching details of cart {}", buyerId,proId);
		new RestTemplate().delete(userUri+"cart/"+buyerId+"/"+proId);
	}	
	@PostMapping(value = "/order/cart",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<CartDTO> addSpecificCart(@RequestBody CartDTO plan) {
		logger.info("Adding cartDetails");	
		List<CartDTO> planDTO=new RestTemplate().postForObject(userUri+"cart",plan,List.class);
		return planDTO;
	}
	@PostMapping(value = "/orders/placeorder",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> placeOrder(@RequestBody PlaceOrder placeorder) {
		ResponseEntity<String> res=null;
		if(placeorder.getAddress().length()==0) {
			res=new ResponseEntity<String>("Please enter Address ", HttpStatus.NOT_ACCEPTABLE);
			return res;
		}
		if(placeorder.getAddress().length()>100) {
			res=new ResponseEntity<String>("Address is greater than 100 digits", HttpStatus.BAD_REQUEST);
			return res;
		}
		
		String address=placeorder.getAddress();
		Integer buyerId=placeorder.getBuyerId();
		//System.out.println(buyerId);
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
		//System.out.println(buyerdto.getRewardPoints());
		//List<CartDTO> cartDTO=new ArrayList<CartDTO>();
		List<CartDTO> cartDTO1=new RestTemplate().getForObject(userUri+"cart/"+buyerId,List.class);
//		cartDTO=cartDTO1;
//		for(CartDTO prod:cartDTO) {
//			System.out.println(prod.getProId());
//		}
		//System.out.println(cartDTO);
		List<ProductDTO> prodDTO=new ArrayList<>();
		List<ProductDTO> p=new RestTemplate().getForObject(productUri+"products",List.class);
		//System.out.println(p);
		ProductDTO pp=new RestTemplate().getForObject(productUri+"products/orders/"+1,ProductDTO.class);
		//System.out.println(pp);
		prodDTO.add(pp);
		ProductDTO pp1=new RestTemplate().getForObject(productUri+"products/orders/"+3,ProductDTO.class);
		//System.out.println(pp1);
		prodDTO.add(pp1);
		ProductDTO pp2=new RestTemplate().getForObject(productUri+"products/orders/"+2,ProductDTO.class);
		//System.out.println(pp1);
		prodDTO.add(pp2);
//		for(ProductDTO prod:prodDTO) {
//			System.out.println(prod.getProductId());
//		}
		List<CartDTO> cartdto=new ArrayList<>();
		CartDTO cart1=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+1,CartDTO.class);
		cartdto.add(cart1);
		//System.out.println(cart1);
		CartDTO cart2=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+2,CartDTO.class);
		cartdto.add(cart2);
		CartDTO cart3=new RestTemplate().getForObject(userUri+"cart/"+buyerId+"/"+3,CartDTO.class);
		cartdto.add(cart3);
		//System.out.println(cartdto);
//		for(CartDTO prod:cartdto) {
//			System.out.println(prod.getProId());
//		}
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
		o.setAddress(address);
		o.setAmount(totalpriceafterdiscount);
		o.setBuyerId(buyerId);
		o.setDate(LocalDate.now());
		o.setStatus("ORDER PLACED");
		
		try {
		planService.addOrderDetails(o);
		}
		catch(Exception e) {
			
		}
	
		List<OrderDTO> ol=new ArrayList<OrderDTO>();
		try {
		ol=planService.getAllOrderDetails();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		int orderid=0;
		for(OrderDTO orderlist:ol) {
			orderid=orderlist.getOrderId();
		}
		//System.out.println(orderid);
		if(orderid!=0) {
			//List<ProdOrdered> pol=new ArrayList<>();
			if(buyerdto.getIsPrivileged()==1) {
				for(CartDTO prod:cartdto) {
					//System.out.println(prod.getProId());
					for(ProductDTO pr:prodDTO) {
					if(prod.getProId().equals(pr.getProductId())) {
						if(prod.getQuantity()<pr.getStock()) {
					ProdOrdered po=new ProdOrdered();
					po.setOrderId(orderid);
					po.setPrice(pr.getPrice()*prod.getQuantity());
					po.setProductId(prod.getProId());
					po.setQuantity(prod.getQuantity());
					po.setSellerid(pr.getSellerId());
					po.setStatus("ORDER PLACED");
					new RestTemplate().delete(userUri+"cart/"+buyerId+"/"+pr.getProductId());
					int stock=pr.getStock()-prod.getQuantity();
					pr.setStock(100);
					new RestTemplate().put(productUri+"products/"+pr.getProductId(), pr);
					try {
					planService.addProdOrderedDetails(po);
					}
					catch(Exception ex) {
						
					}
					}
					}
				}
					
			}
			}
			else {
				for(CartDTO prod:cartdto) {
					//System.out.println(prod.getProId());
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
					po.setStatus("ORDER PLACED");
					new RestTemplate().delete(userUri+"cart/"+buyerId+"/"+pr.getProductId());
					int stock=pr.getStock()-prod.getQuantity();
					pr.setStock(100);
					new RestTemplate().put(productUri+"products/"+pr.getProductId(), pr);
					try {
					planService.addProdOrderedDetails(po);
					}
					catch(Exception ex) {
						
					}
					}
						}
					}
				}
					
			}
			}
		}
		res=new ResponseEntity<String>("Order placed Successfully", HttpStatus.OK);
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
			// TODO: handle exception
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
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			}
		}
		else {
			String msg="Enter valid OrderId and BuyerId  ";
			return new ResponseEntity<String>(msg, HttpStatus.NOT_ACCEPTABLE);
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
			// TODO: handle exception
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
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			}
		}
		else {
			String msg="Enter valid OrderId and BuyerId  ";
			return new ResponseEntity<String>(msg, HttpStatus.NOT_ACCEPTABLE);
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
			// TODO: handle exception
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
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			}
		}
		else {
			String msg="Enter valid OrderId and BuyerId  ";
			return new ResponseEntity<String>(msg, HttpStatus.NOT_ACCEPTABLE);
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
			// TODO: handle exception
		}
		if(order!=null) {
		if(order.getBuyerId().equals(buyerId)) {
			order.setStatus("REORDER PLACED");
			planService.updateOrder(order.getOrderId(), order);
			List<ProdOrdered> listorder=order.getProductsOrdered();
			if(!listorder.isEmpty()) {
				for(ProdOrdered p:listorder) {
					p.setStatus("REORDER PLACED");
					planService.updateProductOrdered(p.getOrderId(), p.getProductId(), p);
				}
				String msg="Reorder is Successful  ";
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			}
		}
		else {
			String msg="Enter valid OrderId and BuyerId  ";
			return new ResponseEntity<String>(msg, HttpStatus.NOT_ACCEPTABLE);
		}
		}
		return res;
	}
}