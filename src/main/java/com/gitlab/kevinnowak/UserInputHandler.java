package com.gitlab.kevinnowak;

import java.util.Scanner;

class UserInputHandler {
    private final Scanner scanner;

    UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    String read() {
        return scanner.next();
    }
}
