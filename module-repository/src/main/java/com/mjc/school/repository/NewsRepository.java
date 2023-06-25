package com.mjc.school.repository;

import com.mjc.school.repository.model.News;

public class NewsRepository extends AbstractRepository<News> {

    public NewsRepository(String fileName, Class<News> type) {
        super(fileName, type);
    }

    @Override
    protected News objectClone(News object) {
        return new News(object);
    }
}
