package Acciones.ComunesAccion;

import CommonFuntions.BaseTest;
import Consultas.MovimientoContableQuery;
import dto.MovimientoContableDto;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertTrue;


/* Autor:ThainerPerez ----- Fecha:12/11/2021 -----  Version:V1.0 ---
 * Descripcion: Se crea la clase y sus metodos inciales y  la validacion contable para CCRED ACRED y CSALD
 * **************************************************************
 * Thainer Perez  --------- Fecha 25/11/2021 -----  Version:V1.1--
 * Descripcion: Se crean los metodos de validacion contable por procesos masivos para APLPAG (Aplicacion de pagos)
 * */
public class MovimientoContableAccion extends BaseTest {

    MovimientoContableQuery queryDinamica;
    private static Logger log = Logger.getLogger(MovimientoContableAccion.class);
    private List<MovimientoContableDto> listMovContable = new ArrayList<MovimientoContableDto>();
    private List<MovimientoContableDto> creditosRadicado = new ArrayList<MovimientoContableDto>();
    private Map<String, String> cuentasConcatenadas = new LinkedHashMap<>();
    private Map<String, List<String>> CreditosCuentas = new LinkedHashMap<>();
    String numCedula = "";

    public MovimientoContableAccion(WebDriver driver) {
        super(driver);
        queryDinamica = new MovimientoContableQuery();
    }

    /*ThainerPerez 12/Nov/2021, Se valida que se proceso por el bridge*/
    public void validarProcesoBridge(String numRadicado, String numCedula, String accountingSource, String fechaRegistro) {
        log.info("******* Se valida que se registro en el bridge, MovimientoContableAccion -validarProcesoBridge()*****");
        ResultSet result = null;
        this.numCedula = numCedula;
        try {

            if (numRadicado.equals("null")) {
                numRadicado = consultarNumradicadoPorCedula(numCedula);
            }

            long start_time = System.currentTimeMillis();
            long wait_time = 30000;
            long end_time = start_time + wait_time;
            String detalle = "";
            String origin = "";
            while (System.currentTimeMillis() < end_time && (detalle.equals("") || detalle.equals("Pendiente de ser procesado por el Bridge Contable"))) {
                result = queryDinamica.validarDetalleBridge(numRadicado, accountingSource, fechaRegistro);
                while (result.next()) {
                    origin = result.getString(1);
                    detalle = result.getString(2);
                }
            }
            if (detalle.equals("Registered in the accounting bridge"))
                log.info("******* 'Exitoso' - Se Proceso el movimiento por el Bridge contable ******");
            else {
                assertBooleanImprimeMensaje("#### " + origin + "  ERROR - No se ha procesado por el bridge revisar la tabla  'movimiento_contable' ###", true);
            }

        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - validarProcesoBridge() ################" + e);
            assertTrue("########## MovimientoContableAccion - validarProcesoBridge()########" + e, false);
        }

    }

    /*ThainerPerez 12/Nov/2021, Se valida la causacion de intereses con los movimientos de tipo 1 y 2, los debitos y creditos*/
    public void validarCausacionMovimientos(String accountingSource, String numRadicado, String fecha) {
        log.info("******* Validar que la suma de los movimientos credito y debito coincidan, MovimientoContableAccion - validarCausacionMovimientos() ******");
        ResultSet result = null;
        Boolean caousacion = false;

        if (numRadicado.equals("null")) {
            numRadicado = consultarNumradicadoPorCedula(this.numCedula);
        }

        try {
            result = queryDinamica.validarCausacionMovimientos(accountingSource, numRadicado, fecha);
            while (result.next()) {
                caousacion = result.getBoolean(1);
            }
            if (!caousacion) {
                assertBooleanImprimeMensaje("#### ERROR - validar la causacion de movimientos contables, no hizo match en la suma de los tipos debito y credito ###", true);
            }
            log.info("******** 'Exitoso' - se validaron los debitos y creditos de los movimientos contables *******");
        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - validarCausacionMovimientos() ################" + e);
            assertTrue("########## MovimientoContableAccion - validarCausacionMovimientos()########" + e, false);
        }
    }

