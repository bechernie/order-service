package com.github.bechernie.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "polar")
public class ClientProperties {
    private URI catalogServiceUri;

    public URI getCatalogServiceUri() {
        return catalogServiceUri;
    }

    public void setCatalogServiceUri(URI catalogServiceUri) {
        this.catalogServiceUri = catalogServiceUri;
    }
}
