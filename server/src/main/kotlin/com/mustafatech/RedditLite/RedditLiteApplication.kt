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
//		mailSender.host = "s***REMOVED***";
//		mailSender.port = 25;
//		mailSender.protocol = "SMTP"
//
//		mailSender.username = "***REMOVED***";
//		mailSender.password = "***REMOVED***";
//		return JavaMailSenderImpl()
//	}
}



fun main(args: Array<String>) {
	runApplication<RedditLiteApplication>(*args)
}
