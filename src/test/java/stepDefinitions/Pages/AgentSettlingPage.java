package stepDefinitions.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;





public class AgentSettlingPage extends BasePage{

    public final By AgentSettlingTableHead = By.xpath("/html/body/section/section/div/div/div[1]/div[1]/h3/span");
    public final By OrdersWaitingTobeFinalizedFilterIcon = By.cssSelector("#img12");
    public final By OrdersWaitingTobeFinalizedDropDownYes = By.xpath("//*[@id=\"thOWTBF\"]/div/div/p[4]");
    public final By OrdersWaitingTobeFinalizedDropDownNo = By.xpath("//*[@id=\"thOWTBF\"]/div/div/p[3]");
    public final By OrdersWaitingTobeFinalizedDropDownClearFilter = By.xpath("//*[@id=\"thOWTBF\"]/div/div/p[2]");
    public final By ShowDetails = By.xpath("//*[@id=\"tblSettlementSummary\"]/tbody/tr[1]/td[14]/a");
    public final By OrdersWaitingToBeFinalized = By.linkText("Orders Waiting To Be finalized");
    public final By AgentCode = By.xpath("//*[@id=\"vendorCodeAndAgentcode\"]");
    public final By VendorName = By.xpath("//*[@id=\"showVendorName\"]");
    public final By RecordsReturned = By.xpath("//*[@id=\"lblRowCount\"]");
    public final By Finalize = By.linkText("Finalize");

    public final By SettleYourAgents = By.linkText("Settle Your Agents");
    public final By FinalAgentSettlementSummaryTable = By.xpath("/html/body/section/div[1]/div[2]/div[1]");
    public final By ConfirmAndSettle = By.linkText("Confirm And Settle");

    public final By ToggleOnOffVerifiedBox = By.xpath("//*[@id=\"tblSettlementSummary\"]/tbody/tr/td[1]/p/label/span");
    public final By SettlementOrderDetailsTable = By.xpath("//*[@id=\"tblSettlingSummaryDetails_wrapper\"]");

    public final By ShowOnlyOrderWithFlags = By.id("summaryDetailsWithFlag");
    public final By TableShowOnlyOrderWithFlags = By.xpath("//*[@id=\"divSettlingSummaryDetail\"]");
    public final By ShowNextWeekOrders = By.id("summaryDetailsNextWeek");
    public final By ShowPreviousWeekOrders = By.id("summaryDetailsPrevWeek");

    public final By AdjustmentsDetails = By.xpath("//*[@id=\"tblAdjustmentDetail\"]");
    public final By AdjustmentsOnHold = By.xpath("//*[@id=\"tblAdjustmentOnHold\"]");
    public final By ReloadAllMyActiveAgentsNotSettled = By.linkText("Reload All My Active Agents Not Settled");
    public final By SettlementSummary = By.xpath("//*[@id=\"tblSettlementSummary\"]");

    public final By ViewEpicorDetails = By.xpath("View Epicor Details");
    public final By viewEpicorDetailSummary = By.xpath("//*[@id=\"viewEpicorDetailSummary\"]");
    public final By viewEpicorHeader = By.xpath("//*[@id=\"viewEpicorHeader\"]");

    public final By Add = By.linkText("Add");
    public final By Confirmation = By.xpath("//*[@id=\"Divpopup\"]/div");
    public final By ConfirmationYes = By.linkText("Yes");

    public AgentSettlingPage(WebDriver driver) {
        super(driver);
    }
}
