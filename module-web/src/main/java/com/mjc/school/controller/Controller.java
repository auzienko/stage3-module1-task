package com.mjc.school.controller;

import java.util.List;

public interface Controller<T> {

    List<T> getAll();

    T getById(Long id);

    T create(T object);

    T update(T object);

    boolean remove(Long id);

}
