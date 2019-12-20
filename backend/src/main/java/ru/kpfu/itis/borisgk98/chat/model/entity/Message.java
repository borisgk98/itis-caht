package ru.kpfu.itis.borisgk98.chat.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
public class Message extends AbstractEntity {

    @CreatedBy
    @ManyToOne
    private User user;

    @CreatedDate
    @Column(name = "date")
    private Date date;

    @Column(name = "data")
    private String data;
    @Column(name = "type")
    private MessageType type;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private MessageStatusEntity statusEntity;

    @ManyToOne
    private Room room;
}
