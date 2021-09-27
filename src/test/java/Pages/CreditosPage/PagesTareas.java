package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;


public class PagesTareas {
	WebDriver driver;
	
	public By filtroDescipcion;
	public By EditarVer;
	public By Aprobar;
	public By filtroTarea;
	public By Guardar;
	
	public PagesTareas (WebDriver driver) throws InterruptedException {
		filtroDescipcion = By.id("form:listaTarea:descripcion:filter");
		filtroTarea  =By.id("form:listaTarea:tarea:filter");
		EditarVer = By.id("form:listaTarea:0:accesoRapido");
	    Aprobar = By.xpath("//*[text()='Aprobar']");
	    Guardar = By.xpath("//*[text()='Guardar']");
	}
}

