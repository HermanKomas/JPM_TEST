package com.jpm.trading.extract.marshalling;

import com.jpm.trading.domain.BuySell;
import com.jpm.trading.domain.SettlementDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

import static java.lang.System.out;

public class SettelmentObjectMapper {

    private Boolean isSGDOrAEDCurrency(String ccy){
        return ccy.equals("SGD") || ccy.equals("AED");
    }

    private Date setWeekDayToNextBusinessDay(Date date, String ccy) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(isSGDOrAEDCurrency(ccy)){
            if(cal.get(Calendar.DAY_OF_WEEK) == 6) {
                cal.add(Calendar.DATE, 2);
            } else {
                cal.add(Calendar.DATE, 1);
            }
        } else {
            if(cal.get(Calendar.DAY_OF_WEEK) == 7) {
                cal.add(Calendar.DATE, 2);
            } else {
                cal.add(Calendar.DATE, 1);
            }
        }

        return cal.getTime();
    }

    private Boolean isValidBusinessDayOfTheWeek(Date date, String ccy) {
        SimpleDateFormat formatter = new SimpleDateFormat("E");
        String dayOfTheWeek = formatter.format(date);

        if (dayOfTheWeek.equals("Sat") || dayOfTheWeek.equals("Sun")) {
            if (!isSGDOrAEDCurrency(ccy)) {
                return false;
            }
        } else if (dayOfTheWeek.equals("Fri") || dayOfTheWeek.equals("Sat")) {
            if (isSGDOrAEDCurrency(ccy)) {
                return false;
            }
        }

        return true;
    }

    private SettlementDto mapInstructionToSettlementDto(JSONObject instruction) throws MappingException {
        String entityName = instruction.get("entity").toString();

        BuySell buySell = null;

        String buySellOption = instruction.get("buy_sell").toString();
        if (buySellOption.equals("B")) {
            buySell = BuySell.BUY;
        } else if (buySellOption.equals("S")) {
            buySell = BuySell.SELL;
        } else {
            throw new MappingException("Invalid BuySell option value for entity: " + entityName);
        }

        String ccy = instruction.get("currency").toString();

        CurrencyUtil currencyUtil = new CurrencyUtil();
        if (!currencyUtil.isValidCurrencyCode(ccy)) {
            throw new MappingException("Invalid Currency Code for entity: " + entityName);
        }

        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        Date instructionDate = null;
        try {
            Date sourceInstructionDate =
                    format.parse(instruction.get("instruction_date").toString());

            instructionDate = isValidBusinessDayOfTheWeek(sourceInstructionDate, ccy)
                    ? sourceInstructionDate
                    : setWeekDayToNextBusinessDay(sourceInstructionDate, ccy);
        } catch (ParseException ex) {
            throw new MappingException("Invalid InstructionDate value for entity: " + entityName);
        }

        Date settlementDate = null;
        try {
            Date sourceSettlementDate =
                    format.parse(instruction.get("settlement_date").toString());

            settlementDate = isValidBusinessDayOfTheWeek(sourceSettlementDate, ccy)
                    ? sourceSettlementDate
                    : setWeekDayToNextBusinessDay(sourceSettlementDate, ccy);
        } catch (ParseException ex) {
            throw new MappingException("Invalid SettlementDate value for entity: " + entityName);
        }

        return new SettlementDto(
                entityName,
                buySell,
                Double.parseDouble(instruction.get("agreed_fx").toString()),
                instruction.get("currency").toString(),
                instructionDate,
                settlementDate,
                Integer.parseInt(instruction.get("units").toString()),
                Double.parseDouble(instruction.get("price_per_unit").toString())
        );
    }

    public List<SettlementDto> convertInstructionsToSettlementDtoList(JSONObject instructions) {

        List<SettlementDto> settlementDtoList = new ArrayList<SettlementDto>();

        JSONArray data = (JSONArray) instructions.get("data");
        Iterator<JSONObject> iterator = data.iterator();

        while (iterator.hasNext()) {
            try {
                SettlementDto settlementDto = mapInstructionToSettlementDto(iterator.next());

                settlementDtoList.add(settlementDto);
            } catch (MappingException ex) {
                out.println(ex.getMessage());
            }
        }

        return settlementDtoList;
    }
}
