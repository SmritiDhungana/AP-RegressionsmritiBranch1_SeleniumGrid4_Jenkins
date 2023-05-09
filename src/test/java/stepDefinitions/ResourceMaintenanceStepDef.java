package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
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
import stepDefinitions.Pages.CorporatePage;
import stepDefinitions.Pages.EBHLoginPage;
import stepDefinitions.Pages.EBHMainMenuPage;
import stepDefinitions.Pages.ResourceMaintenancePage;

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


public class ResourceMaintenanceStepDef {
    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";
    String usernameExpected = "";

    EBHLoginPage ebhlogInPage = new EBHLoginPage(null);
    EBHMainMenuPage mainMenuPage = new EBHMainMenuPage(driver);
    CorporatePage corporatePage = new CorporatePage(driver);
    ResourceMaintenancePage resourceMaintenancePage = new ResourceMaintenancePage(driver);
    Logger log = Logger.getLogger("SettlingStepDef");
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();
    private static Statement stmt;


//............................................/ Background /................................................//

    @After("@FailureScreenShot9")
    public void takeScreenshotOnFailure9(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }

    @Given("^Run Test for \"([^\"]*)\" on Browser \"([^\"]*)\" and Enter the url for EBH Resource Maintenance$")
    public void run_Test_for_on_Browser_and_Enter_the_url_for_EBH_Resource_Maintenance(String environment, String browser) throws MalformedURLException {
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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get(URL);
    }

