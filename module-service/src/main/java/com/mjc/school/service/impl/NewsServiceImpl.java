package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.repository.impl.NewsRepositoryImpl;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.Service;
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

public class NewsServiceImpl implements Service<NewsDtoResponse> {
    private final AuthorRepositoryImpl authorRepository;
    private final NewsRepositoryImpl newsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private final Validator validator;

    public NewsServiceImpl(AuthorRepositoryImpl authorRepository, NewsRepositoryImpl newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<NewsDtoResponse> readAll() {

        return newsRepository.readAll()
                .stream()
                .map(e -> modelMapper.map(e, NewsDtoResponse.class))
                .toList();
    }

    @Override
    public NewsDtoResponse readBy(Long id) {
        NewsModel news = newsRepository.readById(id);
        if (news.getId() == null) {
            throw new NewsNotFoundException();
        }

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

        NewsModel added = newsRepository.create(news);
        if (added.getId() == null) {
            throw new NewsCreationException();
        }
        return readBy(added.getId());
    }

    @Override
    public NewsDtoResponse update(NewsDtoResponse object) {

        validate(object);

        checkAuthorExist(object);

        NewsModel news = newsRepository.readById(object.getId());
        if (news.getId() == null) {
            throw new NewsNotFoundException();
        }

        news.setTitle(object.getTitle());
        news.setContent(object.getContent());
        news.setLastUpdateDate(LocalDateTime.now());
        news.setAuthorId(object.getAuthorId());

        NewsModel updated = newsRepository.update(news);
        if (updated.getId() == null) {
            throw new NewsUpdateException();
        }

        return readBy(updated.getId());
    }

    @Override
    public Boolean delete(Long id) {
        NewsModel news = newsRepository.readById(id);
        if (news.getId() == null) {
            throw new NewsNotFoundException();
        }

        return newsRepository.delete(id);
    }

    private void validate(NewsDtoResponse dto) {
        Set<ConstraintViolation<NewsDtoResponse>> violations = validator.validate(dto);
        Optional<ConstraintViolation<NewsDtoResponse>> any = violations.stream().findAny();
        if (any.isPresent()) {
            throw new NewsDtoValidationException(
                    any.get().getPropertyPath().toString(),
                    any.get().getMessage(),
                    any.get().getInvalidValue().toString());
        }
    }

    private void checkAuthorExist(NewsDtoResponse object) {
        AuthorModel author = authorRepository.readById(object.getAuthorId());
        if (author.getId() == null) {
            throw new AuthorNotFoundException();
        }
    }

    private void dtoIdMustBeNull(NewsDtoResponse object) {
        if (object.getId() != null) {
            throw new NewsIdMustBeNullException();
        }
    }
}
