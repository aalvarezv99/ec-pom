package Pages;

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
		
		
		//Componentes valores
		public By inputTasa;
		public By inputPlazo;		
		public By inputMonto;	
		public By diasIntInicial;
		public By inputIngresos;
		public By inputDescLey;
		public By inputdDescNomina;
		public By vlrCompra;
		public By btnGuardar;
		public By ResultMontoSoli;
		public By EstudioCreditoIVA;
		
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
			listOcupacion = By.xpath("/html/body/div[11]/div/ul/li");		
			
			
			checkLibre = By.id("formSimuladorCredito:j_idt98:0");
			checkControlado = By.id("formSimuladorCredito:j_idt98:1");
			
			
			//Componentes valores
			inputTasa = By.id("formSimuladorCredito:tasa_input");
			inputPlazo = By.id("formSimuladorCredito:plazo_input");		
			inputMonto = By.id("formSimuladorCredito:monto_input");	
			diasIntInicial = By.id("formSimuladorCredito:iIniciales_input");
			inputIngresos = By.id("formSimuladorCredito:ingresos");
			inputDescLey = By.id("formSimuladorCredito:ley");
			inputdDescNomina = By.id("formSimuladorCredito:nomina");
			vlrCompra = By.id("formSimuladorCredito:comprasSaneamiento");
			btnGuardar = By.id("formSimuladorCredito:j_idt202");
			ResultMontoSoli = By.id("formSimuladorCredito:montoTotal_hinput");
			EstudioCreditoIVA = By.id("formSimuladorCredito:estudio_hinput");
			
			//FormCliente
			btnCrearCliente = By.id("formSimuladorCredito:analisis");
			selectContrato = By.id("formSimuladorCredito:tipoContrato_label");
			contContrato = By.xpath("/html/body/div[12]/div/ul");
			listContrato = By.xpath("/html/body/div[12]/div/ul/li");
			selectFechaIngre = By.id("formSimuladorCredito:fechaIngreso_input");
			inputPrNombre = By.id("formSimuladorCredito:primerNombre");
			inputSeNombre = By.id("formSimuladorCredito:segundoNombre");
			inputPrApellido = By.id("formSimuladorCredito:primerApellido");
			inputSeApellido = By.id("formSimuladorCredito:segundoApellido");
			inputCorreo = By.id("formSimuladorCredito:email");
			inputCelular = By.id("formSimuladorCredito:cel");
			selectDpto = By.id("formSimuladorCredito:departamento_label");
			contDpto = By.xpath("/html/body/div[9]/div/ul");
			listDpto = By.xpath("/html/body/div[9]/div/ul/li");
			selectCiudad = By.id("formSimuladorCredito:ciudad_label");
			contCiudad = By.xpath("/html/body/div[14]/div/ul");
			listCiudad = By.xpath("/html/body/div[14]/div/ul/li");
			btnCrear = By.id("formSimuladorCredito:j_idt326");
			
			//Documentos
			fileAutorizacion = By.id("formSimuladorCredito:j_idt242_input");
			fileCedula = By.id("formSimuladorCredito:j_idt248_input");
			fileNomina = By.id("formSimuladorCredito:j_idt254_input");
			btnSoliConsulta = By.id("formSimuladorCredito:guardar");
		}	
}
