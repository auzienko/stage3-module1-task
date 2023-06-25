package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class NewsService implements Service<NewsDtoResponse> {
    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public NewsService(AuthorRepository authorRepository, NewsRepository newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsDtoResponse> findAll() {

        return newsRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, NewsDtoResponse.class))
                .toList();
    }

    @Override
    public NewsDtoResponse findById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(NewsNotFoundException::new);

        return modelMapper.map(news, NewsDtoResponse.class);
    }

    @Override
    public NewsDtoResponse create(NewsDtoResponse object) {

        validate(object);
        checkAuthorExist(object);
        dtoIdMustBeNull(object);

        News news = modelMapper.map(object, News.class);

        LocalDateTime now = LocalDateTime.now();
        news.setCreateDate(now);
        news.setLastUpdateDate(now);

        News added = newsRepository.create(news)
                .orElseThrow(NewsCreationException::new);

        return findById(added.getId());
    }

    @Override
    public NewsDtoResponse update(NewsDtoResponse object) {

        validate(object);

        checkAuthorExist(object);

        News news = newsRepository.findById(object.getId())
                .orElseThrow(NewsNotFoundException::new);

        news.setTitle(object.getTitle());
        news.setContent(object.getContent());
        news.setLastUpdateDate(LocalDateTime.now());
        news.setAuthorId(object.getAuthorId());

        News updated = newsRepository.update(news)
                .orElseThrow(NewsUpdateException::new);

        return findById(updated.getId());
    }

    @Override
    public boolean remove(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(NewsNotFoundException::new);

        return newsRepository.remove(news);
    }

    private void validate(NewsDtoResponse dto) {

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<NewsDtoResponse>> violations = validator.validate(dto);
            Optional<ConstraintViolation<NewsDtoResponse>> any = violations.stream().findAny();
            if (any.isPresent()) {
                throw new NewsDtoValidationException(
                        any.get().getPropertyPath().toString(),
                        any.get().getMessage(),
                        any.get().getInvalidValue().toString());
            }
        }
    }

    private void checkAuthorExist(NewsDtoResponse object) {
        Optional<Author> author = authorRepository.findById(object.getAuthorId());
        if (author.isEmpty()) {
            throw new AuthorNotFoundException();
        }
    }

    private void dtoIdMustBeNull(NewsDtoResponse object) {
        if (object.getId() != null) {
            throw new NewsIdMustBeNullException();
        }
    }
}
