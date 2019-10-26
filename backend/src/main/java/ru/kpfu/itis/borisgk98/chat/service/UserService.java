package ru.kpfu.itis.borisgk98.chat.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.borisgk98.chat.model.entity.User;
import ru.kpfu.itis.borisgk98.chat.repo.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Service
public class UserService extends AbstractCrudService<User> {

    public UserService(JpaRepository<User, Long> repository, EntityManager em, CriteriaBuilder cb) {
        super(repository, em, cb);
    }

    public boolean existByLogin(String login) {
        return ((UserRepo) repository).existsByLogin(login);
    }
}
