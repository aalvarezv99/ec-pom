package Pages.CreditosPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PagesClienteParaBienvenida {
    WebDriver driver;

    public By filtrocedula;
    public By Continuar;
    public By Check;
    public By Guardar;
    public By Correcta;
    public By CheckCondicionesCredito;
    public By label_Nombres_Completos;
    public By Correo;
    public By CedulaGrilla;
    public By Desembolso;
    public By ListDesembolso;
    public By detalledelascarteras;
    public By AceptaCartera;
    public By CalificacionProceso;
    public By CalificacionCobro;
    public By Acepta;
    public By Contactado;
    public By Direccion_Residencia_Si;
    public By CxCSeguroInicial;
    public By AceptarCartera;
    public By AceptarSaneamiento;
    public By ValoresCondicionesCredito;
    public By SaldoAlDia;
    public By ValorSaldoAlDia;
    public By CheckCarterasSaneamientos;
    public By ListaCreditosRecoger;
    public By saldoAlDiaRetanqueo;
    public By ImputOTP;

    public PagesClienteParaBienvenida(WebDriver driver) {
        filtrocedula = By.id("form:listaClientesBienvenida:j_idt77:filter");
        detalledelascarteras = By.xpath("//div[text()='DETALLE DE LAS CARTERAS']");
        AceptaCartera = By.id("formCondicionCredito:j_idt255:0:aceptaRadio:0_clone");
        Continuar = By.xpath("//a[@title='Continuar proceso']");
        Check = By.xpath(
                "//*[(starts-with(@id,'form:estado')  or   starts-with(@id,'form:respuesta') ) and @value='true' and @class='ui-radio-clone']");
        CedulaGrilla = By.xpath("//td[text()='52912399']");
        Guardar = By.xpath("//a[text()='Guardar']");
        Correcta = By.xpath("//*[text()='Correcta']");
        CheckCondicionesCredito = By.xpath(
                "//*[@type='radio' and @value='true' and @class='ui-radio-clone' and @id!='formCondicionCredito:cobroAsesor:0_clone' and @id!='formCondicionCredito:informacionSuministrada:0_clone' and not (contains(@id,'aceptaRadio'))]");
        CheckCarterasSaneamientos = By.xpath(
                "//*[@type='radio' and @value='true' and @class='ui-radio-clone' and @id!='formCondicionCredito:cobroAsesor:0_clone' and @id!='formCondicionCredito:informacionSuministrada:0_clone'and contains(@id,'aceptaRadio')]");
        // CheckCondicionesCredito = By.xpath("//*[@type='radio' and @value='true' and
        // @class='ui-radio-clone' and @id!='formCondicionCredito:cobroAsesor:0_clone'
        // and @id!='formCondicionCredito:informacionSuministrada:0_clone' and
        // @id!='formCondicionCredito:j_idt255:0:aceptaRadio:0_clone']");
        label_Nombres_Completos = By.xpath("//label[text()='Nombres completos: ']");
        Correo = By.name("form:j_idt127");
        Desembolso = By.xpath("//label[text() = 'Seleccione un tipo desembolso: ']//following::label[1]");
        ListDesembolso = By.xpath(
                "//li[starts-with(@id,'formCondicionCredito:j_idt') and @role='option' and contains(text(),'Tr') or contains(text(),'Ef') ]");
        CalificacionProceso = By.id("formCondicionCredito:informacionSuministrada:0_clone");
        CalificacionCobro = By.id("formCondicionCredito:cobroAsesor:1_clone");
        Acepta = By.xpath("//span[text()='Acepta']");
        Contactado = By.xpath("//*[@id=\"form:contCel\"]/div[2]/span"); // se debe cambiar cuando se actualicen los ID
        Direccion_Residencia_Si = By.id("form:direccionCorrespondencia:0");
        CxCSeguroInicial = By.id("formCondicionCredito:seguroInicial:0_clone");
        AceptarCartera = By.xpath(
                "//input[starts-with(@id,'formCondicionCredito:j_i') and contains (@id,'aceptaRadio:0_clone') and contains (@value,'true')]");
        AceptarSaneamiento = By.xpath(
                "//input[starts-with(@id,'formCondicionCredito:j_i') and contains (@id,'aceptaRadio:2_clone') and contains (@value,'true')]");

        // valores validados condiciones del credito
        ValoresCondicionesCredito = By.xpath("//span[@class='form-control fecha-inline']");
        SaldoAlDia = By.xpath("/html/body/div[2]/div/div[2]/div/form/div[1]/div[2]/div[3]/div[2]/label");
        ValorSaldoAlDia = By.xpath("//label[@class='form-control fecha-inline']");
        ListaCreditosRecoger = By.xpath("//div[@id='formCondicionCredito:j_idt317']/child::div//child::span");
        saldoAlDiaRetanqueo = By.xpath("//div[@class='col-xs-8 control-label' and text() = 'Saldo al día']//following-sibling::div//child::label");
        ImputOTP = By.xpath("//input[starts-with(@id,'formCondicionCredito:j_idt') and contains(@onkeyup,'mensajeOtpTemp') ]");
        
    }
}