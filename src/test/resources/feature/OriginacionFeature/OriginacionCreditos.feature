#language: es
Característica: Originacion de Creditos

  Antecedentes:  
  Dado Un agente en el sistema core abacus con sesion iniciada                                       

  
  @SimuladorAsesor
  Esquema del escenario: Simulador Asesor
    Cuando el agente ingresa a la pestana de simulador asesor
     #Y cambia la fecha del servidor <FechaServidor>
     Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria><Cedula><fecha><Oficina><Actividad><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
     #Y valida los calculos correctos de la simulacion<fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
     Y guarda la simulacion presionando el boton guardar
     Entonces se permite crear el cliente <TipoContrato><FechaIngreso><Pnombre><Papellido><Sapellido><Correo><Celular><Dpto><Ciudad>
     Y el sistema habilita el cargue de documentos para el cliente <rutaPDF>
     Y se finaliza con la consulta a centrales
   Ejemplos: 
       | Pagaduria   | Cedula     | fecha         | Oficina     | Actividad    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                     | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor |                                
       | "COLFONDOS" | "93239420" | "02/Jun/1985" | "Cartagena" | "Pensionado" | "1.8" | "120"  | "3600000"  | "10"                 | "3600000" | "360000" | "0"    | "3600000"   | "20500000"   | "360000"                   | "xx" | "360000"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "CARLOS" | "ROJAS" | "OLAYA" | "CARLOSmail@mail.com" | "3115128156" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  |
  
  @SolicitudCredito
  Esquema del escenario: Solicitar credito sin saneamientos
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <IngresosMes><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se pasa a la segunda pestana de digitalizacion se agrega el codigo proforences aprueba referencias<Codigo>
    Y se marca identidida confirmada radicando la solicitud
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis

        Ejemplos: 
      | Cedula     | fecha         | Oficina     | Actividad    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                     | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |
      | "93239420" | "02/Jun/1985" | "Cartagena" | "Pensionado" | "1.8" | "120"  | "3600000"  | "10"                 | "3600000" | "360000" | "0"    | "3600000"   | "20500000"   | "360000"                   | "xx" | "360000"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "CARLOS" | "ROJAS" | "OLAYA" | "CARLOSmail@mail.com" | "3115128156" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "93239420" | "02/Jun/1985" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |
     
  @AnalisisCredito
  Esquema del escenario: Analisis del credito
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador<Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><vlrCompasSaneamientos><AnoAnalisis>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

      Ejemplos: 
      | Pagaduria  | Cedula     | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos |NombreCredito |Mes|AnoAnalisis|
      | "COLFONDOS" | "93239420" | "1.8" | "120"  | "3600000"  | "10"                 | "3600000" | "360000" | "0"    | "3600000"   | "20500000"   | "0"                   | "CARLOS"      |  "Agosto" |"2022"|

  
  @ClientesBienvenida 
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
    Y se validan los valores de las condiciones del credito
    Entonces se pasa a la pestana condiciones de credito se marcan los chech y se acepta<TipoDesen>

       Ejemplos: 
      | Cedula     |Celular         | Correo         | TipoDesen    |
      | "93239420" |"3115128156" | "CARLOSmail@mail.com" | "Efectivo" |

  @CreditosVisacion
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

        Ejemplos: 
      | Cedula     |  fechaActual         | rutaPDF         |
      |"93239420" |  "24/08/2021" 			  | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"  |

  @Desembolso
  Esquema del escenario: Creditos para Desembolso
    Cuando el agente ingresa a la pestana Desembolso lista de pagos <Cedula>
    Y se marca el check aprobando el proceso de pagos
    Y se filtra por monto y se edita <Monto><Banco><rutaPDF>

      Ejemplos: 
      | Cedula     | Monto      | rutaPDF 																		| Banco  | 
      | "93239420" | "3600000"  | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" |"Remanentes - 60237038927 - REMANENTE" |
