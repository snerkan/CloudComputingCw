package com.pg4.cloudcw;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
        .antMatchers("/","/index","/index.jsp")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .loginPage("/")  
        .defaultSuccessUrl("/items")
        .failureUrl("/index").and()
    	.logout().logoutUrl("/logout").logoutSuccessUrl("/index").deleteCookies("JSESSIONID")
    	.invalidateHttpSession(true).permitAll();
;
    }
}