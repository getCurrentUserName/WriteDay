package org.write_day.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
		 		.simpMessageDestMatchers("/topic**").permitAll()
		 		.simpMessageDestMatchers("/app**").permitAll()
		 		.simpMessageDestMatchers("/anon**").permitAll()
				.anyMessage().permitAll();
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		super.configureClientOutboundChannel(registration);
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		super.configureWebSocketTransport(registration);
	}

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}