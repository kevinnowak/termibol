package com.gitlab.kevinnowak;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.crypto.Data;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataHandlerTest {

    private static Stream<Arguments> allLeagueTypes() {
        return Stream.of(
                Arguments.of(League.NONE),
                Arguments.of(League.PREMIER_LEAGUE),
                Arguments.of(League.BUNDESLIGA),
                Arguments.of(League.LA_LIGA),
                Arguments.of(League.LIGUE_1)
        );
    }

    @ParameterizedTest
    @MethodSource("allLeagueTypes")
    void givenLeagueType_whenRequestingResponseBodyForLeague_thenGetAppropriateResponseBody(League league) {
        // When
        try {
            // Then
            if (league == League.NONE) {
                assertThrows(NoLeagueException.class, () -> DataHandler.getResponseBody(league));
            } else {
                String responseBody = DataHandler.getResponseBody(league);
                TableDTO tableDTO = DataHandler.mapToDTO(responseBody);
                assertEquals(league.getCode(), tableDTO.code());
            }
        } catch (ResponseBodyException | MappingException | NoLeagueException e) {
            throw new RuntimeException(e);
        }
    }
}
