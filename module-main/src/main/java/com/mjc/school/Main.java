package com.mjc.school;

import com.mjc.school.controller.NewsController;
import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.repository.impl.NewsRepositoryImpl;
import com.mjc.school.repository.utils.PropertiesReader;
import com.mjc.school.service.Service;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.impl.NewsServiceImpl;
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

        AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl(authorFileName);
        NewsRepositoryImpl newsRepository = new NewsRepositoryImpl(newsFileName);

        Service<NewsDtoResponse> newsService = new NewsServiceImpl(authorRepository, newsRepository);

        NewsController newsController = new NewsController(newsService);

        return new ConsoleView(newsController);
    }
}

