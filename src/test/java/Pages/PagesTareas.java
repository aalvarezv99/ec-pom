package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagesTareas {
	WebDriver driver;
	
	public By filtroDescipcion;
	public By EditarVer;
	public By Aprobar;
	
	public PagesTareas (WebDriver driver) {
		filtroDescipcion = By.id("form:listaTarea:descripcion:filter");
		EditarVer = By.id("form:listaTarea:0:accesoRapido");		
	    Aprobar = By.xpath("//*[text()='Aprobar']");
	}
}