    /*ThainerPerez 12/Nov/2021, Se realiza la comparacion Libranzas Vs Bridge con las cuentas que correspondan*/
    public void compararCuentasLibranzasVsBridge(String numRadicado, String accountingSource, String accountingName, String cedula, String fecha) {
        log.info("*********** Se compara libranzas con el bridge, MovimientoContableAccion - compararCuentasLibranzasVsBridge()********************");
        ResultSet result = null;

        if (numRadicado.equals("null")) {
            numRadicado = consultarNumradicadoPorCedula(cedula);
        }

        result = queryDinamica.consultarMovimientos(numRadicado, accountingSource, fecha);

        try {

            while (result.next()) {
                MovimientoContableDto movimientoLibranza = new MovimientoContableDto();
                movimientoLibranza.setTipoMovimiento(result.getString(1));
                movimientoLibranza.setCuenta(result.getString(2));
                movimientoLibranza.setTipoTransaccion(result.getString(3));
                movimientoLibranza.setNumeroRadicado(result.getString(4));
                movimientoLibranza.setDescripcionCuenta(result.getString(5));
                movimientoLibranza.setValor(result.getString(6));
                movimientoLibranza.setVlrBoolean(result.getBoolean(7));
                listMovContable.add(movimientoLibranza);
            }
            result.close();
            int count = 0;
            String cuentas = "";
            for (MovimientoContableDto movimientoContableDto : listMovContable) {
                cuentas = cuentas + "'" + movimientoContableDto.getCuenta() + "'";
                count++;
                log.info("Cuentas Libranzas (" + (count) + ") - " + movimientoContableDto.getCuenta() + " | " + movimientoContableDto.getTipoMovimiento() + " | " + movimientoContableDto.getTipoTransaccion());
            }
            cuentas = cuentas.replace("''", "','");

            List<MovimientoContableDto> listMovCuentasBridge = new ArrayList<MovimientoContableDto>();
            result = queryDinamica.consultarCuentasBridgeNumRadicado(accountingName, cuentas);
            while (result.next()) {
                MovimientoContableDto movimiento = new MovimientoContableDto();
                movimiento.setNombreProcessoAcounting(result.getString(1));
                movimiento.setNombreCuentaAcouting(result.getString(2));
                movimiento.setCuenta(result.getString(3));
                movimiento.setTipoTransaccion(result.getString(4));
                listMovCuentasBridge.add(movimiento);
            }
            result.close();

            count = 0;
            for (MovimientoContableDto movimientoContableDto : listMovCuentasBridge) {
                count++;
                log.info("Cuentas Bridge(" + (count) + ") - " + movimientoContableDto.getCuenta() + " | " + movimientoContableDto.getNombreProcessoAcounting() + " | " + movimientoContableDto.getTipoTransaccion());
            }

            for (MovimientoContableDto movimientoContableDto : listMovContable) {
                MovimientoContableDto prueba = listMovCuentasBridge.stream()
                        .filter(filtro -> movimientoContableDto.getCuenta().equals(filtro.getCuenta()))
                        .findAny()
                        .orElse(null);
                if (prueba == null) {
                    log.error("No se encontro la cuenta de libranzas " + movimientoContableDto.getCuenta() + " En acoounting bridge");
                }

            }

            if (listMovContable.size() != listMovCuentasBridge.size()) {
                assertBooleanImprimeMensaje(
                        "###### ERROR - la cantidad de cuentas de libranzas y accounting bridge no coinciden ####",
                        true);
            }

            log.info(" 'Exitoso' - Las cuentas de libranzas se encuentran en el bridge");

        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - compararCuentasLibranzasVsBridge() ################" + e);
            assertTrue("########## MovimientoContableAccion - compararCuentasLibranzasVsBridge()########" + e, false);
        }

    }

