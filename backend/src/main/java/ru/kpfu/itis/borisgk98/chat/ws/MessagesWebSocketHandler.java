package ru.kpfu.itis.borisgk98.chat.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.kpfu.itis.borisgk98.chat.annotation.SocketHandler;
import ru.kpfu.itis.borisgk98.chat.model.entity.Message;
import ru.kpfu.itis.borisgk98.chat.model.entity.User;
import ru.kpfu.itis.borisgk98.chat.security.SecurityService;
import ru.kpfu.itis.borisgk98.chat.service.MessageService;
import ru.kpfu.itis.borisgk98.chat.service.UserService;
import ru.kpfu.itis.borisgk98.chat.service.WSMessageService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MessagesWebSocketHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService;
    private final SecurityService securityService;
    private final UserService userService;
    private final WSMessageService wsMessageService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.put(session.getId(), session);
    }

    @Override
    @SocketHandler
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        HttpHeaders headers = session.getHandshakeHeaders();
        String messageAsString = (String) message.getPayload();
        Message body = objectMapper.readValue(messageAsString, Message.class);
        body.setUser(userService.findByLogin(session.getPrincipal().getName()).orElse(null));
        messageService.create(body);
        for (WebSocketSession currentSession : sessions.values()) {
            currentSession.sendMessage(wsMessageService.buildAsTextMessage(body));
        }
        throw new Exception("Kek");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session.getId());
    }
}
