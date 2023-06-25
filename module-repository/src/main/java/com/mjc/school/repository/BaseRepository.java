package com.mjc.school.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> create(T object);

    List<T> findAll();

    Optional<T> findById(Long id);

    boolean remove(T object);

    Optional<T> update(T object);
}
