package org.jeongmo.apigateway.config

import org.jeongmo.apigateway.filter.LocalFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig(
    private val localFilter: LocalFilter,
) {

    @Bean
    fun route(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator {
        return routeLocatorBuilder.routes()
            .route("msaTest") {
                it
                    .path("/health-check/**")
                    .filters { f ->
                        val config = LocalFilter.LocalFilterConfig(true, true)
                        val filter = localFilter.apply(config)
                        f.filter(filter, localFilter.order)
                    }
                    .uri("lb://msaTest")
            }
            .build()
    }
}