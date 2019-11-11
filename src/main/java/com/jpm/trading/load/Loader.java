package com.jpm.trading.load;

import com.jpm.trading.domain.DailyReportDto;
import com.jpm.trading.domain.EntityReportDto;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Loader {

    private static final DecimalFormat numberFormat = new DecimalFormat("#.00");

    private void printTotalForBusinessDay(DailyReportDto dailyReportDto) {
        out.println(
                dailyReportDto.getReportDay()
                        + "\nTOTAL INCOMING: " +
                        numberFormat.format(dailyReportDto.getTotalIncoming()) + " USD"
                        + "\nTOTAL OUTGOING: " +
                        numberFormat.format(dailyReportDto.getTotalOutgoing()) + " USD"
                        + "\n"
        );
    }

    private void printTotalAmounts(List<DailyReportDto> dailyReportDtoList) {
        dailyReportDtoList
                .forEach(x -> printTotalForBusinessDay(x));
    }

    private void printIncomingRanking(List<EntityReportDto> entityReportList) {
        out.println("\nINCOMING RANKING");
        entityReportList.forEach(x ->
                out.println(x.getEntityName()
                        + ": "
                        + numberFormat.format(x.getTotalIncoming()) + " USD"
                )
        );
    }

    private void printOutgoingRanking(List<EntityReportDto> entityReportList) {
        out.println("\nOUTGOING RANKING");
        entityReportList.forEach(x ->
                out.println(x.getEntityName()
                        + ": "
                        + numberFormat.format(x.getTotalOutgoing()) + " USD"
                )
        );
    }

    private void printAllRankings(List<EntityReportDto> entityReportDtoList) {
        List<EntityReportDto> sortedByIncoming = entityReportDtoList.stream()
                .sorted(Comparator.comparing(EntityReportDto::getTotalIncoming).reversed())
                .collect(Collectors.toList());

        List<EntityReportDto> sortedByOutgoing = entityReportDtoList.stream()
                .sorted(Comparator.comparing(EntityReportDto::getTotalOutgoing).reversed())
                .collect(Collectors.toList());

        printIncomingRanking(sortedByIncoming);

        printOutgoingRanking(sortedByOutgoing);
    }

    public void printReportsToTheConsole(List<DailyReportDto> dailyReportDtoList,
                                         List<EntityReportDto> entityReportDtoList) {
        out.println("#### INTERNATIONAL MARKET SETTLEMENT REPORTS ####\n");

        printTotalAmounts(dailyReportDtoList);

        printAllRankings(entityReportDtoList);

        out.println("#################################################");
    }
}
