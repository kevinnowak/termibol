package com.gitlab.kevinnowak;

enum League {
    NONE("None", null),
    PREMIER_LEAGUE("Premier League", "ENG"),
    BUNDESLIGA("Bundesliga", "DEU"),
    LA_LIGA("La Liga", "ESP"),
    LIGUE_1("Ligue 1", "FRA");

    private final String name;
    private final String code;

    League(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getCode() { return code; }

    @Override
    public String toString() {
        return name;
    }
}
