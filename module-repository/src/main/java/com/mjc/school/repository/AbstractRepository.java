package com.mjc.school.repository;

import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.utils.YmlReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractRepository<T extends BaseEntity> implements BaseRepository<T> {
    private List<T> content = new ArrayList<>();
    private final AtomicLong index;

    protected AbstractRepository(String fileName, Class<T> type) {
        contentInit(fileName, type);
        index = new AtomicLong(getMaxIndex());
    }

    private void contentInit(String fileName, Class<T> type) {
        content = YmlReader.getData(fileName, type);
    }

    private Long getMaxIndex() {
        return content.stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public Optional<T> create(T object) {
        if (object == null) {
            return Optional.empty();
        }
        object.setId(index.addAndGet(1L));
        content.add(object);
        return findById(object.getId());
    }

    @Override
    public List<T> findAll() {
        List<T> resultList = new ArrayList<>();
        content.forEach(e -> resultList.add(objectClone(e)));
        return resultList;
    }

    @Override
    public Optional<T> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return content.stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            return false;
        }
        return content.remove(object);
    }

    @Override
    public Optional<T> update(T object) {
        if (object == null) {
            return Optional.empty();
        }
        Optional<T> byId = findById(object.getId());
        if (byId.isEmpty()) {
            return byId;
        }
        return byId.map(e -> objectClone(object));
    }

    protected abstract T objectClone(T object);
}
