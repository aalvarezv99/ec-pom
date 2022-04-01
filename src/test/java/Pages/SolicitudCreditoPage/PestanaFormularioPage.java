package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PestanaFormularioPage {
	WebDriver driver;
	
	public By DestinoCredito;
	public By ListaDestinoCredito;
	public By SexoM;
	public By SexoF;
    public By AgregarCuenta;
    public By EstadoCivil;
    public By EstadoCivillist;
    public By Correo;
    public By Celular;
    public By Direccion;
    public By Departamento;
    public By Departamentolist;
    public By Ciudad;
    public By Ciudadlist;
    public By Correspondencia;
    public By Tipovivienda;
    public By Tipoviviendalist;
    public By Guardar;
    public By PestanaFormulario;
    public By TituloReferencias;
    public By MasReferencia;
    public By CheckFamiliar;    
    public By CheckPersonal;
    public By PapellidoReferencia;
    public By PnombreReferencia;
    public By DireccionReferencia;
    public By Departamentolabel;
    public By DepartamentoList;
    public By CiudadLabel;
    public By CiudadList;
    public By TelefonoResidencia;
    public By TelefonoTrabajo;
    public By TotalActivos;
    public By IngresosMes;
    public By Fecha;
    public By GuardarRefer;
    public By tipoContrato;
    public By listTipoContrato;
    public By listMenosReferencia;
    public By telefonoResidencia;
    public By telefonoTrabajo;
    
	
	public PestanaFormularioPage (WebDriver driver) {
		
		DestinoCredito = By.id("form:destinoCredito_label");
		ListaDestinoCredito = By.xpath("//li[contains(@id,'form:destinoCredito')]");
		SexoM =By.id("form:sexo:1");
		SexoF =By.id("form:sexo:0");
		AgregarCuenta =By.id("form:btnAdd");
		EstadoCivil = By.id("form:estadoCivil_label");
		EstadoCivillist = By.xpath("//li[contains(@id,'form:estadoCivil')]");
		Correo = By.id("form:email");
		Celular = By.id("form:telefonoCelular");		
		Direccion = By.id("form:direccion");
		Departamento = By.id("form:departamento_label");
		Departamentolist = By.xpath("//li[contains(@id,'form:departamento')]");
		Ciudad = By.id("form:ciudad_label");
		Ciudadlist = By.xpath("//li[contains(@id,'form:ciudad_')]");
		Correspondencia = By.id("form:checkResidenciaEsCorrespondencia:0");
		Tipovivienda = By.id("form:tipoVivienda_label");
		Tipoviviendalist = By.xpath("//li[contains(@id,'form:tipoVivienda_')]");
		Guardar = By.xpath("//span[text()='Guardar']");
		PestanaFormulario=By.xpath("//a[@class='link-circle']");
		TituloReferencias=By.xpath("//div[text()='REFERENCIAS']");
		//MasReferencia = By.id("form:j_idt197");
		MasReferencia = By.xpath("//*[starts-with(@id,'form:j_idt') and contains(@class,'iconoSuma2X')]");
		CheckFamiliar = By.id("form:j_idt156:0:tipoReferencia:0");
		CheckPersonal = By.id("form:j_idt156:1:tipoReferencia:1");
		PapellidoReferencia = By.xpath("//input[starts-with(@id,'form:j_idt156:') and contains(@id,'j_idt163')]");
		PnombreReferencia = By.xpath("//input[starts-with(@id,'form:j_idt156:') and contains(@id,'j_idt167')]");
		DireccionReferencia = By.xpath("//input[starts-with(@id,'form:j_idt156:') and contains(@id,'j_idt171')]");
		Departamentolabel = By.xpath("//label[starts-with(@id,'form:j_idt156:') and contains(@id,'departamento_label')]");
		DepartamentoList =By.xpath("//li[starts-with(@id,'form:j_idt156:') and contains(@id,'departamento')]");
		CiudadLabel =By.xpath("//select[starts-with(@id,'form:j_idt156:') and contains(@id,'departamento_input')]");
		CiudadList =By.xpath("//li[starts-with(@id,'form:j_idt156:') and contains(@id,'ciudad')]");
		TelefonoResidencia=By.xpath("//input[starts-with(@id,'form:j_idt156:') and contains(@id,'j_idt182')]");
		TelefonoTrabajo=By.xpath("//input[starts-with(@id,'form:j_idt156:') and contains(@id,'j_idt184')]");
		TotalActivos = By.id("form:totalActivos_input");
		IngresosMes = By.id("form:ingresosMes_input");
		Fecha = By.id("form:fechaEntrevista_input");
		GuardarRefer = By.xpath("//a[text()='Guardar']");
		tipoContrato = By.id("form:tipoContrato_label");
		listTipoContrato = By.xpath("//li[contains(@id,'form:tipoContrato')]");
		listMenosReferencia = By.xpath("//*[starts-with(@id,'form:j_idt') and contains(@class,'iconoResta2X')]");
		telefonoResidencia = By.xpath("//*[text()='Teléfono residencia*:']/following-sibling::input");
		telefonoTrabajo = By.xpath("//*[text()='Teléfono trabajo*:']/following-sibling::input");
		
	}
	
}
