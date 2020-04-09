package cz.vhromada.catalog.gui

import cz.vhromada.catalog.CatalogConfiguration
import cz.vhromada.common.account.AccountConfiguration
import cz.vhromada.common.account.service.AccountService
import cz.vhromada.common.entity.Account
import cz.vhromada.common.provider.AccountProvider
import cz.vhromada.common.provider.TimeProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

/**
 * A class represents Spring configuration for catalog.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(CatalogConfiguration::class, AccountConfiguration::class, AuthenticationConfiguration::class)
class CatalogSwingConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun accountProvider(): AccountProvider {
        return object : AccountProvider {

            override fun getAccount(): Account {
                return SecurityContextHolder.getContext().authentication.principal as Account
            }

        }
    }

    @Bean
    fun timeProvider(): TimeProvider {
        return object : TimeProvider {

            override fun getTime(): LocalDateTime {
                return LocalDateTime.now()
            }

        }
    }

    @Bean
    fun authenticationProvider(passwordEncoder: PasswordEncoder,
                               accountService: AccountService): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(accountService)
        provider.setPostAuthenticationChecks {
            if (it.authorities.none { auth -> listOf("ROLE_ADMIN", "ROLE_USER").contains(auth.authority) }) {
                throw BadCredentialsException("Bad credentials")
            }
        }
        return provider
    }

    @Bean
    fun authenticationManager(
            builder: AuthenticationManagerBuilder,
            provider: AuthenticationProvider): AuthenticationManager {
        return builder.authenticationProvider(provider)
                .build()
    }

}
