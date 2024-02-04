package com.polarbookshop.orderservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.net.URI;
import java.time.Duration;

@ConfigurationProperties(prefix = "polar")
public record ClientProperties(
        @NotNull
        URI catalogServiceUri,
        @DefaultValue("3s")
        Duration catalogServiceTimeout
) {
}
