CREATE OR REPLACE FUNCTION public.obtener_cxc_intereses_fianza(iden_credito bigint, fecha_vencimiento date)
 RETURNS numeric
 LANGUAGE plpgsql
AS $function$
<<var>>
DECLARE 
valor_fianza numeric;
intereses_fianza numeric;
intereses_fianza_det numeric;
intereses_fianza_corrido numeric;
tasa_originacion numeric;
tasa_financiacion numeric;
tasa_usura numeric;
tasa_interes_aplicable numeric;
tipo_segmento integer;
ultimo_periodo_cierre date;
dias_calculo integer;

begin	
	var.intereses_fianza_corrido = 0;
	var.dias_calculo = 0;

	-- Se asigna último periodo cerrado
	select max(periodo_cierre)
	into ultimo_periodo_cierre
	from historico_cierres hc;
	RAISE info 'Fecha último cierre: %', ultimo_periodo_cierre;

	-- Se asigna el valor de la fianza
	select des.valor_fianza 
	into valor_fianza
	from credito cre 
	inner join simulador_analista san on san.id_credito = cre.id
	inner join desglose des on des.id = san.id_desglose 
	and cre.id = iden_credito;
	RAISE info 'Valor fianza: %', valor_fianza;
	
	-- Se calcula el valor de intereses de fianza sin deteriorado
	SELECT coalesce(SUM(calculo), 0) FROM (SELECT (pif.valor_int_fianza - SUM(CASE WHEN dcxpf.valor_recibido IS NULL THEN 0 ELSE dcxpf.valor_recibido END)) AS calculo 
	into intereses_fianza
	FROM plan_intereses_fianza pif 
	LEFT JOIN desglose_contable_cuenta_por_pagar_fianza dcxpf ON dcxpf.id_plan_intereses_fianza = pif.id 
	WHERE pif.id_credito = iden_credito
	and pif.deteriorado IS FALSE 
	GROUP BY pif.id) as c;
	intereses_fianza = ceil(intereses_fianza);
	RAISE info 'intereses_fianza: %', intereses_fianza;

	-- Se calcula el valor de intereses de fianza con deteriorado
	SELECT coalesce(SUM(calculo), 0) FROM (SELECT (pif.valor_int_fianza - SUM(CASE WHEN dcxpf.valor_recibido IS NULL THEN 0 ELSE dcxpf.valor_recibido END)) AS calculo 
	into intereses_fianza_det
	FROM plan_intereses_fianza pif 
	LEFT JOIN desglose_contable_cuenta_por_pagar_fianza dcxpf ON dcxpf.id_plan_intereses_fianza = pif.id 
	WHERE pif.id_credito = iden_credito
	and pif.deteriorado IS true 
	GROUP BY pif.id) as c;
	intereses_fianza_det = ceil(intereses_fianza_det);
	RAISE info 'intereses_fianza_det: %', intereses_fianza_det;

	-- Se obtiene la tasa de originación
	select coalesce(san.tasa, 0) 
	into tasa_originacion
	from credito cre 
	inner join simulador_analista san on san.id_credito = cre.id
	where cre.id = iden_credito;
	RAISE info 'tasa_originacion: %', tasa_originacion;

	-- Se obtiene el tipo segmento
    select case when segmento = 'Producto AAA' then 1 else 2 end
    into tipo_segmento
    FROM segmento
    WHERE id_credito = iden_credito
    ORDER BY id DESC LIMIT 1;
	RAISE info 'tipo_segmento: %', tipo_segmento;
    
   	-- Se obtiene la tasa de financiamiento de la configuración de la fianza
 	SELECT coalesce(valor_calculo, 0)
   	into tasa_financiacion
   	FROM configuracion_fianza cfi  
   	WHERE cfi.concepto_calculo = 'CUENTA_X_COBRAR_INTERESES_FIANZA' AND cfi.id_segmento = tipo_segmento;
   	RAISE info 'tasa_financiacion: %', tasa_financiacion;
   
	-- Se obtiene la tasa de usura
	SELECT coalesce(ccr.maximo, 0)
	into tasa_usura
	FROM configuracion_credito ccr
	WHERE ccr.tipo = 'tasa'
	AND ccr.tipo_valor = 'BLOQUEANTE';
	tasa_usura = coalesce(tasa_usura, 0);
	RAISE info 'tasa_usura: %', tasa_usura;
   
	-- Se obtiene tasa interes aplicable
	if tasa_financiacion != 0 and (tasa_financiacion < tasa_originacion or tasa_originacion = 0) and (tasa_financiacion < tasa_usura or tasa_usura = 0) then 
		tasa_interes_aplicable = tasa_financiacion;
	else 
		if tasa_originacion != 0 and (tasa_originacion < tasa_financiacion or tasa_financiacion = 0) and (tasa_originacion < tasa_usura or tasa_usura = 0) then 
			tasa_interes_aplicable = tasa_originacion;
		end if;
	end if;
	RAISE info 'tasa_interes_aplicable: %', tasa_interes_aplicable;

	-- Se obtienen los días para el cálculo de intereses corridos de fianza
	IF DATE_PART('month', ultimo_periodo_cierre) = DATE_PART('month', fecha_vencimiento) THEN 
		dias_calculo = DATE_PART('day', fecha_vencimiento);
	else 
		dias_calculo = DATE_PART('day', fecha_vencimiento) + 30;
	end if;
	RAISE info 'dias_calculo: %', dias_calculo;

	-- Se obtiene el valor de los intereses de fianza corridos
	intereses_fianza_corrido = round(valor_fianza * tasa_interes_aplicable / 100 / 30 * dias_calculo, 0);
	RAISE info 'intereses_fianza_corrido: %', intereses_fianza_corrido;

	return intereses_fianza + intereses_fianza_det + intereses_fianza_corrido;

END; 
$function$
;