    /*ThainerPerez 12/Nov/2021, se valida Libranzas vs PSL, buscando la coincidencia de las cuentas*/
    public void validacionPSL(String acountingsource, String fecharegistro, String numradicado) {
        ResultSet result = null;
        log.info("***** validando movimientos libranzas contra PSL , MovimientoContableAccion - validacionPSL()******");
        try {

            if (numradicado.equals("null")) {
                numradicado = consultarNumradicadoPorCedula(this.numCedula);
            }

            int count = 0;
            for (MovimientoContableDto movimientoContableDto : listMovContable) {
                count++;
                log.info("Cuentas Libranzas (" + (count) + ") - " + movimientoContableDto.getCuenta() + " | " + movimientoContableDto.getTipoMovimiento() + " | " + movimientoContableDto.getTipoTransaccion());
            }

            count = 0;
            List<MovimientoContableDto> listMovPslCo_detalcompr = new ArrayList<MovimientoContableDto>();
            result = queryDinamica.consultarCuentasPSL(acountingsource, fecharegistro, numradicado);
            while (result.next()) {
                MovimientoContableDto movimiento = new MovimientoContableDto();
                movimiento.setNumeroRadicado(result.getString(1));
                movimiento.setNombreCuentaAcouting(result.getString(2));
                movimiento.setTipoTransaccion(result.getString(3));
                movimiento.setCuenta(result.getString(4));
                movimiento.setValor(result.getString(5));
                listMovPslCo_detalcompr.add(movimiento);
            }
            result.close();

            for (MovimientoContableDto movimientoContableDto : listMovPslCo_detalcompr) {
                count++;
                log.info("Cuentas PSL - co_detalcompr (" + (count) + ") - " + movimientoContableDto.getCuenta() + " | " + movimientoContableDto.getNombreCuentaAcouting() + " | " + movimientoContableDto.getTipoTransaccion());
            }

            for (MovimientoContableDto movimientoContableDto : listMovContable) {
                MovimientoContableDto prueba = listMovPslCo_detalcompr.stream()
                        .filter(filtro -> movimientoContableDto.getCuenta().equals(filtro.getCuenta()))
                        .findAny()
                        .orElse(null);
                if (prueba == null) {
                    log.error("No se encontro la cuenta de libranzas " + movimientoContableDto.getCuenta() + " En PSL co_detalcompr");
                }
            }

            if (listMovContable.size() != listMovPslCo_detalcompr.size()) {

                List<MovimientoContableDto> listMovPslCo_movimentra = new ArrayList<MovimientoContableDto>();
                result = queryDinamica.consultarCuentaPslCo_movimentra(acountingsource, fecharegistro, numradicado);
                while (result.next()) {
                    MovimientoContableDto movimiento = new MovimientoContableDto();
                    movimiento.setMmensaje(result.getString(1));
                    movimiento.setNumeroRadicado(result.getString(2));
                    movimiento.setNombreCuentaAcouting(result.getString(3));
                    movimiento.setTipoMovimiento(result.getString(4));
                    movimiento.setCuenta(result.getString(5));
                    movimiento.setValor(result.getString(6));
                    listMovPslCo_movimentra.add(movimiento);
                }
                result.close();
                count = 0;
                for (MovimientoContableDto movimientoContableDto : listMovPslCo_movimentra) {
                    count++;
                    log.error("Cuentas PSL - co_movimientra  (" + (count) + ") |" + movimientoContableDto.getMmensaje() + "|" + movimientoContableDto.getCuenta() + " | " + movimientoContableDto.getNombreCuentaAcouting() + " | " + movimientoContableDto.getTipoMovimiento());
                }

                assertBooleanImprimeMensaje(
                        "###### ERROR - la cantidad de cuentas de libranzas y PSL no coinciden - 'Revisar las tablas co_movimientra, co_detalcompr' ####",
                        true);
            }

        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion() - validacionPSL() ################" + e);
            assertTrue("########## MovimientoContableAccion - validacionPSL()########" + e, false);
        }

    }

