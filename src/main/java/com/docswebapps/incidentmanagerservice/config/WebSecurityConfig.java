package com.docswebapps.incidentmanagerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String INCIDENT_DETAILS_URL = "/api/v1/incident-details";
    private static final String SERVICE_DETAILS_URL =  "/api/v1/service-details";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,INCIDENT_DETAILS_URL).permitAll()
                .antMatchers(HttpMethod.GET,INCIDENT_DETAILS_URL+"/**").permitAll()
                .antMatchers(HttpMethod.PUT,INCIDENT_DETAILS_URL+"/**").permitAll()
                .antMatchers(HttpMethod.DELETE,INCIDENT_DETAILS_URL+"/**").permitAll()
                .antMatchers(HttpMethod.GET, SERVICE_DETAILS_URL+"/**").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable();
    }
}
