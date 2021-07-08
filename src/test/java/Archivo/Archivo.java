package Archivo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Archivo.ArchivoOriginacion.OriginacionArchivo;
import Archivo.ArchivoOriginacion.OriginacionHojaCliente;

public class Archivo {
	
	public String ruta = "D:\\Thainer Perez\\Repos\\testautomatizacionabacus\\Archivo\\";
	
	ArrayList<OriginacionArchivo> datosFormInicial = new ArrayList<OriginacionArchivo>();
	ArrayList<OriginacionHojaCliente> datosCliente = new ArrayList<OriginacionHojaCliente>();

	
	public ArrayList<OriginacionArchivo> getDatosFormInicial() {
		return datosFormInicial;
	}


	public void setDatosFormInicial(ArrayList<OriginacionArchivo> datosFormInicial) {
		this.datosFormInicial = datosFormInicial;
	}


	public ArrayList<OriginacionHojaCliente> getDatosCliente() {
		return datosCliente;
	}


	public void setDatosCliente(ArrayList<OriginacionHojaCliente> datosCliente) {
		this.datosCliente = datosCliente;
	}

	/*Se lee las hojas para los archivos del simulador Asesor*/
	public void leerArchivoSimuladorAsesor(String formulario) {
		if(formulario == "FormularioInicial") {
			String hoja = "formInicial";
			String nombreArchivo = "OriginacionCreditos.xlsx";
			String rutaArchivo = ruta + nombreArchivo;		

			try (FileInputStream file = new FileInputStream(new File(rutaArchivo))) {
				// leer archivo excel
				XSSFWorkbook worbook = new XSSFWorkbook(file);
				// obtener la hoja que se va leer
				XSSFSheet sheet = worbook.getSheet(hoja);
				// obtener todas las filas de la hoja excel
				Iterator<Row> rowIterator = sheet.iterator();
				Row row;
				// se recorre cada fila hasta el final
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					// se obtiene las celdas por fila
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell cell;
					// se recorre cada celda
					while (cellIterator.hasNext()) {
						// se obtiene la celda en específico 
						cell = cellIterator.next();					
						//Creamos la instancia del la clase
						OriginacionArchivo data = new OriginacionArchivo(); 
						//Se asigna la linea a un arreglo de String
						String[] cortarString = cell.getStringCellValue().split(";");						
						//Se setean los valores a la clase por medio de cada posicion
						data.setPagaduria(cortarString[1]);
						data.setCedula(cortarString[3]);
						data.setFecha(cortarString[5]);	
						data.setOficina(cortarString[7]);
						data.setActividad(cortarString[9]);						
						data.setTasa(cortarString[11]);
						data.setPlazo(cortarString[13]);
						data.setMonto(cortarString[15]);						
						data.setDiasHabilesInt(cortarString[17]);
						data.setIngresos(cortarString[19]);
						data.setDescLey(cortarString[21]);
						data.setDescNomina(cortarString[23]);
						data.setVlrCompasSaneamientos(cortarString[25]);
						data.setTipo(cortarString[27]);
						datosFormInicial.add(data);
					}		
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else if(formulario == "FormularioCliente") {
			String hoja = "formCliente";
			String nombreArchivo = "OriginacionCreditos.xlsx";
			String rutaArchivo = ruta + nombreArchivo;		

			try (FileInputStream file = new FileInputStream(new File(rutaArchivo))) {
				// leer archivo excel
				XSSFWorkbook worbook = new XSSFWorkbook(file);
				// obtener la hoja que se va leer
				XSSFSheet sheet = worbook.getSheet(hoja);
				// obtener todas las filas de la hoja excel
				Iterator<Row> rowIterator = sheet.iterator();
				Row row;
				// se recorre cada fila hasta el final
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					// se obtiene las celdas por fila
					Iterator<Cell> cellIterator = row.cellIterator();
					Cell cell;
					// se recorre cada celda
					while (cellIterator.hasNext()) {
						// se obtiene la celda en específico 
						cell = cellIterator.next();					
						//Creamos la instancia del la clase
						OriginacionHojaCliente data = new OriginacionHojaCliente(); 
						//Se asigna la linea a un arreglo de String
						String[] cortarString = cell.getStringCellValue().split(";");						
						//Se setean los valores a la clase por medio de cada posicion
						data.setTipoContato(cortarString[1]);
						data.setFechaIngreso(cortarString[3]);
						data.setNombre(cortarString[5]);	
						data.setsNombre(cortarString[7]);
						data.setApellido(cortarString[9]);						
						data.setsApellido(cortarString[11]);
						data.setCorreo(cortarString[13]);
						data.setCelular(cortarString[15]);						
						data.setDepartamento(cortarString[17]);
						data.setCiudad(cortarString[19]);
						datosCliente.add(data);
					}	
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	}
