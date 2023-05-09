package stepDefinitions;

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

import static org.junit.Assert.*;


public class AgentSettlementAdjustmentsStepDef {
    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";
    String usernameExpected = "";

    EBHLoginPage ebhlogInPage = new EBHLoginPage(driver);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    SettlementsPage settlementsPage = new SettlementsPage(driver);
    AgentSettlementAdjustmentsPage agentSettlementAdjustmentsPage = new AgentSettlementAdjustmentsPage(driver);
    Logger log = Logger.getLogger("SettlingStepDef");
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();


    //...................................../ Background /..........................................//

    @After("@FailureScreenShot2")
    public void takeScreenshotOnFailure2(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }

    @Given("^Run Test for \"([^\"]*)\" on Browser \"([^\"]*)\" for EBH Agent Settlement Adjustments$")
    public void Run_Test_for_on_Browser_for_EBH_Agent_Settlement_Adjustments(String environment, String browser) throws MalformedURLException {
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
        driver = new RemoteWebDriver(new URL("http://192.168.0.14:4444"), cap);
    }

    @And("^Enter the url for EBH Agent Settlement Adjustments$")
    public void Enter_the_url_for_EBH_Agent_Settlement_Adjustments() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(URL);
    }

    @And("^Login to the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Agent Settlement Adjustments$")
    public void Login_to_the_Agents_Portal_with_username_and_password_for_EBH_Agent_Settlement_Adjustments(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhlogInPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.password));
        driver.findElement(ebhlogInPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.signinButton));
        driver.findElement(ebhlogInPage.signinButton).click();
    }

    @And("^Navigate to the Corporate Page on Main Menu for Agent Settlement Adjustments$")
    public void Navigate_to_the_Corporate_Page_on_Main_Menu_for_Agent_Settlement_Adjustments() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Given("^Navigate to the Settlements page for Agent Settlement Adjustments$")
    public void Navigate_to_the_Settlements_page_for_Agent_Settlement_Adjustments() {
        driver.findElement(corporatePage.settlements).click();
    }

    @Then("^Close all open Browsers on EBH for Agent Settlement Adjustments$")
    public void Close_all_open_Browsers_on_EBH_for_Agent_Settlement_Adjustments() {
        driver.close();
        driver.quit();
    }

    //...................................../ 30 @AgentSettlementAdjustmentsVendorId /..........................................//
    @And("^Navigate to Agent Settlement Adjustments$")
    public void Navigate_to_Agent_Settlement_Adjustments() {
        driver.findElement(settlementsPage.AgentSettlementAdjustments).click();
    }

    @And("^Enter Vendor ID as \"([^\"]*)\" and click$")
    public void Enter_Vendor_ID_as_and_click(String vendorID) {
        driver.findElement(agentSettlementAdjustmentsPage.VendorIDBox).sendKeys(vendorID);
        driver.findElement(agentSettlementAdjustmentsPage.VendorIDBox).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
    }


    @And("^Validate VendorID, Vendor Code Description of \"([^\"]*)\"$")
    public void Validate_VendorID_Vendor_Code_Description_of(String code) throws InterruptedException {
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.VendorCode).getText());
        Thread.sleep(3000);
        System.out.println("Vendor ID and Description Of " + code + " : " + driver.findElement(agentSettlementAdjustmentsPage.VendorCodeDescription).getText());
    }

    @And("^Click on Recurring Only and Validate Total Records Returned and Total Record$")
    public void Click_on_Recurring_Only_and_Validate_Total_Records_Returned_and_Total_Record() throws
            InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println(" Recurring Only: " + driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(10000);
    }


    @And("^Validate Total Records Returned on Recurring Only with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Total_Records_Returned_on_Recurring_Only_with_Database_Record_and(String
                                                                                                   environment, String tableName, String vendorId) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("Recurring Only: ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?,?,?,?)}";
        // VendorCode, VendorName, LocationCode, EFSChecked, RecurringChecked, IncludeComplete
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + vendorId + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "0");
        cstmt.setString(5, "1");
        cstmt.setString(6, "0");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASAReOnlyTable = new ArrayList<>();
        List<String> dbASAReOnlyTable1 = new ArrayList<>();
        List<WebElement> ASAReOnlyTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21) +
                    "\t" + rs.getString(22) +
                    "\t" + rs.getString(23) +
                    "\t" + rs.getString(24) +
                    "\t" + rs.getString(25) +
                    "\t" + rs.getString(26));

            String str = rs.getString(2);
            dbASAReOnlyTable.add(str);

            boolean booleanValue = false;
            for (WebElement reonlyTable : ASAReOnlyTable) {
                if (reonlyTable.getText().contains(str)) {
                    for (String dbreonlyTable : dbASAReOnlyTable) {
                        if (dbreonlyTable.contains(str)) {
                            System.out.println(str);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbASAReOnlyTable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement reonlyTable1 : ASAReOnlyTable) {
                if (reonlyTable1.getText().contains(b)) {
                    for (String dbreonlyTable1 : dbASAReOnlyTable1) {
                        if (dbreonlyTable1.contains(b)) {
                            System.out.println(b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Validate Pay Code contains ES and ME$")
    public void Validate_Pay_Code_has_ES_and_ME() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        System.out.println("=========================================");
        String text1 = driver.findElement(By.xpath("//*[@id=\"thPayCode\"]/div/div")).getText();
        System.out.println(text1);
        Thread.sleep(1000);
        if (text1.contains("ES")) {
            assertTrue("Pay Code contains ES", true);
            System.out.println("In Recurring Only, Pay Code contains ES");
        } else {
            System.out.println("In Recurring Only, Pay Code doesn't contains ES");
        }

        if (text1.contains("ME")) {
            assertTrue("Pay Code contains ME", true);
            System.out.println("In Recurring Only, Pay Code contains ME");
        } else {
            System.out.println("In Recurring Only, Pay Code doesn't contains ME");
        }
    }

    @And("^Click on EFS and Validate total records Returned$")
    public void Click_on_EFS_and_Validate_total_records_Returned() throws InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.EFS).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println(" EFS: " + driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(10000);
    }

    @And("^Validate Total Records Returned on EFS with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Total_Records_Returned_on_EFS_with_Database_Record_and(String environment, String
            tableName, String vendorId) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("EFS : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);

        String query = "{call " + tableName + " (?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);

        cstmt.setString(1, "" + vendorId + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "1");
        cstmt.setString(5, "0");
        cstmt.setString(6, "0");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASAReOnlyTable = new ArrayList<>();
        List<String> dbASAReOnlyTable1 = new ArrayList<>();
        List<WebElement> ASAReOnlyTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21) +
                    "\t" + rs.getString(22) +
                    "\t" + rs.getString(23) +
                    "\t" + rs.getString(24) +
                    "\t" + rs.getString(25) +
                    "\t" + rs.getString(26));

            String a = rs.getString(2);
            dbASAReOnlyTable.add(a);

            String b = rs.getString(5);
            dbASAReOnlyTable1.add(b);

            boolean booleanValue = false;
            for (WebElement reonlyTable : ASAReOnlyTable) {
                if (reonlyTable.getText().contains(a)) {
                    for (String dbreonlyTable : dbASAReOnlyTable) {
                        if (dbreonlyTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            boolean booleanValue1 = false;
            for (WebElement reonlyTable1 : ASAReOnlyTable) {
                if (reonlyTable1.getText().contains(b)) {
                    for (String dbreonlyTable1 : dbASAReOnlyTable1) {
                        if (dbreonlyTable1.contains(b)) {
                            System.out.println(b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @And("^Click on Include Complete and Validate total records Returned$")
    public void Click_on_Include_Complete_and_Validate_total_records_Returned() throws InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.EFS).click();
        Thread.sleep(2000);
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println("Include Complete: " + driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(10000);
    }

    @And("^Validate Total Records Returned on Include Complete with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Total_Records_Returned_on_Include_Complete_with_Database_Record_and(String
                                                                                                     environment, String tableName, String vendorId) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("Include Complete: ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);
        cstmt.setString(1, "" + vendorId + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "0");
        cstmt.setString(5, "0");
        cstmt.setString(6, "1");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbicTable = new ArrayList<>();
        List<WebElement> icTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21) +
                    "\t" + rs.getString(22) +
                    "\t" + rs.getString(23) +
                    "\t" + rs.getString(24) +
                    "\t" + rs.getString(25) +
                    "\t" + rs.getString(26));

            String a = rs.getString(2);
            dbicTable.add(a);

            boolean booleanValue = false;
            for (WebElement ictable : icTable) {
                if (ictable.getText().contains(a)) {
                    for (String itable : dbicTable) {
                        if (itable.contains(a)) {
                            System.out.println(a);
                            System.out.println();
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ 31 @AgentSettlementAdjustmentsAgentCode /................................................//

    @And("^Enter Agent Code as \"([^\"]*)\" and click$")
    public void Enter_Agent_Code_as_and_click(String agentCode) throws InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.SearchByAgentCodeBox).sendKeys(agentCode);
        driver.findElement(agentSettlementAdjustmentsPage.SearchByAgentCodeBox).click();
        Thread.sleep(2000);
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
    }

    @And("^Validate Total Records Returned for Agent Code on Recurring Only with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Total_Records_Returned_for_Agent_Code_on_Recurring_Only_with_Database_Record_and
            (String environment, String tableName, String vendorId, String agentCode) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);
        //? = VendorCode, VendorName, LocationCode, EFSChecked, RecurringChecked, IncludeComplete
        cstmt.setString(1, "" + vendorId + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "" + agentCode + "");
        cstmt.setString(4, "0");
        cstmt.setString(5, "1");
        cstmt.setString(6, "0");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASAReOnlyTable = new ArrayList<>();
        List<String> dbASAReOnlyTable1 = new ArrayList<>();
        List<WebElement> ASAReOnlyTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21) +
                    "\t" + rs.getString(22) +
                    "\t" + rs.getString(23) +
                    "\t" + rs.getString(24) +
                    "\t" + rs.getString(25) +
                    "\t" + rs.getString(26));

            String a = rs.getString(2);
            dbASAReOnlyTable.add(a);

            String b = rs.getString(3);
            dbASAReOnlyTable1.add(b);

            boolean booleanValue = false;
            for (WebElement reonlyTable : ASAReOnlyTable) {
                if (reonlyTable.getText().contains(a)) {
                    for (String dbreonlyTable : dbASAReOnlyTable) {
                        if (dbreonlyTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            boolean booleanValue1 = false;
            for (WebElement reonlyTable1 : ASAReOnlyTable) {
                if (reonlyTable1.getText().contains(b)) {
                    for (String dbreonlyTable1 : dbASAReOnlyTable1) {
                        if (dbreonlyTable1.contains(b)) {
                            System.out.println(b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Validate Total Records Returned for Agent Code on EFS with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Total_Records_Returned_for_Agent_Code_on_EFS_with_Database_Record_and(String
                                                                                                       environment, String tableName, String vendorId, String agentCode) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);
        cstmt.setString(1, "" + vendorId + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "" + agentCode + "");
        cstmt.setString(4, "1");
        cstmt.setString(5, "0");
        cstmt.setString(6, "0");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbASAReOnlyTable = new ArrayList<>();
        List<String> dbASAReOnlyTable1 = new ArrayList<>();
        List<WebElement> ASAReOnlyTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21) +
                    "\t" + rs.getString(22) +
                    "\t" + rs.getString(23) +
                    "\t" + rs.getString(24) +
                    "\t" + rs.getString(25) +
                    "\t" + rs.getString(26));

            String a = rs.getString(2);
            dbASAReOnlyTable.add(a);

            String b = rs.getString(3);
            dbASAReOnlyTable1.add(b);

            boolean booleanValue = false;
            for (WebElement reonlyTable : ASAReOnlyTable) {
                if (reonlyTable.getText().contains(a)) {
                    for (String dbreonlyTable : dbASAReOnlyTable) {
                        if (dbreonlyTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            boolean booleanValue1 = false;
            for (WebElement reonlyTable1 : ASAReOnlyTable) {
                if (reonlyTable1.getText().contains(b)) {
                    for (String dbreonlyTable1 : dbASAReOnlyTable1) {
                        if (dbreonlyTable1.contains(b)) {
                            System.out.println(b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @And("^Validate Total Records Returned for Agent Code on Include Complete with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Total_Records_Returned_for_Agent_Code_on_Include_Complete_with_Database_Record_and
            (String environment, String tableName, String vendorId, String agentCode) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        String query = "{call " + tableName + " (?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query);
        cstmt.setString(1, "" + vendorId + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "" + agentCode + "");
        cstmt.setString(4, "0");
        cstmt.setString(5, "0");
        cstmt.setString(6, "1");

        ResultSet rs = cstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbINCTable = new ArrayList<>();
        List<String> dbINCTable1 = new ArrayList<>();
        List<WebElement> INCTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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
                    "\t" + rs.getString(15) +
                    "\t" + rs.getString(16) +
                    "\t" + rs.getString(17) +
                    "\t" + rs.getString(18) +
                    "\t" + rs.getString(19) +
                    "\t" + rs.getString(20) +
                    "\t" + rs.getString(21) +
                    "\t" + rs.getString(22) +
                    "\t" + rs.getString(23) +
                    "\t" + rs.getString(24) +
                    "\t" + rs.getString(25) +
                    "\t" + rs.getString(26));

            String a = rs.getString(2);
            dbINCTable.add(a);

            String b = rs.getString(3);
            dbINCTable1.add(b);

            boolean booleanValue = false;
            for (WebElement incTable : INCTable) {
                if (incTable.getText().contains(a)) {
                    for (String dbincTable : dbINCTable) {
                        if (dbincTable.contains(a)) {
                            System.out.println(a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            boolean booleanValue1 = false;
            for (WebElement incTable1 : INCTable) {
                if (incTable1.getText().contains(b)) {
                    for (String dbincTable1 : dbINCTable1) {
                        if (dbincTable1.contains(b)) {
                            System.out.println(b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    Assert.assertTrue( "AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................../ 32 @ScenarioActiveCompleteActive /..........................................................//

    @Given("^Enter Vendor Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" and Click on Search$")
    public void enter_Vendor_Code_and_Click_on_search(String vendorCode, String startDate, String endDate) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).sendKeys(vendorCode);
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"txtFromDate\"]")).sendKeys(startDate);
        driver.findElement(By.xpath("//*[@id=\"txtFromDate\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"txtToDate\"]")).sendKeys(endDate);
        driver.findElement(By.xpath("//*[@id=\"txtToDate\"]")).click();

        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);
    }

    @Given("Select the Pay Code {string} and Start Date {string}")
    public void select_the_pay_code_and_start_date(String payCode, String startDate) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                webElement0.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(startDate)) {
                webElement1.click();
                break;
            }
        }
    }

    @Given("^Select the First Edit Button on Action Column \"([^\"]*)\"$")
    public void select_the_First_Edit_Button_on_Action_Column(String adjustmentID) throws InterruptedException {
        Thread.sleep(4000);
        WebElement editClick = driver.findElement(By.id("linkEdit_" + adjustmentID + ""));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", editClick);
    }


    @Given("^Change ACTIVE status to COMPLETE, Select YES Button, Confirm Status changes to COMPLETE \"([^\"]*)\" \"([^\"]*)\"$")
    public void change_ACTIVE_status_to_COMPLETE_Select_YES_Button_Confirm_Status_changes_to_COMPLETE(String status, String endDate) throws Throwable {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).sendKeys(endDate);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"Status_B-1Img\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"Status_DDD_LLBVSTC\"]/table/tr"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(status)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click(); //yes  //*[@id="btnYesAction"]
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"btnSaveAgent\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click(); //ok
        Thread.sleep(2000);
    }


    @Given("Select Include Complete")
    public void select_Include_Complete() throws InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);
    }

    @Given("Select the Pay Code {string} Start Date {string} and Status {string} {string}")
    public void select_the_pay_code_start_date_and_status_for_complete(String payCode, String startDate, String status, String amount) throws InterruptedException {

        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                webElement0.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(startDate)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(status)) {
                webElement2.click();
                break;
            }
        }
        Thread.sleep(2000);


        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(amount)) {
                webElement3.click();
                break;
            }
        }
        Thread.sleep(2000);
    }

    @Given("Get the Records Returned on Agent Settlement Adjustments Table, when ACTIVE status changed to COMPLETE")
    public void get_the_records_returned_on_agent_settlement_adjustments_table_when_active_status_changed_to_complete() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Status after changing ACTIVE to COMPLETE : " + driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]//*[@id=\"tdStatus\"]")).getText());
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(10000);
        System.out.println("..................................");
    }


    @Given("^Validate the Records Returned, WHEN ACTIVE TO COMPLETE with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Records_Returned_WHEN_ACTIVE_TO_COMPLETE_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String status, String startDate, String amount) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   AND [AGENT_ADJUST_STATUS] = '" + status + "'" +
                "   and [AGENT_ADJUST_START_DATE]= '" + startDate + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustments = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();

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
                    "\t" + res.getString(14));


            String a = res.getString(3);
            dbAgentAdjustmentsTable.add(a);
            String b = res.getString(4);
            dbAgentAdjustmentsTable1.add(b);
            System.out.println("STATUS : " + b);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustments) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("PAY CODE : " + a);
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


            boolean booleanValue1 = false;
            for (WebElement aA : agentAdjustments) {
                if (aA.getText().contains(b)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable1) {
                        if (dbAAT1.contains(b)) {
                            System.out.println("STATUS : " + b);
                            System.out.println();
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }

        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Select the First Edit Button on Action Column for COMPLETE \"([^\"]*)\"$")
    public void select_the_First_Edit_Button_on_Action_Column_for_complete(String adjustmentID) throws InterruptedException {
        Thread.sleep(4000);
        WebElement editClick = driver.findElement(By.id("linkEdit_" + adjustmentID + ""));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", editClick);
    }

    @Given("^Change COMPLETE status to ACTIVE, Select YES Button, Confirm ACTIVE Status was accepted \"([^\"]*)\"$")
    public void change_COMPLETE_status_to_ACTIVE_Select_YES_Button_Confirm_ACTIVE_Status_was_accepted(String status1) throws InterruptedException {

        WebElement wb = driver.findElement(By.id("Status_I"));
        wb.clear();
        Thread.sleep(2000);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + status1 + "';", wb);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"btnSaveAgent\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(10000);
    }

    @Given("^Get the Records Returned on Agent Settlement Adjustments Table after Revert Back$")
    public void get_the_Records_Returned_on_Agent_Settlement_Adjustments_Table_after_revert_back() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Status after Revert Back : ");
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(agentSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(10000);
        System.out.println("..................................");
    }


    @Given("^Validate the Records Returned, WHEN COMPLETE TO ACTIVE with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Records_Returned_WHEN_COMPLETE_TO_ACTIVE_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String status1, String startDate, String amount) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   AND [AGENT_ADJUST_STATUS] = '" + status1 + "'" +
                "   and [AGENT_ADJUST_START_DATE]= '" + startDate + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustments = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();

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
                    "\t" + res.getString(14));


            String a = res.getString(3);
            dbAgentAdjustmentsTable.add(a);
            String b = res.getString(4);
            dbAgentAdjustmentsTable1.add(b);
            System.out.println("STATUS : " + b);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustments) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("PAY CODE : " + a);
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


    //............................................../ 33 @ScenarioOnNewDATE /..........................................................//

    @Given("^Enter Vendor Code \"([^\"]*)\" and Click on Search$")
    public void enter_Vendor_Code_and_Click_on_search(String vendorCode) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).sendKeys(vendorCode);
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);
    }

    @Given("^Select the New Button, Enter required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void select_the_New_Button_Enter_required_fields(String payCode, String status, String frequency, String amount, String applytoagent) throws InterruptedException {

        Thread.sleep(5000);
        driver.findElement(agentSettlementAdjustmentsPage.New).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PayCode_I\"]")).sendKeys(payCode);
        driver.findElement(By.xpath("//*[@id=\"PayCode_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Status_I\"]")).sendKeys(status);
        driver.findElement(By.xpath("//*[@id=\"Status_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Frequency_I\"]")).sendKeys(frequency);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Frequency_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"txtAmount\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"txtAmount\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_I\"]")).sendKeys(applytoagent);
        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_I\"]")).click();
        Thread.sleep(2000);
    }


    @Given("^Enter an End Date that is after the Start Date and not in the past, Select the Save button \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_an_End_Date_that_is_after_the_Start_Date_and_not_in_the_past_Select_the_Save_button(String startDate, String endDate) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]")).clear();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]")).sendKeys(startDate);
        driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).sendKeys(endDate);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).click();
        Thread.sleep(2000);

        driver.findElement(agentSettlementAdjustmentsPage.Save).click();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(3000);
    }

    // @Given("^De-select Recurring Only$")
    // public void de_select_Recurring_Only() throws InterruptedException {
    //    Thread.sleep(8000);
    //   driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
    //   driver.findElement(agentSettlementAdjustmentsPage.Search).click();
    //  }


    @Given("Select Include Complete and Search the Newly created record {string} {string} {string} {string} {string} {string}")
    public void select_Include_Complete_and_search_the_newly_created_record(String payCode, String status, String amount, String frequency, String applyToAgent, String startDate) throws InterruptedException {
        Thread.sleep(1000);
        //  driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        WebElement includeComplete = driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", includeComplete);

        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                Thread.sleep(5000);
                webElement0.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(status)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            Thread.sleep(2000);
            if (webElement5.getText().contains(amount)) {
                webElement5.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(frequency)) {
                webElement2.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement3 : list3) {
            Thread.sleep(2000);
            if (webElement3.getText().contains(startDate)) {
                webElement3.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img8\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement6 : list6) {
            Thread.sleep(2000);
            if (webElement6.getText().contains(applyToAgent)) {
                webElement6.click();
                break;
            }
        }
        Thread.sleep(5000);
    }


    @Given("^Validate the Records Returned on selecting NEW BUTTON with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_Records_Returned_on_selecting_NEW_BUTTON_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String status, String amount, String frequency, String applyToAgent, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   and [AGENT_ADJUST_STATUS] = '" + status + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'" +
                "   and [AGENT_ADJUST_FREQ] = '" + frequency + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applyToAgent + "'" +
                "   and [AGENT_ADJUST_START_DATE] = '" + startDate + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

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

            String b = res.getString(1);
            dbAgentAdjustmentsTable1.add(b);
            System.out.println("ADJUSTMENT ID = " + b);

            String a = res.getString(14);
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("AGENT CODE = " + a);
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

            String aa = res.getString(3);
            dbAgentAdjustmentsTable2.add(aa);
            System.out.println("PAY CODE : " + aa);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #34 @ScenarioOnNew/ApplyToAgent/VendorCode /................................................//


    @Given("^Enter Vendor Code as \"([^\"]*)\" and Click on Search$")
    public void enter_Vendor_Code_as_and_Click_on_search(String vendorCode) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).sendKeys(vendorCode);
        driver.findElement(By.xpath("//*[@id=\"VendorPartial_I\"]")).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);
    }

    @Given("^Select the New Button, Enter required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" and Apply To Agent \"([^\"]*)\"$")
    public void select_the_New_Button_Enter_required_fields_and_apply_to_agent(String payCode, String status, String frequency, String amount, String applytoagent) throws InterruptedException {

        Thread.sleep(5000);
        driver.findElement(agentSettlementAdjustmentsPage.New).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PayCode_I\"]")).sendKeys(payCode);
        driver.findElement(By.xpath("//*[@id=\"PayCode_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Status_I\"]")).sendKeys(status);
        driver.findElement(By.xpath("//*[@id=\"Status_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Frequency_I\"]")).sendKeys(frequency);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Frequency_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"txtAmount\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"txtAmount\"]")).click();
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_B-1Img\"]")).click();
        List<WebElement> list = driver.findElements(new By.ByCssSelector("#ApplyToAgentpartial_DDD_PW-1"));
        Thread.sleep(2000);
        for (WebElement webElement : list) {
            Thread.sleep(2000);
            System.out.println(webElement.getText());
            if (webElement.getText().contains(payCode)) {
                webElement.click();
                break;
            }
        }

        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_I\"]")).sendKeys(applytoagent);
        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_I\"]")).click();
        Thread.sleep(2000);
    }

    @Given("Select Include Complete and Search the Newly created record for Apply to Agent {string} {string} {string} {string} {string} {string}")
    public void select_Include_Complete_and_search_the_newly_created_record_for_apply_to_agent(String payCode, String status, String amount, String frequency, String applyToAgent, String startDate) throws InterruptedException {
        Thread.sleep(1000);
        WebElement includeComplete = driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", includeComplete);
        Thread.sleep(1000);
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                Thread.sleep(5000);
                webElement0.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(status)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            if (webElement5.getText().contains(amount)) {
                webElement5.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(frequency)) {
                webElement2.click();
                break;
            }
        }

        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img8\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement6 : list6) {
            if (webElement6.getText().contains(applyToAgent)) {
                webElement6.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(startDate)) {
                Thread.sleep(2000);
                webElement3.click();
                break;
            }
        }
    }

    @Given("^Validate the Records Returned on selecting NEW BUTTON with Database Record for Apply to Agent \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_Records_Returned_on_selecting_NEW_BUTTON_with_Database_Record_and_for_apply_to_agent(String environment, String tableName, String vendorCode, String payCode, String status, String amount, String frequency, String applyToAgent, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   and [AGENT_ADJUST_STATUS] = '" + status + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'" +
                "   and [AGENT_ADJUST_FREQ] = '" + frequency + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applyToAgent + "'" +
                "   and [AGENT_ADJUST_START_DATE] = '" + startDate + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

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

            String b = res.getString(1);
            dbAgentAdjustmentsTable1.add(b);
            System.out.println("ADJUSTMENT ID = " + b);

            String a = res.getString(14);
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("AGENT CODE = " + a);
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

            String aa = res.getString(3);
            dbAgentAdjustmentsTable2.add(aa);
            System.out.println("PAY CODE : " + aa);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #35 @ScenarioOnNew/ApplyToAgent/AgentCode /................................................//

    @Given("Enter Agent Code as {string} and click on Search")
    public void enter_agent_code_as_and_click_on_search(String agentCode) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"AgentCodePartial_I\"]")).sendKeys(agentCode);
        driver.findElement(By.xpath("//*[@id=\"AgentCodePartial_I\"]")).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(1000);
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);
    }

    @Given("^Validate the Records Returned on selecting NEW BUTTON with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_Records_Returned_on_selecting_NEW_BUTTON_with_Database_Record_and_Agent_Code(String environment, String tableName, String agentCode, String payCode, String status, String amount, String frequency, String applyToAgent, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_APPLY_TO_AGENT] = '" + agentCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   and [AGENT_ADJUST_STATUS] = '" + status + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'" +
                "   and [AGENT_ADJUST_FREQ] = '" + frequency + "'" +
                "   and [AGENT_ADJUST_START_DATE] = '" + startDate + "'";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();

        while (rs1.next()) {
            int rows = rs1.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15));

            String b = rs1.getString(1);
            dbAgentAdjustmentsTable1.add(b);
            System.out.println("ADJUSTMENT ID = " + b);

            String a = rs1.getString(2);
            dbAgentAdjustmentsTable.add(a);
            System.out.println("VENDOR CODE = " + a);

          /*  boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("VENDOR CODE = " + a);
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
            }  */
            String d = rs1.getString(14);
            dbAgentAdjustmentsTable3.add(d);
            System.out.println("APPLY TO AGENT = " + d);
            String aa = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(aa);
            System.out.println("PAY CODE : " + aa);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//............................................../ 36 @ScenarioOnNewMaxLimitandTotaltoDate /..........................................................//

    @Given("Select the Required data for Edit {string} {string} {string} {string} {string} {string} {string}")
    public void select_the_required_data_for_edit(String payCode, String status, String amount, String frequency, String applyToAgent, String maxLimit, String startDate) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);


        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                webElement0.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(status)) {
                webElement1.click();
                break;
            }
        }


        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            if (webElement5.getText().contains(amount)) {
                webElement5.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img5\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(frequency)) {
                webElement3.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img8\"]")).click();
        List<WebElement> list7 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement7 : list7) {
            if (webElement7.getText().contains(applyToAgent)) {
                webElement7.click();
                break;
            }
        }


        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(maxLimit)) {
                webElement2.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement6 : list6) {
            if (webElement6.getText().contains(startDate)) {
                webElement6.click();
                break;
            }
        }
    }

    @Given("^Click on Edit$")
    public void click_on_edit() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(agentSettlementAdjustmentsPage.Edit).click();
        Thread.sleep(2000);
    }


    @Given("^Select the New Button, Enter the required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void select_the_New_Button_Enter_the_required_fields(String payCode, String status, String frequency, String amount, String applytoagent) throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(agentSettlementAdjustmentsPage.New).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PayCode_I\"]")).sendKeys(payCode);
        driver.findElement(By.xpath("//*[@id=\"PayCode_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Status_I\"]")).sendKeys(status);
        driver.findElement(By.xpath("//*[@id=\"Status_I\"]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"Frequency_I\"]")).sendKeys(frequency);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"Frequency_I\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"txtAmount\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"txtAmount\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_I\"]")).sendKeys(applytoagent);
        driver.findElement(By.xpath("//*[@id=\"ApplyToAgentpartial_I\"]")).click();
        Thread.sleep(2000);
    }

    @Given("^Enter an End Date that is after the Start Date and not in the past \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_an_End_Date_that_is_after_the_Start_Date_and_not_in_the_past(String startDate, String endDate) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]")).clear();
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]")).sendKeys(startDate);
        driver.findElement(By.xpath("//*[@id=\"txtStartDate\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).sendKeys(endDate);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).click();
        Thread.sleep(2000);
    }

    @Given("^Enter the Max Limit to a value Less Than current Total to Date, Select the Save button \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_the_Max_Limit_to_a_value_Less_Than_current_Total_to_Date_Select_the_Save_button(String maxLimit, String totalToLimit) throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]")).sendKeys(maxLimit);
        driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"txtTotalToDate\"]")).sendKeys(totalToLimit);
        driver.findElement(By.xpath("//*[@id=\"txtTotalToDate\"]")).click();
        Thread.sleep(2000);

        driver.findElement(agentSettlementAdjustmentsPage.Save).click();

        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();

        //   driver.findElement(agentSettlementAdjustmentsPage.Save).click();
        //   Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();  //*[@id="btnOK"]/img
        Thread.sleep(3000);
    }

    @Given("Select Include Complete and search for the newly created record {string} {string} {string} {string} {string} {string}")
    public void select_Include_Complete_and_search_for_the_newly_created_record(String payCode, String status, String amount, String maxLimit, String totalToDate, String startDate) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(3000);

        //  Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                webElement0.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(status)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            if (webElement5.getText().contains(amount)) {
                webElement5.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(maxLimit)) {
                webElement2.click();
                break;
            }
        }

        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img11\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement6 : list6) {
            if (webElement6.getText().contains(totalToDate)) {
                webElement6.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(startDate)) {
                webElement3.click();
                break;
            }
        }

    }


    @Given("^Select the First Edit Button on Action Column on Include Complete$")
    public void select_the_First_Edit_Button_on_Action_Column_on_Include_Complete() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.linkText("EDIT")).click();
    }

    @Given("^Validate the Records Returned when Max Limit is LESS THAN with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_Records_Returned_when_Max_Limit_is_LESS_THAN_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String applytoAgent, String frequency, String amount, String maxlimit1, String startDate, String totaltoDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Records Returned when Max Limit is LESS THAN Total to Date: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applytoAgent + "'" +
                "   and [AGENT_ADJUST_FREQ] = '" + frequency + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'" +
                "   and [AGENT_ADJUST_STATUS] = 'ACTIVE'" +
                "   and [AGENT_ADJUST_TOP_LIMIT] = '" + maxlimit1 + "'" +
                "   and [AGENT_ADJUST_START_DATE] = '" + startDate + "'" +
                "   and [AGENT_ADJUST_TOTAL_TO_DATE] = '" + totaltoDate + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable4 = new ArrayList<>();

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

            String a = res.getString(14);
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("AGENT CODE : " + a);
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

            String aa = res.getString(4);
            dbAgentAdjustmentsTable1.add(aa);

            boolean booleanValue1 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aa)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable1) {
                        if (dbAAT1.contains(aa)) {
                            System.out.println("STATUS : " + aa);
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
            //  System.out.println("STATUS : " + aa);
            String aab = res.getString(5);
            dbAgentAdjustmentsTable2.add(aab);

            boolean booleanValue2 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aab)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable2) {
                        if (dbAAT1.contains(aab)) {
                            System.out.println("FREQUENCY : " + aab);
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

            String aba = res.getString(8);
            dbAgentAdjustmentsTable3.add(aba);

            boolean booleanValue3 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aba)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable3) {
                        if (dbAAT1.contains(aba)) {
                            System.out.println("MAX LIMIT : " + aba);
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
            //   System.out.println("MAX LIMIT : " + aba);
            String abba = res.getString(9);
            dbAgentAdjustmentsTable4.add(abba);

            boolean booleanValue4 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(abba)) {
                    for (String tot : dbAgentAdjustmentsTable4) {
                        if (tot.contains(abba)) {
                            System.out.println("TOTAL TO DATE : " + abba);
                        }
                        booleanValue4 = true;
                        break;
                    }
                }
                if (booleanValue4) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Change the Max Limit to a value = Current Total to Date, Select the Save button \"([^\"]*)\"$")
    public void change_the_Max_Limit_to_a_value_Current_Total_to_Date_Select_the_Save_button(String maxLimit2) throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(2000);
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]")).sendKeys(maxLimit2);
        driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]")).click();
        Thread.sleep(2000);

        driver.findElement(agentSettlementAdjustmentsPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        Thread.sleep(5000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p/i")).getText());
        Thread.sleep(8000);
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(10000);

    }


    @Given("Search for the newly created record {string} {string} {string} {string} {string} {string}")
    public void search_for_the_newly_created_record(String payCode, String status, String amount, String maxLimit, String totalToDate, String startDate) throws InterruptedException {
        //  Thread.sleep(1000);
        //  driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        //  driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        //  Thread.sleep(3000);

        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"img1\"]")));

        driver.findElement(By.xpath("//*[@id=\"img1\"]")).click();
        List<WebElement> list0 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement0 : list0) {
            if (webElement0.getText().contains(payCode)) {
                webElement0.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(status)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement5 : list5) {
            if (webElement5.getText().contains(amount)) {
                webElement5.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(maxLimit)) {
                webElement2.click();
                break;
            }
        }

        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img11\"]")).click();
        List<WebElement> list6 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement6 : list6) {
            if (webElement6.getText().contains(totalToDate)) {
                webElement6.click();
                break;
            }
        }

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img12\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(startDate)) {
                webElement3.click();
                break;
            }
        }

    }


    @Given("^Validate the Records Returned when Max Limit is EQUALS TO with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Records_Returned_when_Max_Limit_is_EQUALS_TO_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String applytoAgent, String frequency, String amount, String maxlimit2, String totaltoDate, String startDate) throws SQLException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //   driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Records Returned when Max Limit is EQUALS TO Total to Date: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applytoAgent + "'" +
                "   and [AGENT_ADJUST_FREQ] = '" + frequency + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'" +
                "   and [AGENT_ADJUST_STATUS] = 'complete'" +
                "   and [AGENT_ADJUST_TOP_LIMIT] = '" + maxlimit2 + "'" +
                "   and [AGENT_ADJUST_TOTAL_TO_DATE] = '" + totaltoDate + "'" +
                "   and [AGENT_ADJUST_START_DATE] = '" + startDate + "'";


        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable4 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable5 = new ArrayList<>();

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

            String a = res.getString(14);
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("AGENT CODE : " + a);
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

            String aa = res.getString(4);
            dbAgentAdjustmentsTable1.add(aa);
            System.out.println("STATUS : " + aa);

         /*   boolean booleanValue1 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aa)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable1) {
                        if (dbAAT1.contains(aa)) {
                            System.out.println("STATUS : " + aa);
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
            }  */

            String aab = res.getString(5);
            dbAgentAdjustmentsTable2.add(aab);

            boolean booleanValue2 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aab)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable2) {
                        if (dbAAT1.contains(aab)) {
                            System.out.println("FREQUENCY : " + aab);
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
            //  System.out.println("FREQUENCY : " + aab);
            String aba = res.getString(8);
            dbAgentAdjustmentsTable3.add(aba);

            boolean booleanValue3 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aba)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable3) {
                        if (dbAAT1.contains(aba)) {
                            System.out.println("MAX LIMIT : " + aba);
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

            //   System.out.println("MAX LIMIT : " + aba);
            String abba = res.getString(9);
            dbAgentAdjustmentsTable4.add(abba);
            //   System.out.println("TOTAL TO DATE : " + abba);

            boolean booleanValue4 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(abba)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable4) {
                        if (dbAAT1.contains(abba)) {
                            System.out.println("TOTAL TO DATE : " + abba);
                        }
                        booleanValue4 = true;
                        break;
                    }
                }
                if (booleanValue4) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }

        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Select the First Edit Button on Action Column on Include Complete again$")
    public void select_the_First_Edit_Button_on_Action_Column_on_Include_Complete_again() throws InterruptedException {
        Thread.sleep(8000);
        driver.findElement(By.linkText("EDIT")).click();
    }

    @Given("^Change the Max Limit to a Value Greater Than > current Total to Date, Select the Save button \"([^\"]*)\" \"([^\"]*)\"$")
    public void change_the_Max_Limit_to_a_Value_Greater_Than_current_Total_to_Date_Select_the_Save_button(String maxLimit3, String status) throws InterruptedException {
        Thread.sleep(2000);
        Actions act = new Actions(driver);
        WebElement ele = driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]"));
        act.doubleClick(ele).perform();
        driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]")).sendKeys(maxLimit3);
        driver.findElement(By.xpath("//*[@id=\"txtMaxLimit\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"Status_B-1Img\"]")).click();
        //html/body/section/form/div/div/div[3]/div[1]/div[2]/div[2]/div/div/div/table/tbody/tr/td/div/div/table[2]/tr[2]/td
        List<WebElement> list = driver.findElements(By.xpath("//html/body/section/form/div/div/div[3]/div[1]/div[2]/div[2]/div/div/div/table/tbody/tr/td/div/div/table[2]/tr[2]/td"));  //*[@id="Status_DDD_PW-1"] //*[@id="Status_DDD_L_LBI20T0"]
        for (WebElement webElement : list) {
            System.out.println(webElement.getText());
            Thread.sleep(2000);
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }

        Thread.sleep(3000);
       // driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();

        driver.findElement(agentSettlementAdjustmentsPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
    }


    @Given("^Validate the Records Returned when Max Limit is GREATER THAN with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_the_Records_Returned_when_Max_Limit_is_GREATER_THAN_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String applytoAgent, String frequency, String amount, String maxlimit3, String totaltoDate, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(10000);
        System.out.println("Records Returned when Max Limit is GREATER THAN Total to Date: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applytoAgent + "'" +
                "   and [AGENT_ADJUST_FREQ] = '" + frequency + "'" +
                "   and [AGENT_ADJUST_AMOUNT] = '" + amount + "'" +
                //  "   and [AGENT_ADJUST_STATUS] = 'ACTIVE'" +
                "   and [AGENT_ADJUST_TOP_LIMIT] = '" + maxlimit3 + "'" +
                "   and [AGENT_ADJUST_TOTAL_TO_DATE] = '" + totaltoDate + "'" +
                "   and [AGENT_ADJUST_START_DATE] = '" + startDate + "'";


        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable4 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable5 = new ArrayList<>();

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

            String a = res.getString(14);
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("AGENT CODE : " + a);
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

            String aa = res.getString(4);
            dbAgentAdjustmentsTable1.add(aa);
            System.out.println("STATUS : " + aa);


            String aab = res.getString(5);
            dbAgentAdjustmentsTable2.add(aab);

            boolean booleanValue2 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aab)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable2) {
                        if (dbAAT1.contains(aab)) {
                            System.out.println("FREQUENCY : " + aab);
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

            String aba = res.getString(8);
            dbAgentAdjustmentsTable3.add(aba);
            //   System.out.println("MAX LIMIT : " + aba);

            boolean booleanValue3 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aba)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable3) {
                        if (dbAAT1.contains(aba)) {
                            System.out.println("MAX LIMIT : " + aba);
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

            String abba = res.getString(9);
            dbAgentAdjustmentsTable4.add(abba);
            //   System.out.println("TOTAL TO DATE : " + abba);

            boolean booleanValue4 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(abba)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable4) {
                        if (dbAAT1.contains(abba)) {
                            System.out.println("TOTAL TO DATE : " + abba);
                        }
                        booleanValue4 = true;
                        break;
                    }
                }
                if (booleanValue4) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");

    }


    //............................................/ 37 @AgentSettlementAdjustmentsVendorCodePayCodeOrderNo/................................................//

    @Given("Enter Vendor Code {string}, Start Date From {string}, Start Date To {string} and Click on Search Button")
    public void enter_vendor_code_start_date_from_start_date_to_and_click_on_search_button(String vendorCode, String startDateFrom, String startDateTo) throws InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.VendorCode).sendKeys(vendorCode);
        driver.findElement(agentSettlementAdjustmentsPage.VendorCode).click();
        Thread.sleep(2000);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateFrom).sendKeys(startDateFrom);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateFrom).click();
        Thread.sleep(2000);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateTo).sendKeys(startDateTo);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateTo).click();
        Thread.sleep(2000);
    }

    @Given("^Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String startDateFrom, String startDateTo) throws Throwable {

        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_RECURRING_ONLY_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {

        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(8000);
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String startDateFrom, String startDateTo) throws Throwable {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_DE_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and  [AGENT_ADJUST_STATUS] <> 'COMPLETE'\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Enter Vendor Code, Pay Code \"([^\"]*)\", Start Date From, Start Date To and Click on Search Button$")
    public void enter_Vendor_Code_Pay_Code_start_date_from_start_date_to_and_Click_on_Search_Button(String payCode) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"PayCodeSearch_I\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"PayCodeSearch_I\"]")).sendKeys(payCode);
        driver.findElement(By.xpath("//*[@id=\"PayCodeSearch_I\"]")).click();
    }

    @Given("^Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_RECURRING_ONLY_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_DE_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and  [AGENT_ADJUST_STATUS] <> 'COMPLETE'\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Enter Vendor Code, Pay Code and Order Number \"([^\"]*)\", Start Date From, Start Date To and Click on Search Button$")
    public void enter_Vendor_Code_Pay_Code_and_Order_Number_start_date_from_start_date_to_and_Click_on_Search_Button(String orderNo) throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"txtorderNumber\"]")).sendKeys(orderNo);
        driver.findElement(By.xpath("//*[@id=\"txtorderNumber\"]")).click();
    }

    @Given("^Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_RECURRING_ONLY_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_DE_SELECTED_Validate_the_Records_Returned_with_Database_Record_and(String environment, String tableName, String vendorCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: VENDOR CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Vendorcode VarChar (10)   = '" + vendorCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Vendorcode [Vendor Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_VENDOR_CODE] = @Vendorcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and  [AGENT_ADJUST_STATUS] <> 'COMPLETE'\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    //........................................./  #38 @AgentSettlementAdjustmentsAgentCodePayCodeOrderNo /.........................................//

    @Given("Enter Agent Code {string}, Start Date From {string}, Start Date To {string} and Click on Search Button")
    public void enter_agent_code_start_date_from_start_date_to_and_click_on_search_button(String agentCode, String startDateFrom, String startDateTo) throws InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.AgentCode).sendKeys(agentCode);
        driver.findElement(agentSettlementAdjustmentsPage.AgentCode).click();
        Thread.sleep(2000);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateFrom).sendKeys(startDateFrom);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateFrom).click();

        driver.findElement(agentSettlementAdjustmentsPage.StartDateTo).sendKeys(startDateTo);
        driver.findElement(agentSettlementAdjustmentsPage.StartDateTo).click();
    }

    @Given("^Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String startDateFrom, String startDateTo) throws Throwable {

        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode\n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_RECURRING_ONLY_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {

        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(8000);
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "       Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String startDateFrom, String startDateTo) throws Throwable {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_DE_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and  [AGENT_ADJUST_STATUS] <> 'COMPLETE'\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Enter Agent Code, Pay Code \"([^\"]*)\", Start Date From, Start Date To and Click on Search Button$")
    public void enter_Agent_Code_Pay_Code_start_date_from_start_date_to_and_Click_on_Search_Button(String payCode) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"PayCodeSearch_I\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"PayCodeSearch_I\"]")).sendKeys(payCode);
        driver.findElement(By.xpath("//*[@id=\"PayCodeSearch_I\"]")).click();
    }

    @Given("^Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("Get Records when RECURRING ONLY is SELECTED, Validate Records Returned with Database Record {string} {string} and Agent Code {string} {string} {string} {string}")
    public void get_records_when_recurring_only_is_selected_validate_records_returned_with_database_record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record {string} {string} and Agent Code {string} {string} {string} {string}")
    public void get_records_when_include_complete_and_recurring_only_both_are_selected_validate_the_records_returned_with_database_record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_DE_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode  \n" +
                "       and  [AGENT_ADJUST_STATUS] <> 'COMPLETE'\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str1 = rs1.getString(23);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("AGENT CODE = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    @Given("^Enter Agent Code, Pay Code and Order Number \"([^\"]*)\", Start Date From, Start Date To and Click on Search Button$")
    public void enter_Agent_Code_Pay_Code_and_Order_Number_start_date_from_start_date_to_and_Click_on_Search_Button(String orderNo) throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"txtorderNumber\"]")).sendKeys(orderNo);
        driver.findElement(By.xpath("//*[@id=\"txtorderNumber\"]")).click();
    }

    @Given("^Get Records when INCLUDE COMPLETE is SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_is_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str3 = rs1.getString(23);
            dbAgentAdjustmentsTable3.add(str3);
            System.out.println("AGENT CODE = " + str3);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("Get Records when RECURRING ONLY is SELECTED, Validate the Records Returned with Database Record {string} {string} and Agent Code {string} {string} {string} {string} {string}")
    public void get_records_when_recurring_only_is_selected_validate_the_records_returned_with_database_record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when RECURRING ONLY is selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str3 = rs1.getString(23);
            dbAgentAdjustmentsTable3.add(str3);
            System.out.println("AGENT CODE = " + str3);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("Get Records when INCLUDE COMPLETE and RECURRING ONLY both are SELECTED, Validate the Records Returned with Database Record {string} {string} and Agent Code {string} {string} {string} {string} {string}")
    public void get_records_when_include_complete_and_recurring_only_both_are_selected_validate_the_records_returned_with_database_record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and AGENT_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str3 = rs1.getString(23);
            dbAgentAdjustmentsTable3.add(str3);
            System.out.println("AGENT CODE = " + str3);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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

    @Given("^Get Records when INCLUDE COMPLETE and RECURRING ONLY both are DE-SELECTED, Validate the Records Returned with Database Record \"([^\"]*)\" \"([^\"]*)\" and Agent Code \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void get_Records_when_INCLUDE_COMPLETE_and_RECURRING_ONLY_both_are_DE_SELECTED_Validate_the_Records_Returned_with_Database_Record_and_agent_code(String environment, String tableName, String agentCode, String payCode, String orderNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).isDisplayed();
        System.out.println(driver.findElement(agentSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody")).getText());

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        System.out.println(" ** SCENARIO: AGENT CODE, PAY CODE, ORDER NUMBER, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("Records Returned when INCLUDE COMPLETE and RECURRING ONLY both are De-selected : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "    Declare @CompanyCode Varchar (3)   = 'EVA' \n" +
                "           Declare @Agentcode VarChar (10)   = '" + agentCode + "' \n" +
                "           Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "           Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "           Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "           Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "           Select @CompanyCode [Company Code], @Agentcode [Agent Code], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @OrderNo [Order No];\n" +
                "           With VNDCTE (VNDCODE) \n" +
                "           AS (\n" +
                "           Select Distinct [AGENT_ADJUST_VENDOR_CODE]\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode )\n" +
                "       SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_END_DATE]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_NOTE]\n" +
                "      ,[AGENT_ADJUST_CREATED_BY]\n" +
                "      ,[AGENT_ADJUST_CREATED_DATE]\n" +
                "      ,[AGENT_ADJUST_UPDATED_BY]\n" +
                "      ,[AGENT_ADJUST_LAST_UPDATED]\n" +
                "      ,[AGENT_ADJUST_IS_DELETED]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_COMP_CODE]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "       FROM [EBHLaunch].[dbo].[AGENT_ADJUSTMENTS]\n" +
                "           Where [AGENT_ADJUST_APPLY_TO_AGENT] = @Agentcode \n" +
                "       and AGENT_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and  [AGENT_ADJUST_STATUS] <> 'COMPLETE'\n" +
                "       and AGENT_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       and AGENT_ADJUST_PAY_CODE = @PayCode\n" +
                "       and AGENT_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY AGENT_ADJUST_ID";

        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Contents of the first result-set: ");
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        while (rs.next()) {
            int rows = rs.getRow();
            System.out.println("Number of Rows:" + rows);
            System.out.println(rs.getString(1) +
                    "\t" + rs.getString(2) +
                    "\t" + rs.getString(3) +
                    "\t" + rs.getString(4));
            System.out.println();
        }

        stmt.getMoreResults();
        System.out.println("Contents of the second result-set: ");
        ResultSet rs1 = stmt.getResultSet();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<WebElement> uiAgentAdjustmentsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable2 = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable3 = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows:" + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String str = rs1.getString(1);
            dbAgentAdjustmentsTable.add(str);
            System.out.println("ADJUSTMENT ID = " + str);

            String str3 = rs1.getString(23);
            dbAgentAdjustmentsTable3.add(str3);
            System.out.println("AGENT CODE = " + str3);

            String str1 = rs1.getString(25);
            dbAgentAdjustmentsTable1.add(str1);
            System.out.println("ORDER NUMBER = " + str1);

            String str2 = rs1.getString(3);
            dbAgentAdjustmentsTable2.add(str2);

            boolean booleanValue = false;
            for (WebElement aA : uiAgentAdjustmentsTable) {
                if (aA.getText().contains(str2)) {
                    for (String dbAAT : dbAgentAdjustmentsTable2) {
                        if (dbAAT.contains(str2)) {
                            System.out.println("PAY CODE = " + str2);
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


    //...................................../  #39 @ScenarioViewButton /.......................................................//


    @Given("^Select the First View Button on Action Column$")
    public void select_the_First_View_Button_on_Action_Column() {
        driver.findElement(By.linkText("VIEW")).click();
    }

    @Given("^Confirm the company code, vendor code, all locations and vendor name are displayed in header in blue font$")
    public void confirm_the_company_code_vendor_code_all_locations_and_vendor_name_are_displayed_in_header_in_blue_font() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");

        System.out.println(driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div/div[1]/div/h4")).getText());
        driver.findElement(By.xpath("//*[@id=\"lblVendorcode\"]")).isDisplayed();
        System.out.println("Company Code, Vendor Code, All Locations and Vendor Name : " + driver.findElement(By.xpath("//*[@id=\"lblVendorcode\"]")).getText());
        String expected = driver.findElement(By.xpath("//*[@id=\"lblVendorcode\"]")).getText();
        String code = driver.findElement(By.xpath("//*[@id=\"lblVendorcode\"]")).getText();

        assertEquals("Both Actual and Expected are Same!!", code, expected);
        System.out.println("Both Actual and Expected are Same!!");
        System.out.println(code);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblVendorcode\"]")).getText());
        System.out.println("=========================================");
    }

    @Given("^Select the 'X' on form$")
    public void select_the_X_on_form() {
        driver.findElement(By.xpath("//*[@id=\"crossIconPopup\"]")).click();
    }


    //...................................../ #40 @ScenarioDeleteButton /.......................................................//

    @Given("^Click on Status, Inactive \"([^\"]*)\"$")
    public void Click_on_Status_Inactive(String inactive) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"img6\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class,'dropdown-menu datatable-filter-list')]/p"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(inactive)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]")).getText());
    }


    @Given("^Validate the Records Returned for Delete with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_Records_Returned_for_Delete_with_Database_Record(String environment, String tableName, String vendorCode, String status, String applyToAgent, String payCode) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_STATUS] = '" + status + "'" +
                "   and [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applyToAgent + "'" +
                "   and [AGENT_ADJUST_PAY_CODE] = '" + payCode + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody/tr[1]"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();

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
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("PAY CODE : " + a);
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

            String aa = res.getString(4);
            dbAgentAdjustmentsTable1.add(aa);

            boolean booleanValue1 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aa)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable1) {
                        if (dbAAT1.contains(aa)) {
                            System.out.println("STATUS : " + aa);
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
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("^Select the First Delete Button on Action Column$")
    public void select_the_First_Delete_Button_on_Action_Column() {
        driver.findElement(By.linkText("DEL")).click();
        driver.findElement(By.xpath("//*[@id=\"Divpopup\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"btnYesAction\"]")).click();
    }


    @Given("^Validate the Records Returned for Delete on Hold with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Validate_the_Records_Returned_for_Delete_on_Hold_with_Database_Record(String environment, String tableName, String vendorCode, String status, String applyToAgent) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT TOP 1000 [AGENT_ADJUST_ID]\n" +
                "      ,[AGENT_ADJUST_VENDOR_CODE]\n" +
                "      ,[AGENT_ADJUST_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_STATUS]\n" +
                "      ,[AGENT_ADJUST_FREQ]\n" +
                "      ,[AGENT_ADJUST_AMOUNT_TYPE]\n" +
                "      ,[AGENT_ADJUST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_TOP_LIMIT]\n" +
                "      ,[AGENT_ADJUST_TOTAL_TO_DATE]\n" +
                "      ,[AGENT_ADJUST_MAX_TRANS]\n" +
                "      ,[AGENT_ADJUST_START_DATE]\n" +
                "      ,[AGENT_ADJUST_LAST_AMOUNT]\n" +
                "      ,[AGENT_ADJUST_PAY_VENDOR_PAY_CODE]\n" +
                "      ,[AGENT_ADJUST_APPLY_TO_AGENT]\n" +
                "      ,[AGENT_ADJUST_ORDER_NO]\n" +
                "   FROM " + tableName + " with (nolock)\n" +
                "   where [AGENT_ADJUST_STATUS] = '" + status + "'" +
                "   and [AGENT_ADJUST_VENDOR_CODE] = '" + vendorCode + "'" +
                "   and [AGENT_ADJUST_APPLY_TO_AGENT] = '" + applyToAgent + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> agentAdjustmentsAs = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbAgentAdjustmentsTable = new ArrayList<>();
        List<String> dbAgentAdjustmentsTable1 = new ArrayList<>();

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

            String a = res.getString(2);
            dbAgentAdjustmentsTable.add(a);

            boolean booleanValue = false;
            for (WebElement aA : agentAdjustmentsAs) {
                if (aA.getText().contains(a)) {
                    for (String dbAAT : dbAgentAdjustmentsTable) {
                        if (dbAAT.contains(a)) {
                            System.out.println("VENDOR CODE : " + a);
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

            String aa = res.getString(4);
            dbAgentAdjustmentsTable1.add(aa);

            boolean booleanValue1 = false;
            for (WebElement aA1 : agentAdjustmentsAs) {
                if (aA1.getText().contains(aa)) {
                    for (String dbAAT1 : dbAgentAdjustmentsTable1) {
                        if (dbAAT1.contains(aa)) {
                            System.out.println("STATUS : " + aa);
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
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //........................................./ #41 @ScenarioReport /.................................................//

    @Given("^Select the Report button$")
    public void select_the_Report_button() throws InterruptedException {
        Thread.sleep(1000);
        //  driver.findElement(agentSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(agentSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
        //   Thread.sleep(2000);
        driver.findElement(agentSettlementAdjustmentsPage.Report).click();
        //  driver.findElement(agentSettlementAdjustmentsPage.PopUpReport).isDisplayed();
        //  driver.findElement(agentSettlementAdjustmentsPage.SearchResults).click();
        Thread.sleep(8000);
    }

    @And("^Get Excel Report from Downloads$")
    public void get_Excel_Report_from_Downloads() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(5000);
        String fileNameAllAdjustments = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
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


    @And("^Validate Excel Report with Database Record \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Excel_Report_with_Database_Record(String environment, String tableName, String vendorCode, String tableName1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "    SELECT * FROM " + tableName + " " +
                "           where AGENT_ADJUST_VENDOR_CODE = '" + vendorCode + "'" +
                "           and agent_adjust_status <> 'COMPLETE'" +
                "           ORDER BY AGENT_ADJUST_APPLY_TO_AGENT," +
                "           AGENT_ADJUST_PAY_CODE";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAgentAdjustmentsTable = new ArrayList<>();

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
                    "\t" + res.getString(15) +
                    "\t" + res.getString(16) +
                    "\t" + res.getString(17) +
                    "\t" + res.getString(18) +
                    "\t" + res.getString(19) +
                    "\t" + res.getString(20) +
                    "\t" + res.getString(21) +
                    "\t" + res.getString(22) +
                    "\t" + res.getString(23) +
                    "\t" + res.getString(24) +
                    "\t" + res.getString(25));

            String a = res.getString(2);
            dbAgentAdjustmentsTable.add(a);
        }

        System.out.println("Database Closed ......");
        System.out.println("=========================================");
        //  System.out.println("Excel Report ......");
        String query1 = "{call " + tableName1 + " (?,?,?,?,?,?)}";
        CallableStatement cstmt = connectionToDatabase.prepareCall(query1);

        cstmt.setString(1, "" + vendorCode + "");
        cstmt.setString(2, "");
        cstmt.setString(3, "");
        cstmt.setString(4, "0");
        cstmt.setString(5, "0");
        cstmt.setString(6, "0");

        ResultSet rs1 = cstmt.executeQuery();
        ResultSetMetaData rsmd1 = rs1.getMetaData();
        int count1 = rsmd1.getColumnCount();
        List<String> columnList1 = new ArrayList<String>();
        for (int i = 1; i <= count1; i++) {
            columnList1.add(rsmd1.getColumnLabel(i));
        }
        System.out.println(columnList1);

        List<String> excelSheet = new ArrayList<>();

        while (rs1.next()) {
            int rows1 = rs1.getRow();
            System.out.println("Number of Rows: " + rows1);
            System.out.println(rs1.getString(1) +
                    "\t" + rs1.getString(2) +
                    "\t" + rs1.getString(3) +
                    "\t" + rs1.getString(4) +
                    "\t" + rs1.getString(5) +
                    "\t" + rs1.getString(6) +
                    "\t" + rs1.getString(7) +
                    "\t" + rs1.getString(8) +
                    "\t" + rs1.getString(9) +
                    "\t" + rs1.getString(10) +
                    "\t" + rs1.getString(11) +
                    "\t" + rs1.getString(12) +
                    "\t" + rs1.getString(13) +
                    "\t" + rs1.getString(14) +
                    "\t" + rs1.getString(15) +
                    "\t" + rs1.getString(16) +
                    "\t" + rs1.getString(17) +
                    "\t" + rs1.getString(18) +
                    "\t" + rs1.getString(19) +
                    "\t" + rs1.getString(20) +
                    "\t" + rs1.getString(21) +
                    "\t" + rs1.getString(22) +
                    "\t" + rs1.getString(23) +
                    "\t" + rs1.getString(24) +
                    "\t" + rs1.getString(25));

            String b = rs1.getString(2);
            excelSheet.add(b);


            boolean booleanValue2 = false;
            for (String aat : dbAgentAdjustmentsTable) {
                if (aat.contains(b)) {
                    for (String es : excelSheet) {
                        if (es.contains(b)) {
                            booleanValue2 = true;
                            break;
                        }
                    }
                }
                if (booleanValue2) {
                    assertTrue("Assertion Passed !!", true);
                } else {
                    fail("Assertion Failed!!");
                }
            }
            System.out.println("VENDOR CODE: " + b);
        }
        System.out.println("Excel Report Closed ......");
        System.out.println("=========================================");
    }

    @Then("^Close all open Browsers for Agent Settlement Adjustments$")
    public void Close_all_open_Browsers_for_Agent_Settlement_Adjustments() throws InterruptedException {
        getAndSwitchToWindowHandles();
        Thread.sleep(3000);
        driver.close();
        driver.quit();
    }


    //...................................................../ #42 @ScenarioQuickEntry /......................................................//

    @And("^Select Quick Entry$")
    public void Select_Quick_Entry() throws InterruptedException {
        Thread.sleep(6000);
        driver.findElement(By.linkText("Quick Entry")).click();
        System.out.println("=========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"mainForm\"]/div/div/div/div[1]/div[1]/h4")).getText());
        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblVendorCodedeQuick\"]")).getText());
        System.out.println("=========================================");
    }

    @And("^Enter the required fields \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Enter_the_required_fields(String payCode, String amount, String applytoAgent, String startDate, String orderNo, String notes) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"dropPayCode0\"]")).sendKeys(payCode);
        driver.findElement(By.xpath("//*[@id=\"dropPayCode0\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"txtAmount0\"]")).sendKeys(amount);
        driver.findElement(By.xpath("//*[@id=\"txtAmount0\"]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"dropSpplyToAgent0\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"dropSpplyToAgent0\"]"));
        for (WebElement webElement : list) {
            System.out.println(webElement.getText());
            if (webElement.getText().contains(applytoAgent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"startDate0\"]")).sendKeys(startDate);
        driver.findElement(By.xpath("//*[@id=\"startDate0\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"orderNumberTd0\"]")).sendKeys(orderNo);
        driver.findElement(By.xpath("//*[@id=\"orderNumberTd0\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"areaNotes0\"]")).sendKeys(notes);
        driver.findElement(By.xpath("//*[@id=\"areaNotes0\"]")).click();
        Thread.sleep(2000);

    }

    @And("^Select Save, Click Yes on Conformation$")
    public void Select_Save_Click_Yes_on_Conformation() throws InterruptedException {
        driver.findElement(By.linkText("Save")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(3000);
        driver.findElement(By.linkText("Close")).click();
    }

}





































