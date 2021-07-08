package Acciones;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import Archivo.LeerArchivo;
import CommonFuntions.BaseTest;
import Pages.SimuladorAsesorPages;

public class OriginacionCreditosAccion extends BaseTest {
	
	WebDriver driver;
	SimuladorAsesorPages simuladorasesorpage;
	PanelPrincipalAccion panelnavegacionaccion;
	LoginAccion loginaccion;
	LeerArchivo archivo;
	//BaseTest baseTest;
	private static Logger log = Logger.getLogger(OriginacionCreditosAccion.class);

	public OriginacionCreditosAccion(WebDriver driver) {
		//this.driver = driver;
		super(driver);
		//baseTest = new BaseTest(driver);
		simuladorasesorpage = new SimuladorAsesorPages(driver); 
		panelnavegacionaccion = new PanelPrincipalAccion(driver);
		loginaccion = new LoginAccion(driver);
		archivo = new LeerArchivo();
	}
	
	/************INICIO ACCIONES PARA SIMULADOR ASESOR***************/
	
	public void ingresarSimuladorAsesor() {
		panelnavegacionaccion.navegarSimulador();
	}
	
	/* Metodo que se utiliza para llenar el formulario asesor del 
	  la opcion somulador ir*/
	public void llenarFormularioAsesor(String Pagaduria, String Cedula, String Fecha,String Oficina,String Actividad,String Tasa,String Plazo,String Monto,String DiasHabilesIntereses,String Ingresos,String descLey,String descNomina,String vlrCompasSaneamientos) throws InterruptedException {
		
		//archivo.leerArchivoSimuladorAsesor(hoja);
		
		// Desplegable pagaduria
		
			try {
				assertEstaPresenteElemento(simuladorasesorpage.desPagaduria);		
				hacerClick(simuladorasesorpage.desPagaduria);
				//driver.findElement(simuladorasesorpage.desPagaduria).click();
				selectValorLista(simuladorasesorpage.contPagaduria, simuladorasesorpage.listPagaduria, Pagaduria);
			} catch (Exception e) {
				log.error("##### -ERROR- #####" + e);
			}
			    ElementVisible();
			    assertTextoelemento(simuladorasesorpage.desPagaduria,Pagaduria);
			    hacerClick(simuladorasesorpage.inputIdentificacion);
			    assertEstaPresenteElemento(simuladorasesorpage.inputIdentificacion);
			    EscribirElemento(simuladorasesorpage.inputIdentificacion,Cedula);			    
			    ElementVisible();
			    MetodoFecha(Fecha,simuladorasesorpage.selectFecha,simuladorasesorpage.contAno,simuladorasesorpage.contMes,simuladorasesorpage.contDia,simuladorasesorpage.listDia);
			    ElementVisible();

			 // Componente Oficina seleccionando la oficina del documento
			    
			    assertEstaPresenteElemento(simuladorasesorpage.desOficina);
			    hacerClick(simuladorasesorpage.desOficina); 
			    selectValorLista(simuladorasesorpage.contOficina,simuladorasesorpage.listOficina,Oficina);
			    ElementVisible();
			    assertTextoelemento(simuladorasesorpage.desOficina,Oficina);
			   
			    
			 // Componente Actividad selecciona la actividad del cliente
			    assertEstaPresenteElemento(simuladorasesorpage.desOcupacion);
			    hacerClick(simuladorasesorpage.desOcupacion); 
			    selectValorLista(simuladorasesorpage.contOcupacion,simuladorasesorpage.listOcupacion,Actividad);
			    ElementVisible();
			    assertTextoelemento(simuladorasesorpage.desOcupacion,Actividad);
			 
			 // Llenar formulario campos del credito
			    LimpiarConTeclado(simuladorasesorpage.inputTasa);
			    EscribirElemento(simuladorasesorpage.inputTasa,Tasa);
			    ElementVisible();
			    hacerClick(simuladorasesorpage.inputPlazo);
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.inputPlazo);
			    EscribirElemento(simuladorasesorpage.inputPlazo,Plazo);
			    ElementVisible();
			    hacerClick(simuladorasesorpage.inputMonto);
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.inputMonto);
			    EscribirElemento(simuladorasesorpage.inputMonto,Monto);
			    ElementVisible();
			    hacerClick(simuladorasesorpage.diasIntInicial);	
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.diasIntInicial);
			    EscribirElemento(simuladorasesorpage.diasIntInicial,DiasHabilesIntereses);
			    ElementVisible();
			    hacerClick(simuladorasesorpage.inputIngresos);	
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.inputIngresos);
			    EscribirElemento(simuladorasesorpage.inputIngresos,Ingresos);
			    ElementVisible();	
			    hacerClick(simuladorasesorpage.inputDescLey);	
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.inputDescLey);
			    EscribirElemento(simuladorasesorpage.inputDescLey,descLey);
			    ElementVisible();
			    hacerClick(simuladorasesorpage.inputdDescNomina);
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.inputdDescNomina);
			    EscribirElemento(simuladorasesorpage.inputdDescNomina,descNomina);
			    ElementVisible();
			    hacerClick(simuladorasesorpage.vlrCompra);
			    ElementVisible();
			    LimpiarConTeclado(simuladorasesorpage.vlrCompra);
			    EscribirElemento(simuladorasesorpage.vlrCompra,vlrCompasSaneamientos);
			    ElementVisible();
			    //Validar resultados de simulacion
			    int calculoMontoSoli =(int)MontoaSolicitar(Integer.parseInt(Monto),24,4625);
			    assertvalidarEquals(TextoElemento(simuladorasesorpage.ResultMontoSoli),String.valueOf(calculoMontoSoli));
		        
			    int EstudioCreditoIva = (int)EstudioCreditoIva(calculoMontoSoli,5.95);
			    assertvalidarEquals(TextoElemento(simuladorasesorpage.EstudioCreditoIVA),String.valueOf(EstudioCreditoIva));
			     
			    
		
		
		
