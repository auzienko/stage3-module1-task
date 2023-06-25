package com.mjc.school.view.console.utils;

import com.mjc.school.view.console.error.ErrorsDict;
import lombok.experimental.UtilityClass;

import java.util.Scanner;

@UtilityClass
public class InputUtil {

    public static String inputString(String message, Scanner reader) {
        System.out.println(message);
        if (reader.hasNext()) {
            return reader.nextLine();
        }
        return inputString(message, reader);
    }

    public static Long inputLong(String message, Scanner reader, ErrorsDict error) {
        try {
            System.out.println(message);
            if (reader.hasNext()) {
                return Long.parseLong(reader.nextLine());
            }
        } catch (NumberFormatException e) {
            error.printLn();
        }
        return inputLong(message, reader, error);
    }
}
