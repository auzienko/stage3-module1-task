package com.mjc.school.view.console.operation;

import com.mjc.school.controller.Controller;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.Scanner;

public class GetAllOperation implements Operation<NewsDtoResponse> {

    @Override
    public void doIt(Controller<NewsDtoResponse> controller, Scanner reader) {
        controller.getAll().forEach(System.out::println);
    }
}
