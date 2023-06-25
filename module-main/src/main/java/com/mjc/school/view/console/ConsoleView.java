package com.mjc.school.view.console;

import com.mjc.school.controller.Controller;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.view.View;
import com.mjc.school.view.console.error.ErrorsDict;
import com.mjc.school.view.console.operation.OperationList;
import com.mjc.school.view.console.utils.InputUtil;

import java.util.Scanner;

public class ConsoleView implements View {

    private static final String TITLE = "Enter the number of operation:\n";
    private static final String TEMPLATE = "%d - %s\n";
    private static final String EXIT = "0 - Exit.";

    private final String menu;

    private static final String OPERATION_NOT_FOUND = "Command not found.";

    private final Controller<NewsDtoResponse> controller;

    public ConsoleView(Controller<NewsDtoResponse> controller) {
        this.controller = controller;
        menu = makeMenu();
    }

    public void show() {
        try (Scanner reader = new Scanner(System.in)) {
            long userChoice;
            do {
                userChoice = InputUtil.inputLong(menu, reader, ErrorsDict.OPERATION_NOT_FOUND);
                runOperation(userChoice, reader);
            } while (userChoice != 0);
        }
    }

    private String makeMenu() {
        StringBuilder sb = new StringBuilder(TITLE);
        for (OperationList o : OperationList.values()) {
            sb.append(TEMPLATE.formatted(o.getId(), o.getDescription()));
        }
        sb.append(EXIT);
        return sb.toString();
    }

    private void runOperation(long userChoice, Scanner reader) {
        if (userChoice == 0) {
            return;
        }
        try {
            OperationList.findById(userChoice).doIt(controller, reader);
        } catch (Exception e) {
            System.out.println(OPERATION_NOT_FOUND);
        }
    }
}
