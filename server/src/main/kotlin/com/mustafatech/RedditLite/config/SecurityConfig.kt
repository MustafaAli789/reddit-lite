package com.mustafatech.RedditLite.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    //csrf attacks are common if there are sessions and cookies but we are building a stateless rest api
    //without sessions (USING jwt) so can disable
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        super.configure(auth)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}