//		WebElement contEstado = driver.findElement(simuladorasesorpage.contPagaduria);
//		List<WebElement> listaEstado =  contEstado.findElements(simuladorasesorpage.listPagaduria);
//		for (WebElement contenido : listaEstado) {
//			if (contenido.getText().contains(data.getPagaduria().toUpperCase())) {				
//				contenido.click();
//				break;
//			}
//		}
//		//espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputIdentificacion).click();
//		driver.findElement(simuladorasesorpage.inputIdentificacion).sendKeys(data.getCedula());
//		//driver.findElement(simuladorasesorpage.inputIdentificacion).sendKeys(data.getCedula();
			    
			    
//		int dia = Integer.parseInt(separarFecha(data.getFecha() + "/", "dia"));
//		String mes = separarFecha(data.getFecha() + "/", "mes");
//		String ano = separarFecha(data.getFecha() + "/", "ano");
//			    
//			    public String separarFecha(String fecha, String tipo) {
//					String result = "";
//					String[] cortarString = fecha.split("/");
//					switch (tipo) {
//					case "dia":
//						result = cortarString[0];
//						break;
//					case "mes":
//						result = cortarString[1];
//						break;
//					case "ano":
//						result = cortarString[2];
//						break;
//					}
//					return result;
//				}
//		// Seleccinar Fecha
//		driver.findElement(simuladorasesorpage.selectFecha).click();
////		espera();
////		espera();
//		ElementVisible();
////		Select se = new Select(driver.findElement(simuladorasesorpage.contAno));
////		se.selectByVisibleText(ano);
//		select = new Select(driver.findElement(simuladorasesorpage.contAno));
//		select.selectByVisibleText(ano);
//		
////		Select select = new Select(driver.findElement(simuladorasesorpage.contMes));
////		select.selectByVisibleText(mes);
//		select = new Select(driver.findElement(simuladorasesorpage.contMes));
//		select.selectByVisibleText(mes);
//
//		WebElement contDia = driver.findElement(simuladorasesorpage.contDia);
//		List<WebElement> listDia = contDia.findElements(simuladorasesorpage.listDia);
//		for (WebElement contenido : listDia) {
//			if (contenido.getText().contains(Integer.toString(dia))) {
//				contenido.click();
//				espera();
//				break;
//			}
//		}
////		espera();
//		ElementVisible();
			    
			    
			    
			    
//		// Componente Oficina seleccionando la oficina del documento		
//		driver.findElement(simuladorasesorpage.desOficina).click();
//		WebElement contOficina = driver.findElement(simuladorasesorpage.contOficina);
//		List<WebElement> listOfi = contOficina.findElements(simuladorasesorpage.listOficina);
//		for (WebElement contenido : listOfi) {
//			if (contenido.getText().toUpperCase().contains(data.getOficina().toUpperCase())) {				
//				contenido.click();
//				break;
//			}
//		}
////		espera();
//		ElementVisible();
			    
