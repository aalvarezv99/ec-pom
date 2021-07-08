#language: es
Característica: Retanqueo de creditos

 Antecedentes: 
  Dado Un agente en el sistema core abacus con sesion iniciada                                        
  |http://184.73.108.125:83/libranzas/autenticacion.seam|Chrome |jtellez@excelcredit.co|Suaita.01|

 @Retanqueo
 Esquema del escenario:  Retanqueo
   Cuando El agente ingrese a la pestana retanqueo
   Y se filtra por <Cedula><Credito>
   Y se da clic a retanquear   
   Y se busca el credito por <Cedula>
   Y se selecciona el retanqueo
   Y borrar archivos
   Y cargar archivos nuevos <rutaPDF>
   Y se solicita la consulta a centrales de riesgo
   Y marcar el credito viable
   Y ingresar al simulador interno y llenar los campos <Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina>
  Ejemplos: 
  |Cedula       |Credito  |rutaPDF                                    | Tasa  | Plazo | Monto      | DiasHabilesIntereses | Ingresos  | descLey  | descNomina | IngresosMes |         
  |"94508122"   |"53436"  |"C:\\Users\\User\\Documents\\PDFPRUEBA.pdf"| "1.8" | "60"  | "5000000"  | "10"                 | "3500000" | "280000" | "50000"    | "3500000"   | 

