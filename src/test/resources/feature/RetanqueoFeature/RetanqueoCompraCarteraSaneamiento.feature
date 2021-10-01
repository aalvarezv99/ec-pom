#language: es
Característica: Retanqueo de creditos

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
  

 @RetanqueoCompraCarteraSaneamiento
 Esquema del escenario:  Retanqueo Compra de cartera y saneamiento
   Cuando El agente ingrese a la pestana retanqueo 
   Y se filtra por <Cedula><Credito>
   Y se da clic a retanquear   
   Y se busca el credito por <Cedula>
   Y se selecciona el retanqueo
   Y borrar archivos
   Y cargar archivos nuevos <rutaPDF>
   Y se solicita la consulta a centrales de riesgo
   Y marcar el credito viable
   Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina><VlrCompraSaneamiento>
   Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses><VlrCompraSaneamiento>
   Y se da clic en solicitar
   Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
   Y marcar los check en correcto guardando en la pestana de digitalizacion
   Y se presiona en verificacion en la pestana digitalizacion
   Y se crean los tipos de cartera o saneamiento a recoger 
   		|Tipo						|Entidad				 				                        |Monto |VlrCuota|FechaVencimiento	|NumObligacion|
   	#	|Cartera				|8600068225 - ACORE				                      |100000|70000  	|30/09/2021     	|21236   			|
   		|Saneamiento    |8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA		|830000|70000  	|30/09/2021     	|29123   			|
   Y se guarda cartera
   Y se pasa a la primera pestana de referenciacion para confirmar la entidad
    	|Tipo						|Entidad				 				|Monto	|VlrCuota|FechaVencimiento	|NumObligacion|
    	|Saneamiento    |8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA				|830000|70000  	|30/09/2021     	|29123   			|
    #	|Cartera				|8600068225 - ACORE			|100000 |70000   |30/09/2021     	  |21236   			|    	
   Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <codigo>
   Y se marca identidida confirmada radicando la solicitud
   Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
   
  Ejemplos: 
  	|Retanqueo|Cedula     |Credito|rutaPDF                                |Tasa |Plazo|DiasHabilesIntereses|Ingresos |descLey  |descNomina|IngresosMes|codigo| VlrCompraSaneamiento|      
    |"7000000"|"72244446" |"77475"|"src/test/resources/Data/PDFPRUEBA.pdf"|"1.8"|"25" |"13"                |"6500000"|"380000" |"100000"  |"3500000"  |"2258"|"200000"|
      
  @AnalisisCreditoRetanqueoCarteraSaneamiento
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con compra de cartera y saneamiento <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><Cartera1><Saneamiento2><DiasHabilesIntereses><Tasa>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>
   Ejemplos: 
    |Retanqueo|Credito|Cedula      |NombreCredito    |Ingresos  |decLey   |descNomina |Mes      |Tasa    |Plazo|descLey  |descNomina |fecha       |Cartera1 |Saneamiento2|DiasHabilesIntereses|AnnoAfetacion  |
    |"7000000"|"77475"|"72244446"  |"ALVARO DE JESUS"|"6500000" |"380000" |"100000"   |"Octubre"|"1.8"   |"25" |"380000" |"100000"   |"25/09/2021"|"0"      |"830000"    |"13"                | "2021"        |


   @ClientesBienvenidaRetanqueosRetanqueoCarteraSaneamiento 
   Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
    Y se validan los valores de las condiciones del credito <Credito>
    Entonces se pasa a la pestana condiciones de credito se marcan los check condiciones y de carteras y se acepta<TipoDesen>
    Ejemplos: 
    |Cedula    |Credito|Celular     |Correo                     |TipoDesen  |
    |"9077633" |"72864"|"3115128152"|"dandresabogadog@mail.com" |"Efectivo" |
    
    
    @CreditosVisacionRetanqueosCarteraSaneamiento 
    Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    
    Ejemplos: 
    |Cedula    |fechaActual |rutaPDF                                    |
    |"32530184"|"13/08/2021"|"src/test/resources/Data/PDFPRUEBA.pdf"|
    
    @DesembolsoCarteraCarteraSaneamiento 
    Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar la cartera uno <Cedula>
    Y se descargadescargan medios de dispersion para la cartera <Cartera1><Banco><rutaPDF>
    
    Ejemplos: 
    |Cedula      |Cartera1 |Banco                                  |rutaPDF                                    |
    |"32530184"   |"1000000"|"Remanentes - 60237038927 - REMANENTE" |"src/test/resources/Data/PDFPRUEBA.pdf"|
       
    @VisacionCarteraCarteraSaneamiento 
    Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>
    Ejemplos: 
    |Cedula      |rutaPDF                                    |
    |"32530184"  |"src/test/resources/Data/PDFPRUEBA.pdf"|
    
    @DesembolsoSaneamientoCarteraSaneamiento 
    Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el saneamiento <Cedula>
    Y se descargadescargan medios de dispersion para el saneamiento <Saneamiento2><Banco><rutaPDF>

    Ejemplos: 
    |Cedula    |Saneamiento2|Banco                                 |rutaPDF                                    |
    |"32530184" |"150000"    |"Remanentes - 60237038927 - REMANENTE"|"src/test/resources/Data/PDFPRUEBA.pdf"|
        
        
    @DesembolsoRetanqueosCarteraSaneamiento 
    Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se descarga medios de dispersion para el remanente <Retanqueo><Cartera1><Saneamiento2><Banco><rutaPDF>
   
    Ejemplos: 
   |Retanqueo  |Cedula    |Banco                                 |rutaPDF                                    |Cartera1 |Saneamiento2|
   |"5000000"  |"32530184" |"Remanentes - 60237038927 - REMANENTE"|"src/test/resources/Data/PDFPRUEBA.pdf"|"1000000"|"150000"    |
   
   