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
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
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
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


public class TractorSettlementAdjustmentsStepDef {
    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";
    String usernameExpected = "";

    EBHLoginPage ebhlogInPage = new EBHLoginPage(driver);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    SettlementsPage settlementsPage = new SettlementsPage(driver);
    TractorSettlementAdjustmentsPage tractorSettlementAdjustmentsPage = new TractorSettlementAdjustmentsPage(driver);
    Logger log = Logger.getLogger("SettlingStepDef");
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    private Object JavascriptExecutor;


    //...................................../  @Background /..........................................//

    @After("@FailureScreenShot7")
    public void takeScreenshotOnFailure7(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }

    @Given("^Run Test for \"([^\"]*)\" on Browser \"([^\"]*)\" for EBH Tractor Settlement Adjustments$")
    public void Run_Test_for_on_Browser_for_EBH_Tractor_Settlement_Adjustments(String environment, String browser) throws MalformedURLException {
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
    }

    @And("^Enter the url for EBH Tractor Settlement Adjustments$")
    public void Enter_the_url_for_EBH_Tractor_Settlement_Adjustments() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(URL);
    }

    @And("^Login to the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Tractor Settlement Adjustments$")
    public void Login_to_the_Agents_Portal_with_username_and_password_for_EBH_Tractor_Settlement_Adjustments(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhlogInPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(visibilityOfElementLocated(ebhlogInPage.username));
        wait.until(visibilityOfElementLocated(ebhlogInPage.password));
        driver.findElement(ebhlogInPage.password).sendKeys(password);
        wait.until(visibilityOfElementLocated(ebhlogInPage.signinButton));
        driver.findElement(ebhlogInPage.signinButton).click();
    }

    @And("^Navigate to the Corporate Page on Main Menu for Tractor Settlement Adjustments$")
    public void Navigate_to_the_Corporate_Page_on_Main_Menu_for_Tractor_Settlement_Adjustments() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Given("^Navigate to the Settlements page for Tractor Settlement Adjustments$")
    public void Navigate_to_the_Settlements_page_for_Tractor_Settlement_Adjustments() {
        driver.findElement(corporatePage.settlements).click();
    }


    @Then("^Close all open Browsers on EBH for Tractor Settlement Adjustments$")
    public void Close_all_open_Browsers_on_EBH_for_Tractor_Settlement_Adjustments() {
        driver.close();
        driver.quit();
    }


    @And("^Navigate to Tractor Settlement Adjustments$")
    public void Navigate_to_Tractor_Settlement_Adjustments() {
        driver.findElement(settlementsPage.TractorSettlementAdjustments).click();
    }


    //...................................../ #101  @TractorSettlementAdjustmentsUnitNo /..........................................//

    @And("^Enter Unit No as \"([^\"]*)\"$")
    public void Enter_Unit_No_as(String unitNo) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).click();
        Thread.sleep(2000);
    }

    @And("^Validate Company Code, Vendor Code, Vendor Name Description of \"([^\"]*)\"$")
    public void Validate_VendorID_Vendor_Code_Description_of(String unitNo) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("=========================================");
        System.out.println("Company Code, Vendor Code, Vendor Name of Unit No (" + unitNo + ") = " + driver.findElement(By.id("lblCompanyCode")).getText() + "," + driver.findElement(By.id("lblVendorCode")).getText() + "," + driver.findElement(By.id("lblVendorName")).getText());
        Thread.sleep(3000);
        System.out.println("=========================================");
    }


    @Given("Enter Start Date From {string} and Start Date To {string}")
    public void enter_start_date_from_and_start_date_to(String StartDateFrom, String StartDateTo) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFrom).sendKeys(StartDateFrom);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFrom).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateTo).sendKeys(StartDateTo);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateTo).click();
    }

    @Given("DESELECT BOTH RECURRING ONLY and INCLUDE COMPLETE and Validate the Records Returned")
    public void deselect_both_recurring_only_and_include_complete_and_validate_the_records_returned() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorSettlementAdjustmentsPage.DataTable));
        System.out.println("=========================================");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);
    }

    @Given("Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is DESELECTED with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_both_recurring_only_and_include_complete_is_deselected_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @And("^SELECT RECURRING ONLY and Validate the Records Returned$")
    public void select_Recurring_Only_and_Validate_the_Records_Returned() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" RECURRING ONLY : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);
    }

    @Given("Validate the Records Returned when RECURRING ONLY is SELECTED with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_recurring_only_is_selected_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo ))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE and Validate the Records Returned")
    public void select_include_complete_and_validate_the_records_returned() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println("INCLUDE COMPLETE : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);
    }


    @Given("Validate the Records Returned when INCLUDE COMPLETE is SELECTED with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_include_complete_is_selected_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                //   "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                //  "-- and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                //  "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("SELECT BOTH RECURRING ONLY and INCLUDE COMPLETE and Validate the Records Returned")
    public void select_both_recurring_only_and_include_complete_and_validate_the_records_returned() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);
    }


    @Given("Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is SELECTED with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_both_recurring_only_and_include_complete_is_selected_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT EFS and Validate the Records Returned")
    public void select_efs_and_validate_the_records_returned() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(120000);
        System.out.println("=========================================");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);
    }

    @Given("Validate the Records Returned when EFS is SELECTED with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_efs_is_selected_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");


    }


    //................................/ #102 @TractorSettlementAdjustmentsLocation /.......................................//

    @And("^Enter Location as \"([^\"]*)\"$")
    public void Enter_Location_as(String location) {
        driver.findElement(tractorSettlementAdjustmentsPage.Location).sendKeys(location);
        driver.findElement(tractorSettlementAdjustmentsPage.Location).click();
    }

    @And("^Validate Location Name of \"([^\"]*)\"$")
    public void Validate_Location_Name_of(String location) throws InterruptedException {
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println("Location name of Location (" + location + ") : " + driver.findElement(By.id("lblLocationName")).getText());
        Thread.sleep(3000);
        System.out.println("=========================================");
    }

    @Given("Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is DESELECTED for Location with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_both_recurring_only_and_include_complete_is_deselected_for_location_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (10)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

    }


    @Given("Validate the Records Returned when RECURRING ONLY is SELECTED for Location with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_recurring_only_is_selected_for_location_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (10)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("Validate the Records Returned when INCLUDE COMPLETE is SELECTED for Location with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_include_complete_is_selected_for_location_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (10)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("Validate the Records Returned when BOTH RECURRING ONLY and INCLUDE COMPLETE is SELECTED for Location with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_both_recurring_only_and_include_complete_is_selected_for_location_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (10)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("Validate the Records Returned when EFS is SELECTED for Location with Database Record {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_when_efs_is_selected_for_location_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (10)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))\n" +
                "       Select TOP 1000 TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //................................/ #103  @TractorSettlementAdjustmentsScenarioReport /.......................................//


    @Given("Click on Report Button for Tractor Settlement Adjustments with Unit No")
    public void click_on_report_button_for_tractor_settlement_adjustments_with_unit_no() throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.Report).click();
        Thread.sleep(5000);
    }

    @Given("Get Excel Report from Downloads for Tractor Settlement Adjustments with Unit No")
    public void get_excel_report_from_downloads_for_tractor_settlement_adjustments_with_unit_no() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(15000);
        String fileNameTSAR = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("ALL RECORDS Excel Report File Name :-" + fileNameTSAR);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameTSAR + "");
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


    @Given("Validate Excel Report with database Records {string} {string} {string} {string} Unit No {string}")
    public void validate_excel_report_with_database_records_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No];" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo ))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //................................/ #104 @TractorSettlementAdjustmentsScenarioNew /.......................................//

    @Given("Select New on Tractor Settlement Adjustments")
    public void select_new_on_tractor_settlement_adjustments() {
        driver.findElement(tractorSettlementAdjustmentsPage.New).click();
    }

    @Given("Verify Tractor Settlement Adjustment Detail form is Opened")
    public void verify_tractor_settlement_adjustment_detail_form_is_opened() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div[1]/div/h3")).isDisplayed();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div[1]/div/h3")).getText());
    }

    @Given("Insert values in all required fields in New {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void insert_values_in_all_required_fields_in_new(String unitNo, String payCode, String status, String frequency, String amount, String orderNumber, String maxNoOfAdjustments, String maxLimit, String endDate, String payToVendorID, String payToVendorPayCode, String lastActivityAmount, String lastActivityDate, String notes) throws InterruptedException {
        Thread.sleep(2000);
      //  driver.findElement(tractorSettlementAdjustmentsPage.UnitNoNew).sendKeys(unitNo);
     //   driver.findElement(tractorSettlementAdjustmentsPage.UnitNoNew).click();
      //  driver.findElement(By.xpath("//*[@id=\"divnewbutton\"]/div[2]/div/div[1]/div[2]/div[1]/label")).click();

        WebElement wb = driver.findElement(tractorSettlementAdjustmentsPage.UnitNoNew);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].value='" + unitNo + "';", wb);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNoNew).click();
      //  driver.findElement(tractorSettlementAdjustmentsPage.UnitNoNew).click();
        Thread.sleep(8000);

        driver.findElement(tractorSettlementAdjustmentsPage.PayCode).sendKeys(payCode);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCode).click();
        Thread.sleep(2000);

        driver.findElement(tractorSettlementAdjustmentsPage.Status).sendKeys(status);
        driver.findElement(tractorSettlementAdjustmentsPage.Status).click();
        Thread.sleep(4000);

        driver.findElement(tractorSettlementAdjustmentsPage.Frequency).sendKeys(frequency);
        driver.findElement(tractorSettlementAdjustmentsPage.Frequency).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.Amount).sendKeys(amount);
        driver.findElement(tractorSettlementAdjustmentsPage.Amount).click();
        Thread.sleep(2000);
        //  driver.findElement(tractorSettlementAdjustmentsPage.OrderNumber).sendKeys(orderNumber);
        //   driver.findElement(tractorSettlementAdjustmentsPage.OrderNumber).click();
        //   Thread.sleep(2000);
        //     driver.findElement(tractorSettlementAdjustmentsPage.MaxNoOfAdjustments).sendKeys(maxNoOfAdjustments);
        //    driver.findElement(tractorSettlementAdjustmentsPage.MaxNoOfAdjustments).click();
        //    Thread.sleep(2000);
        //  driver.findElement(tractorSettlementAdjustmentsPage.MaxLimit).sendKeys(maxLimit);
        //   driver.findElement(tractorSettlementAdjustmentsPage.MaxLimit).click();
        // Thread.sleep(2000);
        //  driver.findElement(tractorSettlementAdjustmentsPage.TotalToDate).sendKeys(totalToDate);
        //   driver.findElement(tractorSettlementAdjustmentsPage.TotalToDate).click();
        //  Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.Enddate).sendKeys(endDate);
        driver.findElement(tractorSettlementAdjustmentsPage.Enddate).click();
        Thread.sleep(2000);

        //    driver.findElement(tractorSettlementAdjustmentsPage.PayToVendorID).sendKeys(payToVendorID);
        //    driver.findElement(tractorSettlementAdjustmentsPage.PayToVendorID).click();
        //  Thread.sleep(2000);
        //   driver.findElement(tractorSettlementAdjustmentsPage.PayToVendorPayCode).sendKeys(payToVendorPayCode);
        //   driver.findElement(tractorSettlementAdjustmentsPage.PayToVendorPayCode).click();
        //  Thread.sleep(2000);
        //  driver.findElement(tractorSettlementAdjustmentsPage.LastActivityAmount).sendKeys(lastActivityAmount);
        //  driver.findElement(tractorSettlementAdjustmentsPage.LastActivityAmount).click();
        //  Thread.sleep(2000);
        //  driver.findElement(tractorSettlementAdjustmentsPage.LastActivityDate).sendKeys(lastActivityDate);
        //  driver.findElement(tractorSettlementAdjustmentsPage.LastActivityDate).click();
        //  Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.Notes).sendKeys(notes);
        driver.findElement(tractorSettlementAdjustmentsPage.Notes).click();
        Thread.sleep(2000);
    }

    @Given("Click on Save on New")
    public void click_on_save_on_new() throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.Save).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Ok).click();
        Thread.sleep(4000);
    }

    @Given("Get the Newly Created New Record on UI {string} {string} {string} {string} {string} {string} {string}")
    public void get_the_newly_created_new_record_on_ui_paycode_start_date_from(String unitNo, String payCode, String status, String frequency, String amount, String startDateFrom, String startDateTo) throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFrom).sendKeys(startDateFrom);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFrom).click();
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateTo).sendKeys(startDateTo);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateTo).click();
        Thread.sleep(1000);
      //  driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);


        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[8]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);


        driver.findElement(By.xpath("//*[@id=\"img8\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[9]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(frequency)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();  //*[@id="img10"]
        List<WebElement> list4 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(6000);
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        System.out.println("=========================================");
        System.out.println("NEWLY CREATED RECORD : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(3000);
    }


    @Given("Validate the Newly Created Record with Database Records {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void validate_the_newly_created_record_with_database_records(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String status, String frequency, String amount, String startDateFrom, String startDateTo) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "\t     TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "\t     TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "\t     TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       AND [TRACTOR_ADJUST_STATUS] = '" + status + "'\n" +
                "       AND [TRACTOR_ADJUST_FREQ] = '" + frequency + "'\n" +
                "       AND [TRACTOR_ADJUST_AMOUNT] = '" + amount + "'\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                //  "       AND [TRACTOR_ADJUST_START_DATE] = @Startdate" +
                //  "       AND [TRACTOR_ADJUST_LAST_DATE] = @EndDate" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //................................/ #105 @TractorSettlementAdjustmentsScenarioQuickEntry /.......................................//

    @Given("Select Quick Entry on Tractor Settlement Adjustments")
    public void select_quick_entry_on_tractor_settlement_adjustments() {
        driver.findElement(tractorSettlementAdjustmentsPage.QuickEntry).click();
    }

    @Given("Insert values in all required fields in Quick Entry {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void insert_values_in_all_required_fields_in_Quick_Entry(String unitNo, String payCode, String frequency, String amount, String startDateFrom, String maxLimit, String maxNoOfAdjustments, String orderNumber, String notes, String splits) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNoQuickEntry).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNoQuickEntry).click();
        Thread.sleep(5000);

        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeQuickEntry).sendKeys(payCode);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeQuickEntry).click();
        Thread.sleep(2000);

        //   driver.findElement(tractorSettlementAdjustmentsPage.FrequencyQuickEntry).sendKeys(frequency);
        //  driver.findElement(tractorSettlementAdjustmentsPage.FrequencyQuickEntry).click();

        driver.findElement(tractorSettlementAdjustmentsPage.AmountQuickEntry).sendKeys(amount);
        driver.findElement(tractorSettlementAdjustmentsPage.AmountQuickEntry).click();
        Thread.sleep(2000);

        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFromQuickEntry).clear();
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFromQuickEntry).click();
        //  Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFromQuickEntry).sendKeys(startDateFrom);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFromQuickEntry).click();
        Thread.sleep(2000);


        //  driver.findElement(tractorSettlementAdjustmentsPage.MaxLimitQuickEntry).sendKeys(maxLimit);
        //  driver.findElement(tractorSettlementAdjustmentsPage.MaxLimitQuickEntry).click();

        //   driver.findElement(tractorSettlementAdjustmentsPage.MaxNoOfAdjustmentsQuickEntry).sendKeys(maxNoOfAdjustments);
        //   driver.findElement(tractorSettlementAdjustmentsPage.MaxNoOfAdjustmentsQuickEntry).click();

        //    driver.findElement(tractorSettlementAdjustmentsPage.OrderNumberQuickEntry).sendKeys(orderNumber);
        //    driver.findElement(tractorSettlementAdjustmentsPage.OrderNumberQuickEntry).click();

        driver.findElement(tractorSettlementAdjustmentsPage.NotesQuickEntry).sendKeys(notes);
        driver.findElement(tractorSettlementAdjustmentsPage.NotesQuickEntry).click();
        Thread.sleep(2000);
        //   driver.findElement(tractorSettlementAdjustmentsPage.SplitsQuickEntry).sendKeys(splits);
        //  driver.findElement(tractorSettlementAdjustmentsPage.SplitsQuickEntry).click();
    }

    @Given("Click on Save on Quick Entry")
    public void click_on_save_on_Quick_Entry() throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.Save).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Ok).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.CloseQuickEntry).click();
        Thread.sleep(10000);
    }

    @Given("Get the Newly Created Quick Entry Record on UI {string} {string} {string} {string} {string} {string}")
    public void get_the_newly_created_quick_entry_record_on_ui(String unitNo, String payCode, String status, String frequency, String amount, String startDateFrom) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);


        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[8]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"img15\"]")).click();  //*[@id="img15"]
        List<WebElement> list5 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[16]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list5) {
            Thread.sleep(3000);
            if (webElement.getText().contains(startDateFrom)) {
                Thread.sleep(8000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"img8\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[9]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(frequency)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(amount)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);


        System.out.println("=========================================");
        System.out.println("NEWLY CREATED RECORD : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(3000);
    }

    @Given("Validate the Newly Created Record with Database Records {string} {string} {string} {string} Unit No {string} {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void validate_the_newly_created_record_with_database_records_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String status, String frequency, String amount, String startDateFrom, String string9, String string10, String string11, String string12, String string13) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'                                 \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       AND [TRACTOR_ADJUST_STATUS] = '" + status + "'\n" +
                "       AND [TRACTOR_ADJUST_FREQ] = '" + frequency + "'\n" +
                "       AND [TRACTOR_ADJUST_AMOUNT] = '" + amount + "'\n" +
                "       and TRACTOR_ADJUST_START_DATE = '" + startDateFrom + "' \n" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs.getString(26) +
                    "\t" + rs.getString(27));

            String a = rs.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String e = rs.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //................................/ #106  @TractorSettlementAdjustmentsScenarioQuickEdit /.......................................//
    @Given("Select Quick Edit on Tractor Settlement Adjustments")
    public void select_quick_edit_on_tractor_settlement_adjustments() {
        driver.findElement(tractorSettlementAdjustmentsPage.QuickEdit).click();
    }


    @Given("Enter Unit Number {string}, Pay Code {string} and click on Search")
    public void enter_unit_number_pay_code_pay_code_and_click_on_search(String unitNo, String paycode) throws InterruptedException {
        Thread.sleep(4000);
        WebElement unitNum = driver.findElement(By.xpath("//*[@id=\"UnitNoQuickEditPartial_I\"]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value='" + unitNo + "';", unitNum);
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeQuickEdit).sendKeys(paycode);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeQuickEdit).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.SearchQuickEdit).click();
        Thread.sleep(8000);
    }

    @Given("Get the Total Records Returned on Tractor Settlement Adjustments Quick Edit Table")
    public void get_the_total_records_returned_on_tractor_settlement_adjustments_quick_edit_table() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println("TOTAL RECORDS RETURNED ON QUICK EDIT: " + driver.findElement(By.xpath("//*[@id=\"lblRowCount1\"]")).getText());
        log.info(driver.findElement(By.xpath("//*[@id=\"tblQuickEdit1\"]")).getText());
        Thread.sleep(3000);
    }

    @Given("Validate the Records Returned on Tractor Settlement Adjustments Quick Edit Table with Database Record {string} {string} {string} {string} and {string} {string} {string}")
    public void validate_the_records_returned_on_tractor_settlement_adjustments_quick_edit_table_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String adjustmentID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_PAY_CODE [Pay Code], TRACTOR_ADJUST_START_DATE [Start Date], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_ORDER_NO [Order No],\n" +
                "       TRACTOR_ADJUST_CREATED_BY [Created By], TRACTOR_ADJUST_ID [Adj. ID]\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'                                 \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       and TRACTOR_ADJUST_ID = '" + adjustmentID + "'" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"tblQuickEdit1\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs.getString(8));

            String a = rs.getString(2);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(3);
            dbTATable1.add(b);
            System.out.println("PayCode : " + b);
            String e = rs.getString(4);
            dbTATable4.add(e);
            System.out.println("Start Date : " + e);
            String c = rs.getString(5);
            dbTATable2.add(c);
            System.out.println("Amount : " + c);
            String d = rs.getString(8);
            dbTATable3.add(d);
            System.out.println("Adjustment ID : " + d);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("Change Start date {string} and Amount {string} on Quick Edit")
    public void change_start_date_and_amount_on_quick_edit(String startDateEdited, String amountEdited) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateQuickEdit).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateQuickEdit).clear();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateQuickEdit).sendKeys(startDateEdited);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateQuickEdit).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.AmountQuickEdit).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.AmountQuickEdit).clear();
        driver.findElement(tractorSettlementAdjustmentsPage.AmountQuickEdit).sendKeys(amountEdited);
        driver.findElement(tractorSettlementAdjustmentsPage.AmountQuickEdit).click();
        Thread.sleep(2000);
    }

    @Given("Click on Save")
    public void click_on_save() throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.SaveQuickEdit).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(5000);
    }

    @Given("Get the Records Returned on Tractor Settlement Adjustments Quick Edit Table, for {string} and {string} when Start date {string} and Amount {string} is CHANGED")
    public void get_the_records_returned_on_tractor_settlement_adjustments_quick_edit_table_for_and_when_start_date_and_amount_is_changed(String unitNo, String payCode, String startDate, String amount) throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).click();
        Thread.sleep(6000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

      /*  driver.findElement(By.xpath("//*[@id=\"img15\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[16]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(startDate)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);  */

        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(amount)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        System.out.println("=========================================");
        System.out.println("NEWLY EDITED RECORD ON QUICK EDIT: " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(3000);
    }

    @Given("Validate the Records Returned on Tractor Settlement Adjustments Quick Edit Table, when Start date and Amount is CHANGED with Database Record {string} {string} {string} {string} and {string} {string} {string}")
    public void validate_the_records_returned_on_tractor_settlement_adjustments_quick_edit_table_when_start_date_and_amount_is_changed_with_database_record_and_pay_code(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String adjustmentID) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'                                 \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       and TRACTOR_ADJUST_ID = '" + adjustmentID + "'" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs.getString(26) +
                    "\t" + rs.getString(27));

            String a = rs.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Pay Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String e = rs.getString(16);
            dbTATable4.add(e);
            System.out.println("Start Date : " + e);
            String c = rs.getString(11);
            dbTATable2.add(c);
            System.out.println("Amount : " + c);
            String d = rs.getString(19);
            dbTATable3.add(d);
            System.out.println("Adj. ID : " + d);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("Select Quick Edit on Tractor Settlement Adjustments to Revert Back Start Date and Amount to original")
    public void select_quick_edit_on_tractor_settlement_adjustments_to_revert_back_start_date_and_amount_to_original() {
        driver.findElement(tractorSettlementAdjustmentsPage.QuickEdit).click();
    }


    @Given("Get the Records Returned on Tractor Settlement Adjustments Quick Edit Table to Revert Back, for {string} and {string} when Start date {string} and Amount {string} is CHANGED")
    public void get_the_records_returned_on_tractor_settlement_adjustments_quick_edit_table_to_revert_back_for_and_when_start_date_and_amount_is_changed(String unitNo, String payCode, String startDate, String amount) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

      /*  driver.findElement(By.xpath("//*[@id=\"img15\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[16]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(startDate)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);  */

        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(amount)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        System.out.println("=========================================");
        System.out.println("NEWLY EDITED RECORD ON QUICK EDIT: " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(3000);
    }


    //................................/ #107   @TractorSettlementAdjustmentsScenarioEdit /.......................................//

    @Given("Enter Unit Number {string} {string} {string} for Edit")
    public void enter_unit_number_for_edit(String unitNo, String startDate, String endDate) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).click();
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFrom).sendKeys(startDate);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateFrom).click();
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateTo).sendKeys(endDate);
        driver.findElement(tractorSettlementAdjustmentsPage.StartDateTo).click();
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(5000);
    }

    @Given("Select the Pay Code {string}, Status {string} as ACTIVE, Amount {string} and Start Date {string} for Edit")
    public void select_the_pay_code_status_as_active_amount_and_start_date_for_edit(String payCode, String status, String amount, String startDate) throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[8]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(6000);
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img15\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[16]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list5) {
            if (webElement.getText().contains(startDate)) {
                Thread.sleep(8000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
    }

    @Given("Get the Record Returned on Tractor Settlement Adjustments for Edit")
    public void get_the_record_returned_on_tractor_settlement_adjustments_for_edit() throws InterruptedException {
        System.out.println("=========================================");
        System.out.println("RECORD RETURNED FOR EDIT: " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(3000);
    }

    @Given("Validate the Record Returned on Tractor Settlement Adjustments for Edit with Database Record {string} {string} {string} {string} and {string} {string} {string} {string} {string}")
    public void validate_the_record_returned_on_tractor_settlement_adjustments_for_edit_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String status, String amount, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("Database Record for EDIT : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       AND [TRACTOR_ADJUST_STATUS] = '" + status + "'\n" +
                "       AND [TRACTOR_ADJUST_AMOUNT] = '" + amount + "'\n" +
                "       and TRACTOR_ADJUST_START_DATE = '" + startDate + "' \n" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs.getString(18));

            String a = rs.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Pay Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String e = rs.getString(8);
            dbTATable4.add(e);
            System.out.println("Status : " + e);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("Select Edit Button on Action Column for Edit {string}")
    public void select_edit_button_on_action_column_for_edit(String adjustmentID) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.Edit).click();
        Thread.sleep(3000);
      //  System.out.println("CONFIRMATION " + driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p")).getText());
      //  driver.findElement(By.linkText("Yes")).click();

        //*[@id="btnEdit"]
        //   WebElement editClick = driver.findElement(By.id("linkEdit_" + adjustmentID + ""));
        //  JavascriptExecutor executor = (JavascriptExecutor) driver;
        // executor.executeScript("arguments[0].click();", editClick);
        //*[@id="btnEdit"]
    }


 /*   @Given("^Select the First Edit Button on Action Column \"([^\"]*)\"$")
    public void select_the_First_Edit_Button_on_Action_Column(String adjustmentID) throws InterruptedException {
        Thread.sleep(4000);
        WebElement editClick = driver.findElement(By.id("linkEdit_" + adjustmentID + ""));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", editClick);
    }

    @Given("^Select the First Edit Button on Action Column for COMPLETE \"([^\"]*)\"$")
    public void select_the_First_Edit_Button_on_Action_Column_for_complete(String adjustmentID) throws InterruptedException {
        Thread.sleep(4000);
        WebElement editClick = driver.findElement(By.id("linkEdit_" + adjustmentID + ""));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", editClick);
    }  */

    @Given("Change ACTIVE status to COMPLETE, Select YES Button and Confirm Status changes to COMPLETE {string} {string}")
    public void change_active_status_to_complete_select_yes_button_and_confirm_status_changes_to_complete(String status, String endDate) throws InterruptedException {

        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).sendKeys(endDate);
        driver.findElement(By.xpath("//*[@id=\"txtEndDate\"]")).click();
        Thread.sleep(1000);

        WebElement wb = driver.findElement(By.id("Status_I"));
        wb.clear();
        Thread.sleep(2000);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + status + "';", wb);
        Thread.sleep(3000);
        driver.findElement(tractorSettlementAdjustmentsPage.Save).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(5000);
    }

    @Given("Select Include Complete, Select the Pay Code {string}, Amount {string}, Start Date {string} and Select Status as COMPLETE {string}")
    public void select_include_complete_select_the_pay_code_amount_start_date_and_select_status_as_complete(String payCode, String amount, String startDate, String status) throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(6000);
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img15\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[16]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list5) {
            if (webElement.getText().contains(startDate)) {
                Thread.sleep(8000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[8]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
    }


    @Given("Get the Records Returned on Tractor Settlement Adjustments Table, when ACTIVE status is changed to COMPLETE")
    public void get_the_records_returned_on_tractor_settlement_adjustments_table_when_active_status_is_changed_to_complete() throws InterruptedException {
        System.out.println("=========================================");
        System.out.println("ACTIVE status changed to COMPLETE : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTableBody).getText());
        Thread.sleep(3000);
    }


    @Given("Validate the Records Returned on Tractor Settlement Adjustments Table, WHEN ACTIVE TO COMPLETE with Database Record {string} {string} {string} {string} and {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_on_tractor_settlement_adjustments_table_when_active_to_complete_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String status, String amount, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("ACTIVE status changed to COMPLETE : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       AND [TRACTOR_ADJUST_AMOUNT] = '" + amount + "'\n" +
                "       and TRACTOR_ADJUST_START_DATE = '" + startDate + "' \n" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs.getString(18));

            String a = rs.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Pay Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String e = rs.getString(8);
            dbTATable4.add(e);
            System.out.println("Status : " + e);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("Select Edit Button on Action Column for COMPLETE")
    public void select_edit_button_on_action_column_for_complete() throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.Edit).click();

      //  System.out.println("CONFIRMATION " + driver.findElement(By.xpath("//*[@id=\"Divpopup\"]/div/div[2]/div/p")).getText());
     //   driver.findElement(By.linkText("Yes")).click();

        Thread.sleep(3000);
    }

    @Given("Change COMPLETE status to ACTIVE, Select YES Button and Confirm ACTIVE Status was accepted {string}")
    public void change_complete_status_to_active_select_yes_button_and_confirm_active_status_was_accepted(String status1) throws InterruptedException {

        WebElement wb = driver.findElement(By.id("Status_I"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + status1 + "';", wb);
        Thread.sleep(3000);

        driver.findElement(tractorSettlementAdjustmentsPage.Save).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(3000);
    }


    @Given("Select Include Complete, Select the Pay Code {string}, Amount {string}, Start Date {string} and Status {string} as ACTIVE")
    public void select_include_complete_select_the_pay_code_amount_start_date_and_status_as_active(String payCode, String amount, String startDate, String status) throws InterruptedException {

        Thread.sleep(8000);
        driver.findElement(By.xpath("//*[@id=\"img4\"]")).click();
        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[5]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(payCode)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);


        driver.findElement(By.xpath("//*[@id=\"img10\"]")).click();
        List<WebElement> list4 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[11]/div/div/p"));
        Thread.sleep(6000);
        for (WebElement webElement : list4) {
            if (webElement.getText().contains(amount)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img15\"]")).click();
        List<WebElement> list5 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[16]/div/div/p"));
        Thread.sleep(5000);
        for (WebElement webElement : list5) {
            if (webElement.getText().contains(startDate)) {
                Thread.sleep(8000);
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"img7\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/thead/tr/th[8]/div/div/p"));
        Thread.sleep(3000);
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(2000);
    }


    @Given("Get the Records Returned on Tractor Settlement Adjustments Table, when COMPLETE status is changed to ACTIVE")
    public void get_the_records_returned_on_tractor_settlement_adjustments_table_when_complete_status_is_changed_to_active() throws InterruptedException {
        System.out.println("=========================================");
        System.out.println("COMPLETE status changed to ACTIVE : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTableBody).getText());
        Thread.sleep(3000);
    }

    @Given("Validate the Records Returned on Tractor Settlement Adjustments Table, WHEN COMPLETE TO ACTIVE with Database Record {string} {string} {string} {string} and {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_on_tractor_settlement_adjustments_table_when_complete_to_active_with_database_record_and(String environment, String tableName, String tableName1, String tableName2, String unitNo, String payCode, String status, String amount, String startDate) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println("COMPLETE status changed to ACTIVE :");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "With\n" +
                "       VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where TRAC_VEND_UNITNO = '" + unitNo + "')\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = 'EVA'\n" +
                "       and TRAC_VEND_UNITNO = '" + unitNo + "' \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS' \n" +
                "       AND [TRACTOR_ADJUST_PAY_CODE] = '" + payCode + "'\n" +
                "       AND [TRACTOR_ADJUST_AMOUNT] = '" + amount + "'\n" +
                "       and TRACTOR_ADJUST_START_DATE = '" + startDate + "' \n" +
                "       ORDER BY TRACTOR_ADJUST_ID ";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs.getString(18));

            String a = rs.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Pay Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String e = rs.getString(8);
            dbTATable4.add(e);
            System.out.println("Status : " + e);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //................................/ #108 @TractorSettlementAdjustments/UnitNo/PayCode/Frequency/OrderNo /.......................................//

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(15000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorSettlementAdjustmentsPage.DataTable));
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo ))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }

    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
    }


    @Given("Enter Unit No, Start Date From, Start Date To, Pay Code {string} and Click on Search Button")
    public void enter_unit_no_start_date_from_start_date_to_pay_code_and_click_on_search_button(String payCode) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeAdv).sendKeys(payCode);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeAdv).click();
        Thread.sleep(1000);
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo ))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }


    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
    }


    @Given("Enter Unit No, Start Date From, Start Date To, Pay Code, Frequency {string} and Click on Search Button")
    public void enter_unit_no_start_date_from_start_date_to_pay_code_frequency_and_click_on_search_button(String frequency) throws InterruptedException {
        Thread.sleep(1000);
        WebElement wb = driver.findElement(tractorSettlementAdjustmentsPage.FrequencyAdv);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + frequency + "';", wb);
        Thread.sleep(1000);
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo ))\n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)     = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)     = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }


    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)      = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
    }


    @Given("Enter Unit No, Start Date From, Start Date To, Pay Code, Frequency, Order Number {string} and Click on Search Button")
    public void enter_unit_no_start_date_from_start_date_to_pay_code_frequency_order_number_and_click_on_search_button(String orderNo) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.OrderNo).sendKeys(orderNo);
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.OrderNo).click();
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED  : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }

    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Unit No {string} {string} {string} {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_unit_no(String environment, String tableName, String tableName1, String tableName2, String unitNo, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println("EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @UnitNo VarChar (10)       = '" + unitNo + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @UnitNo [Unit No], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_UNITNO = @UnitNo )) \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //................................/ #109 @TractorSettlementAdjustments/Location/PayCode/Frequency/OrderNo /.......................................//

    @Given("Enter Location {string} and Click on Search Button")
    public void enter_location_and_click_on_search_button(String location) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.Location).sendKeys(location);
        driver.findElement(tractorSettlementAdjustmentsPage.Location).click();
        Thread.sleep(2000);
    }

    @And("^Validate Location Name \"([^\"]*)\" for Advanced Search$")
    public void Validate_Location_Name_for_advanced_search(String location) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("=========================================");
        System.out.println("Location name of Location (" + location + ") : " + driver.findElement(By.id("lblLocationDesc")).getText());
        Thread.sleep(3000);
        System.out.println("=========================================");
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(130000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorSettlementAdjustmentsPage.DataTable));
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: UNIT NO, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println("BOTH RECURRING ONLY and INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }

    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO IS ENTERED ** ");
        System.out.println(" EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
    }


    @Given("Enter Location, Start Date From, Start Date To, Pay Code {string} and Click on Search Button")
    public void enter_location_start_date_from_start_date_to_pay_code_and_click_on_search_button(String payCode) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeAdv).sendKeys(payCode);
        driver.findElement(tractorSettlementAdjustmentsPage.PayCodeAdv).click();
        Thread.sleep(1000);
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(15000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorSettlementAdjustmentsPage.DataTable));
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH RECURRING ONLY and INCLUDE COMPLETE DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }

    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string}")
    public void select_EFS_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE IS ENTERED ** ");
        System.out.println(" EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);
            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);
            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);
            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
    }


    @Given("Enter Location, Start Date From, Start Date To, Pay Code, Frequency {string} and Click on Search Button")
    public void enter_location_start_date_from_start_date_to_pay_code_frequency_and_click_on_search_button(String frequency) throws InterruptedException {
        Thread.sleep(1000);
        WebElement wb = driver.findElement(tractorSettlementAdjustmentsPage.FrequencyAdv);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + frequency + "';", wb);
        Thread.sleep(1000);
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(15000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorSettlementAdjustmentsPage.DataTable));
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED  : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)     = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)     = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }

    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY IS ENTERED ** ");
        System.out.println(" EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)      = '" + frequency + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode  \n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency  \n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
    }

    @Given("Enter Location, Start Date From, Start Date To, Pay Code, Frequency, Order Number {string} and Click on Search Button")
    public void enter_location_start_date_from_start_date_to_pay_code_frequency_order_number_and_click_on_search_button(String orderNo) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.OrderNo).sendKeys(orderNo);
        Thread.sleep(2000);
        driver.findElement(tractorSettlementAdjustmentsPage.OrderNo).click();
        Thread.sleep(2000);
    }

    @Given("DESELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string} {string}")
    public void deselect_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(15000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(tractorSettlementAdjustmentsPage.DataTable));
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY DESELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string} {string}")
    public void select_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" RECURRING ONLY SELECTED  : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("SELECT INCLUDE COMPLETE, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string} {string}")
    public void select_include_complete_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" INCLUDE COMPLETE SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("SELECT BOTH INCLUDE COMPLETE and RECURRING ONLY, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string} {string}")
    public void select_both_include_complete_and_recurring_only_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" BOTH INCLUDE COMPLETE and RECURRING ONLY SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_CREATED_BY <> 'EFS'    -- If EFS Button is NOT Selected\n" +
                "       and TRACTOR_ADJUST_FREQ IN ('W', 'M', 'Y') -- If Recurring Only IS Selected      \n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

        Thread.sleep(1000);
        driver.findElement(tractorSettlementAdjustmentsPage.IncludeComplete).click();
        driver.findElement(tractorSettlementAdjustmentsPage.RecurringOnly).click();
    }


    @Given("SELECT EFS, get Records and validate the Records Returned with Database Record {string} {string} {string} {string} and Location {string} {string} {string} {string} {string} {string}")
    public void select_efs_get_records_and_validate_the_records_returned_with_database_record_and_location(String environment, String tableName, String tableName1, String tableName2, String location, String startDateFrom, String startDateTo, String payCode, String frequency, String orderNo) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        driver.findElement(tractorSettlementAdjustmentsPage.EFS).click();
        driver.findElement(tractorSettlementAdjustmentsPage.Search).click();
        Thread.sleep(10000);
        System.out.println("=========================================");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println(" EFS SELECTED : " + driver.findElement(tractorSettlementAdjustmentsPage.TotalRecordsReturned).getText());
        log.info(driver.findElement(tractorSettlementAdjustmentsPage.DataTable).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        System.out.println(" ** SCENARIO: LOCATION, START DATE FROM, START DATE TO, PAY CODE, FREQUENCY, ORDER NO IS ENTERED ** ");
        System.out.println("EFS SELECTED : ");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Declare @CompanyCode Varchar (3)   = 'EVA'  \n" +
                "       Declare @Location VarChar (3)       = '" + location + "' \n" +
                "       Declare @Startdate Date            = '" + startDateFrom + "' \n" +
                "       Declare @EndDate  Date             = '" + startDateTo + "' \n" +
                "       Declare @PayCode VarChar (3)       = '" + payCode + "' \n" +
                "       Declare @Frequency VarChar (1)       = '" + frequency + "' \n" +
                "       Declare @OrderNo VarChar (10)       = '" + orderNo + "' \n" +
                "       Select @CompanyCode [Company Code], @Location [Location], @Startdate [Start Date], @EndDate [EndDate], @PayCode [Pay Code], @Frequency [Frequency], @OrderNo [Order No];\n" +
                "       With VNDCTE (VNDCODE, UNITNO )\n" +
                "       AS (\n" +
                "       Select Distinct TRAC_VEND_VENDOR_CODE, TRAC_VEND_UNITNO\n" +
                "       From " + tableName1 + " With(Nolock)\n" +
                "       Where (TRAC_VEND_LOC = @Location))  \n" +
                "       Select TRACTOR_ADJUST_COMP_CODE[Company],TRAC_VEND_LOC [Location], TRACTOR_ADJUST_LOC_OR_TRAC [Unit No], TRACTOR_ADJUST_VENDOR_CODE [Vendor Code], \n" +
                "       TRACTOR_ADJUST_PAY_CODE [Pay Code], PAY_DESC [Pay Desc], PAY_TYPE [Pay Type], TRACTOR_ADJUST_STATUS [Status], TRACTOR_ADJUST_FREQ [Freq], \n" +
                "       TRACTOR_ADJUST_AMOUNT_TYPE [Amt Type], TRACTOR_ADJUST_AMOUNT [Amount], TRACTOR_ADJUST_MAX_TRANS [Max # Adj], TRACTOR_ADJUST_TOP_LIMIT [Max Limit], \n" +
                "       TRACTOR_ADJUST_TOTAL_TO_DATE [Total To Date], TRACTOR_ADJUST_ORDER_NO [Order No], TRACTOR_ADJUST_START_DATE [Start Date],\n" +
                "       TRACTOR_ADJUST_LAST_AMOUNT [Last Activity Amt.], TRACTOR_ADJUST_LAST_DATE [Last Activity Date], TRACTOR_ADJUST_ID [Adj. ID], '     ', *\n" +
                "       From " + tableName + " With(Nolock)\n" +
                "       INNER JOIN " + tableName2 + " With(Nolock)   on PAY_CODE = TRACTOR_ADJUST_PAY_CODE\n" +
                "       Inner Join " + tableName1 + "  With(Nolock) on TRACTOR_ADJUST_LOC_OR_TRAC = TRAC_VEND_UNITNO \n" +
                "       Inner Join VNDCTE on TRACTOR_ADJUST_LOC_OR_TRAC = UNITNO and TRACTOR_ADJUST_VENDOR_CODE = VNDCODE\n" +
                "       WHERE   TRACTOR_ADJUST_COMP_CODE = @CompanyCode \n" +
                "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
                "       and TRACTOR_ADJUST_CREATED_BY = 'EFS'    -- If EFS Button is Selected\n" +
                "       and TRACTOR_ADJUST_START_DATE Between @Startdate and @EndDate\n" +
                "       and TRACTOR_ADJUST_Pay_Code = @PayCode\n" +
                "       and TRACTOR_ADJUST_FREQ = @Frequency\n" +
                "       and TRACTOR_ADJUST_ORDER_NO = @OrderNo\n" +
                "       ORDER BY TRACTOR_ADJUST_ID \n";

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
                    "\t" + rs.getString(4) +
                    "\t" + rs.getString(5) +
                    "\t" + rs.getString(6) +
                    "\t" + rs.getString(7));
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

        List<WebElement> TATable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));
        List<String> dbTATable = new ArrayList<>();
        List<String> dbTATable1 = new ArrayList<>();
        List<String> dbTATable2 = new ArrayList<>();
        List<String> dbTATable3 = new ArrayList<>();
        List<String> dbTATable4 = new ArrayList<>();
        List<String> dbTATable5 = new ArrayList<>();
        List<String> dbTATable6 = new ArrayList<>();
        List<String> dbTATable7 = new ArrayList<>();

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
                    "\t" + rs1.getString(25) +
                    "\t" + rs1.getString(26) +
                    "\t" + rs1.getString(27) +
                    "\t" + rs1.getString(28) +
                    "\t" + rs1.getString(29) +
                    "\t" + rs1.getString(30) +
                    "\t" + rs1.getString(31) +
                    "\t" + rs1.getString(32) +
                    "\t" + rs1.getString(33) +
                    "\t" + rs1.getString(34) +
                    "\t" + rs1.getString(35) +
                    "\t" + rs1.getString(36) +
                    "\t" + rs1.getString(37) +
                    "\t" + rs1.getString(38));

            String a = rs1.getString(3);
            dbTATable.add(a);

            boolean booleanValue = false;
            for (WebElement taTable : TATable) {
                if (taTable.getText().contains(a)) {
                    for (String dbtaTable : dbTATable) {
                        if (dbtaTable.contains(a)) {
                            System.out.println("Unit No : " + a);
                            booleanValue = true;
                            break;
                        }
                    }
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs1.getString(4);
            dbTATable1.add(b);

            boolean booleanValue1 = false;
            for (WebElement taTable1 : TATable) {
                if (taTable1.getText().contains(b)) {
                    for (String dbtaTable1 : dbTATable1) {
                        if (dbtaTable1.contains(b)) {
                            System.out.println("Vendor Code : " + b);
                            booleanValue1 = true;
                            break;
                        }
                    }
                }
                if (booleanValue1) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
            String e = rs1.getString(2);
            dbTATable4.add(e);
            System.out.println("Location : " + e);

            String c = rs1.getString(1);
            dbTATable2.add(c);
            System.out.println("Company Code : " + c);

            String f = rs1.getString(5);
            dbTATable5.add(f);
            System.out.println("Pay Code : " + f);

            String g = rs1.getString(9);
            dbTATable6.add(g);
            System.out.println("Frequency : " + g);

            String h = rs1.getString(15);
            dbTATable7.add(h);
            System.out.println("Order No : " + h);

            String d = rs1.getString(8);
            dbTATable3.add(d);
            System.out.println("Status : " + d);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }
}

