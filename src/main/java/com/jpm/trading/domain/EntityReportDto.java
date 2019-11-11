package com.jpm.trading.domain;

public class EntityReportDto {
    private String entityName;
    private Double totalIncoming;
    private Double totalOutgoing;

    public EntityReportDto(){};

    public EntityReportDto(String entityName, Double totalIncoming, Double totalOutgoing) {
        this.entityName = entityName;
        this.totalIncoming = totalIncoming;
        this.totalOutgoing = totalOutgoing;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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
}
