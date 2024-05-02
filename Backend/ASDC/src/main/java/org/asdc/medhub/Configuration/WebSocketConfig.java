package org.asdc.medhub.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket communication.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * Custom configuration object
     */
    private final CustomConfigurations customConfigurations;

    /**
     * Parameterized constructor
     * @param customConfigurations custom configuration object
     */
    public WebSocketConfig(CustomConfigurations customConfigurations) {
        this.customConfigurations = customConfigurations;
    }

    /**
     * Registers the STOMP endpoints.
     *
     * @param registry The registry for STOMP endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Adds an endpoint for WebSocket communication
        registry.addEndpoint("/ws").setAllowedOriginPatterns(customConfigurations.frontEndAppUrl).withSockJS();
    }

    /**
     * Configures the message broker.
     *
     * @param registry The registry for message brokers.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/user");
        registry.setUserDestinationPrefix("/user");
    }
}

