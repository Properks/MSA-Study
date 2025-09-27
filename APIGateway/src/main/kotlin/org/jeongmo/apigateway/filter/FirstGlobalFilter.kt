package org.jeongmo.apigateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class FirstGlobalFilter(

): GlobalFilter, Ordered {

    private val order: Int = -1;

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        // exchange.request.headers["Authorization"]

        println("Pre order $order")
        return chain.filter(exchange)
            .then(
                Mono.fromRunnable() {
                    println("Post order $order")
                }
            )
    }

    override fun getOrder(): Int {
        return this.order
    }
}