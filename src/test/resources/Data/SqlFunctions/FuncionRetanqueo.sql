CREATE OR REPLACE FUNCTION public.calculos_simulador_retanqueo(creditopadre numeric, tasa numeric, plazo numeric, diashabilesintereses numeric, monto numeric, sumamontocarteras numeric, v_fecha character varying)
 RETURNS TABLE(tipocalculos character varying, calculoprimaanticipadaseguro numeric, calculocuotacorriente numeric, resultgmf41000 numeric, calculoprimanodevengada numeric, calculoprimaneta numeric, calculovalorfianza numeric, fianzapadre numeric, resultfianza numeric, resultestudiocredito numeric, saldoaldia numeric, calculoremanenteestimado numeric)
 LANGUAGE plpgsql
AS $function$

/*ThainerPerez 03/Marzo/2022 -  Se agrega ceiling en la funcion de base de datos para que aproxime hacia arriba*/

declare

-- constantes
iva numeric = 1.19;
tasaXmillon numeric = 4625;
gmf41000 numeric = 0.004;
tasaUno numeric = tasa / 100;
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
resultFianza numeric;
resultEstudioCredito numeric;
resultGmf41000 numeric;
saldoAlDia numeric;
fechaEstudioCredito Date = v_fecha::date;
fechaDesembolso Date;
estadoCreditoHijo varchar;

begin

-- consultar prima padre
select
case when c.numero_radicacion = null then 'true'
else 'false' end as prima
into tienePrimaPadre
from credito c
inner join desglose d on d.id_credito = c.id
inner join prima_seguro_anticipada ps on ps.id_desglose = d.id
where 1=1
and d.desglose_seleccionado is true 
and c.credito_activo is true
and c.numero_radicacion = creditoPadre;

-- consulta el descuento de la prima anticipada
select valor
into descuentoPrimaAnticipada
from parametro_configuracion
where nombre = 'PRIMA_SEGURO_PERIODOS_DESCONTAR'
order by id desc;

-- consulta para obtener la fianza padre
select coalesce(ceiling(d.valor_fianza),0) fianzaPadre
into fianzaPadre
from desglose d
where id_credito in (select  id from credito where numero_radicacion = creditoPadre)
and desglose_seleccionado is true
limit 1;

-- consulta para obtener el id del credito y la pagaduría
select c.id, c.id_pagaduria into idCredito, idPagaduria from credito c where numero_radicacion = creditoPadre;

-- consultar el saldo al dia
select ceiling(saldo_al_dia) into saldoAlDia from saldo_al_dia sad where id_credito = idCredito;

-- fehca desembolso crédito hijo
select coalesce(sian.fecha_desembolso, current_date), credito.estado
into fechaDesembolso, estadoCreditoHijo
from simulador_analista sian
inner join credito on(credito.id = sian.id_credito)
where id_credito in (select credito.id from credito where numero_radicacion = 79796)
order by credito.id desc limit 1;

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
select coalesce(ceiling(obtener_valor_estudio_credito(fechaEstudioCredito, idCredito, idPagaduria, false)),0) estudioCreditoPadre into estudioCreditoPadre;

-- consulta para obtener valores capitalizador
select ceiling(estudio_credito), segunda_tasa, fianza, mes_cambio_tasa
into estudioCredito, tasaDos, tasaFianza, mesDos
from configuracion_capitalizacion_cxc ccc
where tasa_inicial = tasa;

-- calcular periodo de gracia
if (plazo < descuentoPrimaAnticipada)
	then
		periodoGracia = Ceiling(diasHabilesIntereses / 30);
		descuentoPrimaAnticipada = periodoGracia + plazo;

end if;

-- calcular prima credito padre
select ceiling(valor) into primaPadre
from prima_seguro_anticipada psa
join desglose d2
on d2.id=psa.id_desglose
join credito c2
on c2.id = d2.id_credito
where d2.desglose_seleccionado is true
and c2.numero_radicacion = creditoPadre;

