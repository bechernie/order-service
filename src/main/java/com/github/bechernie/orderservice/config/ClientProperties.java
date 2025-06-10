package com.github.bechernie.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.time.Duration;

@ConfigurationProperties(prefix = "polar")
public class ClientProperties {
    private URI catalogServiceUri;
    private Duration catalogServiceTimeout = Duration.ofSeconds(1);

    public URI getCatalogServiceUri() {
        return catalogServiceUri;
    }

    public void setCatalogServiceUri(URI catalogServiceUri) {
        this.catalogServiceUri = catalogServiceUri;
    }

    public Duration getCatalogServiceTimeout() {
        return catalogServiceTimeout;
    }

    public void setCatalogServiceTimeout(Duration catalogServiceTimeout) {
        this.catalogServiceTimeout = catalogServiceTimeout;
    }
}
