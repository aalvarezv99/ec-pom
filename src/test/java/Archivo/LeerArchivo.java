package Archivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Archivo.ArchivoOriginacion.SimuladorAsesorObjeto;
import CommonFuntions.BaseTest;
import Archivo.ArchivoOriginacion.OriginacionHojaCliente;

public class LeerArchivo {

	private static Properties pro = new Properties();
	private static InputStream in = BaseTest.class.getResourceAsStream("../test.properties");
	private static Logger log = Logger.getLogger(LeerArchivo.class);

	ArrayList<SimuladorAsesorObjeto> datosSimuladorAsesor = new ArrayList<SimuladorAsesorObjeto>();
	ArrayList<OriginacionHojaCliente> datosCliente = new ArrayList<OriginacionHojaCliente>();

	String ruta = leerPropiedades("RutaArchivoDatos");

	public ArrayList<SimuladorAsesorObjeto> getDatosSimuladorAsesor() {
		return datosSimuladorAsesor;
	}

	public void setDatosSimuladorAsesor(ArrayList<SimuladorAsesorObjeto> datosSimuladorAsesor) {
		this.datosSimuladorAsesor = datosSimuladorAsesor;
	}

	public ArrayList<OriginacionHojaCliente> getDatosCliente() {
		return datosCliente;
	}

	public void setDatosCliente(ArrayList<OriginacionHojaCliente> datosCliente) {
		this.datosCliente = datosCliente;
	}

	/* Se lee las hojas para los archivos del simulador Asesor */
	public void leerArchivoSimuladorAsesor(String formulario) {
		String hojaUno = leerPropiedades("HojaSimuladorAsesor");
		try {
			if (formulario.replace(" ","").equals(hojaUno)) {
				String nombreArchivo = leerPropiedades("ArchivoOriginacion");
				String rutaArchivo = ruta + nombreArchivo;
				log.info("**********INICIA LECTURA**********" + rutaArchivo);
				try (FileInputStream file = new FileInputStream(new File(rutaArchivo))) {
					// leer archivo excel
					XSSFWorkbook worbook = new XSSFWorkbook(file);
					// obtener la hoja que se va leer
					XSSFSheet sheet = worbook.getSheet(hojaUno);
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
							// Creamos la instancia del la clase
							SimuladorAsesorObjeto data = new SimuladorAsesorObjeto();
							// Se asigna la linea a un arreglo de String
							String[] cortarString = cell.getStringCellValue().split(";");
							// Se setean los valores a la clase por medio de cada posicion
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
							datosSimuladorAsesor.add(data);
						}
					}
				} catch (Exception e) {
					log.error("######### -ERROR LEYENDO EL ARCHIVO- #######" + nombreArchivo);
					log.error(e);
				}
			} else if (formulario == "FormularioCliente") {
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
							// Creamos la instancia del la clase
							OriginacionHojaCliente data = new OriginacionHojaCliente();
							// Se asigna la linea a un arreglo de String
							String[] cortarString = cell.getStringCellValue().split(";");
							// Se setean los valores a la clase por medio de cada posicion
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
		} catch (Exception e) {
			log.fatal("*****TERMINA EJECUCION******");
		}

	}

	public String leerPropiedades(String valor) {
		try {
			pro.load(in);
		} catch (Exception e) {
			log.error("====== ERROR LEYENDO EL ARCHIVO DE PROPIEDADES========= " + e);
		}
		return pro.getProperty(valor);
	}

}
