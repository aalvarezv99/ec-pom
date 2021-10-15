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
	##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |91216107   |5000000   |P.A COLPENSIONES|

@ClientesBienvenidaRetanqueos 
  Esquema del escenario: Clientes para Bienvenida
   Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
   Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
   Y se validan los valores de las condiciones del credito <Credito>
   Y validar las condiciones de la carta de notificacion de creditos <Cedula>
   #Y se marcan los chech y se acepta<TipoDesen><Cedula>

   Ejemplos: 
   |Cedula | Credito |Celular     |Correo                     |TipoDesen  |
	##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@ClientesBienvenidaRetanqueos
   |1110448827   |85863   |3125117715   |prueba@mail.com   |Efectivo|


