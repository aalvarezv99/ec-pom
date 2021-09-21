#language: es
Característica: Retanqueo de creditos

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
  

 @RetanqueoCompraCarteraSaneamiento
 Esquema del escenario:  Retanqueo libre inversion
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
  # Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses><VlrCompraSaneamiento>
   Y se da clic en solicitar
   Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
   Y marcar los check en correcto guardando en la pestana de digitalizacion
   Y se presiona en verificacion en la pestana digitalizacion
   Y se pasa a la segunda pestana de digitalizacion se compra cartera y saneamiento <Competidor1><Cartera1><VlrCuota1><FechaVencimiento1><NumObligacion1><Competidor2><Saneamiento2><VlrCuota2><FechaVencimiento2><NumObligacion2>
   Y se guarda cartera
   Y se pasa a la primera pestana de referenciacion para confirmar la entidad <Competidor1><Cartera1><VlrCuota1><FechaVencimiento1><NumObligacion1><Competidor2><Saneamiento2><VlrCuota2><FechaVencimiento2><NumObligacion2>
   Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <codigo>
   Y se marca identidida confirmada radicando la solicitud
   Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
   
  Ejemplos: 
  	|Retanqueo|Cedula    |Credito|rutaPDF                                    |Tasa |Plazo  |DiasHabilesIntereses|Ingresos |descLey  |descNomina|IngresosMes|codigo|Competidor1         |VlrCuota1|FechaVencimiento1|NumObligacion1  |Competidor2        |Cartera1  | Saneamiento2   |VlrCompraSaneamiento| VlrCuota2    | FechaVencimiento2   | NumObligacion2  |         
    |"800000" |"77006163"|"57244"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"1.8"|"12"   |"21"                |"5100000"|"210000" |"350000"  |"5100000"  |"2258"|"8600068225 - ACORE"|"70000"  |"26/09/2021"     |"9123"          |"8110315267 - AMAR"|"300000"  |"400000"        |"700000"            | "80000"      | "30/09/2021"        | "9321"          |
 
     
      
  @AnalisisCreditoRetanqueoCarteraSaneamiento
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con compra de cartera y saneamiento <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><Cartera1><Saneamiento2><DiasHabilesIntereses>
   # Y Guarda los datos del simulador
   # Y Pasa a la pestana endeudamiento global aprobando
   # Y Aprueba la tarea del credito<Cedula>
   Ejemplos: 
    |Retanqueo|Credito|Cedula     |NombreCredito  |Ingresos  |descNomina |Mes      |Tasa    |Plazo |descLey |fecha       |Cartera1 |Saneamiento2|DiasHabilesIntereses|AnnoAfetacion  |
    |"800000" |"57244"|"77006163" |"DAIRO ANTONIO"|"5100000" |"350000"   |"Octubre"|"1.8"  |"12"  |"210000"|"10/09/2021"|"300000" |"400000"    |"21"                |"2022"         |


   @ClientesBienvenidaRetanqueosRetanqueoCarteraSaneamiento 
   Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
    # Y se validan los valores de las condiciones del credito
    Entonces se pasa a la pestana condiciones de credito se marcan los check condiciones y de carteras y se acepta<TipoDesen>
    Ejemplos: 
    |Cedula    |Celular     |Correo                     |TipoDesen  |
    |"77006163"|"3115128152"|"dandresabogadog@mail.com" |"Efectivo" |
    
    
    @CreditosVisacionRetanqueosCarteraSaneamiento 
    Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    
    Ejemplos: 
    |Cedula    |fechaActual |rutaPDF                                    |
    |"88214447"|"26/08/2021"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
    
    @DesembolsoCarteraCarteraSaneamiento 
    Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar la cartera uno <Cedula>
    Y se descargadescargan medios de dispersion para la cartera <Cartera1><Banco><rutaPDF>
    
    Ejemplos: 
    |Cedula      |Cartera1 |Banco                                 |rutaPDF                                    |
    |"94310649"  |"300000" |"Remanentes - 60237038927 - REMANENTE"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
       
    @VisacionCarteraCarteraSaneamiento 
    Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>
    Ejemplos: 
    |Cedula     |rutaPDF                                    |
    |"94310649" |"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
    
    @DesembolsoSaneamientoCarteraSaneamiento 
    Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el saneamiento <Cedula>
    Y se descargadescargan medios de dispersion para el saneamiento <Saneamiento2><Banco><rutaPDF>

    Ejemplos: 
    |Cedula    |Saneamiento2|Banco                                 |rutaPDF                                    |
    |"94310649"|"400000"    |"Remanentes - 60237038927 - REMANENTE"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
        
        
    @DesembolsoRetanqueosCarteraSaneamiento 
    Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se descarga medios de dispersion para el remanente <Retanqueo><Cartera1><Saneamiento2><Banco><rutaPDF>
   
    Ejemplos: 
   |Retanqueo  |Cedula    |Banco                                 |rutaPDF                                    |Cartera1|Saneamiento2|
   |"800000"  |"94310649"|"Remanentes - 60237038927 - REMANENTE"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"300000"|"400000"    |
   
   