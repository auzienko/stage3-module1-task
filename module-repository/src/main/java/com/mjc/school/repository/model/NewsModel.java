package com.mjc.school.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class NewsModel extends BaseEntity{
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Long authorId;

    public NewsModel(NewsModel news) {
        setId(news.getId());
        setTitle(news.getTitle());
        setContent(news.getContent());
        setCreateDate(news.getCreateDate());
        setLastUpdateDate(news.getLastUpdateDate());
        setAuthorId(news.getAuthorId());
    }
}
