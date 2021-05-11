package com.store.api.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.api.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
