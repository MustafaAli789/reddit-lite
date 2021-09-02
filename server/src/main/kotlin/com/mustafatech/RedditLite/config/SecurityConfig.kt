package com.mustafatech.RedditLite.config

import com.mustafatech.RedditLite.security.AuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(val userDetailsService: UserDetailsService,
                     val passwordEncoder: PasswordEncoder,
                     val authorizationFilter: AuthorizationFilter) : WebSecurityConfigurerAdapter() {

    //csrf attacks are common if there are sessions and cookies but we are building a stateless rest api
    //without sessions (USING jwt) so can disable
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        //spring will first check for token before trying tthe username authenticaion scheme i.e before checking if authenticated
        //if the token is valid the spring context authenticated will be set so shuld pass the autenticated filter
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Bean
    fun authManager(): AuthenticationManager{
        return super.authenticationManagerBean()
    }
}