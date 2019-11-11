package com.jpm.trading.extract;

import com.jpm.trading.domain.SettlementDto;
import com.jpm.trading.extract.marshalling.SettelmentObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Extractor {

    private static final String INSTRUCTIONS_SOURCE_PATH = "instructions.json";

    private JSONObject getInstructionsJsonFromFile() throws IOException, ParseException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(INSTRUCTIONS_SOURCE_PATH);
        JSONParser jsonParser = new JSONParser();

        return (JSONObject) jsonParser.parse(
                new InputStreamReader(inputStream, "UTF-8"));
    }

    public List<SettlementDto> getSettlementsFromInstructions() {
        try {
            JSONObject instructions = getInstructionsJsonFromFile();

            SettelmentObjectMapper objectMapper = new SettelmentObjectMapper();

            return objectMapper
                    .convertInstructionsToSettlementDtoList(instructions);
        } catch (IOException | ParseException ex) {
            out.println(ex.getMessage());

            return new ArrayList<>();
        }
    }
}
