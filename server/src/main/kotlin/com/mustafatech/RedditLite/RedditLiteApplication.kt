package com.mustafatech.RedditLite

import com.mustafatech.RedditLite.config.SwaggerConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@Import(SwaggerConfiguration::class)
class RedditLiteApplication

fun main(args: Array<String>) {
	runApplication<RedditLiteApplication>(*args)
}
