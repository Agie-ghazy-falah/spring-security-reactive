package com.example.security.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfiguration {
	
    private static final String[] AUTH_WHITELIST = {
            "/books/**",
            "/otherPath/**",
            "/product/**"
    };
    
	@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, EntryPoint entryPoint) {
		System.out.println("Run security filter");
        http.httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable();
        
            http
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeExchange()
                .pathMatchers(AUTH_WHITELIST).access((auth, context) -> {
                	String authKey = getHeader("Auth", context);
                	boolean isAuthenticate = (authKey != null);
                	return Mono.just(new AuthorizationDecision(isAuthenticate));
                })
                .anyExchange().authenticated();
        return http.build();
    }
	
	private String getHeader(String key, AuthorizationContext context) {
		try {
			String result = context.getExchange().getRequest().getHeaders().get(key).toString();
			System.out.println("Header of " + key + " is " + result);
			return result;
		} catch(Exception e) {
			return null;
		}
	}
	
//	@Bean
//	public MapReactiveUserDetailsService userDetailsService() {
//		System.out.println("get user detail");
//	    UserDetails user = User
//	      .withUsername("user")
//	      .password(passwordEncoder().encode("password"))
//	      .roles("USER")
//	      .build();
//	    return new MapReactiveUserDetailsService(user);
//	}
//	
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
	
}
