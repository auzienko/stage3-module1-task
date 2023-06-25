package com.mjc.school.repository;

import java.util.List;

public interface BaseRepository<T> {
    T create(T object);

    List<T> readAll();

    T readById(Long id);

    Boolean delete(T object);

    T update(T object);
}
