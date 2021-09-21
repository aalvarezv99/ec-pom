package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreditoSolicitudPage {
	WebDriver driver;
	
	//Pantalla principal
	public By inputFecha;
	public By inputCedula;
	public By contFecha;
	public By listFecha;
	public By selectVerEditar;
	public By countFilas;
	
	//Pestañas
	public By contPestanas;
	public By listPestanas;
	
	//FormSeguridad
	public By checkViable;
	public By btnGuardarSeg;
	
	//FormSimulador
	public By btnGuardarSim;
	public By btnSolicitarSim;
	
	//FormDigitalizacion
	public By contComponentes;
	public By listArchivo;
	public By checkCorrecto;
	public By checkIncorrecto;
	public By btnVerificacion;
	public By inputProforences;
	public By checkIdentConfirmada;
	public By btnRadicar;	
	public By btnguardarDi;
	//Files
	public By fileCarga;
	public By filePagare;
	public By filePEspecia;
	public By btnGuardarDig;
	public By checkCamDigital;
	
	//FormFormulario
	public By inputFechTermina;
	public By contDiasTermina;
	public By listDiasTermina;
	
	//FormFormulario - Datos Credito
	public By selectTipoContrato;
	public By contTipoContrato;
	public By listTipoContrato;
	public By selectDestCredito;
	public By contDestCredito;
	public By listDestCredito;
	
	//Form - Datos Solicitante
	public By checkSexoM;
	public By checkSexoF;
	public By selectEstadoCivil;
	public By contEstadoCivil;
	public By listEstadoCivil;
	public By inputCelular;
	public By dirResidencia;
	public By selectDptoRe;
	public By contDptoRe;
	public By listDptoRe;
	public By selectCiuRe;
	public By contCiuRe;
	public By listCiuRe;
	public By inputTelefono;
	public By checkSiCorrespondencia;
	public By checkNoCorrespondenca;
	public By dirCorrespondencia;
	public By selectDptoCorres;
	public By contDptoCorres;
	public By listDptoCorres;
	public By selectCiuCorres;
	public By contCiuCorres;
	public By listCiudCorres;
	public By selectTipoVi;
	public By contTipoVi;
	public By listTiVi;	
	public By btnGuardarForm;
	
	//Form Referencias
	public By selectReferencia;
	public By addReferencia;
	public By checkRefFami;
	public By checkRefPerso;
	public By checkRefCon;
	public By primerApellido;
	public By segunApellido;
	public By primerNombre;
	public By segunNombre;
	public By selectDptoRef;
	public By contDptoRef;
	public By listDptoRef;
	public By selectCiuRef;
	public By contCiuRef;
	public By listCiuRef;
	public By telResiRef;
	public By tldTraRef;
	public By selectRelRef;
	public By contRelRef;
	public By listRelRef;
	public By btnGuarRef;
	
	//FromReferencia-Verificacion
	public By cambiPestRef;
	public By contRefPrimer;
	public By checkRefPosiPrimer;
	public By checkSiPrimer;
	public By checkRefPosiSegund;
	public By checkSiSegund;
	public By contRefSegun;
	public By btnGuardarRef;
	
	//FormReferencia-Pagaduria
	public By checkSalarioSi;
	public By checkFechaSi;
	public By checkContratoSi;
	public By checkCargoSi;
	public By btnSoliAnalisis;
	
	public By SegundaPestana;
	public By ListBtnAddReference;
	public By notificacion;
	
	public CreditoSolicitudPage(WebDriver driver) {
		this.driver = driver;
		
		//Pantalla principal
		inputCedula = By.xpath("//input[@id='form:listaCreditos:identificacion_cred_filtro:filter']");
		inputFecha = By.id("form:listaCreditos:j_idt109_input");
		contFecha = By.id("ui-datepicker-div");
		listFecha = By.xpath("/html/body/div[11]/table/tbody/tr/td/a");
		countFilas = By.xpath("//*[@id=\"form:listaCreditos_data\"]/tr");
		selectVerEditar = By.xpath("//a[contains(@id,'ver_editar_cred')]");

		//Componentes seleccionar Formularios
		contPestanas = By.xpath("/html/body/div[2]/form/nav/div[2]/div[2]/div/ul");
		listPestanas = By.xpath("/html/body/div[2]/form/nav/div[2]/div[2]/div/ul/li/a");
		
		//FormSeguridad
		checkViable = By.xpath("//*[@id=\"formConsultas:estados0:0_clone\"]");
		btnGuardarSeg = By.xpath("//*[@id=\"formConsultas:j_idt133\"]/div/a[2]");
		
		//FormSimulador
		btnGuardarSim = By.id("formSimuladorCredito:j_idt243");
		btnSolicitarSim = By.id("formSimuladorCredito:j_idt306");
		
		//FormDigitalizacion
		contComponentes = By.id("form:dUiRepeat");
		listArchivo = By.xpath("/html/body/div[2]/div/div[2]/div/form/div/div[2]/div/div[2]/div[2]/div/div[4]/div[1]/div[1]/span/input");
		checkCorrecto = By.xpath("/html/body/div[2]/div/div[2]/div/form/div/div[2]/div/div[2]/div[2]/div/div[1]/div/div[1]/div/span/input");
		checkIncorrecto = By.xpath("/html/body/div[2]/div/div[2]/div/form/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div[1]/div/span/input");
		checkCamDigital = By.xpath("//*[@id=\"form:j_idt92\"]/div[5]/div/ul/li[2]/a");
		inputProforences = By.id("formRadicacion:proforenses");
		checkIdentConfirmada =  By.xpath("//*[@id=\"formRadicacion:resultadoProforenses:0_clone\"]");
		btnguardarDi = By.id("formRadicacion:j_idt166");
		btnRadicar = By.id("formRadicacion:radicar");
		
		btnVerificacion = By.id("form:verificacionCredito");
		//Files
		fileCarga = By.id("form:cargarDocumentos0_input");	
		btnGuardarDig = By.id("form:j_idt284");
		
		//Formulario
		inputFechTermina = By.id("form:fechaTerminacion_input");
		contDiasTermina = By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr");
		listDiasTermina = By.xpath("/html/body/div[14]/table/tbody/tr/td/a");
		
		selectTipoContrato = By.id("form:tipoContrato_label");
		contTipoContrato = By.xpath("/html/body/div[17]/div/ul");
		listTipoContrato = By.xpath("/html/body/div[17]/div/ul/li");		
		selectDestCredito = By.id("form:destinoCredito_label");
		contDestCredito = By.xpath("//*[@id=\"form:destinoCredito_items\"]"); 
		listDestCredito = By.xpath("/html/body/div[20]/div/ul/li");
		
		checkSexoM = By.id("form:sexo:0");
		checkSexoF = By.id("form:sexo:1");
		selectEstadoCivil = By.id("form:estadoCivil_label");
		contEstadoCivil = By.xpath("//*[@id=\"form:estadoCivil_items\"]");
		//listEstadoCivil = By.xpath("/html/body/div[39]/div/ul/li");
		listEstadoCivil = By.xpath("/html/body/div[41]/div/ul/li");
		inputCelular = By.id("form:telefonoCelular");
		dirResidencia = By.xpath("//*[@id=\"form:direccion\"]");
		selectDptoRe = By.id("form:departamento_label");
		contDptoRe = By.xpath("//*[@id=\"form:departamento_items\"]");
		//listDptoRe = By.xpath("/html/body/div[46]/div/ul/li");
		listDptoRe = By.xpath("/html/body/div[48]/div/ul/li");
		selectCiuRe = By.id("form:ciudad_label");
		contCiuRe = By.xpath("//*[@id=\"form:ciudad_items\"]");
		listCiuRe = By.xpath("/html/body/div[73]/div/ul/li");
		inputTelefono = By.id("form:telefonoFijo");
		checkSiCorrespondencia = By.xpath("//*[@id=\"form:checkResidenciaEsCorrespondencia:0\"]");
		checkNoCorrespondenca = By.xpath("//*[@id=\"form:checkResidenciaEsCorrespondencia:1\"]");
		dirCorrespondencia = By.xpath("//*[@id=\"form:direccionCorrespondencia\"]");
		selectDptoCorres = By.id("form:departamentoCorrespondencia_label");
		contDptoCorres = By.xpath("//*[@id=\"form:departamentoCorrespondencia_items\"]");
		listDptoCorres = By.xpath("/html/body/div[54]/div/ul/li");
		selectCiuCorres = By.id("form:ciudadCorrespondencia_label");
		contCiuCorres = By.xpath("//*[@id=\"form:ciudadCorrespondencia_items\"]");
		listCiudCorres = By.xpath("/html/body/div[73]/div/ul/li");
		selectTipoVi = By.id("form:tipoVivienda_label");
		contTipoVi = By.xpath("//*[@id=\"form:tipoVivienda_items\"]");
		//listTiVi = By.xpath("/html/body/div[58]/div/ul/li");
		listTiVi = By.xpath("/html/body/div[61]/div/ul/li");
		btnGuardarForm = By.id("form:j_idt381");
		
		//Form-Referencia
		selectReferencia = By.xpath("//*[@id=\"form:j_idt93\"]/div[6]/div/ul/li[2]/a");
		addReferencia = By.id("form:j_idt197");
		checkRefFami = By.xpath("//*[@id=\"form:j_idt156:0:tipoReferencia:0\"]");
		checkRefPerso = By.xpath("//*[@id=\"form:j_idt156:0:tipoReferencia:1\"]");
		checkRefCon = By.xpath("//*[@id=\"form:j_idt156:0:tipoReferencia:2\"]");
		primerApellido = By.id("form:j_idt156:0:j_idt163");
		segunApellido = By.id("form:j_idt156:0:j_idt165");
		primerNombre = By.id("form:j_idt156:0:j_idt167");
		segunNombre = By.id("form:j_idt156:0:j_idt169");
		selectDptoRef = By.id("form:j_idt156:0:departamento_label");
		contDptoRef = By.xpath("//*[@id=\"form:j_idt156:0:departamento_items\"]");
		listDptoRef = By.xpath("/html/body/div[8]/div/ul/li[3]");
		//html/body/div[9]/div/ul/li[3]
		selectCiuRef = By.id("form:j_idt156:0:ciudad_label");
		contCiuRef = By.xpath("//*[@id=\"form:j_idt156:0:ciudad_items\"]");
		listCiuRef = By.xpath("/html/body/div[27]/div/ul/li");
		telResiRef = By.id("form:j_idt156:0:j_idt182");
		tldTraRef = By.id("form:j_idt156:0:j_idt184");
		selectRelRef = By.id("form:j_idt156:0:relacion_label");
		contRelRef = By.xpath("//*[@id=\"form:j_idt156:0:relacion_items\"]");
		listRelRef = By.xpath("/html/body/div[15]/div/ul/li");
		btnGuarRef = By.xpath("//*[@id=\"form\"]/div[5]/div/a[2]");
		
		
		//FromReferencia-Verificacion
		cambiPestRef = By.xpath("//*[@id=\"formConsultas:j_idt92\"]/div[7]/div/ul/li[2]/a");
		contRefPrimer = By.xpath("//*[@id=\"formReferenciacion:j_idt91:0:j_idt128:0:j_idt139\"]");
		checkRefPosiPrimer = By.xpath("/html/body/div[2]/div/div[2]/div/form/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div[1]/div[2]/div/table/tbody/tr[2]/td[1]/div/div[1]/div/span/input");
		checkSiPrimer = By.xpath("/html/body/div[2]/div/div[2]/div/form/div[1]/div/div[2]/div/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/div/div[1]/div/span/input");
		contRefSegun = By.xpath("//*[@id=\"formReferenciacion:j_idt91:1:j_idt128:0:j_idt139\"]");
		checkRefPosiSegund = By.xpath("//*[@id=\"formReferenciacion:j_idt91:1:j_idt128:0:j_idt136:1\"]");
		checkSiSegund = By.xpath("/html/body/div[2]/div/div[2]/div/form/div[1]/div/div[3]/div/div[1]/div[2]/div[2]/div[2]/div/div/div[1]/div/div[1]/div/span/input");
		btnGuardarRef = By.xpath("//*[@id=\"formReferenciacion\"]/div[2]/div/a[2]");
		
		//FormReferencia-Pagaduria
		checkSalarioSi = By.xpath("//*[@id=\"formConsultas:salario:0_clone\"]");
		checkFechaSi = By.xpath("//*[@id=\"formConsultas:fechaIngreso:0_clone\"]");
		checkContratoSi = By.xpath("//*[@id=\"formConsultas:tipoContrato:0_clone\"]");
		checkCargoSi = By.xpath("//*[@id=\"formConsultas:cargo:0_clone\"]");
		btnSoliAnalisis = By.id("formConsultas:analisis");

		SegundaPestana = By.xpath("//a[starts-with(@onclick,'mojarra') and @class='link-circle']");
		ListBtnAddReference = By.xpath("//a[starts-with(@class, 'ui-commandlink ui-widget iconoMatematicos3X')]");
		notificacion = By.xpath("//*[@class='ui-growl-title']");
	}
		
}
