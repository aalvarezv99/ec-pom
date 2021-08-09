#language: es
Característica: Retanqueo de credito saneamiento

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
  

 @RetanqueoCreditoSaneamiento
 Esquema del escenario:  Retanqueo libre inversion con saneamiento
   Cuando El agente ingrese a la pestana retanqueo 
   Y se filtra por <Cedula><Credito>
   Y se da clic a retanquear   
   Y se busca el credito por <Cedula>
   Y se selecciona el retanqueo
   Y borrar archivos
   Y cargar archivos nuevos <rutaPDF>
   Y se solicita la consulta a centrales de riesgo
   Y marcar el credito viable
   Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina>
   Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses>
   Y se da clic en solicitar
   Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
   Y marcar los check en correcto guardando en la pestana de digitalizacion
   Y se presiona en verificacion en la pestana digitalizacion
   Y se pasa a la segunda pestana de digitalizacion se compra cartera y saneamiento <Competidor><Saneamiento><VlrCuota><FechaVencimiento><NumObligacion>
   Y se guarda cartera
   Y se pasa a la primera pestana de referenciacion para confirmar la entidad <Competidor><Saneamiento><VlrCuota><FechaVencimiento><NumObligacion>
   Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences del retanqueo saneamiento <codigo>
   Y se marca identidida confirmada radicando la solicitud
   Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
   
  Ejemplos: 
  	|Retanqueo|Cedula     |Credito   |rutaPDF                                    |Tasa |Plazo|DiasHabilesIntereses|Ingresos |descLey  | descNomina | IngresosMes |codigo| Competidor             | Saneamiento    | VlrCuota     | FechaVencimiento    | NumObligacion   |         
    |"4000000"|"79496499" |"69124"   |"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"1.8"|"60" |"10"                |"8500000"|"280000" | "50000"    | "3500000"   |"2258"| "8110315267 - AMAR"    | "450000"       | "40000"      | "30/07/2021"        | "9321"          |
      
  @AnalisisCreditoRetanqueoSaneamiento
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con saneamiento <Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><Saneamiento><Credito><DiasHabilesIntereses>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>
   Ejemplos: 
    |Retanqueo|Credito |Cedula         |NombreCredito   |Ingresos |decLey    |descNomina|Mes    |Tasa  |Plazo|descLey  |descNomina|fecha         | Saneamiento |DiasHabilesIntereses|
    |"4000000"|"69124" |"79496499"     |"JIM"          |"8500000"|"280000"  |"50000"     |"Julio"|"1.8" |"60" |"280000" |"50000"   |"22/07/2021"| "450000"   |"10"                |


   @ClientesBienvenidaRetanqueosRetanqueoSaneamiento 
   Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
    Entonces se pasa a la pestana condiciones de credito retanqueo se marcan los check condiciones y de saneamiento y se acepta <TipoDesen>
    Ejemplos: 
    |Cedula         |Celular     |Correo                     |TipoDesen  |
    |"79496499"     |"3115128152"|"dandresabogadog@mail.com" |"Efectivo" |
    
    @VisacionRetanqueosSaneamiento 
    Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    
    Ejemplos: 
    |Cedula    |fechaActual |rutaPDF                                    |
    |"79496499"|"22/07/2021"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
       
    @DesembolsoSaneamiento 
    Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el saneamiento <Cedula>
    Y se descargadescargan medios de dispersion para el saneamiento <Saneamiento><Banco><rutaPDF>

    Ejemplos: 
    |Cedula      |Saneamiento |Banco                                  |rutaPDF                                    |
    |"79496499"  |"450000"    |"Remanentes - 60237038927 - REMANENTE" |"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
        
        
    @DesembolsoRetanqueoRemanente 
    Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se descarga medios de dispersion para el remanente <Retanqueo><Saneamiento><Banco><rutaPDF>
   
    Ejemplos: 
   |Retanqueo  |Cedula    |Banco                                 |rutaPDF                                    |Saneamiento |
   |"4000000"  |"79496499"|"Remanentes - 60237038927 - REMANENTE"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"450000"    |
   
   