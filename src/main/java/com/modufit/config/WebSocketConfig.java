package com.modufit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커 설정
        // /topic: 1:N (한 명이 발행하면 구독한 여러 명이 받음)
        // 공모전/행사별 채팅방에서 모든 참여자가 메시지를 받음
        config.enableSimpleBroker("/topic");

        // 클라이언트에서 메시지 전송 시 사용할 prefix
        // 예: /app/chat/message
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트
        // 클라이언트는 ws://localhost:8080/ws-chat 으로 연결
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")  // CORS 설정 (실제 운영시 구체적으로 지정 필요)
                .withSockJS();  // SockJS fallback 옵션 (WebSocket을 지원하지 않는 브라우저 대응)
    }
}