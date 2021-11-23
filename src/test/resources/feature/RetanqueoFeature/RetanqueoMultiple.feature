#language: es
Característica: Retanqueo de creditos

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @RetanqueoMultipleSeleccion
  Esquema del escenario: Seleccion Pantalla Principal Retanqueo Multiple
    Cuando El agente ingrese a la pestana retanqueo
    Y se filtra por <Cedula> <Pagaduria> para retanqueo multiple
    Y se da clic a retanquear a todos los creditos
    #Y se ingresa el monto a solicitar <Retanqueo>
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @SolicitudRetanqueoMultiple
  Esquema del escenario: Solicitud Retanqueo Multiple
    Cuando se busca el credito por <Cedula>
    Y se selecciona el retanqueo
    Y borrar archivos
    Y cargar archivos nuevos <rutaPDF>
    Y se solicita la consulta a centrales de riesgo
    Y marcar el credito viable
    Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina><VlrCompraSaneamiento>
    Y se validan los datos del simulador retanqueo multiple <Ingresos><descLey><descNomina><Tasa><Plazo><DiasHabilesIntereses><VlrCompraSaneamiento>
    Y se da clic en solicitar
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se confirma identidad en digitalizacion <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Y se aprueban las referencias de la pagaduria
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  #COMPRA CARTERA Y SANEAMIENTOS
  @SolicitudRetanqueoMultipleCompraCarteraSaneamiento
  Esquema del escenario: Retanqueo Multiple Compra de cartera y saneamiento
    Cuando se busca el credito por <Cedula>
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
      | Cartera     | 8600068225 - ACORE                          | 100000 |    70000 | 30/10/2021       |         21236 |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 730000 |    70000 | 30/10/2021       |         29123 |
    Y se guarda cartera
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad
      | Tipo        | Entidad                                     | Monto  | VlrCuota | FechaVencimiento | NumObligacion |
      | Saneamiento | 8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA | 730000 |    70000 | 30/10/2021       |         29123 |
      | Cartera     | 8600068225 - ACORE                          | 100000 |    70000 | 30/10/2021       |         21236 |
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @AnalisisCreditoRetanqueoMultiple
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueo multiple <AnnoAfetacion><Retanqueo><fecha><Mes><Plazo><Tasa>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>
    Ejemplos: 
      | Cedula    | Retanqueo | Pagaduria          | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "8682110" | "5000000" | "P.A COLPENSIONES" | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "LUIS CARLOS" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @AnalisisCreditoRetanqueoMultipleCCS
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador retanqueos con compra de cartera y saneamiento <AnnoAfetacion><Credito><Retanqueo><fecha><Mes><Plazo><Ingresos><descLey><descNomina><Cartera1><Saneamiento2><DiasHabilesIntereses><Tasa>
    #Y Guarda los datos del simulador
    #Y Pasa a la pestana endeudamiento global aprobando
    #Y Aprueba la tarea del credito<Cedula>
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @ClientesBienvenidaRetanqueoMult
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito Multiple
    Y validar las condiciones de la carta de notificacion de creditos <Cedula>
    Y se marcan los chech y se acepta <TipoDesen> <Cedula>
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @CreditosVisacionRetanqueos
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @DesembolsoRetanqueos
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto para retanqueo y se edita <Retanqueo><Banco><rutaPDF>
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  #Pasos para credito despues de llamada de bienvenida cuando tienen compra de cartera y la visacion
  @DesembolsoCarteraCCS
  Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Cartera" con <Cedula>
    Y se descargan medios de dispersion para la cartera
      | Monto  | Banco                                | RutaPdf                               |
      | 200000 | Remanentes - 60237038927 - REMANENTE | src/test/resources/Data/PDFPRUEBA.pdf |
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @VisacionSaneamientoCCS
  Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @DesembolsoSaneamientoCCS
  Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar "Saneamiento" con <Cedula>
    Y se descargan medios de dispersion para la cartera
      | Monto  | Banco                                | RutaPdf                               |
      | 830000 | Remanentes - 60237038927 - REMANENTE | src/test/resources/Data/PDFPRUEBA.pdf |
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |

  @DesembolsoRetanqueosCCS
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
    Y se descarga medios de dispersion para el remanente
      | Monto   | Cartera | Saneamiento | Banco                                | RutaPdf                               |
      | 8000000 |  200000 |      830000 | Remanentes - 60237038927 - REMANENTE | src/test/resources/Data/PDFPRUEBA.pdf |
    Ejemplos: 
      | Cedula     | Retanqueo | Pagaduria | Credito | Celular      | Correo                     | TipoDesen  | rutaPDF                                 | Tasa  | Plazo | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | VlrCompraSaneamiento | codigo | NombreCredito    | Mes       | fecha        | AnnoAfetacion | fechaActual  | Banco                                  | Cartera1 | Saneamiento2 |
      ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
      | "12962960" | "5000000" | "FOPEP"   | "85863" | "3125117715" | "dandresabogadog@mail.com" | "Efectivo" | "src/test/resources/Data/PDFPRUEBA.pdf" | "1.8" | "90"  | "10"                 | "6500000" | "380000" | "100000"   | "0"                  | "2258" | "ROBERTO HERNAN" | "Octubre" | "14/06/1969" | "2021"        | "25/10/2021" | "Remanentes - 60237038927 - REMANENTE" | "0"      | "0"          |
