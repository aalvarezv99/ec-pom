#language: es
#En este archivo de Caracteristicas se tiene en cuenta los procesos de aplicacion
#que comprenden lo siguiente 1.Cargue planilla 2.Pago recaudo 3.Preaplicacion 4.Aplicacion final y 5.Cierre
Característica: Aplicacion de pagos y cierre

  Antecedentes: Usuario en el sistema
    #Dado Un agente en el sistema core abacus con sesion iniciada

  @CarguePlanillaAlSistema
  Esquema del escenario: Cargue planilla de la pagaduria en abacus
    Cuando Navegue al modulo de pagos y seleccione "Lista pagos a cargar"
    Y en la pantalla cargue de lista de pagos seleccione el <Periodo> para el ano actual
    Y Ingrese el <NombrePagaduria> en el campo pagaduria verificando que no se ha cargado anteriormente
    Y cargue la pagaduria <NombrePagaduria> que se encuentra en la ruta <RutaPagaduria>
    Entonces cargara la pagaduria de manera exitosa mostando el mensaje "La carga ha finalizado correctamente"
    Y se valida el valor listado de la <NombrePagaduria> para el <Periodo> con el valor del sistema terminando con el proceso

    #Tener en cuenta que en el periodo y el numero deben ir dos espacios
    Ejemplos: 
      |IdPagaduria|	Periodo|	NombrePagaduria	|RutaPagaduria|	Ano	|PeriodoEspacio|	FiltroFecha|
      ##@externaldata@./src/test/resources/Data/AutomationDataAplicacionPagoPagaduria.xlsx@AplicacionPago
   |271   |Octubre 30   |"BANCO DE LA REPUBLICA NOMINA JUBILADOS"   |"src/test/resources/Data/PagaduriaAplicacion/"   |2021   |"Octubre  30"   |30/10/2021|

  @RecaudoPagaduria
  Esquema del escenario: Recaudo Pagaduria
    Cuando El agente navegue a la pestana pagos hasta la pestana preaplicacion de pagos
    Y Se filtra por <NombrePagaduria><Ano><PeriodoEspacio>
    Y Se captura el valor del recaudo con la suma de valores recibidos
    Entonces se pasa a la pestana de recaudo
    Y se agrega el pago de recaudo <NombrePagaduria><Ano><PeriodoEspacio>

    Ejemplos: 
      |IdPagaduria|	Periodo|	NombrePagaduria	|RutaPagaduria|	Ano	|PeriodoEspacio|	FiltroFecha|
      ##@externaldata@./src/test/resources/Data/AutomationDataAplicacionPagoPagaduria.xlsx@AplicacionPago
   |271   |Octubre 30   |"BANCO DE LA REPUBLICA NOMINA JUBILADOS"   |"src/test/resources/Data/PagaduriaAplicacion/"   |2021   |"Octubre  30"   |30/10/2021|

  @PreaplicacionPagaduria
  Esquema del escenario: Preaplicacion Pagaduria
    Cuando Navegue al modulo de pagos y seleccione "Preaplicacion pagos"
    Y Se filtra por <NombrePagaduria><Ano><PeriodoEspacio>
    Y valide que no se ha realizado una preaplicacion anteriormente con el <IdPagaduria>
    Y valide que el valor del recaudo sea igual al de recibido
    Entonces permite realizar la preaplicacion mostrando el mensaje "Ha iniciado la preaplicación"
    Y se finaliza con el mensaje "Se finalizó la preaplicación de los pagos"

    Ejemplos: 
      |IdPagaduria|	Periodo|	NombrePagaduria	|RutaPagaduria|	Ano	|PeriodoEspacio|	FiltroFecha|
      ##@externaldata@./src/test/resources/Data/AutomationDataAplicacionPagoPagaduria.xlsx@AplicacionPago
   |271   |Octubre 30   |"BANCO DE LA REPUBLICA NOMINA JUBILADOS"   |"src/test/resources/Data/PagaduriaAplicacion/"   |2021   |"Octubre  30"   |30/10/2021|

  @AplicacionFinalPagaduria
  Esquema del escenario: Aplicacion final de pagaduria en abacus
    Cuando Navegue al modulo de pagos y seleccione "Aplicacion Final"
    Y cuando filtre utilizando el <FiltroFecha> con <NombrePagaduria> en la pantalla Aplicacion final
    Y Se muestra un unico registro permitiendo confirmar el pago
    Entonces en pantalla se visualiza el siguiente mensaje "se ha iniciado la aplicación de pagos"
    Y Refresque el navegador haste que cambie a "SI" el "Recaudo confirmado" la <NombrePagaduria> y <FiltroFecha>

    Ejemplos: 
      |IdPagaduria|	Periodo|	NombrePagaduria	|RutaPagaduria|	Ano	|PeriodoEspacio|	FiltroFecha|
      ##@externaldata@./src/test/resources/Data/AutomationDataAplicacionPagoPagaduria.xlsx@AplicacionPago
   |271   |Octubre 30   |"BANCO DE LA REPUBLICA NOMINA JUBILADOS"   |"src/test/resources/Data/PagaduriaAplicacion/"   |2021   |"Octubre  30"   |30/10/2021|
   
  @ValidarDinamicasContablesAPLPAG
  Esquema del escenario: Validar dinamicas contables en la Aplicacion de pagos
    Cuando Se consulten los creditos cargados para <IdPagaduria> y se comparen con los que generaron movimientos contables <AccountingSource> en la <FechaRegistro>
    #Y el sistema valida por <IdPagaduria> en la tabla movimiento contable las <AccountingSource> que se proceso por el bridge en la <FechaRegistro>
   	Y valide la causacion de movimientos <AccountingSource> con sus tipos y valores usando la <IdPagaduria> en la <FechaRegistro>
    Y valida que las cuentas de libranzas <AccountingSource> sean las del bridge <AccountingName> con el <NumRadicado> en la <FechaRegistro>
    #Entonces finalmente se valida la transaccion <AccountingSource> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>
    Ejemplos: 
   | IdPagaduria  |AccountingSource|AccountingName|FechaRegistro|
    | 345 |"'APLPAG'"   |"upper('Aplicación de pago por pagaduría')"   |25/11/2021|

  @CierrePagaduria
  Esquema del escenario: Cierre de pagaduria en abacus
    Cuando Navegue al modulo de pagos y seleccione "Aplicacion Final"
    Y cuando filtre utilizando el <FiltroFecha> con <NombrePagaduria> en la pantalla Aplicacion final
    Y Se muestra un unico registro permitiendo cerrar la pagaduria
    Entonces en pantalla se visualiza el siguiente mensaje "el cierre de la pagaduria se ha iniciado"
    Y Refresque el navegador haste que cambie a "CERRADA" el "Estado Pagaduria" la <NombrePagaduria> y <FiltroFecha>

    Ejemplos: 
      |IdPagaduria|	Periodo|	NombrePagaduria	|RutaPagaduria|	Ano	|PeriodoEspacio|	FiltroFecha|
      ##@externaldata@./src/test/resources/Data/AutomationDataAplicacionPagoPagaduria.xlsx@AplicacionPago
   |271   |Octubre 30   |"BANCO DE LA REPUBLICA NOMINA JUBILADOS"   |"src/test/resources/Data/PagaduriaAplicacion/"   |2021   |"Octubre  30"   |30/10/2021|
