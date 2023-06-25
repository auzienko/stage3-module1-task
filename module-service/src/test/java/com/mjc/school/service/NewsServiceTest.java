package com.mjc.school.service;

import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.repository.impl.NewsRepositoryImpl;
import com.mjc.school.repository.utils.PropertiesReader;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NewsNotFoundException;
import com.mjc.school.service.impl.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceTest {

    private NewsServiceImpl underTest;

    @BeforeEach
    void init() throws IOException {
        PropertiesReader propertiesReader = new PropertiesReader();

        String authorFileName = propertiesReader.getProperties().getProperty(PropertiesName.AUTHOR_FILE);
        String newsFileName = propertiesReader.getProperties().getProperty(PropertiesName.NEWS_FILE);

        AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl(authorFileName);
        NewsRepositoryImpl newsRepository = new NewsRepositoryImpl(newsFileName);

        underTest = new NewsServiceImpl(authorRepository, newsRepository);
    }

    @Test
    void findAll() {
        int expectedSize = 20;

        List<NewsDtoResponse> all = underTest.readAll();

        assertEquals(expectedSize, all.size());
    }

    @Test
    void findById() {
        NewsDtoResponse byId = underTest.readBy(1L);

        assertEquals(1L, byId.getId());
    }

    @Test
    void create() {
        long expectedId = 21L;

        NewsDtoResponse requestDto = new NewsDtoResponse();
        requestDto.setTitle("test title");
        requestDto.setContent("some content text");
        requestDto.setAuthorId(1L);

        NewsDtoResponse responseDto = underTest.create(requestDto);
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthorId(), responseDto.getAuthorId());
        assertEquals(expectedId, responseDto.getId());
        assertNotNull(responseDto.getLastUpdateDate());
        assertNotNull(responseDto.getCreateDate());
    }

    @Test
    void update() {
        long updateId = 1L;

        NewsDtoResponse requestDto = new NewsDtoResponse();
        requestDto.setTitle("test title");
        requestDto.setContent("some content text");
        requestDto.setAuthorId(1L);
        requestDto.setId(updateId);

        NewsDtoResponse responseDto = underTest.update(requestDto);

        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthorId(), responseDto.getAuthorId());
        assertEquals(1L, responseDto.getId());
    }

    @Test
    void remove() {
        long entityId = 1L;

        NewsDtoResponse beforeTest = underTest.readBy(entityId);

        underTest.delete(entityId);

        assertThrows(NewsNotFoundException.class,
                () -> {
                    underTest.readBy(entityId);
                });
    }
}