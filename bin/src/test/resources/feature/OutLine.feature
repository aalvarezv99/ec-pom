#language: es
Característica: Escenario otline

@Prueba
Esquema del escenario:  Iniciar sesion
Dado Un usuario en abacus
Cuando Ingrese el correo <RutaOriginacion>
Entonces e ingrese la contrasena <RutaCliente>

Ejemplos:
| RutaOriginacion  | RutaCliente  |
	| src/test/resources/Data/Data.xlsx| src/test/resources/Data/OriginacionCreditos.xlsx |

#Ejemplos:
# | username  | password  |
#| jtellez@excelcredit.co | Suaita.01 |
# | taperez@excelcredit.co | Colombia.2020 |

#Ejemplos:
#{'datafile':'src/test/resources/Data/Prueba.xls'}

