#language: es
Característica: Retanqueo de creditos

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
  

  @Retanqueo
   Esquema del escenario:  Retanqueo compra cartera
   Cuando El agente ingrese a la pestana retanqueo 
   Y se filtra por <Cedula><Credito>
   Y se da clic a retanquear   
   Y se busca el credito por <Cedula>
   Y se selecciona el retanqueo
   Y borrar archivos
   Y cargar archivos nuevos <rutaPDF>
   Y se solicita la consulta a centrales de riesgo
   Y marcar el credito viable
   Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina><cartera>
   Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses><cartera>
   Y se da clic en solicitar
   Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
   Y marcar los check en correcto guardando en la pestana de digitalizacion
   Y se presiona en verificacion en la pestana digitalizacion
   Y se pasa a la segunda pestana de digitalizacion se agrega la compra de cartera <entidad><cartera><vlr_cuota><fecha_vencimiento><num_obligacion>
   Y guarda la cartera agregada    
   Y se pasa a la primera pestana de referenciacion para confirmar la entidad <entidad>
   Y se confirma el numero de obligacion <num_obligacion>
   Y se aprueba la cartera
   Y se guarda en la primera pestana de referenciacion
   Y se confirma identidad en digitalizacion <codigo>
   Y se pasa a la segunda pestana de digitalizacion y seleciona la cartera
   Y se marca identidida confirmada radicando la solicitud
   Y se aprueban las referencias de la pagaduria
   Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
   
   Ejemplos: 
   |Cedula    |NombreCredito     |Credito |rutaPDF                                    |Retanqueo|Tasa |Plazo|DiasHabilesIntereses|Ingresos |descLey |descNomina|entidad                               |cartera  |vlr_cuota|fecha_vencimiento|num_obligacion|codigo|fechaActual |
   |"7412777" |"EDUARDO ENRIQUE" |"72740" |"src/test/resources/Data/PDFPRUEBA.pdf"    |"7000000"|"1.8"|"20" |"5"                 |"4500000"|"300000"|"50000"   |"8300538122 -  FIDEICOMISO SOLUCIONES"|"1500000"|"65000"  |"30/08/2021"     |"2007"        |"1037"|"29/09/2021"|
 
  @AnalisisCreditoRetanqueo
   Esquema del escenario: Analisis del credito
   Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
   Y ingresa los valores guardando <Ingresos><descLey><descNomina>
   Y pasa a la siguiente pestana del simulador analista
   Entonces Valida los valores del simulador retanqueos <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><cartera><DiasHabilesIntereses>
   Y Guarda los datos del simulador
   Y Pasa a la pestana endeudamiento global aprobando
   Y Aprueba la tarea del credito<Cedula>
    
   Ejemplos: 
   |Cedula    |NombreCredito    |Credito |Retanqueo|Tasa |Plazo|fecha        |Mes      |DiasHabilesIntereses|Ingresos |descLey |descNomina|cartera  |AnnoAfetacion  |
   |"7412777" |"EDUARDO ENRIQUE"|"72740" |"7000000"|"1.8"|"20" |"29/09/2021"|"Octubre"|"5"                |"4500000"|"300000"|"50000"    |"1500000"|"2021"         |
 
  @ClientesBienvenida 
   Esquema del escenario: Clientes para Bienvenida
   Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
   Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
   Y se validan los valores de las condiciones del credito
  # Entonces se pasa a la pestana condiciones de credito se marcan los check acepta cartera y se acepta condiciones<TipoDesen>
  
   Ejemplos: 
   |Cedula    |TipoDesen |Celular     |Correo              |
   |"93376715" |"Efectivo"|"3165328218"|"luis.cubillos715@casur.gov.co"|
  
  @CreditosVisacion
   Esquema del escenario: Creditos para Visacion
   Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
   Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    
   Ejemplos: 
   |Cedula    |rutaPDF                                    |fechaActual |
   |"93376715" |"src/test/resources/Data/PDFPRUEBA.pdf"|"25/08/2021"|
   
  @DesembolsoCartera
   Esquema del escenario: Carteras para Desembolso
   Cuando el agente ingresa a la lista de pagos para procesar la cartera <Cedula>
   Y se descargadescargan medios de dispersion para la cartera <cartera><Banco><rutaPDF>

   Ejemplos: 
   |Cedula     |rutaPDF                                    |cartera  |Banco                                            |
   |"93376715"  |"src/test/resources/Data/PDFPRUEBA.pdf"|"1500000"|"Banco de Occidente - 219856622 - COMPRA_CARTERA"|
 
 @VisacionCartera
    Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>
    
   Ejemplos: 
   |Cedula      |rutaPDF                                    |
   |"93376715"   |"src/test/resources/Data/PDFPRUEBA.pdf"|
 
  @Desembolso
   Esquema del escenario: Remanente para Desembolso
   Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
   Y se descarga medios de dispersion para el remanente <Retanqueo><cartera><Banco><rutaPDF>
    
   Ejemplos: 
   |Cedula      |rutaPDF                                    |Retanqueo|cartera  |Banco                                            |
   |"93376715"   |"src/test/resources/Data/PDFPRUEBA.pdf"|"3000000"|"1500000"|"Banco de Occidente - 219856622 - COMPRA_CARTERA"|
  
  
    