    /*ThainerPerez 12/11/2021, Se consulta el numero de radicado para los procesos que no lo tienen como prerequisito*/
    public String consultarNumradicadoPorCedula(String numCedula) {
        String numRadicado = "";
        ResultSet result = null;
        try {
            result = queryDinamica.consultarNumeroradicado(numCedula);
            while (result.next()) {
                numRadicado = result.getString(1);
            }
        } catch (Exception e) {
            log.error("############## 'ERROR' consultarNumeroradicado() - consultarNumradicadoPorCedula() ################" + e);
            assertTrue("########## MovimientoContableAccion - consultarNumradicadoPorCedula()########" + e, false);
        }
        return numRadicado;
    }

    /*Acciones aplicacion*/
    public void validarCargueContraLibranzas(String idPagaduria, String accoutingsource, String fechaRegistro) {
        log.info(
                "***Validar los  registros cargados de la planilla conta la Base de datos, MovimientoContableAccion - validarCargueContraLibranzas()***");
        ResultSet result = null;
        List<MovimientoContableDto> listDocumento = new ArrayList<MovimientoContableDto>();
        try {
            result = queryDinamica.buscarCreditosPlanilla(idPagaduria, fechaRegistro, accoutingsource);

            while (result.next()) {
                MovimientoContableDto creditosCargados = new MovimientoContableDto();
                creditosCargados.setNumeroRadicado(result.getString(1));
                creditosCargados.setVlrBoolean(result.getBoolean(2));
                creditosCargados.setEstadoCredito(result.getString(3));
                creditosCargados.setEstadoIncor(result.getString(4));
                creditosCargados.setMmensaje(result.getString(5));
                listDocumento.add(creditosCargados);
            }
            result.close();

            int count = 0;
            for (MovimientoContableDto object : listDocumento) {

                if (count == 0) {
                    log.info("**** 'REVISAR' ----> los siguientes Creditos no generaron movimientos contables ****");
                    log.info("|NumRadicado|Bloqueocausal|EstadoCre|Causal");
                }
                log.info("|" + object.getNumeroRadicado() + "|" + object.getVlrBoolean() + "|"
                        + object.getEstadoCredito() + "|" + object.getMmensaje() + "|");
                count++;
            }

        } catch (Exception e) {
            log.error(
                    "############## 'ERROR' MovimientoContableAccion - validarCargueContraLibranzas() ################"
                            + e);
            assertTrue("########## MovimientoContableAccion - validarCargueContraLibranzas()########" + e, false);
        }

    }

    /*Thainer Perez V1.0 - 25/Nov/2021, 1. 	Se crea este medoto con el fin de validar el procesamiento masivo del bridge
     * 										Cuando se realizo el cargue de una pagaduria con limite de 3.3 minutos*/
    public void validarBridgeMasivo(String idPagaduria, String accountingSource, String fechaRegistro) {
        log.info("'INICIA PROCESO - VALIDACION BRIDGE MASIVO'");
        log.info(
                "***Validar que se procesaron los movimientos por el bridge, validarBridgeMasivo - validarCargueContraLibranzas()***");
        ResultSet result = null;
        List<MovimientoContableDto> resultBridge = new ArrayList<MovimientoContableDto>();
        try {
            MovimientoContableDto validacionBridge = new MovimientoContableDto();
            long start_time = System.currentTimeMillis();
            long wait_time = 200000;
            long end_time = start_time + wait_time;
            Boolean repetir = true;
            while (System.currentTimeMillis() < end_time && repetir) {
                result = queryDinamica.validarProcesamientoBridge(idPagaduria, fechaRegistro, accountingSource);
                while (result.next()) {
                    validacionBridge = new MovimientoContableDto();
                    validacionBridge.setValor(result.getString(1));
                    validacionBridge.setMmensaje(result.getString(2));
                    resultBridge.add(validacionBridge);
                }
                log.info("*** Validando mensaje masivo 'Registered in the accounting bridge' ****");
                if (resultBridge.size() == 1) {
                    for (MovimientoContableDto movimientoContableDto : resultBridge) {
                        if (movimientoContableDto.getMmensaje().equals("Registered in the accounting bridge")) {
                            log.info("'EXITOSO' creditos se procesaron por el bridge' " + movimientoContableDto.getValor() + "|" + movimientoContableDto.getMmensaje());
                            repetir = false;
                            resultBridge.removeAll(resultBridge);
                            break;
                        }
                    }
                }
                resultBridge.removeAll(resultBridge);
            }
            result.close();
            if (repetir == true) {
                log.error(" ###### 'ERROR' - VALIDAR LOS MOVIMIENTOS DE LA TABLA 'movimiento contable' YA QUE CUMPLIO EL LIMITE DE TIEMPO Y NO SE PROCESARON POR EL BRIDGE ######## ");
                assertTrue("########## MovimientoContableAccion - consultarNumradicadoPorCedula()########", false);
            }

        } catch (Exception e) {
            log.error(
                    "############## 'ERROR' MovimientoContableAccion - consultarNumradicadoPorCedula() ################"
                            + e);
            assertTrue("########## MovimientoContableAccion - consultarNumradicadoPorCedula()########" + e, false);
        }
    }


