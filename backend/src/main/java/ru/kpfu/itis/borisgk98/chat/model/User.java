package ru.kpfu.itis.borisgk98.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "ic", name = "chat_user")
public class User extends AbstractEntity {

    private String login;
    private String passHash;

    @OneToMany
    private List<Message> messages;
}
