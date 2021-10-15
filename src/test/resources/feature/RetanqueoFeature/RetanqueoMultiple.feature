#language: es
Característica: Retanqueo de creditos

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
 
 @Retanqueo
 Esquema del escenario:  Retanqueo libre inversion
   Cuando El agente ingrese a la pestana retanqueo
   Y se filtra por <Cedula> <Pagaduria> para retanqueo multiple
   Y se da clic a retanquear a todos los creditos
   Y se ingresa el monto a solicitar <Retanqueo>

  Ejemplos:
  |Cedula |Retanqueo |Pagaduria|
	##@externaldata@./src/test/resources/Data/AutomationData.xlsx@RetanqueoMultiple
   |91216107   |5000000   |PROTECCIÓN S.A|

