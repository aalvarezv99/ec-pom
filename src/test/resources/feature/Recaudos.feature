#language: es
Característica: Recaudos generales en el sistema de cliente y pagaduria

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @RecaudoCertificacionSaldos
  Esquema del escenario: Recaudo Certificacion de saldos
    Cuando Navegue a la pestana pagos de recaudos
    Y se realiza la validacion del PDF descargado con el <NumRadicado>
    Entonces permite generar el recaudo con el <ValorRecaudo> para <TipoPago> con los datos del cliente <NumCedula>

    Ejemplos: 
      | NumRadicado | NumCedula | ValorRecaudo | TipoPago                  |
      |       12364 |  12021021 |        18000 | "Certificacion de saldos" |

  @RecaudoPrepagoCredito
  Esquema del escenario: Recaudo Prepago Credito
    Cuando El agente visualice el documento del certificado <RutaDocumento> del credito <NumRadicado>
    Y se realiza la validacion del PDF descargado con el <NumRadicado>
    Y Navegue a la pestana pagos de recaudos
    Y Realice el recaudo del credito <NumRadicado> con el valor total a pagar <RutaDocumento> para <TipoPago> con los datos del cliente <NumCedula>
    Entonces se finaliza verificando el estado del credito <NumRadicado> que cambio a "Prepagado"

    #Y la amortizacion del prepago y lo movimientos contables en bases de datos
    Ejemplos: 
      | NumRadicado | NumCedula | TipoPago  | RutaDocumento                                       |
      |       56005 |  52660958 | "Prepago" | "C:\\Users\\User\\Downloads\\CertificacionSaldos\\" |

  @RecaudoPagaduria
  Esquema del escenario: Recaudo Pagaduria
    Cuando El agente navegue a la pestana pagos hasta la pestana preaplicacion de pagos
    Y Se filtra por <Pagaduria><Ano><Periodo>
    Y Se captura el valor del recaudo con la suma de valores recibidos
    Entonces se pasa a la pestana de recaudo
    Y se agrega el pago de recaudo <Pagaduria><Periodo><Periodo>
   
    Ejemplos: 
      |Pagaduria                                     |Ano   |Periodo      |
      |"ALCALDIA MUNICIPAL DE ESPINAL PENSIONADOS"   |"2020"|"Junio  30"  |