    @And("^Login to the Agents Portal with username \"([^\"]*)\" and password \"([^\"]*)\" for EBH Resource Maintenance$")
    public void Login_to_the_Agents_Portal_with_username_and_password_for_EBH_Resource_Maintenance(String username, String password) {
        usernameExpected = username;
        driver.findElement(ebhlogInPage.username).sendKeys(usernameExpected.toUpperCase());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.password));
        driver.findElement(ebhlogInPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ebhlogInPage.signinButton));
        driver.findElement(ebhlogInPage.signinButton).click();
    }

    @Given("^Navigate to the Corporate Page on Main Menu and to the Resources page for EBH Resource Maintenance$")
    public void navigate_to_the_Corporate_Page_on_Main_Menu_and_to_the_Resources_page_for_EBH_Resource_Maintenance() {
        driver.findElement(mainMenuPage.corporate).click();
        getAndSwitchToWindowHandles();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        driver.findElement(corporatePage.resources).click();
    }

    private void getAndSwitchToWindowHandles() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Then("^Close all open Browsers on EBH Resource Maintenance$")
    public void Close_all_open_Browsers_on_EBH_Resource_Maintenance() throws InterruptedException {
        Thread.sleep(5000);
        driver.close();
        driver.quit();
    }


    //............................................/ #82 @ResourceMaintenanceSearch/Report /................................................//

    @Given("^Navigate to the Resource Maintenance page$")
    public void Navigate_to_the_Resource_maintenance_page() {
        driver.findElement(resourceMaintenancePage.ResourceMaintenance).click();
    }

    @Given("Enter text to Search Box Code or Locations or Name {string} and click on Search")
    public void enter_text_to_serach_box_code_or_locations_or_name_and_click_on_search(String code) throws InterruptedException {
        driver.findElement(resourceMaintenancePage.SearchBox).sendKeys(code);
        driver.findElement(resourceMaintenancePage.Search).click();
        Thread.sleep(5000);
    }

    @Given("Get the Total Records Returned on Resource Maintenance Table")
    public void get_the_total_records_returned_on_resource_maintenance_table() throws InterruptedException {
        System.out.println("=========================================");

        System.out.println(driver.findElement(By.xpath("//*[@id=\"lblSearch\"]")).getText());
        System.out.println(".........................................");
        System.out.println("Total Records Returned on RESOURCE MAINTENANCE: " + driver.findElement(resourceMaintenancePage.TotalRecordsReturned).getText());
        log.info(driver.findElement(resourceMaintenancePage.DataTable).getText());
        Thread.sleep(10000);
    }

    @Given("Validate the Records Returned on Resource Maintenance with Database Record {string} {string} {string} and {string}")
    public void validate_the_records_returned_on_resource_maintenance_with_database_record_and(String environment, String tableName, String tableName1, String code) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT RES.RES_ID\n" +
                "      ,RES.RES_TYPE as [Type]\n" +
                "      ,RES.RES_COMP_CODE as [Company]\n" +
                "      ,RES.RES_RESOURCE_CODE as [Code]\n" +
                "      ,RES.RES_NAME as [Display Name]\n" +
                "      ,RES.RES_OWNER_IFTA as [Owner IFTA]\n" +
                "      ,RES.RES_CELL_CARRIER_CODE as [Cell Carrier]\n" +
                "      ,RES.RES_CELL_PHONE_NUM as [Cell Phone #]\n" +
                "      ,RES.RES_EMAIL as [Email Address]\n" +
                "      ,RES.RES_EFS_CARD_NUM as [EFS Card #]\n" +
                "      ,RES.RES_STATUS as [Status]\n" +
                "      ,RES_LOC.[RES_ALL_LOCATION] as [Home Locations]\n" +
                "  FROM " + tableName + " as RES WITH(NOLOCK)\n" +
                "  INNER JOIN " + tableName1 + "as RES_LOC WITH(NOLOCK) ON RES_LOC.RES_ALL_RES_ID = RES.RES_ID\n" +
                "  WHERE RES.RES_RESOURCE_CODE like '" + code + "'\n" +
                "  OR RES.RES_NAME like '" + code + "'\n" +
                "  OR RES_LOC.RES_ALL_LOCATION like '" + code + "'\n" +
                "  AND RES_ALL_HOME = 'True'\n" +
                "  ORDER BY RES_CREATED_DATE desc";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> RMTable = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody"));
        List<String> dbRMTable = new ArrayList<>();
        List<String> dbRMTable1 = new ArrayList<>();
        List<String> dbRMTable2 = new ArrayList<>();
        List<String> dbRMTable3 = new ArrayList<>();
        List<String> dbRMTable4 = new ArrayList<>();
        List<String> dbRMTable5 = new ArrayList<>();

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
                    "\t" + rs.getString(12));

            String a = rs.getString(4);
            dbRMTable.add(a);
            boolean booleanValue = false;
            for (WebElement rmTable : RMTable) {
                if (rmTable.getText().contains(a)) {
                    for (String dbrmTable : dbRMTable) {
                        if (dbrmTable.contains(a)) {
                            System.out.println("Code : " + a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbRMTable1.add(b);
            System.out.println("Display Name : " + b);

            String e = rs.getString(6);
            dbRMTable2.add(e);
            System.out.println("Owner IFTA : " + e);

            String c = rs.getString(2);
            dbRMTable3.add(c);
            System.out.println("Type : " + c);

            String d = rs.getString(11);
            dbRMTable4.add(d);
            System.out.println("Status : " + d);

            String f = rs.getString(12);
            dbRMTable5.add(d);
            System.out.println("Home Locations : " + f);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    @Given("Click on Report Button and Click on Search Results on Resource Maintenance")
    public void click_on_report_button_and_click_on_search_results_on_resource_maintenance() throws InterruptedException {
        driver.findElement(resourceMaintenancePage.Report).click();
        Thread.sleep(5000);
        driver.findElement(resourceMaintenancePage.SearchResults).click();
        Thread.sleep(2000);
    }

    @Given("Get SEARCH RESULTS Excel Report from Downloads for Resource Maintenance")
    public void get_search_results_excel_report_from_downloads_for_resource_maintenance() throws InterruptedException, IOException {
        System.out.println("=========================================");
        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");

        Thread.sleep(10000);
        String fileNameRM = (String) jse.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println("ALL RECORDS Excel Report File Name :-" + fileNameRM);
        driver.close();

        FileInputStream inputStream = new FileInputStream("C:\\Users\\Smriti Dhugana\\Downloads\\" + fileNameRM + "");
        HSSFWorkbook wbRM = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wbRM.getSheetAt(0);
        inputStream.close();

        FormulaEvaluator formulaEvaluator = wbRM.getCreationHelper().createFormulaEvaluator();

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
        wbRM.close();
        getAndSwitchToWindowHandles();
    }

    @Given("Validate SEARCH RESULTS Excel Report for Resource Maintenance with Database Record {string} {string} {string} and {string}")
    public void validate_search_results_excel_report_for_resource_maintenance_with_database_record_and(String environment, String tableName, String tableName1, String code) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT RES.RES_ID\n" +
                "      ,RES.RES_TYPE as [Type]\n" +
                "      ,RES.RES_COMP_CODE as [Company]\n" +
                "      ,RES.RES_RESOURCE_CODE as [Code]\n" +
                "      ,RES.RES_NAME as [Display Name]\n" +
                "      ,RES.RES_OWNER_IFTA as [Owner IFTA]\n" +
                "      ,RES.RES_CELL_CARRIER_CODE as [Cell Carrier]\n" +
                "      ,RES.RES_CELL_PHONE_NUM as [Cell Phone #]\n" +
                "      ,RES.RES_EMAIL as [Email Address]\n" +
                "      ,RES.RES_EFS_CARD_NUM as [EFS Card #]\n" +
                "      ,RES.RES_STATUS as [Status]\n" +
                "      ,RES_LOC.[RES_ALL_LOCATION] as [Home Locations]\n" +
                "  FROM " + tableName + " as RES WITH(NOLOCK)\n" +
                "  INNER JOIN " + tableName1 + "as RES_LOC WITH(NOLOCK) ON RES_LOC.RES_ALL_RES_ID = RES.RES_ID\n" +
                "  WHERE RES.RES_RESOURCE_CODE like '" + code + "'\n" +
                "  OR RES.RES_NAME like '" + code + "'\n" +
                "  OR RES_LOC.RES_ALL_LOCATION like '" + code + "'\n" +
                "  AND RES_ALL_HOME = 'True'\n" +
                "  ORDER BY RES_CREATED_DATE desc";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> RMTable = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody"));
        List<String> dbRMTable = new ArrayList<>();
        List<String> dbRMTable1 = new ArrayList<>();
        List<String> dbRMTable2 = new ArrayList<>();
        List<String> dbRMTable3 = new ArrayList<>();
        List<String> dbRMTable4 = new ArrayList<>();
        List<String> dbRMTable5 = new ArrayList<>();

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
                    "\t" + rs.getString(12));

            String a = rs.getString(4);
            dbRMTable.add(a);
            boolean booleanValue = false;
            for (WebElement rmTable : RMTable) {
                if (rmTable.getText().contains(a)) {
                    for (String dbrmTable : dbRMTable) {
                        if (dbrmTable.contains(a)) {
                            System.out.println("Code : " + a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbRMTable1.add(b);
            System.out.println("Display Name : " + b);

            String e = rs.getString(6);
            dbRMTable2.add(e);
            System.out.println("Owner IFTA : " + e);

            String c = rs.getString(2);
            dbRMTable3.add(c);
            System.out.println("Type : " + c);

            String d = rs.getString(11);
            dbRMTable4.add(d);
            System.out.println("Status : " + d);

            String f = rs.getString(12);
            dbRMTable5.add(d);
            System.out.println("Home Locations : " + f);
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

//............................................/ #83 @ResourceMaintenanceView/Edit /................................................//

    @Given("Identify the Code to Edit {string} and Get the Record Returned")
    public void identify_the_code_to_edit_and_get_the_record_returned(String code) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/thead/tr/th[3]/div/div"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(code)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(5000);
        System.out.println("=========================================");
        System.out.println("Total Records Returned on RESOURCE MAINTENANCE: " + driver.findElement(resourceMaintenancePage.TotalRecordsReturned).getText());
        log.info(driver.findElement(resourceMaintenancePage.DataTable).getText());
        Thread.sleep(5000);
    }


    @Given("Validate the Record Returned with Database Record {string} {string} {string} and {string}")
    public void validate_the_record_returned_with_database_record_and(String environment, String tableName, String tableName1, String code) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT RES.RES_ID\n" +
                "      ,RES.RES_TYPE as [Type]\n" +
                "      ,RES.RES_COMP_CODE as [Company]\n" +
                "      ,RES.RES_RESOURCE_CODE as [Code]\n" +
                "      ,RES.RES_NAME as [Display Name]\n" +
                "      ,RES.RES_OWNER_IFTA as [Owner IFTA]\n" +
                "      ,RES.RES_CELL_CARRIER_CODE as [Cell Carrier]\n" +
                "      ,RES.RES_CELL_PHONE_NUM as [Cell Phone #]\n" +
                "      ,RES.RES_EMAIL as [Email Address]\n" +
                "      ,RES.RES_EFS_CARD_NUM as [EFS Card #]\n" +
                "      ,RES.RES_STATUS as [Status]\n" +
                "      ,RES_LOC.[RES_ALL_LOCATION] as [Home Locations]\n" +
                "  FROM " + tableName + " as RES WITH(NOLOCK)\n" +
                "  INNER JOIN " + tableName1 + "as RES_LOC WITH(NOLOCK) ON RES_LOC.RES_ALL_RES_ID = RES.RES_ID\n" +
                "  WHERE RES.RES_RESOURCE_CODE like '" + code + "'\n" +
                "  OR RES.RES_NAME like '" + code + "'\n" +
                "  OR RES_LOC.RES_ALL_LOCATION like '" + code + "'\n" +
                "  AND RES_ALL_HOME = 'True'\n" +
                "  ORDER BY RES_CREATED_DATE desc";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> RMTable = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody"));
        List<String> dbRMTable = new ArrayList<>();
        List<String> dbRMTable1 = new ArrayList<>();
        List<String> dbRMTable2 = new ArrayList<>();
        List<String> dbRMTable3 = new ArrayList<>();
        List<String> dbRMTable4 = new ArrayList<>();
        List<String> dbRMTable5 = new ArrayList<>();

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
                    "\t" + rs.getString(12));

            String a = rs.getString(4);
            dbRMTable.add(a);
            boolean booleanValue = false;
            for (WebElement rmTable : RMTable) {
                if (rmTable.getText().contains(a)) {
                    for (String dbrmTable : dbRMTable) {
                        if (dbrmTable.contains(a)) {
                            System.out.println("Code : " + a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbRMTable1.add(b);
            System.out.println("Display Name : " + b);

            String e = rs.getString(6);
            dbRMTable2.add(e);
            System.out.println("Owner IFTA : " + e);

            String c = rs.getString(2);
            dbRMTable3.add(c);
            System.out.println("Type : " + c);

            String d = rs.getString(11);
            dbRMTable4.add(d);
            System.out.println("Status : " + d);

            String f = rs.getString(12);
            dbRMTable5.add(d);
            System.out.println("Home Locations : " + f);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

    @Given("Click on View on Resource Maintenance")
    public void click_on_view_on_resource_maintenance() throws InterruptedException {
        driver.findElement(resourceMaintenancePage.View).click();
        System.out.println("=========================================");
        Thread.sleep(3000);
        System.out.println("VIEW :");
        System.out.println(driver.findElement(By.id("lblCode")).getText());
        System.out.println(driver.findElement(By.id("tableResult2")).getText());
        System.out.println();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"LocationEquipFileDetailsPartial_I\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"LocationEquipFileDetailsPartial_I\"]")).sendKeys("AAR");
        driver.findElement(By.xpath("//*[@id=\"btnAdd\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(5000);

        driver.findElement(resourceMaintenancePage.View).click();
        Thread.sleep(3000);
        System.out.println("VIEW (LOCATION ADDED) :");
        System.out.println(driver.findElement(By.id("lblCode")).getText());
        System.out.println(driver.findElement(By.id("tableResult2")).getText());
        System.out.println();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//*[@id=\"setMargin\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"btnOK\"]/img")).click();
        Thread.sleep(5000);

        driver.findElement(resourceMaintenancePage.View).click();
        Thread.sleep(3000);
        System.out.println("VIEW (LOCATION DELETED) :");
        System.out.println(driver.findElement(By.id("lblCode")).getText());
        System.out.println(driver.findElement(By.id("tableResult2")).getText());
        System.out.println();
        Thread.sleep(3000);
        System.out.println("=========================================");
    }

    @Given("Click on Edit on Resource Maintenance")
    public void click_on_edit_on_resource_maintenance() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.Edit).click();
        Thread.sleep(2000);
    }

    @Given("Change ACTIVE Status to INACTIVE and Save, and Click on Search {string}")
    public void change_active_status_to_inactive_and_save_and_click_on_search(String code) throws InterruptedException {
        Thread.sleep(2000);
        WebElement wb = driver.findElement(resourceMaintenancePage.StatusEdit);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='INACTIVE';", wb);
        Thread.sleep(3000);
        driver.findElement(resourceMaintenancePage.SaveEdit).click();
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.Ok).click();
        driver.findElement(resourceMaintenancePage.Search).click();
        Thread.sleep(3000);
    }

    @Given("Get the Records Returned and Validate the Records Returned when ACTIVE Status changed to INACTIVE with Database Record {string} {string} {string} and {string}")
    public void get_the_records_returned_and_validate_the_records_returned_when_active_status_changed_to_inactive_with_database_record_and(String environment, String tableName, String tableName1, String code) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        Thread.sleep(5000);
        System.out.println("Record Returned when ACTIVE Status changed to INACTIVE: " + driver.findElement(resourceMaintenancePage.TotalRecordsReturned).getText());
        log.info(driver.findElement(resourceMaintenancePage.DataTableBody).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT RES.RES_ID\n" +
                "      ,RES.RES_TYPE as [Type]\n" +
                "      ,RES.RES_COMP_CODE as [Company]\n" +
                "      ,RES.RES_RESOURCE_CODE as [Code]\n" +
                "      ,RES.RES_NAME as [Display Name]\n" +
                "      ,RES.RES_OWNER_IFTA as [Owner IFTA]\n" +
                "      ,RES.RES_CELL_CARRIER_CODE as [Cell Carrier]\n" +
                "      ,RES.RES_CELL_PHONE_NUM as [Cell Phone #]\n" +
                "      ,RES.RES_EMAIL as [Email Address]\n" +
                "      ,RES.RES_EFS_CARD_NUM as [EFS Card #]\n" +
                "      ,RES.RES_STATUS as [Status]\n" +
                "      ,RES_LOC.[RES_ALL_LOCATION] as [Home Locations]\n" +
                "  FROM " + tableName + " as RES WITH(NOLOCK)\n" +
                "  INNER JOIN " + tableName1 + "as RES_LOC WITH(NOLOCK) ON RES_LOC.RES_ALL_RES_ID = RES.RES_ID\n" +
                "  WHERE RES.RES_RESOURCE_CODE like '" + code + "'\n" +
                "  OR RES.RES_NAME like '" + code + "'\n" +
                "  OR RES_LOC.RES_ALL_LOCATION like '" + code + "'\n" +
                "  AND RES_ALL_HOME = 'True'\n" +
                "  ORDER BY RES_CREATED_DATE desc";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> RMTable = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody"));
        List<String> dbRMTable = new ArrayList<>();
        List<String> dbRMTable1 = new ArrayList<>();
        List<String> dbRMTable2 = new ArrayList<>();
        List<String> dbRMTable3 = new ArrayList<>();
        List<String> dbRMTable4 = new ArrayList<>();
        List<String> dbRMTable5 = new ArrayList<>();

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
                    "\t" + rs.getString(12));

            String a = rs.getString(4);
            dbRMTable.add(a);
            boolean booleanValue = false;
            for (WebElement rmTable : RMTable) {
                if (rmTable.getText().contains(a)) {
                    for (String dbrmTable : dbRMTable) {
                        if (dbrmTable.contains(a)) {
                            System.out.println("Code : " + a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbRMTable1.add(b);
            System.out.println("Display Name : " + b);

            String e = rs.getString(6);
            dbRMTable2.add(e);
            System.out.println("Owner IFTA : " + e);

            String c = rs.getString(2);
            dbRMTable3.add(c);
            System.out.println("Type : " + c);

            String d = rs.getString(11);
            dbRMTable4.add(d);
            System.out.println("Status : " + d);

            String f = rs.getString(12);
            dbRMTable5.add(d);
            System.out.println("Home Locations : " + f);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");

    }

    @Given("Revert Back Changes from INACTIVE Status to ACTIVE and Save, and Click on Search {string}")
    public void revert_back_changes_from_inactive_status_to_active_and_save_and_click_on_search(String code) throws InterruptedException {
        Thread.sleep(2000);
        WebElement wbb = driver.findElement(resourceMaintenancePage.StatusEdit);
        JavascriptExecutor jsee = (JavascriptExecutor) driver;
        jsee.executeScript("arguments[0].value='ACTIVE';", wbb);
        Thread.sleep(3000);
        Thread.sleep(3000);
        driver.findElement(resourceMaintenancePage.SaveEdit).click();
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.Ok).click();
        driver.findElement(resourceMaintenancePage.Search).click();
        Thread.sleep(3000);
    }

    @Given("Get the Records Returned and Validate the Records Returned when INACTIVE Status changed to ACTIVE  with Database Record {string} {string} {string} and {string}")
    public void get_the_records_returned_and_validate_the_records_returned_when_inactive_status_changed_to_active_with_database_record_and(String environment, String tableName, String tableName1, String code) throws InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        Thread.sleep(5000);
        System.out.println("Record Returned when INACTIVE Status changed to ACTIVE: " + driver.findElement(resourceMaintenancePage.TotalRecordsReturned).getText());
        log.info(driver.findElement(resourceMaintenancePage.DataTableBody).getText());
        Thread.sleep(5000);

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT RES.RES_ID\n" +
                "      ,RES.RES_TYPE as [Type]\n" +
                "      ,RES.RES_COMP_CODE as [Company]\n" +
                "      ,RES.RES_RESOURCE_CODE as [Code]\n" +
                "      ,RES.RES_NAME as [Display Name]\n" +
                "      ,RES.RES_OWNER_IFTA as [Owner IFTA]\n" +
                "      ,RES.RES_CELL_CARRIER_CODE as [Cell Carrier]\n" +
                "      ,RES.RES_CELL_PHONE_NUM as [Cell Phone #]\n" +
                "      ,RES.RES_EMAIL as [Email Address]\n" +
                "      ,RES.RES_EFS_CARD_NUM as [EFS Card #]\n" +
                "      ,RES.RES_STATUS as [Status]\n" +
                "      ,RES_LOC.[RES_ALL_LOCATION] as [Home Locations]\n" +
                "  FROM " + tableName + " as RES WITH(NOLOCK)\n" +
                "  INNER JOIN " + tableName1 + "as RES_LOC WITH(NOLOCK) ON RES_LOC.RES_ALL_RES_ID = RES.RES_ID\n" +
                "  WHERE RES.RES_RESOURCE_CODE like '" + code + "'\n" +
                "  OR RES.RES_NAME like '" + code + "'\n" +
                "  OR RES_LOC.RES_ALL_LOCATION like '" + code + "'\n" +
                "  AND RES_ALL_HOME = 'True'\n" +
                "  ORDER BY RES_CREATED_DATE desc";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> RMTable = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody"));
        List<String> dbRMTable = new ArrayList<>();
        List<String> dbRMTable1 = new ArrayList<>();
        List<String> dbRMTable2 = new ArrayList<>();
        List<String> dbRMTable3 = new ArrayList<>();
        List<String> dbRMTable4 = new ArrayList<>();
        List<String> dbRMTable5 = new ArrayList<>();

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
                    "\t" + rs.getString(12));

            String a = rs.getString(4);
            dbRMTable.add(a);
            boolean booleanValue = false;
            for (WebElement rmTable : RMTable) {
                if (rmTable.getText().contains(a)) {
                    for (String dbrmTable : dbRMTable) {
                        if (dbrmTable.contains(a)) {
                            System.out.println("Code : " + a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbRMTable1.add(b);
            System.out.println("Display Name : " + b);

            String e = rs.getString(6);
            dbRMTable2.add(e);
            System.out.println("Owner IFTA : " + e);

            String c = rs.getString(2);
            dbRMTable3.add(c);
            System.out.println("Type : " + c);

            String d = rs.getString(11);
            dbRMTable4.add(d);
            System.out.println("Status : " + d);

            String f = rs.getString(12);
            dbRMTable5.add(d);
            System.out.println("Home Locations : " + f);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #84a @ValidCodeForTypeLocOnResourceMaintenanceNewForm /................................................//

    @Given("Locate a Record from Database for Valid Code for Type LOC in New Form on Resource Maintenance {string} {string}")
    public void locate_a_record_from_database_for_valid_code_for_type_loc_in_new_form_on_resource_maintenance(String environment, String tableName) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT " +
                "       LOCATION_COMP_CODE, " +
                "       LOCATION_CODE, " +
                "       LOCATION_STATUS FROM " + tableName + "\n" +
                "       WHERE  LOCATION_COMP_CODE = 'EVA'\n" +
                "       AND LOCATION_STATUS = 'ACTIVE'";

        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            System.out.println(res.getString(1) +
                    "\t" + res.getString(2) +
                    "\t" + res.getString(3));
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #85b @ResourceMaintenanceNew /................................................//
    @Given("Click on New on Resource Maintenance")
    public void click_on_new_on_resource_maintenance() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.New).click();
    }


    @Given("Enter all the details {string} {string} {string} {string} {string} {string}")
    public void enter_all_the_details_eva(String type, String company, String code, String homeLocation, String ownerIFTA, String status) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(resourceMaintenancePage.HeaderNew).getText());
        System.out.println("=========================================");
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.TypeNew).sendKeys(type);
        driver.findElement(resourceMaintenancePage.TypeNew).click();
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.CompanyNew).sendKeys(company);
        driver.findElement(resourceMaintenancePage.CompanyNew).click();
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.CodeNew).sendKeys(code);
        driver.findElement(resourceMaintenancePage.CodeNew).click();
        Thread.sleep(2000);
        WebElement wbb = driver.findElement(resourceMaintenancePage.HomeLocationNew);
        JavascriptExecutor jsee = (JavascriptExecutor) driver;
        jsee.executeScript("arguments[0].value='" + homeLocation + "';", wbb);
        Thread.sleep(2000);

        driver.findElement(resourceMaintenancePage.OwnerIFTANew).sendKeys(ownerIFTA);
        driver.findElement(resourceMaintenancePage.OwnerIFTANew).click();
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.StatusNew).sendKeys(status);
        driver.findElement(resourceMaintenancePage.StatusNew).click();
        Thread.sleep(2000);
    }


    @Given("Click on Save on Add Resource Information")
    public void click_on_save_on_add_resource_information() throws InterruptedException {
        Thread.sleep(3000);
        WebElement saveNew = driver.findElement(resourceMaintenancePage.SaveNew);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", saveNew);
        Thread.sleep(3000);
        driver.findElement(resourceMaintenancePage.Ok).click();
        Thread.sleep(3000);;
    }


    @Given("Get the Total Records Returned on Resource Maintenance Table for Newly created Record {string} {string}")
    public void get_the_total_records_returned_on_resource_maintenance_table_for_newly_created_record(String type, String code) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(resourceMaintenancePage.SearchBox).sendKeys(code);
        Thread.sleep(1000);
      //  driver.findElement(resourceMaintenancePage.Search).click();

        WebElement search = driver.findElement(resourceMaintenancePage.Search);
        JavascriptExecutor executorA = (JavascriptExecutor) driver;
        executorA.executeScript("arguments[0].click();", search);
        Thread.sleep(3000);


        driver.findElement(By.xpath("//*[@id=\"img0\"]")).click();
        List<WebElement> list = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/thead/tr/th[1]/div/div"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(type)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }


        driver.findElement(By.xpath("//*[@id=\"img2\"]")).click();
        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/thead/tr/th[3]/div/div"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(code)) {
                Thread.sleep(3000);
                webElement.click();
                break;
            }
        }

        System.out.println("=========================================");
        System.out.println("Record Returned for Newly created Record: " + driver.findElement(resourceMaintenancePage.TotalRecordsReturned).getText());
        log.info(driver.findElement(resourceMaintenancePage.DataTableBody).getText());
        Thread.sleep(5000);
    }

    @Given("Validate the Records Returned for NEW on Resource Maintenance with Database Record {string} {string} {string} and {string} {string} {string} {string} {string} {string}")
    public void validate_the_records_returned_for_new_on_resource_maintenance_with_database_record_and(String environment, String tableName, String tableName1, String type, String company, String code, String homeLocation, String ownerIFTA, String status) throws SQLException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ......");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "SELECT RES.RES_ID\n" +
                "      ,RES.RES_TYPE as [Type]\n" +
                "      ,RES.RES_COMP_CODE as [Company]\n" +
                "      ,RES.RES_RESOURCE_CODE as [Code]\n" +
                "      ,RES.RES_NAME as [Display Name]\n" +
                "      ,RES.RES_OWNER_IFTA as [Owner IFTA]\n" +
                "      ,RES.RES_CELL_CARRIER_CODE as [Cell Carrier]\n" +
                "      ,RES.RES_CELL_PHONE_NUM as [Cell Phone #]\n" +
                "      ,RES.RES_EMAIL as [Email Address]\n" +
                "      ,RES.RES_EFS_CARD_NUM as [EFS Card #]\n" +
                "      ,RES.RES_STATUS as [Status]\n" +
                "      ,RES_LOC.[RES_ALL_LOCATION] as [Home Locations]\n" +
                "  FROM " + tableName + " as RES WITH(NOLOCK)\n" +
                "  INNER JOIN " + tableName1 + "as RES_LOC WITH(NOLOCK) ON RES_LOC.RES_ALL_RES_ID = RES.RES_ID\n" +
                "  WHERE RES.RES_RESOURCE_CODE like '" + code + "'\n" +
                "  OR RES_LOC.RES_ALL_LOCATION like '" + code + "'\n" +
                "  AND RES_ALL_HOME = 'True'\n" +
                "  AND RES_TYPE = '" + type + "'\n" +
                "  AND RES_COMP_CODE = '" + company + "'\n" +
           //     "  AND RES_ALL_LOCATION = '" + homeLocation + "'\n" +
                "  AND RES_OWNER_IFTA = '" + ownerIFTA + "'\n" +
                "  AND RES_STATUS= '" + status + "'\n" +
                "  ORDER BY RES_CREATED_DATE desc";

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<WebElement> RMTable = driver.findElements(By.xpath("//*[@id=\"tblResourceMaintenance\"]/tbody"));
        List<String> dbRMTable = new ArrayList<>();
        List<String> dbRMTable1 = new ArrayList<>();
        List<String> dbRMTable2 = new ArrayList<>();
        List<String> dbRMTable3 = new ArrayList<>();
        List<String> dbRMTable4 = new ArrayList<>();
        List<String> dbRMTable5 = new ArrayList<>();

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
                    "\t" + rs.getString(12));

            String a = rs.getString(4);
            dbRMTable.add(a);
            boolean booleanValue = false;
            for (WebElement rmTable : RMTable) {
                if (rmTable.getText().contains(a)) {
                    for (String dbrmTable : dbRMTable) {
                        if (dbrmTable.contains(a)) {
                            System.out.println("Code : " + a);
                            booleanValue = true;
                        }
                    }
                    break;
                }
                if (booleanValue) {
                    assertTrue("Assertion Passed!!", true);
                } else {
                    fail("AssertValue is not present!!");
                }
            }

            String b = rs.getString(5);
            dbRMTable1.add(b);
            System.out.println("Display Name : " + b);

            String e = rs.getString(6);
            dbRMTable2.add(e);
            System.out.println("Owner IFTA : " + e);

            String c = rs.getString(2);
            dbRMTable3.add(c);
            System.out.println("Type : " + c);

            String d = rs.getString(11);
            dbRMTable4.add(d);
            System.out.println("Status : " + d);

            String f = rs.getString(12);
            dbRMTable5.add(d);
            System.out.println("Home Locations : " + f);
            System.out.println();
        }
        System.out.println("Database closed ......");
        System.out.println("=========================================");
    }

}




