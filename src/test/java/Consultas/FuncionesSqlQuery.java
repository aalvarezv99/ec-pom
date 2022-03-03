package Consultas;


import org.apache.log4j.Logger;

import static org.junit.Assert.assertTrue;

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
            dbconector.ejecutorFunciones("CREATE OR REPLACE FUNCTION public.obtener_cxc_intereses_fianza(iden_credito bigint, fecha_vencimiento date)\r\n"
                    + " RETURNS numeric\r\n"
                    + " LANGUAGE plpgsql\r\n"
                    + "AS $function$\r\n"
                    + "<<var>>\r\n"
                    + "DECLARE \r\n"
                    + "valor_fianza numeric;\r\n"
                    + "intereses_fianza numeric;\r\n"
                    + "intereses_fianza_det numeric;\r\n"
                    + "intereses_fianza_corrido numeric;\r\n"
                    + "tasa_originacion numeric;\r\n"
                    + "tasa_financiacion numeric;\r\n"
                    + "tasa_usura numeric;\r\n"
                    + "tasa_interes_aplicable numeric;\r\n"
                    + "tipo_segmento integer;\r\n"
                    + "ultimo_periodo_cierre date;\r\n"
                    + "dias_calculo integer;\r\n"
                    + "\r\n"
                    + "begin	\r\n"
                    + "	var.intereses_fianza_corrido = 0;\r\n"
                    + "	var.dias_calculo = 0;\r\n"
                    + "\r\n"
                    + "	-- Se asigna último periodo cerrado\r\n"
                    + "	select max(periodo_cierre)\r\n"
                    + "	into ultimo_periodo_cierre\r\n"
                    + "	from historico_cierres hc;\r\n"
                    + "	RAISE info 'Fecha último cierre: %', ultimo_periodo_cierre;\r\n"
                    + "\r\n"
                    + "	-- Se asigna el valor de la fianza\r\n"
                    + "	select des.valor_fianza \r\n"
                    + "	into valor_fianza\r\n"
                    + "	from credito cre \r\n"
                    + "	inner join simulador_analista san on san.id_credito = cre.id\r\n"
                    + "	inner join desglose des on des.id = san.id_desglose \r\n"
                    + "	and cre.id = iden_credito;\r\n"
                    + "	RAISE info 'Valor fianza: %', valor_fianza;\r\n"
                    + "	\r\n"
                    + "	-- Se calcula el valor de intereses de fianza sin deteriorado\r\n"
                    + "	SELECT coalesce(SUM(calculo), 0) FROM (SELECT (pif.valor_int_fianza - SUM(CASE WHEN dcxpf.valor_recibido IS NULL THEN 0 ELSE dcxpf.valor_recibido END)) AS calculo \r\n"
                    + "	into intereses_fianza\r\n"
                    + "	FROM plan_intereses_fianza pif \r\n"
                    + "	LEFT JOIN desglose_contable_cuenta_por_pagar_fianza dcxpf ON dcxpf.id_plan_intereses_fianza = pif.id \r\n"
                    + "	WHERE pif.id_credito = iden_credito\r\n"
                    + "	and pif.deteriorado IS FALSE \r\n"
                    + "	GROUP BY pif.id) as c;\r\n"
                    + "	intereses_fianza = ceil(intereses_fianza);\r\n"
                    + "	RAISE info 'intereses_fianza: %', intereses_fianza;\r\n"
                    + "\r\n"
                    + "	-- Se calcula el valor de intereses de fianza con deteriorado\r\n"
                    + "	SELECT coalesce(SUM(calculo), 0) FROM (SELECT (pif.valor_int_fianza - SUM(CASE WHEN dcxpf.valor_recibido IS NULL THEN 0 ELSE dcxpf.valor_recibido END)) AS calculo \r\n"
                    + "	into intereses_fianza_det\r\n"
                    + "	FROM plan_intereses_fianza pif \r\n"
                    + "	LEFT JOIN desglose_contable_cuenta_por_pagar_fianza dcxpf ON dcxpf.id_plan_intereses_fianza = pif.id \r\n"
                    + "	WHERE pif.id_credito = iden_credito\r\n"
                    + "	and pif.deteriorado IS true \r\n"
                    + "	GROUP BY pif.id) as c;\r\n"
                    + "	intereses_fianza_det = ceil(intereses_fianza_det);\r\n"
                    + "	RAISE info 'intereses_fianza_det: %', intereses_fianza_det;\r\n"
                    + "\r\n"
                    + "	-- Se obtiene la tasa de originación\r\n"
                    + "	select coalesce(san.tasa, 0) \r\n"
                    + "	into tasa_originacion\r\n"
                    + "	from credito cre \r\n"
                    + "	inner join simulador_analista san on san.id_credito = cre.id\r\n"
                    + "	where cre.id = iden_credito;\r\n"
                    + "	RAISE info 'tasa_originacion: %', tasa_originacion;\r\n"
                    + "\r\n"
                    + "	-- Se obtiene el tipo segmento\r\n"
                    + "    select case when segmento = 'Producto AAA' then 1 else 2 end\r\n"
                    + "    into tipo_segmento\r\n"
                    + "    FROM segmento\r\n"
                    + "    WHERE id_credito = iden_credito\r\n"
                    + "    ORDER BY id DESC LIMIT 1;\r\n"
                    + "	RAISE info 'tipo_segmento: %', tipo_segmento;\r\n"
                    + "    \r\n"
                    + "   	-- Se obtiene la tasa de financiamiento de la configuración de la fianza\r\n"
                    + " 	SELECT coalesce(valor_calculo, 0)\r\n"
                    + "   	into tasa_financiacion\r\n"
                    + "   	FROM configuracion_fianza cfi  \r\n"
                    + "   	WHERE cfi.concepto_calculo = 'CUENTA_X_COBRAR_INTERESES_FIANZA' AND cfi.id_segmento = tipo_segmento;\r\n"
                    + "   	RAISE info 'tasa_financiacion: %', tasa_financiacion;\r\n"
                    + "   \r\n"
                    + "	-- Se obtiene la tasa de usura\r\n"
                    + "	SELECT coalesce(ccr.maximo, 0)\r\n"
                    + "	into tasa_usura\r\n"
                    + "	FROM configuracion_credito ccr\r\n"
                    + "	WHERE ccr.tipo = 'tasa'\r\n"
                    + "	AND ccr.tipo_valor = 'BLOQUEANTE';\r\n"
                    + "	tasa_usura = coalesce(tasa_usura, 0);\r\n"
                    + "	RAISE info 'tasa_usura: %', tasa_usura;\r\n"
                    + "   \r\n"
                    + "	-- Se obtiene tasa interes aplicable\r\n"
                    + "	if tasa_financiacion != 0 and (tasa_financiacion < tasa_originacion or tasa_originacion = 0) and (tasa_financiacion < tasa_usura or tasa_usura = 0) then \r\n"
                    + "		tasa_interes_aplicable = tasa_financiacion;\r\n"
                    + "	else \r\n"
                    + "		if tasa_originacion != 0 and (tasa_originacion < tasa_financiacion or tasa_financiacion = 0) and (tasa_originacion < tasa_usura or tasa_usura = 0) then \r\n"
                    + "			tasa_interes_aplicable = tasa_originacion;\r\n"
                    + "		end if;\r\n"
                    + "	end if;\r\n"
                    + "	RAISE info 'tasa_interes_aplicable: %', tasa_interes_aplicable;\r\n"
                    + "\r\n"
                    + "	-- Se obtienen los días para el cálculo de intereses corridos de fianza\r\n"
                    + "	IF DATE_PART('month', ultimo_periodo_cierre) = DATE_PART('month', fecha_vencimiento) THEN \r\n"
                    + "		dias_calculo = DATE_PART('day', fecha_vencimiento);\r\n"
                    + "	else \r\n"
                    + "		dias_calculo = DATE_PART('day', fecha_vencimiento) + 30;\r\n"
                    + "	end if;\r\n"
                    + "	RAISE info 'dias_calculo: %', dias_calculo;\r\n"
                    + "\r\n"
                    + "	-- Se obtiene el valor de los intereses de fianza corridos\r\n"
                    + "	intereses_fianza_corrido = round(valor_fianza * tasa_interes_aplicable / 100 / 30 * dias_calculo, 0);\r\n"
                    + "	RAISE info 'intereses_fianza_corrido: %', intereses_fianza_corrido;\r\n"
                    + "\r\n"
                    + "	return intereses_fianza + intereses_fianza_det + intereses_fianza_corrido;\r\n"
                    + "\r\n"
                    + "END; \r\n"
                    + "$function$\r\n"
                    + ";");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void ejecutarFuncionOriginacion() {
        log.info("***Ejecutando Funcion Originacion ***");
        try {
            dbconector.ejecutorFunciones(
                    "create or replace\r\n"
                            + "function public.calculos_automatizacion_pruebas (\r\n"
                            + "  p_monto integer,\r\n"
                            + "p_xperiodoPrima integer,\r\n"
                            + "p_tasaInicial numeric,\r\n"
                            + "p_plazo numeric,\r\n"
                            + "p_diasIniciales numeric,\r\n"
                            + "p_vlrCompasSaneamientos numeric,\r\n"
                            + "p_ingresos numeric,\r\n"
                            + "p_descLey numeric,\r\n"
                            + "p_descNomina numeric,\r\n"
                            + "p_pagaduria text \r\n"
                            + ")\r\n"
                            + "returns table (\r\n"
                            + "r_monto_solicitar int,\r\n"
                            + "r_cuota_corriente int,\r\n"
                            + "r_estudio_credito_iva int,\r\n"
                            + "r_fianza int,\r\n"
                            + "r_gmf4100 int,\r\n"
                            + "r_valor_intereses_iniciales int,\r\n"
                            + "r_prima_anticipada_seguro int,\r\n"
                            + "r_remanente_estimado int,\r\n"
                            + "r_monto_max_desembolsar int,\r\n"
                            + "r_capacidad_cliente int\r\n"
                            + ")\r\n"
                            + "language plpgsql\r\n"
                            + "as $function$\r\n"
                            + "\r\n"
                            + "declare\r\n"
                            + "-- ============================================================================\r\n"
                            + "-- Autor: Equipo automatizacion Pruebas.\r\n"
                            + "-- Fecha: 08/Nov/2021 Version 1.0 ThainerPerez - Jonathan Varon\r\n"
                            + "-- Se crea la funcion que retorna los calculos de originacion para los simuladores\r\n"
                            + "-- ============================================================================\r\n"
                            + "\r\n"
                            + "\r\n"
                            + "/*VARIABLES GLOBALES PARA LOS PARAMETROS*/\r\n"
                            + "v_tasa_estudio_credito numeric;\r\n"
                            + "\r\n"
                            + "v_tasa_fianza numeric;\r\n"
                            + "\r\n"
                            + "v_vlr_mes_dos numeric;\r\n"
                            + "\r\n"
                            + "v_tasa_uno numeric = p_tasaInicial / 100 ;\r\n"
                            + "\r\n"
                            + "v_tasa_dos numeric;\r\n"
                            + "\r\n"
                            + "v_tasaXmillonSeguro numeric;\r\n"
                            + "\r\n"
                            + "v_valor_millon numeric = 1000000;\r\n"
                            + "\r\n"
                            + "v_iva numeric = 1.19;\r\n"
                            + "\r\n"
                            + "v_diasMes numeric = 30;\r\n"
                            + "\r\n"
                            + "v_gmf4100 numeric = 0.004;\r\n"
                            + "\r\n"
                            + "v_capacidad numeric;\r\n"
                            + "\r\n"
                            + "v_colchon numeric;\r\n"
                            + "\r\n"
                            + "begin\r\n"
                            + "/*PARAMETROS GLOBALES*/\r\n"
                            + "\r\n"
                            + "/*consultar valores capitalizados*/\r\n"
                            + "select\r\n"
                            + "	segunda_tasa,\r\n"
                            + "	estudio_credito,\r\n"
                            + "	fianza,\r\n"
                            + "	mes_cambio_tasa\r\n"
                            + "into\r\n"
                            + "	v_tasa_dos,\r\n"
                            + "	v_tasa_estudio_credito,\r\n"
                            + "	v_tasa_fianza,\r\n"
                            + "	v_vlr_mes_dos\r\n"
                            + "from\r\n"
                            + "	configuracion_capitalizacion_cxc ccc\r\n"
                            + "where\r\n"
                            + "	tasa_inicial = p_tasaInicial;\r\n"
                            + "\r\n"
                            + "/*consultar tasaXmillon*/\r\n"
                            + "select\r\n"
                            + "	tasa_millon\r\n"
                            + "into\r\n"
                            + "	v_tasaXmillonSeguro\r\n"
                            + "from\r\n"
                            + "	seguro;\r\n"
                            + "\r\n"
                            + "/*consultar colchon*/\r\n"
                            + "select\r\n"
                            + "	pp.valor\r\n"
                            + "into\r\n"
                            + "	v_colchon\r\n"
                            + "from\r\n"
                            + "	pagaduria p\r\n"
                            + "inner join pagaduria_parametro pp on\r\n"
                            + "	pp.id_pagaduria = p.id\r\n"
                            + "inner join parametro pa on\r\n"
                            + "	pa.id = pp.id_parametro\r\n"
                            + "where\r\n"
                            + "	p.nombre = p_pagaduria\r\n"
                            + "	and pa.nombre = 'colchon';\r\n"
                            + "\r\n"
                            + "/**********************************CALCULOS DE CONCEPTOS PARA SIMULADORES********************************************/\r\n"
                            + "   \r\n"
                            + "	/*Calcular monto solicitado -  originacion*/\r\n"
                            + "r_monto_solicitar := p_monto + (p_monto * v_tasaXmillonSeguro / v_valor_millon * p_xperiodoPrima)\r\n"
                            + "                + (p_monto * (v_tasa_estudio_credito / 100) * v_iva) + (p_monto * (v_tasa_fianza / 100) * v_iva);\r\n"
                            + "\r\n"
                            + "raise notice 'Monto solicitar %',\r\n"
                            + "(r_monto_solicitar);\r\n"
                            + "\r\n"
                            + "	/*Calcular cuota corriente -  originacion*/\r\n"
                            + "   if(p_plazo < v_vlr_mes_dos) then\r\n"
                            + "    r_cuota_corriente := r_monto_solicitar / ((power((1 + v_tasa_uno), (p_plazo)) - 1) / (v_tasa_uno * power((1 + v_tasa_uno), (p_plazo))));\r\n"
                            + "else\r\n"
                            + "	v_tasa_dos = v_tasa_dos / 100;\r\n"
                            + "   r_cuota_corriente := round(r_monto_solicitar\r\n"
                            + "                    / ((power((1 + v_tasa_uno), (v_vlr_mes_dos - 1)) - 1) / (v_tasa_uno * power((1 + v_tasa_uno), (v_vlr_mes_dos - 1)))\r\n"
                            + "                    + ((power((1 + v_tasa_dos), (p_plazo - (v_vlr_mes_dos - 1))) - 1)\r\n"
                            + "                    / (v_tasa_dos * power((1 + v_tasa_dos), (p_plazo - (v_vlr_mes_dos - 1)))))\r\n"
                            + "                    / (power((1 + v_tasa_uno), (v_vlr_mes_dos - 1)))));\r\n"
                            + "                   \r\n"
                            + "   /*raise notice '>>>> valor credito %', (r_monto_solicitar);\r\n"
                            + "  raise notice '>>>> valor tasaUno  %', (v_tasa_uno);\r\n"
                            + " raise notice '>>>> valor mesDos  %', (v_vlr_mes_dos);\r\n"
                            + " raise notice '>>>> valor tazaDos  %', (v_tasa_dos);\r\n"
                            + "raise notice '>>>> valor plazo  %', (p_plazo);*/\r\n"
                            + "end if;\r\n"
                            + "\r\n"
                            + "raise notice 'Cuota Corriente %', (r_cuota_corriente);\r\n"
                            + "\r\n"
                            + "	/*Calcular Estudio Credito IVA -  originacion*/\r\n"
                            + "r_estudio_credito_iva := ((p_monto * v_tasa_estudio_credito) / 100) + (((p_monto * v_tasa_estudio_credito) / 100) * (v_iva - 1));\r\n"
                            + "\r\n"
                            + "raise notice 'valor Estudio Credito + Iva %', (r_estudio_credito_iva);\r\n"
                            + "\r\n"
                            + "	/*Calcular Fianza -  originacion*/\r\n"
                            + "r_fianza := ((p_monto * v_tasa_fianza) / 100) * v_iva;\r\n"
                            + "\r\n"
                            + "raise notice 'valor Fianza(s) %',\r\n"
                            + "(r_fianza);\r\n"
                            + "\r\n"
                            + "	/*Calcular Gmf4*1000 -  originacion*/\r\n"
                            + "r_gmf4100 := p_vlrCompasSaneamientos * v_gmf4100;\r\n"
                            + "\r\n"
                            + "raise notice 'Valor GMF4100 %', (r_gmf4100);\r\n"
                            + "\r\n"
                            + "	/*Calular Valor Intereses Iniciales -  originacion*/\r\n"
                            + "r_valor_intereses_iniciales := ((r_monto_solicitar * p_tasaInicial) / 100) * (p_diasIniciales / v_diasMes);\r\n"
                            + "\r\n"
                            + "raise notice 'Valor Intereses Iniciales %',\r\n"
                            + "(r_valor_intereses_iniciales);\r\n"
                            + "\r\n"
                            + "	/*Calcular Prima Anticipada Seguro -  originacion*/\r\n"
                            + "r_prima_anticipada_seguro := ((p_monto / v_valor_millon)*(v_tasaXmillonSeguro * p_xperiodoPrima));\r\n"
                            + "\r\n"
                            + "raise notice 'PrimaSeguro %',\r\n"
                            + "(r_prima_anticipada_seguro);\r\n"
                            + "\r\n"
                            + "	/*Calcular Remanente Estimado -  originacion*/\r\n"
                            + "\r\n"
                            + "r_remanente_estimado := r_monto_solicitar - (p_vlrCompasSaneamientos + r_gmf4100 + r_prima_anticipada_seguro + r_estudio_credito_iva + r_fianza);\r\n"
                            + "\r\n"
                            + "raise notice 'Remanente Estimado %', (r_remanente_estimado);\r\n"
                            + "\r\n"
                            + "	/*Calcular Monto Maximo a Desembolsar -  originacion*/\r\n"
                            + "\r\n"
                            + "v_capacidad := ((p_ingresos - p_descLey) / 2) - p_descNomina - v_colchon;\r\n"
                            + "raise notice 'Capacidad Cliente %', (v_capacidad);\r\n"
                            + "\r\n"
                            + "r_capacidad_cliente := v_capacidad;\r\n"
                            + "--Logica\r\n"
                            + "if (p_plazo < v_vlr_mes_dos) then\r\n"
                            + "	r_monto_max_desembolsar := v_capacidad * ((power((1 + v_tasa_uno),(p_plazo))) - 1) / (v_tasa_uno * power((1 + v_tasa_uno),(p_plazo)));\r\n"
                            + "else \r\n"
                            + "	r_monto_max_desembolsar := v_capacidad * ((power((1 + v_tasa_uno), (v_vlr_mes_dos - 1))) - 1)\r\n"
                            + "                    / (v_tasa_uno * power((1 + v_tasa_uno), (v_vlr_mes_dos - 1)))\r\n"
                            + "                    + (v_capacidad * ((power((1 + v_tasa_dos), (p_plazo - (v_vlr_mes_dos - 1)))) - 1)\r\n"
                            + "                    / (v_tasa_dos * power((1 + v_tasa_dos), (p_plazo - (v_vlr_mes_dos - 1)))))\r\n"
                            + "                    / power((1 + v_tasa_uno), (v_vlr_mes_dos - 1));\r\n"
                            + "end if;\r\n"
                            + "\r\n"
                            + "raise notice 'Monto Maximo a Desembolsar %', (r_monto_max_desembolsar);\r\n"
                            + "\r\n"
                            + "/**********************************RETORNO DE : CALCULOS DE CONCEPTOS PARA SIMULADORES********************************************/\r\n"
                            + "return query\r\n"
                            + "select coalesce (r_monto_solicitar,0),\r\n"
                            + "coalesce (r_cuota_corriente,0), \r\n"
                            + "coalesce (r_estudio_credito_iva,0),\r\n"
                            + "coalesce (r_fianza,0),\r\n"
                            + "coalesce (r_gmf4100,0),\r\n"
                            + "coalesce (r_valor_intereses_iniciales,0),\r\n"
                            + "coalesce (r_prima_anticipada_seguro,0),\r\n"
                            + "coalesce (r_remanente_estimado,0),\r\n"
                            + "coalesce (r_monto_max_desembolsar,0),\r\n"
                            + "coalesce (r_capacidad_cliente,0);\r\n"
                            + "end;\r\n"
                            + "\r\n"
                            + "$function$");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    //Funcion Retanqueo Implementacion Jonathann Varon - ADP-172

    public static void ejecutarFuncionRetanqueo() {
        log.info("***Ejecutando Funcion Retanqueo ***");
        try {
            dbconector.ejecutorFunciones(
                    "CREATE OR REPLACE FUNCTION public.calculos_simulador_retanqueo(creditopadre numeric, tasa numeric, plazo numeric, diashabilesintereses numeric, monto numeric, sumamontocarteras numeric, v_fecha varchar)\r\n"
                    + " RETURNS TABLE(tipocalculos character varying, calculoprimaanticipadaseguro numeric, calculocuotacorriente numeric, resultgmf41000 numeric, calculoprimanodevengada numeric, calculoprimaneta numeric, calculovalorfianza numeric, fianzapadre numeric, resultfianza numeric, resultestudiocredito numeric, saldoaldia numeric, calculoremanenteestimado numeric)\r\n"
                    + " LANGUAGE plpgsql\r\n"
                    + "AS $function$\r\n"
                    + "\r\n"
                    + "/*ThainerPerez 03/Marzo/2022 -  Se agrega ceiling en la funcion de base de datos para que aproxime hacia arriba*/\r\n"
                    + "\r\n"
                    + "declare\r\n"
                    + "\r\n"
                    + "-- constantes\r\n"
                    + "iva numeric = 1.19;\r\n"
                    + "tasaXmillon numeric = 4625;\r\n"
                    + "gmf41000 numeric = 0.004;\r\n"
                    + "tasaUno numeric = tasa / 100;\r\n"
                    + "variableMillon numeric = 1000000;\r\n"
                    + "\r\n"
                    + "-- variables de calculos\r\n"
                    + "calculoPrimaAnticipadaSeguro numeric;\r\n"
                    + "calculoEstudioCreditoHijo numeric;\r\n"
                    + "calculoValorFianza numeric;\r\n"
                    + "calculoPrimaNeta numeric;\r\n"
                    + "calculoPrimaNoDevengada numeric;\r\n"
                    + "calculoCuotaCorriente numeric;\r\n"
                    + "calculoRemanenteEstimado numeric;\r\n"
                    + "\r\n"
                    + "-- variables\r\n"
                    + "tienePrimaPadre varchar;\r\n"
                    + "descuentoPrimaAnticipada numeric;\r\n"
                    + "fianzaPadre numeric;\r\n"
                    + "estudioCreditoPadre numeric;\r\n"
                    + "idCredito integer;\r\n"
                    + "idPagaduria integer;\r\n"
                    + "estudioCredito numeric;\r\n"
                    + "tasaFianza numeric;\r\n"
                    + "tasaDos numeric;\r\n"
                    + "mesDos numeric;\r\n"
                    + "periodoGracia numeric;\r\n"
                    + "primaPadre numeric;\r\n"
                    + "montoPadre numeric;\r\n"
                    + "mesesActivosPadre numeric;\r\n"
                    + "tipoCalculos varchar;\r\n"
                    + "resultFianza numeric;\r\n"
                    + "resultEstudioCredito numeric;\r\n"
                    + "resultGmf41000 numeric;\r\n"
                    + "saldoAlDia numeric;\r\n"
                    + "fechaEstudioCredito Date = v_fecha::date;\r\n"
                    + "fechaDesembolso Date;\r\n"
                    + "estadoCreditoHijo varchar;\r\n"
                    + "\r\n"
                    + "begin\r\n"
                    + "\r\n"
                    + "-- consultar prima padre\r\n"
                    + "select\r\n"
                    + "case when c.numero_radicacion = null then 'true'\r\n"
                    + "else 'false' end as prima\r\n"
                    + "into tienePrimaPadre\r\n"
                    + "from credito c\r\n"
                    + "inner join desglose d on d.id_credito = c.id\r\n"
                    + "inner join prima_seguro_anticipada ps on ps.id_desglose = d.id\r\n"
                    + "where 1=1\r\n"
                    + "and d.desglose_seleccionado is true \r\n"
                    + "and c.credito_activo is true\r\n"
                    + "and c.numero_radicacion = creditoPadre;\r\n"
                    + "\r\n"
                    + "-- consulta el descuento de la prima anticipada\r\n"
                    + "select valor\r\n"
                    + "into descuentoPrimaAnticipada\r\n"
                    + "from parametro_configuracion\r\n"
                    + "where nombre = 'PRIMA_SEGURO_PERIODOS_DESCONTAR'\r\n"
                    + "order by id desc;\r\n"
                    + "\r\n"
                    + "-- consulta para obtener la fianza padre\r\n"
                    + "select coalesce(ceiling(d.valor_fianza),0) fianzaPadre\r\n"
                    + "into fianzaPadre\r\n"
                    + "from desglose d\r\n"
                    + "where id_credito in (select  id from credito where numero_radicacion = creditoPadre)\r\n"
                    + "and desglose_seleccionado is true\r\n"
                    + "limit 1;\r\n"
                    + "\r\n"
                    + "-- consulta para obtener el id del credito y la pagaduría\r\n"
                    + "select c.id, c.id_pagaduria into idCredito, idPagaduria from credito c where numero_radicacion = creditoPadre;\r\n"
                    + "\r\n"
                    + "-- consultar el saldo al dia\r\n"
                    + "select ceiling(saldo_al_dia) into saldoAlDia from saldo_al_dia sad where id_credito = idCredito;\r\n"
                    + "\r\n"
                    + "-- fehca desembolso crédito hijo\r\n"
                    + "select coalesce(sian.fecha_desembolso, current_date), credito.estado\r\n"
                    + "into fechaDesembolso, estadoCreditoHijo\r\n"
                    + "from simulador_analista sian\r\n"
                    + "inner join credito on(credito.id = sian.id_credito)\r\n"
                    + "where id_credito in (select credito.id from credito where numero_radicacion = 79796)\r\n"
                    + "order by credito.id desc limit 1;\r\n"
                    + "\r\n"
                    + "if (estadoCreditoHijo = 'LLAMADA_BIENVENIDA' or estadoCreditoHijo = 'PENDIENTE_DESEMBOLSO')\r\n"
                    + "	then\r\n"
                    + "		if (fechaDesembolso <= current_date)\r\n"
                    + "		then\r\n"
                    + "			fechaEstudioCredito = fechaDesembolso;\r\n"
                    + "		else\r\n"
                    + "			fechaEstudioCredito = current_date;\r\n"
                    + "		end if;\r\n"
                    + "end if;\r\n"
                    + "\r\n"
                    + "raise notice 'fechaEstudioCredito %', fechaEstudioCredito;\r\n"
                    + "\r\n"
                    + "-- cunsulta para obtener el estudio del credito padre\r\n"
                    + "select coalesce(ceiling(obtener_valor_estudio_credito(fechaEstudioCredito, idCredito, idPagaduria, false)),0) estudioCreditoPadre into estudioCreditoPadre;\r\n"
                    + "\r\n"
                    + "-- consulta para obtener valores capitalizador\r\n"
                    + "select ceiling(estudio_credito), segunda_tasa, fianza, mes_cambio_tasa\r\n"
                    + "into estudioCredito, tasaDos, tasaFianza, mesDos\r\n"
                    + "from configuracion_capitalizacion_cxc ccc\r\n"
                    + "where tasa_inicial = tasa;\r\n"
                    + "\r\n"
                    + "-- calcular periodo de gracia\r\n"
                    + "if (plazo < descuentoPrimaAnticipada)\r\n"
                    + "	then\r\n"
                    + "		periodoGracia = Ceiling(diasHabilesIntereses / 30);\r\n"
                    + "		descuentoPrimaAnticipada = periodoGracia + plazo;\r\n"
                    + "\r\n"
                    + "end if;\r\n"
                    + "\r\n"
                    + "-- calcular prima credito padre\r\n"
                    + "select ceiling(valor) into primaPadre\r\n"
                    + "from prima_seguro_anticipada psa\r\n"
                    + "join desglose d2\r\n"
                    + "on d2.id=psa.id_desglose\r\n"
                    + "join credito c2\r\n"
                    + "on c2.id = d2.id_credito\r\n"
                    + "where d2.desglose_seleccionado is true\r\n"
                    + "and c2.numero_radicacion = creditoPadre;\r\n"
                    + "\r\n"
                    + "-- consultar monto padre\r\n"
                    + "select ceiling(c2.monto_aprobado) into montoPadre\r\n"
                    + "from prima_seguro_anticipada psa\r\n"
                    + "join desglose d2\r\n"
                    + "on d2.id=psa.id_desglose\r\n"
                    + "join credito c2\r\n"
                    + "on c2.id = d2.id_credito\r\n"
                    + "where d2.desglose_seleccionado is true\r\n"
                    + "and c2.numero_radicacion = creditoPadre;\r\n"
                    + "\r\n"
                    + "-- consultar meses activos padre\r\n"
                    + "with ConteoPeriodos as (\r\n"
                    + "select (count(fechas)+complemento-1) periodos\r\n"
                    + "from (\r\n"
                    + "select id_credito, generate_series(mes_contable, now(), cast('1 month' as interval) )fechas,\r\n"
                    + "case when date_part('day', now()) < date_part('day', mes_contable) then 1 else 0 end complemento\r\n"
                    + "from movimiento_contable as mc\r\n"
                    + "where id_credito in(select c2.id from credito c2 where c2.numero_radicacion = creditoPadre)\r\n"
                    + "and tipo_transaccion = 'ACTIVACION_CREDITO'\r\n"
                    + ") x group by complemento, id_credito\r\n"
                    + ")\r\n"
                    + "select periodos into mesesActivosPadre from ConteoPeriodos;\r\n"
                    + "\r\n"
                    + "-- se realizan los calculos\r\n"
                    + "\r\n"
                    + "-- calcular prima anticipada de seguro\r\n"
                    + "calculoPrimaAnticipadaSeguro = ceiling((tasaXmillon / 1000000 * descuentoPrimaAnticipada) * monto);\r\n"
                    + "\r\n"
                    + "-- calcular cuota corriente\r\n"
                    + "if(plazo < mesDos) then\r\n"
                    + "  calculoCuotaCorriente = ceiling(monto / ((power((1 + tasaUno), (plazo)) - 1) / (tasaUno * power((1 + tasaUno), (plazo)))));\r\n"
                    + "else\r\n"
                    + "  tasaDos = tasaDos / 100;\r\n"
                    + "  calculoCuotaCorriente = ceiling(monto\r\n"
                    + "  / ((power((1 + tasaUno), (mesDos - 1)) - 1) / (tasaUno * power((1 + tasaUno), (mesDos - 1)))\r\n"
                    + "  + ((power((1 + tasaDos), (plazo - (mesDos - 1))) - 1)\r\n"
                    + "  / (tasaDos * power((1 + tasaDos), (plazo - (mesDos - 1)))))\r\n"
                    + "  / (power((1 + tasaUno), (mesDos - 1)))));\r\n"
                    + "end if;\r\n"
                    + "\r\n"
                    + "-- calcular 4*1000\r\n"
                    + "resultGmf41000 = ceiling(sumaMontoCarteras * gmf41000);\r\n"
                    + "\r\n"
                    + "-- calcular prima no devengada\r\n"
                    + "calculoPrimaNoDevengada = ceiling(coalesce(primaPadre, 0) - ((coalesce(montoPadre, 0) * tasaXmillon) / variableMillon) * mesesActivosPadre);\r\n"
                    + "\r\n"
                    + "-- calcular prima neta\r\n"
                    + "calculoPrimaNeta = ceiling(calculoPrimaAnticipadaSeguro - calculoPrimaNoDevengada);\r\n"
                    + "\r\n"
                    + "-- calcular estudio de credito retanqueo hijo\r\n"
                    + "calculoEstudioCreditoHijo = ceiling(monto / (1 + (estudioCredito / 100) * iva + (tasaFianza / 100) * iva +\r\n"
                    + "							(tasaXmillon / 1000000 * descuentoPrimaAnticipada)) * (estudioCredito / 100) * iva);\r\n"
                    + "\r\n"
                    + "-- calcular valor de la fianza\r\n"
                    + "calculoValorFianza = ceiling(monto * (tasaFianza / 100) * 1.19);\r\n"
                    + "\r\n"
                    + "-- result fianza\r\n"
                    + "resultFianza = calculoValorFianza - fianzaPadre;\r\n"
                    + "if (resultFianza < 0)\r\n"
                    + "  then\r\n"
                    + "  resultFianza = 0;\r\n"
                    + "end if;\r\n"
                    + "\r\n"
                    + "-- estudio de crédito\r\n"
                    + "resultEstudioCredito = ceiling(monto * (estudioCredito / 100) * iva) - estudioCreditoPadre;\r\n"
                    + "if (resultEstudioCredito < 0)\r\n"
                    + "  then\r\n"
                    + "  resultEstudioCredito = 0;\r\n"
                    + "end if;\r\n"
                    + "\r\n"
                    + "-- calculo remanente estimado\r\n"
                    + "calculoRemanenteEstimado = ceiling(monto - (saldoAlDia + resultFianza + calculoPrimaNeta + resultEstudioCredito + sumaMontoCarteras + resultGmf41000));\r\n"
                    + "\r\n"
                    + "-- validaciones si es anticipado o mensualizado\r\n"
                    + "if (tienePrimaPadre = '')\r\n"
                    + "	then\r\n"
                    + "		tipoCalculos = 'mensualizado';\r\n"
                    + "	else\r\n"
                    + "		tipoCalculos = 'anticipado';\r\n"
                    + "end if;\r\n"
                    + "\r\n"
                    + "-- return values\r\n"
                    + "return query\r\n"
                    + "select tipoCalculos,\r\n"
                    + "coalesce(calculoPrimaAnticipadaSeguro, 0),\r\n"
                    + "coalesce(calculoCuotaCorriente, 0),\r\n"
                    + "coalesce(resultGmf41000, 0),\r\n"
                    + "coalesce(calculoPrimaNoDevengada, 0),\r\n"
                    + "coalesce(calculoPrimaNeta, 0),\r\n"
                    + "coalesce(calculoValorFianza, 0),\r\n"
                    + "coalesce(fianzaPadre, 0),\r\n"
                    + "coalesce(resultFianza, 0),\r\n"
                    + "coalesce(resultEstudioCredito, 0),\r\n"
                    + "coalesce(saldoAlDia, 0),\r\n"
                    + "coalesce(calculoRemanenteEstimado, 0);\r\n"
                    + "end;\r\n"
                    + "\r\n"
                    + "$function$\r\n"
                    + ";\r\n"
                    + "");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void ejecutarFuncionRetanqueoMultiple() {
        log.info("***Ejecutando Funcion Retanqueo Multiple***");
        try {
            dbconector.ejecutorFunciones("CREATE OR REPLACE FUNCTION public.autopruebas_retanqueo_multiple_cal_simulador(v_cedula text, v_nombre_pagaduria text, v_tasa numeric, v_plazo numeric, v_diashabilesintereses numeric, v_monto numeric, v_sumamontocarteras numeric, v_fecha text)\n" +
                    " RETURNS TABLE(tipocalculos character varying, calculoprimaanticipadaseguro numeric, calculocuotacorriente numeric, resultgmf41000 numeric, calculoprimanodevengada numeric, calculoprimaneta numeric, r_sumafianzas numeric, r_fianzapadre numeric, r_fianza_neta numeric, resultestudiocredito numeric, saldoaldia numeric, calculoremanenteestimado numeric)\n" +
                    " LANGUAGE plpgsql\n" +
                    "AS $function$\n" +
                    "\n" +
                    "declare\n" +
                    "-- ============================================================================\n" +
                    "-- Autor: Equipo automatizacion Pruebas.\n" +
                    "-- Fecha: 24/Enero/2021 Version 1.0 ThainerPerez\n" +
                    "-- Se crea la funcion que retorna los calculos de RetanqueoMultiple para los simuladores\n" +
                    "-- ============================================================================\n" +
                    "\n" +
                    "\n" +
                    "-- constantes Globales\n" +
                    "iva numeric = 1.19;\n" +
                    "tasaXmillon numeric = 4625;\n" +
                    "gmf41000 numeric = 0.004;\n" +
                    "tasaUno numeric = v_tasa / 100;\n" +
                    "variableMillon numeric = 1000000;\n" +
                    "-- variables de calculos\n" +
                    "calculoPrimaAnticipadaSeguro numeric;\n" +
                    "calculoEstudioCreditoHijo numeric;\n" +
                    "calculoValorFianza numeric;\n" +
                    "calculoPrimaNeta numeric;\n" +
                    "calculoPrimaNoDevengada numeric;\n" +
                    "calculoCuotaCorriente numeric;\n" +
                    "calculoRemanenteEstimado numeric;\n" +
                    "-- variables\n" +
                    "tienePrimaPadre varchar;\n" +
                    "descuentoPrimaAnticipada numeric;\n" +
                    "fianzaPadre numeric;\n" +
                    "estudioCreditoPadre numeric;\n" +
                    "idCredito integer;\n" +
                    "v_nombrePagaduria text;\n" +
                    "idPagaduria integer;\n" +
                    "estudioCredito numeric;\n" +
                    "tasaFianza numeric;\n" +
                    "tasaDos numeric;\n" +
                    "mesDos numeric;\n" +
                    "periodoGracia numeric;\n" +
                    "primaPadre numeric;\n" +
                    "montoPadre numeric;\n" +
                    "mesesActivosPadre numeric;\n" +
                    "tipoCalculos varchar;\n" +
                    "r_fianza_neta numeric;\n" +
                    "resultEstudioCredito numeric;\n" +
                    "resultGmf41000 numeric;\n" +
                    "saldoAlDia numeric;\n" +
                    "fechaEstudioCredito Date = v_fecha::date;\n" +
                    "fechaDesembolso Date;\n" +
                    "estadoCreditoHijo varchar;\n" +
                    "\n" +
                    "\n" +
                    "begin\n" +
                    "\n" +
                    "-- consulta el descuento de la prima anticipada\n" +
                    "select\n" +
                    "valor\n" +
                    "into\n" +
                    "descuentoPrimaAnticipada\n" +
                    "from\n" +
                    "parametro_configuracion\n" +
                    "where\n" +
                    "nombre = 'PRIMA_SEGURO_PERIODOS_DESCONTAR'\n" +
                    "order by\n" +
                    "id desc;\n" +
                    "\n" +
                    "\n" +
                    "-- consulta para obtener el id del credito y la pagadurÃ\u00ADa\n" +
                    "select\n" +
                    "p.id\n" +
                    "into\n" +
                    "idPagaduria\n" +
                    "from pagaduria p\n" +
                    "where\n" +
                    "upper(p.nombre) in (upper(v_nombre_pagaduria));\n" +
                    "\n" +
                    "\n" +
                    "/*Consulta Saldo al dia*/\n" +
                    "select\n" +
                    "coalesce(sum(round(saldo_al_dia)),0)\n" +
                    "into\n" +
                    "saldoAlDia\n" +
                    "from\n" +
                    "saldo_al_dia sad\n" +
                    "where\n" +
                    "id_credito in (select  c.id from credito c join cliente cl on c.id_cliente = cl.id\n" +
                    "where cl.identificacion = v_cedula\n" +
                    "and c.estado = 'ACTIVO'\n" +
                    "and c.id_pagaduria = idPagaduria);\n" +
                    "\n" +
                    "\n" +
                    "-- consulta para obtener valores capitalizador\n" +
                    "select\n" +
                    "estudio_credito,\n" +
                    "segunda_tasa,\n" +
                    "fianza,\n" +
                    "mes_cambio_tasa\n" +
                    "into\n" +
                    "estudioCredito,\n" +
                    "tasaDos,\n" +
                    "tasaFianza,\n" +
                    "mesDos\n" +
                    "from\n" +
                    "configuracion_capitalizacion_cxc ccc\n" +
                    "where\n" +
                    "tasa_inicial = v_tasa;\n" +
                    "\n" +
                    "raise notice 'Tasa Original %',v_tasa;\n" +
                    "\n" +
                    "raise notice 'EstudioCredito Tasa TasaFianza mesDos % % % % ', estudioCredito, tasaDos, TasaFianza, mesDos;\n" +
                    "\n" +
                    "-- calcular periodo de gracia\n" +
                    "if (v_plazo < descuentoPrimaAnticipada)\n" +
                    "then\n" +
                    "periodoGracia = ceiling(v_diashabilesintereses / 30);\n" +
                    "\n" +
                    "descuentoPrimaAnticipada = periodoGracia + v_plazo;\n" +
                    "end if;\n" +
                    "\n" +
                    "-- calcular cuota corriente\n" +
                    "if(v_plazo < mesDos) then\n" +
                    "  calculoCuotaCorriente = v_monto / ((power((1 + tasaUno), (v_plazo)) - 1) / (tasaUno * power((1 + tasaUno), (v_plazo))));\n" +
                    "else\n" +
                    "  tasaDos = tasaDos / 100;\n" +
                    "\n" +
                    "calculoCuotaCorriente = round(v_monto\n" +
                    "  / ((power((1 + tasaUno), (mesDos - 1)) - 1) / (tasaUno * power((1 + tasaUno), (mesDos - 1)))\n" +
                    "  + ((power((1 + tasaDos), (v_plazo - (mesDos - 1))) - 1)\n" +
                    "  / (tasaDos * power((1 + tasaDos), (v_plazo - (mesDos - 1)))))\n" +
                    "  / (power((1 + tasaUno), (mesDos - 1)))));\n" +
                    "end if;\n" +
                    "\n" +
                    "raise notice 'CuotaCorriente %',\n" +
                    "(calculoCuotaCorriente);\n" +
                    "-- calcular 4*1000\n" +
                    "resultGmf41000 = round(v_sumamontocarteras * gmf41000);\n" +
                    "\n" +
                    "raise notice '4X100  %',\n" +
                    "(resultGmf41000);\n" +
                    "\n" +
                    "-- calcular prima anticipada de seguro\n" +
                    "calculoPrimaAnticipadaSeguro = round((v_monto / variableMillon)*(tasaXmillon * descuentoPrimaAnticipada));\n" +
                    "raise notice 'PrimaAnticipada %',\n" +
                    "(calculoPrimaAnticipadaSeguro);\n" +
                    "\n" +
                    "-- calcular prima no devengada\n" +
                    "with ConteoPeriodos as (\n" +
                    "select\n" +
                    "(count(fechas)+ complemento-1) periodos, id_credito\n" +
                    "from\n" +
                    "(\n" +
                    "select\n" +
                    "id_credito,\n" +
                    "generate_series(mes_contable, now(), cast('1 month' as interval) )fechas,\n" +
                    "case\n" +
                    "when date_part('day', now()) < date_part('day', mes_contable) then 1\n" +
                    "else 0\n" +
                    "end complemento\n" +
                    "from\n" +
                    "movimiento_contable as mc\n" +
                    "where\n" +
                    "id_credito in(\n" +
                    "select\n" +
                    "c2.id\n" +
                    "from\n" +
                    "credito c2\n" +
                    "where\n" +
                    "c2.numero_radicacion in (select  c.numero_radicacion from credito c join cliente cl on c.id_cliente = cl.id\n" +
                    "where cl.identificacion = v_cedula\n" +
                    "and c.estado = 'ACTIVO'\n" +
                    "and c.id_pagaduria = idPagaduria))\n" +
                    "and tipo_transaccion = 'ACTIVACION_CREDITO'\n" +
                    ") x\n" +
                    "group by\n" +
                    "complemento,\n" +
                    "id_credito\n" +
                    "),\n" +
                    "monto_padre_creditos as (select\n" +
                    "round(c.monto_aprobado) montoCredito, c.id, psa.valor\n" +
                    "from\n" +
                    "prima_seguro_anticipada psa\n" +
                    "join desglose d2\n" +
                    "on\n" +
                    "d2.id = psa.id_desglose\n" +
                    "join ConteoPeriodos c2\n" +
                    "on\n" +
                    "c2.id_credito = d2.id_credito\n" +
                    "join credito c on c.id = c2.id_credito\n" +
                    "where\n" +
                    "d2.desglose_seleccionado is true)\n" +
                    "select\n" +
                    "coalesce(sum(round(coalesce(mp.valor,0) - (coalesce(mp.valor, 0) / descuentoPrimaAnticipada * periodos))),0) primaNoDevengada\n" +
                    "into\n" +
                    "calculoPrimaNoDevengada\n" +
                    "from\n" +
                    "ConteoPeriodos ct\n" +
                    "   join monto_padre_creditos mp on ct.id_credito = mp.id;\n" +
                    "\n" +
                    "raise notice 'Prima no devengada  %',\n" +
                    "(calculoPrimaNoDevengada);\n" +
                    "\n" +
                    "-- calcular prima neta\n" +
                    "calculoPrimaNeta = calculoPrimaAnticipadaSeguro - calculoPrimaNoDevengada;\n" +
                    "if (calculoPrimaNeta < 0)\n" +
                    "  then\n" +
                    "  calculoPrimaNeta := calculoPrimaAnticipadaSeguro;\n" +
                    "end if;\n" +
                    "raise notice 'Prima Neta  %',\n" +
                    "(calculoPrimaNeta);\n" +
                    "\n" +
                    "-- consulta para obtener la fianza padre\n" +
                    "select\n" +
                    "coalesce(round(sum(d.valor_fianza)), 0) fianzaPadre\n" +
                    "into\n" +
                    "fianzaPadre\n" +
                    "from\n" +
                    "desglose d\n" +
                    "where\n" +
                    "id_credito in (\n" +
                    "select\n" +
                    "id\n" +
                    "from\n" +
                    "credito\n" +
                    "where\n" +
                    "numero_radicacion in (select  c.numero_radicacion from credito c join cliente cl on c.id_cliente = cl.id\n" +
                    "where cl.identificacion = v_cedula\n" +
                    "and c.estado = 'ACTIVO'\n" +
                    "and c.id_pagaduria = idPagaduria))\n" +
                    "and desglose_seleccionado is true;\n" +
                    "\n" +
                    "raise notice 'Fianza Padre %',\n" +
                    "(fianzaPadre);\n" +
                    "\n" +
                    "\n" +
                    "-- calcular valor de la fianza\n" +
                    "calculoValorFianza = round(((v_monto * tasaFianza) / 100) * iva);\n" +
                    "raise notice 'Valor Fianza  %',\n" +
                    "(calculoValorFianza);\n" +
                    "\n" +
                    "-- result fianza\n" +
                    "r_fianza_neta = calculoValorFianza - fianzaPadre;\n" +
                    "if (r_fianza_neta < 0)\n" +
                    "  then\n" +
                    "  r_fianza_neta = 0;\n" +
                    "end if;\n" +
                    "\n" +
                    "r_sumaFianzas := (fianzaPadre + r_fianza_neta);\n" +
                    "\n" +
                    "r_fianzaPadre := fianzaPadre;\n" +
                    "\n" +
                    "raise notice 'Suma Fianzas %',\n" +
                    "(r_sumaFianzas);\n" +
                    "\n" +
                    "raise notice 'Fianza Neta %',\n" +
                    "(r_fianza_neta);\n" +
                    "\n" +
                    "\n" +
                    "-- calcular estudio de credito retanqueo hijo\n" +
                    "calculoEstudioCreditoHijo = round(((v_monto * estudioCredito) / 100) + (((v_monto * estudioCredito) / 100) * (iva - 1)));\n" +
                    "\n" +
                    "raise notice 'EstudioCredito  %',\n" +
                    "(calculoEstudioCreditoHijo);\n" +
                    "\n" +
                    "-- fehca desembolso crédito hijo\n" +
                    "select coalesce(sian.fecha_desembolso, current_date), credito.estado\n" +
                    "into fechaDesembolso, estadoCreditoHijo\n" +
                    "from simulador_analista sian\n" +
                    "inner join credito on(credito.id = sian.id_credito)\n" +
                    "where id_credito in (select credito.id from credito\n" +
                    "left join cliente on(cliente.id = credito.id_cliente)\n" +
                    "where cliente.identificacion = v_cedula\n" +
                    "order by credito.id desc limit 1);\n" +
                    "\n" +
                    "if (estadoCreditoHijo = 'LLAMADA_BIENVENIDA' or estadoCreditoHijo = 'PENDIENTE_DESEMBOLSO')\n" +
                    "\tthen\n" +
                    "\t\tif (fechaDesembolso <= current_date)\n" +
                    "\t\tthen\n" +
                    "\t\t\tfechaEstudioCredito = fechaDesembolso;\n" +
                    "\t\telse\n" +
                    "\t\t\tfechaEstudioCredito = current_date;\n" +
                    "\t\tend if;\n" +
                    "end if;\n" +
                    "\n" +
                    "raise notice 'fechaEstudioCredito %', fechaEstudioCredito;\n" +
                    "\n" +
                    "-- cunsulta para obtener el estudio del credito padre\n" +
                    "select coalesce(sum(round(public.obtener_valor_estudio_credito(fechaEstudioCredito, c.id::integer, c.id_pagaduria::integer , false))),0) estudioCredito\n" +
                    "into\n" +
                    "estudioCreditoPadre\n" +
                    "from credito c\n" +
                    "where c.numero_radicacion in (select  c.numero_radicacion from credito c join cliente cl on c.id_cliente = cl.id\n" +
                    "where cl.identificacion = v_cedula\n" +
                    "and c.estado = 'ACTIVO'\n" +
                    "and c.id_pagaduria = idPagaduria);\n" +
                    "\n" +
                    "-- estudio de crÃ©dito\n" +
                    "resultEstudioCredito = calculoEstudioCreditoHijo - estudioCreditoPadre;\n" +
                    "\n" +
                    "if (resultEstudioCredito < 0)\n" +
                    "  then\n" +
                    "  resultEstudioCredito = 0;\n" +
                    "end if;\n" +
                    "\n" +
                    "raise notice 'EstudioCredito  %',\n" +
                    "(resultEstudioCredito);\n" +
                    "\n" +
                    "-- calculo remanente estimado\n" +
                    "calculoRemanenteEstimado = round(v_monto - (saldoAlDia + r_fianza_neta + resultEstudioCredito + v_sumamontocarteras + resultGmf41000 + calculoPrimaNeta));\n" +
                    "\n" +
                    "raise notice 'RemanenteEstimado  %',\n" +
                    "(calculoRemanenteEstimado);\n" +
                    "\n" +
                    "-- validaciones si es anticipado o mensualizado\n" +
                    "if (tienePrimaPadre = '')\n" +
                    "then\n" +
                    "tipoCalculos = 'mensualizado';\n" +
                    "else\n" +
                    "tipoCalculos = 'anticipado';\n" +
                    "end if;\n" +
                    "-- return values\n" +
                    "return query\n" +
                    "select\n" +
                    "tipoCalculos,\n" +
                    "coalesce(calculoPrimaAnticipadaSeguro, 0),\n" +
                    "coalesce(calculoCuotaCorriente, 0),\n" +
                    "coalesce(resultGmf41000, 0),\n" +
                    "coalesce(calculoPrimaNoDevengada, 0),\n" +
                    "coalesce(calculoPrimaNeta, 0),\n" +
                    "coalesce(r_sumaFianzas, 0),\n" +
                    "coalesce(fianzaPadre, 0),\n" +
                    "coalesce(r_fianza_neta, 0),\n" +
                    "coalesce(resultEstudioCredito, 0),\n" +
                    "coalesce(saldoAlDia, 0),\n" +
                    "coalesce(calculoRemanenteEstimado, 0);\n" +
                    "end;\n" +
                    "\n" +
                    "$function$ \n"
                    + ";\r\n"
                    + "");
        } catch (Exception e) {
            log.error(e.getMessage());
            assertTrue("ERROR EJECUTANDO LA FUNCION - ejecutarFuncionRetanqueoMultiple()", false);
        }
    }
}
