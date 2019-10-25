package ru.kpfu.itis.borisgk98.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.borisgk98.chat.model.Message;
import ru.kpfu.itis.borisgk98.chat.model.MessageStatus;
import ru.kpfu.itis.borisgk98.chat.model.MessageStatusEntity;
import ru.kpfu.itis.borisgk98.chat.repo.MessageRepo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class MessageService extends AbstractCrudService<Message> {

    private final MessageRepo messageRepo;

    public MessageService(JpaRepository<Message, Long> repository, EntityManager em, CriteriaBuilder cb, MessageRepo messageRepo) {
        super(repository, em, cb);
        this.messageRepo = messageRepo;
    }

    @Override
    public Message create(Message m) {
        m.setStatusEntity(MessageStatusEntity.builder().status(MessageStatus.UNREADED).message(m).build());
        return super.create(m);
    }

    public List<Message> getUnreaded() {
        return messageRepo.findAllByStatus(MessageStatus.UNREADED);
    }

    public void setReaded(List<Message> messages) {
        messages.forEach(message -> {
            message.getStatusEntity().setStatus(MessageStatus.READED);
            messageRepo.save(message);
        });
    }
}
