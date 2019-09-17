package com.example.security.reactive;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class EntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
    	System.out.println("Run entry point");

    	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    	exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    	
    	GdnBaseResponse response = new GdnBaseResponse(UUID.randomUUID().toString(), "ERR-123");
        DataBufferFactory factory = new DefaultDataBufferFactory();
    	DataBuffer buffer = factory.allocateBuffer().write(toByteArray(response));
    	return exchange.getResponse().writeWith(Mono.just(buffer));
    }
    
    private byte[] toByteArray(Object obj) {
    	try {
	    	ObjectMapper mapper = new ObjectMapper();
	    	return mapper.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8);
    	} catch (Exception e) {
    		System.out.println("ERROR when entry point to byte array");
    		return "error response".getBytes(StandardCharsets.UTF_8);
		}
    }
}
