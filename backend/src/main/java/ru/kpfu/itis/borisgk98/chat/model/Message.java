package ru.kpfu.itis.borisgk98.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(schema = "ic", name = "message")
public class Message extends AbstractEntity {

    @ManyToOne
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "data")
    private String data;
    @Column(name = "type")
    private MessageType type;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private MessageStatusEntity statusEntity;
}
