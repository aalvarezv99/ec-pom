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
   |Pagaduria|Cedula|fecha|Oficina|Actividad|Tasa|Plazo|Monto|DiasHabilesIntereses|Ingresos|descLey|descNomina|IngresosMes|TotalActivos|vlrCompasSaneamientos|tipo|colchon|TipoContrato|FechaIngreso|Pnombre|Papellido|Sapellido|Correo|Celular|Dpto|Ciudad|rutaPDF|FechaServidor|
   
   ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@SimuladorAsesor
  
 @SolicitudCredito
  Esquema del escenario: Solicitar credito sin saneamientos
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon><rutaPDF>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <IngresosMes><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences aprueba referencias<Codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos: 
    |Cedula      |NombreCredito|fecha           |Tasa    |Plazo  |Monto        |DiasHabilesIntereses|Ingresos |descLey |descNomina|vlrCompasSaneamientos|tipo|colchon|rutaPDF|DestinoCredito|Sexo|EstadoCivil|Direccion|Dpto|Ciudad|TipoVivienda|Correo|Celular|IngresosMes|TotalActivos|PapellidoReferencia|PnombreReferencia|TelefonoResidencia|TelefonoTrabajo|Codigo| 			
    
  	##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@SolicitudCredito
   
  @AnalisisCredito
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador<Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><vlrCompasSaneamientos><AnoAnalisis><fechaDesembolso>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos: 
    |Cedula|NombreCredito|Ingresos|descLey|descNomina|Mes|Monto|Tasa|Plazo|Pagaduria|vlrCompasSaneamientos|AnoAnalisis|fechaDesembolso| 	
    ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@AnalisisCredito
   
  @ClientesBienvenida
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito
    Y validar las condiciones de la carta de notificacion de creditos originacion <Cedula>
    Y se marcan los chech y se acepta el detalle originacion<TipoDesen><Cedula>

    Ejemplos: 
      | Cedula     | Celular      | Correo                 | TipoDesen  |
    ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@ClientesBienvenida
  
  
  
  @CreditosVisacion
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
     Ejemplos: 
       | Cedula     | fechaActual   | rutaPDF         												 |
	##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@CreditosVisacion

   

  @Desembolso
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto y se edita <Monto><Banco><rutaPDF>
      Ejemplos: 
       | Cedula     | Monto      | rutaPDF 																 | Banco  															 | 
    ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@Desembolso
