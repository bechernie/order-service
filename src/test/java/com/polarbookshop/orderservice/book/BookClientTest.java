package com.polarbookshop.orderservice.book;

import com.polarbookshop.orderservice.config.ClientProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.Random.class)
class BookClientTest {

    private MockWebServer mockWebServer;
    private BookClient bookClient;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        bookClient = new BookClient(
                WebClient.builder()
                        .baseUrl(mockWebServer.url("/").uri().toString())
                        .build(),
                new ClientProperties(URI.create(""), Duration.ofSeconds(3))
        );
    }

    @AfterEach
    void clean() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void whenBookExistsThenReturnBook() {
        final var isbn = "1234567890";
        final var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("""
                        {
                            "isbn": "%s",
                            "title": "Title",
                            "author": "Author",
                            "price": 9.90,
                            "publisher": "Polarsophia"
                        }
                        """.formatted(isbn));
        mockWebServer.enqueue(mockResponse);

        final var book = bookClient.getBookByIsbn(isbn);

        StepVerifier
                .create(book)
                .expectNextMatches(b -> b.isbn().equals(isbn))
                .verifyComplete();
    }
}