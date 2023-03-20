package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResourceMaintenancePage extends BasePage{

    public final By ResourceMaintenance = By.id("btnResourceMaintenance");
    public final By SearchBox = By.id("txtSearchFilter");
    public final By Search = By.linkText("Search");
    public final By TotalRecordsReturned = By.id("countTableRecord");
    public final By DataTable = By.id("tblResourceMaintenance");
    public final By DataTableBody = By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody");
    public final By View = By.linkText("View");
    public final By Edit = By.linkText("Edit");
    public final By HomeLocationEdit = By.id("LocationEquipFilePartial_I");
    public final By DisplayNameEdit = By.id("txtName");
    public final By OwnerIFTAEdit= By.id("OwnerIFTAPartial_I");
    public final By CellCarrierEdit = By.id("CellCarrierPartial_I");
    public final By CellPhoneEdit = By.id("txtCellPhone");
    public final By EmailAddressEdit = By.id("txtEmailAddress");
    public final By EFSCardEdit = By.id("txtEfsCard");
    public final By StatusEdit = By.id("StatusEquipFilePartial_I");
    public final By SaveEdit = By.linkText("Save");
    public final By Ok = By.xpath("//*[@id=\"btnOK\"]/img");
    public final By New = By.linkText("New");
    public final By HeaderNew = By.id("popupHeading");
    public final By TypeNew = By.id("EquipmentTypePartial_I");
    public final By CompanyNew = By.id("CompanyCodeResourceMaintPartial_I");
    public final By CodeNew = By.id("txtCode");
    public final By HomeLocationNew = By.id("LocationEquipFilePartial_I");
    public final By DisplayNameNew = By.id("txtName");
    public final By OwnerIFTANew = By.id("OwnerIFTAPartial_I");
    public final By CellCarrierNew = By.id("CellCarrierPartial_I");
    public final By CellPhoneNew = By.id("txtCellPhone");
    public final By EmailAddressNew = By.id("txtEmailAddress");
    public final By EFSCardNew = By.id("txtEfsCard");
    public final By StatusNew = By.id("StatusEquipFilePartial_I");
    public final By SaveNew = By.linkText("Save");
    public final By Cancel = By.linkText("Cancel");

    public final By Report = By.linkText("Report");
    public final By SearchResults = By.linkText("Search Results");

    public ResourceMaintenancePage(WebDriver driver) {
        super(driver);
    }
}
