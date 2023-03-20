package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TractorSettlementAdjustmentsPage extends BasePage {

    public final By UnitNo = By.id("TractorUnitNumber_I");
    public final By StartDateFrom = By.id("txtFromDate");
    public final By StartDateTo = By.id("txtToDate");
    public final By Location = By.id("LocationCode_I");
    public final By Search = By.linkText("Search");
    public final By RecurringOnly = By.xpath("//*[@id=\"chkRecurring\"]");
    public final By EFS = By.id("chkEFS");
    public final By IncludeComplete = By.xpath("//*[@id=\"chkComplete\"]");
    public final By TotalRecordsReturned = By.id("dataTable_info");
    public final By DataTable = By.id("dataTable");
    public final By DataTableBody = By.xpath("//*[@id=\"dataTable\"]/tbody");
    public final By New = By.id("btnNew");
    public final By UnitNoNew = By.xpath("//*[@id=\"TractorUnitNumberform_I\"]");
    public final By PayCode = By.id("PayCode_I");
    public final By Status = By.id("Status_I");
    public final By Frequency = By.id("Frequency_I");
    public final By Amount = By.id("txtAmount");
    public final By OrderNumber = By.id("txtOrderNo");
    public final By MaxNoOfAdjustments = By.id("maxAdjustment");
    public final By MaxLimit = By.id("maxLimit");
    public final By TotalToDate = By.id("totalToDate");
    public final By Enddate = By.id("txtEndDate");
    public final By PayToVendorID = By.xpath("//*[@id=\"VendorPartial\"]/tbody/tr/td[2]");
    public final By PayToVendorPayCode = By.xpath("//*[@id=\"PayToVendorPayCodeTractorSettlementPartial\"]/tbody/tr/td[2]");
    public final By LastActivityAmount = By.id("lastActivityAmount");
    public final By LastActivityDate = By.id("txtLastActivityDate");
    public final By Notes = By.id("txtAreaNotes");
    public final By QuickEntry = By.id("btnQuickEntry");
    public final By UnitNoQuickEntry = By.xpath(" //*[@id=\"dropUnitNumber0\"]");
    public final By PayCodeQuickEntry = By.id("dropPayCode0");
    public final By FrequencyQuickEntry = By.id("txtFreq0");
    public final By AmountQuickEntry = By.id("txtAmount0");
    public final By StartDateFromQuickEntry = By.id("startDate0");
    public final By MaxLimitQuickEntry = By.id("txtMaxLimit0");
    public final By MaxNoOfAdjustmentsQuickEntry = By.id("txtMaxAdj0");
    public final By OrderNumberQuickEntry = By.id("orderNumberTd0");
    public final By NotesQuickEntry = By.id("areaNotes0");
    public final By SplitsQuickEntry = By.id("txtsplit0");
    public final By CloseQuickEntry = By.linkText("Close");
    public final By QuickEdit = By.id("btnQuickEdit");
    public final By UnitNoQuickEdit = By.id("UnitNoQuickEditPartial_I");
    public final By PayCodeQuickEdit = By.id("PayCodeQuickEditPartial_I");
    public final By SearchQuickEdit = By.linkText("Search");
    public final By SaveQuickEdit = By.linkText("Save");
    public final By StartDateQuickEdit = By.id("startDate15951095");
    public final By AmountQuickEdit = By.id("amount15951095");
    public final By Report = By.id("btnReport");
    public final By SearchResults = By.linkText("Search Results");
    public final By PayCodeAdv = By.id("AdvPayCodePartial_I");
    public final By FrequencyAdv = By.id("AdvFrequencyPartial_I");
    public final By OrderNo = By.id("txtorderNumber");
    public final By Edit = By.linkText("EDIT");
    public final By StatusEdit = By.id("Status_I");
    public final By Save = By.linkText("Save");
    public final By Ok = By.xpath("//*[@id=\"btnOK\"]/img");
    public final By Cancel = By.linkText("Cancel");


    public TractorSettlementAdjustmentsPage(WebDriver driver) {
        super(driver);
    }
}
