package org.tenpo.challenge.cvergara.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class RequestParamInterceptor  implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // Obtiene los parámetros de la URL (Query Params)
        var queryParams = exchange.getRequest().getQueryParams().toSingleValueMap();


        System.out.println("Interceptando parámetros: " + queryParams);


        return chain.filter(exchange);
    }
}