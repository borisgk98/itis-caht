package ru.kpfu.itis.borisgk98.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import ru.kpfu.itis.borisgk98.chat.model.dto.WSMessageWrapper;
import ru.kpfu.itis.borisgk98.chat.model.enums.WSMessageType;

@Component
@RequiredArgsConstructor
public class WSMessageService {
    private final ObjectMapper objectMapper;

    public WSMessageWrapper build(Object o) {
        try {
            return new WSMessageWrapper(WSMessageType.fromClass(o.getClass()), objectMapper.writeValueAsString(o));
        }
        catch (Exception e) {
            return null;
        }
    }

    public String buildAsString(Object o) {
        try {
            return objectMapper.writeValueAsString(build(o));
        }
        // TODO нормальная обработка
        catch (Exception e) {
            return null;
        }
    }

    public TextMessage buildAsTextMessage(Object o) {
        return new TextMessage(buildAsString(o));
    }
}
