package ru.kpfu.itis.borisgk98.chat.model;

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
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "ic", name = "message_status_entity")
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
