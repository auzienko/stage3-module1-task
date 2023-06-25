package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.datasourse.DataSource;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.model.NewsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class NewsRepositoryImpl implements BaseRepository<NewsModel> {
    private static final NewsModel EMPTY = new NewsModel();
    private final DataSource<NewsModel> dataSource;
    private final AtomicLong index;

    public NewsRepositoryImpl(String fileName) {
        dataSource = new DataSource<>(fileName, NewsModel.class);
        index = new AtomicLong(getMaxIndex());
    }

    private List<NewsModel> getContent() {
        return dataSource.getContent();
    }

    private Long getMaxIndex() {
        return getContent().stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public NewsModel create(NewsModel object) {
        if (object == null) {
            return EMPTY;
        }
        object.setId(index.addAndGet(1L));
        getContent().add(object);
        return readById(object.getId());
    }

    @Override
    public List<NewsModel> readAll() {
        List<NewsModel> resultList = new ArrayList<>();
        getContent().forEach(e -> resultList.add(objectClone(e)));
        return resultList;
    }

    @Override
    public NewsModel readById(Long id) {
        if (id == null) {
            return EMPTY;
        }

        return getContent().stream()
                .filter(e -> e.getId().equals(id))
                .findAny()
                .orElse(EMPTY);
    }

    @Override
    public Boolean delete(Long id) {
        if (id == null) {
            return false;
        }
        Optional<NewsModel> any = getContent().stream()
                .filter(e -> e.getId().equals(id)).findAny();

        return any.map(e -> getContent().remove(e))
                .orElse(false);
    }

    @Override
    public NewsModel update(NewsModel object) {
        if (object == null) {
            return EMPTY;
        }
        NewsModel currentNews = readById(object.getId());
        if (currentNews.getId() == null) {
            return EMPTY;
        }

        currentNews = objectClone(object);

        return currentNews;
    }

    protected NewsModel objectClone(NewsModel object) {
        return new NewsModel(object);
    }
}
