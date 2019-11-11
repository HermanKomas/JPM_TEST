package com.jpm.trading.domain;

import java.util.Date;
import java.util.List;

public class DailyReportDto {
    private Date reportDay;
    private List<SettlementDto> settlementList;
    private Double totalIncoming;

    public DailyReportDto(){}

    public DailyReportDto(Date reportDay, List<SettlementDto> settlementList, Double totalIncoming, Double totalOutgoing) {
        this.reportDay = reportDay;
        this.settlementList = settlementList;
        this.totalIncoming = totalIncoming;
        this.totalOutgoing = totalOutgoing;
    }

    public Date getReportDay() {
        return reportDay;
    }

    public void setReportDay(Date reportDay) {
        this.reportDay = reportDay;
    }

    public List<SettlementDto> getSettlementList() {
        return settlementList;
    }

    public void setSettlementList(List<SettlementDto> settlementList) {
        this.settlementList = settlementList;
    }

    public Double getTotalIncoming() {
        return totalIncoming;
    }

    public void setTotalIncoming(Double totalIncoming) {
        this.totalIncoming = totalIncoming;
    }

    public Double getTotalOutgoing() {
        return totalOutgoing;
    }

    public void setTotalOutgoing(Double totalOutgoing) {
        this.totalOutgoing = totalOutgoing;
    }

    private Double totalOutgoing;
}
