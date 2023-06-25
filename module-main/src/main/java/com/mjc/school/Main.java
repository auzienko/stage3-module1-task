package com.mjc.school;

import com.mjc.school.controller.NewsController;
import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.utils.PropertiesReader;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.Service;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.view.View;
import com.mjc.school.view.console.ConsoleView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        initView()
                .show();
    }

    private static View initView() throws IOException {
        PropertiesReader propertiesReader = new PropertiesReader();

        String authorFileName = propertiesReader.getProperties().getProperty(PropertiesName.AUTHOR_FILE);
        String newsFileName = propertiesReader.getProperties().getProperty(PropertiesName.NEWS_FILE);

        AuthorRepository authorRepository = new AuthorRepository(authorFileName, Author.class);
        NewsRepository newsRepository = new NewsRepository(newsFileName, News.class);

        Service<NewsDtoResponse> newsService = new NewsService(authorRepository, newsRepository);

        NewsController newsController = new NewsController(newsService);

        return new ConsoleView(newsController);
    }
}