-- consultar monto padre
select ceiling(c2.monto_aprobado) into montoPadre
from prima_seguro_anticipada psa
join desglose d2
on d2.id=psa.id_desglose
join credito c2
on c2.id = d2.id_credito
where d2.desglose_seleccionado is true
and c2.numero_radicacion = creditoPadre;

-- consultar meses activos padre
with ConteoPeriodos as (
select (count(fechas)+complemento-1) periodos
from (
select id_credito, generate_series(mes_contable, now(), cast('1 month' as interval) )fechas,
case when date_part('day', now()) < date_part('day', mes_contable) then 1 else 0 end complemento
from movimiento_contable as mc
where id_credito in(select c2.id from credito c2 where c2.numero_radicacion = creditoPadre)
and tipo_transaccion = 'ACTIVACION_CREDITO'
) x group by complemento, id_credito
)
select periodos into mesesActivosPadre from ConteoPeriodos;

-- se realizan los calculos

-- calcular prima anticipada de seguro
calculoPrimaAnticipadaSeguro = ceiling((tasaXmillon / 1000000 * descuentoPrimaAnticipada) * monto);

-- calcular cuota corriente
if(plazo < mesDos) then
  calculoCuotaCorriente = ceiling(monto / ((power((1 + tasaUno), (plazo)) - 1) / (tasaUno * power((1 + tasaUno), (plazo)))));
else
  tasaDos = tasaDos / 100;
  calculoCuotaCorriente = ceiling(monto
  / ((power((1 + tasaUno), (mesDos - 1)) - 1) / (tasaUno * power((1 + tasaUno), (mesDos - 1)))
  + ((power((1 + tasaDos), (plazo - (mesDos - 1))) - 1)
  / (tasaDos * power((1 + tasaDos), (plazo - (mesDos - 1)))))
  / (power((1 + tasaUno), (mesDos - 1)))));
end if;

-- calcular 4*1000
resultGmf41000 = ceiling(sumaMontoCarteras * gmf41000);

-- calcular prima no devengada
calculoPrimaNoDevengada = ceiling(coalesce(primaPadre, 0) - ((coalesce(montoPadre, 0) * tasaXmillon) / variableMillon) * mesesActivosPadre);

-- calcular prima neta
calculoPrimaNeta = ceiling(calculoPrimaAnticipadaSeguro - calculoPrimaNoDevengada);

-- calcular estudio de credito retanqueo hijo
calculoEstudioCreditoHijo = ceiling(monto / (1 + (estudioCredito / 100) * iva + (tasaFianza / 100) * iva +
							(tasaXmillon / 1000000 * descuentoPrimaAnticipada)) * (estudioCredito / 100) * iva);

-- calcular valor de la fianza
calculoValorFianza = ceiling(monto * (tasaFianza / 100) * 1.19);

-- result fianza
resultFianza = calculoValorFianza - fianzaPadre;
if (resultFianza < 0)
  then
  resultFianza = 0;
end if;

-- estudio de crédito
resultEstudioCredito = ceiling(monto * (estudioCredito / 100) * iva) - estudioCreditoPadre;
if (resultEstudioCredito < 0)
  then
  resultEstudioCredito = 0;
end if;

-- calculo remanente estimado
calculoRemanenteEstimado = ceiling(monto - (saldoAlDia + resultFianza + calculoPrimaNeta + resultEstudioCredito + sumaMontoCarteras + resultGmf41000));

-- validaciones si es anticipado o mensualizado
if (tienePrimaPadre = '')
	then
		tipoCalculos = 'mensualizado';
	else
		tipoCalculos = 'anticipado';
end if;

-- return values
return query
select tipoCalculos,
coalesce(calculoPrimaAnticipadaSeguro, 0),
coalesce(calculoCuotaCorriente, 0),
coalesce(resultGmf41000, 0),
coalesce(calculoPrimaNoDevengada, 0),
coalesce(calculoPrimaNeta, 0),
coalesce(calculoValorFianza, 0),
coalesce(fianzaPadre, 0),
coalesce(resultFianza, 0),
coalesce(resultEstudioCredito, 0),
coalesce(saldoAlDia, 0),
coalesce(calculoRemanenteEstimado, 0);
end;

$function$
;