package com.github.bechernie.orderservice.order.web;

import com.github.bechernie.orderservice.config.SecurityConfig;
import com.github.bechernie.orderservice.order.domain.Order;
import com.github.bechernie.orderservice.order.domain.OrderService;
import com.github.bechernie.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@WebFluxTest(OrderController.class)
@Import(SecurityConfig.class)
class OrderControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ReactiveJwtDecoder reactiveJwtDecoder;

    @Test
    void whenBookNotAvailableThenRejectOrder() {
        final var orderRequest = new OrderRequest("1234567890", 3);
        final var expectedOrder = Order.buildRejectedOrder("1234567890", 3);
        given(orderService.submitOder(orderRequest.isbn(), orderRequest.quantity()))
                .willReturn(Mono.just(expectedOrder));
        webTestClient
                .mutateWith(mockJwt().authorities(new SimpleGrantedAuthority("ROLE_customer")))
                .post()
                .uri("/orders")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Order.class).value(actualOrder -> {
                    assertThat(actualOrder).isNotNull();
                    assertThat(actualOrder.status()).isEqualTo(OrderStatus.REJECTED);
                });
    }
}