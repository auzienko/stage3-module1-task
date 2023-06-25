package com.mjc.school.service;

import com.mjc.school.repository.impl.AuthorDataSourceRepositoryImpl;
import com.mjc.school.repository.impl.NewsDataSourceRepositoryImpl;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
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
    private final AuthorDataSourceRepositoryImpl authorRepository;
    private final NewsDataSourceRepositoryImpl newsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public NewsService(AuthorDataSourceRepositoryImpl authorRepository, NewsDataSourceRepositoryImpl newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsDtoResponse> findAll() {

        return newsRepository.readAll()
                .stream()
                .map(e -> modelMapper.map(e, NewsDtoResponse.class))
                .toList();
    }

    @Override
    public NewsDtoResponse findById(Long id) {
        NewsModel news = newsRepository.readById(id)
                .orElseThrow(NewsNotFoundException::new);

        return modelMapper.map(news, NewsDtoResponse.class);
    }

    @Override
    public NewsDtoResponse create(NewsDtoResponse object) {

        validate(object);
        checkAuthorExist(object);
        dtoIdMustBeNull(object);

        NewsModel news = modelMapper.map(object, NewsModel.class);

        LocalDateTime now = LocalDateTime.now();
        news.setCreateDate(now);
        news.setLastUpdateDate(now);

        NewsModel added = newsRepository.create(news)
                .orElseThrow(NewsCreationException::new);

        return findById(added.getId());
    }

    @Override
    public NewsDtoResponse update(NewsDtoResponse object) {

        validate(object);

        checkAuthorExist(object);

        NewsModel news = newsRepository.readById(object.getId())
                .orElseThrow(NewsNotFoundException::new);

        news.setTitle(object.getTitle());
        news.setContent(object.getContent());
        news.setLastUpdateDate(LocalDateTime.now());
        news.setAuthorId(object.getAuthorId());

        NewsModel updated = newsRepository.update(news)
                .orElseThrow(NewsUpdateException::new);

        return findById(updated.getId());
    }

    @Override
    public boolean remove(Long id) {
        NewsModel news = newsRepository.readById(id)
                .orElseThrow(NewsNotFoundException::new);

        return newsRepository.delete(news);
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
        Optional<AuthorModel> author = authorRepository.readById(object.getAuthorId());
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
