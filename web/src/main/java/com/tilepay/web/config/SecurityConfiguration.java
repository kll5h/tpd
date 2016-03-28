package com.tilepay.web.config;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tilepay.core.service.LoginService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private LoginService loginService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //TODO CSRF?
        http
                .csrf().disable();

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/saveNewAccount").permitAll()
                .antMatchers(HttpMethod.GET, "/createNewAccount").permitAll()
                .antMatchers(HttpMethod.GET, "/checkBlockchain").permitAll()
                .antMatchers(HttpMethod.GET, "/login**").permitAll()
                .antMatchers(HttpMethod.GET, "/components/**").permitAll()
                .anyRequest().authenticated().and().formLogin()
                .loginPage("/login").failureUrl("/login?error").successHandler(new TPUrlAuthenticationSuccessHandler()).permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //        //TODO: md5 or sha?
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(loginService).passwordEncoder(encoder);
    }

    protected class TPUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        //TODO: 19.10.2014 Andrei Sljusar: @Inject
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
            String url = "/wallet";
            String firstLogin = request.getParameter("firstLogin");
            if (firstLogin != null && !firstLogin.isEmpty()) {
                url = "/profile";
            }
            redirectStrategy.sendRedirect(request, response, url);
        }

    }
}
