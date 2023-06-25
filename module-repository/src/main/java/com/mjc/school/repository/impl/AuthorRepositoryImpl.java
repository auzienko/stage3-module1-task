package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.datasourse.DataSource;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class AuthorRepositoryImpl implements BaseRepository<AuthorModel> {

    private static final AuthorModel EMPTY = new AuthorModel();
    private final DataSource<AuthorModel> dataSource;
    private final AtomicLong index;

    public AuthorRepositoryImpl(String fileName) {
        dataSource = new DataSource<>(fileName, AuthorModel.class);
        index = new AtomicLong(getMaxIndex());
    }

    private List<AuthorModel> getContent() {
        return dataSource.getContent();
    }

    private Long getMaxIndex() {
        return getContent().stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public AuthorModel create(AuthorModel object) {
        if (object == null) {
            return EMPTY;
        }
        object.setId(index.addAndGet(1L));
        getContent().add(object);
        return readById(object.getId());
    }

    @Override
    public List<AuthorModel> readAll() {
        List<AuthorModel> resultList = new ArrayList<>();
        getContent().forEach(e -> resultList.add(objectClone(e)));
        return resultList;
    }

    @Override
    public AuthorModel readById(Long id) {
        if (id == null) {
            return EMPTY;
        }
        return getContent().stream()
                .filter(e -> e.getId().equals(id))
                .findAny()
                .orElse(EMPTY);
    }

    @Override
    public Boolean delete(AuthorModel object) {
        if (object == null) {
            return false;
        }
        return getContent().remove(object);
    }

    @Override
    public AuthorModel update(AuthorModel object) {
        if (object == null) {
            return EMPTY;
        }
        AuthorModel current = readById(object.getId());
        if (current.getId() == null) {
            return EMPTY;
        }
        current = objectClone(object);
        return current;
    }

    protected AuthorModel objectClone(AuthorModel object) {
        return new AuthorModel(object);
    }
}
