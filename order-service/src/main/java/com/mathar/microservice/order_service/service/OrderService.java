package com.mathar.microservice.order_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mathar.microservice.order_service.client.InventoryClient;
import com.mathar.microservice.order_service.dto.OrderRequest;
import com.mathar.microservice.order_service.model.Order;
import com.mathar.microservice.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
  
  private final OrderRepository orderRepository;
  private final InventoryClient inventoryClient;

  public void placeOrder(OrderRequest orderRequest) {
    
    var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

    if (isProductInStock) {
      Order order = new Order();
      order.setOrderNumber(UUID.randomUUID().toString());
      order.setPrice(orderRequest.price());
      order.setSkuCode(orderRequest.skuCode());
      order.setQuantity(orderRequest.quantity());
      orderRepository.save(order);
    } else {
      throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is not in stock");
    }
  }
}
