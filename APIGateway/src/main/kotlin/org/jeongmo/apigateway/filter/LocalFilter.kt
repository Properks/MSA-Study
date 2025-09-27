package org.jeongmo.apigateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class LocalFilter(
    val order: Int = 1,
): AbstractGatewayFilterFactory<LocalFilter.LocalFilterConfig>(LocalFilterConfig::class.java) {


    override fun apply(config: LocalFilterConfig): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            if (config.isPre) {
                println("local: pre order $order")
            }

            chain.filter(exchange)
                .then(
                    Mono.fromRunnable {
                        if (config.isPost) {
                            println("local: post Order $order")
                        }
                    }
                )
        }
    }

    open class LocalFilterConfig(
        val isPre: Boolean,
        val isPost: Boolean,
    ) {
    }

}
