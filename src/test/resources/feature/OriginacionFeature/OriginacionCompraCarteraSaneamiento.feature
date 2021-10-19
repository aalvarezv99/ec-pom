#language: es
Característica: Solicitud combra de cartera

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @SimuladorAsesorCCS
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
      | Pagaduria                                          | Cedula    | fecha         | Oficina         | Actividad    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo | colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido | Sapellido  | Correo               | Celular      | Dpto     | Ciudad    | rutaPDF                                 |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@SimuladorAsesorCCS
      | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "9777757" | "17/Mar/1956" | "Bogotá Centro" | "Pensionado" | "1.8" | "36"  | "20000000" | "25"                 | "4500000" | "360000" | "50000"    | "3000000"   | "70500000"   | "930000"              | "xx" | "0"     | "Pensionado por Tiempo (Vejez)" | "10/01/2009" | "CARLOS" | "HERRERA" | "ARBOLEDA" | "carlos123@mail.com" | "3125117717" | "Tolima" | "Espinal" | "src/test/resources/Data/PDFPRUEBA.pdf" |

  @SolicitudCreditoCCS
  Esquema del escenario: Solicitar credito con compra de cartera y saneamientos
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon><rutaPDF>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <IngresosMes><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se crean los tipos de cartera o saneamiento a recoger
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/09/2021       | 21236         |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 830000 | 70000    | 30/09/2021       | 29123         |
    Y se guarda cartera
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 830000 | 70000    | 30/09/2021       | 29123         |
      | Cartera     | 8600068225 - ACORE                          | 100000 | 70000    | 30/09/2021       | 21236         |
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <Codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

    Ejemplos: 
      | Cedula    | NombreCredito | fecha         | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | vlrCompasSaneamientos | tipo | colchon | rutaPDF                                 | DestinoCredito     | Sexo | EstadoCivil | Direccion         | Dpto           | Ciudad     | TipoVivienda | Correo                | Celular      | IngresosMes | TotalActivos | PapellidoReferencia | PnombreReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@SolicitudCreditoCCS
      | "9777757" | "CARLOS"      | "17/Mar/1956" | "1.8" | "36"  | "20000000" | "25"                 | "4500000" | "360000" | "50000"    | "930000"              | "xx" | "0"     | "src/test/resources/Data/PDFPRUEBA.pdf" | "Educacion propia" | "M"  | "Soltero"   | "Calle 2d #22-52" | "Cundinamarca" | "Anapoima" | "FAMILIAR"   | "prueba123@gmail.com" | "3125127117" | "6500000"   | "20500000"   | "perez"             | "alejandro"       | "7210273"          | "9007146"       | "3112" |

  @AnalisisCreditoCCS
  Esquema del escenario: Analisis del credito con compra de cartera y saneamiento
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador para compra de cartera con saneamiento <Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><Cartera1><Saneamiento2><AnoAnalisis><fechaDesembolso>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

    Ejemplos: 
      | Cedula    | NombreCredito | Ingresos  | descLey  | descNomina | Mes         | Monto      | Tasa  | Plazo | Pagaduria   | Cartera1 | Saneamiento2 | AnoAnalisis | fechaDesembolso |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@AnalisisCreditoCCS
      | "9777757" | "CARLOS"      | "6500000" | "480000" | "90000"    | "Noviembre" | "20000000" | "1.8" | "36"  | "COLFONDOS" | "100000" | "830000"     | "2021"      | "19/10/2021"    |

  @ClientesBienvenidaCCS
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito
    Y validar las condiciones de la carta de notificacion de creditos originacion <Cedula>
    Y se marcan los chech y se acepta carteras y saneamientos <TipoDesen><Cedula>

    Ejemplos: 
      | Cedula    | Celular      | Correo                 | TipoDesen  |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@ClientesBienvenidaCCS
      | "9777757" | "3125127117" | "apqwerty250@mail.com" | "Efectivo" |

  @CreditosVisacionCCS
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

    Ejemplos: 
      | Cedula    | fechaActual   | rutaPDF                                 |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@CreditosVisacionCCS
      | "9777757" | "19/Oct/2021" | "src/test/resources/Data/PDFPRUEBA.pdf" |

  @DesembolsoCarteraCCS
  Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar la cartera uno <Cedula>
    Y se descargan medios de dispersion para la cartera <Cartera1><Banco><rutaPDF>

    Ejemplos: 
      | Cedula    | Monto      | Cartera1 | rutaPDF                                 | Banco                                  |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@DesembolsoCarteraCCS
      | "9777757" | "20000000" | "100000" | "src/test/resources/Data/PDFPRUEBA.pdf" | "Remanentes - 60237038927 - REMANENTE" |

  @VisacionCarteraCCS
  Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>

    Ejemplos: 
      | Cedula    | rutaPDF                                 |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@VisacionCarteraCCS
      | "9777757" | "src/test/resources/Data/PDFPRUEBA.pdf" |

  @DesembolsoSaneamientoCCS
  Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el saneamiento <Cedula>
    Y se descargan medios de dispersion para el saneamiento <Saneamiento2><Banco><rutaPDF>

    Ejemplos: 
      | Cedula    | Monto      | Saneamiento2 | rutaPDF                                 | Banco                                  |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@DesembolsoSaneamientoCCS
      | "9777757" | "20000000" | "830000"     | "src/test/resources/Data/PDFPRUEBA.pdf" | "Remanentes - 60237038927 - REMANENTE" |

  @DesembolsoCCS
  Esquema del escenario: Remanente para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
    Y se descarga medios de dispersion para el remanente <Monto><Cartera1><Saneamiento2><Banco><rutaPDF>

    Ejemplos: 
      | Cedula    | Monto      | Cartera1 | Saneamiento2 | rutaPDF                                 | Banco                                  | entidad                                |
      ##@externaldata@./src/test/resources/Data/AutomationDataOriginacion.xlsx@DesembolsoCCS
      | "9777757" | "20000000" | "100000" | "830000"     | "src/test/resources/Data/PDFPRUEBA.pdf" | "Remanentes - 60237038927 - REMANENTE" | "8300538122 -  FIDEICOMISO SOLUCIONES" |
