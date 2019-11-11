package com.jpm.trading.extract.marshalling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import static java.lang.System.out;

public class CurrencyUtil {
    private JSONArray getCcyList() throws IOException, ParseException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ccy_fx.json");
        JSONParser jsonParser = new JSONParser();

        JSONObject ccyResource = (JSONObject) jsonParser.parse(
                new InputStreamReader(inputStream, "UTF-8"));

        return (JSONArray) ccyResource.get("rates");
    }

    private Double getFxRateForCcy(String ccy) {
        Double fxRate = null;

        try {
            Iterator<JSONObject> iterator = getCcyList().iterator();

            while (iterator.hasNext()) {
                JSONObject ccyObject = iterator.next();

                if (ccyObject.containsKey(ccy)) {
                    fxRate = Double.parseDouble(ccyObject.get(ccy).toString());
                }
            }
        } catch (IOException | ParseException ex) {
            out.println(ex.getMessage());
        }

        return fxRate;
    }

    private Double roundAmount(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public Double convertToUsd(Double amount, String ccy) {
        if (ccy.equals("USD")) {
            return roundAmount(amount, 2);
        } else {
            return roundAmount(amount * getFxRateForCcy(ccy), 2);
        }
    }

    public Boolean isValidCurrencyCode(String ccy) {
        try {
            Iterator<JSONObject> iterator = getCcyList().iterator();

            while (iterator.hasNext()) {
                JSONObject ccyObject = iterator.next();

                if (ccyObject.containsKey(ccy)) {
                    return true;
                }
            }
        } catch (IOException | ParseException ex) {
            out.println(ex.getMessage());
        }

        return false;
    }
}
