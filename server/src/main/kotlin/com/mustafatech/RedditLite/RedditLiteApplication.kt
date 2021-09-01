package com.mustafatech.RedditLite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@SpringBootApplication
class RedditLiteApplication {
//	@Bean
//	fun javaMailSender(): JavaMailSender {
//		val mailSender = JavaMailSenderImpl()
//		mailSender.host = "smtp.mailtrap.io";
//		mailSender.port = 25;
//		mailSender.protocol = "SMTP"
//
//		mailSender.username = "e88fa8057260e5";
//		mailSender.password = "87c71474fed1a4";
//		return JavaMailSenderImpl()
//	}
}



fun main(args: Array<String>) {
	runApplication<RedditLiteApplication>(*args)
}