    /*ThainerPerez V1.0 26/Nov/2021,1. 	se crea este metodo con el fin de comparar masivamente la causacion de los creditos
     * 								 	que se aplicaron en la pagaduria, en el proceso de aplicacion
     *ThainerPerez V1.1 17/Enero/2022, 1. Se agrega el tipo de fuente "Cierre", para que consulte los creditos con movimientos
     *									  debido a que si estan al dia no generan movimientos*/
    
    public void validarCausacionMovimientosMasivo(String accountingSource, String idPagaduria, String fechaRegistro) {
        log.info("'INICIA PROCESO - VALIDACION CAUSACION MASIVO");
        log.info("**** Validar Causacion de manera masiva, MovimientoContableAccion- validarCausacionMovimientosMasivo() ****");
        ResultSet result = null;
        try {
        	
			if (accountingSource.equals("'CIERRE'")) {
				result = queryDinamica.buscarCreditosConMovContables(idPagaduria, fechaRegistro, accountingSource);
				while (result.next()) {
					MovimientoContableDto radicadoDto = new MovimientoContableDto();
					radicadoDto.setNumeroRadicado(result.getString(1));
					creditosRadicado.add(radicadoDto);
				}
				result.close();
				
			} else {
				result = queryDinamica.consultarCreditosMasivos(accountingSource, idPagaduria, fechaRegistro);
				while (result.next()) {
					MovimientoContableDto radicadoDto = new MovimientoContableDto();
					radicadoDto.setNumeroRadicado(result.getString(1));
					creditosRadicado.add(radicadoDto);
				}
				result.close();
			}
            
            log.info(" ****** Creditos a validar Causacion " + creditosRadicado.size() + " ******");
            
            List<String> erroCausacion = new ArrayList<String>();
            for (MovimientoContableDto movimientoContableDto : creditosRadicado) {
                Boolean caousacion = false;
                log.info("***** Validando Causacion para el credito " + movimientoContableDto.getNumeroRadicado() + " ****** ");
                result = queryDinamica.validarCausacionMovimientos(accountingSource, movimientoContableDto.getNumeroRadicado(), fechaRegistro);
                while (result.next()) {
                    caousacion = result.getBoolean(1);
                }
                if (caousacion == false) {
                    erroCausacion.add("**** ERROR CAUSACION N° Radicado - " + movimientoContableDto.getNumeroRadicado() + " ******");
                }
            }

            if (erroCausacion.size() > 0) {
                log.error("########## ERROR EN LOS SIGUIENTES CREDITOS CON LA CAUSACION ###########");
                for (String string : erroCausacion) {
                    log.error(string);
                }
                assertTrue("########## ERROR CAUSACION DE MOVIMIENTOS ########", false);
            }

            log.info("*** ' EXITOSO ' - no hubieron errores en la causacion de movimientos ***");

        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - validarCausacionMovimientosMasivo() ################" + e);
            assertTrue("########## MovimientoContableAccion - validarCausacionMovimientosMasivo()########" + e, false);
        }
    }

