package ru.kpfu.itis.borisgk98.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.borisgk98.chat.model.entity.Message;
import ru.kpfu.itis.borisgk98.chat.model.entity.MessageStatus;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

    @Query("select m from Message m join fetch m.statusEntity se where se.status = :status")
    List<Message> findAllByStatus(@Param("status") MessageStatus status);
}
