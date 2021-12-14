#language: es
Característica: Retanqueo de creditos

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @RetanqueoCCS
  Esquema del escenario: Retanqueo Compra de cartera y saneamiento
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
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/12/2021       | 21236         |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/12/2021       | 21236         |
      | Saneamiento | 8600382991 - PAN AMERICAN LIFE DE COLOMBIA COMPAÑIA DE SEG | 400000 | 70000    | 30/12/2021       | 29123         |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 830000 |    70000 | 30/12/2021       |         29123 |
    Y se guarda cartera
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad
       | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/12/2021       | 21236         |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/12/2021       | 21236         |
      | Saneamiento | 8600382991 - PAN AMERICAN LIFE DE COLOMBIA COMPAÑIA DE SEG | 400000 | 70000    | 30/12/2021       | 29123         |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 830000 |    70000 | 30/12/2021       |         29123 |
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @AnalisisCreditoRetanqueoCCS
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con compra de cartera y saneamiento <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><Cartera1><Saneamiento2><DiasHabilesIntereses><Tasa>
    Y Guarda los datos del simulador
    #Y ingrese a la pestana del plan de pagos 
    #Y valide la informacion cabecera con sus conceptos para Retanqueo<Tasa><Plazo>
    #Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @ClientesBienvenidaRetanqueoCCS
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito <Credito>
    #Y validar las condiciones de la carta de notificacion de creditos <Cedula>
    Y se marcan los chech y se acepta carteras y saneamientos <TipoDesen><Cedula>

    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @CreditosVisacionRetanqueosCCS
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @DesembolsoCarteraCCS
  Esquema del escenario: Carteras para Desembolso
Cuando el agente ingresa a la lista de pagos para procesar "Cartera" con <Cedula>
    Y se descargan medios de dispersion para la cartera
    |Monto|Banco																|RutaPdf|
    |200000	|Remanentes - 60237038927 - REMANENTE	|src/test/resources/Data/PDFPRUEBA.pdf|
    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @VisacionSaneamientoCCS
  Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>

    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @DesembolsoSaneamientoCCS
  Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Saneamiento" con <Cedula>
    Y se descargan medios de dispersion para la cartera
    |Monto|Banco																|RutaPdf|
    |830000	|Remanentes - 60237038927 - REMANENTE	|src/test/resources/Data/PDFPRUEBA.pdf|
    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|

  @DesembolsoRetanqueosCCS
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
    Y se descarga medios de dispersion para el remanente
    |Monto|Cartera|Saneamiento|Banco|RutaPdf|
    |8000000|200000|830000|Remanentes - 60237038927 - REMANENTE	|src/test/resources/Data/PDFPRUEBA.pdf|
    Ejemplos: 
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes | fecha | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular | Correo | TipoDesen | fechaActual | Banco |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoCCS
   |"8000000"   |"24580532"   |"78635"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"25"   |"13"   |"6500000"   |"380000"   |"100000"   |"2258"   |"830000"   |"BLANCA NURY"   |"Octubre"   |"14/06/1969"   |"2021"   |"100000"   |"730000"   |"3145363053"   |"dandresabogadog@mail.com"   |"Efectivo"   |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"|
