package ru.kpfu.itis.borisgk98.chat.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "message_status_entity")
public class MessageStatusEntity extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Message message;
    @Column(name = "status")
    @Enumerated(value = EnumType.ORDINAL)
    private MessageStatus status;
}
