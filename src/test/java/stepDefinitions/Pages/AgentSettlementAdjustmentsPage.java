package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentSettlementAdjustmentsPage extends BasePage {

    public final By VendorIDBox = By.xpath("//*[@id=\"VendorPartial_I\"]");
    public final By View = By.id("linkView");
    public final By VendorCode = By.id("VendorPartial_I");
    public final By AgentCode = By.id("AgentCodePartial_I");
    public final By StartDateFrom = By.id("txtFromDate");
    public final By StartDateTo = By.id("txtToDate");
    public final By VendorCodeDescription = By.xpath("//*[@id=\"lblVendorCodedescription\"]");
    public final By CrossIcon = By.xpath("//*[@id=\"crossIconPopup\"]");
    public final By SearchByAgentCodeBox = By.xpath("//*[@id=\"AgentCodePartial_I\"]");
    public final By RecurringOnly = By.xpath("//*[@id=\"chkRecurring\"]");
    public final By EFS = By.xpath("//*[@id=\"chkEFS\"]");
    public final By IncludeComplete = By.id("chkIncludeComplete");
    public final By TotalRecordsReturned = By.id("dataTable_info");
    public final By DataTable = By.id("dataTable");
    public final By Edit = By.linkText("EDIT");
    public final By Save = By.linkText("Save");
    public final By New = By.linkText("New");
    public final By Report = By.linkText("Report");
    public final By PopUpReport = By.xpath("//*[@id=\"Divpopup\"]");
    public final By Yes = By.linkText("Yes");
    public final By SearchResults = By.linkText("Search Results");
    public final By HeaderAdvanceSearch = By.xpath("//*[@id=\"mainForm\"]/div/div/div/div[1]/div[1]/h4");
    public final By AdvancedSearch = By.linkText("Advanced Search");
    public final By Search = By.linkText("Search");
    public final By SearchOnAdvancedSearch = By.linkText("Search");

    public AgentSettlementAdjustmentsPage(WebDriver driver) {
        super(driver);
    }
}