    /*
     * ThainerPerez V1.0 - 29/Nov/2021, 1. 	Se usa la sobrecarga del metodo, enviando un parametro menos el
     * 										numero de cedula para modificarlo y usarlo para procesos masivos.
     * 									2. 	Se utiliza el objeti listMovContable que esta declarado global y se llena
     * 										Para que sea utilizado en la validacion de PSL*/
    public void compararCuentasLibranzasVsBridge(String accountingSource, String accountingName, String fecha) {
        log.info("*********** Se compara Masivamente libranzas con el bridge, MovimientoContableAccion - compararCuentasLibranzasVsBridge()********************");
        log.info("****'INICIO PROCESO' - COMPARACION MASIVA LIBRANZAS VS BRIDGE ****");

        try {

            this.listMovContable = consultarMovContables(creditosRadicado, accountingSource, fecha);
            this.CreditosCuentas = this.consultarCreditosconcuentas(this.listMovContable);
            this.cuentasConcatenadas = this.unirCuentasCreditosMasivos(this.CreditosCuentas);
            Map<String, List<String>> cuentasBridge = new LinkedHashMap<>();
            String name = "";
            for (Entry<String, String> mapCuentas : this.cuentasConcatenadas.entrySet()) {
                String descripcionCuenta = this.consultarDescripcionCuenta(mapCuentas.getKey());
                if(accountingSource.equals("'CIERRE'")) {
                	name = accountingName;
                }
                else if (accountingSource.equals("'RETANQ'")) {
                	name = accountingName;
                }
                else {
                	 name = this.consultarAccountingName(mapCuentas.getKey());	
                }
                cuentasBridge.put(mapCuentas.getKey(), this.consultarCuentasBridgeMasivo(name, mapCuentas.getValue(), descripcionCuenta));
            }

            if (cuentasBridge.size() > 0) {
                log.info("Se encontraron cuentas en el bridge, inicia la comparacion masiva");
            }

            compararCuentas(this.CreditosCuentas, cuentasBridge);

            log.info("********* 'EXITOSO' - Los creditos y sus cuentas existen en el bridge *********");

        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - compararCuentasLibranzasVsBridge() ################" + e);
            assertTrue("########## MovimientoContableAccion - compararCuentasLibranzasVsBridge()########" + e, false);
        }
    }

    public String consultarAccountingName(String numRadicado) {
        String name = "upper('Aplicación de pago por pagaduría')";
        MovimientoContableDto movimiento = listMovContable.stream()
                .filter(filtro -> filtro.getNumeroRadicado().equals(numRadicado))
                .findAny()
                .orElse(null);
        if (movimiento != null && movimiento.getVlrBoolean()) {
            name = "upper('Aplicación de pago venta en firme')";
        }
        return name;
    }

