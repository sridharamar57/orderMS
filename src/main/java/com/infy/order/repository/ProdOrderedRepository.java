package com.infy.order.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.order.entity.CompositeKey;
import com.infy.order.entity.ProdOrdered;

public interface ProdOrderedRepository extends JpaRepository<ProdOrdered,CompositeKey>{

	List<ProdOrdered> findByProductId(Integer productId);
}
