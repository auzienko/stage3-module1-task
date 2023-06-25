package com.mjc.school.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Author extends BaseEntity {
    private String name;

    public Author(Author author) {
        setId(author.getId());
        setName(author.getName());
    }
}
