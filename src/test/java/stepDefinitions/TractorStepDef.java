package stepDefinitions;

// TractorVendorRelationship.feature
// FuelPurchaseMaintenance.feature


import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.Pages.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class TractorStepDef {

    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";
    String usernameExpected = "";

    EBHLoginPage ebhLoginPage = new EBHLoginPage(null);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    SettlementsPage settlementsPage = new SettlementsPage(driver);
    Logger log = Logger.getLogger("SettlingStepDef");
    TractorVendorRelationshipPage tractorVendorRelationshipPage = new TractorVendorRelationshipPage(driver);
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    FuelPurchaseMaintenancePage fuelPurchaseMaintenancePage = new FuelPurchaseMaintenancePage(driver);
    private static Statement stmt;


    //............................................/ Background /................................................//

    @After("@FailureScreenShot6")
    public void takeScreenshotOnFailure6(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }


    @Given("^Run Test for Environment \"([^\"]*)\" on Browser \"([^\"]*)\" for EBH Tractors and Enter the url$")
    public void run_Test_for_Environment_on_Browser_for_EBH_Tractors_and_Enter_the_url(String environment, String browser) throws MalformedURLException {
        URL = browserDriverInitialization.getDataFromPropertiesFileForEBH(environment, browser);
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
        driver = new RemoteWebDriver(new URL("http://192.168.0.11:4444"), cap);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(URL);
    }

    @And("^Login to the EBH with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Tractors$")
    public void Login_to_the_EBH_with_username_and_password_for_EBH_Tractors(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhLoginPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.password));
        driver.findElement(ebhLoginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhLoginPage.signinButton));
        driver.findElement(ebhLoginPage.signinButton).click();
    }

    @Given("^Navigate to the Corporate Page on Main Menu then to the Settlements page for EBH Tractors$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_then_to_the_Settlements_page_for_EBH_Tractors() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
        driver.findElement(corporatePage.settlements).click();
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Then("^Close all open Browsers on EBH for Tractors$")
    public void Close_all_open_Browsers_on_EBH_for_Tractors() {
        driver.close();
        driver.quit();
    }


    //............................................/ 64 @FPMValidationTractorID /................................................//
    @Given("^Navigate to the Corporate Page on Main Menu then to the Fuel and Mileage page for EBH Tractors$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_then_to_the_Fuel_and_Mileage_page_for_EBH_Tractors() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(corporatePage.fuelAndMileage).click();
    }

    @Given("^Navigate to Fuel Purchase Maintenance Page$")
    public void navigate_to_Fuel_Purchase_Maintenance_Page() {
        driver.findElement(corporatePage.fuelPurchaseMaintenance).click();
    }

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_Latest_Date_State_Company_IFTA(String tractorID, String earliestDate, String latestDate, String
            state, String company) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify the Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for TractorID$")
    public void verify_the_Total_Records_Returned_with_Database_Record_for_TractorID(String environment, String tableName, String tractorID, String earliestDateDB, String latestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] >= '" + earliestDateDB + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }

                    if (booleanValue1) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 65 @FPMValidationTractorIDEarliestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_Latest_Date_State_Company_IFTA(String tractorID, String earliestDate, String
            state, String company) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("State = " + state);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify the Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" Earliest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for TractorID$")
    public void verify_the_Total_Records_Returned_with_Database_Record_Earliest_Date_for_TractorID(String environment, String tableName, String tractorID, String earliestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] >= '" + earliestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }

                    if (booleanValue1) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 66 @FPMValidationTractorIDLatestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\"$")
    public void enter_Tractor_Id_Latest_Date_State_Company(String tractorID, String latestDate, String
            state, String company) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify the Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" Latest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for TractorID$")
    public void verify_the_Total_Records_Returned_with_Database_Record_Latest_Date_for_TractorID(String environment, String tableName, String tractorID, String latestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " \n" +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }

                    if (booleanValue1) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 67 @FPMValidationIFTA /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\" IFTA \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_Latest_Date_State_Company_IFTA(String tractorID, String earliestDate, String latestDate, String
            state, String company, String iFTA) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).sendKeys(iFTA);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("IFTA = " + iFTA);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }

    @Given("^Verify The Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for IFTA$")
    public void verify_The_Total_Records_Returned_with_Database_Record_for_IFTA(String environment, String tableName, String earliestDateDB, String latestDateDB, String state, String company, String ifta, String tractorID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_UNITNO] = '" + tractorID + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }

                    if (booleanValue1) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//............................................/ 68 @FPMValidationIFTAEarliestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Earliest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\" IFTA \"([^\"]*)\"$")
    public void enter_Tractor_Id_Earliest_Date_State_Company_IFTA(String tractorID, String earliestDate, String
            state, String company, String iFTA) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).sendKeys(earliestDate);
        driver.findElement(fuelPurchaseMaintenancePage.EarliestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).sendKeys(iFTA);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Earliest Date = " + earliestDate);
        System.out.println("State = " + state);
        System.out.println("IFTA = " + iFTA);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify The Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" Earliest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for IFTA$")
    public void verify_The_Total_Records_Returned_with_Database_Record_Earliest_Date_for_IFTA(String environment, String tableName, String earliestDateDB, String state, String company, String ifta, String tractorID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_UNITNO] = '" + tractorID + "' ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }

                    if (booleanValue1) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//............................................/ 69 @FPMValidationIFTALatestDate /................................................//

    @Given("^Enter Tractor Id \"([^\"]*)\" Latest Date \"([^\"]*)\" State \"([^\"]*)\" Company \"([^\"]*)\" IFTA \"([^\"]*)\"$")
    public void enter_Tractor_Id_Latest_Date_State_Company_IFTA(String tractorID, String latestDate, String
            state, String company, String iFTA) throws InterruptedException {
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.Header).getText());
        System.out.println("========================================");
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).sendKeys(latestDate);
        driver.findElement(fuelPurchaseMaintenancePage.LatestDate).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.State).sendKeys(state);
        driver.findElement(fuelPurchaseMaintenancePage.State).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Company).sendKeys(company);
        driver.findElement(fuelPurchaseMaintenancePage.Company).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).sendKeys(iFTA);
        driver.findElement(fuelPurchaseMaintenancePage.IFTA).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
        System.out.println("Total Records Returned when ");
        System.out.println("Tractor ID = " + tractorID);
        System.out.println("Latest Date = " + latestDate);
        System.out.println("State = " + state);
        System.out.println("IFTA = " + iFTA);
        System.out.println("     = " + driver.findElement(fuelPurchaseMaintenancePage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(5000);
        System.out.println("========================================");
    }


    @Given("^Verify The Total Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" Latest Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for IFTA$")
    public void verify_The_Total_Records_Returned_with_Database_Record_Latest_Date_for_IFTA(String environment, String tableName, String latestDateDB, String state, String company, String ifta, String tractorID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_UNITNO] = '" + tractorID + "' ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }

                    if (booleanValue1) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("  STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }

                    if (booleanValue2) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 70 @FPMScenarioNew /................................................//

    @Given("^Click on Search Button$")
    public void click_on_Search_Button() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        Thread.sleep(20000);
    }

    @Given("^Select the New Button, Enter required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void select_the_New_Button_Enter_required_fields(String company, String tractorID, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"btnNewSettlement12\"]")).click();
        Thread.sleep(3000);
        System.out.println("========================================");
        System.out.println(driver.findElement(fuelPurchaseMaintenancePage.PopupHeader).getText());
        driver.findElement(fuelPurchaseMaintenancePage.CompanyN).click();
        driver.findElement(fuelPurchaseMaintenancePage.CompanyN).sendKeys(company);
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.DateN).sendKeys(date);
        driver.findElement(fuelPurchaseMaintenancePage.DateN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).sendKeys(location);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.StateN).sendKeys(state);
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).sendKeys(gallons);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).sendKeys(amount);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).click();
        Thread.sleep(2000);
    }

    @Given("^Click Cancel on Add Fuel Purchase$")
    public void click_Cancel_on_Add_Fuel_Purchase() throws InterruptedException {

       // driver.findElement(fuelPurchaseMaintenancePage.CancelN).click();
     //   WebElement element = driver.findElement(By.xpath("//*[@id=\"btnCancel\"]")); //*[@id="btnCancel"]
      //  WebElement element = driver.findElement(By.linkText("Cancel"));
     //   JavascriptExecutor executor = (JavascriptExecutor) driver;
      //  executor.executeScript("arguments[0].click();", element);

        Thread.sleep(2000);
    }

    @Given("^Re-Enter required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void re_Enter_required_fields(String company, String tractorID, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        //  Thread.sleep(2000);
        //  driver.findElement(fuelPurchaseMaintenancePage.CompanyN).click();
        //   driver.findElement(fuelPurchaseMaintenancePage.CompanyN).sendKeys(company);
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).sendKeys(tractorID);
        driver.findElement(fuelPurchaseMaintenancePage.TractorIDN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.DateN).sendKeys(date);
        driver.findElement(fuelPurchaseMaintenancePage.DateN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).sendKeys(location);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).click();
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.StateN).click();
        driver.findElement(fuelPurchaseMaintenancePage.StateN).sendKeys(state);
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).sendKeys(gallons);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).sendKeys(amount);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).click();
        Thread.sleep(2000);
    }

    @Given("^Click Save on Add Fuel Purchase$")
    public void click_Save_on_Add_Fuel_Purchase() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.SaveN).click();
        Thread.sleep(2000);
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getText());
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();

        driver.findElement(By.id("crossIconPopup")).click();
        System.out.println("========================================");
    }


    @Given("^Select \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" from dropdown$")
    public void select_from_dropdown(String company, String tractor, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractor);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(2000);
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(company)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(tractor)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img3\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(date)) {
                webElement.click();
                break;
            }
        }


        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(location)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list5) {
            if (webElement.getText().contains(state)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img6\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list6) {
            if (webElement.getText().contains(gallons)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list7 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list7) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }

        Thread.sleep(3000);
    }

    @Given("Get the Records Returned on save")
    public void get_the_records_returned_on_save() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println("Total Records Returned : " + driver.findElement(fuelPurchaseMaintenancePage.DataTableInfo).getText());
        log.info(driver.findElement(fuelPurchaseMaintenancePage.DataTable).getText());
        Thread.sleep(5000);

    }

    @Given("^Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String tractorID, String company, String date, String location, String state, String gallons, String amount) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] = '" + date + "'" +
                "       AND [FUEL_PUR_LOCATION] = '" + location + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_GALLONS] = '" + gallons + "'" +
                "       AND [FUEL_PUR_TOTAL] = '" + amount + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(6);
            dbFPM2.add(c);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println(" TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("LOCATION: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println("STATE: " + c);
                        }
                        booleanValue2 = true;
                        break;
                    }
                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 71 @FPMScenarioEdit /................................................//

    @Given("^Select Tractor ID \"([^\"]*)\"$")
    public void select_Tractor_ID(String tractor) throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).sendKeys(tractor);
        driver.findElement(fuelPurchaseMaintenancePage.TractorID).click();
        Thread.sleep(3000);
    }

    @Given("^Click on Search Button for EDIT$")
    public void click_on_Search_Button_for_EDIT() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(8000);
    }

    @Given("^Select \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" from dropdown for EDIT$")
    public void select_from_dropdown_for_EDIT(String company, String tractor, String date, String location, String state, String gallons, String amount) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(company)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(tractor)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img3\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(date)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(location)) {
                Thread.sleep(2000);
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list5) {
            if (webElement.getText().contains(state)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img6\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list6) {
            if (webElement.getText().contains(gallons)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list7 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list7) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }

        Thread.sleep(3000);
    }


    @Given("^Click on Edit, and edit the fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void click_on_Edit_and_edit_the_fields(String location1, String gallons1, String amount1) throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.Edit).click();
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div/div[1]/div/h4")).getText());
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).clear();
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).sendKeys(location1);
        driver.findElement(fuelPurchaseMaintenancePage.LocationN).click();
        Thread.sleep(3000);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).clear();
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).sendKeys(gallons1);
        driver.findElement(fuelPurchaseMaintenancePage.GallonsN).click();
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).clear();
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).sendKeys(amount1);
        driver.findElement(fuelPurchaseMaintenancePage.AmountN).click();
    }

    @Given("^Click Save on Edit Fuel Purchase$")
    public void click_Save_on_Edit_Fuel_Purchase() throws InterruptedException {
        driver.findElement(fuelPurchaseMaintenancePage.SaveN).click();
        Thread.sleep(15000);
        System.out.println("========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[1]/strong/i")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getText());
        driver.findElement(By.id("btnOK")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list4) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img6\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list6) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list7 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list7) {
            if (webElement.getText().contains("Clear Filter")) {
                webElement.click();
                break;
            }
        }
        driver.findElement(fuelPurchaseMaintenancePage.Search).click();
        Thread.sleep(3000);
    }


    @Given("^Validate the Newly Edited Record with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Newly_Edited_Record_with_Database_Record_and(String environment, String tableName, String tractorID, String company, String date, String location1, String state, String gallons1, String amount1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_TRANS_DATETIME] = '" + date + "'" +
                "       AND [FUEL_PUR_LOCATION] = '" + location1 + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_GALLONS] = '" + gallons1 + "'" +
                "       AND [FUEL_PUR_TOTAL] = '" + amount1 + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();
        List<String> dbFPM1 = new ArrayList<>();
        List<String> dbFPM2 = new ArrayList<>();
        List<String> dbFPM3 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);
            String b = rs.getString(5);
            dbFPM1.add(b);
            String c = rs.getString(7);
            dbFPM2.add(c);
            String d = rs.getString(8);
            dbFPM3.add(d);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            boolean booleanValue1 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(b)) {
                    for (String dbfpm1 : dbFPM1) {
                        if (dbfpm1.contains(b)) {
                            System.out.println("  LOCATION1: " + b);
                        }
                        booleanValue1 = true;
                        break;
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            boolean booleanValue2 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(c)) {
                    for (String dbfpm2 : dbFPM2) {
                        if (dbfpm2.contains(c)) {
                            System.out.println(" GALLONS1: " + c);
                            booleanValue2 = true;
                        }
                        break;
                    }

                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            boolean booleanValue3 = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(d)) {
                    for (String dbfpm3 : dbFPM3) {
                        if (dbfpm3.contains(d)) {
                            System.out.println("  AMOUNT1: " + d);
                        }
                        booleanValue3 = true;
                        break;
                    }
                }
                if (booleanValue3) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 72 @FPMScenarioReportTractorID /................................................//

    @Given("^Click on Report Button, and Click on ALL RECORDS$")
    public void click_on_Report_Button_and_Click_on_ALL_RECORDS() {
        driver.findElement(fuelPurchaseMaintenancePage.Report).click();
        driver.findElement(fuelPurchaseMaintenancePage.AllRecords).click();
    }

    @And("^Get ALL RECORDS Excel Report from Downloads for FPM$")
    public void get_ALL_RECORDS_Excel_Report_from_Downloads_for_FPM() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(5000);
        String fileNameFPMAR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("ALL RECORDS Excel Report File Name :-" + fileNameFPMAR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameFPMAR + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();

        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("^Validate ALL RECORDS Excel Report with Database Record \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_ALL_RECORDS_Excel_Report_with_Database_Record(String environment, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    SELECT * FROM " + tableName + " " +
                "    WHERE [FUEL_PUR_ISDELETED] = 0 ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10) +
                    "\t" + res.getString(11) +
                    "\t" + res.getString(12) +
                    "\t" + res.getString(13) +
                    "\t" + res.getString(14) +
                    "\t" + res.getString(15));

            String a = res.getString(3);
            dbFPM.add(a);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                    if (booleanValue) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Excel Report Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Click on Report Button and Click on SEARCH RESULTS$")
    public void click_on_Report_Button_and_Click_on_Search_Results() {
        driver.findElement(fuelPurchaseMaintenancePage.Report).click();
        driver.findElement(fuelPurchaseMaintenancePage.SearchResults).click();
    }


    @And("^Get SEARCH RESULTS Excel Report from Downloads for FPM Tractor ID$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_FPM_Tractor_ID() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(8000);
        String fileNameFPMSR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("SEARCH RESULTS Excel Report File Name :-" + fileNameFPMSR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameFPMSR + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();

        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("^Validate SEARCH RESULTS Excel Report with Database Record \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_SEARCH_RESULTS_Excel_Report_with_Database_Record(String environment, String tableName, String tractorID, String earliestDateDB, String latestDateDB, String state, String company) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [FUEL_PUR_UNITNO] = '" + tractorID + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND FUEL_PUR_ISDELETED = 0";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10) +
                    "\t" + res.getString(11) +
                    "\t" + res.getString(12) +
                    "\t" + res.getString(13) +
                    "\t" + res.getString(14) +
                    "\t" + res.getString(15));

            String a = res.getString(3);
            dbFPM.add(a);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                    if (booleanValue) {
                        assertTrue("Assertion Passed!!", true);
                    } else {
                        fail("Assertion Failed!!");
                    }
                }
            }
        }
        System.out.println("Excel Report Closed ......");
        System.out.println("=========================================");
    }


