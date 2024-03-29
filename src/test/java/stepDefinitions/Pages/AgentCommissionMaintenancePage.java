package stepDefinitions.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AgentCommissionMaintenancePage extends BasePage {

    // By Locators:
    public final By agentLocationCode = By.xpath("//*[@id=\"AgentCodeList_I\"]"); //*[@id="AgentCodeList_I"]
    public final  By agentCodeDescription = By.xpath("//*[@id=\"lblAgentCodeDesc\"]");
    public final  By agentIsActive = By.xpath("//*[@id=\"lblIsActiveInActiveAgent\"]/b");


    // Constructor of the page class:
    public AgentCommissionMaintenancePage(WebDriver driver) {
        super(driver);
    }



}