    public String consultarDescripcionCuenta(String numRadicado) {
        String descripcion = null;
        MovimientoContableDto movimiento = listMovContable.stream()
                .filter(filtro -> filtro.getNumeroRadicado().equals(numRadicado))
                .findAny()
                .orElse(null);
        if (movimiento != null) {
            descripcion = movimiento.getDescripcionCuenta();
        }
        return descripcion;
    }
    /*
     * ThainerPerez V1.0 - 01/Dic/2021, 1. Se utiliza la sobrecarga del metodo validacion PSL para realizar la comparacion masiva
     * 									2. Se toman los valores de la variable global cuenta concatenada llenada en el metodo compararCuentasLibranzasVsBridge()
     * 									3. Se obtienen las cuentas de libranzas de la variable global y se comparan con las de PSL
     * */
    @SuppressWarnings("unused")
    public void validacionPSL(String acountingsource, String fecharegistro) {
        log.info("***** 'INICIO PROCESO' -  COMPARACION MASIVA LIBRANZAS VS PSL *****");
        try {

            Map<String, List<String>> cuentasPsl = new LinkedHashMap<>();
            for (Entry<String, String> mapCuentas : this.cuentasConcatenadas.entrySet()) {
                cuentasPsl.put(mapCuentas.getKey(), this.cuentasContablesPSLMasivo(acountingsource, fecharegistro, mapCuentas.getKey()));
            }

            for (MovimientoContableDto movimientoContableDto : creditosRadicado) {

            }

            log.info("------------------------>" + cuentasPsl.size());

            if ((cuentasPsl.size() > 0 || cuentasPsl != null)) {
                log.info("Se encontraron cuentas en PSL, inicia la comparacion masiva");
            } else {
                log.error("###### 'ERROR' - NO SE ENCONTRARON REGISTROS EN PSL, NO SE REALIZARA LA COMPARACION MASIVA ######");
                assertTrue("###### 'ERROR' - NO SE ENCONTRARON REGISTROS EN PSL, NO SE REALIZARA LA COMPARACION MASIVA ######", false);
            }
            compararCuentas(this.CreditosCuentas, cuentasPsl);
            log.info("********* 'EXITOSO' - Los creditos y sus cuentas existen en psl *********");
        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - validacionPSL() ################" + e);
            assertTrue("########## MovimientoContableAccion - validacionPSL()########" + e, false);
        }
    }


    /*
     * ThainerPerez V1.0 - 29/Nov/2021, 1. 	Se recibe una lista de MovimientoContableDto donde los valores llenos son Radicado y cuentas
     * 								    2.  Se retorna un numero de radicado y una lista con sus cuentas*/
    private Map<String, List<String>> consultarCreditosconcuentas(List<MovimientoContableDto> movimientocontabledto) {
        Map<String, List<String>> CreditosCuentas = new LinkedHashMap<>();

        for (MovimientoContableDto listMovContable : movimientocontabledto) {
            List<String> listCuentas = new ArrayList<String>();
            if (!CreditosCuentas.containsKey(listMovContable.getNumeroRadicado())) {
                listCuentas.add(listMovContable.getCuenta());
                CreditosCuentas.put(listMovContable.getNumeroRadicado(), listCuentas);
            } else {
                CreditosCuentas.get(listMovContable.getNumeroRadicado()).add(listMovContable.getCuenta());
            }
        }
        return CreditosCuentas;

    }

    /*
     * ThainerPerez V1.0 - 29/Nov/2021, 1. 	Este metodo recibe un mapa de String y una lista de string.
     * 									2. 	Se retorna el numero de radicado y sus cuentas separadas por (,)*/
    private Map<String, String> unirCuentasCreditosMasivos(Map<String, List<String>> CreditosCuentas) {
        Map<String, String> result = new LinkedHashMap<>();
        try {
            for (Map.Entry<String, List<String>> mapCuentas : CreditosCuentas.entrySet()) {
                String cuentas = "";
                for (String cuenta : mapCuentas.getValue()) {
                    if (cuentas.equals("")) {
                        cuentas = cuenta;
                    } else {
                        cuentas += "','" + cuenta;
                    }
                }
                result.put(mapCuentas.getKey(), "'" + cuentas + "'");

            }
        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - unirCuentasCreditosMasivos() ################" + e);
            assertTrue("########## MovimientoContableAccion - unirCuentasCreditosMasivos()########" + e, false);
        }
        return result;

    }

    /*ThainerPerez - 30/Nov/2021 V1.0,	1.	Este metodo consulta los movimientos contables generados para una fecha y una fuente contable especifica*/
    private List<MovimientoContableDto> consultarMovContables(List<MovimientoContableDto> creditoRadicado, String accountingSource, String fecha) {
        List<MovimientoContableDto> listMov = new ArrayList<MovimientoContableDto>();
        ResultSet result = null;
        try {
            for (MovimientoContableDto movimientoContableDto : creditoRadicado) {
                result = queryDinamica.consultarMovimientos(movimientoContableDto.getNumeroRadicado(), accountingSource, fecha);
                while (result.next()) {
                    MovimientoContableDto movimientoLibranza = new MovimientoContableDto();
                    movimientoLibranza.setTipoMovimiento(result.getString(1));
                    movimientoLibranza.setCuenta(result.getString(2));
                    movimientoLibranza.setTipoTransaccion(result.getString(3));
                    movimientoLibranza.setNumeroRadicado(result.getString(4));
                    movimientoLibranza.setDescripcionCuenta(result.getString(5));
                    movimientoLibranza.setValor(result.getString(6));
                    movimientoLibranza.setVlrBoolean(result.getBoolean(7));
                    listMov.add(movimientoLibranza);
                }
                result.close();
            }
        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - consultarMovContables() ################" + e);
            assertTrue("########## MovimientoContableAccion - consultarMovContables()########" + e, false);
        }
        return listMov;
    }

