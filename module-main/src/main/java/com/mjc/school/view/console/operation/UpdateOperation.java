package com.mjc.school.view.console.operation;

import com.mjc.school.controller.Controller;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.AuthorNotFoundException;
import com.mjc.school.service.exception.NewsDtoValidationException;
import com.mjc.school.view.console.error.ErrorsDict;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;
import static com.mjc.school.view.console.utils.InputUtil.inputString;

public class UpdateOperation implements Operation<NewsDtoResponse> {

    private static final String STEP_1 = """
            Operation: Update news.
            Enter news id:""";
    private static final String STEP_2 = "Enter news title:";
    private static final String STEP_3 = "Enter news content:";
    private static final String STEP_4 = "Enter author id:";

    @Override
    public void doIt(Controller<NewsDtoResponse> controller, Scanner reader) {
        NewsDtoResponse newsDto = new NewsDtoResponse();
        try {
            newsDto
                    .setId(inputLong(STEP_1, reader, ErrorsDict.NEWS_ID_SHOULD_BE_NUMBER))
                    .setTitle(inputString(STEP_2, reader))
                    .setContent(inputString(STEP_3, reader))
                    .setAuthorId(inputLong(STEP_4, reader, ErrorsDict.AUTHOR_ID_SHOULD_BE_NUMBER));

            System.out.println(controller.update(newsDto));

        } catch (AuthorNotFoundException e) {
            ErrorsDict.AUTHOR_ID_DOES_NOT_EXIST.printLn(newsDto.getAuthorId());
        } catch (NewsDtoValidationException e) {
            ErrorsDict.NEWS_DTO_VALIDATION.printLn(e.getMessage(), e.getField(), e.getValue());
        }
    }
}
