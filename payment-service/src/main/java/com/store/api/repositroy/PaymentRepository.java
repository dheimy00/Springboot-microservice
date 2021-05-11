package com.store.api.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.api.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	Payment findByOrderId(Integer orderId);

}
 