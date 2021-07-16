package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PestanaReferenciacionPage {
	WebDriver driver;
	
	public By SalarioCheck;
	public By FechaIngreso;
	public By TipoContrato;
	public By CargoCheck;
	public By Entidad;
	public By FiltroEntidad;
	public By NumObligacion;
	public By Aprobar;
	public By Guardar;
	public By ReferenciaPositiva;
	public By CheckSI;
	public By Titulo;
	public By GuardarReferencias;
	public By SolicitarAnalisis;
	
	
	
	public  PestanaReferenciacionPage  (WebDriver driver) {
		
		Titulo = By.xpath("//*[text()='REFERENCIAS']");
		SalarioCheck = By.id("formConsultas:salario:0_clone");
		FechaIngreso  = By.id("formConsultas:fechaIngreso:0_clone");
		TipoContrato = By.id("formConsultas:tipoContrato:0_clone");
		CargoCheck = By.id("formConsultas:cargo:0_clone");
		Entidad = By.id("formConsultas:j_idt170:0:j_idt203_label"); 
		FiltroEntidad =  By.id("formConsultas:j_idt170:0:j_idt203_filter");
		Aprobar = By.id("formConsultas:j_idt170:0:j_idt218");
		NumObligacion = By.name("formConsultas:j_idt170:0:j_idt216");
		Guardar = By.id("formConsultas:guardar");
		ReferenciaPositiva = By.xpath("//*[@value='REFERENCIA_POSITIVA']");
		CheckSI = By.xpath("//*[@value='true' and @data-itemindex='0']");
		GuardarReferencias = By.xpath("//a[text()='Guardar']");
		SolicitarAnalisis = By.id("formConsultas:analisis");
	}
}
