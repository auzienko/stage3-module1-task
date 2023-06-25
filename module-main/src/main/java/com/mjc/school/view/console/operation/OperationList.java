package com.mjc.school.view.console.operation;

import com.mjc.school.service.dto.NewsDtoResponse;
import lombok.Getter;

@Getter
public enum OperationList {
    GET_ALL(1, "Get all news.", new GetAllOperation()),
    GET_BY_ID(2, "Get news by id.", new GetByIdOperation()),
    CREATE(3, "Create news.", new CreateOperation()),
    UPDATE(4, "Update news.", new UpdateOperation()),
    REMOVE_BY_ID(5, "Remove news by id.", new RemoveByIdOperation()),
    ;

    private final long id;
    private final String description;
    private final Operation<NewsDtoResponse> operation;

    OperationList(long id, String description, Operation<NewsDtoResponse> operation) {
        this.id = id;
        this.description = description;
        this.operation = operation;
    }

    public static Operation<NewsDtoResponse> findById(long id) {
        for (OperationList o : OperationList.values()) {
            if (o.getId() == id) {
                return o.getOperation();
            }
        }
        throw new UnsupportedOperationException();
    }
}