package com.github.bechernie.orderservice.order.domain;

import com.github.bechernie.orderservice.book.BookClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public OrderService(OrderRepository orderRepository, BookClient bookClient) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn)
                .map(book -> Order.buildAcceptedOrder(book,quantity))
                .defaultIfEmpty(Order.buildRejectedOrder(isbn,quantity))
                .flatMap(orderRepository::save);
    }
}
