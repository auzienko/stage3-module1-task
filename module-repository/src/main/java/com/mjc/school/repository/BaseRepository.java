package com.mjc.school.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> create(T object);

    List<T> readAll();

    Optional<T> readById(Long id);

    boolean delete(T object);

    Optional<T> update(T object);
}
