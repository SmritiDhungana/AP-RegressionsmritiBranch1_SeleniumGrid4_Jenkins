package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.CommonUtils.CommonUtils;
import stepDefinitions.Pages.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class AgentPortalBillingFormStepDef {
    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";

    String bookLoadNumber = "";
    String usernameExpctd = "";
    String billToExpctd = "";
    String fromExpctd = "";
    String toExpctd = "";
    String containerExpctd = "";
    String chassisExpctd = "";
    String emptyContainerExpctd = "";
    String referenceOneExpctd = "";
    String referenceTwoExpctd = "";
    String referenceThreeExpctd = "";
    String freightChargesTotalRPExpctd = "";
    String freightChargesIndependentContractorPayRPExpctd = "";
    String fuelChargesQuantityRPExpctd = "";
    String fuelChargesRateRPExpctd = "";
    String fuelChargesIndependentContractorPayRPExpctd = "";
    String dailyChassisChargesQuantityRPExpctd = "";
    String dailyChassisChargesRateRPExpctd = "";
    String dailyChassisChargesIndependentContractorPayRPExpctd = "";
    String pickupAppointmentDateExpctd = "";
    String pickupAppointmentTimeExpctd = "";
    String actualDateExpctd = "";

    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    CommonUtils commonUtils = new CommonUtils();
    BillingPage billingPage = new BillingPage(driver);
    RetrieveOrderPage retrieveOrderPage = new RetrieveOrderPage(driver);
    DispatchGridPage dispatchGridPage = new DispatchGridPage(driver);
    StatusSelectionPage statusSelectionPage = new StatusSelectionPage(driver);
    MainMenuPage mainMenuPage = new MainMenuPage(driver);
    Logger log = Logger.getLogger("BillingStepDef");






    @After("@FailureScreenShot10")
    public void takeScreenshotOnFailure10(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }




    @Given("^run test for \"([^\"]*)\" on browser \"([^\"]*)\"$")
    public void runTestForOnBrowser(String environment, String browser) throws MalformedURLException {

        URL = browserDriverInitialization.getDataFromPropertiesFileForAgentPortal(environment, browser);
        if (browser.equals("chrome")) {
            cap.setPlatform(Platform.ANY);
            cap.setBrowserName("chrome");
            ChromeOptions options = new ChromeOptions();
            options.merge(cap);
        } else if (browser.equals("MicrosoftEdge")) {
            cap.setPlatform(Platform.ANY);
            cap.setBrowserName("MicrosoftEdge");
            EdgeOptions options = new EdgeOptions();
            options.merge(cap);
        }
        driver = new RemoteWebDriver(new URL("http://192.168.0.14:4444"), cap);
    }

    @Given("^enter the url$")
    public void enter_the_url() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(URL);
    }

  /*  @And("^login on the agents portal with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginOnTheAgentsPortalWithUsernameAndPassword(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        usernameExpctd = username;
        driver.findElement(loginPage.username).sendKeys(usernameExpctd.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.password));
        driver.findElement(loginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.loginButton));
        driver.findElement(loginPage.loginButton).click();

        driver.findElement(loginPage.Alert).isDisplayed();
        driver.findElement(loginPage.EnterFirstNameandLastName).sendKeys("smriti dhungana");
        driver.findElement(loginPage.Ok).click();
        //   driver.findElement(By.xpath("//*[@id=\"closePopup\"]/img")).click();
    }  */

    @Then("^navigate to the bookload page$")
    public void navigate_to_the_bookload_page() {
        driver.findElement(mainMenuPage.operations).click();
        driver.findElement(mainMenuPage.biling).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }


    //............................................/ #50 @CreateOrders/BookLoad/................................................//

    @Given("^run test for \"([^\"]*)\" on browser \"([^\"]*)\" in Agents Portal$")
    public void runTestForOnBrowserInAgentsPortal(String environment, String browser) throws MalformedURLException {

        URL = browserDriverInitialization.getDataFromPropertiesFileForAgentPortal(environment, browser);
        if (browser.equals("chrome")) {
            cap.setPlatform(Platform.ANY);
            cap.setBrowserName("chrome");
            ChromeOptions options = new ChromeOptions();
            options.merge(cap);
        } else if (browser.equals("MicrosoftEdge")) {
            cap.setPlatform(Platform.ANY);
            cap.setBrowserName("MicrosoftEdge");
            EdgeOptions options = new EdgeOptions();
            options.merge(cap);
        }
        driver = new RemoteWebDriver(new URL("http://192.168.0.14:4444"), cap);
    }

    @Given("^enter the url of Agents Portal$")
    public void enter_the_url_of_agents_portal() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(URL);
    }

    @And("^login on the agents portal with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void loginOnTheAgentsPortalWithUsernameAndPassword(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        usernameExpctd = username;
        driver.findElement(loginPage.username).sendKeys(usernameExpctd.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.password));
        driver.findElement(loginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.loginButton));
        driver.findElement(loginPage.loginButton).click();

        driver.findElement(loginPage.Alert).isDisplayed();
        driver.findElement(loginPage.EnterFirstNameandLastName).sendKeys("smriti dhungana");
        driver.findElement(loginPage.Ok).click();
          //  driver.findElement(By.xpath("//*[@id=\"closePopup\"]/img")).click();
    }


    @And("^login on the agents portal with username \"([^\"]*)\" and password \"([^\"]*)\" FOR HCI ONLY$")
    public void loginOnTheAgentsPortalWithUsernameAndPasswordForHciOnly(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        usernameExpctd = username;
        driver.findElement(loginPage.username).sendKeys(usernameExpctd.toUpperCase());
     //   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.password));
        driver.findElement(loginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.loginButton));
        driver.findElement(loginPage.loginButton).click();

        driver.findElement(loginPage.Alert).isDisplayed();
        driver.findElement(loginPage.EnterFirstNameandLastName).sendKeys("smriti dhungana");
        driver.findElement(loginPage.Ok).click();
         driver.findElement(By.xpath("//*[@id=\"closePopup\"]/img")).click();
    }



    @And("^enter Bill To as \"([^\"]*)\" From as \"([^\"]*)\" and To as \"([^\"]*)\"$")
    public void enterBillToAsFromAsAndToAs(String billto, String from, String to) {
        billToExpctd = billto;
        driver.findElement(billingPage.billto).sendKeys(billto);
        driver.findElement(billingPage.billto_retrieve).click();

        fromExpctd = from;
        driver.findElement(billingPage.from).sendKeys(from);
        driver.findElement(billingPage.from_retrieve).click();

        toExpctd = to;
        driver.findElement(billingPage.to).sendKeys(to);
        driver.findElement(billingPage.to_retrieve).click();
    }


    @And("^in References Tab enter the Container as \"([^\"]*)\" Chassis as \"([^\"]*)\" and Empty Container as \"([^\"]*)\"$")
    public void inReferencesTabEnterTheContainerAsChassisAsAndEmptyContainerAs(String container, String chassis, String emptyContainer) {
        containerExpctd = container + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.container).sendKeys(containerExpctd);

        chassisExpctd = chassis + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.chassis).sendKeys(chassisExpctd);

        emptyContainerExpctd = emptyContainer + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.emptyContainer).sendKeys(emptyContainerExpctd);
    }

    @And("^in References Tab enter the ReferenceOne as \"([^\"]*)\" ReferenceTwo as \"([^\"]*)\" and RefOne as \"([^\"]*)\"$")
    public void inReferencesTabEnterTheReferenceOneAsReferenceTwoAsAndReferenceOneAs(String referenceOne, String referenceTwo, String refOne) {
        referenceOneExpctd = referenceOne + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceOne).sendKeys(referenceOneExpctd);

        referenceTwoExpctd = referenceTwo + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceTwo).sendKeys(referenceTwoExpctd);

        referenceThreeExpctd = refOne + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceThree).sendKeys(referenceThreeExpctd);

        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"EquipmentType\"]")));
        dropdown.selectByVisibleText("48 foot van");

    }

    @And("^in Financial Information - Customer Charges Tab enter the Freight Charges as \"([^\"]*)\"$")
    public void inFinancialInformationCustomerChargesTabEnterTheFreightChargesAs(String freightChargesTotal) {
        driver.findElement(billingPage.freightChargesTotal).sendKeys(freightChargesTotal);
        driver.findElement(billingPage.freightChargesTotal).sendKeys(Keys.TAB);
        freightChargesTotalRPExpctd = driver.findElement(billingPage.freightChargesTotal).getAttribute("value");
    }



    @Given("^in Financial Information - Customer Charges Tab enter the Fuel Surcharges Quantity as \"([^\"]*)\" and Rate as \"([^\"]*)\" \"([^\"]*)\"$")
    public void in_Financial_Information_Customer_Charges_Tab_enter_The_Fuel_Surcharges_Quantity_as_and_Rate_as(String CustomerChargesFuelSurchargesQuantity, String CustomerChargesFuelSurchargesRate, String CustomerChargesFuelSurchargesTotal) throws Throwable {

        //  driver.findElement(billingPage.fuelSurchargesQuantity).sendKeys(CustomerChargesFuelSurchargesQuantity);
        //   driver.findElement(billingPage.fuelSurchargesQuantity).sendKeys(Keys.TAB);
        //  fuelChargesQuantityRPExpctd = driver.findElement(billingPage.fuelSurchargesQuantity).getAttribute("value");

        //   driver.findElement(billingPage.fuelSurchargesRate).sendKeys(CustomerChargesFuelSurchargesRate);
        //  driver.findElement(billingPage.fuelSurchargesRate).sendKeys(Keys.TAB);
        //  fuelChargesRateRPExpctd = driver.findElement(billingPage.fuelSurchargesRate).getAttribute("value");

        driver.findElement(billingPage.fuelSurchargesTotal).sendKeys(CustomerChargesFuelSurchargesTotal);
        driver.findElement(billingPage.fuelSurchargesTotal).sendKeys(Keys.TAB);
        freightChargesTotalRPExpctd = driver.findElement(billingPage.fuelSurchargesTotal).getAttribute("value");

    }


    @Given("^in Financial Information - Customer Charges Tab enter the Daily Chassis Charges Quantity as \"([^\"]*)\" and Rate as \"([^\"]*)\"$")
    public void in_Financial_Information_Customer_Charges_Tab_enter_The_Daily_Chassis_Charges_Quantity_as_and_Rate_as(String CustomerChargesDailyChassisChargesQuantity, String CustomerChargesDailyChassisChargesRate) {

        driver.findElement(billingPage.dailyChasisChargesQuantity).sendKeys(CustomerChargesDailyChassisChargesQuantity);
        driver.findElement(billingPage.dailyChasisChargesQuantity).sendKeys(Keys.TAB);
        dailyChassisChargesQuantityRPExpctd = driver.findElement(billingPage.dailyChasisChargesQuantity).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesRate).sendKeys(CustomerChargesDailyChassisChargesRate);
        driver.findElement(billingPage.dailyChasisChargesRate).sendKeys(Keys.TAB);
        dailyChassisChargesRateRPExpctd = driver.findElement(billingPage.dailyChasisChargesRate).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass value \"([^\"]*)\" from the TractorOne Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_value_from_the_TractorOne_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorOne, String fCOne, String fSOne, String dCCOne) {

        driver.findElement(billingPage.tractorOne).sendKeys(tractorOne);

        driver.findElement(billingPage.freightChargesIndependentContractorPay).sendKeys(fCOne);
        driver.findElement(billingPage.freightChargesIndependentContractorPay).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPay).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).sendKeys(fSOne);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).sendKeys(dCCOne);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass value \"([^\"]*)\" from the TractorTwo Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_value_from_the_TractorTwo_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorTwo, String fCTwo, String fSTwo, String dCCTwo) {

        driver.findElement(billingPage.tractorTwo).sendKeys(tractorTwo);

        driver.findElement(billingPage.freightChargesIndependentContractorPayTwo).sendKeys(fCTwo);
        driver.findElement(billingPage.freightChargesIndependentContractorPayTwo).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayTwo).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayTwo).sendKeys(fSTwo);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayTwo).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayTwo).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayTwo).sendKeys(dCCTwo);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayTwo).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayTwo).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass value \"([^\"]*)\" from the TractorThree Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_value_from_the_TractorThree_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorThree, String fCThree, String fSThree, String dCCThree) throws InterruptedException {

        driver.findElement(billingPage.tractorThree).sendKeys(tractorThree);

        driver.findElement(billingPage.freightChargesIndependentContractorPayThree).sendKeys(fCThree);
        driver.findElement(billingPage.freightChargesIndependentContractorPayThree).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayThree).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayThree).sendKeys(fSThree);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayThree).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayThree).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayThree).sendKeys(dCCThree);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayThree).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayThree).getAttribute("value");


        driver.findElement(By.xpath("//*[@id=\"divFinancialInformation_Financialinformation1_next\"]/img")).click();
        Thread.sleep(3000);
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass value \"([^\"]*)\" from the TractorFour Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_value_from_the_TractorFour_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorFour, String fCFour, String fSFour, String dCCFour) {

        driver.findElement(billingPage.tractorFour).sendKeys(tractorFour);

        driver.findElement(billingPage.freightChargesIndependentContractorPayFour).sendKeys(fCFour);
        driver.findElement(billingPage.freightChargesIndependentContractorPayFour).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayFour).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFour).sendKeys(fSFour);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFour).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFour).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFour).sendKeys(dCCFour);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFour).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFour).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass value \"([^\"]*)\" from the TractorFive Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_value_from_the_TractorFive_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorFive, String fCFive, String fSFive, String dCCFive) {

        driver.findElement(billingPage.tractorFive).sendKeys(tractorFive);

        driver.findElement(billingPage.freightChargesIndependentContractorPayFive).sendKeys(fCFive);
        driver.findElement(billingPage.freightChargesIndependentContractorPayFive).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPayFive).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFive).sendKeys(fSFive);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFive).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPayFive).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFive).sendKeys(dCCFive);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFive).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPayFive).getAttribute("value");
    }


    @Given("^in Financial Information - Independent Contractor Pay Tab pass value \"([^\"]*)\" from the TractorSix Field, Freight Charges \"([^\"]*)\", Fuel Surcharges \"([^\"]*)\" and Daily Chassis Charges \"([^\"]*)\"$")
    public void in_Financial_Information_Independent_Contractor_Pay_Tab_pass_value_from_the_TractorSix_Field_Freight_Charges_Fuel_Surcharges_and_Daily_Chassis_Charges(String tractorSix, String fCSix, String fSSix, String dCCSix) {

        driver.findElement(billingPage.tractorSix).sendKeys(tractorSix);

        driver.findElement(billingPage.freightChargesIndependentContractorPaySix).sendKeys(fCSix);
        driver.findElement(billingPage.freightChargesIndependentContractorPaySix).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPaySix).getAttribute("value");

        driver.findElement(billingPage.fuelSurChargesIndependentContractorPaySix).sendKeys(fSSix);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPaySix).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPaySix).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPaySix).sendKeys(dCCSix);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPaySix).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPaySix).getAttribute("value");
    }


    @And("in Operations Information - enter the Actual Date as \"([^\"]*)\" and Time as \"([^\"]*)\"$")
    public void inOperationsInformationEnterTheActualDateAsAndTimeAs(String actualDate, String actualTime) {
        actualDateExpctd = actualDate;
        driver.findElement(billingPage.actualDate).sendKeys(actualDate);
        driver.findElement(billingPage.actualTime).sendKeys(actualTime);
    }

    @Given("^in Operations Information - enter the Delivery Appointment Date as \"([^\"]*)\" and Time as \"([^\"]*)\"$")
    public void in_Operations_Information_enter_the_Delivery_Appointment_Date_as_and_Time_as(String dADate, String dATime) {
        driver.findElement(billingPage.pickupAppointmentDate).sendKeys(dADate);
        driver.findElement(billingPage.pickupAppointmentTime).sendKeys(dATime);
    }


    @Then("click on Book Load and validate if the Booking Number got generated on BookLoad")
    public void click_on_book_load_and_validate_if_the_booking_number_got_generated_on_book_load() {

        driver.findElement(billingPage.bookLoadButton).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        driver.close();
    }

    //............................................/ #51 @CreateOrders/BookLoadWithBillingDoc /................................................//

    @Then("^click on Book Load$")
    public void click_on_Book_Load() throws InterruptedException {
        driver.findElement(billingPage.bookLoadButton).click();
        Thread.sleep(3000);
    }


    //............................................/ #52 @CreateOrders/BCPD /................................................//

    @Then("^click on BCPD and validate if the Booking Number got generated$")
    public void click_on_BCPD_and_validate_if_the_Booking_Number_got_generated() throws InterruptedException {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        Thread.sleep(3000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
    }


    //............................................/ #53 @CreateOrders/BCPD /................................................//

    @Then("^click on BCPD$")
    public void click_on_BCPD() throws InterruptedException {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        Thread.sleep(3000);
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
    }


    @Then("Click on Scan Documents on Bill Created")
    public void click_on_scan_documents_on_bill_created() throws InterruptedException {
        getAndSwitchToWindowHandles();
        driver.findElement(By.id("Scandocuments")).click();
        Thread.sleep(1000);

        // Click on Browse Button
        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"btnBrowse\"]")).click();

        driver.switchTo().frame("RadWindowContentFrameRadWindowManager1_ClosableWindow");
        Thread.sleep(2000);
        WebElement uploadDoc = driver.findElement(By.xpath("//*[@id=\"FileUpload1\"]"));
        File file = new File("C:/Users/Smriti Dhugana/Documents/Billing Document.pdf");

        System.out.println(file.getAbsolutePath());
        uploadDoc.sendKeys(file.getAbsolutePath());
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"btnUpload\"]")).click();  ///done

        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"UploadButton\"]")).click();  //upload
        Thread.sleep(1000);
