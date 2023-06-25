package com.mjc.school.service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();

    T findById(Long id);

    T create(T object);

    T update(T object);

    boolean remove(Long id);
}
