package com.orange.proposta.configuracoes;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class ConfiguracoesDeSeguranca extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers(HttpMethod.POST, "/propostas/").hasAuthority("SCOPE_proposta:write")
                .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_proposta:read")
                .antMatchers(HttpMethod.GET, "/actuator/prometheus").hasAuthority("SCOPE_proposta:read")
                .anyRequest().authenticated()
        ).sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .csrf().disable()
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}
