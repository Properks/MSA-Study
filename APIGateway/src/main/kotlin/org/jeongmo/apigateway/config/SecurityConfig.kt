package org.jeongmo.apigateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Value("\${actuator.id}") private val id: String,
    @Value("\${actuator.password}") private val password: String,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange {
               it
                    .pathMatchers("/actuator/**").hasRole("ENDPOINT_ADMIN")
//                    .pathMatchers("/actuator/**").permitAll()
                    .anyExchange().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .csrf { it.disable() }
            .build()
    }

    @Bean
    fun reactiveUserDetailsService(): ReactiveUserDetailsService {
        val userDetails: UserDetails = User.builder()
            .username(this.id)
            .password(passwordEncoder().encode(this.password))
            .roles("ENDPOINT_ADMIN")
            .build()
        return MapReactiveUserDetailsService(userDetails)
    }
}