package org.jeongmo.healthcheck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HealthCheckApplication

fun main(args: Array<String>) {
	runApplication<HealthCheckApplication>(*args)
}
