package com.mustafatech.RedditLite.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
data class AppConfig(
        var url: String = ""
)