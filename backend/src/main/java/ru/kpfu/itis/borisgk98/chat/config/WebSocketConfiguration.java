package ru.kpfu.itis.borisgk98.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.kpfu.itis.borisgk98.chat.statical.StaticNames;
import ru.kpfu.itis.borisgk98.chat.ws.AuthHandshakeHandler;
import ru.kpfu.itis.borisgk98.chat.ws.MessagesWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private MessagesWebSocketHandler messagesWebSocketHandler;

    @Autowired
    private AuthHandshakeHandler authHandshakeHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messagesWebSocketHandler, StaticNames.SERVER_PREFIX + "ws/chat")
                .setHandshakeHandler(authHandshakeHandler);
    }
}
