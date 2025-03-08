package com.casa.productos.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean(name="marketWebClient")
    public WebClient marketWebClient(){
        return WebClient
                .builder()
                .baseUrl("https://fakestoreapi.com/products")
                .filter(ExchangeFilterFunctionWebFlux.logRequest())
                .filter(ExchangeFilterFunctionWebFlux.logResponse())
                .build();
    }
}
