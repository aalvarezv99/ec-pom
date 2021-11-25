#language: es
Característica: Recaudos generales en el sistema de cliente y pagaduria

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @RecaudoCertificacionSaldos
  Esquema del escenario: Recaudo Certificacion de saldos
    Cuando Navegue a la pestana pagos de recaudos
    Entonces permite generar el recaudo con el <ValorRecaudo> para "certificacion de saldo" con los datos del cliente <NumCedula>
    Ejemplos: 
       |NumRadicado|	NumCedula|	ValorRecaudo|	TipoPago	|RutaDocumento|	VlrPagar|
	##@externaldata@./src/test/resources/Data/AutomationDataRecaudos.xlsx@RecaudosAbacus
   |66994   |16343742   |100000   |Recaudo por ventanilla   |"C:\\Users\\User\\Downloads\\CertificacionSaldos\\"   |"100000"|

  @RecaudoPrepagoCredito
  Esquema del escenario: Recaudo Prepago Credito
    Cuando El agente visualice el documento del certificado <RutaDocumento> del credito <NumRadicado>
    Y se realiza la validacion del PDF descargado con el <NumRadicado> en la ruta <RutaDocumento>
    Y Navegue a la pestana pagos de recaudos
    Y Realice el recaudo del credito <NumRadicado> con el valor total a pagar <RutaDocumento> para <TipoPago> con los datos del cliente <NumCedula>
    Entonces se finaliza verificando el estado del credito <NumRadicado> que cambio a "Prepagado"

    #Y la amortizacion del prepago y lo movimientos contables en bases de datos
      |NumRadicado|	NumCedula|	ValorRecaudo|	TipoPago	|RutaDocumento|	VlrPagar|
	##@externaldata@./src/test/resources/Data/AutomationDataRecaudos.xlsx@RecaudosAbacus
   |66994   |16343742   |100000   |Recaudo por ventanilla   |"C:\\Users\\User\\Downloads\\CertificacionSaldos\\"   |"100000"|

  @RecaudoPagaduria
  Esquema del escenario: Recaudo Pagaduria
    Cuando Navegue a la pestana pagos de recaudos
    Y Realice el recaudo con el valor <ValorRecaudo> y origen "pagaduria"
    Ejemplos: 
        |NumRadicado|	NumCedula|	ValorRecaudo|	TipoPago	|RutaDocumento|	VlrPagar|
	##@externaldata@./src/test/resources/Data/AutomationDataRecaudos.xlsx@RecaudosAbacus
   |66994   |16343742   |100000   |Recaudo por ventanilla   |"C:\\Users\\User\\Downloads\\CertificacionSaldos\\"   |"100000"|
      
    @RecaudoPorVentanilla
    Esquema del escenario: Recaudo Cliente Por Ventanilla
    Cuando Navegue a la pestana pagos de recaudos
    Y Realice el recaudo del credito <NumRadicado> con el valor total a pagar <VlrPagar> para <TipoPago> con los datos del cliente <NumCedula>
    Ejemplos: 
      |NumRadicado|	NumCedula|	ValorRecaudo|	TipoPago	|RutaDocumento|	VlrPagar|
	##@externaldata@./src/test/resources/Data/AutomationDataRecaudos.xlsx@RecaudosAbacus
   |66994   |16343742   |100000   |Recaudo por ventanilla   |"C:\\Users\\User\\Downloads\\CertificacionSaldos\\"   |"100000"|
      
    @RecaudoDebitoAutomatico
    Esquema del escenario: Recaudo Debito automatico
    Cuando Navegue a la pestana pagos de recaudos
    Y Realice el recaudo del credito <NumRadicado> con el valor total a pagar <VlrPagar> para <TipoPago> con los datos del cliente <NumCedula>
    Ejemplos: 
      |NumRadicado|	NumCedula|	ValorRecaudo|	TipoPago	|RutaDocumento|	VlrPagar|
	##@externaldata@./src/test/resources/Data/AutomationDataRecaudos.xlsx@RecaudosAbacus
   |66994   |16343742   |100000   |Recaudo por ventanilla   |"C:\\Users\\User\\Downloads\\CertificacionSaldos\\"   |"100000"|
