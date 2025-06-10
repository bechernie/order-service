package com.github.bechernie.orderservice.book;

import com.github.bechernie.orderservice.config.ClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class BookClient {
    private static final String BOOK_ROOT_API = "/books/";

    private final WebClient webClient;
    private final ClientProperties clientProperties;

    public BookClient(WebClient webClient, ClientProperties clientProperties) {
        this.webClient = webClient;
        this.clientProperties = clientProperties;
    }

    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient
                .get()
                .uri(BOOK_ROOT_API + isbn)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(clientProperties.getCatalogServiceTimeout(), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(Exception.class, ex -> Mono.empty());
    }
}
