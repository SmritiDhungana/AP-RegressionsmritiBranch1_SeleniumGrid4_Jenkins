package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TractorVendorRelationshipPage extends BasePage {

    public final By TractorVendorCodeRelationshipMaintenanceHeader = By.xpath("//*[@id=\"mainDiv\"]/div[1]/div[1]/h4/strong/span");

    public final By Search = By.id("btnSearch");
    public final By UnitNumber = By.id("SearchTractorUnitNumber_I");
    public final By OwnerId = By.id("SearchOwnerId_I");
    public final By OwnerOrVendorName = By.id("txtSearch");
    public final By Report = By.id("btnReport");
    public final By SearchResults = By.id("btnNo");
    public final By New = By.id("btnNewSettlement12");
    public final By ReportNotInRelationship = By.id("btnNotInRelationship");
    public final By UnitNumberNew = By.id("TractorUnitNumber_I");
    public final By View = By.linkText("VIEW");
    public final By AccountingStatus = By.id("Status_I");
   // public final By New1 = By.xpath("//*[@id=\"actionNotRelation\"]/a");
    public final By New1 = By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/tbody/tr[1]/td[7]/a");
    public final By Edit = By.linkText("Edit");
    public final By Save = By.linkText("Save");
    public final By CrossIconPopup = By.id("crossIconPopup");
    public final By Cancel = By.id("btnCancel");
    public final By VendorCode = By.id("SearchVendorCodePartial_I");
    public final By VendorCodeNew = By.xpath(" //table[@id = 'VendorCodePartial_DDD_L_LBT']");
    public final By VendorCode1 = By.xpath("  //*[@id=\"VendorCodePartial_I\"]");
    public final By SaveNew = By.linkText("btnSaveAgent");
    public final By TotalRecordsReturnedNotInRelationship = By.xpath("//*[@id=\"totalRecordCount\"]");
    public final By DataTableNotInRelationship = By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]");

    public final By LocationSearchBox = By.xpath("//*[@id=\"SearchLocationPartial_I\"]");
    public final By LocationDropDown = By.xpath("//*[@id=\"SearchLocationPartial_B-1Img\"]");

    public TractorVendorRelationshipPage(WebDriver driver) {
        super(driver);
    }
}
