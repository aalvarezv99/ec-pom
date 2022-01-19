package Pages.SolicitudCreditoPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PestanaReferenciacionPage {
    WebDriver driver;

    public By SalarioCheck;
    public By FechaIngreso;
    public By TipoContrato;
    public By CargoCheck;
    public By RadioSaneamiento;
    public By Competidor1;
    public By FiltroCompetidor1;
    public By Cartera1;
    public By VlrCuota1;
    public By FechaVencimiento1;
    public By NumObligacion1;
    //public By BtnAprobar1;
    public By Competidor2;
    public By FiltroCompetidor2;
    public By Cartera2;
    public By VlrCuota2;
    public By FechaVencimiento2;
    public By NumObligacion2;
    //public By BtnAprobar2;
    public By BtnsAprobar;
    public By Guardar;
    public By ReferenciaPositiva;
    public By CheckSI;
    public By Titulo;
    public By GuardarReferencias;
    public By SolicitarAnalisis;
    public By ListLabelEntidad;
    public By ListFiltroEntidad;
    public By ListMonto;
    public By ListValorCuota;
    public By ListFecha;
    public By ListNumObligacion;
    public By ListBtnAprobar;
    public By ListRadioSaneamiento;
    public By ListTipo;
    public By ListRadioCompra;
    public By Aprobar;
    public By Entidad;
    public By FiltroEntidad;
    public By NumObligacion;
    public By SeleccionarSaneamiento;
    public By listDescEntidad;
    public By labelTelefonos;

    public PestanaReferenciacionPage(WebDriver driver) {

        Titulo = By.xpath("//*[text()='REFERENCIAS']");
        SalarioCheck = By.id("formConsultas:salario:0_clone");
        FechaIngreso = By.id("formConsultas:fechaIngreso:0_clone");
        TipoContrato = By.id("formConsultas:tipoContrato:0_clone");
        CargoCheck = By.id("formConsultas:cargo:0_clone");

        RadioSaneamiento = By.id("formConsultas:j_idt170:2:j_idt198:0");
        ListLabelEntidad = By.xpath("//label[starts-with(@id,'formConsultas:j_idt170:')]");
        ListFiltroEntidad = By.xpath("//input[starts-with(@name,'formConsultas:j_idt170') and @class='ui-selectonemenu-filter ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all']");
        ListMonto = By.xpath("//input[starts-with(@name,'formConsultas:j_idt170:') and contains(@name,'j_idt207')]");
        ListValorCuota = By.xpath("//input[starts-with(@name,'formConsultas:j_idt170:') and contains(@name,'j_idt213')]");
        ListFecha = By.xpath("//input[starts-with(@name,'formConsultas:j_idt170:') and contains(@name,'j_idt210')]");
        ListNumObligacion = By.xpath("//input[starts-with(@name,'formConsultas:j_idt170:') and contains(@name,'j_idt216')]");
        ListBtnAprobar = By.xpath("//a[starts-with(@id,'formConsultas:j_idt170:') and contains(@id,'j_idt218')]");
        ListTipo = By.xpath("//label[contains(text(),'SANEAMIENTO') or contains(text(),'COMPRA')]");
        ListRadioCompra = By.xpath("//input[starts-with(@id,'formConsultas:j_idt170:') and contains(@name,'j_idt198') and contains(@value,'COMPRA') and not (contains(@value,'LIBRANZA'))]");
        ListRadioSaneamiento = By.xpath("//input[starts-with(@id,'formConsultas:j_idt170:') and contains(@name,'j_idt198') and contains(@value,'SANEAMIENTO') and not (contains(@value,'LIBRANZA'))]");
        Competidor1 = By.id("formConsultas:j_idt170:0:j_idt203_label");
        FiltroCompetidor1 = By.id("formConsultas:j_idt170:0:j_idt203_filter");
        Cartera1 = By.name("formConsultas:j_idt170:0:j_idt207");
        VlrCuota1 = By.name("formConsultas:j_idt170:0:j_idt213");
        FechaVencimiento1 = By.id("formConsultas:j_idt170:0:j_idt210_input");
        NumObligacion1 = By.name("formConsultas:j_idt170:0:j_idt216");
        //BtnAprobar1 = By.id("formConsultas:j_idt170:0:j_idt218");
        Competidor2 = By.id("formConsultas:j_idt170:2:j_idt203_label");
        FiltroCompetidor2 = By.id("formConsultas:j_idt170:2:j_idt203_filter");
        Cartera2 = By.name("formConsultas:j_idt170:2:j_idt207");
        VlrCuota2 = By.name("formConsultas:j_idt170:2:j_idt213");
        FechaVencimiento2 = By.id("formConsultas:j_idt170:2:j_idt210_input");
        NumObligacion2 = By.name("formConsultas:j_idt170:2:j_idt216");
        //BtnAprobar2 = By.id("formConsultas:j_idt170:2:j_idt218");
        BtnsAprobar = By.xpath("//a[starts-with(@id,'formConsultas:j_idt170:') and text()='Aprobar']");

        Entidad = By.xpath("//label[starts-with(@id,'formConsultas:j_idt170') and contains (@id,'j_idt170')]");
        FiltroEntidad = By.xpath("//input[starts-with(@id,'formConsultas:j_idt170') and contains (@id,'j_idt203_filter')]");
        Aprobar = By.xpath("//a[starts-with(@id,'formConsultas:j_idt170') and contains (@id,'j_idt218') and contains (text (),'Aprobar')]");
        NumObligacion = By.name("formConsultas:j_idt170:0:j_idt216");

        listDescEntidad = By.xpath("//div[@class='conmasX2 padding-right50']//child::span[@class='form-control']");

        Guardar = By.id("formConsultas:guardar");
        ReferenciaPositiva = By.xpath("//*[@value='REFERENCIA_POSITIVA']");
        CheckSI = By.xpath("//*[@value='true' and @data-itemindex='0']");
        GuardarReferencias = By.xpath("//a[text()='Guardar']");
        SolicitarAnalisis = By.id("formConsultas:analisis");

        //Compra cartera
        Aprobar = By.id("formConsultas:j_idt170:0:j_idt218");
        Entidad = By.id("formConsultas:j_idt170:0:j_idt203_label");
        FiltroEntidad = By.id("formConsultas:j_idt170:0:j_idt203_filter");
        NumObligacion = By.name("formConsultas:j_idt170:0:j_idt216");
        SeleccionarSaneamiento = By.id("formConsultas:j_idt170:0:j_idt198:0");

        labelTelefonos = By.xpath("//label[starts-with(@id, 'formReferenciacion:j_idt91:')]");
    }
}
