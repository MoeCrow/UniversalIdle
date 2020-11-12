package com.moecrow.demo.config;

import com.moecrow.demo.handler.StompWebSocketHandler;
import com.moecrow.demo.handler.UserIdHandshakeHandler;
import com.moecrow.demo.interceptor.AuthHandshakeInterceptor;
import com.moecrow.demo.interceptor.MyChannelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * STOMP协议的WebStocket
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/2/28
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends WebSocketMessageBrokerConfigurationSupport implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private AuthHandshakeInterceptor authHandshakeInterceptor;

    @Autowired
    private UserIdHandshakeHandler userIdHandshakeHandler;

    @Autowired
    private MyChannelInterceptor myChannelInterceptor;

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        super.configureWebSocketTransport(registry);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return super.configureMessageConverters(messageConverters);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(myChannelInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        super.configureClientOutboundChannel(registration);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/simple")
                .addInterceptors(authHandshakeInterceptor)
                .setHandshakeHandler(userIdHandshakeHandler)
                .setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .enableSimpleBroker("/topic", "/queue");
    }

    @Override
    @Bean
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new StompWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
    }

}
