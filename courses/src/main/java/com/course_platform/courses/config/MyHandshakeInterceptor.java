package com.course_platform.courses.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
public class MyHandshakeInterceptor implements HandshakeInterceptor {



    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        // Your WebSocket handshake logic
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("s1");
        if (request instanceof ServletServerHttpRequest) {
            System.out.println("success");
            HttpServletResponse servletResponse = ((ServletServerHttpResponse
                    ) response).getServletResponse();
            servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
        return true;
    }
}
