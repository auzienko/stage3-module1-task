package com.mjc.school.repository.datasourse;

import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.utils.YmlReader;
import lombok.Getter;

import java.util.List;

@Getter
public class DataSource<T extends BaseEntity> {
    private final List<T> content;

    public DataSource(String fileName, Class<T> clazz) {
        content = contentInit(fileName, clazz);
    }

    private List<T> contentInit(String fileName, Class<T> clazz) {
        return YmlReader.getData(fileName, clazz);
    }
}
