#language: es
Característica: Originacion de Creditos 

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @SimuladorAsesor
  Esquema del escenario: Simulador Asesor
    Cuando el agente ingresa a la pestana de simulador asesor
    # Y cambia la fecha del servidor <FechaServidor>
    Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria> <Cedula> <fecha> <Oficina> <Actividad> <Tasa> <Plazo> <Monto> <DiasHabilesIntereses> <Ingresos> <descLey> <descNomina> <vlrCompasSaneamientos> <tipo> <colchon>
    Y valida los calculos correctos de la simulacion<fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
    Y guarda la simulacion presionando el boton guardar
    Entonces se permite crear el cliente <TipoContrato><FechaIngreso><Pnombre><Papellido><Sapellido><Correo><Celular><Dpto><Ciudad>
    Y el sistema habilita el cargue de documentos para el cliente <rutaPDF>
    Y se finaliza con la consulta a centrales <Cedula>

    Ejemplos: 
   |Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
   ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
 @SolicitudCredito
  Esquema del escenario: Solicitar credito sin saneamientos
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon><rutaPDF>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <Ingresos><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences aprueba referencias<Codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos: 
   |Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
  	##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
   
  @AnalisisCredito
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador<Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><vlrCompasSaneamientos><AnoAnalisis><fechaDesembolso>
    Y Guarda los datos del simulador
    Y ingrese a la pestana del plan de pagos 
    Y valide la informacion cabecera con sus conceptos para Originacion<Tasa><Plazo>
    Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos: 
   | Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
    ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
   
  @ClientesBienvenida
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito
    Y validar las condiciones de la carta de notificacion de creditos originacion <Cedula>
    Y se marcan los chech y se acepta el detalle originacion<TipoDesen><Cedula>

    Ejemplos: 
  |  Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
    ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  @CreditosVisacion
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
     Ejemplos: 
    Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
	##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|

  @Desembolso
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto y se edita <Monto><Banco><rutaPDF>
      Ejemplos: 
  | Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
    ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
   
  @ValidarDinamicasContablesOriginacion
  Esquema del escenario: Validar dinamicas contables ORIGINACION CREDITO
    Cuando el sistema valida por <NumRadicado> y <Cedula> en la tabla movimiento contable las <AccountingSource> que se proceso por el bridge en la <FechaRegistro>
   	Y valide la causacion de movimientos <AccountingSource> con sus tipos y valores usando el <NumRadicado> en la <FechaRegistro>
    Y valida que las cuentas de libranzas <AccountingSource> sean las del bridge <AccountingName> con el <NumRadicado> y <Cedula> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion <AccountingSource> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>
    Ejemplos: 
     |Pagaduria	|Cedula	|fecha	|Tasa	|Plazo|	Monto	|DiasHabilesIntereses|	Ingresos	|descLey|	descNomina|	Mes|	fechaDesembolso|	NombreCredito|	Pnombre|	Snombre	|Papellido	|Sapellido|	fechaActual|	Oficina	|Actividad|	TotalActivos|	vlrCompasSaneamientos|	tipo|	colchon|	TipoContrato|	FechaIngreso|	Correo|	Celular	|Dpto|	Ciudad|	rutaPDF|	FechaServidor|	DestinoCredito|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda	|PapellidoReferencia|	PnombreReferencia|	TelefonoResidencia	|TelefonoTrabajo|	Codigo|	AnoAnalisis|	TipoDesen	|Banco|	AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCredito
   |"COLFONDOS"   |"9777757"   |"17/Mar/1956"   |"1.8"   |"36"   |"20000000"   |"21"   |"6500000"   |"480000"   |"90000"   |"Noviembre"   |"05/10/2021"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"30/Sep/2021"   |"Cartagena"   |"Pensionado"   |"20500000"   |"0"   |"xx"   |"360000"   |"Pensionado por Tiempo (Vejez)"   |"10/03/2000"   |"prueba123@gmail.com"   |"3125127717"   |"Cundinamarca"   |"Anapoima"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"2021-04-20"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"2021"   |"Efectivo"   |"Remanentes - 60237038927 - REMANENTE"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
