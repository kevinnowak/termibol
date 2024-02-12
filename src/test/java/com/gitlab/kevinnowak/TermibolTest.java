package com.gitlab.kevinnowak;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TermibolTest {

    private static Stream<Arguments> userInputAndCorrespondingLeagueProvider() {
        return Stream.of(
                Arguments.of("0", League.NONE),
                Arguments.of("1", League.PREMIER_LEAGUE),
                Arguments.of("2", League.BUNDESLIGA),
                Arguments.of("3", League.LA_LIGA),
                Arguments.of("4", League.LIGUE_1)
        );
    }

    @Test
    void givenValidUserInput_whenGameTerminatesWithoutError_thenReturnIntZero() {
        // Given
        String simulatedUserInput = "0\n1";
        InputStream originalIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        Termibol termibol = new Termibol();

        try {
            // When
            int result = termibol.start();

            // Then
            assertEquals(0, result);
        } finally {
            System.setIn(originalIn);
        }
    }

    @ParameterizedTest
    @MethodSource("userInputAndCorrespondingLeagueProvider")
    void givenValidUserInput_whenTryingToDetermineLeague_thenReturnCorrespondingLeague(
            String simulatedUserInput, League expectedLeague) {
        // Given
        Termibol termibol = new Termibol();

        // When
        League result = termibol.determineSelectedLeagueFromInt(Integer.parseInt(simulatedUserInput));

        // Then
        assertEquals(expectedLeague, result);
    }

    @ParameterizedTest
    @MethodSource("userInputAndCorrespondingLeagueProvider")
    void givenValidUserInput_whenTryingToDetermineLeague_thenSetLeagueCorrectly(
            String simulatedUserInput, League expectedLeague) throws NoNumberInputException {
        // Given
        InputStream originalIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        Termibol termibol = new Termibol();

        try {
            // When
            int userInput = termibol.readUserInputAndSetSelectedLeague();

            // Then
            assertEquals(expectedLeague, termibol.getSelectedLeague());
            assertEquals(userInput, Integer.parseInt(simulatedUserInput));
        } finally {
            System.setIn(originalIn);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "12", "39", "-4"})
    void givenInvalidUserInput_whenTryingToDetermineLeague_thenThrowNoLeagueException(String simulatedUserInput) {
        // Given
        InputStream originalIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        Termibol termibol = new Termibol();
        int simulatedUserInputInt = Integer.parseInt(simulatedUserInput);
        String expectedMessage = String.format(MessageHandler.INVALID_INPUT_MESSAGE, simulatedUserInputInt);

        try {
            // When
            Exception exception = assertThrows(NoLeagueException.class, termibol::getUserInputViaPrompt);
            // Then
            assertEquals(League.NONE, termibol.getSelectedLeague());
            assertEquals(expectedMessage, exception.getMessage());
        } finally {
            System.setIn(originalIn);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"f", "Q", ".", "<", "+", " "})
    void givenUserInputIsNotANumber_whenTryingToParseInputToInt_thenThrowNoNumberInputException(
            String simulatedUserInput) {
        // Given
        InputStream originalIn = System.in;
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        Termibol termibol = new Termibol();
        String expectedMessage = String.format(MessageHandler.NOT_A_NUMBER_MESSAGE, simulatedUserInput);

        try {
            // When
            Exception exception = assertThrows(NoNumberInputException.class, termibol::getUserInputViaPrompt);
            // Then
            assertEquals(League.NONE, termibol.getSelectedLeague());
            assertEquals(expectedMessage, exception.getMessage());
        } finally {
            System.setIn(originalIn);
        }
    }
}