//		// Componente Actividad selecciona la actividad del document
//		if (data.getTipo().contains("ClienteNuevo")) {
//			driver.findElement(simuladorasesorpage.desOcupacion).click();
//			WebElement contOcu = driver.findElement(simuladorasesorpage.contOcupacion);
//			List<WebElement> listOcu = contOcu.findElements(simuladorasesorpage.listOcupacion);
//			for (WebElement contenido : listOcu) {
//				if (contenido.getText().toUpperCase().contains(data.getActividad().toUpperCase())) {
//					contenido.click();					
//					break;
//				}
//			}
//		} else {
//			//Se utiliza este xpath siempre que el cliente no sea nuevo
//			simuladorasesorpage.listOcupacion = By.xpath("/html/body/div[14]/div/ul/li");
//			driver.findElement(simuladorasesorpage.desOcupacion).click();
//			WebElement contOcu = driver.findElement(simuladorasesorpage.contOcupacion);
//			List<WebElement> listOcu = contOcu.findElements(simuladorasesorpage.listOcupacion);
//			for (WebElement contenido : listOcu) {
//				System.out.println(contenido.getText());
//				if (contenido.getText().toUpperCase().contains(data.getActividad().toUpperCase())) {
//					contenido.click();
//				//espera();
//					break;
//				}
//			}
//		}		
//		
////		esperaWait();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputTasa).click();
//		System.out.println(data.getTasa());
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		// Javascript Para la tasa, Componentes valores tasa
//		String Script = "document.getElementById('formSimuladorCredito:tasa_input').value = '" + data.getTasa() + "';";
//		js.executeScript(Script);
//		js.executeScript("$( document.getElementById('formSimuladorCredito:tasa_input') ).keydown();");
////		espera();
////		espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputPlazo).click();
////		espera();
////		espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputPlazo).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),
//				data.getPlazo());
//		//espera();
//		
//		driver.findElement(simuladorasesorpage.inputMonto).click();
//		//espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputMonto).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),
//				data.getMonto()); 
////		driver.findElement(simuladorasesorpage.diasIntInicial).click();
////		driver.findElement(simuladorasesorpage.diasIntInicial).sendKeys(data.getDiasHabilesInt());
//		driver.findElement(simuladorasesorpage.inputIngresos).click();
//		//espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputIngresos).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),
//				data.getIngresos());
//		driver.findElement(simuladorasesorpage.inputDescLey).click();
//		//espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputDescLey).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),
//				data.getDescLey());
//		driver.findElement(simuladorasesorpage.inputdDescNomina).click();
//		//espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.inputdDescNomina).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),
//				data.getDescNomina());
//		driver.findElement(simuladorasesorpage.vlrCompra).click();
//		//espera();
//		ElementVisible();
//		driver.findElement(simuladorasesorpage.vlrCompra).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END),
//				data.getVlrCompasSaneamientos());
//		driver.findElement(simuladorasesorpage.btnGuardar).click();
//		//espera();
//		//ElementVisible();

	}
	
	
	/************FIN ACCIONES PARA SIMULADOR ASESOR***************/
	
	
	//==============================================================
	/************INICIA ACCIONES SOLICITUD CREDITO***************/
	
	public void ingresarSolicitudCredito() {
		panelnavegacionaccion.navegarCreditoSolicitud();
	}
	/************FIN ACCIONES SOLICITUD CREDITO***************/
	
	/************INICIA ACCIONES ANALISTA***************/
	/************INICIA ACCIONES ANALISTA***************/
}
