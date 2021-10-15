#language: es
Característica: Escenario otline

  @Prueba
  Esquema del escenario: Iniciar sesion
    Dado Un usuario en abacus 
    	| tipo | valor|
    	| Cartera |10000|
    	|saneamiento|5000|
    Cuando Ingrese el correo <RutaOriginacion>
    Entonces e ingrese la contrasena <RutaCliente>

    Ejemplos: 
      | RutaOriginacion | RutaCliente |
      | Ruta          | Cliente     |
