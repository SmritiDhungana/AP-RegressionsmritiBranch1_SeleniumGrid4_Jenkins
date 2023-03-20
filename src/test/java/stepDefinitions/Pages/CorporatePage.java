package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CorporatePage extends BasePage {


        public final By settlements = By.id("btnSettlements");
        public final By basicFileMaintenance = By.id("btnBasicFileMaintenance");
        public final By fuelAndMileage = By.id("btnFuelMileage");
        public final By fuelPurchaseMaintenance = By.id("btnFuelPurchaseMaintenance"); //*[@id="btnFuelPurchaseMaintenance"]
        public final By resources = By.id("btnEquipment");


        public CorporatePage(WebDriver driver) {
            super(driver);
        }
}
