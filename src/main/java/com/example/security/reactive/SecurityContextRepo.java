package com.example.security.reactive;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepo implements ServerSecurityContextRepository {
//    @Override
//    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
//        // Don't know yet where this is for.
//        return null;
//    }
//
//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
//        // JwtAuthenticationToken and GuestAuthenticationToken are custom Authentication tokens.
//    	System.out.println("LOAD logic");
////        Authentication authentication = true ? 
////            new JwtAuthenticationToken(null) :
////            new GuestAuthenticationToken();
//        return new SecurityContextImpl(null);
//    }

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		// Don't know yet where this is for.
		return null;
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		System.out.println("CHECK auth here");
      Authentication authentication = null;
		return  Mono.just(new SecurityContextImpl(authentication));
	}
}
