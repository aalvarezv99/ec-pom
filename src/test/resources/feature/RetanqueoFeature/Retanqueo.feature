#language: es
Característica: Retanqueo de creditos

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada
    Y con las funciones sql necesarias del proyecto creadas

  @Retanqueo
  Esquema del escenario: Retanqueo libre inversion
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
    #Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses><VlrCompraSaneamiento>
    Y se da clic en solicitar
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se confirma identidad en digitalizacion <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Y se aprueban las referencias de la pagaduria
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|

  @AnalisisCreditoRetanqueo
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><DiasHabilesIntereses><Tasa><VlrCompraSaneamiento>
    Y Guarda los datos del simulador
    Y ingrese a la pestana del plan de pagos
    #Y valide la informacion cabecera con sus conceptos para Retanqueo<Tasa><Plazo>
    #Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|

  @ClientesBienvenidaRetanqueos
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    #Y se validan los valores de las condiciones del credito <Credito><Plazo><DiasHabilesIntereses>
    #Y validar las condiciones de la carta de notificacion de creditos <Cedula>
    Y se marcan los chech y se acepta <TipoDesen> <Cedula>

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|

  @CreditosVisacionRetanqueos
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|

  @DesembolsoRetanqueos
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto para retanqueo y se edita <Retanqueo><Banco><rutaPDF>
    Y se valida el estado del credito padre <Credito><FechaRegistro>

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|

  @ValidarDinamicasContablesRetanqueoHijo
  Esquema del escenario: Validar dinamicas contables Retanqueo Hijo
    Cuando se valida por <NumRadicado> y <Cedula> en la tabla movimiento contable las <AccountingSourceHijo> que se proceso por el bridge en la <FechaRegistro>
    Y valide la causacion de movimientos <AccountingSourceHijo> con sus tipos y valores usando el <NumRadicado> en la <FechaRegistro>
    Y validando las cuentas de libranzas <AccountingSourceHijo> sean las del bridge <AccountingNameHijo> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion <AccountingSourceHijo> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|

  @ValidarDinamicasContablesRetanqueoPadre
  Esquema del escenario: Validar dinamicas contables Retanqueo Padre
    Cuando se valida por <Credito> y <Cedula> en la tabla movimiento contable las <AccountingSourcePadre> que se proceso por el bridge en la <FechaRegistro>
    Y valide la causacion de movimientos <AccountingSourcePadre> con sus tipos y valores usando el <Credito> en la <FechaRegistro>
    Y validando las cuentas de libranzas <AccountingSourcePadre> sean las del bridge <AccountingNamePadre> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion <AccountingSourcePadre> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>

    Ejemplos: 
      | Retanqueo  | Cedula    | Credito | rutaPDF                                 | Tasa   | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes     | fecha        | AnnoAfetacion | Celular      | Correo                      | TipoDesen  | fechaActual  | Banco                                  | AccountingSourcePadre | AccountingNamePadre              | AccountingSourceHijo | AccountingNameHijo                                                      | FechaRegistro | NumRadicado |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@Retanqueo
   |"20000000"   |"7505895"   |"52975"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.70"   |"30"   |"120"   |"6500000"   |"300000"   |"100000"   |"0"   |"2258"   |"Camilo"   |"Enero"   |"03/09/1967"   |"2022"   |"3204992496"   |"jvcutilidades@hotmail.com"   |"Efectivo"    |"19/01/2022"   |"Bancolombia Remanentes - 60237038927 - REMANENTE"   |"'RETANQ'"   |"upper('Retanqueo de créditos')"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |"24/01/2022"   |null|
