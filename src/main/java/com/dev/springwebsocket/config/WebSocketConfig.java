package com.dev.springwebsocket.config;

import com.dev.springwebsocket.handler.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { // 경로 등록
        registry
                .addHandler(customSocketHandler(), "/chat")
                .setAllowedOrigins("*");

    }

    public WebSocketHandler customSocketHandler() {
        return new WebSocketHandler();
    }
}
