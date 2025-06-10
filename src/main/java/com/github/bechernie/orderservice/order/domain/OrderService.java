package com.github.bechernie.orderservice.order.domain;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOder(String isbn, int quantity) {
        return Mono.just(Order.buildRejectedOrder(isbn, quantity))
                .flatMap(orderRepository::save);
    }
}
