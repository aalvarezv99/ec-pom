package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SimuladorAsesorPages {
    WebDriver driver;

    //Componentes iniciales
    public By desPagaduria;
    public By contPagaduria;
    public By listPagaduria;
    public By inputIdentificacion;
    public By selectFecha;
    public By contFecha;
    public By contMes;
    public By contAno;
    public By contDia;
    public By listDia;
    public By desOficina;
    public By contOficina;
    public By listOficina;
    public By desOcupacion;
    public By contOcupacion;
    public By listOcupacion;
    public By checkLibre;
    public By checkControlado;
    public By notificacionGuardado;

    //Componentes valores
    public By inputTasa;
    public By inputPlazo;
    public By labelTasa;
    public By inputMonto;
    public By diasIntInicial;
    public By inputIngresos;
    public By inputDescLey;
    public By inputdDescNomina;
    public By vlrCompra;
    public By btnGuardar;
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
    public By notificacionCreacionCliente;
    public By CapacidadAproximada;

    //Formulario Cliente
    public By btnCrearCliente;
    public By selectContrato;
    public By contContrato;
    public By listContrato;
    public By selectFechaIngre;
    public By inputPrNombre;
    public By inputSeNombre;
    public By inputPrApellido;
    public By inputSeApellido;
    public By inputCorreo;
    public By inputCelular;
    public By selectDpto;
    public By contDpto;
    public By listDpto;
    public By selectCiudad;
    public By contCiudad;
    public By listCiudad;
    public By btnCrear;

    //Componentes cargar
    public By fileAutorizacion;
    public By fileCedula;
    public By fileNomina;
    public By btnSoliConsulta;
    public By ElementosCarga;

    public By notificacion;
    public By inputTasaFiltro;

	public By listTasa;
	public By optionsTasa;

    public SimuladorAsesorPages(WebDriver driver) {
        this.driver = driver;
        desPagaduria = By.id("formSimuladorCredito:pagaduria_label");
        contPagaduria = By.xpath("/html/body/div[7]/div[2]/ul");
        listPagaduria = By.xpath("/html/body/div[7]/div[2]/ul/li");
        inputIdentificacion = By.id("formSimuladorCredito:identi");
        selectFecha = By.id("formSimuladorCredito:fechaNacimiento_input");
        contFecha = By.id("ui-datepicker-div");
        contMes = By.className("ui-datepicker-month");
        contAno = By.className("ui-datepicker-year");
        contDia = By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr");
        listDia = By.xpath("/html/body/div[7]/table/tbody/tr/td/a");
        desOficina = By.id("formSimuladorCredito:oficina_label");
        contOficina = By.xpath("/html/body/div[13]/div/ul");
        listOficina = By.xpath("/html/body/div[13]/div/ul/li");
        desOcupacion = By.id("formSimuladorCredito:actividad_label");
        //contOcupacion = By.xpath("/html/body/div[11]/div/ul");
        contOcupacion = By.xpath("//*[@id=\"formSimuladorCredito:actividad_items\"]");
        listOcupacion = By.xpath("//li[starts-with(@id, 'formSimuladorCredito:actividad')]");
        notificacionGuardado = By.xpath("//*[starts-with(@class,'ui-growl-title')]");
        checkLibre = By.id("formSimuladorCredito:j_idt98:0");
        checkControlado = By.id("formSimuladorCredito:j_idt98:1");


        //Componentes valores
        inputTasa = By.id("formSimuladorCredito:tasa_input");
        inputPlazo = By.id("formSimuladorCredito:plazo_input");
        labelTasa = By.id("formSimuladorCredito:tasa_label");
        inputTasaFiltro = By.id("formSimuladorCredito:tasa_filter");
        inputMonto = By.id("formSimuladorCredito:monto_input");
        diasIntInicial = By.id("formSimuladorCredito:iIniciales_input");
        inputIngresos = By.id("formSimuladorCredito:ingresos");
        inputDescLey = By.id("formSimuladorCredito:ley");
        inputdDescNomina = By.id("formSimuladorCredito:nomina");
        vlrCompra = By.id("formSimuladorCredito:comprasSaneamiento");
        //btnGuardar = By.id("formSimuladorCredito:guardar_simulador_boton");
        btnGuardar = By.xpath("//*[text()='Guardar']");
        ResultMontoSoli = By.id("formSimuladorCredito:montoTotal_hinput");
        EstudioCreditoIVA = By.id("formSimuladorCredito:estudio_hinput");
        ValorFianza = By.id("formSimuladorCredito:fianzaCr_hinput");
        Gmf4100 = By.id("formSimuladorCredito:gmf_hinput");
        Valorinteresesini = By.id("formSimuladorCredito:diasI_hinput");
        PrimaAnticipadaSeguro = By.id("formSimuladorCredito:primaSeguro_hinput");
        RemanenteEstimado = By.id("formSimuladorCredito:remanente_hinput");
        CuotaCorriente = By.id("formSimuladorCredito:cuota_hinput");
        edad = By.id("formSimuladorCredito:edad_hinput");
        MontoMaximoSugerido = By.id("formSimuladorCredito:montoMax_hinput");
        CapacidadAproximada = By.id("formSimuladorCredito:capacidad_hinput");

        //FormCliente
        btnCrearCliente = By.id("formSimuladorCredito:analisis");
        selectContrato = By.id("formSimuladorCredito:tipoContrato_label");
        contContrato = By.xpath("//ul[starts-with(@id,'formSimuladorCredito:tipoContrato_items') and starts-with(@class,'ui-select')]");
        listContrato = By.xpath("//li[starts-with(@id,'formSimuladorCredito:tipoContrato_')]");
        selectFechaIngre = By.id("formSimuladorCredito:fechaIngreso_input");
        inputPrNombre = By.id("formSimuladorCredito:primerNombre");
        inputSeNombre = By.id("formSimuladorCredito:segundoNombre");
        inputPrApellido = By.id("formSimuladorCredito:primerApellido");
        inputSeApellido = By.id("formSimuladorCredito:segundoApellido");
        inputCorreo = By.id("formSimuladorCredito:email");
        inputCelular = By.id("formSimuladorCredito:cel");
        selectDpto = By.id("formSimuladorCredito:departamento_label");
        contDpto = By.xpath("//ul[starts-with(@id,'formSimuladorCredito:departamento_items') and starts-with(@class,'ui-select')]");
        listDpto = By.xpath("//li[starts-with(@id,'formSimuladorCredito:departamento_')]");
        selectCiudad = By.id("formSimuladorCredito:ciudad_label");
        contCiudad = By.xpath("//ul[starts-with(@id,'formSimuladorCredito:ciudad_items') and starts-with(@class,'ui-select')]");
        listCiudad = By.xpath("//li[starts-with(@id,'formSimuladorCredito:ciudad_')]");
        btnCrear = By.xpath("//*[starts-with(@id,'formSimuladorCredito:confirmar_crear_cliente_boton')]");
        notificacionCreacionCliente = By.xpath("//*[@class='ui-growl-title']");


        //Documentos
        notificacion = By.xpath("//*[@class='ui-growl-title']");
        fileAutorizacion = By.id("formSimuladorCredito:aut_consulta_cargar_input");
        fileCedula = By.id("formSimuladorCredito:copia_cedula_cargar_input");
        fileNomina = By.id("formSimuladorCredito:desp_nomina_cargar_input");
        btnSoliConsulta = By.id("formSimuladorCredito:guardar");

		listTasa = By.xpath("//ul[@id = 'formSimuladorCredito:tasa_items']");
		optionsTasa = By.xpath("//li[starts-with(@id, 'formSimuladorCredito:tasa')]");
    }
}
