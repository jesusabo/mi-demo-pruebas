package com.casa.productos.app.config;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class ExchangeFilterFunctionWebFlux {

    public static ExchangeFilterFunction logRequest(){
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            System.out.println("Request :"+ request.method() + " " + request.url());
            return Mono.just(request);
        });
    }

    public static ExchangeFilterFunction logResponse(){
        return ExchangeFilterFunction.ofResponseProcessor( response -> {
            System.out.println("Response Status: "+response.statusCode());
            return Mono.just(response);
        });
    }
}
