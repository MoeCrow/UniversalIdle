package com.moecrow.demo.config;

import com.moecrow.demo.handler.CustomSubProtocolWebSocketHandler;
import com.moecrow.demo.interceptor.AuthHandshakeInterceptor;
import com.moecrow.demo.interceptor.MyChannelInterceptor;
import com.moecrow.demo.interceptor.MyHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    private MyHandshakeHandler myHandshakeHandler;

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

    class FastPrincipal implements Principal {

        private final String name;

        public FastPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/simple")
//                .setHandshakeHandler(new DefaultHandshakeHandler() {
//                    @Override
//                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//                        //握手成功后调用，可以在这里保存用户id
//                        return new FastPrincipal(((ServletServerHttpRequest) request).getServletRequest().getParameter("name"));
//                    }
//                })
                //解决跨域问题
                .addInterceptors(authHandshakeInterceptor)
                .setHandshakeHandler(myHandshakeHandler)
                .setAllowedOrigins("*");
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    @Override
    @Bean
    public WebSocketHandler subProtocolWebSocketHandler() {
        return new CustomSubProtocolWebSocketHandler(clientInboundChannel(), clientOutboundChannel());
    }

}
