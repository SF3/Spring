package com.instil;

import com.instil.entities.Delivery;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DeliveryRepository extends ReactiveCrudRepository<Delivery, Integer> {
}
