package com.mjc.school.service;

import java.util.List;

public interface Service<T> {
    List<T> readAll();

    T readBy(Long id);

    T create(T object);

    T update(T object);

    Boolean delete(Long id);
}
