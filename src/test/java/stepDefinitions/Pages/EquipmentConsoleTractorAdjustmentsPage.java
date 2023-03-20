package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EquipmentConsoleTractorAdjustmentsPage extends BasePage {

    public final By alert = By.xpath("/html/body/div[9]/div[3]/div/button[2]");
    public final By alertNo = By.xpath("/html/body/div[9]/div[3]/div/button[2]/span");
    public final By invoice = By.xpath("//*[@id=\"txtInvoiceFilter\"]");
    public final By proNumber = By.xpath("//*[@id=\"txtProNumberFilter\"]");
    public final By refresh = By.xpath("//*[@id=\"SearchBar\"]/table/tbody/tr[1]/td[3]/a[1]/img");  //*[@id="SearchBar"]/table/tbody/tr[1]/td[3]/a/img
    public final By clearFilters = By.xpath("//*[@id=\"SearchBar\"]/table/tbody/tr[1]/td[4]/a/img");
    public final By agentStatus = By.xpath("//*[@id=\"griddata_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th[1]");
    public final By agentReview = By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a");
    public final By agent = By.xpath("//*[@id=\"griddata_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th[9]");
    public final By proNo = By.xpath("//*[@id=\"griddata_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th[11]");
    public final By statusUpdateDate = By.xpath("//*[@id=\"griddata_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th[43]");
    public final By corpStatus = By.xpath("//*[@id=\"griddata_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th[2]");
    public final By corpReview = By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[2]/a");
    public final By dataTableBody = By.xpath("//*[@id=\"griddata\"]/tbody");
    public final By dataTable = By.xpath("//*[@id=\"griddata_wrapper\"]");



    //DriverReimbursement
    public final By tractorDR = By.id("Text44");
    public final By amountDR = By.id("Text45");
    public final By effectivedateDR = By.id("DriverReimbursementEffectiveDate");
    public final By noOfDaysDR = By.id("Text46");
    public final By customerNotesDR = By.id("txtCustomerNo_DR");
    public final By agentNotesDR = By.id("txtAgentCode_DR");
    public final By notesDR = By.id("Textarea2");
    public final By alertNotesDR = By.xpath("/html/body/div[7]");
    public final By okDR = By.xpath("//button[@type='button' and span='Ok']");
    public final By goDR = By.id("DriverReimbursementbutton");
    public final By driverReimbursementsAlert = By.xpath("/html/body/div[14]");
    public final By noDR = By.xpath("/html/body/div[14]/div[3]/div/button[2]/span");
    public final By yesDR = By.xpath("/html/body/div[14]/div[3]/div/button[1]/span");
    public final By crossCloseDR = By.xpath("/html/body/div[11]/div[1]/div/button/span");


    //DriverDeduct
    public final By tractorDD = By.id("DriverDeducttractor");
    public final By amountDD = By.id("Text5");
    public final By effectivedateDD = By.id("DriverDeductEffectiveDate");
    public final By noOfDaysDD = By.id("Text6");
    public final By noOfSplitsDD = By.id("DriverDeductNoOfSplits");
    public final By customerNotesDD = By.id("txtCustomerNo");
    public final By agentNotesDD = By.id("txtAgentCode");
    public final By notesDD = By.id("driverNotes");
    public final By alertNotesDD = By.xpath("/html/body/div[7]");
    public final By okDD = By.xpath("//button[@type='button' and span='Ok']");
    public final By goDD = By.id("DriverDeductbutton");
    public final By driverDeductAlert = By.xpath("/html/body/div[13]");
    public final By noDD = By.xpath("/html/body/div[14]/div[3]/div/button[2]/span");
    public final By yesDD = By.xpath("/html/body/div[14]/div[3]/div/button[1]/span");
    public final By corpReviewDD = By.xpath("//*[@id=\"griddata\"]/tbody/tr/td[2]/a");



    //SplitAmount
    public final By tractorSA = By.id("Text63");
    public final By agentAmountSA = By.id("Text57");
    public final By driverAmountSA = By.id("Text59");
    public final By effectivedateSA = By.id("Text11");
    public final By noOfSplitsSA = By.id("Text9");
    public final By noOfDaysSA = By.id("SplitAmountDays1");

    public final By agentNotesSA = By.xpath("//*[@id=\"txtSPAgentNotes\"]/tbody/tr/td[1]/input");
    public final By agentNotesCustomerSA = By.xpath("//*[@id=\"txtSPCustomerBill\"]");
    public final By agentNotesChassisSA = By.xpath("//*[@id=\"txtSPChasisNumber\"]");
    public final By agentNotesContainerSA = By.xpath("//*[@id=\"txtSPContainerNumber\"]");

    public final By driverNotesSA = By.xpath("//*[@id=\"txtSPDriverNotes\"]/tbody/tr/td[1]/input");
    public final By driverNotesCustomerSA = By.xpath("//*[@id=\"txtSPCustomerNo\"]");
    public final By driverNotesAgentSA = By.xpath("//*[@id=\"txtSPAgentCode\"]");
    public final By notesSA = By.id("SplitAmountNotes");




    public final By alertNotesSA = By.xpath("/html/body/div[12]");
    public final By okSA = By.xpath("//button[@type='button' and span='Ok']");
    public final By goSA = By.id("SplitAmountbutton");
    public final By splitAmountAlert = By.xpath("/html/body/div[13]");

    public final By noSA = By.xpath("/html/body/div[14]/div[3]/div/button[2]/span");
    public final By yesSA = By.xpath("/html/body/div[14]/div[3]/div/button[1]/span");
    public final By crossCloseSA = By.xpath("/html/body/div[10]/div[1]/div/button/span[2]");

    public EquipmentConsoleTractorAdjustmentsPage(WebDriver driver) {
        super(driver);
    }

}