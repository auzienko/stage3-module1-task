package com.mjc.school.controller;

import com.mjc.school.service.Service;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.List;

public class NewsController implements Controller<NewsDtoResponse> {
    private final Service<NewsDtoResponse> service;

    public NewsController(Service<NewsDtoResponse> service) {
        this.service = service;
    }

    @Override
    public List<NewsDtoResponse> getAll() {
        return service.readAll();
    }

    @Override
    public NewsDtoResponse getById(Long id) {
        return service.readBy(id);
    }

    @Override
    public NewsDtoResponse create(NewsDtoResponse object) {
        return service.create(object);
    }

    @Override
    public NewsDtoResponse update(NewsDtoResponse object) {
        return service.update(object);
    }

    @Override
    public boolean remove(Long id) {
        return service.delete(id);
    }
}

