package Consultas;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CertificacionSaldoQuery {
	
	private static Logger log = Logger.getLogger(CertificacionSaldoQuery.class);
	ConexionBase dbconector = new ConexionBase();
	// Certificacion
	String idCertificado;
	String nombres;
	String apellidos;
	String identificacion;
	String numRadicacion;
	String idCredito;
	String fechaSolicitud;

	// Recaudo
	String nRadicado;
	String idRecaudo;
	String origen;
	String tipo;
	String valor;
	String fecha;

	// CuentasCXC
	String FechaAprobacion;

	// Intereses de fianza
	String interesFianza;

	WebDriver driver;
	//CertificacionSaldoAccion cert = new CertificacionSaldoAccion(driver);//Se comentario se debe validar
	

	//Se debe validar su uso
	/*public CertificacionSaldoAccion getCert() {
		return cert;
	}

	public void setCert(CertificacionSaldoAccion cert) {
		this.cert = cert;
	}*/

	public void ConsultarRegistroCertificacion(String identificacion, String numRadicado) {
		try {
			ResultSet r = dbconector.conexion("SELECT DISTINCT CE.ID,\r\n"
					+ "UPPER(C.PRIMER_NOMBRE ||' '|| C.SEGUNDO_NOMBRE) NOMBRES,\r\n"
					+ "UPPER( C.PRIMER_APELLIDO ||' '|| C.SEGUNDO_APELLIDO) APELLIDOS,\r\n"
					+ "C.IDENTIFICACION CEDULA,\r\n" + "CR.NUMERO_RADICACION,\r\n" + "CR.ID IDCREDITO,\r\n"
					+ "CE.FECHA_SOLICITUD \r\n" + "FROM CLIENTE C \r\n" + "INNER JOIN CREDITO CR\r\n"
					+ "ON CR.ID_CLIENTE = C.ID \r\n" + "INNER JOIN CERTIFICACION CE\r\n"
					+ "ON CE.ID_CREDITO = CR.ID \r\n" + "WHERE 1=1\r\n" + "AND C.IDENTIFICACION = '" + identificacion
					+ "' AND CR.NUMERO_RADICACION  =" + numRadicado + "\r\n" + "ORDER BY CE.FECHA_SOLICITUD DESC;");
			while (r.next()) {
				this.idCertificado = r.getString(1);
				this.nombres = r.getString(2);
				this.apellidos = r.getString(3);
				this.identificacion = r.getString(4);
				this.numRadicacion = r.getString(5);
				this.idCredito = r.getString(6);
				this.fechaSolicitud = r.getString(7);
			}
			System.out.println("##CONSULTA TABLA CERTIFICACION##");
			System.out.println("idCertificado " + idCertificado + "\n" + "nombres " + nombres + "\n" + "Apellidos "
					+ apellidos + "\n" + "identificacino " + identificacion + "\n" + "Num Radicado " + numRadicacion
					+ "\n" + "IdCredito " + idCredito + "\n" + "FechaSolicitud " + fechaSolicitud);
			//validarRegistroCertificacion(fechaSolicitud, numRadicacion);
		} catch (Exception e) {
			log.error("********ERROR EJECUTANDO LA CONSULTA EL METODO - ConsultarRegistroCertificacion() ********");
			log.error(e.getMessage());			
		}

	}

	public void validarRegistroCertificacion(String fechaSolicitud, String numRadicacion) throws ParseException {
		SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-mm-dd");
		Date date1 = objSDF.parse(fechaSolicitud);
		System.out.println(date1);
	}

	public void consultarRecaudo(String numRadicado) {
		try {
			ResultSet r = dbconector.conexion("SELECT C.NUMERO_RADICACION,R.ID,R.ORIGEN,R.TIPO,R.VALOR, R.FECHA\r\n"
					+ "FROM RECAUDO_CLIENTE RC\r\n" + "INNER JOIN CREDITO C ON RC.ID_CREDITO = C.ID\r\n"
					+ "INNER JOIN RECAUDO R ON RC.ID_RECAUDO = R.ID\r\n" + "WHERE 1=1\r\n" + "AND C.NUMERO_RADICACION ="
					+ numRadicado + "\r\n" + "AND R.TIPO IN ('CERTIFICACION_DE_SALDO');");
			while (r.next()) {
				this.nRadicado = r.getString(1);
				this.idRecaudo = r.getString(2);
				this.origen = r.getString(3);
				this.tipo = r.getString(4);
				this.valor = r.getString(5);
				this.fecha = r.getString(6);
			}
			System.out.println("##CONSULTAR RECAUDO##");
			System.out.println("nRadicado " + nRadicado + "\n" + "idRecaudo " + idRecaudo + "\n" + "Apellidos "
					+ apellidos + "\n" + "origen " + origen + "\n" + "Num Radicado " + numRadicacion + "\n" + "tipo "
					+ tipo + "\n" + "valor " + valor);
		} catch (Exception e) {
			System.out.println("Error consulta Recauido" + e.getMessage());
		}

	}

	public void consultarCXC(String numRadicado) {
		try {
			ResultSet r = dbconector.conexion("SELECT\r\n"
					+ "	UPPER(C.PRIMER_NOMBRE || ' ' || C.SEGUNDO_NOMBRE) NOMBRES,\r\n"
					+ "	UPPER( C.PRIMER_APELLIDO || ' ' || C.SEGUNDO_APELLIDO) APELLIDOS,\r\n"
					+ "	CR.FECHA_APROBACION\r\n" + "FROM\r\n" + "	CREDITO CR\r\n" + "INNER JOIN CLIENTE C ON\r\n"
					+ "	CR.ID_CLIENTE = C.ID\r\n" + "WHERE\r\n" + "	CR.FECHA_APROBACION >= '2019-02-06'\r\n"
					+ "AND	CR.NUMERO_RADICACION =" + numRadicado + ";");
			while (r.next()) {
				this.nombres = r.getString(1);
				this.apellidos = r.getString(2);
				this.FechaAprobacion = r.getString(3);
			}
			System.out.println("###CONSULTA CXC");
			System.out.println("nombres " + nombres + "\n" + "apellidos " + apellidos + "\n" + "FechaAprobacion "
					+ FechaAprobacion);
		} catch (Exception e) {
			System.out.println("Error consulta CXC" + e.getMessage());
			FechaAprobacion = " ";
		}
		if (FechaAprobacion == null ) {
			System.out.println("No tiene intereses cuenta por cobrar");
		} else {
			System.out.println("Tiene intereses cuenta ppor cobrar");
		}
	}

	public void ConsultarInteresesFianza(String numRadicado) {
		try {
			ResultSet r = dbconector
					.conexion("SELECT\r\n" + "	UPPER(C.PRIMER_NOMBRE || ' ' || C.SEGUNDO_NOMBRE) NOMBRES,\r\n"
							+ "	UPPER( C.PRIMER_APELLIDO || ' ' || C.SEGUNDO_APELLIDO) APELLIDOS,\r\n"
							+ "	CR.FECHA_APROBACION,\r\n" + "	D.VALOR_FIANZA\r\n" + "FROM\r\n" + "	CREDITO CR\r\n"
							+ "INNER JOIN CLIENTE C ON\r\n" + "	CR.ID_CLIENTE = C.ID\r\n"
							+ "INNER JOIN DESGLOSE D ON\r\n" + "	CR.ID = D.ID_CREDITO\r\n" + "WHERE\r\n"
							+ "	D.VALOR_FIANZA IS NOT NULL\r\n" + "	AND D.DESGLOSE_SELECCIONADO IS TRUE\r\n"
							+ "	AND CR.NUMERO_RADICACION =" + numRadicado + ";");
			while (r.next()) {
				this.nombres = r.getString(1);
				this.apellidos = r.getString(2);
				this.FechaAprobacion = r.getString(3);
				this.interesFianza = r.getString(4);
			}
			System.out.println("##CONSULTA INT RIANZA##");
			System.out.println("nombres " + nombres + "\n" + "apellidos " + apellidos + "\n" + "FechaAprobacion "
					+ FechaAprobacion + "\n" + "interesFianza " + interesFianza);
		} catch (Exception e) {
			System.out.println("Error consulta intereses" + e.getMessage());
		}
	}
}
