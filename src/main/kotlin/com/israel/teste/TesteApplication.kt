package com.israel.teste

import lombok.extern.slf4j.Slf4j
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.math.log

@SpringBootApplication
class TesteApplication


	private val logger = LogManager.getLogger(TesteApplication::class.java)

	fun main(args: Array<String>) {
		runApplication<TesteApplication>(*args)
		logger.info("Starting application with version [0.0.1-SNAPSHOT]")
	}
