package dto;

import java.math.BigDecimal;

public class SaldoInsolutoDto {

    /**
     * saldoInsoluto
     */
    private BigDecimal saldoInsoluto = BigDecimal.ZERO;

    /**
     * capital
     */
    private BigDecimal capital = BigDecimal.ZERO;

    /**
     * interesMora
     */
    private BigDecimal interesMora = BigDecimal.ZERO;

    /**
     * gastosCobranza
     */
    private BigDecimal gastosCobranza = BigDecimal.ZERO;

    /**
     * interesesCorrientes
     */
    private BigDecimal interesesCorrientes = BigDecimal.ZERO;

    /**
     * seguro
     */
    private BigDecimal seguro = BigDecimal.ZERO;

    /**
     * cxcEstudioCredito
     */
    private BigDecimal cxcEstudioCredito = BigDecimal.ZERO;

    /**
     * cxcEstudioCredito
     */
    private BigDecimal cxcIntesesInciales = BigDecimal.ZERO;

    /**
     * cxcSeguro
     */
    private BigDecimal cxcSeguroIncial = BigDecimal.ZERO;

    /**
     * cxcFianza
     */
    private BigDecimal cxcFianza = BigDecimal.ZERO;

    /**
     * cxcInteresesFianza
     */
    private BigDecimal cxcInteresesFianza = BigDecimal.ZERO;

    /**
     * seguroPrimaAnticipada
     */
    private BigDecimal seguroPrimaAnticipada = BigDecimal.ZERO;

    /**
     * interesesSeguroPrimaAnt
     */
    private BigDecimal interesesSeguroPrimaAnt = BigDecimal.ZERO;

    public BigDecimal getSaldoInsoluto() {
        return saldoInsoluto;
    }

    public void setSaldoInsoluto(BigDecimal saldoInsoluto) {
        this.saldoInsoluto = saldoInsoluto;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInteresMora() {
        return interesMora;
    }

    public void setInteresMora(BigDecimal interesMora) {
        this.interesMora = interesMora;
    }

    public BigDecimal getGastosCobranza() {
        return gastosCobranza;
    }

    public void setGastosCobranza(BigDecimal gastosCobranza) {
        this.gastosCobranza = gastosCobranza;
    }

    public BigDecimal getInteresesCorrientes() {
        return interesesCorrientes;
    }

    public void setInteresesCorrientes(BigDecimal interesesCorrientes) {
        this.interesesCorrientes = interesesCorrientes;
    }

    public BigDecimal getSeguro() {
        return seguro;
    }

    public void setSeguro(BigDecimal seguro) {
        this.seguro = seguro;
    }

    public BigDecimal getCxcEstudioCredito() {
        return cxcEstudioCredito;
    }

    public void setCxcEstudioCredito(BigDecimal cxcEstudioCredito) {
        this.cxcEstudioCredito = cxcEstudioCredito;
    }

    public BigDecimal getCxcIntesesInciales() {
        return cxcIntesesInciales;
    }

    public void setCxcIntesesInciales(BigDecimal cxcIntesesInciales) {
        this.cxcIntesesInciales = cxcIntesesInciales;
    }

    public BigDecimal getCxcSeguroIncial() {
        return cxcSeguroIncial;
    }

    public void setCxcSeguroIncial(BigDecimal cxcSeguroIncial) {
        this.cxcSeguroIncial = cxcSeguroIncial;
    }

    public BigDecimal getCxcFianza() {
        return cxcFianza;
    }

    public void setCxcFianza(BigDecimal cxcFianza) {
        this.cxcFianza = cxcFianza;
    }

    public BigDecimal getCxcInteresesFianza() {
        return cxcInteresesFianza;
    }

    public void setCxcInteresesFianza(BigDecimal cxcInteresesFianza) {
        this.cxcInteresesFianza = cxcInteresesFianza;
    }

    public BigDecimal getSeguroPrimaAnticipada() {
        return seguroPrimaAnticipada;
    }

    public void setSeguroPrimaAnticipada(BigDecimal seguroPrimaAnticipada) {
        this.seguroPrimaAnticipada = seguroPrimaAnticipada;
    }

    public BigDecimal getInteresesSeguroPrimaAnt() {
        return interesesSeguroPrimaAnt;
    }

    public void setInteresesSeguroPrimaAnt(BigDecimal interesesSeguroPrimaAnt) {
        this.interesesSeguroPrimaAnt = interesesSeguroPrimaAnt;
    }

}
