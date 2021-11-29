package dto;


public class MovimientoContableDto {
	
	private String numeroRadicado;
	
	private String tipoTransaccion;
	
	private String tipoMovimiento;
	
	private String cuenta;
	
	private String valor;
	
	private String nombreProcessoAcounting;
	
	private String nombreCuentaAcouting;
	
	private String mmensaje;
	
	/*Valores de planilla*/
	private Boolean vlrBoolean;
	
	private String estadoCredito;
	
	private String estadoIncor;
	
	
	public Boolean getVlrBoolean() {
		return vlrBoolean;
	}

	public void setVlrBoolean(Boolean vlrBoolean) {
		this.vlrBoolean = vlrBoolean;
	}

	public String getEstadoCredito() {
		return estadoCredito;
	}

	public void setEstadoCredito(String estadoCredito) {
		this.estadoCredito = estadoCredito;
	}

	public String getEstadoIncor() {
		return estadoIncor;
	}

	public void setEstadoIncor(String estadoIncor) {
		this.estadoIncor = estadoIncor;
	}


	public String getMmensaje() {
		return mmensaje;
	}

	public void setMmensaje(String mmensaje) {
		this.mmensaje = mmensaje;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getNombreProcessoAcounting() {
		return nombreProcessoAcounting;
	}

	public void setNombreProcessoAcounting(String nombreProcessoAcounting) {
		this.nombreProcessoAcounting = nombreProcessoAcounting;
	}

	public String getNombreCuentaAcouting() {
		return nombreCuentaAcouting;
	}

	public void setNombreCuentaAcouting(String nombreCuentaAcouting) {
		this.nombreCuentaAcouting = nombreCuentaAcouting;
	}

	public String getNumeroRadicado() {
		return numeroRadicado;
	}

	public void setNumeroRadicado(String numeroRadicado) {
		this.numeroRadicado = numeroRadicado;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}
