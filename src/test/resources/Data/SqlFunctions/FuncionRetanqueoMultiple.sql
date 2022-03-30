CREATE OR REPLACE FUNCTION public.autopruebas_retanqueo_multiple_cal_simulador(v_cedula text, v_nombre_pagaduria text, v_tasa numeric, v_plazo numeric, v_diashabilesintereses numeric, v_monto numeric, v_sumamontocarteras numeric, v_fecha text)
 RETURNS TABLE(tipocalculos character varying, calculoprimaanticipadaseguro numeric, calculocuotacorriente numeric, resultgmf41000 numeric, calculoprimanodevengada numeric, calculoprimaneta numeric, r_sumafianzas numeric, r_fianzapadre numeric, r_fianza_neta numeric, resultestudiocredito numeric, saldoaldia numeric, calculoremanenteestimado numeric)
 LANGUAGE plpgsql
AS $function$
 
 declare
 -- ============================================================================
 -- Autor: Equipo automatizacion Pruebas.
 -- Fecha: 24/Enero/2021 Version 1.0 ThainerPerez
 -- Se crea la funcion que retorna los calculos de RetanqueoMultiple para los simuladores
 -- Fecha: 04/Marzo/2022 Version 1.3 ThainerPerez
 -- Se ajusta el calculo de Prima no devengada
 -- Se aproxima el valor remplazando los ceiling por ceiling, decimales hacia arriba
 -- ============================================================================
 
 
 -- constantes Globales
 iva numeric = 1.19;
 tasaXmillon numeric = 4625;
 gmf41000 numeric = 0.004;
 tasaUno numeric = v_tasa / 100;
 variableMillon numeric = 1000000;
 -- variables de calculos
 calculoPrimaAnticipadaSeguro numeric;
 calculoEstudioCreditoHijo numeric;
 calculoValorFianza numeric;
 calculoPrimaNeta numeric;
 calculoPrimaNoDevengada numeric;
 calculoCuotaCorriente numeric;
 calculoRemanenteEstimado numeric;
 -- variables
 tienePrimaPadre varchar;
 descuentoPrimaAnticipada numeric;
 fianzaPadre numeric;
 estudioCreditoPadre numeric;
 idCredito integer;
 v_nombrePagaduria text;
 idPagaduria integer;
 estudioCredito numeric;
 tasaFianza numeric;
 tasaDos numeric;
 mesDos numeric;
 periodoGracia numeric;
 primaPadre numeric;
 montoPadre numeric;
 mesesActivosPadre numeric;
 tipoCalculos varchar;
 r_fianza_neta numeric;
 resultEstudioCredito numeric;
 resultGmf41000 numeric;
 saldoAlDia numeric;
 fechaEstudioCredito Date = v_fecha::date;
 fechaDesembolso Date;
 estadoCreditoHijo varchar;
 
 
 begin
 
 -- consulta el descuento de la prima anticipada
 select
 valor
 into
 descuentoPrimaAnticipada
 from
 parametro_configuracion
 where
 nombre = 'PRIMA_SEGURO_PERIODOS_DESCONTAR'
 order by
 id desc;
 
 
 -- consulta para obtener el id del credito y la pagadurÃƒÂ­a
 select
 p.id
 into
 idPagaduria
 from pagaduria p
 where
 upper(p.nombre) in (upper(v_nombre_pagaduria));
 
 
 /*Consulta Saldo al dia*/
 select
 coalesce(sum(ceiling(saldo_al_dia)),0)
 into
 saldoAlDia
 from
 saldo_al_dia sad
 where
 id_credito in (select distinct c.id 
							from credito c 
							join plan_de_pagos pdp on c.id =  pdp.id_credito 
							join cliente cl on c.id_cliente = cl.id
							right join desglose_contable_pago dc on pdp.id = dc.id_plan_de_pago 
							where cl.identificacion = v_cedula
							 and c.estado = 'ACTIVO'
							 and c.credito_activo is true
							 and c.id_pagaduria = idPagaduria);
 
 
 -- consulta para obtener valores capitalizador
 select
 estudio_credito,
 segunda_tasa,
 fianza,
 mes_cambio_tasa
 into
 estudioCredito,
 tasaDos,
 tasaFianza,
 mesDos
 from
 configuracion_capitalizacion_cxc ccc
 where
 tasa_inicial = v_tasa;
 
 raise notice 'Tasa Original %',v_tasa;
 
 raise notice 'EstudioCredito Tasa TasaFianza mesDos % % % % ', estudioCredito, tasaDos, TasaFianza, mesDos;
 
 -- calcular periodo de gracia
 if (v_plazo < descuentoPrimaAnticipada)
 then
 periodoGracia = ceiling(v_diashabilesintereses / 30);
 
 descuentoPrimaAnticipada = periodoGracia + v_plazo;
 end if;
 
 -- calcular cuota corriente
 if(v_plazo < mesDos) then
   calculoCuotaCorriente = v_monto / ((power((1 + tasaUno), (v_plazo)) - 1) / (tasaUno * power((1 + tasaUno), (v_plazo))));
 else
   tasaDos = tasaDos / 100;
 
 calculoCuotaCorriente = ceiling(v_monto
   / ((power((1 + tasaUno), (mesDos - 1)) - 1) / (tasaUno * power((1 + tasaUno), (mesDos - 1)))
   + ((power((1 + tasaDos), (v_plazo - (mesDos - 1))) - 1)
   / (tasaDos * power((1 + tasaDos), (v_plazo - (mesDos - 1)))))
   / (power((1 + tasaUno), (mesDos - 1)))));
 end if;
 
 raise notice 'CuotaCorriente %',
 (calculoCuotaCorriente);
 -- calcular 4*1000
 resultGmf41000 = ceiling(v_sumamontocarteras * gmf41000);
 
 raise notice '4X100  %',
 (resultGmf41000);
 
 -- calcular prima anticipada de seguro
 calculoPrimaAnticipadaSeguro = ceiling((v_monto / variableMillon)*(tasaXmillon * descuentoPrimaAnticipada));
 raise notice 'PrimaAnticipada %',
 (calculoPrimaAnticipadaSeguro);
 
 -- calcular prima no devengada
 with ConteoPeriodos as (
 select
 (count(fechas)+ complemento-1) periodos, id_credito
 from
 (
 select
 id_credito,
 generate_series(mes_contable, now(), cast('1 month' as interval) )fechas,
 case
 when date_part('day', now()) < date_part('day', mes_contable) then 1
 else 0
 end complemento
 from
 movimiento_contable as mc
 where
 id_credito in(
 select
 c2.id
 from
 credito c2
 where
 c2.numero_radicacion in (select distinct c.numero_radicacion 
							from credito c 
							join plan_de_pagos pdp on c.id =  pdp.id_credito 
							join cliente cl on c.id_cliente = cl.id
							right join desglose_contable_pago dc on pdp.id = dc.id_plan_de_pago 
							where cl.identificacion = v_cedula
							 and c.estado = 'ACTIVO'
							 and c.credito_activo is true
							 and c.id_pagaduria = idPagaduria))
 and tipo_transaccion = 'ACTIVACION_CREDITO'
 ) x
 group by
 complemento,
 id_credito
 ),
 monto_padre_creditos as (select
 ceiling(c.monto_aprobado) montoCredito, c.id, psa.valor
 from
 prima_seguro_anticipada psa
 join desglose d2
 on
 d2.id = psa.id_desglose
 join ConteoPeriodos c2
 on
 c2.id_credito = d2.id_credito
 join credito c on c.id = c2.id_credito
 where
 d2.desglose_seleccionado is true)
 select
 coalesce(sum(ceiling(coalesce(mp.valor,0)-(coalesce(mp.valor,0)/descuentoPrimaAnticipada*periodos))),0) primaNoDevengada
 into
 calculoPrimaNoDevengada
 from
 ConteoPeriodos ct
    join monto_padre_creditos mp on ct.id_credito = mp.id;
 
 raise notice 'Prima no devengada  %',
 (calculoPrimaNoDevengada);
 
 -- calcular prima neta
 calculoPrimaNeta = calculoPrimaAnticipadaSeguro - calculoPrimaNoDevengada;
 if (calculoPrimaNeta < 0)
   then
   calculoPrimaNeta := calculoPrimaAnticipadaSeguro;
 end if;
 raise notice 'Prima Neta  %',
 (calculoPrimaNeta);
 
 -- consulta para obtener la fianza padre
 select
 coalesce(ceiling(sum(d.valor_fianza)), 0) fianzaPadre
 into
 fianzaPadre
 from
 desglose d
 where
 id_credito in (
 select
 id
 from
 credito
 where
 numero_radicacion in (select distinct c.numero_radicacion 
							from credito c 
							join plan_de_pagos pdp on c.id =  pdp.id_credito 
							join cliente cl on c.id_cliente = cl.id
							right join desglose_contable_pago dc on pdp.id = dc.id_plan_de_pago 
							where cl.identificacion = v_cedula
							 and c.estado = 'ACTIVO'
							 and c.credito_activo is true
							 and c.id_pagaduria = idPagaduria))
 and desglose_seleccionado is true;
 
 raise notice 'Fianza Padre %',
 (fianzaPadre);
 
 
 -- calcular valor de la fianza
 calculoValorFianza = ceiling(((v_monto * tasaFianza) / 100) * iva);
 raise notice 'Valor Fianza  %',
 (calculoValorFianza);
 
 -- result fianza
 r_fianza_neta = calculoValorFianza - fianzaPadre;
 if (r_fianza_neta < 0)
   then
   r_fianza_neta = 0;
 end if;
 
 r_sumaFianzas := (fianzaPadre + r_fianza_neta);
 
 r_fianzaPadre := fianzaPadre;
 
 raise notice 'Suma Fianzas %',
 (r_sumaFianzas);
 
 raise notice 'Fianza Neta %',
 (r_fianza_neta);
 
 
 -- calcular estudio de credito retanqueo hijo
 calculoEstudioCreditoHijo = ceiling(((v_monto * estudioCredito) / 100) + (((v_monto * estudioCredito) / 100) * (iva - 1)));
 
 raise notice 'EstudioCredito  %',
 (calculoEstudioCreditoHijo);
 
 -- fehca desembolso crÃ©dito hijo
 select coalesce(sian.fecha_desembolso, current_date), credito.estado
 into fechaDesembolso, estadoCreditoHijo
 from simulador_analista sian
 inner join credito on(credito.id = sian.id_credito)
 where id_credito in (select credito.id from credito
 left join cliente on(cliente.id = credito.id_cliente)
 where cliente.identificacion = v_cedula
 order by credito.id desc limit 1);
 
 if (estadoCreditoHijo = 'LLAMADA_BIENVENIDA' or estadoCreditoHijo = 'PENDIENTE_DESEMBOLSO')
 	then
 		if (fechaDesembolso <= current_date)
 		then
 			fechaEstudioCredito = fechaDesembolso;
 		else
 			fechaEstudioCredito = current_date;
 		end if;
 end if;
 
 raise notice 'fechaEstudioCredito %', fechaEstudioCredito;
 
 -- cunsulta para obtener el estudio del credito padre
 select coalesce(sum(ceiling(public.obtener_valor_estudio_credito(fechaEstudioCredito, c.id::integer, c.id_pagaduria::integer , false))),0) estudioCredito
 into
 estudioCreditoPadre
 from credito c
 where c.numero_radicacion in (select distinct c.numero_radicacion 
							from credito c 
							join plan_de_pagos pdp on c.id =  pdp.id_credito 
							join cliente cl on c.id_cliente = cl.id
							right join desglose_contable_pago dc on pdp.id = dc.id_plan_de_pago 
							where cl.identificacion = v_cedula
							 and c.estado = 'ACTIVO'
							 and c.credito_activo is true
							 and c.id_pagaduria = idPagaduria);
 
 -- estudio de crÃƒÂ©dito
 resultEstudioCredito = calculoEstudioCreditoHijo - estudioCreditoPadre;
 
 if (resultEstudioCredito < 0)
   then
   resultEstudioCredito = 0;
 end if;
 
 raise notice 'EstudioCredito  %',
 (resultEstudioCredito);
 
 -- calculo remanente estimado
 calculoRemanenteEstimado = ceiling(v_monto - (saldoAlDia + r_fianza_neta + resultEstudioCredito + v_sumamontocarteras + resultGmf41000 + calculoPrimaNeta));
 
 raise notice 'RemanenteEstimado  %',
 (calculoRemanenteEstimado);
 
 -- validaciones si es anticipado o mensualizado
 if (tienePrimaPadre = '')
 then
 tipoCalculos = 'mensualizado';
 else
 tipoCalculos = 'anticipado';
 end if;
 -- return values
 return query
 select
 tipoCalculos,
 coalesce(calculoPrimaAnticipadaSeguro, 0),
 coalesce(calculoCuotaCorriente, 0),
 coalesce(resultGmf41000, 0),
 coalesce(calculoPrimaNoDevengada, 0),
 coalesce(calculoPrimaNeta, 0),
 coalesce(r_sumaFianzas, 0),
 coalesce(fianzaPadre, 0),
 coalesce(r_fianza_neta, 0),
 coalesce(resultEstudioCredito, 0),
 coalesce(saldoAlDia, 0),
 coalesce(calculoRemanenteEstimado, 0);
 end;
 
 $function$
;