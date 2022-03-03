#language: es
Característica: Retanqueo de creditos

  Antecedentes:
    Dado Un agente en el sistema core abacus con sesion iniciada
    Y con las funciones sql necesarias del proyecto creadas

  @RetanqueoMultipleSeleccion
  Esquema del escenario: Seleccion Pantalla Principal Retanqueo Multiple
    Cuando El agente ingrese a la pestana retanqueo
    Y se filtra por <Cedula> <Pagaduria> para retanqueo multiple
    Y se da clic a retanquear a todos los creditos
    Y se ingresa el monto a solicitar <Retanqueo>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @SolicitudRetanqueoMultiple
  Esquema del escenario: Solicitud Retanqueo Multiple
    Cuando se busca el credito por <Cedula>
    Y se selecciona el retanqueo
    Y borrar archivos
    Y cargar archivos nuevos <rutaPDF>
    Y se solicita la consulta a centrales de riesgo
    Y marcar el credito viable
    Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina><VlrCompraSaneamiento>
    Y se validan los datos del simulador retanqueo multiple <Cedula><Pagaduria><Ingresos><descLey><descNomina><Tasa><Plazo><DiasHabilesIntereses><VlrCompraSaneamiento>
    Y se da clic en solicitar
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se confirma identidad en digitalizacion <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Y se aprueban las referencias de la pagaduria
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|


  #COMPRA CARTERA Y SANEAMIENTOS
  @SolicitudRetanqueoMultipleCCS
  Esquema del escenario: Retanqueo Multiple Compra de cartera y saneamiento
    Cuando se busca el credito por <Cedula>
    Y se selecciona el retanqueo
    Y borrar archivos
    Y cargar archivos nuevos <rutaPDF>
    Y se solicita la consulta a centrales de riesgo
    Y marcar el credito viable
    Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina><VlrCompraSaneamiento>
    Y se validan los datos del simulador retanqueo multiple <Cedula><Pagaduria><Ingresos><descLey><descNomina><Tasa><Plazo><DiasHabilesIntereses><VlrCompraSaneamiento>
    Y se da clic en solicitar
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se crean los tipos de cartera o saneamiento a recoger
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/10/2021       | 21236         |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 730000 | 70000    | 30/10/2021       | 29123         |
    Y se guarda cartera
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 730000 | 70000    | 30/10/2021       | 29123         |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/10/2021       | 21236         |
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @AnalisisCreditoRetanqueoMultiple
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueo multiple <Cedula><Pagaduria><DiasHabilesIntereses><AnnoAfetacion><Retanqueo><fechaActual><Mes><Plazo><Tasa><VlrCompraSaneamiento>
    Y Guarda los datos del simulador
    Y ingrese a la pestana del plan de pagos
    Y valide la informacion cabecera con sus conceptos para Retanqueo<Tasa><Plazo>
    Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @AnalisisCreditoRetanqueoMultipleCCS
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con compra de cartera y saneamiento retanqueo multiple <AnnoAfetacion><Retanqueo><fechaActual><Mes><Plazo><Cartera1><Saneamiento2><Tasa>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @ClientesBienvenidaRetanqueoMult
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito Multiple <Cedula><Pagaduria><Tasa><Plazo><DiasHabilesIntereses><VlrCompraSaneamiento>
    #Y validar las condiciones de la carta de notificacion de creditos <Cedula>
    Y se marcan los chech y se acepta <TipoDesen><Cedula>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @CreditosVisacionRetanqueos
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @DesembolsoRetanqueos
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto para retanqueo y se edita <Retanqueo><Banco><rutaPDF>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  #Pasos para credito despues de llamada de bienvenida cuando tienen compra de cartera y la visacion
  @DesembolsoCarteraCCS
  Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Cartera" con <Cedula>
    Y se descargan medios de dispersion para la cartera
      | Monto  | Banco                                | RutaPdf                               |
      | 100000 | Remanentes - 60237038927 - REMANENTE | src/test/resources/Data/PDFPRUEBA.pdf |

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @VisacionSaneamientoCCS
  Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @DesembolsoSaneamientoCCS
  Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Saneamiento" con <Cedula>
    Y se descargan medios de dispersion para la cartera
      | Monto  | Banco                                | RutaPdf                               |
      | 730000 | Remanentes - 60237038927 - REMANENTE | src/test/resources/Data/PDFPRUEBA.pdf |

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @DesembolsoRetanqueosCCS
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
    Y se descarga medios de dispersion para el remanente
      | Monto   | Cartera | Saneamiento | Banco                                | RutaPdf                               |
      | 5000000 | 100000  | 730000      | Remanentes - 60237038927 - REMANENTE | src/test/resources/Data/PDFPRUEBA.pdf |

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @ValidarDinamicasContablesRetanqueoHijo
  Esquema del escenario: Validar dinamicas contables Retanqueo Hijo
    Cuando se valida por <NumRadicado> y <Cedula> en la tabla movimiento contable las <AccountingSourceHijo> que se proceso por el bridge en la <FechaRegistro>
    Y valide la causacion de movimientos <AccountingSourceHijo> con sus tipos y valores usando el <NumRadicado> en la <FechaRegistro>
    Y validando las cuentas de libranzas <AccountingSourceHijo> sean las del bridge <AccountingNameHijo> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion <AccountingSourceHijo> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|

  @ValidarDinamicasContablesRetanqueoPadre
  Esquema del escenario: Validar dinamicas contables Retanqueo Padre
    Cuando se valida los creditos multiples en la tabla movimiento contables <AccountingSourcePadre><Cedula><Pagaduria><FechaRegistro>
    Y valide la causacion de movimientos con sus tipos y valores <AccountingSourcePadre><FechaRegistro>
    Y validando las cuentas de libranzas <AccountingSourcePadre> sean las del bridge <AccountingNamePadre> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion en la base de datos de PSL <AccountingSourcePadre><FechaRegistro>

    Ejemplos:
      | Cedula     | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito      | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                              | Cartera1 | Saneamiento2 | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                        | NumRadicado | FechaRegistro |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"12960211"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"   |"GERARDO BENJAMIN"   |"Febrero"   |"05/03/2022"   |"2022"   |"01/02/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"0"   |"0"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'''   |"'upper('Desembolso egreso'), upper('Desembolso activación de crédito')'"   |null   |"01/02/2022"|
