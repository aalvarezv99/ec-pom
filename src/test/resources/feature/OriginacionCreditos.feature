#language: es
Característica: Originacion de Creditos

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @SimuladorAsesor
  Esquema del escenario: Creacion cliente nuevo
    Cuando el agente ingrese a la pestana de simulador asesor
    Y cree la simulacion con la informacion del archivo contenida en la tabla <Pagaduria><Cedula><fecha><Oficina><Actividad><Tasa><Plazo><Monto><DiasHabilesIntereses><Ingresos><descLey><descNomina><vlrCompasSaneamientos><tipo>
    Y guarde la simulacion presionando el boton guardar 
    Entonces se permite crear el cliente con la informacion del archivo contenida en la hoja formCliente
    Y el sistema habilite el cargue de documentos para el cliente
    Y se finaliza con la consulta a centrales
   Ejemplos:
   |Pagaduria	 |Cedula	   |fecha	        |Oficina	   |Actividad   |	Tasa|Plazo|	Monto   |	DiasHabilesIntereses |Ingresos	|descLey  |descNomina|vlrCompasSaneamientos |tipo   |
   |"COLFONDOS"|"52912399" |"12/Ago/1982" |"Cartagena" |"Pensionado"|"1.8"|"60" |"5000000"|"10"                  |"3500000" |"280000" |"50000"   |"90000"               |"xx"   |




  @SolicitudCredito
  Escenario: Realizar Simulacion Cliente Nuevo
    Cuando el agente ingrese a la pestana solicitud credito

   	