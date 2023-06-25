package com.mjc.school.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class NewsDtoResponse {
    private Long id;

    @NotEmpty
    @Size(max = 30, min = 5, message = "News title can not be less than 5 and more than 30 symbols")
    private String title;

    @NotEmpty
    @Size(max = 255, min = 5, message = "News content can not be less than 5 and more than 255 symbols")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @NotNull
    @Min(value = 1L, message = "Author id can not be null or less than 1")
    private Long authorId;
}
