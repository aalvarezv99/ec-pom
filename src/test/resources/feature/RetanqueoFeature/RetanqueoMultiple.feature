#language: es
Característica: Retanqueo de creditos

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @RetanqueoMultipleSeleccion
  Esquema del escenario: Seleccion Pantalla Principal Retanqueo Multiple
    Cuando El agente ingrese a la pestana retanqueo
    Y se filtra por <Cedula> <Pagaduria> para retanqueo multiple
    Y se da clic a retanquear a todos los creditos
    Y se ingresa el monto a solicitar <Retanqueo>

    Ejemplos: 
      | Cedula | Retanqueo | Pagaduria | Credito | Celular | Correo | TipoDesen | rutaPDF | Tasa | Plazo | DiasHabilesIntereses | Ingresos | descLey | descNomina | VlrCompraSaneamiento |codigo|

  ##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"8682110"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"|
 
  @SolicitudRetanqueoMultiple
  Esquema del escenario: Solicitud Retanqueo Multiple
    Cuando se busca el credito por <Cedula>
    Y se selecciona el retanqueo
    #Y borrar archivos
    #Y cargar archivos nuevos <rutaPDF>
    #Y se solicita la consulta a centrales de riesgo
    Y marcar el credito viable
    Y ingresar al simulador interno y llenar los campos <Retanqueo><Tasa><Plazo><DiasHabilesIntereses><Ingresos><descLey><descNomina><VlrCompraSaneamiento>
    Y se validan los datos del simulador <Ingresos><descLey><descNomina><Tasa><Plazo><Credito><DiasHabilesIntereses><VlrCompraSaneamiento>
		Y se da clic en solicitar
    Y carga todos los archivos en la pestana de digitalizacion <rutaPDF>
    Y marcar los check en correcto guardando en la pestana de digitalizacion
    Y se presiona en verificacion en la pestana digitalizacion
    Y se confirma identidad en digitalizacion <codigo>
    Y se marca identidida confirmada radicando la solicitud
    Y se aprueban las referencias de la pagaduria
    Entonces se aprueba la referenciacion de la pagaduria en la pestana referenciacion permite realizar la solicitud del analisis
    
    Ejemplos: 
      | Cedula | Retanqueo | Pagaduria | Credito | Celular | Correo | TipoDesen | rutaPDF | Tasa | Plazo | DiasHabilesIntereses | Ingresos | descLey | descNomina | VlrCompraSaneamiento |codigo|
	##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"8682110"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"|
  
  @ClientesBienvenidaRetanqueoMult
  Esquema del escenario: Clientes para Bienvenida
    Cuando el agente ingresa a la pestana clientes para bienvenida<Cedula>
    Y se marcar los check correctos junto con el celular y correo<Celular> <Correo>
    Y se validan los valores de las condiciones del credito <Credito>
    Y validar las condiciones de la carta de notificacion de creditos <Cedula>
    Y se marcan los chech y se acepta <TipoDesen> <Cedula>

    Ejemplos: 
      | Cedula | Retanqueo | Pagaduria | Credito | Celular | Correo | TipoDesen | rutaPDF | Tasa | Plazo | DiasHabilesIntereses | Ingresos | descLey | descNomina | VlrCompraSaneamiento |codigo|
	##@externaldata@./src/test/resources/Data/AutomationDataRetanqueo.xlsx@RetanqueoMultiple
   |"8682110"   |"5000000"   |"P.A COLPENSIONES"   |"85863"   |"3125117715"   |"dandresabogadog@mail.com"   |"Efectivo"   |"src/test/resources/Data/PDFPRUEBA.pdf"   |"1.8"   |"90"   |"10"   |"6500000"   |"380000"   |"100000"   |"0"   |"2258"|
