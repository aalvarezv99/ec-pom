#language: es
Característica: Originacion Compra Cartera

  Antecedentes:  
  Dado Un agente en el sistema core abacus con sesion iniciada                                       

  
  @SimuladorAsesor
  Esquema del escenario: Simulador Asesor
    Cuando el agente ingresa a la pestana de simulador asesor
     #Y cambia la fecha del servidor <FechaServidor>
     Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria><Cedula><fecha><Oficina><Actividad><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
     Y valida los calculos correctos de la simulacion<fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><Pagaduria>
     Y guarda la simulacion presionando el boton guardar
     Entonces se permite crear el cliente <TipoContrato><FechaIngreso><Pnombre><Papellido><Sapellido><Correo><Celular><Dpto><Ciudad>
     Y el sistema habilita el cargue de documentos para el cliente <rutaPDF>
     Y se finaliza con la consulta a centrales
     
       Ejemplos: 
        | Pagaduria                                          | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina |vlrCompasSaneamientos | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                     | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  |                                
        | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "79065233" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120" | "15000000"  | "25"                 | "4500000" | "360000" | "50000"    | "2000000"            |   "4500000" | "20500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "JUAN"   | "ALDANA"    | "VALENCIA" | "aldanajuan7929@gmail.com" | "3212179734" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "79065233" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "JUAN"        | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Julio" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |
       #| "COLFONDOS"                                        | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"  | "20000000"  | "21"                 | "6500000" | "480000" | "90000"    | "6500000"            | "80500000"  | "0"          | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"     | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |

  @SolicitudCredito
  Esquema del escenario: Solicitar credito con compra de cartera
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><Pagaduria>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se llenan los campos obligatorios en la pestana formulario guardando <DestinoCredito><Sexo><EstadoCivil><Direccion><Dpto><Ciudad><TipoVivienda><Correo><Celular>
    Y se agregar las referencias en la segunta pestana del formulario guardando <IngresosMes><TotalActivos><PapellidoReferencia><PnombreReferencia><Direccion><TelefonoResidencia><TelefonoTrabajo><Dpto><Ciudad>
    Y se presiona en verificacion en la pestana digitalizacion
    Y se pasa a la segunda pestana de digitalizacion se agrega la compra de cartera <entidad><cartera><vlr_cuota><fecha_vencimiento><num_obligacion>
    Y guarda la cartera agregada    
    Y se pasa a la primera pestana de referenciacion para confirmar la entidad <entidad>
    Y se confirma el numero de obligacion <num_obligacion>
    Y se aprueba la cartera
    Y se guarda en la primera pestana de referenciacion
    Y se pasa a la segunda pestana de digitalizacion para agregar el codigo proforences aprueba referencias<Codigo>
    Y se pasa a la segunda pestana de digitalizacion y seleciona la cartera
    Y se marca identidida confirmada para radicar la solicitud
    Entonces se realiza la solicitud del analisis

      Ejemplos: 
       | Pagaduria                                          | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                       | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  | entidad                               | cartera | vlr_cuota |fecha_vencimiento |num_obligacion|
       | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "79065233" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120"  | "15000000"  | "25"                 | "4500000" | "360000" | "50000"    | "4500000"   | "20500000"   | "2000000"             | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "JUAN"   | "ALDANA"    | "VALENCIA" | "aldanajuan7929@gmail.com"   | "3212179734" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "79065233" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "JUAN"        | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Julio" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |"8300538122 -  FIDEICOMISO SOLUCIONES" |"2000000"|"100000"   |"31/07/21"        |"1223"        |
      #| "COLFONDOS"                                        | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"   | "20000000"  | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"       | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |
 
 
  @AnalisisCredito
  Esquema del escenario: Analisis del credito con compra de cartera
    Cuando el agente ingresa a pestana analisis de credito busca con la cedula del cliente <Cedula><NombreCredito>
    Y ingresa los valores guardando <Ingresos><descLey><descNomina>
    Y pasa a la siguiente pestana del simulador analista
    Entonces Valida los valores del simulador para compra de cartera <Mes><Monto><Tasa><Plazo><Ingresos><descLey><descNomina><Pagaduria><cartera>
    Y Guarda los datos del simulador
    Y Pasa a la pestana endeudamiento global aprobando
    Y Aprueba la tarea del credito<Cedula>

     Ejemplos: 
      | Pagaduria                                          | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                     | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  |entidad                               | cartera | vlr_cuota |fecha_vencimiento |num_obligacion|
      | "ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "79065233" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120"  | "15000000"  | "25"                 | "4500000" | "360000" | "50000"    | "4500000"   | "20500000"   | "2000000"             | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "JUAN"   | "ALDANA"    | "VALENCIA" | "aldanajuan7929@gmail.com" | "3212179734" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "79065233" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "JUAN"        | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Julio" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |"8300538122 -  FIDEICOMISO SOLUCIONES"|"2000000"|"100000"   |"31/07/21"        |"1223"        |
     #| "COLFONDOS"                                        | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"   | "20000000"  | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"     | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |

   @ClientesBienvenida 
   Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular><Correo>
    Entonces se pasa a la pestana condiciones de credito se marcan los check acepta cartera y se acepta condiciones<TipoDesen>

     Ejemplos: 
      |Pagaduria                                          | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo  | Monto       | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                       | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  |entidad                                 | cartera | vlr_cuota |fecha_vencimiento |num_obligacion|
      |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "79065233" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120"  | "15000000"  | "25"                 | "4500000" | "360000" | "50000"    | "4500000"   | "20500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "JUAN"   | "ALDANA"    | "VALENCIA" | "aldanajuan7929@gmail.com"   | "3212179734" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "79065233" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "JUAN"        | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Julio" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |"8300538122 -  FIDEICOMISO SOLUCIONES"  |"5000000"|"100000"   |"31/07/21"        |"1223"        |
     #| "COLFONDOS"                                       | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"   | "20000000"  | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"       | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |
  
  
    @CreditosVisacion
    Esquema del escenario: Creditos para Visacion
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se marca aprobado se selecciona la fecha aprobando<fechaActual><rutaPDF>

    Ejemplos: 
     | Pagaduria                                         | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                      | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  |entidad                                 | cartera | vlr_cuota |fecha_vencimiento |num_obligacion|
     |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "79065233" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120" | "15000000" | "25"                 | "4500000" | "360000" | "50000"    | "4500000"   | "20500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "JUAN"    | "ALDANA"  | "VALENCIA" | "aldanajuan7929@gmail.com"      | "3212179734" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "79065233" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "JUAN"         | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Julio" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |"8300538122 -  FIDEICOMISO SOLUCIONES"  |"5000000"|"100000"   |"31/07/21"        |"1223"        |
    #| "COLFONDOS"                                       | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"  | "20000000" | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"      | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |
  
    @DesembolsoCartera
    Esquema del escenario: Carteras para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar la cartera <Cedula><estadopago>
    Y se descargadescargan medios de dispersion para la cartera <cartera><Banco><rutaPDF>

    Ejemplos: 
     |Pagaduria                                         |Cedula    |cartera |Banco                                            |rutaPDF                                    |estadopago  |
     |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"|"79065233"|"2000000"|"Banco de Occidente - 219856622 - COMPRA_CARTERA"|"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"Habilitado"|
  

   @VisacionCartera
    Esquema del escenario: Visacion de la cartera
    Cuando el agente ingresa a la pestana clientes para Visacion <Cedula>
    Y se navega hasta carteras <rutaPDF>

    Ejemplos: 
     | Pagaduria                                         | Cedula     | fecha         | Oficina         | Actividad    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                      | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  |entidad                                 | cartera | vlr_cuota |fecha_vencimiento |num_obligacion|
     |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS" | "79065233" | "12/Ago/1982" | "Bogotá Centro" | "Pensionado" | "1.8" | "120" | "15000000" | "25"                 | "4500000" | "360000" | "50000"    | "4500000"   | "20500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "JUAN"    | "ALDANA"  | "VALENCIA" | "aldanajuan7929@gmail.com"      | "3212179734" | "Bogotá D.C"   | "Bogota"   | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "79065233" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "JUAN"         | "Miguel"              | "Lopez"             | "7510273"          | "9807146"       | "3112" |  "Julio" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |"8300538122 -  FIDEICOMISO SOLUCIONES"  |"5000000"|"100000"   |"31/07/21"        |"1223"        |
    #| "COLFONDOS"                                       | "9777757"  | "17/Mar/1956" | "Cartagena"     | "Pensionado" | "1.8" | "36"  | "20000000" | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"      | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |
 
    @Desembolso
    Esquema del escenario: Remanente para Desembolso
    Cuando el agente ingresa a la lista de pagos para procesar la cartera <Cedula><estadopago>
    Y se descarga medios de dispersion para el remanente <Monto><cartera><Banco><rutaPDF>
    
   Ejemplos: 
    |Pagaduria                                         |Cedula    |Monto     |cartera |Banco                                            |rutaPDF                                    |estadopago  |
    |"ALCALDIA MUNICIPAL DE MANIZALES NÓMINA JUBILADOS"|"79065233"|"15000000"|"2000000"|"Remanentes - 60237038927 - REMANENTE"           |"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"|"Habilitado"|
  