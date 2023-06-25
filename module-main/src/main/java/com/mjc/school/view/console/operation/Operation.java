package com.mjc.school.view.console.operation;

import com.mjc.school.controller.Controller;

import java.util.Scanner;

public interface Operation<T> {
    void doIt(Controller<T> controller, Scanner reader);
}
