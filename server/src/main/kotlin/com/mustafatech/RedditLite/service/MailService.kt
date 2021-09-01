package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.exception.SpringRedditException
import com.mustafatech.RedditLite.model.NotificationEmail
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.event.MailEvent

@Service
class MailService(val mailSender: JavaMailSender, val mailContentBuilder: MailContentBuilder) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Async
    fun sendMail(notificationEmail: NotificationEmail) {
        val msgPreparator = MimeMessagePreparator { mimeMessage ->
            val msgHelper = MimeMessageHelper(mimeMessage)
            msgHelper.setFrom("springreddit@email.com")
            msgHelper.setTo(notificationEmail.recipient)
            msgHelper.setSubject(notificationEmail.subject)
            msgHelper.setText(mailContentBuilder.build(notificationEmail.body))
        }
        try {
            mailSender.send(msgPreparator)
            logger.info("Activation email sent")
        } catch (e: MailException) {
            throw SpringRedditException("Exception occurred when sending mail to ${notificationEmail.recipient}")
        }
    }

}