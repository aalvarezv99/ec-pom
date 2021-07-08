#language: es
Característica: Certificacion de Saldos

  Antecedentes: 
    Dado Un agente en el sistema core abacus con sesion iniciada

  @CertificacionActiva
  Esquema del escenario: Certificacion de saldo con credito en estado activo
    Cuando ingrese a la opcion de certificacion de saldos
    Y este ingrese el numero de radicado <NumRadicado> con cedula <NumCedula> para un cliente con credito "ACTIVO" se aplicara el filtro
    Y seleccionando el boton solicitar
    Y este ingrese la informacion en la ventana solicitar presionando el boton guardar
    Entonces permite generar el recaudo para la certificacion con los datos del cliente
    Y posteriormente descargando la certificacion en el modulo gestion certificados con los datos del cliente
    Y se realiza la validacion del PDF descargado finalizando con el proceso

    Ejemplos: 
      | NumRadicado | NumCedula   |
      | 50092 			| 9992284     |
      | 55412 			| 9971523			|

  @CertificacionEnMora
  Escenario: Certificacion de saldo con credito en estdo en mora
    Cuando ingrese a la opcion de certificacion de saldos
    Y este ingrese el numero de radicado con cedula para un cliente con credito en MORA se aplicara el filtro
    Y seleccionando el boton solicitar
    Y este ingrese la informacion en la ventana solicitar presionando el boton guardar
    Entonces permite generar el recaudo para la certificacion con los datos del cliente
    Y posteriormente descargando la certificacion en el modulo gestion certificados con los datos del cliente
    Y se realiza la validacion del PDF descargado finalizando con el proceso
