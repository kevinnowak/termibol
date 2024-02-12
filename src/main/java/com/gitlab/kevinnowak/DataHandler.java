package com.gitlab.kevinnowak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class DataHandler {

    private DataHandler() {
    }

    static String callApiForStanding(League selectedLeague)
            throws MappingException, NoLeagueException, ResponseBodyException {
        String responseBody;
        TableDTO tableDTO;
        responseBody = getResponseBody(selectedLeague);
        tableDTO = mapToDTO(responseBody);

        return MessageHandler.formatTableDTO(tableDTO);
    }

    static TableDTO mapToDTO(String responseBody) throws MappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TableDTO tableDTO;

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode tableNode = rootNode.path("standings").get(0).path("table");

            tableDTO = new TableDTO(rootNode.path("area").path("code").asText(), new ArrayList<>());

            for (JsonNode tableRowNode : tableNode) {
                TableRowDTO tableRowDTO = new TableRowDTO(
                        tableRowNode.path("position").asInt(),
                        tableRowNode.path("team").path("shortName").asText(),
                        tableRowNode.path("playedGames").asInt(),
                        tableRowNode.path("won").asInt(),
                        tableRowNode.path("draw").asInt(),
                        tableRowNode.path("lost").asInt(),
                        tableRowNode.path("goalsFor").asInt(),
                        tableRowNode.path("goalsAgainst").asInt(),
                        tableRowNode.path("goalDifference").asInt(),
                        tableRowNode.path("points").asInt()
                );

                tableDTO.tableRows().add(tableRowDTO);
            }

        } catch (JsonProcessingException e) {
            throw new MappingException(MessageHandler.MAPPING_ISSUE_MESSAGE);
        }

        return tableDTO;
    }

    static String getResponseBody(League selectedLeague) throws ResponseBodyException, NoLeagueException {
        Configuration configuration = new Configuration();
        String token = configuration.getProperty("api.token");
        StringBuilder sb = new StringBuilder(configuration.getProperty("api.url"));

        switch (selectedLeague) {
            case LA_LIGA -> sb.append("/PD");
            case LIGUE_1 -> sb.append("/FL1");
            case BUNDESLIGA -> sb.append("/BL1");
            case PREMIER_LEAGUE -> sb.append("/PL");
            case NONE -> throw new NoLeagueException(MessageHandler.INVALID_INPUT_MESSAGE);
        }

        sb.append("/standings");

        try {
            URL url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Auth-Token", token);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                return response.toString();

            }
        } catch (IOException e) {
            throw new ResponseBodyException(MessageHandler.NO_RESPONSE_BODY_MESSAGE);
        }

        return null;
    }
}
