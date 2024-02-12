package com.gitlab.kevinnowak;

import java.util.NoSuchElementException;

class Termibol {
    private final UserInputHandler userInputHandler;
    private League selectedLeague;
    private int userInput;

    Termibol() {
        this.userInputHandler = new UserInputHandler();
        this.selectedLeague = League.NONE;
    }

    int start() {
        printBanner();

        do {
            try {
                getUserInputViaPrompt();
            } catch (NoLeagueException | NoNumberInputException e) {
                System.err.println(e.getMessage());
            }
        } while (this.selectedLeague == League.NONE);

        String table;

        try {
            table = DataHandler.callApiForStanding(selectedLeague);
        } catch (MappingException | NoLeagueException | ResponseBodyException e) {
            System.err.println(e.getMessage());
            return 1;
        }

        System.out.println(table);

        return 0;
    }

    void getUserInputViaPrompt() throws NoLeagueException, NoNumberInputException {
        printUserInputPrompt();
        this.userInput = readUserInputAndSetSelectedLeague();

        if (this.selectedLeague == League.NONE) {
            throw new NoLeagueException(String.format(MessageHandler.INVALID_INPUT_MESSAGE, this.userInput));
        }
    }

    int readUserInputAndSetSelectedLeague() throws NoNumberInputException {
        String tmp = " ";

        try {
            tmp = userInputHandler.read();
            this.userInput = Integer.parseInt(tmp);
        } catch (NumberFormatException | NoSuchElementException e) {
            throw new NoNumberInputException(String.format(MessageHandler.NOT_A_NUMBER_MESSAGE, tmp));
        }

        this.selectedLeague = determineSelectedLeagueFromInt(this.userInput);

        return this.userInput;
    }

    League determineSelectedLeagueFromInt(int userInput) {
        return switch (userInput) {
            case 1 -> League.PREMIER_LEAGUE;
            case 2 -> League.BUNDESLIGA;
            case 3 -> League.LA_LIGA;
            case 4 -> League.LIGUE_1;
            default -> League.NONE;
        };
    }

    private void printBanner() {
        System.out.println(MessageHandler.BANNER);
    }

    private void printUserInputPrompt() {
        System.out.print(MessageHandler.USER_INPUT_PROMPT);
    }

    League getSelectedLeague() {
        return selectedLeague;
    }
}
