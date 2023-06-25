package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.YmlReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class NewsDataSourceRepositoryImpl implements BaseRepository<NewsModel> {

    private List<NewsModel> content = new ArrayList<>();
    private final AtomicLong index;

    public NewsDataSourceRepositoryImpl(String fileName) {
        contentInit(fileName);
        index = new AtomicLong(getMaxIndex());
    }

    private void contentInit(String fileName) {
        content = YmlReader.getData(fileName, NewsModel.class);
    }

    private Long getMaxIndex() {
        return content.stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public Optional<NewsModel> create(NewsModel object) {
        if (object == null) {
            return Optional.empty();
        }
        object.setId(index.addAndGet(1L));
        content.add(object);
        return readById(object.getId());
    }

    @Override
    public List<NewsModel> readAll() {
        List<NewsModel> resultList = new ArrayList<>();
        content.forEach(e -> resultList.add(objectClone(e)));
        return resultList;
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return content.stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    @Override
    public boolean delete(NewsModel object) {
        if (object == null) {
            return false;
        }
        return content.remove(object);
    }

    @Override
    public Optional<NewsModel> update(NewsModel object) {
        if (object == null) {
            return Optional.empty();
        }
        Optional<NewsModel> byId = readById(object.getId());
        if (byId.isEmpty()) {
            return byId;
        }
        return byId.map(e -> objectClone(object));
    }

    protected NewsModel objectClone(NewsModel object) {
        return new NewsModel(object);
    }
}
