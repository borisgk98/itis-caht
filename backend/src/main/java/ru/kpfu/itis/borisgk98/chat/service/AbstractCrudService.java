package ru.kpfu.itis.borisgk98.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.borisgk98.chat.exception.ModelNotFound;
import ru.kpfu.itis.borisgk98.chat.model.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractCrudService<T extends AbstractEntity> implements ICrudService<T, Long> {
    protected final JpaRepository<T, Long> repository;
    protected final EntityManager em;
    protected final CriteriaBuilder cb;

    @Override
    public T create(T m) {
        return repository.save(m);
    }

    @Override
    public T read(Long id) throws ModelNotFound {
        Optional<T> m = repository.findById(id);
        if (!m.isPresent()) {
            throw new ModelNotFound();
        }
        return m.get();
    }

    @Override
    public T update(T m) throws ModelNotFound {
        if (!existById(m.getId())) {
            throw new ModelNotFound();
        }
        return repository.save(m);
    }

    @Override
    public void delete(Long id) throws ModelNotFound {
        if (!existById(id)) {
            throw new ModelNotFound();
        }
        repository.deleteById(id);
    }

    @Override
    public boolean exist(T m) {
        return repository.exists(Example.of(m));
    }

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public List<T> getRange(Integer offset, Integer limit) {
        Pageable request = PageRequest.of(offset, limit);
        return repository.findAll(request).getContent();
    }
}