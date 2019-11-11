package com.jpm.trading;

import com.jpm.trading.domain.DailyReportDto;
import com.jpm.trading.domain.EntityReportDto;
import com.jpm.trading.domain.SettlementDto;
import com.jpm.trading.extract.Extractor;
import com.jpm.trading.load.Loader;
import com.jpm.trading.transform.Transformer;

import java.util.List;

import static java.lang.System.out;

/**
 * The purpose of this micro ETL system is to load list of settlement instructions
 * from the static file and transform in a list of daily reports using predefined
 * calculation formulas and display in the console.
 * <p>
 * NOTE: In addition a list of currency codes and FX rates are located under resources/ccy_fx.json
 * in order to validate that provided CCY codes are valid and to source the FX rate.
 * <p>
 * NOTE: Dependency Injection practice is omitted in this implementation due to simplicity of the
 * system however could be easily introduced in case of additional business requirements
 */
public class Service {
    private static Extractor extractorClient = new Extractor();
    private static Transformer transformerClient = new Transformer();
    private static Loader loaderClient = new Loader();

    public static void main(String[] args) {
        List<SettlementDto> settlements = extractorClient.getSettlementsFromInstructions();

        if (!settlements.isEmpty()) {
            List<EntityReportDto> entityReport = transformerClient.settlementListToEntityReportList(settlements);
            List<DailyReportDto> dailyReport = transformerClient.settlementListToDailyReportList(settlements);

            loaderClient.printReportsToTheConsole(dailyReport, entityReport);
        } else {
            out.println("Settlements List is empty or there was an error during Extract process, " +
                    "please see the log for more details");
        }
    }
}
