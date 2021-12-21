#language: es
Característica: Prepago de Creditos

  Antecedentes: Usuario en el sistema
    Dado Un agente en el sistema core abacus con sesion iniciada
    Y con las funciones sql necesarias del proyecto creadas

  @CertidicacionSaldoActivaCXCFianza
  Esquema del escenario: Certificacion de saldo con credito en estado activo con cxc y fianza
    Cuando Navegue a la configuracion global del pregago
    Y Configure los valores <DiaCertificacion> <VencimientoCert> <ValorCertificacion> para la certificacion de saldo
    Y ingrese a la opcion de certificacion de saldos
    Y este ingrese el numero de radicado <NumRadicado> con cedula <NumCedula> para un cliente con credito "ACTIVO" se aplicara el filtro
    Y seleccionando el boton solicitar
    Y este ingrese la informacion en la ventana solicitar presionando el boton guardar
    Y Navegue a la pestana pagos de recaudos
    Entonces permite generar el recaudo con el <ValorCertificacion> para "certificacion de saldo" con los datos del cliente <NumCedula>
    Y posteriormente descargando la certificacion en el modulo gestion certificados con los datos del cliente <NumRadicado> y <NumCedula>
    Y El agente visualice el documento del certificado <RutaDocumento> del credito <NumRadicado>
    Y se realiza la validacion del PDF descargado con el <NumRadicado> en la ruta <RutaDocumento>
    Y Navegue a la pestana pagos de recaudos
    Y Realice el recaudo del credito <NumRadicado> con el valor total a pagar <RutaDocumento> para <TipoPago> con los datos del cliente <NumCedula>
    Entonces se finaliza verificando el estado del credito <NumRadicado> que cambio a "Prepagado"
    #Y la amortizacion del prepago y lo movimientos contables en bases de datos
    Ejemplos:
      | NumRadicado | NumCedula | DiaCertificacion | VencimientoCert | ValorCertificacion | TipoPago | RutaDocumento                                  | AccountingSource | AccountingName                          | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataCertSaldos.xlsx@CertificacionSaldo
   |66994   |16343742   |21   |0   |18000   |Prepago   |"src/test/resources/Data/CertificacionSaldos/"   |"'CSALD'"   |"upper('Recaudo certificado de saldo')"   |21/12/2021|

  @ValidarDinamicasContablesCSALD
  Esquema del escenario: Validar dinamicas contables CERTIFICACION SALDO
    Cuando se valida por <NumRadicado> y <NumCedula> en la tabla movimiento contable las <AccountingSource> que se proceso por el bridge en la <FechaRegistro>
    Y valide la causacion de movimientos <AccountingSource> con sus tipos y valores usando el <NumRadicado> en la <FechaRegistro>
    Y valida que las cuentas de libranzas <AccountingSource> sean las del bridge <AccountingName> con el <NumRadicado> y <NumCedula> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion <AccountingSource> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>
    Ejemplos:
      | NumRadicado | NumCedula | DiaCertificacion | VencimientoCert | ValorCertificacion | TipoPago | RutaDocumento                                  | AccountingSource | AccountingName                          | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataCertSaldos.xlsx@CertificacionSaldo
   |66994   |16343742   |21   |0   |18000   |Prepago   |"src/test/resources/Data/CertificacionSaldos/"   |"'CSALD'"   |"upper('Recaudo certificado de saldo')"   |21/12/2021|

   
