package com.jpm.trading;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.System.out;

public class StubUtils {

    public JSONObject getStub(String fileName){
        JSONObject object = null;

        try{
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(fileName);

            JSONParser jsonParser = new JSONParser();

            object = (JSONObject) jsonParser.parse(
                    new InputStreamReader(inputStream, "UTF-8"));
        } catch(ParseException | IOException ex){
            out.println(ex.getMessage());
        }

        return object;
    }
}
