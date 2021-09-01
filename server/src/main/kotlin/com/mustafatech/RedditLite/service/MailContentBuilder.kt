package com.mustafatech.RedditLite.service

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailContentBuilder(val templateEngine: TemplateEngine) {

    fun build(msg: String): String {
        val ctx = Context()
        ctx.setVariable("message", msg)
        return templateEngine.process("mailTemplate", ctx)
    }

}