//............................................/ 73 @FPMScenarioReportIFTA /................................................//

    @And("^Get SEARCH RESULTS Excel Report from Downloads for FPM IFTA$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_FPM_IFTA() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(8000);
        String fileNameFPMSR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("SEARCH RESULTS Excel Report File Name :-" + fileNameFPMSR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameFPMSR + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();

        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }

    @Given("^Validate SEARCH RESULTS Excel Report with Database Record for IFTA \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_SEARCH_RESULTS_Excel_Report_with_Database_Record_for_IFTA(String environment, String tableName, String earliestDateDB, String latestDateDB, String state, String company, String ifta, String tractorID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT DISTINCT [FUEL_PUR_ID]\n" +
                "      ,[FUEL_PUR_COMP_CODE]\n" +
                "      ,[FUEL_PUR_UNITNO]\n" +
                "      ,[FUEL_PUR_TRANS_DATETIME]\n" +
                "      ,[FUEL_PUR_LOCATION]\n" +
                "      ,[FUEL_PUR_STATE]\n" +
                "      ,[FUEL_PUR_GALLONS]\n" +
                "      ,[FUEL_PUR_TOTAL]\n" +
                "      ,[RES_OWNER_IFTA]\n" +
                "      ,[FUEL_PUR_DRIVER_NAME]\n" +
                "      ,[FUEL_PUR_EFS_ID]\n" +
                "      ,[FUEL_PUR_CREATED_BY]\n" +
                "      ,[FUEL_PUR_CREATED_DT]\n" +
                "      ,[FUEL_PUR_UPDATED_BY]\n" +
                "      ,[FUEL_PUR_UPDATED_DT]\n" +
                "      ,[FUEL_PUR_ISDELETED]\n" +
                "       FROM " + tableName + " WITH(NOLOCK)\n" +
                "       INNER JOIN [EBH].[dbo].[RESOURCES] as RES ON RES.RES_RESOURCE_CODE = FUEL_PUR.FUEL_PUR_UNITNO\n" +
                "       AND RES.RES_COMP_CODE = FUEL_PUR.FUEL_PUR_COMP_CODE\n" +
                "       WHERE RES_STATUS = 'ACTIVE'\n" +
                "       AND FUEL_PUR_ISDELETED = 0\n" +
                "       AND RES_OWNER_IFTA = '" + ifta + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME >= '" + earliestDateDB + "'" +
                "       AND FUEL_PUR_TRANS_DATETIME <= '" + latestDateDB + "'" +
                "       AND [FUEL_PUR_STATE] = '" + state + "'" +
                "       AND [FUEL_PUR_COMP_CODE] = '" + company + "'" +
                "       AND [FUEL_PUR_UNITNO] = '" + tractorID + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> fPM = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbFPM = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10) +
                    "\t" + rs.getString(11) +
                    "\t" + rs.getString(12) +
                    "\t" + rs.getString(13) +
                    "\t" + rs.getString(14) +
                    "\t" + rs.getString(15));

            String a = rs.getString(3);
            dbFPM.add(a);

            boolean booleanValue = false;
            for (WebElement fpm : fPM) {
                if (fpm.getText().contains(a)) {
                    for (String dbfpm : dbFPM) {
                        if (dbfpm.contains(a)) {
                            System.out.println("  TRACTOR ID: " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #74 @TractorVendorRelationship-UnitNumber/Report /................................................//
    @And("^Navigate to Tractor Vendor Relationship$")
    public void Navigate_to_Tractor_Vendor_Relationship() throws InterruptedException {
        driver.findElement(settlementsPage.TractorVendorRelationship).click();
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorVendorRelationshipPage.TractorVendorCodeRelationshipMaintenanceHeader));
        driver.findElement(tractorVendorRelationshipPage.TractorVendorCodeRelationshipMaintenanceHeader).isDisplayed();
        System.out.println("========================================");
        System.out.println(driver.findElement(tractorVendorRelationshipPage.TractorVendorCodeRelationshipMaintenanceHeader).getText());
    }

    @Given("^Enter Unit Number \"([^\"]*)\" and click on Search$")
    public void enter_unit_number_and_click_on_search(String unitNumber) throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).sendKeys(unitNumber);
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).click();
        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println("Records Returned on UNIT NUMBER =  " + unitNumber + ": " + driver.findElement(By.xpath("//*[@id=\"dataTable_info\"]")).getText());
        System.out.println(driver.findElement(By.xpath("  //*[@id=\"dataTable\"]")).getText());
        System.out.println();
    }

    @Given("Validate the Total Records Returned with database Records {string} {string} Unit Number {string}")
    public void validate_the_total_records_returned_with_database_records_unit_number(String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [TRAC_VEND_ID]\n" +
                "      ,[TRAC_VEND_UNITNO]\n" +
                "      ,[TRAC_VEND_VENDOR_CODE]\n" +
                "      ,[TRAC_VEND_LOC]\n" +
                "      ,[TRAC_VEND_COMP_CODE]\n" +
                "      ,[TRAC_VEND_ACCTG_STATUS]\n" +
                "      ,[TRAC_VEND_CREATED_BY]\n" +
                "      ,[TRAC_VEND_CREATED_DATE]\n" +
                "      ,[TRAC_VEND_UPDATED_BY]\n" +
                "      ,[TRAC_VEND_UPDATED_DATE]\n" +
                "       FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<WebElement> tractorVendorRelationship2 = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<WebElement> tractorVendorRelationship1 = driver.findElements(By.xpath("  //*[@id=\"tdstatus\"]"));
        //*[@id="tbltractorvendor"]
        List<String> dbtractorVendor = new ArrayList<>();
        List<String> dbtractorVendor1 = new ArrayList<>();
        List<String> dbtractorVendor2 = new ArrayList<>();
        List<String> dbtractorVendor3 = new ArrayList<>();
        List<String> dbtractorVendor4 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10));
            System.out.println();

            String a = res.getString(5);
            dbtractorVendor.add(a);
            String b = res.getString(2);
            dbtractorVendor1.add(b);
            String c = res.getString(6);
            dbtractorVendor2.add(c);
            String d = res.getString(3);
            dbtractorVendor3.add(d);
            String e = res.getString(4);
            dbtractorVendor4.add(e);

            System.out.println("TRAC_VEND_COMP_CODE : " + a);

          /*  boolean booleanValue = false;
            for (WebElement tVR : tractorVendorRelationship2) {
                if (tVR.getText().contains(b)) {
                    for (String dbTV1 : dbtractorVendor1) {
                        if (dbTV1.contains(b)) {    */
            System.out.println("TRAC_VEND_UNITNO : " + b);
                  /*          booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }  */
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + c);
            System.out.println("TRAC_VEND_VENDOR_CODE : " + d);
            System.out.println("TRAC_VEND_LOC : " + e);

            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


    @Given("Validate the Total Records Returned with database Records DriverThreeSixty {string} {string} Unit Number {string}")
    public void validate_the_total_records_returned_with_database_records_DriverThreeSixty_unit_number(String environment1, String tableName1, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "" +
                "       WHERE UnitNo = '" + unitNo + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tdownerId\"]"));
        List<String> dbEquipment = new ArrayList<>();
        List<String> dbEquipment1 = new ArrayList<>();
        List<String> dbEquipment2 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4));

            String a = res.getString(3);
            dbEquipment.add(a);
            String b = res.getString(2);
            dbEquipment1.add(b);
            String c = res.getString(4);
            dbEquipment2.add(c);
            System.out.println("Unit No : " + a);


            boolean booleanValue = false;
            for (WebElement atv : tractorVendorRelationship) {
                if (atv.getText().contains(b)) {
                    for (String dbe1 : dbEquipment1) {
                        if (dbe1.contains(b)) {
                            System.out.println("Owner ID : " + b);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Status : " + c);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


    @Given("Click on Report Button and on SEARCH RESULTS")
    public void click_on_report_button_and_on_search_results() {
        driver.findElement(tractorVendorRelationshipPage.Report).click();
        driver.findElement(tractorVendorRelationshipPage.SearchResults).click();
    }


    @And("^Get SEARCH RESULTS Excel Report from Downloads for EBH Tractors$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_EBH_Tractors() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(15000);
        String fileNameAllAdjustments = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        Thread.sleep(10000);
        System.out.println(" SEARCH RESULTS Excel Report File Name :-" + fileNameAllAdjustments);
        driver.close();


        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameAllAdjustments + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("Validate the Excel Search result Report with database Records {string} {string} Unit Number {string}")
    public void validate_the_excel_search_result_report_with_database_records_unit_number(String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbTV = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String a = rs.getString(2);
            dbTV.add(a);

            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(a)) {
                    for (String dbtv : dbTV) {
                        if (dbtv.contains(a)) {
                            System.out.println("UNIT NO: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    //............................................/ #75 @TractorVendorRelationship-VendorCode/Report /................................................//

    @Given("Enter Vendor Code {string} and click on Search")
    public void enter_vendor_code_and_click_on_search(String vendorCode) throws InterruptedException {

        Thread.sleep(5000);
        WebElement vendorCodeSearchBox = driver.findElement(By.xpath("//*[@id=\"SearchVendorCodePartial_I\"]"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + vendorCode + "';", vendorCodeSearchBox);
        Thread.sleep(5000);

        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");

        System.out.println("Records Returned on VENDOR CODE =  " + vendorCode + ": " + driver.findElement(By.xpath("//*[@id=\"dataTable_info\"]")).getText());
        System.out.println(driver.findElement(By.xpath("  //*[@id=\"dataTable\"]")).getText());
        System.out.println();
    }

    @Given("Validate the Total Records Returned with database Records {string} {string} Vendor Code {string}")
    public void validate_the_total_records_returned_with_database_records_vendor_code(String environment, String tableName, String vendorCode) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [TRAC_VEND_ID]\n" +
                "      ,[TRAC_VEND_UNITNO]\n" +
                "      ,[TRAC_VEND_VENDOR_CODE]\n" +
                "      ,[TRAC_VEND_LOC]\n" +
                "      ,[TRAC_VEND_COMP_CODE]\n" +
                "      ,[TRAC_VEND_ACCTG_STATUS]\n" +
                "      ,[TRAC_VEND_CREATED_BY]\n" +
                "      ,[TRAC_VEND_CREATED_DATE]\n" +
                "      ,[TRAC_VEND_UPDATED_BY]\n" +
                "      ,[TRAC_VEND_UPDATED_DATE]\n" +
                "       FROM " + tableName + "" +
                "       WHERE TRAC_VEND_VENDOR_CODE = '" + vendorCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));

        List<String> dbtractorVendor = new ArrayList<>();
        List<String> dbtractorVendor1 = new ArrayList<>();
        List<String> dbtractorVendor2 = new ArrayList<>();
        List<String> dbtractorVendor3 = new ArrayList<>();
        List<String> dbtractorVendor4 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10));
            System.out.println();

            String a = res.getString(5);
            dbtractorVendor.add(a);
            String b = res.getString(2);
            dbtractorVendor1.add(b);
            String c = res.getString(6);
            dbtractorVendor2.add(c);
            String d = res.getString(3);
            dbtractorVendor3.add(d);
            String e = res.getString(4);
            dbtractorVendor4.add(e);


            boolean booleanValue = false;
            for (WebElement tVR : tractorVendorRelationship) {
                if (tVR.getText().contains(d)) {
                    for (String dbTV3 : dbtractorVendor3) {
                        if (dbTV3.contains(d)) {
                            System.out.println("TRAC_VEND_VENDOR_CODE : " + d);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

            System.out.println("TRAC_VEND_COMP_CODE : " + a);
            System.out.println("TRAC_VEND_UNITNO : " + b);
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + c);
            System.out.println("TRAC_VEND_LOC : " + e);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }

    @And("^Get SEARCH RESULTS Excel Report from Downloads for Tractor Vendor Relationship VendorCode, Report$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_Tractor_Vendor_Relationship_VendorCode_Report() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(10000);
        String fileNameTVRvcR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println(" SEARCH RESULTS Excel Report File Name :-" + fileNameTVRvcR);
        driver.close();


        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameTVRvcR + "");
        XSSFWorkbook wbTVRvcR = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbTVRvcR.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wbTVRvcR.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbTVRvcR.close();
        getAndSwitchToWindowHandles();
    }


    @Given("Validate the Excel Search result Report with database Records {string} {string} Vendor Code {string}")
    public void validate_the_excel_search_result_report_with_database_records_vendor_code(String environment, String tableName, String vendorCode) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [TRAC_VEND_ID]\n" +
                "      ,[TRAC_VEND_UNITNO]\n" +
                "      ,[TRAC_VEND_VENDOR_CODE]\n" +
                "      ,[TRAC_VEND_LOC]\n" +
                "      ,[TRAC_VEND_COMP_CODE]\n" +
                "      ,[TRAC_VEND_ACCTG_STATUS]\n" +
                "      ,[TRAC_VEND_CREATED_BY]\n" +
                "      ,[TRAC_VEND_CREATED_DATE]\n" +
                "      ,[TRAC_VEND_UPDATED_BY]\n" +
                "      ,[TRAC_VEND_UPDATED_DATE]\n" +
                "       FROM " + tableName + "" +
                "       WHERE TRAC_VEND_VENDOR_CODE = '" + vendorCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));

        List<String> dbtractorVendor = new ArrayList<>();
        List<String> dbtractorVendor1 = new ArrayList<>();
        List<String> dbtractorVendor2 = new ArrayList<>();
        List<String> dbtractorVendor3 = new ArrayList<>();
        List<String> dbtractorVendor4 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10));
            System.out.println();

            String a = res.getString(5);
            dbtractorVendor.add(a);
            String b = res.getString(2);
            dbtractorVendor1.add(b);
            String c = res.getString(6);
            dbtractorVendor2.add(c);
            String d = res.getString(3);
            dbtractorVendor3.add(d);
            String e = res.getString(4);
            dbtractorVendor4.add(e);


            boolean booleanValue = false;
            for (WebElement tVR : tractorVendorRelationship) {
                if (tVR.getText().contains(d)) {
                    for (String dbTV3 : dbtractorVendor3) {
                        if (dbTV3.contains(d)) {
                            System.out.println("TRAC_VEND_VENDOR_CODE : " + d);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("TRAC_VEND_COMP_CODE : " + a);
            System.out.println("TRAC_VEND_UNITNO : " + b);
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + c);
            System.out.println("TRAC_VEND_LOC : " + e);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


//............................................/ #76 @TractorVendorRelationship-OwnerId/Report /................................................//

    @Given("Enter Owner Id {string} and click on Search")
    public void enter_owner_id_and_click_on_search(String ownerID) throws InterruptedException {
        //   driver.findElement(tractorVendorRelationshipPage.OwnerId).click();
        driver.findElement(tractorVendorRelationshipPage.OwnerId).sendKeys(ownerID);
        Thread.sleep(3000);
        //   driver.findElement(tractorVendorRelationshipPage.OwnerId).click();
        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println("Records Returned on OWNER ID =  " + ownerID + ": " + driver.findElement(By.xpath("//*[@id=\"dataTable_info\"]")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        System.out.println();
    }

    @Given("Validate the Total Records Returned with database Records DriverThreeSixty {string} {string} Owner Id {string}")
    public void validate_the_total_records_returned_with_database_records_driver_three_sixty_owner_id(String environment1, String tableName1, String ownerId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "" +
                "       WHERE OwnerID = '" + ownerId + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbEquipment = new ArrayList<>();
        List<String> dbEquipment1 = new ArrayList<>();
        List<String> dbEquipment2 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4));

            String a = res.getString(3);
            dbEquipment.add(a);
            String b = res.getString(2);
            dbEquipment1.add(b);
            String c = res.getString(4);
            dbEquipment2.add(c);

            boolean booleanValue = false;
            for (WebElement atv : tractorVendorRelationship) {
                if (atv.getText().contains(b)) {
                    for (String dbe1 : dbEquipment1) {
                        if (dbe1.contains(b)) {
                            System.out.println("Owner ID : " + b);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Unit No : " + a);
            System.out.println("Status : " + c);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


    @And("^Get SEARCH RESULTS Excel Report from Downloads for Tractor Vendor Relationship Owner Id, Report$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_Tractor_Vendor_Relationship_Owner_Id_Report() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(40000);
        String fileNameTVRoiR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println(" SEARCH RESULTS Excel Report File Name :-" + fileNameTVRoiR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameTVRoiR + "");
        XSSFWorkbook wbTVRvcR = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbTVRvcR.getSheetAt(0);
        inputStream.close();

        FormulaEvaluator formulaEvaluator = wbTVRvcR.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbTVRvcR.close();
        getAndSwitchToWindowHandles();
    }

    @Given("Validate the Excel Search result Report with database Records {string} {string} Owner Id {string}")
    public void validate_the_excel_search_result_report_with_database_records_owner_id(String environment1, String tableName1, String ownerId) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "" +
                "       WHERE OwnerID = '" + ownerId + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbEquipment = new ArrayList<>();
        List<String> dbEquipment1 = new ArrayList<>();
        List<String> dbEquipment2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));

            String a = rs.getString(3);
            dbEquipment.add(a);
            String b = rs.getString(2);
            dbEquipment1.add(b);
            String c = rs.getString(4);
            dbEquipment2.add(c);

            boolean booleanValue = false;
            for (WebElement atv : tvr) {
                if (atv.getText().contains(b)) {
                    for (String dbe1 : dbEquipment1) {
                        if (dbe1.contains(b)) {
                            System.out.println("Owner ID : " + b);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Unit No : " + a);
            System.out.println("Status : " + c);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


    //............................................/ #77 @TractorVendorRelationship-Location/Report /................................................//


    @Given("Enter Location {string} and click on Search")
    public void enter_location_and_click_on_search(String location) throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.LocationSearchBox).sendKeys(location);
        driver.findElement(tractorVendorRelationshipPage.LocationSearchBox).click();
        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println("Records Returned on LOCATION =  " + location + ": " + driver.findElement(By.xpath("//*[@id=\"dataTable_info\"]")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        System.out.println();
    }

    @Given("Validate the Total Records Returned with database Records {string} {string} Location {string}")
    public void validate_the_total_records_returned_with_database_records_location(String environment, String tableName, String location) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [TRAC_VEND_ID]\n" +
                "      ,[TRAC_VEND_UNITNO]\n" +
                "      ,[TRAC_VEND_VENDOR_CODE]\n" +
                "      ,[TRAC_VEND_LOC]\n" +
                "      ,[TRAC_VEND_COMP_CODE]\n" +
                "      ,[TRAC_VEND_ACCTG_STATUS]\n" +
                "      ,[TRAC_VEND_CREATED_BY]\n" +
                "      ,[TRAC_VEND_CREATED_DATE]\n" +
                "      ,[TRAC_VEND_UPDATED_BY]\n" +
                "      ,[TRAC_VEND_UPDATED_DATE]\n" +
                "       FROM " + tableName + "" +
                "       WHERE TRAC_VEND_LOC = '" + location + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<WebElement> tractorVendorRelationship2 = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<WebElement> tractorVendorRelationship1 = driver.findElements(By.xpath("  //*[@id=\"tdstatus\"]"));

        List<String> dbtractorVendor = new ArrayList<>();
        List<String> dbtractorVendor1 = new ArrayList<>();
        List<String> dbtractorVendor2 = new ArrayList<>();
        List<String> dbtractorVendor3 = new ArrayList<>();
        List<String> dbtractorVendor4 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10));
            System.out.println();

            String a = res.getString(5);
            dbtractorVendor.add(a);
            String b = res.getString(2);
            dbtractorVendor1.add(b);
            String c = res.getString(6);
            dbtractorVendor2.add(c);
            String d = res.getString(3);
            dbtractorVendor3.add(d);
            String e = res.getString(4);
            dbtractorVendor4.add(e);

            System.out.println("TRAC_VEND_COMP_CODE : " + a);
            System.out.println("TRAC_VEND_UNITNO : " + b);
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + c);
            System.out.println("TRAC_VEND_VENDOR_CODE : " + d);


            boolean booleanValue = false;
            for (WebElement uitvr : tractorVendorRelationship) {
                if (uitvr.getText().contains(e)) {
                    for (String dbtv1 : dbtractorVendor4) {
                        if (dbtv1.contains(e)) {
                            System.out.println("TRAC_VEND_LOC: " + e);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Get SEARCH RESULTS Excel Report from Downloads for Tractor Vendor Relationship Location, Report")
    public void get_search_results_excel_report_from_downloads_for_tractor_vendor_relationship_location_report() throws IOException, InterruptedException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(10000);
        String fileNameLocations = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println(" SEARCH RESULTS Excel Report File Name :-" + fileNameLocations);
        driver.close();


        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameLocations + "");
        XSSFWorkbook wbFPM = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM.getSheetAt(0);
        inputStream.close();


        FormulaEvaluator formulaEvaluator = wbFPM.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbFPM.close();
        getAndSwitchToWindowHandles();
    }


    @Given("Validate the Excel Search result Report with database Records {string} {string} Location {string}")
    public void validate_the_excel_search_result_report_with_database_records_location(String environment, String tableName, String location) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + "" +
                "       WHERE TRAC_VEND_LOC = '" + location + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<String> dbTV = new ArrayList<>();
        List<String> dbTV1 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String a = rs.getString(2);
            dbTV.add(a);
            System.out.println("UNIT NO: " + a);
            String b = rs.getString(4);
            dbTV1.add(b);

            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(b)) {
                    for (String dbtv1 : dbTV1) {
                        if (dbtv1.contains(b)) {
                            System.out.println("LOCATION: " + b);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #78 @TractorVendorRelationshipEDIT /................................................//

    @Given("Enter Unit Number {string} which has Accounting Status as CURRENT and click on Search")
    public void enter_unit_number_which_has_accounting_status_as_current_and_click_on_search(String unitNumber) throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).sendKeys(unitNumber);
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).click();
        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println("Accounting Status as CURRENT: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
    }


    @Given("Click on View")
    public void click_on_view() throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.View).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div/div[1]/div/h4")).getText());  //*[@id="btnAddTractorVendorRelation"]
        System.out.println("CREATED BY : " + driver.findElement(By.id("lblCreatedBy")).getText() + " " + driver.findElement(By.id("lblCreatedDate")).getText());
        System.out.println("UPDATED BY : " + driver.findElement(By.id("lblUpdatedBy")).getText() + " " + driver.findElement(By.id("lblUpdatedDate")).getText());
        Thread.sleep(5000);
        driver.findElement(tractorVendorRelationshipPage.CrossIconPopup).click();
        Thread.sleep(3000);
    }


    @Given("Click on Edit, select Accounting Status as COMPLETED")
    public void click_on_edit_select_accounting_status_as_completed() throws InterruptedException {
        System.out.println("=========================================");
        driver.findElement(tractorVendorRelationshipPage.Edit).click();
        Thread.sleep(5000);

        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).clear();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(tractorVendorRelationshipPage.AccountingStatus);
        act.doubleClick(ele).perform();
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).sendKeys("COMPLETED");
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).click();
        Thread.sleep(5000);
        driver.findElement(tractorVendorRelationshipPage.Save).click();


        //   System.out.println(driver.findElement(By.xpath("//*[@id=\"divSmoke\"]")).getAttribute("value"));
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(2000);
        driver.findElement(tractorVendorRelationshipPage.Cancel).click();
        Thread.sleep(2000);
        driver.findElement(tractorVendorRelationshipPage.CrossIconPopup).click();
        Thread.sleep(5000);
        System.out.println("Accounting Status changed from CURRENT to COMPLETED: ");
        //  System.out.println(driver.findElement(By.xpath("//*[@id=\"tbltractorvendor\"]")).getText()); //*[@id="dataTable"]/tbody
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(3000);
    }

    @Given("Validate the Total Records Returned with database Records, Use SQL Four {string} {string} {string} {string}")
    public void validate_the_total_records_returned_with_database_records_use_sql_Four(String environment, String tableName, String unitNo, String accstatus1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + " " +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'" +
                "       AND TRAC_VEND_ACCTG_STATUS = '" + accstatus1 + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<String> dbTV1 = new ArrayList<>();
        List<String> dbTV2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String b = rs.getString(6);
            dbTV1.add(b);
            String c = rs.getString(2);
            dbTV2.add(c);


            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(c)) {
                    for (String dbtv2 : dbTV2) {
                        if (dbtv2.contains(c)) {
                            System.out.println("TRAC_VEND_UNITNO: " + c);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + b);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Click on Edit, select Accounting Status as ACCOUNTING HOLD, Select Save")
    public void click_on_edit_select_accounting_status_as_accounting_hold_select_Save() throws InterruptedException {
        System.out.println("=========================================");

        driver.findElement(tractorVendorRelationshipPage.Edit).click();
        Thread.sleep(5000);

        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).clear();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(tractorVendorRelationshipPage.AccountingStatus);
        act.doubleClick(ele).perform();
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).sendKeys("ACCOUNTING HOLD");
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).click();
        Thread.sleep(5000);
        driver.findElement(tractorVendorRelationshipPage.Save).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(2000);
        //   driver.findElement(tractorVendorRelationshipPage.Save).click();
        Thread.sleep(8000);

        System.out.println("Accounting Status changed from CURRENT to ACCOUNTING HOLD: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(10000);
    }


    @Given("Validate the Total Records Returned with database Records, Use SQL four {string} {string} {string} {string}")
    public void validate_the_total_records_returned_with_database_records_use_sql_four(String environment, String tableName, String unitNo, String accstatus2) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + " " +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'" +
                "       AND TRAC_VEND_ACCTG_STATUS = '" + accstatus2 + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<String> dbTV1 = new ArrayList<>();
        List<String> dbTV2 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String b = rs.getString(6);
            dbTV1.add(b);
            String c = rs.getString(2);
            dbTV2.add(c);


            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(c)) {
                    for (String dbtv2 : dbTV2) {
                        if (dbtv2.contains(c)) {
                            System.out.println("TRAC_VEND_UNITNO: " + c);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + b);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Revert Back Accounting Status to Original, CURRENT")
    public void revert_back_accounting_status_to_CURRENT() throws InterruptedException {
        System.out.println("=========================================");

        driver.findElement(tractorVendorRelationshipPage.Edit).click();
        Thread.sleep(3000);
        WebElement wb = driver.findElement(tractorVendorRelationshipPage.AccountingStatus);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='CURRENT';", wb);
        driver.findElement(tractorVendorRelationshipPage.AccountingStatus).click();

        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(15000);

        System.out.println("Accounting Status changed from ACCOUNTING HOLD to CURRENT: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(5000);
    }


    //............................................/ #79a @IdentifyUnitNoForTractorVendorRelationshipNEW /................................................//

    @Given("SQL query to Identify Active Tractors not in a Relatioship {string} {string}")
    public void sql_query_to_identify_active_tractors_not_in_a_relationship(String environment1, String tableName1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase1 = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase1.createStatement();

        String query1 = "SELECT TOP 100 [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "" +
                "       WHERE Status = 'Active'" +
                "       ORDER BY UnitNo DESC";

        ResultSet res1 = stmt.executeQuery(query1);
        ResultSetMetaData rsmd1 = res1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);
        List<String> dbEquipment = new ArrayList<>();
        while (res1.next()) {
            int rows = res1.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res1.getString(1) +
                    "\t" + res1.getString(2) +
                    "\t" + res1.getString(3) +
                    "\t" + res1.getString(4));
            String a = res1.getString(3);
            dbEquipment.add(a);
            System.out.println("Unit No : " + a);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #79b @TractorVendorRelationshipNEW /................................................//

    @Given("Click on New")
    public void click_on_new() throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.New).click();
        Thread.sleep(5000);
    }

    @Given("Enter Unit Number {string} Vendor Code {string} and Location {string}")
    public void enter_unit_number_vendor_code_and_location(String unitNumber, String vendorCode, String location) throws InterruptedException {

        driver.findElement(tractorVendorRelationshipPage.UnitNumberNew).click();
        WebElement unitNo = driver.findElement(tractorVendorRelationshipPage.UnitNumberNew);
        JavascriptExecutor jseUN = (JavascriptExecutor) driver;
        jseUN.executeScript("arguments[0].value='" + unitNumber + "';", unitNo);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div/div[2]/div[1]/div[3]/div[1]/label")).click();


        WebElement vCode = driver.findElement(By.id("VendorCodePartial_I"));
        JavascriptExecutor jseVC = (JavascriptExecutor) driver;
        jseVC.executeScript("arguments[0].value='" + vendorCode + "';", vCode);
        Thread.sleep(4000);

        driver.findElement(By.id("LocationCodePartial_I")).click();
        driver.findElement(By.id("LocationCodePartial_I")).clear();
        WebElement loc = driver.findElement(By.id("LocationCodePartial_I"));
        JavascriptExecutor jseLOC = (JavascriptExecutor) driver;
        jseLOC.executeScript("arguments[0].value='" + location + "';", loc);
        Thread.sleep(2000);
    }


    @Given("Select Accounting Status as CURRENT and Click on Save")
    public void select_accounting_status_as_current_and_click_on_save() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"btnSaveAgent\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(5000);
    }

    @Given("Find this Newly created Record on Tractor Vendor Code Relationship Maintenance Table {string} {string} {string}")
    public void find_this_newly_created_record_on_tractor_vendor_code_relationship_maintenance_table_unit_no_vendor_code_accounting_status(String unitNumber, String vendorCode, String accoStatus) throws InterruptedException {
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).sendKeys(unitNumber);
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).click();
        Thread.sleep(1000);
        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(15000);
        System.out.println("=========================================");
        System.out.println("Newly Created Record where Unit Number = " + unitNumber + " , Vendor Code = " + vendorCode + " , and Accounting Status = " + accoStatus + " ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());
        Thread.sleep(10000);
    }


    @Given("Validate the Total Records Returned with database Records, Use SQL Four {string} {string} {string} {string} {string}")
    public void validate_the_total_records_returned_with_database_records_use_sql_four_accounting_status(String environment, String tableName, String unitNumber, String vendorCode, String accostatus) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [TRAC_VEND_ID]\n" +
                "      ,[TRAC_VEND_UNITNO]\n" +
                "      ,[TRAC_VEND_VENDOR_CODE]\n" +
                "      ,[TRAC_VEND_LOC]\n" +
                "      ,[TRAC_VEND_COMP_CODE]\n" +
                "      ,[TRAC_VEND_ACCTG_STATUS]\n" +
                "      ,[TRAC_VEND_CREATED_BY]\n" +
                "      ,[TRAC_VEND_CREATED_DATE]\n" +
                "      ,[TRAC_VEND_UPDATED_BY]\n" +
                "      ,[TRAC_VEND_UPDATED_DATE]\n" +
                "       FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNumber + "'" +
                "       AND TRAC_VEND_ACCTG_STATUS = '" + accostatus + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<String> dbTV1 = new ArrayList<>();
        List<String> dbTV2 = new ArrayList<>();
        List<String> dbTV3 = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String c = rs.getString(2);
            dbTV2.add(c);
            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(c)) {
                    for (String dbtv2 : dbTV2) {
                        if (dbtv2.contains(c)) {
                            System.out.println("TRAC_VEND_UNITNO: " + c);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            String d = rs.getString(3);
            dbTV3.add(d);
            System.out.println("TRAC_VEND_VENDOR_CODE : " + d);
            String b = rs.getString(6);
            dbTV1.add(b);
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + b);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/  #80 @ActiveTractorsNotInaRelationship /................................................//

    @Given("Select Company Code {string} Unit Number {string} on Active Tractors not in a Relationship table")
    public void select_company_code_unit_number_on_active_tractors_not_in_a_relationship_table(String companyCode, String unitNo) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img0\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[1]/div/div/p[1]/input")).sendKeys(companyCode);
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[1]/div/div/p[1]/input")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(companyCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(4000);

        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[2]/div/div/p[1]/input")).sendKeys(unitNo);
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[2]/div/div/p[1]/input")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            if (webElement5.getText().contains(unitNo)) {
                webElement5.click();
                break;
            }
        }
        Thread.sleep(2000);
    }


    @Given("Use SQL Seven and Compare to results from SQL One to confirm, what tractors are active or Termination in Process but do not have a relationship  {string} {string} {string} {string} {string}")
    public void use_sql_seven_and_Compare_to_results_from_sql_one_to_confirm_what_tractors_are_active_or_termination_in_process_but_do_not_have_a_relationship(String environment1, String tableName1, String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("This SQL statement shows that this Unit Number " + unitNo + " is ACTIVE ");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase1 = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase1.createStatement();

        String query1 = "SELECT [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "" +
                "       WHERE UnitNo = '" + unitNo + "'";

        ResultSet res1 = stmt.executeQuery(query1);
        ResultSetMetaData rsmd1 = res1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<String> dbEquipment = new ArrayList<>();
        List<String> dbEquipment1 = new ArrayList<>();
        List<String> dbEquipment2 = new ArrayList<>();

        while (res1.next()) {
            int rows = res1.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res1.getString(1) +
                    "\t" + res1.getString(2) +
                    "\t" + res1.getString(3) +
                    "\t" + res1.getString(4));

            String a = res1.getString(3);
            dbEquipment.add(a);
            String b = res1.getString(2);
            dbEquipment1.add(b);
            String c = res1.getString(4);
            dbEquipment2.add(c);
            System.out.println("Owner ID : " + b);


            boolean booleanValue = false;
            for (WebElement atv : tractorVendorRelationship) {
                if (atv.getText().contains(a)) {
                    for (String dbe : dbEquipment) {
                        if (dbe.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Status : " + c);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }


        System.out.println("=========================================");
        System.out.println("This Unit Number " + unitNo + " is not in RELATIONSHIP, so below Database Record is Empty");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/tbody"));
        List<String> dbTV = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String a = rs.getString(2);
            dbTV.add(a);
            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(a)) {
                    for (String dbtv : dbTV) {
                        if (dbtv.contains(a)) {
                            System.out.println("UNIT NO: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Click on Report Button on Active Tractors not in a Relationship Table")
    public void click_on_report_button_on_active_tractors_not_in_a_relationship_table() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[2]/div/div/p[2]")).click();
        Thread.sleep(4000);
        driver.findElement(By.xpath("//*[@id=\"img0\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[1]/div/div/p[2]")).click();
        Thread.sleep(4000);
        driver.findElement(tractorVendorRelationshipPage.ReportNotInRelationship).click();
        Thread.sleep(1000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p")).getText());
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
    }


    @And("^Get SEARCH RESULTS Excel Report from Downloads for Active Tractors not in a Relationship$")
    public void get_SEARCH_RESULTS_Excel_Report_from_Downloads_for_Active_Tractors_not_in_a_Relationship() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(20000);
        String fileNameNotInaRelationship = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println(" SEARCH RESULTS Excel Report File Name :-" + fileNameNotInaRelationship);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameNotInaRelationship + "");
        XSSFWorkbook wbFPM1 = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wbFPM1.getSheetAt(0);
        inputStream.close();

        FormulaEvaluator formulaEvaluator = wbFPM1.getCreationHelper().createFormulaEvaluator();
        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (formulaEvaluator.evaluateInCell(cell).getCellTypeEnum()) {
                    case NUMERIC:
                        System.out.println(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.println(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
        wbFPM1.close();
        getAndSwitchToWindowHandles();
    }


    @Given("Validate the Excel Search result Report with database Records {string} {string} {string}")
    public void validate_the_excel_search_result_report_with_database_records(String environment1, String tableName1, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase1 = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase1.createStatement();

        String query1 = "SELECT TOP 100 [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "";

        ResultSet res1 = stmt.executeQuery(query1);
        ResultSetMetaData rsmd1 = res1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/tbody"));
        List<String> dbEquipment = new ArrayList<>();
        List<String> dbEquipment1 = new ArrayList<>();
        List<String> dbEquipment2 = new ArrayList<>();

        while (res1.next()) {
            int rows = res1.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res1.getString(1) +
                    "\t" + res1.getString(2) +
                    "\t" + res1.getString(3) +
                    "\t" + res1.getString(4));

            String a = res1.getString(3);
            dbEquipment.add(a);
            String b = res1.getString(2);
            dbEquipment1.add(b);

            System.out.println("Unit No : " + a);
            System.out.println("Owner ID : " + b);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #81 @ActiveTractorsNotInaRelationshipNEW /................................................//

    @Given("Select Company Code {string} and Unit Number {string} on Active Tractors not in a Relationship table")
    public void select_company_code_and_unit_number_on_active_tractors_not_in_a_relationship_table(String companyCode, String unitNo) throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img0\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[1]/div/div/p[1]/input")).sendKeys(companyCode);
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[1]/div/div/p[1]/input")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(companyCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(4000);

        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[2]/div/div/p[1]/input")).sendKeys(unitNo);
        driver.findElement(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/thead/tr/th[2]/div/div/p[1]/input")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            if (webElement5.getText().contains(unitNo)) {
                webElement5.click();
                break;
            }
        }
        Thread.sleep(2000);
    }


    @Given("Get the Records Returned")
    public void get_the_records_returned() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println("Active Tractors not in a Relationship : " + driver.findElement(tractorVendorRelationshipPage.TotalRecordsReturnedNotInRelationship).getText());
        log.info(driver.findElement(tractorVendorRelationshipPage.DataTableNotInRelationship).getText());
        Thread.sleep(5000);
    }


    @Given("Use SQL Seven and compare to results from SQL One to confirm what tractors are active or Termination in Process but do not have a relationship  {string} {string} {string} {string} {string}")
    public void use_sql_seven_and_compare_to_results_from_sql_one_to_confirm_what_tractors_are_active_or_termination_in_process_but_do_not_have_a_relationship(String environment1, String tableName1, String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("This SQL statement shows that this Unit Number " + unitNo + " is ACTIVE ");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase1 = browserDriverInitialization.getConnectionToDatabase(environment1);
        stmt = connectionToDatabase1.createStatement();

        String query1 = "SELECT TOP 1000 [EquipmentID]\n" +
                "      ,[OwnerID]\n" +
                "      ,[UnitNo]\n" +
                "      ,[Status]\n" +
                "       FROM " + tableName1 + "" +
                "       WHERE UnitNo = '" + unitNo + "'";

        ResultSet res1 = stmt.executeQuery(query1);
        ResultSetMetaData rsmd1 = res1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<String> dbEquipment = new ArrayList<>();
        List<String> dbEquipment1 = new ArrayList<>();
        List<String> dbEquipment2 = new ArrayList<>();

        while (res1.next()) {
            int rows = res1.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res1.getString(1) +
                    "\t" + res1.getString(2) +
                    "\t" + res1.getString(3) +
                    "\t" + res1.getString(4));

            String a = res1.getString(3);
            dbEquipment.add(a);
            String b = res1.getString(2);
            dbEquipment1.add(b);
            String c = res1.getString(4);
            dbEquipment2.add(c);
            System.out.println("Owner ID : " + b);


            boolean booleanValue = false;
            for (WebElement atv : tractorVendorRelationship) {
                if (atv.getText().contains(a)) {
                    for (String dbe : dbEquipment) {
                        if (dbe.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                        }
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("Status : " + c);
            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }


        System.out.println("=========================================");
        System.out.println("This Unit Number " + unitNo + " is not in RELATIONSHIP, so below Database Record is Empty");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT * FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tvr = driver.findElements(By.xpath("//*[@id=\"tblActiveTractorNotInRelationship\"]/tbody"));
        List<String> dbTV = new ArrayList<>();

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7) +
                    "\t" + rs.getString(8) +
                    "\t" + rs.getString(9) +
                    "\t" + rs.getString(10));

            String a = rs.getString(2);
            dbTV.add(a);
            boolean booleanValue = false;
            for (WebElement uitvr : tvr) {
                if (uitvr.getText().contains(a)) {
                    for (String dbtv : dbTV) {
                        if (dbtv.contains(a)) {
                            System.out.println("UNIT NO: " + a);
                        }
                        booleanValue = true;
                        break;
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Click on New on Tractors not in a Relationship table")
    public void click_on_new_on_tractors_not_in_a_relationship_table() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.New1).click();
        Thread.sleep(3000);
    }


    @Given("Enter a valid Vendor Code and location, click Cancel and exit the form {string} {string}")
    public void enter_a_valid_vendor_code_and_location_click_cancel_and_exit_the_form(String vendorCode, String location) throws InterruptedException {

        WebElement vCode = driver.findElement(By.id("VendorCodePartial_I"));
        JavascriptExecutor jseVC = (JavascriptExecutor) driver;
        jseVC.executeScript("arguments[0].value='" + vendorCode + "';", vCode);
        Thread.sleep(4000);

        driver.findElement(By.id("LocationCodePartial_I")).click();
        driver.findElement(By.id("LocationCodePartial_I")).clear();
        WebElement loc = driver.findElement(By.id("LocationCodePartial_I"));
        JavascriptExecutor jseLOC = (JavascriptExecutor) driver;
        jseLOC.executeScript("arguments[0].value='" + location + "';", loc);
        Thread.sleep(2000);

        driver.findElement(tractorVendorRelationshipPage.Cancel).click();
        Thread.sleep(1000);
    }

    @Given("Enter a valid Vendor Code and location, click Save and exit the form {string} {string}")
    public void enter_a_valid_vendor_code_and_location_click_save_and_exit_the_form(String vendorCode, String location) throws InterruptedException {

        WebElement vCode = driver.findElement(By.id("VendorCodePartial_I"));
        JavascriptExecutor jseVC = (JavascriptExecutor) driver;
        jseVC.executeScript("arguments[0].value='" + vendorCode + "';", vCode);
        Thread.sleep(4000);

        driver.findElement(By.id("LocationCodePartial_I")).click();
        driver.findElement(By.id("LocationCodePartial_I")).clear();
        WebElement loc = driver.findElement(By.id("LocationCodePartial_I"));
        JavascriptExecutor jseLOC = (JavascriptExecutor) driver;
        jseLOC.executeScript("arguments[0].value='" + location + "';", loc);
        Thread.sleep(2000);

        driver.findElement(tractorVendorRelationshipPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(10000);
    }


    @Given("Confirm the edited record now appears in the top grid and the original record is removed from the Active Tractors not in a Relationship grid {string}")
    public void confirm_the_edited_record_now_appears_in_the_top_grid_and_the_original_record_is_removed_from_the_active_tractors_not_in_a_relationship_grid(String unitNo) throws InterruptedException {

        driver.findElement(tractorVendorRelationshipPage.UnitNumber).sendKeys(unitNo);
        driver.findElement(tractorVendorRelationshipPage.UnitNumber).click();
        Thread.sleep(3000);
        driver.findElement(tractorVendorRelationshipPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]")).getText());
        Thread.sleep(3000);
    }

    @Given("Confirm the tractor vendor record was created properly in database Records SQL ONE {string} {string} {string}")
    public void confirm_the_tractor_vendor_record_was_created_properly_in_database_records_sql_one(String environment, String tableName, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [TRAC_VEND_ID]\n" +
                "      ,[TRAC_VEND_UNITNO]\n" +
                "      ,[TRAC_VEND_VENDOR_CODE]\n" +
                "      ,[TRAC_VEND_LOC]\n" +
                "      ,[TRAC_VEND_COMP_CODE]\n" +
                "      ,[TRAC_VEND_ACCTG_STATUS]\n" +
                "      ,[TRAC_VEND_CREATED_BY]\n" +
                "      ,[TRAC_VEND_CREATED_DATE]\n" +
                "      ,[TRAC_VEND_UPDATED_BY]\n" +
                "      ,[TRAC_VEND_UPDATED_DATE]\n" +
                "       FROM " + tableName + "" +
                "       WHERE TRAC_VEND_UNITNO = '" + unitNo + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> tractorVendorRelationship = driver.findElements(By.xpath("//*[@id=\"tbltractorvendor\"]"));
        List<WebElement> tractorVendorRelationship2 = driver.findElements(By.xpath("//*[@id=\"tdvendorUnitNo\"]"));
        List<WebElement> tractorVendorRelationship1 = driver.findElements(By.xpath("  //*[@id=\"tdstatus\"]"));
        List<String> dbtractorVendor = new ArrayList<>();
        List<String> dbtractorVendor1 = new ArrayList<>();
        List<String> dbtractorVendor2 = new ArrayList<>();
        List<String> dbtractorVendor3 = new ArrayList<>();
        List<String> dbtractorVendor4 = new ArrayList<>();

        while (res.next()) {
            int rows = res.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3) +
                    "\t" + res.getString(4) +
                    "\t" + res.getString(5) +
                    "\t" + res.getString(6) +
                    "\t" + res.getString(7) +
                    "\t" + res.getString(8) +
                    "\t" + res.getString(9) +
                    "\t" + res.getString(10));
            System.out.println();

            String a = res.getString(5);
            dbtractorVendor.add(a);
            String b = res.getString(2);
            dbtractorVendor1.add(b);
            String c = res.getString(6);
            dbtractorVendor2.add(c);
            String d = res.getString(3);
            dbtractorVendor3.add(d);
            String e = res.getString(4);
            dbtractorVendor4.add(e);

            System.out.println("TRAC_VEND_COMP_CODE : " + a);

          /*  boolean booleanValue = false;
            for (WebElement tVR : tractorVendorRelationship2) {
                if (tVR.getText().contains(b)) {
                    for (String dbTV1 : dbtractorVendor1) {
                        if (dbTV1.contains(b)) {    */
            System.out.println("TRAC_VEND_UNITNO : " + b);
                  /*          booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }  */
            System.out.println("TRAC_VEND_ACCTG_STATUS : " + c);
            System.out.println("TRAC_VEND_VENDOR_CODE : " + d);
            System.out.println("TRAC_VEND_LOC : " + e);

            System.out.println("Database closed ......");
            System.out.println("=========================================");
        }
    }


}