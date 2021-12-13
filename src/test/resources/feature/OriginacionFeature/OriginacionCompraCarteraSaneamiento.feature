#language: es
Característica: Solicitud combra de cartera

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada
    Y con las funciones sql necesarias del proyecto creadas

  @OriginacionCCS
  Esquema del escenario: Simulador Asesor
    Cuando el agente ingresa a la pestana de simulador asesor
    # Y cambia la fecha del servidor <FechaServidor>
    Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria> <Cedula> <fecha> <Oficina> <Actividad> <Tasa> <Plazo> <Monto> <DiasHabilesIntereses> <Ingresos> <descLey> <descNomina> <vlrCompasSaneamientos> <tipo> <colchon>
    Y valida los calculos correctos de la simulacion<fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><Pagaduria>
    Y guarda la simulacion presionando el boton guardar
    Entonces se permite crear el cliente <TipoContrato><FechaIngreso><Pnombre><Papellido><Sapellido><Correo><Celular><Dpto><Ciudad>
    Y el sistema habilita el cargue de documentos para el cliente <rutaPDF>
    Y se finaliza con la consulta a centrales <Cedula>
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|

  @SolicitudCreditoCCS
  Esquema del escenario: Solicitar credito con compra de cartera y saneamientos
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon><rutaPDF>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <Ingresos><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se crean los tipos de cartera o saneamiento a recoger
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Cartera     | 8600068225 - ACORE                          | 100000 |    70000 | 30/11/2021       |         21236 |
      | Cartera     | 8600068225 - ACORE                          | 100000 |    70000 | 30/11/2021       |         21236 |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 830000 |    70000 | 30/11/2021       |         29123 |
    Y se guarda cartera
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 830000 |    70000 | 30/11/2021       |         29123 |
      | Cartera     | 8600068225 - ACORE                          | 100000 |    70000 | 30/11/2021       |         21236 |
      | Cartera     | 8600068225 - ACORE                          | 100000 |    70000 | 30/11/2021       |         21236 |
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <Codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
    Ejemplos:
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|

  @AnalisisCreditoCCS
  Esquema del escenario: Analisis del credito con compra de cartera y saneamiento
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador para compra de cartera con saneamiento <Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><Cartera1><Saneamiento2><AnoAnalisis><fechaDesembolso>
    Y Guarda los datos del simulador
    Y ingrese a la pestana del plan de pagos
    Y valide la informacion cabecera con sus conceptos para OriginacionCCS<Tasa><Plazo>
    Y Validacion de saldo a capital en el desgloce del plan de pagos con el ultimo<Plazo>
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  @ClientesBienvenidaCCS
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito
   # Y validar las condiciones de la carta de notificacion de creditos originacion <Cedula>
    Y se marcan los chech y se acepta carteras y saneamientos <TipoDesen><Cedula>
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  @CreditosVisacionCCS
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  #En la tabla interna en el valor monto, se agrega la suma de las carteras que se agrego en la solicitud del credito
  @DesembolsoCarteraCCS
  Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Cartera" con <Cedula>
    Y se descargan medios de dispersion para la cartera
    |Monto|Banco																|RutaPdf|
    |200000	|Remanentes - 60237038927 - REMANENTE	|src/test/resources/Data/PDFPRUEBA.pdf|
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  @VisacionCarteraCCS
  Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  #En el Valor monto se suman los valores de los saneamientos agregados en la solicitud del credito
  @DesembolsoSaneamientoCCS
  Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Saneamiento" con <Cedula>
    Y se descargan medios de dispersion para la cartera
    |Monto|Banco																|RutaPdf|
    |830000	|Remanentes - 60237038927 - REMANENTE	|src/test/resources/Data/PDFPRUEBA.pdf|
    Ejemplos: 
      | Pagaduria | Cedula | fecha | Oficina | Actividad | Tasa | Plazo | Monto | DiasHabilesIntereses | Ingresos | descLey | descNomina | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato | FechaIngreso | NombreCredito | Pnombre | Snombre | Papellido | Sapellido | Correo | Celular | Dpto | Ciudad | rutaPDF | DestinoCredito | Sexo | EstadoCivil | Direccion | TipoVivienda | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo | Mes | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso | TipoDesen | fechaActual | Banco | entidad | AccountingSource | AccountingName | FechaRegistro | NumRadicado |
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  #En el campo monto va el valor del credito, Cartera suma de las carteras, Saneamiento Summa de saneamientos o cero si no tiene estas ultimas
  @DesembolsoCCS
  Esquema del escenario: Remanente para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
    Y se descarga medios de dispersion para el remanente
    |Monto|Cartera|Saneamiento|Banco|RutaPdf|
    |20000000|200000|830000|Remanentes - 60237038927 - REMANENTE	|src/test/resources/Data/PDFPRUEBA.pdf|
    Ejemplos: 
      |Pagaduria	|Cedula		|fecha|Oficina	|	Actividad		|Tasa	|	Plazo	|Monto	|	DiasHabilesIntereses|	Ingresos	|descLey	|descNomina|	TotalActivos	|vlrCompasSaneamientos	|tipo	|colchon	 |	TipoContrato|	FechaIngreso|	NombreCredito|	Pnombre	|Snombre|		Papellido|	Sapellido	|Correo|	Celular|	Dpto|	Ciudad		|rutaPDF|	DestinoCredito		|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda|	PapellidoReferencia	|PnombreReferencia|	TelefonoResidencia|	TelefonoTrabajo	|Codigo	|Mes	|Cartera1	|Saneamiento2|	AnoAnalisis|	fechaDesembolso|	TipoDesen|	fechaActual|	Banco	|entidad	|AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
  ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
  
  @ValidarDinamicasContablesOriginacion
  Esquema del escenario: Validar dinamicas contables ORIGINACION CREDITO
    Cuando el sistema valida por <NumRadicado> y <Cedula> en la tabla movimiento contable las <AccountingSource> que se proceso por el bridge en la <FechaRegistro>
    Y valide la causacion de movimientos <AccountingSource> con sus tipos y valores usando el <NumRadicado> en la <FechaRegistro>
    Y valida que las cuentas de libranzas <AccountingSource> sean las del bridge <AccountingName> con el <NumRadicado> y <Cedula> en la <FechaRegistro>
    Entonces finalmente se valida la transaccion <AccountingSource> con <FechaRegistro> en la base de datos de PSL con el <NumRadicado>
    Ejemplos: 
      |Pagaduria	|Cedula		|fecha|Oficina	|	Actividad		|Tasa	|	Plazo	|Monto	|	DiasHabilesIntereses|	Ingresos	|descLey	|descNomina|	TotalActivos	|vlrCompasSaneamientos	|tipo	|colchon	 |	TipoContrato|	FechaIngreso|	NombreCredito|	Pnombre	|Snombre|		Papellido|	Sapellido	|Correo|	Celular|	Dpto|	Ciudad		|rutaPDF|	DestinoCredito		|	Sexo|	EstadoCivil|	Direccion|	TipoVivienda|	PapellidoReferencia	|PnombreReferencia|	TelefonoResidencia|	TelefonoTrabajo	|Codigo	|Mes	|Cartera1	|Saneamiento2|	AnoAnalisis|	fechaDesembolso|	TipoDesen|	fechaActual|	Banco	|entidad	|AccountingSource|	AccountingName	|FechaRegistro|	NumRadicado|
	##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@OriginacionCCS
   |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"   |"9777757"   |"17/Mar/1956"   |"Bogotá Centro"   |"Pensionado"   |"1.8"   |"90"   |"20000000"   |"25"   |"4500000"   |"360000"   |"50000"   |"70500000"   |"930000"   |"xx"   |"0"   |"Pensionado por Tiempo (Vejez)"   |"10/01/2009"   |"CARLOS"   |"CARLOS"   |""   |"HERRERA"   |"ARBOLEDA"   |"carlos123@mail.com"   |"3125117717"   |"Tolima"   |"Espinal"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"Educacion propia"   |"M"   |"Soltero"   |"Calle 2d #22-52"   |"FAMILIAR"   |"perez"   |"alejandro"   |"7210273"   |"9007146"   |"3112"   |"Noviembre"   |"200000"   |"830000"   |"2021"   |"13/12/2021"   | "Efectivo"    |"13/Dic/2021"   |"Remanentes - 60237038927 - REMANENTE"   |"8300538122 -  FIDEICOMISO SOLUCIONES"   |"'ACRED','EGRESO'"    |"upper('Desembolso egreso'), upper('Desembolso activación de crédito')"   |29/10/2021   |null|
