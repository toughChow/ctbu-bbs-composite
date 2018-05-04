package bbs.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // 注册点对点协议
        stompEndpointRegistry.addEndpoint("/endpointP2P").withSockJS();

        // 注册公告协议
        stompEndpointRegistry.addEndpoint("/endpointBoard").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 点对点增加一个queue代理
        registry.enableSimpleBroker("/queue","/topic");

//        点对面增加一个topic代理 若添加此处 p2p报错。
//        registry.enableSimpleBroker("/topic");
    }
}
