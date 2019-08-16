package com.zikozee.springboot.thymeleafdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider(){
//        return new
//                ActiveDirectoryLdapAuthenticationProvider("gloworld.com", "ldap://192.168.0.6:389/");
//    }

    //Requires spring-security-lda dependency
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) {
//        ActiveDirectoryLdapAuthenticationProvider adProvider = new ActiveDirectoryLdapAuthenticationProvider("gloworld.com", "ldap://192.168.0.6:389/");
//        adProvider.setConvertSubErrorCodesToExceptions(true);
//        adProvider.setUseAuthenticationRequestCredentials(true);
//
//        auth.authenticationProvider(adProvider);
//        auth.eraseCredentials(false); // to keep login values cos default it is erased
//    }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.inMemoryAuthentication()
                    .withUser("zikozee").password(passwordEncoder().encode("ziko123")).roles("EMPLOYEE")
                    .and()
                    .withUser("mary").password(passwordEncoder().encode("test123")).roles("MANAGER")
                    .and()
                    .withUser("susan").password(passwordEncoder().encode("test123")).roles("ADMIN");
        }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
