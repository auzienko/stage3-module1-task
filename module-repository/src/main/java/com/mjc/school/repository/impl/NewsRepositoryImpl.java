package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.utils.YmlReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class NewsRepositoryImpl implements BaseRepository<News> {

    private List<News> content = new ArrayList<>();
    private final AtomicLong index;

    public NewsRepositoryImpl(String fileName) {
        contentInit(fileName);
        index = new AtomicLong(getMaxIndex());
    }

    private void contentInit(String fileName) {
        content = YmlReader.getData(fileName, News.class);
    }

    private Long getMaxIndex() {
        return content.stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public Optional<News> create(News object) {
        if (object == null) {
            return Optional.empty();
        }
        object.setId(index.addAndGet(1L));
        content.add(object);
        return findById(object.getId());
    }

    @Override
    public List<News> findAll() {
        List<News> resultList = new ArrayList<>();
        content.forEach(e -> resultList.add(objectClone(e)));
        return resultList;
    }

    @Override
    public Optional<News> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return content.stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    @Override
    public boolean remove(News object) {
        if (object == null) {
            return false;
        }
        return content.remove(object);
    }

    @Override
    public Optional<News> update(News object) {
        if (object == null) {
            return Optional.empty();
        }
        Optional<News> byId = findById(object.getId());
        if (byId.isEmpty()) {
            return byId;
        }
        return byId.map(e -> objectClone(object));
    }

    protected News objectClone(News object) {
        return new News(object);
    }
}
