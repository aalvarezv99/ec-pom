#language: es
#En este archivo de Caracteristicas se tiene en cuenta los procesos de aplicacion
#que comprenden lo siguiente 1.Cargue planilla 2.Pago recaudo 3.Preaplicacion 4.Aplicacion final y 5.Cierre
Característica: Aplicacion de pagos y cierre

  Antecedentes: Usuario en el sistema
    Dado Un agente en el sistema core abacus con sesion iniciada

  @CarguePlanillaAlSistema
  Esquema del escenario: Cargue planilla de la pagaduria en abacus
    Cuando Navegue al modulo de pagos y seleccione "Lista pagos a cargar"
    Y en la pantalla cargue de lista de pagos seleccione el <Periodo> para el ano actual
    Y Ingrese el <NombrePagaduria> en el campo pagaduria verificando que no se ha cargado anteriormente
    Y cargue la pagaduria <NombrePagaduria> que se encuentra en la ruta <RutaPagaduria>
    Entonces cargara la pagaduria de manera exitosa mostando el mensaje "La carga ha finalizado correctamente"
    Y se valida el valor listado de la <NombrePagaduria> para el <Periodo> con el valor del sistema terminando con el proceso

    Ejemplos: 
      | Periodo  | NombrePagaduria                        | RutaPagaduria                                            |
    #  | Junio 30 | FIDUPREVISORA NÓMINA PENSIONADOS FOMAG | "C:\\Users\\User\\Downloads\\PlanillasCarguePagaduria\\" |
	  |  Junio 30  |P.A POLICIA NACIONAL NOMINA UNIFORMADOS ACTIVOS|"C:\\Users\\User\\Downloads\\PlanillasCarguePagaduria\\"|
  
  @RecaudoPagaduria
  Esquema del escenario: Recaudo Pagaduria
    Cuando El agente navegue a la pestana pagos hasta la pestana preaplicacion de pagos
    Y Se filtra por <Pagaduria><Ano><Periodo>
    Y Se captura el valor del recaudo con la suma de valores recibidos
    Entonces se pasa a la pestana de recaudo
    Y se agrega el pago de recaudo <Pagaduria><Ano><Periodo>

    Ejemplos: 
      | Pagaduria        | Ano    | Periodo     |
      | "P.A POLICIA NACIONAL NOMINA UNIFORMADOS ACTIVOS" | "2021" | "Junio  30" |
      
    @PreaplicacionPagaduria
    Esquema del escenario: Preaplicacion Pagaduria
    	Cuando Navegue al modulo de pagos y seleccione "Preaplicacion pagos"
    	Y Se filtra por <Pagaduria><Ano><Periodo>
    	Y valide que no se ha realizado una preaplicacion anteriormente con el <IdPagaduria>
    	Y valide que el valor del recaudo sea igual al de recibido    	
    	Entonces permite realizar la preaplicacion mostrando el mensaje "Ha iniciado la preaplicación" 
    	Y se finaliza con el mensaje "Se finalizó la preaplicación de los pagos"
    	Ejemplos: 
    	|IdPagaduria| Pagaduria                                   | Ano    | Periodo     |
      |404| "P.A POLICIA NACIONAL NOMINA UNIFORMADOS ACTIVOS" | "2021" | "Junio  30" |
      
      @AplicacionFinalPagaduria
      Esquema del escenario: Aplicacion final de pagaduria en abacus
      	Cuando Navegue al modulo de pagos y seleccione "Aplicacion Final"
      	Y cuando filtre utilizando el <Periodo> con <Pagaduria> en la pantalla Aplicacion final
      	Y Se muestra un unico registro permitiendo confirmar el pago
      	Entonces en pantalla se visualiza el siguiente mensaje "se ha iniciado la aplicación de pagos"      	
      	Y Refresque el navegador haste que cambie a "SI" el "Recaudo confirmado" la <Pagaduria> y <Periodo>
      	Ejemplos:
      	|Pagaduria|Periodo|
      	|P.A POLICIA NACIONAL NOMINA UNIFORMADOS ACTIVOS|30/06/2021|
      	
      @CierrePagaduria
      Esquema del escenario: Cierre de pagaduria en abacus
      	Cuando Navegue al modulo de pagos y seleccione "Aplicacion Final"
      	Y cuando filtre utilizando el <Periodo> con <Pagaduria> en la pantalla Aplicacion final
      	Y Se muestra un unico registro permitiendo cerrar la pagaduria
      	Entonces en pantalla se visualiza el siguiente mensaje "el cierre de la pagaduria se ha iniciado"      	
      	Y Refresque el navegador haste que cambie a "CERRADA" el "Estado Pagaduria" la <Pagaduria> y <Periodo>
      	Ejemplos:
      	|Pagaduria|Periodo|
      	|P.A POLICIA NACIONAL NOMINA UNIFORMADOS ACTIVOS|30/06/2021|
      	
