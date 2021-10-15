#language: es
Característica: Originacion de Creditos

  Antecedentes: Un usuario sin iniciar sesion 
    Dado un usuario en el sistema de abacus sin iniciar sesion
  
    
 	Escenario: Iniciar sesion 
 		Cuando ingrese un correo electronico y contrasena valida
 		Y se presione el boton autenticar
 		Entonces se permite el ingreso al sistema 		               
    