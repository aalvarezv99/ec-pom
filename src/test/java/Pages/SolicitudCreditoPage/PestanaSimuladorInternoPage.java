package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PestanaSimuladorInternoPage {
	WebDriver driver;
	
	public By ResultMontoSoli;
	public By EstudioCreditoIVA;
	public By ValorFianza;
	public By Gmf4100;
	public By Valorinteresesini;
	public By PrimaAnticipadaSeguro;
	public By RemanenteEstimado;
	public By CuotaCorriente;
	public By edad;
	public By MontoMaximoSugerido;
	public By btnGuardar;
	public By Solicitar;
	public By FiltroCedula;
	public By EditarVer;
	public By inputMesada;
	public By inputSalud;
	public By Guardar;
	public By SgdPestana;
	public By MesDeAfecatcion;
	public By ListaMes;
	public By anoAfectacion;
	public By CalcularDesglose;
	public By FechaDesembolso;
	public By FechasManuales;
	
	public By MontoSolicitado;
	public By CapitalTotal;
	public By ValorCuota;
	public By PrimaAnticipadaSeguroAsesor;
	public By MontoMaximoAsesor;
	public By CapacidadAsesor;
	public By ValorEstudioCreditoCXC;
	public By ValorFianzaCXC;
	public By Gravamento4x1000;
	public By ValoraDesembolsar;
	public By MontoAsesor;
	public By PlazoAsesor;
	public By TasaAsesor;
	public By IngresosAsesor;
	public By DescuentosLey;
	public By DescuentosNomina;
	public By DescuentoAfiliaciones;
	public By GuardarSimulacion;
	public By Aprobar;
	public By CompraSaneamiento;
	public By ValorCompraCartera;
	public By Monto;
	public By SimuladorInternorValoraDesembolsar;
	public By InteresesInicialesSimuladorAnalista;
	
	public PestanaSimuladorInternoPage(WebDriver driver) {
		
		ResultMontoSoli = By.id("formSimuladorCredito:montoTotalCr_hinput");
		EstudioCreditoIVA = By.id("formSimuladorCredito:estudioCr_hinput");
        ValorFianza = By.id("formSimuladorCredito:fianzaCr_hinput");
        Gmf4100 = By.id("formSimuladorCredito:gmfCr_hinput");
        Valorinteresesini = By.id("formSimuladorCredito:diasICr_hinput");
        PrimaAnticipadaSeguro = By.id("formSimuladorCredito:primaSeguro_hinput");
        RemanenteEstimado = By.id("formSimuladorCredito:remanenteCr_hinput");         						   
        CuotaCorriente = By.id("formSimuladorCredito:cuotaCr_hinput");
        edad = By.id("formSimuladorCredito:edad_hinput");
        MontoMaximoSugerido = By.id("formSimuladorCredito:montoMax_hinput");
        btnGuardar = By.id("formSimuladorCredito:guardar_simulador_boton");
        Solicitar=By.id("formSimuladorCredito:solicitar_boton");
        FiltroCedula=By.id("form:listaCreditos:identificacion_analisis_filtro:filter");
        EditarVer=By.id("form:listaCreditos:0:j_idt98");
        inputMesada =By.xpath("//input[starts-with(@id,'simuladorAnalista:j_idt99') and contains(@id,'docs_carrusel_analista_a_input_input')]");
	    inputSalud  =By.xpath("//input[starts-with(@id,'simuladorAnalista:j_idt104') and contains(@id,'descuentos_ley_analista_a_input_input')]");
	    Guardar = By.id("simuladorAnalista:guardar_analista_a_boton");
	    SgdPestana = By.id("simuladorAnalista:vista_siguiente_analista_a");
	    MesDeAfecatcion = By.id("formSimulador:mesAfectacion_label");
	    ListaMes= By.xpath("//li[contains(@class,'ui-selectonemenu-item')]");
	    anoAfectacion = By.id("formSimulador:anioAfectacion");
	    CalcularDesglose = By.id("formSimulador:calcular_desglose_analista_b_boton");
	    CompraSaneamiento = By.id("formSimulador:valorCompraCartera_hinput");
	    ValorCompraCartera = By.id("formSimulador:valorCompraCartera_hinput");  
	    FechaDesembolso=By.id("formSimulador:fechaDesembolso_input");
	    FechasManuales=By.xpath("//span[text()='Aplicar fechas manuales']");
	    
	    
	    DescuentoAfiliaciones = By.id("simuladorAnalista:j_idt109:0:descuentos_nomina_analista_a_input_input");
	    MontoSolicitado = By.id("formSimulador:montoAprobado_hinput");
	    CapitalTotal = By.id("formSimulador:totalCredito_hinput");    
	    ValorCuota = By.id("formSimulador:valorTotalCuota_input");
	    PrimaAnticipadaSeguroAsesor = By.id("formSimulador:primaAnticipadaSeguro_hinput");
	    MontoMaximoAsesor =By.id("formSimulador:montoMaximoSugerencias_hinput");
	    CapacidadAsesor= By.id("formSimulador:valorCuotaMontoMaximoSugerencias_hinput");     
	    ValorEstudioCreditoCXC= By.id("formSimulador:valorEstudio_hinput");
	    ValorFianzaCXC = By.id("formSimulador:valorFianza_hinput");
	    Gravamento4x1000 = By.id("formSimulador:cuatroPorMil_hinput");
	    ValoraDesembolsar = By.id("formSimulador:valorDesembolsar_hinput");
	    SimuladorInternorValoraDesembolsar =   By.id("formSimuladorCredito:remanenteCr_hinput");
	    MontoAsesor = By.id("formSimulador:monto_hinput");
	    PlazoAsesor = By.id("formSimulador:plazo");
	    TasaAsesor = By.id("formSimulador:tasaExcepcion_hinput");
	    IngresosAsesor = By.id("formSimulador:ingresos");
	    DescuentosLey = By.id("formSimulador:descuentosLey");
	    DescuentosNomina = By.id("formSimulador:descuentosNomina");
	    GuardarSimulacion=By.id("formSimulador:guardar");
	    Aprobar = By.id("form:aprobar");
	    Monto =By.id("formSimuladorCredito:montoCr_hinput");
	    InteresesInicialesSimuladorAnalista= By.id("formSimulador:diasInteresInicial_hinput");
	    
	    
	}
}
