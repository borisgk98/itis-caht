package ru.kpfu.itis.borisgk98.chat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.borisgk98.chat.model.enums.WSMessageType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSMessageWrapper {
    private WSMessageType type;
    private String data;
}
