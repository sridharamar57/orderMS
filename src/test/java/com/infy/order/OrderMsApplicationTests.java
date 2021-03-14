package com.infy.order;


import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infy.order.dto.OrderDTO;
import com.infy.order.dto.ProdOrderedDTO;
import com.infy.order.entity.CompositeKey;
import com.infy.order.entity.Order;
import com.infy.order.entity.ProdOrdered;
import com.infy.order.repository.OrderRepository;
import com.infy.order.repository.ProdOrderedRepository;
import com.infy.order.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMsApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Mock
	OrderRepository orderRepo;
	
	@Mock
	ProdOrderedRepository prodRepo;
	
//	@Autowired
//	OrderService orderService;
	
	@InjectMocks
	OrderService orderService;
	
	
	
	@Test
	public void getSpecificOrderValid() throws Exception {
		//
		Order o=new Order();
		o.setAddress("abc");
		o.setAmount(100.00);
		o.setBuyerId(1);
		o.setDate(null);
		o.setOrderId(21);
		o.setStatus("ss");
		Optional<Order> oo=Optional.of(o);
		//System.out.println(orderService.getSpecificOrder(21));
		Mockito.when(orderRepo.findById(21)).thenReturn(oo);
		//System.out.println(orderService.getSpecificOrder(21));
		//System.out.println(oo);
		OrderDTO order=orderService.getSpecificOrder(oo.get().getOrderId());
	
		Assertions.assertEquals(21,order.getOrderId());
	}
	@Test
	public void getSpecificOrderInValid() throws Exception {
		//OrderDTO o=orderService.getSpecificOrder(11);
		//System.out.println(o);
		Order o=new Order();
		o.setAddress("abc");
		o.setAmount(100.00);
		o.setBuyerId(1);
		o.setDate(null);
		o.setOrderId(21);
		o.setStatus("ss");
		Optional<Order> o11=Optional.of(o);
		
		Order o1=new Order();
		o1.setAddress("abdc");
		o1.setAmount(1002.00);
		o1.setBuyerId(12);
		o1.setDate(null);
		o1.setOrderId(241);
		o1.setStatus("abc");
		//Optional<Order> o11=Optional.of(o1);
		Mockito.when(orderRepo.findById(o.getOrderId())).thenReturn(o11);
		//System.out.println(orderService.getSpecificOrder(21));
		Exception exception=Assertions.assertThrows(Exception.class,()->orderService.getSpecificOrder(o1.getOrderId()));
		Assertions.assertEquals("OrderService.Orders_NOT_FOUND", exception.getMessage());
	}
	@Test
	public void addOrderDetails() throws Exception{
		Order o=new Order();
		o.setAddress("abc");
		o.setAmount(100.00);
		o.setBuyerId(1);
		o.setDate(null);
		o.setOrderId(3);
		o.setStatus("ss");
		//Optional<Order> oo=Optional.of(o);
		Mockito.when(orderRepo.save(o)).thenReturn(o);
		//System.out.println(oo);
//		String actual = customerLoginService.authenticateCustomer(customer);
//		Assertions.assertEquals("SUCCESS", actual);
		String s=orderService.addOrderDetails(o);
		Assertions.assertEquals("Success",s);
	}
	
	@Test
	public void addOrderDetailsInvalid() throws Exception {
		Order o=new Order();
		o.setAddress("abc");
		o.setAmount(100.00);
		o.setBuyerId(1);
		o.setDate(null);
		o.setOrderId(21);
		o.setStatus("ss");
		Optional<Order> o11=Optional.of(o);
		
		Order o1=new Order();
		//Optional<Order> o11=Optional.of(o1);
		Mockito.when(orderRepo.findById(o.getOrderId())).thenReturn(o11);
		System.out.println(orderService.getSpecificOrder(21));
		Exception exception=Assertions.assertThrows(Exception.class,()->orderService.addOrderDetails(o1));
		//Assertions.assertEquals("OrderService.Orders_NOT_FOUND", exception.getMessage());
	}
	
	@Test
	public void deleteOrderDetails() throws Exception{
		Order o=new Order();
		o.setAddress("abc");
		o.setAmount(100.00);
		o.setBuyerId(1);
		o.setDate(null);
		o.setOrderId(3);
		o.setStatus("ss");
		Optional<Order> oo=Optional.of(o);
		
		Mockito.when(orderRepo.findById(o.getOrderId())).thenReturn(oo);
		//System.out.println(oo);
//		String actual = customerLoginService.authenticateCustomer(customer);
//		Assertions.assertEquals("SUCCESS", actual);
		String s=orderService.deleteSpecificOrder(1044);
		Assertions.assertEquals("Success",s);
	}
	@Test
	public void deleteOrderDetailsInvalid() throws Exception {
		Order o=new Order();
		o.setAddress("abc");
		o.setAmount(100.00);
		o.setBuyerId(1);
		o.setDate(null);
		o.setOrderId(21);
		o.setStatus("ss");
		Optional<Order> o11=Optional.of(o);
		Order o1=new Order();
		o1.setAddress("abdc");
		o1.setAmount(1002.00);
		o1.setBuyerId(121);
		o1.setDate(null);
		o1.setOrderId(241);
		o1.setStatus("abc");
		//Optional<Order> o11=Optional.of(o1);
		Mockito.when(orderRepo.findById(o.getOrderId())).thenReturn(o11);
		//orderRepo.deleteById(o1.getOrderId());
		//System.out.println(orderService.getSpecificOrder(21));
		Exception exception=Assertions.assertThrows(Exception.class,()->orderService.deleteSpecificOrder(o1.getOrderId()));
		Assertions.assertEquals("OrderService.Orders_NOT_FOUND", exception.getMessage());
	}
