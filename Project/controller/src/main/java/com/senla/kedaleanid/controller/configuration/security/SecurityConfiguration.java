package com.senla.kedaleanid.controller.configuration.security;

import com.senla.kedaleanid.controller.configuration.exceptionhandler.RestAccessDeniedHandler;
import com.senla.kedaleanid.controller.configuration.exceptionhandler.RestAuthenticationEntryPoint;
import com.senla.kedaleanid.controller.configuration.security.principal.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 AuthenticationSuccessHandler authenticationSuccessHandler,
                                 LogoutSuccessHandler logoutSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/ads", "/ads/{adId}", "/ads/{adId}/comments/", "/users/*", "/users/{userId}").permitAll()
                .antMatchers("/chats/{userId}/**", "/users/{userId}/**").access("(@userSecurity.hasUserId(authentication, #userId) and hasAnyRole('ADMIN','USER')) or hasRole('ADMIN')")
                .antMatchers("/ads/{adId}/comments/**", "/ads/edit-ad", "/ads/create-ad", "/ads/delete-ad", "/pay-ad", "/chats/send-message",
                        "chats/start-chat", "chats/update-chat", "chats/delete-chat", "chats/update-message",
                        "chats/delete-message").hasAnyRole("ADMIN", "USER")
                .antMatchers("/logout").authenticated()
                .antMatchers("/sign-in", "/login", "/sign-up", "/restore-password/**").anonymous()
                .and().formLogin().loginPage("/login").successHandler(authenticationSuccessHandler)
                .and().logout().deleteCookies("JSESSIONID").invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(logoutSuccessHandler)
                .and().httpBasic()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint())
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and().csrf().disable();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RestAccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public UserSecurity userSecurity() {
        return new UserSecurity();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
