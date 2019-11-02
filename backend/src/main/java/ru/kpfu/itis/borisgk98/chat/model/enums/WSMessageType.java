package ru.kpfu.itis.borisgk98.chat.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kpfu.itis.borisgk98.chat.model.entity.Message;

@Getter
@AllArgsConstructor
public enum WSMessageType {
    MESSAGE(Message.class), ERROR(Exception.class);

    private Class mClass;

    public static WSMessageType fromClass(Class c) {
        for (WSMessageType wsMessageType : values()) {
            if (wsMessageType.getMClass().equals(c)) {
                return wsMessageType;
            }
        }
        return null;
    }
}
