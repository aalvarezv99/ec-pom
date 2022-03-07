package Consultas;


import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;

public class FuncionesSqlQuery {
	
private static Logger log = Logger.getLogger(FuncionesSqlQuery.class);
	
	static ConexionBase dbconector = new ConexionBase();
	
	
	/*
	 * ThainerPerez 21/Dic/2021, V1.0 - 	1. Se crea la Funcion para calcular los intereses de fianza de la certificacion de saldos
	 * 										2. Esta funcion se implementa en el codigo ya que inicialmente estaba creada por SQL la creo el Dev ArturoEstrada
	 * 										*/
	public static void funcionCxcFianzaCertSaldo() {
		log.info("*** Crear funcion cxc Fianza Certificacion Saldos ****");
		try {
			dbconector.ejecutorFunciones("src/test/resources/Data/SqlFunctions/FuncionCxcFianzaCertSaldo.sql");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static void ejecutarFuncionOriginacion() {
		log.info("***Ejecutando Funcion Originacion ***");
		try {
			dbconector.ejecutorFunciones("src/test/resources/Data/SqlFunctions/FuncionOriginacion.sql");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
	//Funcion Retanqueo Implementacion Jonathann Varon - ADP-172
	
	public static void ejecutarFuncionRetanqueo() {
		log.info("***Ejecutando Funcion Retanqueo ***");
		try {
			dbconector.ejecutorFunciones("src/test/resources/Data/SqlFunctions/FuncionRetanqueo.sql");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public static void ejecutarFuncionRetanqueoMultiple() {
		log.info("***Ejecutando Funcion Retanqueo Multiple***");
		try {
			dbconector.ejecutorFunciones("src/test/resources/Data/SqlFunctions/FuncionRetanqueoMultiple.sql");
		} catch (Exception e) {
			log.error(e.getMessage());
			assertTrue("ERROR EJECUTANDO LA FUNCION - ejecutarFuncionRetanqueoMultiple()", false);
		}
	}
}
