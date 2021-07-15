#language: es
Característica: Originación de Creditos con saneamientos

  Antecedentes:
  Dado Un agente en el sistema core abacus con sesion iniciada

  @SimuladorAsesor
  Esquema del escenario: Simulador Asesor
    Cuando el agente ingresa a la pestana de simulador asesor
     #Y cambia la fecha del servidor <FechaServidor>
     Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria><Cedula><fecha><Oficina><Actividad><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
     Y valida los calculos correctos de la simulacion<fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
     Y guarda la simulacion presionando el boton guardar
     Entonces se permite crear el cliente <TipoContrato><FechaIngreso><Pnombre><Papellido><Sapellido><Correo><Celular><Dpto><Ciudad>
     Y el sistema habilita el cargue de documentos para el cliente <rutaPDF>
     Y se finaliza con la consulta a centrales
   Ejemplos:
       | Pagaduria   | Cedula     | fecha         | Oficina     | Actividad    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes | TotalActivos | vlrCompasSaneamientos | tipo |colchon | TipoContrato                    | FechaIngreso | Pnombre  | Papellido   | Sapellido  | Correo                     | Celular      | Dpto           | Ciudad     | rutaPDF                                     | FechaServidor | DestinoCredito     | Sexo | EstadoCivil | Cedula     | fecha         | Direccion         | TipoVivienda | NombreCredito | PnombreReferencia     | PapellidoReferencia | TelefonoResidencia | TelefonoTrabajo | Codigo |  Mes     | TipoDesen  | fechaActual  | Banco                                  |
       | "COLFONDOS" | "52912399" | "12/Ago/1982" | "Cartagena" | "Pensionado" | "1.8" | "60"  | "5000000"  | "10"                 | "3500000" | "280000" | "50000"    | "3500000"   | "20500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2000" | "ALEYDA" | "RODRIGUEZ" | "GONZALEZ" | "dandresabogadog@mail.com" | "3115128152" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-20"  | "Educacion propia" | "F"  | "Soltero"   | "52912399" | "12/Ago/1982" | "Calle 2d #22-52" | "FAMILIAR"   | "ALEYDA"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |
     # | "COLFONDOS" | "9777757"  | "17/Mar/1956" | "Cartagena" | "Pensionado" | "1.8" | "36"  | "20000000" | "21"                 | "6500000" | "480000" | "90000"    | "6500000"   | "80500000"   | "0"                   | "xx" | "0"    | "Pensionado por Tiempo (Vejez)" | "10/03/2005" | "CARLOS" | "HERRERA"   | "ARBOLEDA" | "ajflorez248@mail.com"     | "3015129153" | "Cundinamarca" | "Anapoima" | "C:\\Users\\User\\Documents\\PDFPRUEBA.pdf" | "2021-04-21"  | "Educacion propia" | "M"  | "Soltero"   | "9777757"  | "17/Mar/1956" | "Calle 2d #22-52" | "FAMILIAR"   | "CARLOS"      | "alejandro"           | "perez"             | "7210273"          | "9007146"       | "3112" |  "Abril" | "Efectivo" | "03/05/2021" | "Remanentes - 60237038927 - REMANENTE" |

  @SolicitudCredito
  Esquema del escenario: Solicitar credito con saneamientos
    Cuando el agente ingrese a la pestana solicitud credito con la cedula del cliente <Cedula><NombreCredito>
    Y consulta la pestana seguridad dejando el cliente viable
    Y valida los calculos correctos de la simulacion interna <fecha><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo><colchon>
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y pasar a la siguiente pestaña
