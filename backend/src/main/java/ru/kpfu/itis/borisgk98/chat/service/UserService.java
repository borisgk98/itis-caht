package ru.kpfu.itis.borisgk98.chat.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.borisgk98.chat.model.entity.User;
import ru.kpfu.itis.borisgk98.chat.repo.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@Service
public class UserService extends AbstractCrudService<User> {

    public UserService(UserRepo repository, EntityManager em, CriteriaBuilder cb) {
        super(repository, em, cb);
    }

    public boolean existByLogin(String login) {
        return ((UserRepo) repository).existsByLogin(login);
    }

    public Optional<User> findByLogin(String login) {
        return ((UserRepo) repository).findByLogin(login);
    }
}
