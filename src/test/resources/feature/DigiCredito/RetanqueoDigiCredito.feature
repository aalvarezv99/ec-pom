#language: es
Característica: Retanqueo de creditos

  Antecedentes:
    Dado Un agente en el sistema core abacus con sesion iniciada

  @Retanqueo
  Esquema del escenario: Retanqueo libre inversion digicredito
    Cuando el agente ingrese a la pestana solicitud filtra por el numero de radicacion del credito <NumRadicadoCredito><NombreCredito>
   # Y cargar archivos nuevos <rutaPDF>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se confirma identidad en digitalizacion <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Y se aprueban las referencias de la pagaduria
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
    Ejemplos:
      | Retanqueo | Cedula     | Credito | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes       | fecha        | AnnoAfetacion | Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCredito
   |"7500000"   |"10092369"   |"68003"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"250000"   |"300000"   |"0"   |"2258"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"3115128152"   |"dandresabogadog@mail.com"   |"Efectivo"    |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86379"|

  @RetanqueoCCS
  Esquema del escenario: Retanqueo Compra de cartera y saneamiento Digicredito
    Cuando el agente ingrese a la pestana solicitud filtra por el numero de radicacion del credito <NumRadicadoCredito><NombreCredito>
    Y cargar archivos nuevos <rutaPDF>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se crean los tipos de cartera o saneamiento a recoger creados en digicredito
    Y se guarda cartera
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad
      | Tipo        | Entidad                            | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Saneamiento | AMERICAS BUSINESS PROCESS SERVICES | 250000 | 60000    | 30/12/2021       | 29124         |
      | Cartera     | AMERICAS BUSINESS PROCESS SERVICES | 200000 | 50000    | 30/12/2021       | 21237         |
    Y se confirma identidad en digitalizacion <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Y se aprueban las referencias de la pagaduria
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos:
      | Retanqueo | Cedula     | Credito  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes       | fecha        | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCreditoCCS
   |"8000000"   |"10092369"   |"134950"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"200000"   |"300000"   |"2258"   |"450000"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"200000"   |"250000"   |"3228483385"   |"dandresabogadog@mail.com"   |"Efectivo"   |"25/11/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86318"|

  @AnalisisCreditoRetanqueo
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><DiasHabilesIntereses><Tasa>
    Y Guarda los datos del simulador
    Y ingrese a la pestana del plan de pagos
    Y valide la informacion cabecera con sus conceptos para Retanqueo<Tasa><Plazo>
    Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos:
      | Retanqueo | Cedula     | Credito  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes       | fecha        | AnnoAfetacion |  Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCredito
   |"7500000"   |"10092369"   |"68003"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"250000"   |"300000"   |"0"   |"2258"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"3115128152"   |"dandresabogadog@mail.com"   |"Efectivo"    |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86379"|

  @AnalisisCreditoRetanqueoCCS
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con compra de cartera y saneamiento <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><Cartera1><Saneamiento2><DiasHabilesIntereses><Tasa>
    Y Guarda los datos del simulador
    Y ingrese a la pestana del plan de pagos
    Y valide la informacion cabecera con sus conceptos para Retanqueo<Tasa><Plazo>
    Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>
    Ejemplos:
      | Retanqueo | Cedula     | Credito  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes       | fecha        | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCreditoCCS
   |"8000000"   |"10092369"   |"134950"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"200000"   |"300000"   |"2258"   |"450000"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"200000"   |"250000"   |"3228483385"   |"dandresabogadog@mail.com"   |"Efectivo"   |"25/11/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86318"|

  @ClientesBienvenidaRetanqueos
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito <Credito>
    Y validar las condiciones de la carta de notificacion de creditos <Cedula>
    Y se marcan los chech y se acepta <TipoDesen> <Cedula>
    Entonces se ingresa el codigo OTP <Cedula>

    Ejemplos:
      | Retanqueo | Cedula     | Credito  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes       | fecha        | AnnoAfetacion |  Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCredito
   |"7500000"   |"10092369"   |"68003"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"250000"   |"300000"   |"0"   |"2258"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"3115128152"   |"dandresabogadog@mail.com"   |"Efectivo"    |"20/10/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86379"|

  @CreditosVisacionRetanqueos
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

    Ejemplos:
      | Retanqueo | Cedula     | Credito  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes       | fecha        | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCreditoCCS
   |"8000000"   |"10092369"   |"134950"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"200000"   |"300000"   |"2258"   |"450000"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"200000"   |"250000"   |"3228483385"   |"dandresabogadog@mail.com"   |"Efectivo"   |"25/11/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86318"|

  @DesembolsoRetanqueos
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto para retanqueo y se edita <Retanqueo><Banco><rutaPDF>

    Ejemplos:
      | Retanqueo | Cedula     | Credito  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | codigo | VlrCompraSaneamiento | NombreCredito | Mes       | fecha        | AnnoAfetacion | Cartera1 | Saneamiento2 | Celular      | Correo                     | TipoDesen  | fechaActual  | Banco                                  | NumRadicadoCredito |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoDigiCreditoCCS
   |"8000000"   |"10092369"   |"134950"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"50"   |"50"   |"8700000"   |"200000"   |"300000"   |"2258"   |"450000"   |"OSCAR"   |"Octubre"   |"14/06/1969"   |"2021"   |"200000"   |"250000"   |"3228483385"   |"dandresabogadog@mail.com"   |"Efectivo"   |"25/11/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"86318"|
