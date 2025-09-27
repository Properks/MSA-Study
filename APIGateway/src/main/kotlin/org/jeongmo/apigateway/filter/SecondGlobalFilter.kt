package org.jeongmo.apigateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecondGlobalFilter: GlobalFilter, Ordered {

    private val order: Int = 0;

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        println("Pre Order $order")
        return chain.filter(exchange)
            .then(
                Mono.fromRunnable {
                    println("Post Order $order")
                }
            )
    }

    override fun getOrder(): Int {
        return this.order
    }
}