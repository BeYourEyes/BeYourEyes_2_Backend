package com.beyoureyes.beyoureyes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BeyoureyesApplication

fun main(args: Array<String>) {
	runApplication<BeyoureyesApplication>(*args)
}
