package com.mustafatech.RedditLite.service

import com.mustafatech.RedditLite.repository.UserRepo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val userRepo: UserRepo) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username)
                ?: throw UsernameNotFoundException("User with name $username not found")
        return User(user.username,
                user.password,
                user.enabled,
                true,
                true,
                true,
                getAuthoritiesSingletonList("USER"))
    }
    private fun getAuthoritiesSingletonList(role: String): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }
}