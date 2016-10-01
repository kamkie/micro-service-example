package net.devopssolutions.microservice.registration.security;

import net.devopssolutions.microservice.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Configuration
    @Order(2)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**");

            http.csrf().disable();

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.anonymous();
            // http
            // .authorizeRequests()
            // .anyRequest().hasRole("USER")
            // .and()
            // .httpBasic();
        }
    }

    @Configuration
    @Order(1)
    public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/admin/**");

            http.csrf().disable();

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.anonymous();
            // http
            // .authorizeRequests()
            // .anyRequest().hasRole("USER")
            // .and()
            // .httpBasic();
        }
    }

}
