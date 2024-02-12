package com.gitlab.kevinnowak;

public record TableRowDTO(int position, String teamName, int played, int won, int draw, int lost, int goalsFor,
                          int goalsAgainst, int goalDifference, int points) {
}
