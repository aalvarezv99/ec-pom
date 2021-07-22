package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import CommonFuntions.BaseTest;


public class PagesTareas {
	WebDriver driver;
	
	public By filtroDescipcion;
	public By EditarVer;
	public By Aprobar;
	
	public PagesTareas (WebDriver driver) throws InterruptedException {
		filtroDescipcion = By.id("form:listaTarea:descripcion:filter");
		EditarVer = By.id("form:listaTarea:0:accesoRapido");
		Thread.sleep(1000);
	    Aprobar = By.xpath("//*[text()='Aprobar']");
	    
	}
}

