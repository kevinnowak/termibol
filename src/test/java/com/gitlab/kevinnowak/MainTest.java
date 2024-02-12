package com.gitlab.kevinnowak;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
//    void shouldPrintSomethingToOutputStreamWhenRunningMain() {
    void givenValidUserInput_whenRunningMain_thenPrintSomethingToOutputStream() {
        // Given
        InputStream originalIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream("1".getBytes());
        System.setIn(testIn);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        try {
            // When
            Main.main(null);

            // Then
            assertTrue(bos.size() > 0);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
