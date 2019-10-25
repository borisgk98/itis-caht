package ru.kpfu.itis.borisgk98.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.borisgk98.chat.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