    private List<String> consultarCuentasBridgeMasivo(String accountingName, String cuentas, String descripcion) {
        List<String> cuentasBridge = new ArrayList<String>();
        try {
            ResultSet result = null;
            List<MovimientoContableDto> listMovCuentasBridge = new ArrayList<MovimientoContableDto>();
            result = queryDinamica.consultarCuentasBridge(accountingName, cuentas, descripcion);
            while (result.next()) {
                MovimientoContableDto movimiento = new MovimientoContableDto();
                movimiento.setNombreProcessoAcounting(result.getString(1));
                movimiento.setNombreCuentaAcouting(result.getString(2));
                movimiento.setCuenta(result.getString(3));
                movimiento.setTipoTransaccion(result.getString(4));
                listMovCuentasBridge.add(movimiento);
                cuentasBridge.add(result.getString(3));
            }
        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - consultarCuentasBridge() ################" + e);
            assertTrue("########## MovimientoContableAccion - consultarCuentasBridge()########" + e, false);
        }
        return cuentasBridge;
    }

    public void compararCuentas(Map<String, List<String>> cuentasLibranzas, Map<String, List<String>> cuentasComparacion) {
        ArrayList<String> errores = new ArrayList<>();
        for (Map.Entry<String, List<String>> mapaLibranza : cuentasLibranzas.entrySet()) {
            for (Map.Entry<String, List<String>> mapaComparacion : cuentasComparacion.entrySet()) {
                if (mapaLibranza.getKey().equals(mapaComparacion.getKey())) {
                    if (!mapaLibranza.getValue().equals(mapaComparacion.getValue())) {
                        errores.add("###### 'ERROR' Credito " + mapaLibranza.getKey() + " Cuentas Libranzas "
                                + mapaLibranza.getValue() + " Cuenta Comparacion " + mapaComparacion.getValue()
                                + " #######");
                    }
                }
            }
        }

        if (errores.size() > 0) {
            log.error("##### 'ERROR - VALIDAR CUENTAS' ######");
            for (String string : errores) {
                log.error(string);
            }
            assertTrue("#### 'ERROR' - FALLO LA COMPARACION DE CUENTAS MASIVAS####", false);
        }
    }


    public List<String> cuentasContablesPSLMasivo(String acountingsource, String fecharegistro, String numradicado) {
        List<String> listMovPslCo_detalcompr = new ArrayList<>();
        ResultSet result = null;
        try {
            result = queryDinamica.consultarCuentasPSL(acountingsource, fecharegistro, numradicado);
            while (result.next()) {
                MovimientoContableDto movimiento = new MovimientoContableDto();
                movimiento.setNumeroRadicado(result.getString(1));
                movimiento.setNombreCuentaAcouting(result.getString(2));
                movimiento.setTipoTransaccion(result.getString(3));
                movimiento.setCuenta(result.getString(4));
                movimiento.setValor(result.getString(5));
                listMovPslCo_detalcompr.add(result.getString(4));
            }
            result.close();
        } catch (Exception e) {
            log.error("############## 'ERROR' MovimientoContableAccion - consultarCuentasBridge() ################" + e);
            assertTrue("########## MovimientoContableAccion - consultarCuentasBridge()########" + e, false);
        }

        return listMovPslCo_detalcompr;
    }


}