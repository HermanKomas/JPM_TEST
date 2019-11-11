package com.jpm.trading;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.jpm.trading.domain.DailyReportDto;
import com.jpm.trading.domain.EntityReportDto;
import com.jpm.trading.domain.SettlementDto;
import com.jpm.trading.extract.marshalling.SettelmentObjectMapper;
import com.jpm.trading.transform.Transformer;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ServiceTest {
    SettelmentObjectMapper mapper = new SettelmentObjectMapper();
    Transformer transformer = new Transformer();
    StubUtils stubUtils = new StubUtils();
    TestUtils testUtils = new TestUtils();

    @Test
    public void shouldGiveValidEntityReport() {
        JSONObject jsonSource = stubUtils.getStub("instructions_stub_entity.json");

        List<SettlementDto> settlementDtos =
                mapper.convertInstructionsToSettlementDtoList(jsonSource);

        List<EntityReportDto> entityReportDtoList =
                transformer.settlementListToEntityReportList(settlementDtos);

        assertTrue(entityReportDtoList.size() == 2);

        assertEquals("bar", entityReportDtoList.get(0).getEntityName());
        assertTrue(entityReportDtoList.get(0).getTotalIncoming() == 4411.0);
        assertTrue(entityReportDtoList.get(0).getTotalOutgoing() == 0.0);

        assertEquals("foo", entityReportDtoList.get(1).getEntityName());
        assertTrue(entityReportDtoList.get(1).getTotalIncoming() == 2187.64);
        assertTrue(entityReportDtoList.get(1).getTotalOutgoing() == 7649.08);
    }

    @Test
    public void shouldGiveValidDailyReport() {
        JSONObject jsonSource = stubUtils.getStub("instructions_stub_daily.json");

        List<SettlementDto> settlementDtos =
                mapper.convertInstructionsToSettlementDtoList(jsonSource);

        List<DailyReportDto> dailyReportDtoList =
                transformer.settlementListToDailyReportList(settlementDtos);

        assertTrue(dailyReportDtoList.size() == 3);

        assertEquals(
                testUtils.parseDate("13 Nov 2019").toString(),
                dailyReportDtoList.get(0).getReportDay().toString()
        );
        assertTrue(dailyReportDtoList.get(0).getTotalOutgoing() == 4050.73);
        assertTrue(dailyReportDtoList.get(0).getTotalIncoming() == 11760.33);

        assertEquals(
                testUtils.parseDate("07 Nov 2019").toString(),
                dailyReportDtoList.get(1).getReportDay().toString()
        );
        assertTrue(dailyReportDtoList.get(1).getTotalOutgoing() == 40631.32);
        assertTrue(dailyReportDtoList.get(1).getTotalIncoming() == 4411.0);

        assertEquals(
                testUtils.parseDate("04 Nov 2019").toString(),
                dailyReportDtoList.get(2).getReportDay().toString()
        );
        assertTrue(dailyReportDtoList.get(2).getTotalOutgoing() == 122156.31);
        assertTrue(dailyReportDtoList.get(2).getTotalIncoming() == 2187.64);
    }

    @Test
    public void shouldGiveValidDailyReportWhenWeekend() {
        JSONObject jsonSource = stubUtils.getStub("instructions_stub_daily_weekend.json");

        List<SettlementDto> settlementDtos =
                mapper.convertInstructionsToSettlementDtoList(jsonSource);

        List<DailyReportDto> dailyReportDtoList =
                transformer.settlementListToDailyReportList(settlementDtos);

        dailyReportDtoList.forEach(x -> out.println(
                x.getTotalOutgoing() + " " +
                        x.getTotalIncoming() + " " +
                        x.getReportDay() + " "
        ));

        assertTrue(dailyReportDtoList.size() == 2);

        assertEquals(
                testUtils.parseDate("03 Nov 2019").toString(),
                dailyReportDtoList.get(0).getReportDay().toString()
        );
        assertTrue(dailyReportDtoList.get(0).getTotalOutgoing() == 2127.82);
        assertTrue(dailyReportDtoList.get(0).getTotalIncoming() == 1446.36);

        assertEquals(
                testUtils.parseDate("04 Nov 2019").toString(),
                dailyReportDtoList.get(1).getReportDay().toString()
        );
        assertTrue(dailyReportDtoList.get(1).getTotalOutgoing() == 7649.08);
        assertTrue(dailyReportDtoList.get(1).getTotalIncoming() == 7010.25);
    }
}
