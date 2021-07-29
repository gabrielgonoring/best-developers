package com.gabriel.gonoring.borges.bdb.configuration;

import com.gabriel.gonoring.borges.bdb.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfiguration.class);

    @Value("${webclient.github.key}")
    private String key;

    @Value("${webclient.github.secret}")
    private String secret;


    @Bean
    public WebClient webClientGitHub(){

        WebClient.Builder builder = WebClient.builder()
                                        .baseUrl("https://api.github.com")
                                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json");

        if (Utils.isStringNotNullAndNotBlack(secret) && Utils.isStringNotNullAndNotBlack(key)) {
            builder.defaultHeaders(headers -> headers.setBasicAuth(key, secret));
        }
        else
            LOGGER.warn("GitHub Key and Secret is not defined");

        return builder.build();
    }
}