//OK
     /*   getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"A1\"]")).click(); //Return to Load
        //*[@id="A1"]
        Thread.sleep(3000);  */
    }


    //............................................/ #54 @CreateOrdersPDOnly /................................................//
    @Then("^click on PDOnly and validate if the Booking Number got generated$")
    public void click_on_PDOnly_and_validate_if_the_Booking_Number_got_generated() throws InterruptedException {
        driver.findElement(billingPage.payDriverOnlyButton).click();
        Thread.sleep(3000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
    }



    //............................................/ #55 @CreateOrders/PDOnlyWithBillingDoc /................................................//
    @Then("^click on PDOnly$")
    public void click_on_PDOnly() throws InterruptedException {
        driver.findElement(billingPage.payDriverOnlyButton).click();
        Thread.sleep(3000);
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
    }


    //............................................/ #56 @CreateOrdersBCOnly /................................................//
    @Then("^click on BCOnly and validate if the Booking Number got generated$")
    public void click_on_BCOnly_and_validate_if_the_Booking_Number_got_generated() throws InterruptedException {
        driver.findElement(billingPage.billCustomerOnlyButton).click();
        Thread.sleep(3000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        driver.findElement(By.xpath("//*[@id=\"A1\"]")).click();
    }


    //............................................/ #57 @CreateOrders/BCOnlyWithBillingDoc /................................................//
    @Then("^click on BCOnly$")
    public void click_on_BCOnly() throws InterruptedException {
        driver.findElement(billingPage.billCustomerOnlyButton).click();
        Thread.sleep(3000);
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
    }





    //............................................/  /................................................//

    @Then("^click on Bill Customer Only$")
    public void clickOnBillCustomerOnly() throws InterruptedException {
        driver.findElement(billingPage.billCustomerOnlyButton).click();
        Thread.sleep(3000);
    }

    @Then("^validate the newly generated Order Number$")
    public void validate_the_newly_generated_Order_Number() throws InterruptedException {
        Thread.sleep(2000);
        getAndSwitchToWindowHandles();
        System.out.println("=========================================");
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));
        bookLoadNumber = driver.findElement(billingPage.billCreated).getText().substring(0, 6);
        System.out.println("=========================================");
    }


    @And("^enter billto as \"([^\"]*)\" from as \"([^\"]*)\" and to as \"([^\"]*)\"$")
    public void enterBilltoAsFromAsAndToAs(String billto, String from, String to) {

        billToExpctd = billto;
        driver.findElement(billingPage.billto).sendKeys(billto);
        driver.findElement(billingPage.billto_retrieve).click();

        fromExpctd = from;
        driver.findElement(billingPage.from).sendKeys(from);
        driver.findElement(billingPage.from_retrieve).click();

        toExpctd = to;
        driver.findElement(billingPage.to).sendKeys(to);
        driver.findElement(billingPage.to_retrieve).click();

    }


    @And("^in references tab enter container as \"([^\"]*)\" chasis as \"([^\"]*)\" and empty container as \"([^\"]*)\"$")
    public void inReferencesTabEnterContainerAsChasisAsAndEmptyContainerAs(String container, String chasis, String emptyContainer) {
        containerExpctd = container + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.container).sendKeys(containerExpctd);

        chassisExpctd = chasis + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.chassis).sendKeys(chassisExpctd);

        emptyContainerExpctd = emptyContainer + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.emptyContainer).sendKeys(emptyContainerExpctd);
    }

    @And("^in references tab enter referenceOne as \"([^\"]*)\" referenceTwo as \"([^\"]*)\" and referenceThree as \"([^\"]*)\"$")
    public void inReferencesTabEnterReferenceOneAsReferenceTwoAsAndReferenceThreeAs(String referenceOne, String referenceTwo, String referenceThree) {
        referenceOneExpctd = referenceOne + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceOne).sendKeys(referenceOneExpctd);

        referenceTwoExpctd = referenceTwo + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceTwo).sendKeys(referenceTwoExpctd);

        referenceThreeExpctd = referenceThree + commonUtils.getRandomFourLetterString();
        driver.findElement(billingPage.referenceThree).sendKeys(referenceThreeExpctd);

        Select dropdown = new Select(driver.findElement(By.xpath("//*[@id=\"EquipmentType\"]")));
        dropdown.selectByVisibleText(".NOT ARC");


    }

    @And("^in financial information - customer charges tab enter freight charges as \"([^\"]*)\"$")
    public void inFinancialInformationCustomerChargesTabEnterFreightChargesAs(String freightChargesTotal) {
        driver.findElement(billingPage.freightChargesTotal).sendKeys(freightChargesTotal);
        driver.findElement(billingPage.freightChargesTotal).sendKeys(Keys.TAB);
        freightChargesTotalRPExpctd = driver.findElement(billingPage.freightChargesTotal).getAttribute("value");
    }

    @And("^in financial information - independent contractor pay tab enter freight charges as \"([^\"]*)\"$")
    public void inFinancialInformationIndependentContractorPayTabEnterFreightChargesAs(String freightChargesContractorPay) {
        driver.findElement(billingPage.freightChargesIndependentContractorPay).sendKeys(freightChargesContractorPay);
        driver.findElement(billingPage.freightChargesIndependentContractorPay).sendKeys(Keys.TAB);
        freightChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.freightChargesIndependentContractorPay).getAttribute("value");
    }

    @And("^in financial information - customer charges tab enter fuel charges quantity as \"([^\"]*)\" and rate as \"([^\"]*)\"$")
    public void inFinancialInformationCustomerChargesTabEnterFuelChargesQuantityAsAndRateAs(String fuelQty, String fuelRate) {
        driver.findElement(billingPage.fuelSurchargesQuantity).sendKeys(fuelQty);
        driver.findElement(billingPage.fuelSurchargesQuantity).sendKeys(Keys.TAB);
        fuelChargesQuantityRPExpctd = driver.findElement(billingPage.fuelSurchargesQuantity).getAttribute("value");

        driver.findElement(billingPage.fuelSurchargesRate).sendKeys(fuelRate);
        driver.findElement(billingPage.fuelSurchargesRate).sendKeys(Keys.TAB);
        fuelChargesRateRPExpctd = driver.findElement(billingPage.fuelSurchargesRate).getAttribute("value");
    }

    @And("^in financial information - independent contractor pay tab enter fuel surcharges as \"([^\"]*)\"$")
    public void inFinancialInformationIndependentContractorPayTabEnterFuelSurchargesAs(String fuelSurchargesContractorPay) {
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).sendKeys(fuelSurchargesContractorPay);
        driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).sendKeys(Keys.TAB);
        fuelChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.fuelSurChargesIndependentContractorPay).getAttribute("value");
    }

    @And("^in financial information - customer charges tab enter daily chasis charges quantity as \"([^\"]*)\" and rate as \"([^\"]*)\"$")
    public void inFinancialInformationCustomerChargesTabEnterDailyChasisChargesQuantityAsAndRateAs(String dailyChasisQty, String dailyChasisRate) {
        driver.findElement(billingPage.dailyChasisChargesQuantity).sendKeys(dailyChasisQty);
        driver.findElement(billingPage.dailyChasisChargesQuantity).sendKeys(Keys.TAB);
        dailyChassisChargesQuantityRPExpctd = driver.findElement(billingPage.dailyChasisChargesQuantity).getAttribute("value");

        driver.findElement(billingPage.dailyChasisChargesRate).sendKeys(dailyChasisRate);
        driver.findElement(billingPage.dailyChasisChargesRate).sendKeys(Keys.TAB);
        dailyChassisChargesRateRPExpctd = driver.findElement(billingPage.dailyChasisChargesRate).getAttribute("value");
    }

    @And("in financial information - independent contractor pay tab enter daily chasis charges as \"([^\"]*)\"")
    public void inFinancialInformationIndependentContractorPayTabEnterDailyChasisChargesAs(String dailyChasisChargesContractorPay) {
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).sendKeys(dailyChasisChargesContractorPay);
        driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).sendKeys(Keys.TAB);
        dailyChassisChargesIndependentContractorPayRPExpctd = driver.findElement(billingPage.dailyChasisChargesIndependentContractorPay).getAttribute("value");
    }

    @And("in operations information - pickup appointment enter date as \"([^\"]*)\" and time as \"([^\"]*)\"")
    public void inOperationsInformationPickupAppointmentEnterDateAsAndTimeAs(String pickupDate, String pickupTime) {
        pickupAppointmentDateExpctd = pickupDate;
        driver.findElement(billingPage.pickupAppointmentDate).sendKeys(pickupDate);
        pickupAppointmentTimeExpctd = pickupTime;
        driver.findElement(billingPage.pickupAppointmentTime).sendKeys(pickupTime);
    }

    @And("in operations information - actual enter date as \"([^\"]*)\" and time as \"([^\"]*)\"")
    public void inOperationsInformationActualEnterDateAsAndTimeAs(String actualDate, String actualTime) {
        actualDateExpctd = actualDate;
        driver.findElement(billingPage.actualDate).sendKeys(actualDate);
        driver.findElement(billingPage.actualTime).sendKeys(actualTime);
    }

    ///////////////////////////////////////////////

    @Then("^click on book load$")
    public void click_on_book_load() {
        driver.findElement(billingPage.bookLoadButton).click();
    }


    @Then("^click yes on alert$")
    public void click_yes_on_alert() {
        getAndSwitchToWindowHandles();
        driver.findElement(By.xpath("//*[@id=\"Yes\"]/img")).click();
    }

    @Then("^validate if the booking number got generated$")
    public void validate_if_the_booking_number_got_generated() {
        getAndSwitchToWindowHandles();
        System.out.println("Bill Created" + driver.findElement(By.xpath("//strong//span")).getText());

    }

    /////////////////////////////////////////////////////////




    @Then("close all open browsers")
    public void closeAllOpenBrowsers() {
        driver.quit();
    }

    @Then("retrieve the load with loadnumber and validate the fields")
    public void retrieveTheLoadWithLoadnumberAndValidateTheFields() {
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.retrieveOrderButton).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.switchTo().frame(0);

        driver.findElement(retrieveOrderPage.orderNumberField).sendKeys(bookLoadNumber);
        driver.findElement(retrieveOrderPage.goButton).click();

        Assert.assertTrue(driver.findElement(retrieveOrderPage.billToRP).getText().contains(billToExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.fromRP).getText().contains(fromExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.toRP).getText().contains(toExpctd));

        Assert.assertEquals(driver.findElement(retrieveOrderPage.containerRP).getAttribute("value"), containerExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.chassisRP).getAttribute("value"), chassisExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.emptyContainerRP).getAttribute("value"), emptyContainerExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceOneRP).getAttribute("value"), referenceOneExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceTwoRP).getAttribute("value"), referenceTwoExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.referenceThreeRP).getAttribute("value"), referenceThreeExpctd.toUpperCase());
        Assert.assertEquals(driver.findElement(retrieveOrderPage.freightChargesTotalRP).getAttribute("value"), freightChargesTotalRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.freightChargesIndependentContractorPayRP).getAttribute("value"), freightChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesQuantityRP).getAttribute("value"), fuelChargesQuantityRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesRateRP).getAttribute("value"), fuelChargesRateRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.fuelChargesIndependentContractorPayRP).getAttribute("value"), fuelChargesIndependentContractorPayRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesQuantityRP).getAttribute("value"), dailyChassisChargesQuantityRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesRateRP).getAttribute("value"), dailyChassisChargesRateRPExpctd);
        Assert.assertEquals(driver.findElement(retrieveOrderPage.dailyChassisChargesIndependentContractorPayRP).getAttribute("value"), dailyChassisChargesIndependentContractorPayRPExpctd);
        Assert.assertTrue(driver.findElement(retrieveOrderPage.pickupAppointmentDate).getAttribute("value").equals(pickupAppointmentDateExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.pickupAppointmentTime).getAttribute("value").equals(pickupAppointmentTimeExpctd));
        Assert.assertTrue(driver.findElement(retrieveOrderPage.actualDate).getAttribute("value").equals(actualDateExpctd));
    }


    @And("navigate to Dispatch Grid screen")
    public void navigateToDispatchGridScreen() {
        getAndSwitchToWindowHandles();
        driver.close();
        getAndSwitchToWindowHandles();
        driver.findElement(mainMenuPage.dispatchGridLink).click();
    }

    @And("search with the order number created")
    public void searchWithTheOrderNumberCreated() throws InterruptedException {
        Thread.sleep(5000);
        getAndSwitchToWindowHandles();

        Thread.sleep(10000);
        //WebDriverWait wait = new WebDriverWait(driver, 200);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ContentArea\"]/div/table/tbody")));
        driver.findElement(dispatchGridPage.proNumberField).sendKeys(bookLoadNumber);
        driver.findElement(dispatchGridPage.quickSearchButton).click();
    }

    @And("assign tractor from the carrier lookup section")
    public void assignTractorFromTheCarrierLookupSection() {
        try {
            WebElement from = driver.findElement(dispatchGridPage.firstTractorToPickInTheList);
            WebElement to = driver.findElement(dispatchGridPage.assignToOrderNumberPulledUp);
            Actions action = new Actions(driver);
            action.dragAndDrop(from, to).build().perform();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            WebElement from = driver.findElement(dispatchGridPage.firstTractorToPickInTheList);
            WebElement to = driver.findElement(dispatchGridPage.assignToOrderNumberPulledUp);
            Actions action = new Actions(driver);
            action.dragAndDrop(from, to).build().perform();
        }
    }

    @Then("assign different status and validate in status history")
    public void assignDifferentStatusAndValidateInStatusHistory() {

        Assert.assertEquals("Assigned", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        driver.switchTo().frame(0);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String text = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String assigned = text.substring(text.indexOf("Assigned"));
        String substring = assigned.substring(0, Math.min(assigned.length(), 76));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateAndTime = dateTimeFormatter.format(localDateTime);//date and time generator code

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substring.contains(dateAndTime));
        Assert.assertTrue(substring.contains(usernameExpctd.toUpperCase()));

        //change new status to COMMITTED
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(0);
        driver.findElement(statusSelectionPage.statusForCommitted).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Committed", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(2);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForCommitted = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String committed = textForCommitted.substring(textForCommitted.indexOf("Committed"));
        String substringForCommitted = committed.substring(0, Math.min(assigned.length(), 77));

        String dateAndTimeForCommitted = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForCommitted.contains(dateAndTimeForCommitted));
        Assert.assertTrue(substringForCommitted.contains(usernameExpctd.toUpperCase()));

        //change new status to CUSTOMS HOLD
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(2);
        driver.findElement(statusSelectionPage.statusForCustomsHold).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Customs Hold", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(3);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForCustomsHold = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String customsHold = textForCustomsHold.substring(textForCustomsHold.indexOf("Customs Hold"));
        String substringForCustomsHold = customsHold.substring(0, Math.min(assigned.length(), 80));

        String dateAndTimeForCustomsHold = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForCustomsHold.contains(dateAndTimeForCustomsHold));
        Assert.assertTrue(substringForCustomsHold.contains(usernameExpctd.toUpperCase()));

        //change new status to CUSTOMS CLEARED
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(3);
        driver.findElement(statusSelectionPage.statusForCustomsCleared).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Customs Cleared", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(4);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForCustomsCleared = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String customsCleared = textForCustomsCleared.substring(textForCustomsCleared.indexOf("Customs Cleared"));
        String substringForCustomsCleared = customsCleared.substring(0, Math.min(assigned.length(), 83));

        String dateAndTimeForCustomsCleared = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForCustomsCleared.contains(dateAndTimeForCustomsCleared));
        Assert.assertTrue(substringForCustomsCleared.contains(usernameExpctd.toUpperCase()));

        //change new status to LOADING
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(4);
        driver.findElement(statusSelectionPage.statusForLoading).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Loading", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(5);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForLoading = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String droppedLoading = textForLoading.substring(textForLoading.indexOf("Loading"));
        String substringForLoading = droppedLoading.substring(0, Math.min(assigned.length(), 75));

        String dateAndTimeForLoading = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForLoading.contains(dateAndTimeForLoading));
        Assert.assertTrue(substringForLoading.contains(usernameExpctd.toUpperCase()));

        //change new status to DEPARTED PICKUP
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(5);
        driver.findElement(statusSelectionPage.statusForDepartedPickUp).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Departed Pick Up", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(6);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForDepartedPickup = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String departedPickup = textForDepartedPickup.substring(textForDepartedPickup.indexOf("Departed Pick Up"));
        String substringForDepartedPickup = departedPickup.substring(0, Math.min(assigned.length(), 84));

        String dateAndTimeForDepartedPickup = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForDepartedPickup.contains(dateAndTimeForDepartedPickup));
        Assert.assertTrue(substringForDepartedPickup.contains(usernameExpctd.toUpperCase()));


        //change new status to UNLOADING
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(6);
        driver.findElement(statusSelectionPage.statusForUnloading).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Unloading", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(7);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForUnloading = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String unloading = textForUnloading.substring(textForUnloading.indexOf("Unloading"));
        String substringForUnloading = unloading.substring(0, Math.min(assigned.length(), 77));

        String dateAndTimeForUnloading = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForUnloading.contains(dateAndTimeForUnloading));
        Assert.assertTrue(substringForUnloading.contains(usernameExpctd.toUpperCase()));


        //change new status to DEPARTED DELIVERY
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(7);
        driver.findElement(statusSelectionPage.statusForDepartedDelivery).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Departed Delivery", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(8);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForDepartedDelivery = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String departedDelivery = textForDepartedDelivery.substring(textForDepartedDelivery.indexOf("Departed Delivery"));
        String substringForDepartedDelivery = departedDelivery.substring(0, Math.min(assigned.length(), 85));

        String dateAndTimeForDepartedDelivery = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForDepartedDelivery.contains(dateAndTimeForDepartedDelivery));
        Assert.assertTrue(substringForDepartedDelivery.contains(usernameExpctd.toUpperCase()));


        //change new status to DISPATCHED
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(8);
        driver.findElement(statusSelectionPage.statusForDispatched).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Dispatched", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(9);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForDispatched = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String dispatched = textForDispatched.substring(textForDispatched.indexOf("Dispatched"));
        String substringForDispatched = dispatched.substring(0, Math.min(assigned.length(), 78));

        String dateAndTimeForDispatched = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForDispatched.contains(dateAndTimeForDispatched));
        Assert.assertTrue(substringForDispatched.contains(usernameExpctd.toUpperCase()));


        //change new status to COMPLETED
        getAndSwitchToWindowHandles();
        driver.switchTo().frame(9);
        driver.findElement(statusSelectionPage.statusForCompleted).click();
        driver.findElement(statusSelectionPage.changeStatusButton).click();

        getAndSwitchToWindowHandles();
        Assert.assertEquals("Completed", driver.findElement(dispatchGridPage.statusTabOnOrderLine).getText());
        driver.findElement(dispatchGridPage.statusTabOnOrderLine).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(10);
        driver.findElement(dispatchGridPage.statusHistoryButtonOnStatusSelectionPopup).click();

        getAndSwitchToWindowHandles();
        driver.switchTo().frame(1);

        String textForCompleted = driver.findElement(dispatchGridPage.entireTableInHistorySearch).getText();
        String completed = textForCompleted.substring(textForCompleted.indexOf("Completed"));
        String substringForCompleted = completed.substring(0, Math.min(assigned.length(), 77));

        String dateAndTimeForCompleted = dateTimeFormatter.format(localDateTime);

        getAndSwitchToWindowHandles();
        driver.findElement(dispatchGridPage.exitButtonOnHistorySearch).click();
        Assert.assertTrue(substringForCompleted.contains(dateAndTimeForCompleted));
        Assert.assertTrue(substringForCompleted.contains(usernameExpctd.toUpperCase()));

    }

    @Then("^click on bill customer pay driver and validate if the booking number got generated$")
    public void clickOnBillCustomerPayDriverAndValidateIfTheBookingNumberGotGenerated() throws InterruptedException {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));

    }

    @Then("^click on pay driver only and validate if the booking number got generated$")
    public void clickOnPayDriverOnlyAndValidateIfTheBookingNumberGotGenerated() throws InterruptedException {
        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));

    }

    @Then("^click on bill customer only and validate if the booking number got generated$")
    public void clickOnBillCustomerOnlyAndValidateIfTheBookingNumberGotGenerated() throws InterruptedException {
        driver.findElement(billingPage.billCustomerOnlyButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        log.info("Book Load Created - " + driver.findElement(billingPage.billCreated).getText());
        Assert.assertTrue("successfully created message not Returned!", driver.findElement(billingPage.billCreated).getText().contains("successfully created"));

    }

    @And("^pass the value \"([^\"]*)\" from the TractorOne field$")
    public void passTheValueFromTheTractorOneField(String tractorOne) throws Throwable {
        driver.findElement(billingPage.tractorOne).sendKeys(tractorOne);
    }

    @Then("^click on bill customer pay driver and validate if it shows actual date and time error$")
    public void clickOnBillCustomerPayDriverAndValidateIfItShowsActualDateAndTimeError() {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.instructionalMessageActualDateAndTime).getText().contains("following issues must be resolved before resubmitting the form:"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.actualDateErrorMessage).getText().contains("Enter Actual Date In MM/DD/YY Format"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.actualTimeErrorMessage).getText().contains("Enter Actual Time In HH:MM Format"));
    }

    @Then("^click on bill customer only and validate if it shows actual date and time error$")
    public void clickOnBillCustomerOnlyAndValidateIfItShowsActualDateAndTimeError() {
        driver.findElement(billingPage.billCustomerOnlyButton).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.instructionalMessageActualDateAndTime).getText().contains("following issues must be resolved before resubmitting the form:"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.actualDateErrorMessage).getText().contains("Enter Actual Date In MM/DD/YY Format"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.actualTimeErrorMessage).getText().contains("Enter Actual Time In HH:MM Format"));
    }

    @Then("^click on bill customer pay driver and validate if it shows the tractor one charge error$")
    public void clickOnBillCustomerPayDriverAndValidateIfItShowsTheTractorOneChargeError() throws InterruptedException {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.instructionalMessageActualDateAndTime).getText().contains("following issues must be resolved before resubmitting the form:"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.tractorOneChargeErrorMessage).getText().contains("Enter Tractor 1 Charge"));
    }

    @Then("^click on pay driver only and validate if it shows the tractor one charge error$")
    public void clickOnPayDriverOnlyAndValidateIfItShowsTheTractorOneChargeError() throws InterruptedException {
        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.instructionalMessageActualDateAndTime).getText().contains("following issues must be resolved before resubmitting the form:"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.tractorOneChargeErrorMessage).getText().contains("Enter Tractor 1 Charge"));
    }

    @Then("^click on bill customer pay driver and validate if it shows the tractor one id error$")
    public void clickOnBillCustomerPayDriverAndValidateIfItShowsTheTractorOneIdError() {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.instructionalMessageActualDateAndTime).getText().contains("following issues must be resolved before resubmitting the form:"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.tractorOneIdErrorMessage).getText().contains("Enter Tractor 1 Id"));
    }

    @Then("^click on pay driver only and validate if it shows the tractor one id error$")
    public void clickOnPayDriverOnlyAndValidateIfItShowsTheTractorOneIdError() {
        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.instructionalMessageActualDateAndTime).getText().contains("following issues must be resolved before resubmitting the form:"));
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.tractorOneIdErrorMessage).getText().contains("Enter Tractor 1 Id"));
    }

    @Then("^click on bill customer pay driver and validate if accept risk when accepting order alert$")
    public void clickOnBillCustomerPayDriverAndValidateIfAcceptRiskWhenAcceptingOrderAlert() throws InterruptedException {
        driver.findElement(billingPage.billCustomerPayDriverButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.alertForAcceptingRiskWhenSubmittingOrder).getText().contains("This customer is an Agent Risk, by booking this order, you will be accepting the credit risk. You, the agent, will be charged back if the customer does not pay this order within 60 day."));
    }

    @Then("^click on pay driver only and validate if accept risk when accepting order alert$")
    public void clickOnPayDriverOnlyAndValidateIfAcceptRiskWhenAcceptingOrderAlert() throws InterruptedException {
        driver.findElement(billingPage.payDriverOnlyButton).click();
        getAndSwitchToWindowHandles();
        driver.findElement(billingPage.assignTractorToIdAlertYes).click();
        Thread.sleep(10000);
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        Assert.assertTrue("Correct message not Returned!", driver.findElement(billingPage.alertForAcceptingRiskWhenSubmittingOrder).getText().contains("This customer is an Agent Risk, by booking this order, you will be accepting the credit risk. You, the agent, will be charged back if the customer does not pay this order within 60 day."));
    }


    @Given("^Connect to \"([^\"]*)\" and \"([^\"]*)\"$")
    public void connect_to_and(String environment, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        Statement stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
        String query = "SELECT TOP 1000 [ACCT_CLASS]\n" +
                "      ,[ACCT_CLASS_DESC]\n" +
                "      ,[ACCT_CREATED_BY]\n" +
                "      ,[ACCT_CREATED_DATE]\n" +
                "      ,[ACCT_UPDATED_BY]\n" +
                "      ,[ACCT_UPDATED_DATE]\n" +
                "      ,[ACCT_CLASS_Is_Deleted]\n" +
                "  FROM [EBHLaunch].[dbo].[ACCOUNTS_CLASS_CODES]";
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            System.out.print(res.getString(1));
            System.out.print("\t" + res.getString(2));
            System.out.print("\t" + res.getString(3));
            System.out.println("\t" + res.getString(4));
        }

    }


}