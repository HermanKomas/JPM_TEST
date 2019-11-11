package com.jpm.trading.domain;

import java.util.Date;

public class SettlementDto {

    private String entity;
    private BuySell buySell;
    private Double agreedFx;
    private String ccy;
    private Date instructionDate;
    private Date settelmentDate;
    private Integer units;
    private Double pricePerUnit;

    public SettlementDto(){}

    public SettlementDto(String entity, BuySell buySell, Double agreedFx, String ccy, Date instructionDate, Date settlmentDate, Integer units, Double pricePerUnit) {
        this.entity = entity;
        this.buySell = buySell;
        this.agreedFx = agreedFx;
        this.ccy = ccy;
        this.instructionDate = instructionDate;
        this.settelmentDate = settlmentDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public void setBuySell(BuySell buySell) {
        this.buySell = buySell;
    }

    public Double getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(Double agreedFx) {
        this.agreedFx = agreedFx;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Date getSettelmentDate() {
        return settelmentDate;
    }

    public void setSettelmentDate(Date settelmentDate) {
        this.settelmentDate = settelmentDate;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
