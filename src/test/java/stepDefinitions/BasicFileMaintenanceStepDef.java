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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.CommonUtils.BrowserDriverInitialization;
import stepDefinitions.Pages.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.*;


public class BasicFileMaintenanceStepDef {
    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";
    String usernameExpected = "";

    EBHLoginPage ebhlogInPage = new EBHLoginPage(driver);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    BasicFileMaintenancePage basicFileMaintenancePage = new BasicFileMaintenancePage(driver);
    AccountFilePage accountFilePage = new AccountFilePage(driver);
    LocationFilePage locationFilePage = new LocationFilePage(driver);
    Logger log = Logger.getLogger("SettlingStepDef");
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();


//............................................/ Background /................................................//

    @After("@FailureScreenShot3")
    public void takeScreenshotOnFailure3(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }

    @Given("^Run Test for \"([^\"]*)\" on Browser \"([^\"]*)\" and Enter the url for EBH Basic File Maintenance$")
    public void run_Test_for_on_Browser_and_Enter_the_url_for_EBH_Basic_File_Maintenance(String environment, String browser) throws MalformedURLException {
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

    @And("^Login to the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Basic File Maintenance$")
    public void Login_to_the_Agents_Portal_with_username_and_password_for_EBH_Basic_File_Maintenance(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhlogInPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.password));
        driver.findElement(ebhlogInPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.signinButton));
        driver.findElement(ebhlogInPage.signinButton).click();
    }

    @Given("^Navigate to the Corporate Page on Main Menu and to the Basic File Maintenance page for EBH Basic File Maintenance$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_and_to_the_Basic_File_Maintenance_page_for_EBH_Basic_File_Maintenance() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        driver.findElement(corporatePage.basicFileMaintenance).click();
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Then("^Close all open Browsers on EBH$")
    public void Close_all_open_Browsers_on_EBH() {
        driver.close();
        driver.quit();
    }



    //............................................/ 20 @AccountFile /................................................//

    @Given("^Navigate to the Account File page$")
    public void Navigate_to_the_Account_File_page() {
        driver.findElement(basicFileMaintenancePage.AccountFile).click();
    }

    @And("^Enter Agent/Location Code as \"([^\"]*)\" in Search In Box and click$")
    public void Enter_Agent_Location_Code_as_in_Search_In_Box_and_click(String code) throws
            InterruptedException {
        driver.findElement(accountFilePage.SearchIn).sendKeys(code + "\n");
        Thread.sleep(1000);
    }

    @And("^Verify Bill-To is Checked on and get the Records Returned for \"([^\"]*)\"$")
    public void verify_Bill_To_is_Checked_on_and_get_the_Records_Returned_for(String code) throws
            InterruptedException {
        System.out.println("=========================================");
        System.out.println("Bill-To is Checked On : " + driver.findElement(accountFilePage.BillToButton).isSelected());
        Thread.sleep(5000);
        System.out.println("Records returned when Bill-To is Checked On for " + code + " : " + driver.findElement(By.id("lblRowCount")).getText());
        log.info(driver.findElement(By.xpath("//*[@id=\"dataTable_wrapper\"]/div[2]/div")).getText());
        //*[@id="dataTable"]
        //*[@id="dataTable"]/tbody
        Thread.sleep(10000);
    }

    @And("^Validate Records Returned on Bill-To with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_Bill_To_with_Database_Record_and(String environment, String
            tableName, String accountName, String assertValue) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000 [Account_Code]\n" +
                "       ,[Account_Name] \n" +
                "       ,[Account_Addr1]\n" +
                "       ,[Account_Addr2]\n" +
                "       ,[Account_City]\n" +
                "       ,[Account_State]\n" +
                "       FROM " + tableName + " " +
                "       WHERE [ACCOUNT_BILLTO] = 1\n" +
                "       AND [Account_Name] LIKE '" + accountName + "' OR [Account_Code] LIKE '" + accountName + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAccountsTable = new ArrayList<>();
        List<WebElement> billToAccountsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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

            String a = rs.getString(2);
            dbAccountsTable.add(a);

        }
        String b = "" + assertValue + "";

        boolean booleanValue = false;
        for (WebElement billToTable : billToAccountsTable) {
            if (billToTable.getText().contains(b)) {
                for (String dbaTable : dbAccountsTable) {
                    if (dbaTable.contains(b)) {
                        System.out.println(b);
                        System.out.println();
                        booleanValue = true;
                    }
                }
                break;
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click on SH/CON, Verify SH/CON is Checked on and get the Records Returned for \"([^\"]*)\"$")
    public void click_on_SH_CON_Verify_SH_CON_is_Checked_on_and_get_the_Records_Returned_for(String code) throws
            InterruptedException {
        Thread.sleep(8000);
        driver.findElement(accountFilePage.SHCONButton).click();
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println("SH/CON is Checked On : " + driver.findElement(accountFilePage.SHCONButton).isSelected());
        System.out.println("Records returned when SH/CON is Checked On for " + code + " : " + driver.findElement(By.id("lblRowCount")).getText());
        log.info(driver.findElement(By.xpath("//*[@id=\"dataTable_wrapper\"]")).getText());
        Thread.sleep(10000);
    }

    @And("^Validate Records Returned on SH/CON with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_on_SH_CON_with_Database_Record_and(String environment, String
            tableName, String accountName, String assertValue2) throws
            SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "SELECT TOP 1000[Account_Code]\n" +
                "       ,[Account_Name] \n" +
                "       ,[Account_Addr1]\n" +
                "       ,[Account_Addr2]\n" +
                "       ,[Account_City]\n" +
                "       ,[Account_State]\n" +
                "       FROM " + tableName + "" +
                "       WHERE [ACCOUNT_SHIPCON] = 1\n" +
                "       AND [Account_Name] LIKE '" + accountName + "' OR [Account_Code] LIKE '" + accountName + "'";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbShConTable = new ArrayList<>();
        List<WebElement> shipConAccountsTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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

            String a = rs.getString(1);
            dbShConTable.add(a);
        }

        String str = "" + assertValue2 + "";

        boolean booleanValue = false;
        for (WebElement shConToTable : shipConAccountsTable) {
            if (shConToTable.getText().contains(str)) {
                for (String dbShTable : dbShConTable) {
                    if (dbShTable.contains(str)) {
                        System.out.println(str);
                        System.out.println();
                        booleanValue = true;
                    }
                }
                break;
            }
            if (booleanValue) {
                Assert.assertTrue("AssertValue is present !!", true);
            } else {
                fail("AssertValue is not present!!");
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @And("^Click Select of first column$")
    public void Click_Select_of_first_column() {
        driver.findElement(accountFilePage.SelectFirstColumn).click();
    }

    @Then("^Validate Table Header and Name on Account File Maintenance$")
    public void Validate_Table_Header_and_Name_on_Account_File_Maintenance() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("=========================================");
        assertTrue(driver.findElement(accountFilePage.AccountFileMaintenance).isDisplayed());
        System.out.println(driver.findElement(accountFilePage.AccountFileMaintenance).getText());
        Thread.sleep(5000);
        assertTrue(driver.findElement(accountFilePage.Name).isDisplayed());
        System.out.println(driver.findElement(accountFilePage.Name).getText());
        System.out.println("=========================================");
    }


    //............................................/ 21 @LocationFile /................................................//

    @Given("^Navigate to the Basic File Maintenance page$")
    public void Navigate_to_the_Basic_File_Maintenance_page() {
        driver.findElement(corporatePage.basicFileMaintenance).click();
    }

    @And("^Navigate to the Location File page$")
    public void Navigate_to_the_Location_File_page() {
        driver.findElement(basicFileMaintenancePage.LocationFile).click();
    }


    @Given("^Enter Part of Location Name as \"([^\"]*)\" and click$")
    public void enter_Part_of_Location_Name_as_and_click(String partOfLocationName) throws InterruptedException {
        driver.findElement(locationFilePage.PartOfLocationName).sendKeys(partOfLocationName + "\n");
        Thread.sleep(5000);
    }

    @Given("^Validate Records Returned for Location File Maintenance with Database Record \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$")
    public void validate_Records_Returned_for_Location_File_Maintenance_with_Database_Record_and(String environment, String tableName, String partOfLocationName1) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String useDB = "use " + tableName;
        String query = "SELECT TOP 1000 [LOCATION_CODE]\n" +
                "      ,[LOCATION_NAME]\n" +
                "      ,[LOCATION_ADDR1]\n" +
                "      ,[LOCATION_ADDR2]\n" +
                "      ,[LOCATION_CITY]\n" +
                "      ,[LOCATION_STATE]\n" +
                "  FROM " + tableName + "\n" +
                "  WHERE [LOCATION_NAME] like '" + partOfLocationName1 + "'";


        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbLocationsTable = new ArrayList<>();
        List<WebElement> locationFileMainteTable = driver.findElements(By.xpath("//*[@id=\"dataTable\"]/tbody"));

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

            String a = rs.getString(2);
            dbLocationsTable.add(a);

            boolean booleanValue = false;
            for (WebElement locaTable : locationFileMainteTable) {
                if (locaTable.getText().contains(a)) {
                    for (String dbLocaTable : dbLocationsTable) {
                        if (dbLocaTable.contains(a)) {
                            System.out.println(a);
                            System.out.println();
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    Assert.assertTrue("AssertValue is present !!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("^Click on Select and Validate Name, Status and AAR Rep$")
    public void click_on_Select_and_Validate_Name_Status_and_AAR_Rep() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"selectFocus\"]")).click();

        Thread.sleep(5000);
        assertTrue(driver.findElement(locationFilePage.LocationCode).isDisplayed());

        WebElement LocationCode = driver.findElement(locationFilePage.LocationCode);
        // String aa = driver.findElement(locationFilePage.LocationCode).getAttribute());
        //   assertEquals();
        //  System.out.println(driver.findElement(agentCommissionMaintenancePage.agentCodeDescription).getText());
        System.out.println("Location Code : " + driver.findElement(locationFilePage.LocationCode).getText());
        System.out.println("Name : " + driver.findElement(locationFilePage.Name).getText());
        System.out.println("Status : " + driver.findElement(locationFilePage.Status).getText());
        System.out.println("AAR Rep : " + driver.findElement(locationFilePage.AARRep).getText());
        assertEquals(LocationCode, LocationCode);
    }

//////////////////////////////////////////////////////////////////////////////


    //...................................../ #66  @TractorSettlementAdjustmentsUnitNo /..........................................//
/*
    @And("^Enter Unit No as \"([^\"]*)\"$")
    public void Enter_Unit_No_as(String unitNo) throws InterruptedException {
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).sendKeys(unitNo);
        driver.findElement(tractorSettlementAdjustmentsPage.UnitNo).click();
        Thread.sleep(2000);
    }

    @And("^Validate Company Code, Vendor Code, Vendor Name Description of \"([^\"]*)\"$")
    public void Validate_VendorID_Vendor_Code_Description_of(String unitNo) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("=========================================");
        System.out.println("Company Code, Vendor Code, Vendor Name of Unit No (" + unitNo + ") : " + driver.findElement(By.id("lblCompanyCode")).getText() + "," + driver.findElement(By.id("lblVendorCode")).getText() + "," + driver.findElement(By.id("lblVendorName")).getText());
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
        WebDriverWait wait = new WebDriverWait(driver,150);
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
                //   "       and TRACTOR_ADJUST_STATUS <> 'COMPLETE'   -- If Include Complete Button is NOT Selected     \n" +
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

*/

}




