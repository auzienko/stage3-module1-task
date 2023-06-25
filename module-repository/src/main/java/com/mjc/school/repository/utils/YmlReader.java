package com.mjc.school.repository.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mjc.school.repository.exception.YamlReadException;
import com.mjc.school.repository.model.BaseEntity;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.List;

@UtilityClass
public final class YmlReader {
    public static <T extends BaseEntity> List<T> getData(String fileName, Class<T> type) {
        ClassLoader classLoader = YmlReader.class.getClassLoader();
        ObjectMapper mapper = new YAMLMapper();
        mapper.registerModule(new JavaTimeModule());
        try (InputStream resourceAsStream = classLoader.getResourceAsStream(fileName)){
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(List.class, type);
            return mapper.readValue(resourceAsStream, javaType);
        } catch (Exception e) {
            throw new YamlReadException(e);
        }
    }
}
