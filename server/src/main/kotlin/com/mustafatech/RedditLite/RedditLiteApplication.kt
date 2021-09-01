package com.mustafatech.RedditLite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class RedditLiteApplication

fun main(args: Array<String>) {
	runApplication<RedditLiteApplication>(*args)
}
