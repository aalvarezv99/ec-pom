#language: es
Característica: Retanqueo de creditos

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
  

 @Retanqueo
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
   Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses><VlrCompraSaneamiento>
   Y se da clic en solicitar
   Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
   Y marcar los check en correcto guardando en la pestana de digitalizacion
   Y se presiona en verificacion en la pestana digitalizacion
   Y se confirma identidad en digitalizacion <codigo>
   Y se marca identidida confirmada radicando la solicitud
   Y se aprueban las referencias de la pagaduria
   Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
   
  Ejemplos: 
  |Retanqueo |Cedula    |Credito|rutaPDF                                    |Tasa |Plazo|DiasHabilesIntereses|Ingresos |descLey | descNomina|VlrCompraSaneamiento|codigo|         
  |"12000000"|"27950898"|"36929"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"1.8"|"22" |"30"                |"5000000"|"360000"| "80000"   |"0"                 |"2258"|

  @AnalisisCreditoRetanqueo
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos <Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><DiasHabilesIntereses>
    #Y Guarda los datos del simulador                     
    #Y Pasa a la pestana endeudamiento global aprobando
    #Y Aprueba la tarea del credito<Cedula>
   Ejemplos: 
    |Retanqueo  |Cedula     |Credito|NombreCredito|Ingresos |decLey  |descNomina|Mes     |Tasa  |Plazo|descLey  |fecha       |DiasHabilesIntereses|
    |"12000000" |"27950898" |"36929"|"MARIA EMMA" |"5000000"|"360000"|"80000"   |"Agosto"|"1.8" |"22" |"360000" |"30/08/2021"|"30"                |


   @ClientesBienvenidaRetanqueos 
   Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
    #Y se validan los valores de las condiciones del credito
    Entonces se pasa a la pestana condiciones de credito se marcan los chech y se acepta<TipoDesen>

    Ejemplos: 
    |Cedula    |Celular     |Correo                     |TipoDesen  |
    |"32530184"|"3115128152"|"dandresabogadog@mail.com" |"Efectivo" |
    
    
    @CreditosVisacionRetanqueos
    Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    
    Ejemplos: 
    |Cedula    |fechaActual |rutaPDF                                    |
    |"32530184"|"25/08/2021"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
    
    @DesembolsoRetanqueos
    Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto para retanqueo y se edita <Retanqueo><Banco><rutaPDF>
   
    Ejemplos: 
   |Retanqueo  |Cedula     |Banco                                  |rutaPDF                                    |
   |"5250000"  |"32530184" |"Remanentes - 60237038927 - REMANENTE" |"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|
   
   