package com.gitlab.kevinnowak;

class MessageHandler {
    static final String BANNER =
            """
                      
                      _______                  _ _           _
                     |__   __|                (_) |         | |
                        | | ___ _ __ _ __ ___  _| |__   ___ | |
                        | |/ _ \\ '__| '_ ` _ \\| | '_ \\ / _ \\| |
                        | |  __/ |  | | | | | | | |_) | (_) | |
                        |_|\\___|_|  |_| |_| |_|_|_.__/ \\___/|_|
                    """;
    static final String USER_INPUT_PROMPT =
            """
                    1) %s
                    2) %s
                    3) %s
                    4) %s
                                    
                    Which league's standings would you like to see:\s"""
                    .formatted(League.PREMIER_LEAGUE, League.BUNDESLIGA, League.LA_LIGA, League.LIGUE_1);
    static final String INVALID_INPUT_MESSAGE =
            """
                                    
                    Your input "%d" is invalid! Please try again.
                    """;
    static final String NOT_A_NUMBER_MESSAGE =
            """
                                        
                    Your input "%s" is not a number! Please enter a number.
                    """;
    static final String SEPARATION_LINE =
            "-".repeat(100);
    static final String FORMATTED_WIDTH_TITLE =
            "| %-8s |            %s            | %-6s | %-3s | %-4s | %-4s |  %s  |  %s  |  %s  | %-6s |%n";
    static final String FORMATTED_WIDTH_ROW =
            "| %-8s | %-26s | %6s | %3s | %4s | %4s | %4s | %4s | %4s | %6s |%n";
    static final String NO_RESPONSE_BODY_MESSAGE =
            """
                    Something went wrong with generating the response body!
                    """;
    static final String MAPPING_ISSUE_MESSAGE =
            """
                    Something went wrong with mapping to the table DTO!""";

    private MessageHandler() {
    }

    static String formatTableDTO(TableDTO tableDTO) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(SEPARATION_LINE).append("\n");
        sb.append(String.format(FORMATTED_WIDTH_TITLE,
                "Position", "Team", "Played", "Won", "Draw", "Lost", "GF", "GA", "GD", "Points"));
        sb.append(SEPARATION_LINE).append("\n");

        for (TableRowDTO row : tableDTO.tableRows()) {
            sb.append(String.format(
                    FORMATTED_WIDTH_ROW,
                    row.position(), row.teamName(), row.played(), row.won(), row.draw(), row.lost(), row.goalsFor(),
                    row.goalsAgainst(), row.goalDifference(), row.points()
            ));
        }

        sb.append(SEPARATION_LINE).append("\n");

        return sb.toString();
    }
}
