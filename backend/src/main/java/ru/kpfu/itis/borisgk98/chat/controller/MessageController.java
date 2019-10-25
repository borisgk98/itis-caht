package ru.kpfu.itis.borisgk98.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.borisgk98.chat.model.Message;
import ru.kpfu.itis.borisgk98.chat.service.MessageService;
import ru.kpfu.itis.borisgk98.chat.statical.StaticNames;

import java.util.List;

@RestController
@RequestMapping(StaticNames.SERVER_PREFIX + "/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("new")
    public List<Message> getNew() throws InterruptedException {
        List<Message> result;
        synchronized (messageService) {
            result = messageService.getUnreaded();
            if (result.isEmpty()) {
                messageService.wait();
            }
            result = messageService.getUnreaded();
            messageService.setReaded(result);
        }
        return result;
    }

    @PostMapping
    public Message postMessage(@RequestBody Message message) {
        Message result;
        synchronized (messageService) {
            result = messageService.create(message);
            messageService.notifyAll();
        }
        return result;
    }
}
