package com.jpm.trading.transform;

import com.jpm.trading.domain.*;
import com.jpm.trading.extract.marshalling.CurrencyUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Transformer {

    private Double getSettledAmount(SettlementDto settlementDto) {
        Double settledAmount = settlementDto.getPricePerUnit()
                * settlementDto.getUnits()
                * settlementDto.getAgreedFx();

        CurrencyUtil currencyUtil = new CurrencyUtil();

        return currencyUtil
                .convertToUsd(settledAmount, settlementDto.getCcy());
    }

    private Double getTotalSettledAmount(List<SettlementDto> settlementDtoList, BuySell buySell) {
        List<SettlementDto> filtered =
                settlementDtoList.stream()
                        .filter(x -> x.getBuySell() == buySell)
                        .collect(Collectors.toList());

        return filtered.stream()
                .map(x -> getSettledAmount(x))
                .reduce(0.0, (a, b) -> a + b);
    }

    private EntityReportDto generateEntityReport(String entityName, List<SettlementDto> settlementDtoList) {
        return new EntityReportDto(
                entityName,
                getTotalSettledAmount(settlementDtoList, BuySell.SELL),
                getTotalSettledAmount(settlementDtoList, BuySell.BUY)
        );
    }

    private DailyReportDto generateDailyReport(Date reportDate, List<SettlementDto> settlementDtoList) {
        return new DailyReportDto(
                reportDate,
                settlementDtoList,
                getTotalSettledAmount(settlementDtoList, BuySell.SELL),
                getTotalSettledAmount(settlementDtoList, BuySell.BUY)
        );
    }

    public List<EntityReportDto> settlementListToEntityReportList(List<SettlementDto> settlementDtoList) {
        Map<String, List<SettlementDto>> settlmentsByEntityName =
                settlementDtoList.stream()
                        .collect(Collectors.groupingBy(
                                SettlementDto::getEntity
                        ));

        return settlmentsByEntityName.entrySet().stream()
                .map(entry -> generateEntityReport(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<DailyReportDto> settlementListToDailyReportList(List<SettlementDto> settlementDtoList) {

        Map<Date, List<SettlementDto>> settlmentsByBusinessDay =
                settlementDtoList.stream()
                        .collect(Collectors.groupingBy(
                                SettlementDto::getSettelmentDate
                        ));

        return settlmentsByBusinessDay.entrySet().stream()
                .map(entry -> generateDailyReport(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
