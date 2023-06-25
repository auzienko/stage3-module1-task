package com.mjc.school.view.console.operation;

import com.mjc.school.controller.Controller;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NewsNotFoundException;
import com.mjc.school.view.console.error.ErrorsDict;

import java.util.Scanner;

import static com.mjc.school.view.console.utils.InputUtil.inputLong;

public class RemoveByIdOperation implements Operation<NewsDtoResponse> {

    private static final String STEP_1 = """
            Operation: Remove news by id.
            Enter news id:""";

    @Override
    public void doIt(Controller<NewsDtoResponse> controller, Scanner reader) {
        Long userChoice = null;
        try {
            userChoice = inputLong(STEP_1, reader, ErrorsDict.NEWS_ID_SHOULD_BE_NUMBER);

            System.out.println(controller.remove(userChoice));
        } catch (NewsNotFoundException e) {
            ErrorsDict.NEWS_WITH_ID_DOES_NOT_EXIST.printLn(userChoice);
        }
    }
}
