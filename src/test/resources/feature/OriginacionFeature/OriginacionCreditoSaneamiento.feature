#language: es
Característica: Originación de Creditos con saneamientos

  Antecedentes:
  Dado Un agente en el sistema core abacus con sesion iniciada

  @SimuladorAsesor
  Esquema del escenario: Simulador Asesor
    Cuando el agente ingresa a la pestana de simulador asesor
     #Y cambia la fecha del servidor <FechaServidor>
     Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria><Cedula><fecha><Oficina><Actividad><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
     #Y valida los calculos correctos de la simulacion<fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><Pagaduria>
     Y guarda la simulacion presionando el boton guardar
     Entonces se permite crear el cliente <TipoContrato><FechaIngreso><Pnombre><Papellido><Sapellido><Correo><Celular><Dpto><Ciudad>
     Y el sistema habilita el cargue de documentos para el cliente <rutaPDF>
     Y se finaliza con la consulta a centrales
  Ejemplos:
      | Pagaduria                                          | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                       | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                      | entidad                                                    | cartera | vlr_cuota |fecha_vencimiento |num_obligacion| entidad_san                                 | saneamiento | vlr_cuota_san |fecha_vencimiento |num_obligacion_san|
      | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "28378037" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120"  | "8000000"   | "35"                 | "4000000" | "360000" | "50000"    | "4500000"   | "20500000"   | "350000"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "HILDA"  | "QUIÑONES"  | "MACIAS"   | "hildaui@hotmail.com"        | "3104884378" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "28378037" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "HILDA"       | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Agosto" | "Efectivo" | "23/07/2021" | "Remanentes - 60237038927 - REMANENTE"     |"8300538122 -  FIDEICOMISO SOLUCIONES"                      |"500000" |"50000"    |"31/07/21"        |"1223"        |"8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA"|"382600"     |"10000"        |"31/07/21"        |"1607"            |
     #| "COLFONDOS"                                        | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"   | "20000000"  | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"       | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE"     |
     
  @SolicitudCredito
  Esquema del escenario: Solicitar credito con Saneamiento
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <IngresosMes><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se pasa a la segunda pestana de digitalizacion se agrega el saneamiento <entidad_san><vlrCompasSaneamientos><vlr_cuota_san><fecha_vencimiento><num_obligacion_san>
    Y se selecciona el tipo de cartera saneamiento
    Y guarda el saneamiento agregado    
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad saneamiento <entidad_san>
    Y se confirma el numero de obligacion <num_obligacion_san>
    Y se aprueba el saneamiento
    Y se guarda en la primera pestana de referenciacion
    Y se pasa a la segunda pestana de digitalizacion para agregar el codigo proforences aprueba referencias<Codigo>
    Y se pasa a la segunda pestana de digitalizacion y seleciona el saneamiento
    Y se marca identidida confirmada para radicar la solicitud
    Entonces se realiza la solicitud del analisis

  Ejemplos: 
      | Pagaduria                                          | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                       | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                      | entidad                                                    | cartera | vlr_cuota |fecha_vencimiento |num_obligacion| entidad_san                                      |  vlr_cuota_san |fecha_vencimiento |num_obligacion_san|
      | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "28378037" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120"  | "8000000"   | "35"                 | "4000000" | "360000" | "50000"    | "4500000"   | "20500000"   | "350000"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "HILDA"  | "QUIÑONES"  | "MACIAS"   | "hildaui@hotmail.com"        | "3104884378" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "28378037" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "HILDA"       | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Agosto" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE"     |"8300538122 -  FIDEICOMISO SOLUCIONES"                      |"500000" |"50000"    |"30/08/21"        |"1223"        |"8600370136 - COMPAÑIA MUNDIAL DE SEGUROS SA"|"10000"        |"30/07/21"        |"1607"            |
     #| "COLFONDOS"                                        | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"   | "20000000"  | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"       | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE"     |
 
  @AnalisisCredito
  Esquema del escenario: Analisis del Credito con Saneamiento
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador<Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><vlrCompasSaneamientos><AnoAnalisis>  
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>
 
  Ejemplos: 
     | Pagaduria                                          | Cedula     | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos |NombreCredito |Mes|AnoAnalisis|
     | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"  | "28378037" | "1.8" | "120"  | "8000000"   | "35"                 | "4000000" | "360000" | "50000"    | "4500000"   | "20500000"   | "350000"              | "HILDA"      |"Agosto"|"2022"|
 
  @ClientesBienvenida 
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check corretos junto con el celular correo para saneamiento <Celular><Correo>
    #Y se validan los valores de las condiciones del credito
    Entonces se pasa a la pestana condiciones de credito se marcan los check acepta saneamiento y se acepta condiciones<TipoDesen>

  Ejemplos: 
      | Cedula     | Celular         | Correo         | TipoDesen    | 
     | "28378037" | "3104884378" | "hildaui@hotmail.com" | "Efectivo" |
     
     
  @CreditosVisacion
  Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>
    
  Ejemplos: 
      | Cedula     | fechaActual         | rutaPDF         | 
      | "28378037" | "16/07/2021" 			 | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"  | 
     
    
  @DesembolsoSaneamiento
  Esquema del escenario: Saneamiento para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el saneamiento <Cedula>
    Y se descargadescargan medios de dispersion para el saneamiento <vlrCompasSaneamientos><Banco><rutaPDF>

  Ejemplos: 
      | Cedula     | Monto       | vlrCompasSaneamientos | rutaPDF                                     |Banco                                      |
      | "28378037" |"8000000"    |  "350000"                  | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" |"Remanentes - 60237038927 - REMANENTE"     |
     
     
  @Desembolso
  Esquema del escenario: Remanente para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar el remanente <Cedula>
    Y se descarga medios de dispersion para el remanente <Monto><vlrCompasSaneamientos><Banco><rutaPDF>

  Ejemplos: 
      | Cedula     | Monto       | vlrCompasSaneamientos | rutaPDF                                     |Banco                                      | entidad                                                    |
      | "28378037" |"8000000"    |  "350000"                  | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" |"Remanentes - 60237038927 - REMANENTE"     |"8300538122 -  FIDEICOMISO SOLUCIONES"                      |
     
  