//	@Test
//	public void getSpecificProductOrderedValid() throws Exception {
//		//
//		ProdOrdered o=new ProdOrdered();
//		o.setOrderId(8);
//		o.setProductId(8);
//		o.setPrice(1000.00);
//		o.setQuantity(8);
//		o.setSellerid(2);
//		o.setStatus("ordered");
//		
//		Optional<ProdOrdered> oo=Optional.of(o);
//		CompositeKey ckey=new CompositeKey();
//		ckey.setOrderId(8);
//		ckey.setProductId(8);
//		//System.out.println(orderService.getSpecificOrder(21));
//		Mockito.when(prodRepo.findById(ckey)).thenReturn(oo);
//		//System.out.println(orderService.getAllProdsOrdered());
//		//System.out.println(orderService.getSpecificOrder(21));
//		//System.out.println(oo);
//		//System.out.println(o.getOrderId());
//		//System.out.println(o.getProductId());
//		ProdOrderedDTO order=orderService.getSpecificProductOrdered(oo.get().getOrderId(),oo.get().getProductId());
//		System.out.println(order);
//		Assertions.assertEquals(1,order.getOrderId());
//	}
//	@Test
//	public void getSpecificProdOrderedInValid() throws Exception {
//		//OrderDTO o=orderService.getSpecificOrder(11);
//		//System.out.println(o);
//		ProdOrdered o=new ProdOrdered();
//		o.setOrderId(8);
//		o.setProductId(8);
//		o.setPrice(1000.00);
//		o.setQuantity(8);
//		o.setSellerid(2);
//		o.setStatus("ordered");
//		CompositeKey ckey=new CompositeKey();
//		ckey.setOrderId(8);
//		ckey.setProductId(8);
//		Optional<ProdOrdered> oo=Optional.of(o);
//		ProdOrdered o1=new ProdOrdered();
//		o1.setOrderId(18);
//		o1.setProductId(18);
//		o1.setPrice(10001.00);
//		o1.setQuantity(18);
//		o1.setSellerid(22);
//		o1.setStatus("Prodordered");
//		//Optional<Order> o11=Optional.of(o1);
//		Mockito.when(prodRepo.findById(ckey)).thenReturn(oo);
//		//System.out.println(orderService.getSpecificOrder(21));
//		
//		Exception exception=Assertions.assertThrows(Exception.class,()->orderService.getSpecificProductOrdered(o.getOrderId(), o.getProductId()));
//		Assertions.assertEquals("OrderService.Orders_NOT_FOUND", exception.getMessage());
//	}
//	
//	@Test
//	public void addProdOrderDetailsValid() throws Exception {
//		ProdOrdered o=new ProdOrdered();
//		o.setOrderId(8);
//		o.setProductId(8);
//		o.setPrice(1000.00);
//		o.setQuantity(8);
//		o.setSellerid(2);
//		o.setStatus("ordered");
//		CompositeKey ckey=new CompositeKey();
//		ckey.setOrderId(8);
//		ckey.setProductId(8);
//		Optional<ProdOrdered> oo=Optional.of(o);
//		//Optional<Order> o11=Optional.of(o1);
//		Mockito.when(prodRepo.findById(ckey)).thenReturn(oo);
//		//System.out.println(orderService.getSpecificOrder(21));
//		String s=orderService.addProdOrderedDetails(o);
//		Assertions.assertEquals("Success",s);
//	}
//
//	@Test
//	public void addProdOrderDetailsInvalid() throws Exception {
//		ProdOrdered o=new ProdOrdered();
//		o.setOrderId(8);
//		o.setProductId(8);
//		o.setPrice(1000.00);
//		o.setQuantity(8);
//		o.setSellerid(2);
//		o.setStatus("ordered");
//		CompositeKey ckey=new CompositeKey();
//		ckey.setOrderId(8);
//		ckey.setProductId(8);
//		Optional<ProdOrdered> oo=Optional.of(o);
//		ProdOrdered o1=new ProdOrdered();
//		o1.setOrderId(18);
//		o1.setProductId(18);
//		o1.setPrice(10001.00);
//		o1.setQuantity(18);
//		o1.setSellerid(22);
//		o1.setStatus("Prodordered");
//		//Optional<Order> o11=Optional.of(o1);
//		Mockito.when(prodRepo.findById(ckey)).thenReturn(oo);
//		System.out.println(orderService.getSpecificOrder(21));
//		Exception exception=Assertions.assertThrows(Exception.class,()->orderService.addProdOrderedDetails(o));
//		Assertions.assertEquals("OrderService.Orders_NOT_FOUND", exception.getMessage());
//	}
//	@Test
//	public void deleteProdOrderedDetails() {
//		ProdOrdered o=new ProdOrdered();
//		o.setOrderId(9);
//		o.setProductId(9);
//		o.setPrice(1000.00);
//		o.setQuantity(8);
//		o.setSellerid(2);
//		o.setStatus("ordered");
//		CompositeKey ckey=new CompositeKey();
//		ckey.setOrderId(o.getOrderId());
//		ckey.setProductId(o.getProductId());
//		Optional<ProdOrdered> oo=Optional.of(o);
//		
//		Mockito.when(prodRepo.findById(ckey)).thenReturn(oo);
//		//System.out.println(oo);
////		String actual = customerLoginService.authenticateCustomer(customer);
////		Assertions.assertEquals("SUCCESS", actual);
//		String s=orderService.deleteSpecificProductOrdered(o.getOrderId(),o.getProductId());
//		Assertions.assertEquals("Success",s);
//	}
//	@Test
//	public void deleteProdOrderDetailsInvalid() throws Exception {
//		ProdOrdered o=new ProdOrdered();
//		o.setOrderId(8);
//		o.setProductId(8);
//		o.setPrice(1000.00);
//		o.setQuantity(8);
//		o.setSellerid(2);
//		o.setStatus("ordered");
//		CompositeKey ckey=new CompositeKey();
//		ckey.setOrderId(8);
//		ckey.setProductId(8);
//		Optional<ProdOrdered> oo=Optional.of(o);
//		ProdOrdered o1=new ProdOrdered();
//		o1.setOrderId(18);
//		o1.setProductId(18);
//		o1.setPrice(10001.00);
//		o1.setQuantity(18);
//		o1.setSellerid(22);
//		o1.setStatus("Prodordered");
//		//Optional<Order> o11=Optional.of(o1);
//		Mockito.when(prodRepo.findById(ckey)).thenReturn(oo);
//		System.out.println(orderService.getSpecificOrder(21));
////		Exception exception=Assertions.assertThrows(Exception.class,()->orderService.deleteSpecificProductOrdered(o.getOrderId(), o.getProductId()));
////		Assertions.assertEquals("OrderService.Orders_NOT_FOUND", exception.getMessage());
//	}
}
