package ru.kpfu.itis.borisgk98.chat.service;

import ru.kpfu.itis.borisgk98.chat.exception.ModelNotFound;
import ru.kpfu.itis.borisgk98.chat.model.entity.AbstractEntity;

import java.util.List;

public interface ICrudService<T extends AbstractEntity, F> {
    T create(T m);
    T read(F id) throws ModelNotFound;
    T update(T m) throws ModelNotFound;
    void delete(F id) throws ModelNotFound;
    boolean existById(F id);
    boolean exist(T m);
    List<T> getAll();
    List<T> getRange(Integer offset, Integer limit);
}