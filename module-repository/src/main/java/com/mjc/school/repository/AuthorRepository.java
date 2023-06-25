package com.mjc.school.repository;

import com.mjc.school.repository.model.Author;

public class AuthorRepository extends AbstractRepository<Author> {
    public AuthorRepository(String fileName, Class<Author> type) {
        super(fileName, type);
    }

    @Override
    protected Author objectClone(Author object) {
        return new Author(object);
    }
}
