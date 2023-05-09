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
import stepDefinitions.Pages.EquipmentConsoleLoginPage;
import stepDefinitions.Pages.EquipmentConsoleTractorAdjustmentsPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class EquipmentConsoleTractorAdjustmentsStepDef {

    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();
    String URL = "";

    EquipmentConsoleLoginPage equipmentConsoleLoginPage = new EquipmentConsoleLoginPage(driver);
    EquipmentConsoleTractorAdjustmentsPage equipmentConsoleTractorAdjustmentsPage = new EquipmentConsoleTractorAdjustmentsPage(driver);
    private static Statement stmt;
    BrowserDriverInitialization browserDriverInitialization = new BrowserDriverInitialization();


    //............................................/ BACKGROUND /................................................//
    @After("@FailureScreenShot8")
    public void takeScreenshotOnFailure8(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] src = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(src, "image/png", "screenshot");
            System.out.println("Closing EBH ........");
            driver.close();
            driver.quit();
        }
    }

    @Given("Run Test for {string} on Browser {string} and Enter the url for Tractor")
    public void run_test_for_on_browser_and_enter_the_url_for_tractor(String environment1, String browser) throws MalformedURLException {
        URL = browserDriverInitialization.getDataFromPropertiesFileForEquipmentConsole(environment1, browser);
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

    @Given("Login to the Portal with USERNAME {string} and PASSWORD {string} for Tractor")
    public void login_to_the_portal_with_username_and_password_for_tractor(String username, String password) {
        driver.findElement(equipmentConsoleLoginPage.username).sendKeys(username);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(equipmentConsoleLoginPage.username));
        wait.until(ExpectedConditions.visibilityOfElementLocated(equipmentConsoleLoginPage.password));
        driver.findElement(equipmentConsoleLoginPage.password).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(equipmentConsoleLoginPage.loginButton));
        driver.findElement(equipmentConsoleLoginPage.loginButton).click();
    }

    @And("Click NO on alert")
    public void click_no_on_alert() {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNo).click();
    }

    @Then("^Close all open Browsers on Equipment Console$")
    public void close_all_open_Browsers_on_Equipment_Console() {
        driver.close();
        driver.quit();
    }


    //....................../   #85 @IdentifyInvoiceAndValidTractorNoForTractorReimbursement/Deduct /..............................//

    @Given("^Locate a Record from Database for Tractor Reimbursement, Tractor Deduct \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void Locate_a_Record_from_Database_for_Tractor_Reimbursement_tractor_deduct(String environment, String tableName, String tableName1, String tableName2, String tableName3, String tableName4, String tableName5, String location) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "    WITH \n" +
                "           PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "           AS (\n" +
                "           SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "           From " + tableName1 + " With(Nolock)\n" +
                "           Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "           AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "           AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "           Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "           ), \n" +
                "           IRRCTE ( InvoiceRegisterID, Agent, ProNumber, AgentStatus, CorpStatus)\n" +
                "           AS (\n" +
                "           SELECT  InvoiceRegisterID, Agent, ProNumber, AgentStatus, CorpStatus\n" +
                "           From " + tableName + " With(Nolock)\n" +
                "           Where     AgentStatus IN ('CorpReview', 'AgentReview') and Agent is NOT Null and ProNumber  <> ''\n" +
                "           Group By  InvoiceRegisterID, Agent, ProNumber, AgentStatus, CorpStatus\n" +
                "           )\n" +
                "           ,\n" +
                "           SICTE (Agent_Stl_Agent_Code, Agent_Stl_Company_Code)\n" +
                "           AS (\n" +
                "           SELECT  Agent_Stl_Agent_Code, Agent_Stl_Company_Code\n" +
                "           From " + tableName2 + "  With(Nolock)\n" +
                "           Group By Agent_Stl_Agent_Code, Agent_Stl_Company_Code\n" +
                "           )\n" +
                "           ,\n" +
                "           SLCTE (Sl_Agent)\n" +
                "           AS (\n" +
                "           SELECT  distinct substring(STL_PRONUM, 1,3) as Sl_Agent\n" +
                "           From " + tableName3 + " With(Nolock)\n" +
                "\t         Group By substring(STL_PRONUM, 1,3)\n" +
                "           )\n" +
                "           Select Distinct ir.InvoiceNumber, ir.InvoiceDate, irr.Agent, irr.ProNumber, ir.TotalDue, ir.TypeOfBill, Matrix_Prim_Vendor, MATRIX_COMP_CODE, sl.Sl_Agent,  AgentStatus, CorpStatus\n" +
                "           FROM [Evans].[dbo].[InvoiceRegister]ir With(Nolock)\n" +
                "           inner Join IRRCTE irr With(Nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId\n" +
                "           inner Join PMCTE apm With(Nolock) on irr.Agent = apm.MATRIX_LOC_CODE\n" +
                "           Inner join " + tableName4 + " o With(Nolock) on o.Ord_loc = irr.Agent and o.Ord_num = irr.ProNumber\n" +
                "           Inner join " + tableName5 + " L With(Nolock) on l.LOCATION_CODE = o.ord_loc\n" +
                "           Inner join SICTE si With(Nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE\n" +
                "           Inner join SLCTE sl With(Nolock) on o.Ord_loc = sl_Agent\n" +
                "           Where InvoiceDate >= '01/01/2021' \n" +
                "           and Agent = '"+ location +"'" +
                "           Order by IRR.Agent, ir.InvoiceNumber, ir.InvoiceDate, IRR.ProNumber\n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder = new ArrayList<>();
        List<String> stringBuilder2 = new ArrayList<>();

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
                    "\t" + res.getString(11));

            String a = res.getString(1);
            System.out.println("Invoice Number = " + a);
            String a3 = res.getString(3);
            System.out.println("Agent = " + a3);
            String a4 = res.getString(4);
            System.out.println("Pro Number = " + a4);

            StringBuilder sb2 = new StringBuilder();
            sb2.append(res.getString(3)).append(res.getString(4));
            stringBuilder2.add(sb2.toString());
            System.out.println("Agent and Pro Number:  " + sb2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("Locate a Record from Database for Valid Tractor Number for Tractor Reimbursement, Tractor Deduct {string} {string} {string}")
    public void locate_a_record_from_database_for_valid_tractor_number_for_tractor_reimbursement_tractor_deduct(String environment, String tableName6, String location) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select Distinct top 50 tv.TRAC_VEND_UNITNO [Tractor No.], " +
                "       tv.TRAC_VEND_VENDOR_CODE [Vendor Code], " +
                "       tv.TRAC_VEND_LOC, " +
                "       tv.TRAC_VEND_COMP_CODE [Company Code], *\n" +
                "       From " + tableName6 + " tv With(Nolock)\n" +
                "       Where tv.TRAC_VEND_COMP_CODE = 'EVA' " +
                "       and tv.TRAC_VEND_LOC = '" + location + "'\n" +
                "       Order by tv.TRAC_VEND_UNITNO";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

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
            String a1 = res.getString(1);
            String a2 = res.getString(10);
            System.out.println("Tractor No =             " + a1);
            System.out.println("TRAC_VEND_ACCTG_STATUS = " + a2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


//................................/ #86 @TractorReimbursement /.......................................//

    @Given("Enter Invoice Number {string} in Search Criteria on Equipment Console Interface for Tractor")
    public void enter_invoice_number_in_search_criteria_on_equipment_console_interface_for_tractor(String invoiceNo) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.invoice).sendKeys(invoiceNo);
        WebElement refresh = driver.findElement(equipmentConsoleTractorAdjustmentsPage.refresh);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", refresh);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.clearFilters).click();
        Thread.sleep(3000);
    }

    @Given("Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review {string} for Tractor")
    public void select_agent_status_button_for_a_record_that_has_a_agent_status_agent_review_or_corp_review_for_tractor(String agentStatus) {

        WebElement agentSTATUS = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", agentSTATUS);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("Select Agent {string} and ProNo {string} for Tractor")
    public void select_agent_and_pro_no_for_tractor(String agent, String proNum) throws InterruptedException {
        Thread.sleep(3000);
        WebElement aGENT= driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", aGENT);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement proNUM = driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo);
        JavascriptExecutor executorPN = (JavascriptExecutor)driver;
        executorPN.executeScript("arguments[0].click();", proNUM);

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        boolean booleanValue = false;
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(proNum)) {
                webElement1.click();
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("AssertValue is present !!", true);
        } else {
            fail("AssertValue is not present!!");
        }
    }

    @Given("Select DriverReimbursement on Status {string}")
    public void select_driverreimbursement_on_status(String driverReimbursement) throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentReview).click();

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(driverReimbursement)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("Enter a Valid Tractor No {string}")
    public void enter_a_valid_tractor_no(String tractor) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).sendKeys(tractor);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).click();
    }

    @Given("^Enter Amount or # of Days, Effective Date = Todays Date \"([^\"]*)\" \"([^\"]*)\"$")
    public void enter_Amount_or_of_Days_Effective_Date_Todays_Date(String amount, String effDate) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).sendKeys(amount);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).click();
        Thread.sleep(2000);
    }

    @Given("^Verify Notes Column has the Customer Number and Agent, prefilled in the notes column$")
    public void verify_Notes_Column_has_the_Customer_Number_and_agent_prefilled_in_the_notes_column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("AGENT STATUS, AGENT REVIEW, DRIVER REIMBURSEMENT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDR).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDR).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDR).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDR).getAttribute("value"));
        System.out.println();
        System.out.println("=========================================");
    }

    @Given("^Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No \"([^\"]*)\"$")
    public void enter_a_message_into_the_Notes_Column_and_make_sure_to_enter_a_comma_Select_Ok_Select_Go_Select_No(String notes) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesDR).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okDR).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noDR).click();
    }

    @Given("^Verify previously entered data remained same for Driver Reimbursement, Select Go, Select Yes, Main Form appears$")
    public void verify_previously_entered_data_remained_same_for_driver_reimbursementSelect_Go_Select_Yes_Main_Form_appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDR).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDR).click();
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
    }


    @Given("^Query Data in Tractor Adjustments Table, There should be no record in Tractor Adjustments table for this transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void query_Data_in_Tractor_Adjustments_Table_There_should_be_no_record_in_tractor_adjustments_table_for_this_transaction(String environment, String tableName, String tableName1, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Select  ta.TRACTOR_ADJUST_COMP_CODE, " +
                "        ta.TRACTOR_ADJUST_LOC_OR_TRAC,  " +
                "        ta.TRACTOR_ADJUST_VENDOR_CODE, " +
                "        tv.TRAC_VEND_VENDOR_CODE, " +
                "        ta.TRACTOR_ADJUST_START_DATE, " +
                "        ta.*\n" +
                "        From " + tableName + " ta With(Nolock)\n" +
                "        Left Join " + tableName1 + " tv With(Nolock) on tv.TRAC_VEND_UNITNO = ta.TRACTOR_ADJUST_LOC_OR_TRAC and ta.TRACTOR_ADJUST_VENDOR_CODE = tv.TRAC_VEND_VENDOR_CODE\n" +
                "        Where ta.TRACTOR_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN') and tv.TRAC_VEND_COMP_CODE = 'EVA'\n" +
                "        and ta.TRACTOR_ADJUST_CREATED_DATE >= '" + createdDate + "' " +
                "        and ta.TRACTOR_ADJUST_ORDER_NO = '" + orderNum + "'\n" +
                "        and ta.TRACTOR_ADJUST_LOC_OR_TRAC = '" + tractorNo + "' " +
                "        and ta.tractor_Adjust_Created_By = '" + createdBy + "' ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            dbAATable.add(a28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a28)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a28)) {
                        System.out.println("Order No: " + a28);
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

            System.out.println();
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a1);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a2);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a3);
            System.out.println("TRAC_VEND_VENDOR_CODE               = " + a4);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a5);
            System.out.println("TRACTOR_ADJUST_ID                   = " + a6);
            System.out.println("TRACTOR_ADJUST_PAY_CODE             = " + a7);
            System.out.println("TRACTOR_ADJUST_STATUS               = " + a8);
            System.out.println("TRACTOR_ADJUST_FREQ                 = " + a9);
            System.out.println("TRACTOR_ADJUST_AMOUNT_TYPE          = " + a10);
            System.out.println("TRACTOR_ADJUST_AMOUNT               = " + a11);
            System.out.println("TRACTOR_ADJUST_TOP_LIMIT            = " + a12);
            System.out.println("TRACTOR_ADJUST_TOTAL_TO_DATE        = " + a13);
            System.out.println("TRACTOR_ADJUST_LAST_DATE            = " + a14);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a15);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a16);
            System.out.println("TRACTOR_ADJUST_MAX_TRANS            = " + a17);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a18);
            System.out.println("TRACTOR_ADJUST_END_DATE             = " + a19);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR           = " + a20);
            System.out.println("TRACTOR_ADJUST_LAST_AMOUNT          = " + a21);
            System.out.println("TRACTOR_ADJUST_NOTE                 = " + a22);
            System.out.println("TRACTOR_ADJUST_FC_TRANS_ID          = " + a23);
            System.out.println("TRACTOR_ADJUST_CREATED_BY           = " + a24);
            System.out.println("TRACTOR_ADJUST_CREATED_DATE         = " + a25);
            System.out.println("TRACTOR_ADJUST_UPDATED_BY           = " + a26);
            System.out.println("TRACTOR_ADJUST_LAST_UPDATED         = " + a27);
            System.out.println("TRACTOR_ADJUST_ORDER_NO             = " + a28);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a29);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR_PAY_CODE  = " + a30);
            System.out.println("TRACTOR_ADJUST_DISCOUNT             = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Given("^Click on Clear Column Filters$")
    public void Click_on_Click_Filters() throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.clearFilters).click();
        Thread.sleep(4000);
    }

    @Given("^Select Corp Status = Corp Review for that same record that has Agent Status = DriverReimbursement and Corp Status = Corp Review for Driver Reimbursement \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void select_Corp_Status_Corp_Review_for_that_same_record_that_has_Agent_Status_DriverReimbursement_and_Corp_Status_Corp_Review_for_Driver_Reimbursement(String corpStatus, String agentStatus1, String agent1, String proNum) throws InterruptedException {

        WebElement agentStatus = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus);
        JavascriptExecutor executorAS = (JavascriptExecutor) driver;
        executorAS.executeScript("arguments[0].click();", agentStatus);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus1)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement agent = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent);
        JavascriptExecutor executorA = (JavascriptExecutor) driver;
        executorA.executeScript("arguments[0].click();", agent);

        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(agent1)) {
                webElement2.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement proNo = driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo);
        JavascriptExecutor executorP = (JavascriptExecutor) driver;
        executorP.executeScript("arguments[0].click();", proNo);

        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(proNum)) {
                webElement3.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement corpStatuss = driver.findElement(equipmentConsoleTractorAdjustmentsPage.corpStatus);
        JavascriptExecutor executorCS = (JavascriptExecutor) driver;
        executorCS.executeScript("arguments[0].click();", corpStatuss);

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(corpStatus)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(5000);
    }


    @Given("^Select DriverReimbursement on CropReview$")
    public void select_DriverReimbursement_on_CropReview() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.corpReview).click();
        Thread.sleep(2000);
        List<WebElement> list = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains("DriverReimbursement")) {
                webElement.click();
                break;
            }
        }
    }

    @Given("^Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered\\. Enter a different Amount, Days or Effective Date \"([^\"]*)\"$")
    public void tractor_Days_Amount_and_Notes_Columns_are_filled_in_with_the_same_information_that_was_previously_entered_Enter_a_different_Amount_Days_or_Effective_Date(String ofDays) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).clear();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).sendKeys(ofDays);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).click();
    }

    @Given("^Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column$")
    public void verify_Notes_Column_has_the_Customer_Number_and_agent_prefilled_in_the_Notes_Column() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("CORP STATUS, CORP REVIEW, DRIVER REIMBURSEMENT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDR).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDR).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDR).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDR).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).isDisplayed();
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).getAttribute("value"));
        System.out.println();
        System.out.println("=========================================");
    }

    @Given("^Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No \"([^\"]*)\"$")
    public void enter_a_Message_into_the_Notes_Column_and_make_sure_to_enter_a_Comma_Select_Ok_Select_Go_Select_No(String notes1) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).sendKeys(notes1);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesDR).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okDR).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noDR).click();
    }


    @Given("^Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Main Form Appears$")
    public void verify_previously_entered_data_remained_same_for_Driver_Reimbursement_Corp_Review_Select_Go_Select_Yes_Main_Form_Appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDR).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDR).click();
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
    }

    @Given("^Verify Agent Status and Corp Status = DriverReimbursement in Main Form$")
    public void verify_Agent_Status_and_Corp_Status_DriverReimbursement_in_Main_Form() throws InterruptedException {
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.dataTable).getText());
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[2]/a")).getText();
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        assertEquals("Agent Status and Corp Status = Agent Reimbursement", agentStatus, corpStatus);
        System.out.println(" Both Agent Status and Corp Status are SAME!!");
        System.out.println("=========================================");
    }


    @Given("^Query Data in Tractor Adjustments SQL Table, There should be one record in Tractor Adjustments table for this transaction \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void query_Data_in_Tractor_Adjustments_SQL_Table_There_should_be_one_record_in_Tractor_Adjustments_table_for_this_transaction(String environment, String tableName, String tableName1, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Select  ta.TRACTOR_ADJUST_COMP_CODE, " +
                "        ta.TRACTOR_ADJUST_LOC_OR_TRAC,  " +
                "        ta.TRACTOR_ADJUST_VENDOR_CODE, " +
                "        tv.TRAC_VEND_VENDOR_CODE, " +
                "        ta.TRACTOR_ADJUST_START_DATE, " +
                "        ta.*\n" +
                "        From " + tableName + " ta With(Nolock)\n" +
                "        Left Join " + tableName1 + " tv With(Nolock) on tv.TRAC_VEND_UNITNO = ta.TRACTOR_ADJUST_LOC_OR_TRAC and ta.TRACTOR_ADJUST_VENDOR_CODE = tv.TRAC_VEND_VENDOR_CODE\n" +
                "        Where ta.TRACTOR_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN') and tv.TRAC_VEND_COMP_CODE = 'EVA'\n" +
                "        and ta.TRACTOR_ADJUST_CREATED_DATE >= '" + createdDate + "' " +
                "        and ta.TRACTOR_ADJUST_ORDER_NO = '" + orderNum + "'\n" +
                "        and ta.TRACTOR_ADJUST_LOC_OR_TRAC = '" + tractorNo + "' " +
                "        and ta.tractor_Adjust_Created_By = '" + createdBy + "' ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            dbAATable.add(a28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a28)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a28)) {
                        System.out.println("Order No: " + a28);
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

            System.out.println();
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a1);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a2);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a3);
            System.out.println("TRAC_VEND_VENDOR_CODE               = " + a4);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a5);
            System.out.println("TRACTOR_ADJUST_ID                   = " + a6);
            System.out.println("TRACTOR_ADJUST_PAY_CODE             = " + a7);
            System.out.println("TRACTOR_ADJUST_STATUS               = " + a8);
            System.out.println("TRACTOR_ADJUST_FREQ                 = " + a9);
            System.out.println("TRACTOR_ADJUST_AMOUNT_TYPE          = " + a10);
            System.out.println("TRACTOR_ADJUST_AMOUNT               = " + a11);
            System.out.println("TRACTOR_ADJUST_TOP_LIMIT            = " + a12);
            System.out.println("TRACTOR_ADJUST_TOTAL_TO_DATE        = " + a13);
            System.out.println("TRACTOR_ADJUST_LAST_DATE            = " + a14);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a15);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a16);
            System.out.println("TRACTOR_ADJUST_MAX_TRANS            = " + a17);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a18);
            System.out.println("TRACTOR_ADJUST_END_DATE             = " + a19);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR           = " + a20);
            System.out.println("TRACTOR_ADJUST_LAST_AMOUNT          = " + a21);
            System.out.println("TRACTOR_ADJUST_NOTE                 = " + a22);
            System.out.println("TRACTOR_ADJUST_FC_TRANS_ID          = " + a23);
            System.out.println("TRACTOR_ADJUST_CREATED_BY           = " + a24);
            System.out.println("TRACTOR_ADJUST_CREATED_DATE         = " + a25);
            System.out.println("TRACTOR_ADJUST_UPDATED_BY           = " + a26);
            System.out.println("TRACTOR_ADJUST_LAST_UPDATED         = " + a27);
            System.out.println("TRACTOR_ADJUST_ORDER_NO             = " + a28);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a29);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR_PAY_CODE  = " + a30);
            System.out.println("TRACTOR_ADJUST_DISCOUNT             = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    // ........................................../ #87 @TractorReimbursement/TractorWithoutRecordOnTractorVendorTable /..................................................../

    @Given("^Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered\\. Enter Effective Date \"([^\"]*)\"$")
    public void tractor_Days_Amount_and_Notes_Columns_are_filled_in_with_the_same_information_that_was_previously_entered_Enter_Effective_Date(String effDate) throws InterruptedException {

        String effMonths = effDate.split("-")[0].substring(0, 2);

        String effMonth = null;
        switch (effMonths) {
            case "01":
                effMonth = "January";
                break;
            case "02":
                effMonth = "February";
                break;
            case "03":
                effMonth = "March";
                break;
            case "04":
                effMonth = "April";
                break;
            case "05":
                effMonth = "May";
                break;
            case "06":
                effMonth = "June";
                break;
            case "07":
                effMonth = "July";
                break;
            case "08":
                effMonth = "August";
                break;
            case "09":
                effMonth = "September";
                break;
            case "10":
                effMonth = "October";
                break;
            case "11":
                effMonth = "November";
                break;
            case "12":
                effMonth = "December";
                break;
        }

        String effDay = effDate.split("-")[1].substring(0, 2);

        String effYear = effDate.split("-")[2].substring(0, 4);

        WebElement selectDate = driver.findElement(By.xpath("//*[@id=\"DriverReimbursementEffectiveDate\"]"));
        selectDate.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ui-datepicker-calendar")));

        assert effMonth != null;
        if (effMonth.equals("February") && Integer.parseInt(effDay) > 29) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }
        if (Integer.parseInt(effDay) > 31) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }

        String monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();

        String month = monthYear.split(" ")[0].trim();
        String year = monthYear.split(" ")[1].trim();

        while (!(month.equals(effMonth) && year.equals(effYear))) {
            driver.findElement(By.xpath("//a[@title='Next']")).click();
            monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();
            System.out.println(monthYear);
            month = monthYear.split(" ")[0].trim();
            year = monthYear.split(" ")[1].trim();
        }
        try {
            driver.findElement(By.xpath("//a[text()='" + effDay + "']")).click();
        } catch (Exception e) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
        }
    }


    @Given("^Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Select Ok$")
    public void verify_previously_entered_data_remained_same_for_Driver_Reimbursement_Corp_Review_Select_Go_Select_Yes_Select_Ok() throws InterruptedException {
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDR).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        Thread.sleep(1000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDR).click();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]/div[2]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.crossCloseDR).click();
    }


    @Given("Verify Agent Status = DriverReimbursment and Corp Status = CorpReview in Main Form")
    public void verify_agent_status_driver_reimbursment_and_corp_status_corp_review_in_main_form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.dataTable).getText());
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[2]/a")).getText();
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        System.out.println("=========================================");
    }


    // ........................................../ #88 @TractorReimbursement/TractorNotCurrentlyAssignedToAgentOnDriverReimbursementForm /..................................................../

    @Given("^Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Select Ok for Tractor NOT currently assigned to the Agent on the DRIVER REIMBURSEMENT Form$")
    public void verify_previously_entered_data_remained_same_for_Driver_Reimbursement_Corp_Review_Select_Go_Select_Yes_Select_Ok_for_Tractor_NOT_currently_assigned_to_the_agent_on_the_driver_reimbursement_form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDR).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        Thread.sleep(1000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDR).click();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[3]/div/button[1]/span")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[12]/div[11]/div/button/span")).click();  //ok
    }


    // ........................................../ #89 @TractorReimbursement/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent /..................................................../

    @Given("Change Tractor Number Driver Reimbursement {string}")
    public void change_tractor_number_driver_reimbursement(String tractorNo) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).clear();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).sendKeys(tractorNo);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).click();
    }

    @Given("Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered. Enter Effective Date {string} for Tractor that does NOT have a Tractor record with a Status of Current")
    public void tractor_days_amount_and_notes_columns_are_filled_in_with_the_same_information_that_was_previously_entered_enter_effective_date_for_tractor_that_does_not_have_a_tractor_record_with_a_status_of_current(String effDate) {

        String effMonths = effDate.split("-")[0].substring(0, 2);

        String effMonth = null;
        switch (effMonths) {
            case "01":
                effMonth = "January";
                break;
            case "02":
                effMonth = "February";
                break;
            case "03":
                effMonth = "March";
                break;
            case "04":
                effMonth = "April";
                break;
            case "05":
                effMonth = "May";
                break;
            case "06":
                effMonth = "June";
                break;
            case "07":
                effMonth = "July";
                break;
            case "08":
                effMonth = "August";
                break;
            case "09":
                effMonth = "September";
                break;
            case "10":
                effMonth = "October";
                break;
            case "11":
                effMonth = "November";
                break;
            case "12":
                effMonth = "December";
                break;
        }

        String effDay = effDate.split("-")[1].substring(0, 2);

        String effYear = effDate.split("-")[2].substring(0, 4);

        WebElement selectDate = driver.findElement(By.xpath("//*[@id=\"DriverReimbursementEffectiveDate\"]")); //*[@id="DriverReimbursementEffectiveDate"]
        selectDate.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ui-datepicker-calendar")));

        assert effMonth != null;
        if (effMonth.equals("February") && Integer.parseInt(effDay) > 29) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }
        if (Integer.parseInt(effDay) > 31) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }

        String monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();

        String month = monthYear.split(" ")[0].trim();
        String year = monthYear.split(" ")[1].trim();

        while (!(month.equals(effMonth) && year.equals(effYear))) {
            driver.findElement(By.xpath("//a[@title='Next']")).click();
            monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();
            System.out.println(monthYear);
            month = monthYear.split(" ")[0].trim();
            year = monthYear.split(" ")[1].trim();
        }
        try {
            driver.findElement(By.xpath("//a[text()='" + effDay + "']")).click();
        } catch (Exception e) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
        }

    }


    @Given("^Verify previously entered data remained same for Driver Reimbursement Corp Review, Select Go, Select Yes, Select Ok, for Tractor that does NOT have a Tractor record with a Status of Current$")
    public void verify_previously_entered_data_remained_same_for_Driver_Reimbursement_Corp_Review_Select_Go_Select_Yes_Select_Ok_for_Tractor_that_does_NOT_have_a_Tractor_record_with_a_Status_of_Current() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDR).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDR).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDR).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDR).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDR).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDR).click();
        Thread.sleep(1000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverReimbursementsAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDR).click();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[3]/div/button[1]/span")).click();
        Thread.sleep(2000);

        System.out.println("ERROR MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]")).getText()); //*[@id="dialog-message"]
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[12]/div[11]/div/button/span")).click();  //ok
    }


    // ........................................../ #90 @TractorDeduct /..................................................../

    @Given("Enter Pro Number {string} in Search Criteria on Equipment Console Interface for Tractor")
    public void enter_pro_number_in_search_criteria_on_equipment_console_interface_for_tractor(String proNumber) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNumber).sendKeys(proNumber);
        WebElement refresh = driver.findElement(equipmentConsoleTractorAdjustmentsPage.refresh);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", refresh);
        Thread.sleep(3000);
    }

    @Given("Select DriverDeduct on Status {string}")
    public void select_driverdeduct_on_status(String status) throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentReview).click();

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("Enter a Valid Tractor No {string} for Driver Deduct")
    public void enter_a_valid_tractor_no_for_driver_deduct(String tractor) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).sendKeys(tractor);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).click();
    }


    @Given("^Enter Amount or # of Days, Effective Date = Todays Date \"([^\"]*)\" \"([^\"]*)\" for Driver Deduct$")
    public void enter_Amount_or_of_Days_Effective_Date_Todays_Date_for_Driver_Deduct(String amount, String effDate) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).sendKeys(amount);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).click();
        Thread.sleep(2000);
    }

    @Given("^Verify Notes Column has the Customer Number and Agent, prefilled in the notes column for Driver Deduct$")
    public void verify_Notes_Column_has_the_Customer_Number_and_agent_prefilled_in_the_notes_column_for_Driver_Deduct() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("AGENT STATUS, AGENT REVIEW, DRIVER DEDUCT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDD).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDD).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDD).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDD).getAttribute("value"));
        System.out.println();
        System.out.println("=========================================");
    }


    @Given("Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No {string} for Driver Deduct")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_select_go_select_no_for_driver_deduct(String notes) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesDD).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okDD).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noDD).click();
    }


    @Given("Verify previously entered data remained same for Driver Deduct, Select Go, Select Yes, Main Form appears")
    public void verify_previously_entered_data_remained_same_for_driver_deduct_select_go_select_yes_main_form_appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDD).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDD).click();
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
    }

    @Given("Query Data in Tractor Adjustments Table for Driver Deduct, There should be no record in Tractor Adjustments table for this transaction {string} {string} {string} {string} {string} {string} {string}")
    public void query_data_in_tractor_adjustments_table_for_driver_deduct_there_should_be_no_record_in_tractor_adjustments_table_for_this_transaction_pro_order_num(String environment, String tableName, String tableName1, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Select  ta.TRACTOR_ADJUST_COMP_CODE, " +
                "        ta.TRACTOR_ADJUST_LOC_OR_TRAC,  " +
                "        ta.TRACTOR_ADJUST_VENDOR_CODE, " +
                "        tv.TRAC_VEND_VENDOR_CODE, " +
                "        ta.TRACTOR_ADJUST_START_DATE, " +
                "        ta.*\n" +
                "        From " + tableName + " ta With(Nolock)\n" +
                "        Left Join " + tableName1 + " tv With(Nolock) on tv.TRAC_VEND_UNITNO = ta.TRACTOR_ADJUST_LOC_OR_TRAC and ta.TRACTOR_ADJUST_VENDOR_CODE = tv.TRAC_VEND_VENDOR_CODE\n" +
                "        Where ta.TRACTOR_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN') and tv.TRAC_VEND_COMP_CODE = 'EVA'\n" +
                "        and ta.TRACTOR_ADJUST_CREATED_DATE >= '" + createdDate + "' " +
                "        and ta.TRACTOR_ADJUST_ORDER_NO = '" + orderNum + "'\n" +
                "        and ta.TRACTOR_ADJUST_LOC_OR_TRAC = '" + tractorNo + "' " +
                "        and ta.tractor_Adjust_Created_By = '" + createdBy + "' ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            dbAATable.add(a28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a28)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a28)) {
                        System.out.println("Order No: " + a28);
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

            System.out.println();
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a1);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a2);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a3);
            System.out.println("TRAC_VEND_VENDOR_CODE               = " + a4);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a5);
            System.out.println("TRACTOR_ADJUST_ID                   = " + a6);
            System.out.println("TRACTOR_ADJUST_PAY_CODE             = " + a7);
            System.out.println("TRACTOR_ADJUST_STATUS               = " + a8);
            System.out.println("TRACTOR_ADJUST_FREQ                 = " + a9);
            System.out.println("TRACTOR_ADJUST_AMOUNT_TYPE          = " + a10);
            System.out.println("TRACTOR_ADJUST_AMOUNT               = " + a11);
            System.out.println("TRACTOR_ADJUST_TOP_LIMIT            = " + a12);
            System.out.println("TRACTOR_ADJUST_TOTAL_TO_DATE        = " + a13);
            System.out.println("TRACTOR_ADJUST_LAST_DATE            = " + a14);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a15);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a16);
            System.out.println("TRACTOR_ADJUST_MAX_TRANS            = " + a17);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a18);
            System.out.println("TRACTOR_ADJUST_END_DATE             = " + a19);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR           = " + a20);
            System.out.println("TRACTOR_ADJUST_LAST_AMOUNT          = " + a21);
            System.out.println("TRACTOR_ADJUST_NOTE                 = " + a22);
            System.out.println("TRACTOR_ADJUST_FC_TRANS_ID          = " + a23);
            System.out.println("TRACTOR_ADJUST_CREATED_BY           = " + a24);
            System.out.println("TRACTOR_ADJUST_CREATED_DATE         = " + a25);
            System.out.println("TRACTOR_ADJUST_UPDATED_BY           = " + a26);
            System.out.println("TRACTOR_ADJUST_LAST_UPDATED         = " + a27);
            System.out.println("TRACTOR_ADJUST_ORDER_NO             = " + a28);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a29);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR_PAY_CODE  = " + a30);
            System.out.println("TRACTOR_ADJUST_DISCOUNT             = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Then("Select Corp Status = CorpReview for that same record that has Agent Status = DriverDeduct and Corp Status = CorpReview for Driver Deduct {string} {string} {string} {string}")
    public void select_corp_status_corp_review_for_that_same_record_that_has_agent_status_driver_deduct_and_corp_status_corp_review_for_driver_deduct(String agentStatus1, String agent, String proNum, String corpStatus) throws InterruptedException {
        WebElement agentStatus = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus);
        JavascriptExecutor executorAS = (JavascriptExecutor) driver;
        executorAS.executeScript("arguments[0].click();", agentStatus);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus1)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement aGENT = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent);
        JavascriptExecutor executorA = (JavascriptExecutor) driver;
        executorA.executeScript("arguments[0].click();", aGENT);

        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(agent)) {
                webElement2.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement proNo = driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo);
        JavascriptExecutor executorP = (JavascriptExecutor) driver;
        executorP.executeScript("arguments[0].click();", proNo);

        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(proNum)) {
                webElement3.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement corpSTATUS = driver.findElement(equipmentConsoleTractorAdjustmentsPage.corpStatus);
        JavascriptExecutor executorCS = (JavascriptExecutor) driver;
        executorCS.executeScript("arguments[0].click();", corpSTATUS);

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(corpStatus)) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(5000);
    }

    @Then("Select DriverDeduct on CropReview")
    public void select_driver_deduct_on_crop_review() {

        WebElement corpReview = driver.findElement(equipmentConsoleTractorAdjustmentsPage.corpReview);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", corpReview);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains("DriverDeduct")) {
                webElement.click();
                break;
            }
        }
    }


    @Given("^Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered\\. Enter a different Amount, Days or Effective Date \"([^\"]*)\" for Driver Deduct$")
    public void tractor_Days_Amount_and_Notes_Columns_are_filled_in_with_the_same_information_that_was_previously_entered_Enter_a_different_Amount_Days_or_Effective_Date_for_Driver_Deduct(String ofDays) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).clear();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).sendKeys(ofDays);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).click();
    }


    @Given("^Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Driver Deduct$")
    public void verify_Notes_Column_has_the_Customer_Number_and_agent_prefilled_in_the_Notes_Column_for_Driver_Deduct() throws InterruptedException {
        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("CORP STATUS, CORP REVIEW, DRIVER DEDUCT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDD).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.customerNotesDD).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDD).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesDD).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).isDisplayed();
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).getAttribute("value"));
        System.out.println();
        System.out.println("=========================================");
    }

    @Given("^Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No \"([^\"]*)\" for Driver Deduct$")
    public void enter_a_Message_into_the_Notes_Column_and_make_sure_to_enter_a_Comma_Select_Ok_Select_Go_Select_No_for_Driver_Deduct(String notes1) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).sendKeys(notes1);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesDD).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okDD).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noDD).click();
    }


    @Then("Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Main Form Appears")
    public void verify_previously_entered_data_remained_same_for_driver_deduct_corp_review_select_go_select_yes_main_form_appears() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDD).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDD).click();
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click(); //ok
    }

    @Then("Verify Agent Status and Corp Status = DriverDeduct in Main Form")
    public void verify_agent_status_and_corp_status_driver_deduct_in_main_form() throws InterruptedException {
        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"griddata_wrapper\"]")).getText());
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[2]/a")).getText();
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        assertEquals("Agent Status and Corp Status = Agent Reimbursement", agentStatus, corpStatus);
        System.out.println(" Both Agent Status and Corp Status are SAME!!");
        System.out.println("=========================================");
    }

    @Then("Query Data in Tractor Adjustments SQL Table for Driver Deduct, There should be one record in Tractor Adjustments table for this transaction {string} {string} {string} {string} {string} {string} {string}")
    public void query_data_in_tractor_adjustments_sql_table_for_driver_deduct_there_should_be_one_record_in_tractor_adjustments_table_for_this_transaction_pro_order_num(String environment, String tableName, String tableName1, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = " Select  ta.TRACTOR_ADJUST_COMP_CODE, " +
                "        ta.TRACTOR_ADJUST_LOC_OR_TRAC,  " +
                "        ta.TRACTOR_ADJUST_VENDOR_CODE, " +
                "        tv.TRAC_VEND_VENDOR_CODE, " +
                "        ta.TRACTOR_ADJUST_START_DATE, " +
                "        ta.*\n" +
                "        From " + tableName + " ta With(Nolock)\n" +
                "        Left Join " + tableName1 + " tv With(Nolock) on tv.TRAC_VEND_UNITNO = ta.TRACTOR_ADJUST_LOC_OR_TRAC and ta.TRACTOR_ADJUST_VENDOR_CODE = tv.TRAC_VEND_VENDOR_CODE\n" +
                "        Where ta.TRACTOR_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN') and tv.TRAC_VEND_COMP_CODE = 'EVA'\n" +
                "        and ta.TRACTOR_ADJUST_CREATED_DATE >= '" + createdDate + "' " +
                "        and ta.TRACTOR_ADJUST_ORDER_NO = '" + orderNum + "'\n" +
                "        and ta.TRACTOR_ADJUST_LOC_OR_TRAC = '" + tractorNo + "' " +
                "        and ta.tractor_Adjust_Created_By = '" + createdBy + "' ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            dbAATable.add(a28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);

            boolean booleanValue = false;
            if (Objects.equals(orderNum, a28)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a28)) {
                        System.out.println("Order No: " + a28);
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

            System.out.println();
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a1);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a2);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a3);
            System.out.println("TRAC_VEND_VENDOR_CODE               = " + a4);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a5);
            System.out.println("TRACTOR_ADJUST_ID                   = " + a6);
            System.out.println("TRACTOR_ADJUST_PAY_CODE             = " + a7);
            System.out.println("TRACTOR_ADJUST_STATUS               = " + a8);
            System.out.println("TRACTOR_ADJUST_FREQ                 = " + a9);
            System.out.println("TRACTOR_ADJUST_AMOUNT_TYPE          = " + a10);
            System.out.println("TRACTOR_ADJUST_AMOUNT               = " + a11);
            System.out.println("TRACTOR_ADJUST_TOP_LIMIT            = " + a12);
            System.out.println("TRACTOR_ADJUST_TOTAL_TO_DATE        = " + a13);
            System.out.println("TRACTOR_ADJUST_LAST_DATE            = " + a14);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a15);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a16);
            System.out.println("TRACTOR_ADJUST_MAX_TRANS            = " + a17);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a18);
            System.out.println("TRACTOR_ADJUST_END_DATE             = " + a19);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR           = " + a20);
            System.out.println("TRACTOR_ADJUST_LAST_AMOUNT          = " + a21);
            System.out.println("TRACTOR_ADJUST_NOTE                 = " + a22);
            System.out.println("TRACTOR_ADJUST_FC_TRANS_ID          = " + a23);
            System.out.println("TRACTOR_ADJUST_CREATED_BY           = " + a24);
            System.out.println("TRACTOR_ADJUST_CREATED_DATE         = " + a25);
            System.out.println("TRACTOR_ADJUST_UPDATED_BY           = " + a26);
            System.out.println("TRACTOR_ADJUST_LAST_UPDATED         = " + a27);
            System.out.println("TRACTOR_ADJUST_ORDER_NO             = " + a28);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a29);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR_PAY_CODE  = " + a30);
            System.out.println("TRACTOR_ADJUST_DISCOUNT             = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    // ........................................../ #91 @TractorDeduct/TractorWithoutRecordOnTractorVendorTable /..................................................../

    @Given("^Tractor, Days, Amount and Notes Columns are filled in with the same information that was previously entered\\. Enter Effective Date \"([^\"]*)\" for Driver Deduct$")
    public void tractor_Days_Amount_and_Notes_Columns_are_filled_in_with_the_same_information_that_was_previously_entered_Enter_Effective_Date_for_Driver_Deduct(String effDate) throws InterruptedException {

        String effMonths = effDate.split("-")[0].substring(0, 2);

        String effMonth = null;
        switch (effMonths) {
            case "01":
                effMonth = "January";
                break;
            case "02":
                effMonth = "February";
                break;
            case "03":
                effMonth = "March";
                break;
            case "04":
                effMonth = "April";
                break;
            case "05":
                effMonth = "May";
                break;
            case "06":
                effMonth = "June";
                break;
            case "07":
                effMonth = "July";
                break;
            case "08":
                effMonth = "August";
                break;
            case "09":
                effMonth = "September";
                break;
            case "10":
                effMonth = "October";
                break;
            case "11":
                effMonth = "November";
                break;
            case "12":
                effMonth = "December";
                break;
        }

        String effDay = effDate.split("-")[1].substring(0, 2);

        String effYear = effDate.split("-")[2].substring(0, 4);


        WebElement selectDate = driver.findElement(By.xpath("//*[@id=\"DriverDeductEffectiveDate\"]")); //*[@id="DriverDeductEffectiveDate"]
        selectDate.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ui-datepicker-calendar")));

        assert effMonth != null;
        if (effMonth.equals("February") && Integer.parseInt(effDay) > 29) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }
        if (Integer.parseInt(effDay) > 31) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }

        String monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();

        String month = monthYear.split(" ")[0].trim();
        String year = monthYear.split(" ")[1].trim();

        while (!(month.equals(effMonth) && year.equals(effYear))) {
            driver.findElement(By.xpath("//a[@title='Next']")).click();
            monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();
            System.out.println(monthYear);
            month = monthYear.split(" ")[0].trim();
            year = monthYear.split(" ")[1].trim();
        }
        try {
            driver.findElement(By.xpath("//a[text()='" + effDay + "']")).click();
        } catch (Exception e) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
        }
    }


    @Then("Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Select Ok")
    public void verify_previously_entered_data_remained_same_for_driver_deduct_corp_review_select_go_select_yes_select_ok() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDD).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDD).click();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]/div[2]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click(); //ok
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.crossCloseDR).click();
    }

    @Then("Verify Agent Status = DriverDeduct and Corp Status = CorpReview in Main Form")
    public void verify_agent_status_driver_deduct_and_corp_status_corp_review_in_main_form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.dataTable).getText());
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[2]/a")).getText();
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        ;
        System.out.println("=========================================");
    }

    //............................................/ #92 @TractorDeduct/TractorNotCurrentlyAssignedToAgentOnDriverDeductForm /................................................//

    @Given("^Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Select Ok for Tractor NOT currently assigned to the Agent on the DRIVER DEDUCT Form$")
    public void verify_previously_entered_data_remained_same_for_Driver_Deduct_Corp_Review_Select_Go_Select_Yes_Select_Ok_for_Tractor_NOT_currently_assigned_to_the_Agent_on_the_DRIVER_DEDUCT_Form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDD).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDD).click();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[3]/div/button[1]/span")).click(); //Yes
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[12]/div[11]/div/button/span")).click();  //ok
    }


    //............................................/ #93 @TractorDeduct/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent /................................................//

    @Given("Change Tractor Number Driver Deduct {string}")
    public void change_tractor_number_driver_deduct(String tractorNo) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).clear();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).sendKeys(tractorNo);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).click();
    }





    @Given("^Verify previously entered data remained same for Driver Deduct Corp Review, Select Go, Select Yes, Select Ok, for Tractor that does NOT have a Tractor record with a Status of Current$")
    public void verify_previously_entered_data_remained_same_for_Driver_Deduct_Corp_Review_Select_Go_Select_Yes_Select_Ok_for_Tractor_that_does_NOT_have_a_Tractor_record_with_a_Status_of_Current() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorDD).getAttribute("value"));
        System.out.println("Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.amountDD).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateDD).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysDD).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesDD).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goDD).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverDeductAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesDD).click();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[3]/div/button[1]/span")).click(); //Yes
        Thread.sleep(2000);
        System.out.println("ERROR MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[12]/div[11]/div/button/span")).click();  //ok
    }


    //............................................/ #94 @IdentifyInvoiceForSplitAmount /................................................//

    @Given("Locate a Record from Database for Valid Invoice Number for Split Amount {string} {string} {string} {string} {string} {string} {string}")
    public void locate_a_record_from_database_for_valid_invoice_number_for_split_amount(String environment, String tableName1, String tableName2, String tableName3, String tableName4, String tableName5, String agent) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();
        String query = "    Select top 500 ir.InvoiceNumber, " +
                "           ir.InvoiceDate, " +
                "           irr.Agent, " +
                "           irr.ProNumber, " +
                "           irr.AgentStatus, " +
                "           irr.CorpStatus, " +
                "           ir.TotalDue,  " +
                "           l.LOCATION_CODE, " +
                "           l.LOCATION_COMP_CODE,  " +
                "           si.AGENT_STL_AGENT_CODE, " +
                "           si.AGENT_STL_COMPANY_CODE, *\n" +
                "       FROM " + tableName1 + "irr With(nolock)\n" +
                "       Inner Join " + tableName2 + "ir With (nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId \n" +
                "       Inner join " + tableName3 + " L With(nolock) on l.LOCATION_CODE = irr.agent \n" +
                "       Inner Join " + tableName4 + " o With(nolock) on irr.Agent = o.ord_loc and irr.ProNumber = o.ord_num\n" +
                "       Inner Join " + tableName5 + " si With(nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE \n" +
                "       Where irr.CorpStatus in ('AgentReview', 'CorpReview') and InvoiceDate >= '01/01/2022'" +
                "       and irr.agent = '" + agent + "'";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder2 = new ArrayList<>();

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
                    "\t" + res.getString(20));

            String a = res.getString(1);
            System.out.println("Invoice Number = " + a);
            String a3 = res.getString(3);
            System.out.println("Agent = " + a3);
            String a4 = res.getString(4);
            System.out.println("Pro Number = " + a4);

            StringBuilder sb2 = new StringBuilder();
            sb2.append(res.getString(3)).append("").append(res.getString(4));
            stringBuilder2.add(sb2.toString());
            System.out.println("Agent and Pro Number:  " + sb2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Given("Locate a Record from Database for Tractor {string} {string} {string}")
    public void locate_a_record_from_database_for_tractor(String environment, String tableName6, String location) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select Distinct top 50 tv.TRAC_VEND_UNITNO [Tractor No.], " +
                "       tv.TRAC_VEND_VENDOR_CODE [Vendor Code], " +
                "       tv.TRAC_VEND_LOC, " +
                "       tv.TRAC_VEND_COMP_CODE [Company Code], *\n" +
                "       From " + tableName6 + " tv With(Nolock)\n" +
                "       Where tv.TRAC_VEND_COMP_CODE = 'EVA' " +
                "       and tv.TRAC_VEND_LOC = '" + location + "'\n" +
                "       Order by tv.TRAC_VEND_UNITNO";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

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
            String a1 = res.getString(1);
            String a2 = res.getString(10);
            System.out.println("Tractor No =             " + a1);
            System.out.println("TRAC_VEND_ACCTG_STATUS = " + a2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #95 @SplitAmount /................................................//

    @Given("Enter Invoice Number {string} in Search Criteria on Equipment Console Interface for Split Amount")
    public void enter_invoice_number_in_search_criteria_on_equipment_console_interface_for_split_amount(String invoiceNo) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.invoice).sendKeys(invoiceNo);
        WebElement refresh = driver.findElement(equipmentConsoleTractorAdjustmentsPage.refresh);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", refresh);
        Thread.sleep(5000);
    }

    @Given("Select Agent Status Button for a record that has a Agent Status, Agent Review or Corp Review {string} for Split Amount")
    public void select_agent_status_button_for_a_record_that_has_a_agent_status_agent_review_or_corp_review_for_split_amount(String agentStatus) {

        WebElement agentSTATUS = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", agentSTATUS);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus)) {
                webElement.click();
                break;
            }
        }
    }

    @Given("Select Agent {string} and ProNo {string} for Split Amount")
    public void select_agent_and_pro_no_for_split_amount(String agent, String proNum) throws InterruptedException {
        Thread.sleep(3000);
        WebElement aGENT= driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", aGENT);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);


        WebElement proNUM = driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo);
        JavascriptExecutor executorPN = (JavascriptExecutor)driver;
        executorPN.executeScript("arguments[0].click();", proNUM);

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        boolean booleanValue = false;
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains(proNum)) {
                webElement1.click();
                booleanValue = true;
                break;
            }
        }
        if (booleanValue) {
            Assert.assertTrue("AssertValue is present !!", true);
        } else {
            fail("AssertValue is not present!!");
        }
    }

    @Given("Select SplitAmount on Status {string}")
    public void select_split_amount_on_status(String status) throws InterruptedException {
        Thread.sleep(4000);

        WebElement agentReview = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentReview);
        JavascriptExecutor executorPN = (JavascriptExecutor)driver;
        executorPN.executeScript("arguments[0].click();", agentReview);

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list1) {
            if (webElement.getText().contains(status)) {
                webElement.click();
                break;
            }
        }
    }


    @Given("^Enter Agent Amount and Driver Amount or # of Days, Effective Date = Todays Date \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for Split Amount$")
    public void enter_agent_Amount_and_driver_amount_or_of_Days_Effective_Date_Todays_Date_for_Split_Amount(String agentAmount, String driverAmount, String effDate) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentAmountSA).sendKeys(agentAmount);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentAmountSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverAmountSA).sendKeys(driverAmount);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverAmountSA).click();
        Thread.sleep(2000);
    }

    @Given("Enter a Valid Tractor No {string} for Split Amount")
    public void enter_a_valid_tractor_no_for_split_amount(String tractor) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).sendKeys(tractor);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).click();
    }


    @Given("Verify Notes Column has the Customer Number, Chassis No and Container No, prefilled in the notes column for Split Amount")
    public void verify_notes_column_has_the_customer_number_chassis_no_and_container_no_prefilled_in_the_notes_column_for_split_amount() throws InterruptedException {

        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("AGENT STATUS, AGENT REVIEW, SPLIT AMOUNT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesSA).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesCustomerSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesChassisSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesContainerSA).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesSA).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesCustomerSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesAgentSA).getAttribute("value"));
        System.out.println();
        System.out.println("=========================================");
    }

    @Given("Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go, Select No {string} for Split Amount")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_select_go_select_no_for_split_amount(String notes) {

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noSA).click();
    }


    @Given("Verify previously entered data remained same for Split Amount, Select Go, Select Yes, Main Form appears")
    public void verify_previously_entered_data_remained_same_for_split_amount_select_go_select_yes_main_form_appears() throws InterruptedException {

        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Agent Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentAmountSA).getAttribute("value"));
        System.out.println("Driver Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverAmountSA).getAttribute("value"));
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).getAttribute("value"));
        System.out.println("No of Splits: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateSA).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysSA).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesSA).click();
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
    }

    @Given("Query Data in Agent_Adjustments Table for Split Amount, There should be no record in this table for this transaction {string} {string} {string} {string} {string} {string} {string}")
    public void query_data_in_agent_adjustments_table_for_split_amount_there_should_be_no_record_in_this_table_for_this_transaction(String environment, String tableName, String tableName1, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = ";WITH \n" +
                "       PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "       AS (\n" +
                "       SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "       From " + tableName1 + "  With(nolock)\n" +
                "       Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "       AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "       AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "       Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "       )\n" +
                "       Select 'Primary Vendor Info', " +
                "       pm.MATRIX_LOC_CODE, " +
                "       pm.MATRIX_COMP_CODE, " +
                "       pm.MATRIX_PRIM_VENDOR, '   ', " +
                "       'Agent Adjustment Record', \n" +
                "       aa.AGENT_ADJUST_ID, " +
                "       aa.AGENT_ADJUST_VENDOR_CODE, " +
                "       aa.AGENT_ADJUST_PAY_CODE, " +
                "       aa.AGENT_ADJUST_STATUS, " +
                "       aa.AGENT_ADJUST_FREQ, \n" +
                "       aa.AGENT_ADJUST_AMOUNT_TYPE, " +
                "       aa.AGENT_ADJUST_AMOUNT, " +
                "       aa.AGENT_ADJUST_TOP_LIMIT, " +
                "       aa.agent_adjust_total_to_date,  " +
                "       aa.AGENT_ADJUST_LAST_DATE,\n" +
                "       aa.AGENT_ADJUST_MAX_TRANS, " +
                "       aa.AGENT_ADJUST_START_DATE, " +
                "       aa.AGENT_ADJUST_END_DATE, " +
                "       aa.AGENT_ADJUST_PAY_VENDOR, " +
                "       aa.AGENT_ADJUST_LAST_AMOUNT,\n" +
                "       aa.AGENT_ADJUST_NOTE, " +
                "       aa.AGENT_ADJUST_CREATED_BY, " +
                "       aa.AGENT_ADJUST_CREATED_DATE, " +
                "       aa.AGENT_ADJUST_UPDATED_BY, " +
                "       aa.AGENT_ADJUST_LAST_UPDATED,\n" +
                "       aa.AGENT_ADJUST_IS_DELETED, " +
                "       aa.AGENT_ADJUST_PAY_VENDOR_PAY_CODE, " +
                "       aa.AGENT_ADJUST_APPLY_TO_AGENT, " +
                "       aa.AGENT_ADJUST_ORDER_NO, " +
                "       aa.AGENT_ADJUST_COMP_CODE\n" +
                "       From " + tableName + " AA With(nolock)\n" +
                "       Left Join PMCTE PM With(nolock)  on pm.MATRIX_LOC_CODE = aa.AGENT_ADJUST_APPLY_TO_AGENT  \n" +
                "       Where aa.AGENT_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "       and aa.AGENT_ADJUST_CREATED_DATE >= '" + createdDate + "' \n" +
                "       and aa.AGENT_ADJUST_ORDER_NO = '" + orderNum + "' \n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            dbAATable.add(a30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a30)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a30)) {
                        System.out.println("Order No: " + a30);
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

            System.out.println();
            System.out.println("                                  = " + a1);
            System.out.println("MATRIX_LOC_CODE                   = " + a2);
            System.out.println("MATRIX_COMP_CODE                  = " + a3);
            System.out.println("MATRIX_PRIM_VENDOR                = " + a4);
            System.out.println("                                  = " + a5);
            System.out.println("                                  = " + a6);
            System.out.println("AGENT_ADJUST_ID                   = " + a7);
            System.out.println("AGENT_ADJUST_VENDOR_CODE          = " + a8);
            System.out.println("AGENT_ADJUST_PAY_CODE             = " + a9);
            System.out.println("AGENT_ADJUST_STATUS               = " + a10);
            System.out.println("AGENT_ADJUST_FREQ                 = " + a11);
            System.out.println("AGENT_ADJUST_AMOUNT_TYPE          = " + a12);
            System.out.println("AGENT_ADJUST_AMOUNT               = " + a13);
            System.out.println("AGENT_ADJUST_TOP_LIMIT            = " + a14);
            System.out.println("agent_adjust_total_to_date        = " + a15);
            System.out.println("AGENT_ADJUST_LAST_DATE            = " + a16);
            System.out.println("AGENT_ADJUST_MAX_TRANS            = " + a17);
            System.out.println("AGENT_ADJUST_START_DATE           = " + a18);
            System.out.println("AGENT_ADJUST_END_DATE             = " + a19);
            System.out.println("AGENT_ADJUST_PAY_VENDOR           = " + a20);
            System.out.println("AGENT_ADJUST_LAST_AMOUNT          = " + a21);
            System.out.println("AGENT_ADJUST_NOTE                 = " + a22);
            System.out.println("AGENT_ADJUST_CREATED_BY           = " + a23);
            System.out.println("AGENT_ADJUST_CREATED_DATE         = " + a24);
            System.out.println("AGENT_ADJUST_UPDATED_BY           = " + a25);
            System.out.println("AGENT_ADJUST_LAST_UPDATED         = " + a26);
            System.out.println("AGENT_ADJUST_IS_DELETED           = " + a27);
            System.out.println("AGENT_ADJUST_PAY_VENDOR_PAY_CODE  = " + a28);
            System.out.println("AGENT_ADJUST_APPLY_TO_AGENT       = " + a29);
            System.out.println("AGENT_ADJUST_ORDER_NO             = " + a30);
            System.out.println("AGENT_ADJUST_COMP_CODE            = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");


    }

    @Given("Query Data in Tractor_Adjustments Table for Split Amount, There should be no record in this table for this transaction {string} {string} {string} {string} {string} {string} {string}")
    public void query_data_in_tractor_adjustments_table_for_split_amount_there_should_be_no_record_in_this_table_for_this_transaction(String environment, String tableName2, String tableName3, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select  tv.TRAC_VEND_COMP_CODE, " +
                "       tv.TRAC_VEND_UNITNO, " +
                "       tv.TRAC_VEND_VENDOR_CODE, '   ***   Tractor Adjustment Record   ***',\n" +
                "       ta.TRACTOR_ADJUST_COMP_CODE, " +
                "       ta.TRACTOR_ADJUST_LOC_OR_TRAC,  " +
                "       ta.TRACTOR_ADJUST_VENDOR_CODE,  " +
                "       ta.TRACTOR_ADJUST_START_DATE, ta.*\n" +
                "       From " + tableName2 + " TA With(Nolock)\n" +
                "       Left Join " + tableName3 + "  tv With(Nolock) on tv.TRAC_VEND_UNITNO = ta.TRACTOR_ADJUST_LOC_OR_TRAC \n" +
                "       Where ta.TRACTOR_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "       and ta.TRACTOR_ADJUST_CREATED_DATE >= '" + createdDate + "' " +
                "       and tv.TRAC_VEND_VENDOR_CODE = ta.TRACTOR_ADJUST_VENDOR_CODE\n" +
                "        and ta.TRACTOR_ADJUST_ORDER_NO = '" + orderNum + "'\n" +
                "        and ta.TRACTOR_ADJUST_LOC_OR_TRAC = '" + tractorNo + "' " +
                "        and ta.tractor_Adjust_Created_By = '" + createdBy + "' ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31) +
                    "\t" + res.getString(32) +
                    "\t" + res.getString(33) +
                    "\t" + res.getString(34));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            dbAATable.add(a28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);
            String a32 = res.getString(32);
            String a33 = res.getString(33);
            String a34 = res.getString(34);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a28)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a28)) {
                        System.out.println("Order No: " + a28);
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

            System.out.println();
            System.out.println("TRAC_VEND_COMP_CODE                 = " + a1);
            System.out.println("TRAC_VEND_UNITNO                    = " + a2);
            System.out.println("TRAC_VEND_VENDOR_CODE               = " + a3);
            System.out.println("                                    = " + a4);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a5);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a6);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a7);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a8);
            System.out.println("TRACTOR_ADJUST_ID                   = " + a9);
            System.out.println("TRACTOR_ADJUST_PAY_CODE             = " + a10);
            System.out.println("TRACTOR_ADJUST_STATUS               = " + a11);
            System.out.println("TRACTOR_ADJUST_FREQ                 = " + a12);
            System.out.println("TRACTOR_ADJUST_AMOUNT_TYPE          = " + a13);
            System.out.println("TRACTOR_ADJUST_AMOUNT               = " + a14);
            System.out.println("TRACTOR_ADJUST_TOP_LIMIT            = " + a15);
            System.out.println("TRACTOR_ADJUST_TOTAL_TO_DATE        = " + a16);
            System.out.println("TRACTOR_ADJUST_LAST_DATE            = " + a17);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a18);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a19);
            System.out.println("TRACTOR_ADJUST_MAX_TRANS            = " + a20);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a21);
            System.out.println("TRACTOR_ADJUST_END_DATE             = " + a22);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR           = " + a23);
            System.out.println("TRACTOR_ADJUST_LAST_AMOUNT          = " + a24);
            System.out.println("TRACTOR_ADJUST_NOTE                 = " + a25);
            System.out.println("TRACTOR_ADJUST_FC_TRANS_ID          = " + a26);
            System.out.println("TRACTOR_ADJUST_CREATED_BY           = " + a27);
            System.out.println("TRACTOR_ADJUST_CREATED_DATE         = " + a28);
            System.out.println("TRACTOR_ADJUST_UPDATED_BY           = " + a29);
            System.out.println("TRACTOR_ADJUST_LAST_UPDATED         = " + a30);
            System.out.println("TRACTOR_ADJUST_ORDER_NO             = " + a31);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a32);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR_PAY_CODE  = " + a33);
            System.out.println("TRACTOR_ADJUST_DISCOUNT             = " + a34);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    @Then("Select Corp Status = CorpReview for that same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount {string} {string} {string}")
    public void select_corp_status_corp_review_for_that_same_record_that_has_agent_status_split_amount_and_corp_status_corp_review_for_split_amount(String agentStatus1, String agent, String proNum) throws InterruptedException {

        WebElement agentStatus = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus);
        JavascriptExecutor executorAS = (JavascriptExecutor) driver;
        executorAS.executeScript("arguments[0].click();", agentStatus);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus1)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement aGENT = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent);
        JavascriptExecutor executorA = (JavascriptExecutor) driver;
        executorA.executeScript("arguments[0].click();", aGENT);

        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement2 : list2) {
            if (webElement2.getText().contains(agent)) {
                webElement2.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement proNo = driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo);
        JavascriptExecutor executorP = (JavascriptExecutor) driver;
        executorP.executeScript("arguments[0].click();", proNo);

        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement3 : list3) {
            if (webElement3.getText().contains(proNum)) {
                webElement3.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement corpSTATUS = driver.findElement(equipmentConsoleTractorAdjustmentsPage.corpStatus);
        JavascriptExecutor executorCS = (JavascriptExecutor) driver;
        executorCS.executeScript("arguments[0].click();", corpSTATUS);

        List<WebElement> list1 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement1 : list1) {
            if (webElement1.getText().contains("CorpReview")) {
                webElement1.click();
                break;
            }
        }
        Thread.sleep(5000);
    }

    @Then("Select SplitAmount on CropReview")
    public void select_split_amount_on_crop_review() {

        WebElement corpREVIEW = driver.findElement(equipmentConsoleTractorAdjustmentsPage.corpReview);
        JavascriptExecutor executorCS = (JavascriptExecutor) driver;
        executorCS.executeScript("arguments[0].click();", corpREVIEW);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='statusList']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains("SplitAmount")) {
                webElement.click();
                break;
            }
        }
    }

    @Then("The Days, Amount, Tractor and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits {string} for Split Amount")
    public void the_days_amount_tractor_and_notes_columns_are_filled_in_with_the_same_information_that_was_previously_entered_enter_a_different_amount_days_or_effective_date_or_no_of_splits_for_split_amount(String ofSplits) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).clear();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).sendKeys(ofSplits);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).click();
    }


    @Then("Verify Notes Column has the Customer Number and Agent, prefilled in the Notes Column for Split Amount")
    public void verify_notes_column_has_the_customer_number_and_agent_prefilled_in_the_notes_column_for_split_amount() throws InterruptedException {

        System.out.println("=========================================");
        Thread.sleep(8000);
        System.out.println("CORP STATUS, CORP REVIEW, SPLIT AMOUNT FORM: ");
        System.out.println("Already Prefilled NOTES: ");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesSA).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesCustomerSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesChassisSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentNotesContainerSA).getAttribute("value"));
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesSA).isDisplayed();
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesCustomerSA).getAttribute("value"));
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverNotesAgentSA).getAttribute("value"));
        System.out.println();
        System.out.println("=========================================");
    }

    @Then("Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok, Select Go, Select No {string} for Split Amount CorpReview")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_select_go_select_no_for_split_amount_corpReview(String notes1) throws InterruptedException {

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes1);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.noSA).click();
    }

    @Then("Verify previously entered data remained same for Split Amount Corp Review, Select Go, Select Yes, Main Form Appears")
    public void verify_previously_entered_data_remained_same_for_split_amount_corp_review_select_go_select_yes_main_form_appears() throws InterruptedException {

        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("PREVIOUSLY ENTERED DATAS:");
        System.out.println("Agent Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentAmountSA).getAttribute("value"));
        System.out.println("Driver Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverAmountSA).getAttribute("value"));
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).getAttribute("value"));
        System.out.println("No of Splits: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateSA).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysSA).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.yesSA).click();
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
    }


    @Then("Verify Agent Status and Corp Status = SplitAmount in Main Form")
    public void verify_agent_status_and_corp_status_split_amount_in_main_form() throws InterruptedException {


        Thread.sleep(8000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.dataTable).getText());
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[2]/a")).getText();
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        assertEquals("Agent Status and Corp Status = SplitAmount", agentStatus, corpStatus);
        System.out.println(" Both Agent Status and Corp Status are SAME!!");
        System.out.println("=========================================");
    }

    @Then("Query Data in Agent_Adjustments Table for Split Amount, There should be one record in this table for this transaction {string} {string} {string} {string} {string} {string} {string}")
    public void query_data_in_agent_adjustments_table_for_split_amount_there_should_be_one_record_in_this_table_for_this_transaction(String environment, String tableName, String tableName1, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = ";WITH \n" +
                "       PMCTE (MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE)\n" +
                "       AS (\n" +
                "       SELECT  MATRIX_LOC_CODE ,MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE\n" +
                "       From " + tableName1 + "  With(nolock)\n" +
                "       Where     ISNULL(MATRIX_BILLTO,'') = ''\n" +
                "       AND ISNULL(MATRIX_SHIPPER,'') = ''\n" +
                "       AND ISNULL(MATRIX_CONS,'') = ''\n" +
                "       Group By MATRIX_LOC_CODE, MATRIX_PRIM_VENDOR, MATRIX_COMP_CODE \n" +
                "       )\n" +
                "       Select 'Primary Vendor Info', " +
                "       pm.MATRIX_LOC_CODE, " +
                "       pm.MATRIX_COMP_CODE, " +
                "       pm.MATRIX_PRIM_VENDOR, '   ', " +
                "       'Agent Adjustment Record', \n" +
                "       aa.AGENT_ADJUST_ID, " +
                "       aa.AGENT_ADJUST_VENDOR_CODE, " +
                "       aa.AGENT_ADJUST_PAY_CODE, " +
                "       aa.AGENT_ADJUST_STATUS, " +
                "       aa.AGENT_ADJUST_FREQ, \n" +
                "       aa.AGENT_ADJUST_AMOUNT_TYPE, " +
                "       aa.AGENT_ADJUST_AMOUNT, " +
                "       aa.AGENT_ADJUST_TOP_LIMIT, " +
                "       aa.agent_adjust_total_to_date,  " +
                "       aa.AGENT_ADJUST_LAST_DATE,\n" +
                "       aa.AGENT_ADJUST_MAX_TRANS, " +
                "       aa.AGENT_ADJUST_START_DATE, " +
                "       aa.AGENT_ADJUST_END_DATE, " +
                "       aa.AGENT_ADJUST_PAY_VENDOR, " +
                "       aa.AGENT_ADJUST_LAST_AMOUNT,\n" +
                "       aa.AGENT_ADJUST_NOTE, " +
                "       aa.AGENT_ADJUST_CREATED_BY, " +
                "       aa.AGENT_ADJUST_CREATED_DATE, " +
                "       aa.AGENT_ADJUST_UPDATED_BY, " +
                "       aa.AGENT_ADJUST_LAST_UPDATED,\n" +
                "       aa.AGENT_ADJUST_IS_DELETED, " +
                "       aa.AGENT_ADJUST_PAY_VENDOR_PAY_CODE, " +
                "       aa.AGENT_ADJUST_APPLY_TO_AGENT, " +
                "       aa.AGENT_ADJUST_ORDER_NO, " +
                "       aa.AGENT_ADJUST_COMP_CODE\n" +
                "       From " + tableName + " AA With(nolock)\n" +
                "       Left Join PMCTE PM With(nolock)  on pm.MATRIX_LOC_CODE = aa.AGENT_ADJUST_APPLY_TO_AGENT  \n" +
                "       Where aa.AGENT_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "       and aa.AGENT_ADJUST_CREATED_DATE >= '" + createdDate + "' \n" +
                "       and aa.AGENT_ADJUST_ORDER_NO = '" + orderNum + "' \n";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            dbAATable.add(a30);
            String a31 = res.getString(31);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a30)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a30)) {
                        System.out.println("Order No: " + a30);
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

            System.out.println();
            System.out.println("                                  = " + a1);
            System.out.println("MATRIX_LOC_CODE                   = " + a2);
            System.out.println("MATRIX_COMP_CODE                  = " + a3);
            System.out.println("MATRIX_PRIM_VENDOR                = " + a4);
            System.out.println("                                  = " + a5);
            System.out.println("                                  = " + a6);
            System.out.println("AGENT_ADJUST_ID                   = " + a7);
            System.out.println("AGENT_ADJUST_VENDOR_CODE          = " + a8);
            System.out.println("AGENT_ADJUST_PAY_CODE             = " + a9);
            System.out.println("AGENT_ADJUST_STATUS               = " + a10);
            System.out.println("AGENT_ADJUST_FREQ                 = " + a11);
            System.out.println("AGENT_ADJUST_AMOUNT_TYPE          = " + a12);
            System.out.println("AGENT_ADJUST_AMOUNT               = " + a13);
            System.out.println("AGENT_ADJUST_TOP_LIMIT            = " + a14);
            System.out.println("agent_adjust_total_to_date        = " + a15);
            System.out.println("AGENT_ADJUST_LAST_DATE            = " + a16);
            System.out.println("AGENT_ADJUST_MAX_TRANS            = " + a17);
            System.out.println("AGENT_ADJUST_START_DATE           = " + a18);
            System.out.println("AGENT_ADJUST_END_DATE             = " + a19);
            System.out.println("AGENT_ADJUST_PAY_VENDOR           = " + a20);
            System.out.println("AGENT_ADJUST_LAST_AMOUNT          = " + a21);
            System.out.println("AGENT_ADJUST_NOTE                 = " + a22);
            System.out.println("AGENT_ADJUST_CREATED_BY           = " + a23);
            System.out.println("AGENT_ADJUST_CREATED_DATE         = " + a24);
            System.out.println("AGENT_ADJUST_UPDATED_BY           = " + a25);
            System.out.println("AGENT_ADJUST_LAST_UPDATED         = " + a26);
            System.out.println("AGENT_ADJUST_IS_DELETED           = " + a27);
            System.out.println("AGENT_ADJUST_PAY_VENDOR_PAY_CODE  = " + a28);
            System.out.println("AGENT_ADJUST_APPLY_TO_AGENT       = " + a29);
            System.out.println("AGENT_ADJUST_ORDER_NO             = " + a30);
            System.out.println("AGENT_ADJUST_COMP_CODE            = " + a31);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }

    @Then("Query Data in Tractor_Adjustments Table for Split Amount, There should be one record in this table for this transaction {string} {string} {string} {string} {string} {string} {string}")
    public void query_data_in_tractor_adjustments_table_for_split_amount_there_should_be_one_record_in_this_table_for_this_transaction(String environment, String tableName2, String tableName3, String createdDate, String orderNum, String tractorNo, String createdBy) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select  tv.TRAC_VEND_COMP_CODE, " +
                "       tv.TRAC_VEND_UNITNO, " +
                "       tv.TRAC_VEND_VENDOR_CODE, '   ***   Tractor Adjustment Record   ***',\n" +
                "       ta.TRACTOR_ADJUST_COMP_CODE, " +
                "       ta.TRACTOR_ADJUST_LOC_OR_TRAC,  " +
                "       ta.TRACTOR_ADJUST_VENDOR_CODE,  " +
                "       ta.TRACTOR_ADJUST_START_DATE, ta.*\n" +
                "       From " + tableName2 + " TA With(Nolock)\n" +
                "       Left Join " + tableName3 + "  tv With(Nolock) on tv.TRAC_VEND_UNITNO = ta.TRACTOR_ADJUST_LOC_OR_TRAC \n" +
                "       Where ta.TRACTOR_ADJUST_PAY_CODE in ('CH', 'CI', 'DM', 'DN')\n" +
                "       and ta.TRACTOR_ADJUST_CREATED_DATE >= '" + createdDate + "' " +
                "       and tv.TRAC_VEND_VENDOR_CODE = ta.TRACTOR_ADJUST_VENDOR_CODE\n" +
                "       and ta.TRACTOR_ADJUST_ORDER_NO = '" + orderNum + "'\n" +
                "       and ta.TRACTOR_ADJUST_LOC_OR_TRAC = '" + tractorNo + "' " +
                "       and ta.tractor_Adjust_Created_By = '" + createdBy + "' ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> dbAATable = new ArrayList<>();

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
                    "\t" + res.getString(25) +
                    "\t" + res.getString(26) +
                    "\t" + res.getString(27) +
                    "\t" + res.getString(28) +
                    "\t" + res.getString(29) +
                    "\t" + res.getString(30) +
                    "\t" + res.getString(31) +
                    "\t" + res.getString(32) +
                    "\t" + res.getString(33) +
                    "\t" + res.getString(34));

            String a1 = res.getString(1);
            String a2 = res.getString(2);
            String a3 = res.getString(3);
            String a4 = res.getString(4);
            String a5 = res.getString(5);
            String a6 = res.getString(6);
            String a7 = res.getString(7);
            String a8 = res.getString(8);
            String a9 = res.getString(9);
            String a10 = res.getString(10);
            String a11 = res.getString(11);
            String a12 = res.getString(12);
            String a13 = res.getString(13);
            String a14 = res.getString(14);
            String a15 = res.getString(15);
            String a16 = res.getString(16);
            String a17 = res.getString(17);
            String a18 = res.getString(18);
            String a19 = res.getString(19);
            String a20 = res.getString(20);
            String a21 = res.getString(21);
            String a22 = res.getString(22);
            String a23 = res.getString(23);
            String a24 = res.getString(24);
            String a25 = res.getString(25);
            String a26 = res.getString(26);
            String a27 = res.getString(27);
            String a28 = res.getString(28);
            String a29 = res.getString(29);
            String a30 = res.getString(30);
            String a31 = res.getString(31);
            dbAATable.add(a31);
            String a32 = res.getString(32);
            String a33 = res.getString(33);
            String a34 = res.getString(34);


            boolean booleanValue = false;
            if (Objects.equals(orderNum, a31)) {
                System.out.println("Order Num: " + orderNum);
                for (String dbAAT : dbAATable) {
                    if (dbAAT.contains(a31)) {
                        System.out.println("Order No: " + a31);
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

            System.out.println();
            System.out.println("TRAC_VEND_COMP_CODE                 = " + a1);
            System.out.println("TRAC_VEND_UNITNO                    = " + a2);
            System.out.println("TRAC_VEND_VENDOR_CODE               = " + a3);
            System.out.println("                                    = " + a4);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a5);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a6);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a7);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a8);
            System.out.println("TRACTOR_ADJUST_ID                   = " + a9);
            System.out.println("TRACTOR_ADJUST_PAY_CODE             = " + a10);
            System.out.println("TRACTOR_ADJUST_STATUS               = " + a11);
            System.out.println("TRACTOR_ADJUST_FREQ                 = " + a12);
            System.out.println("TRACTOR_ADJUST_AMOUNT_TYPE          = " + a13);
            System.out.println("TRACTOR_ADJUST_AMOUNT               = " + a14);
            System.out.println("TRACTOR_ADJUST_TOP_LIMIT            = " + a15);
            System.out.println("TRACTOR_ADJUST_TOTAL_TO_DATE        = " + a16);
            System.out.println("TRACTOR_ADJUST_LAST_DATE            = " + a17);
            System.out.println("TRACTOR_ADJUST_VENDOR_CODE          = " + a18);
            System.out.println("TRACTOR_ADJUST_LOC_OR_TRAC          = " + a19);
            System.out.println("TRACTOR_ADJUST_MAX_TRANS            = " + a20);
            System.out.println("TRACTOR_ADJUST_START_DATE           = " + a21);
            System.out.println("TRACTOR_ADJUST_END_DATE             = " + a22);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR           = " + a23);
            System.out.println("TRACTOR_ADJUST_LAST_AMOUNT          = " + a24);
            System.out.println("TRACTOR_ADJUST_NOTE                 = " + a25);
            System.out.println("TRACTOR_ADJUST_FC_TRANS_ID          = " + a26);
            System.out.println("TRACTOR_ADJUST_CREATED_BY           = " + a27);
            System.out.println("TRACTOR_ADJUST_CREATED_DATE         = " + a28);
            System.out.println("TRACTOR_ADJUST_UPDATED_BY           = " + a29);
            System.out.println("TRACTOR_ADJUST_LAST_UPDATED         = " + a30);
            System.out.println("TRACTOR_ADJUST_ORDER_NO             = " + a31);
            System.out.println("TRACTOR_ADJUST_COMP_CODE            = " + a32);
            System.out.println("TRACTOR_ADJUST_PAY_VENDOR_PAY_CODE  = " + a33);
            System.out.println("TRACTOR_ADJUST_DISCOUNT             = " + a34);
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #96 @IdentifyInvoiceForSplitAmountTestForAgentWithoutRecordOnAgent_Settlement_InfoTable /................................................//

    @Given("Locate a Record from Database for Valid Invoice Number for Split Amount, Test for Agent without a record on the Agent_Settlement_Info table {string} {string} {string} {string} {string}")
    public void locate_a_record_from_database_for_valid_invoice_number_for_split_amount_test_for_agent_without_a_record_on_the_agent_settlement_info_table(String environment, String tableName1, String tableName2, String tableName3, String tableName4) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        System.out.println("=========================================");
        System.out.println("Connecting to Database ..............................");
        Connection connectionToDatabase = browserDriverInitialization.getConnectionToDatabase(environment);
        stmt = connectionToDatabase.createStatement();

        String query = "Select top 50 ir.InvoiceNumber, " +
                "       ir.InvoiceDate, " +
                "       irr.Agent, " +
                "       irr.ProNumber, " +
                "       ir.TotalDue,  " +
                "       l.LOCATION_CODE, " +
                "       l.LOCATION_COMP_CODE,  " +
                "       si.AGENT_STL_AGENT_CODE, " +
                "       si.AGENT_STL_COMPANY_CODE, *\n" +
                "       FROM " + tableName1 + " irr With(nolock)\n" +
                "       Inner Join " + tableName2 + " ir With(nolock) on ir.InvoiceRegisterId = irr.InvoiceRegisterId \n" +
                "       Inner join " + tableName3 + " l With(nolock) on l.LOCATION_CODE = irr.agent \n" +
                "       Left Join " + tableName4 + " si With(nolock) on l.LOCATION_CODE = si.Agent_Stl_Agent_Code " +
                "       and si.Agent_Stl_Company_Code = l.LOCATION_COMP_CODE \n" +
                "       Where irr.CorpStatus in ('AgentReview', 'CorpReview') " +
                "       and InvoiceDate >= '01/01/2022' and si.AGENT_STL_AGENT_CODE is Null ";

        ResultSet res = stmt.executeQuery(query);
        ResultSetMetaData rsmd = res.getMetaData();
        int count = rsmd.getColumnCount();
        List<String> columnList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            columnList.add(rsmd.getColumnLabel(i));
        }
        System.out.println(columnList);

        List<String> stringBuilder2 = new ArrayList<>();

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
                    "\t" + res.getString(9));

            String a = res.getString(1);
            System.out.println("Invoice Number = " + a);
            String a3 = res.getString(3);
            System.out.println("Agent = " + a3);
            String a4 = res.getString(4);
            System.out.println("Pro Number = " + a4);

            StringBuilder sb2 = new StringBuilder();
            sb2.append(res.getString(3)).append("").append(res.getString(4));
            stringBuilder2.add(sb2.toString());
            System.out.println("Agent and Pro Number:  " + sb2);
            System.out.println();
        }
        System.out.println("Database Closed ......");
        System.out.println("=========================================");
    }


    //............................................/ #97  @SplitAmount/TestForAgentWithoutRecordOnAgentSettlementInfoTable /................................................//

    @Given("Enter a message into the Notes Column and make sure to enter a comma, Select Ok {string} for Split Amount")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_for_split_amount(String notes) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
    }

    @Given("Verify previously entered data remained same for Split Amount, Select Go")
    public void verify_previously_entered_data_remained_same_for_split_amount_select_go() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("ENTERED DATAS:");
        System.out.println("Agent Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentAmountSA).getAttribute("value"));
        System.out.println("Driver Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverAmountSA).getAttribute("value"));
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).getAttribute("value"));
        System.out.println("No of Splits: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateSA).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysSA).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();
        Thread.sleep(4000);
     //   driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]/div[2]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.crossCloseDR).click();
    }

    //............................................/ #98 @SplitAmount/TractorWithoutRecordOnTractorVendorTable /................................................//

    @Then("Select Corp Status = CorpReview for the same record that has Agent Status = SplitAmount and Corp Status = CorpReview for Split Amount {string} {string} {string}")
    public void select_corp_status_corp_review_for_the_same_record_that_has_agent_status_split_amount_and_corp_status_corp_review_for_split_amount(String agentStatus1, String agent, String proNum) throws InterruptedException {
        Thread.sleep(4000);
        WebElement agentStatus = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentStatus);
        JavascriptExecutor executorAS = (JavascriptExecutor) driver;
        executorAS.executeScript("arguments[0].click();", agentStatus);

        List<WebElement> list = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list) {
            if (webElement.getText().contains(agentStatus1)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement aGENT = driver.findElement(equipmentConsoleTractorAdjustmentsPage.agent);
        JavascriptExecutor executorA = (JavascriptExecutor) driver;
        executorA.executeScript("arguments[0].click();", aGENT);

        List<WebElement> list2 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list2) {
            if (webElement.getText().contains(agent)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);

        WebElement proNo = driver.findElement(equipmentConsoleTractorAdjustmentsPage.proNo);
        JavascriptExecutor executorP = (JavascriptExecutor) driver;
        executorP.executeScript("arguments[0].click();", proNo);

        List<WebElement> list3 = driver.findElements(By.xpath("//*[@id='optionlist']/li"));
        for (WebElement webElement : list3) {
            if (webElement.getText().contains(proNum)) {
                webElement.click();
                break;
            }
        }
        Thread.sleep(3000);
    }


    @Then("The Days, Amount, Tractor {string} and Notes Columns are filled in with the same information that was previously entered. Enter a different Amount, Days or Effective Date or No of Splits {string} for Split Amount")
    public void the_days_amount_tractor_and_notes_columns_are_filled_in_with_the_same_information_that_was_previously_entered_enter_a_different_amount_days_or_effective_date_or_no_of_splits_for_split_amount(String tractor, String effDate) {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).clear();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).sendKeys(tractor);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).click();


        String effMonths = effDate.split("-")[0].substring(0, 2);

        String effMonth = null;
        switch (effMonths) {
            case "01":
                effMonth = "January";
                break;
            case "02":
                effMonth = "February";
                break;
            case "03":
                effMonth = "March";
                break;
            case "04":
                effMonth = "April";
                break;
            case "05":
                effMonth = "May";
                break;
            case "06":
                effMonth = "June";
                break;
            case "07":
                effMonth = "July";
                break;
            case "08":
                effMonth = "August";
                break;
            case "09":
                effMonth = "September";
                break;
            case "10":
                effMonth = "October";
                break;
            case "11":
                effMonth = "November";
                break;
            case "12":
                effMonth = "December";
                break;
        }

        String effDay = effDate.split("-")[1].substring(0, 2);
        String effYear = effDate.split("-")[2].substring(0, 4);

        WebElement selectDate = driver.findElement(By.xpath("//*[@id=\"Text11\"]")); //*[@id="DriverDeductEffectiveDate"]
        selectDate.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ui-datepicker-calendar")));

        assert effMonth != null;
        if (effMonth.equals("February") && Integer.parseInt(effDay) > 29) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }
        if (Integer.parseInt(effDay) > 31) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
            return;
        }

        String monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();

        String month = monthYear.split(" ")[0].trim();
        String year = monthYear.split(" ")[1].trim();

        while (!(month.equals(effMonth) && year.equals(effYear))) {
            driver.findElement(By.xpath("//a[@title='Next']")).click();
            monthYear = driver.findElement(By.className("ui-datepicker-title")).getText();
            System.out.println(monthYear);
            month = monthYear.split(" ")[0].trim();
            year = monthYear.split(" ")[1].trim();
        }
        try {
            driver.findElement(By.xpath("//a[text()='" + effDay + "']")).click();
        } catch (Exception e) {
            System.out.println("Wrong date: " + effMonth + " : " + effDay);
        }
    }

    @Then("Enter a Message into the Notes Column and make sure to enter a Comma, Select Ok {string} for Split Amount CorpReview")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_for_split_amount_corpReview(String notes1) throws InterruptedException {
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes1);
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
    }

    @Then("Verify previously entered data remained same for Split Amount Corp Review, Select Go, Select Yes, Select Ok")
    public void verify_previously_entered_data_remained_same_for_split_amount_corp_review_select_go_select_yes_select_ok() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println("PREVIOUSLY ENTERED DATAS:");
        System.out.println("Agent Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.agentAmountSA).getAttribute("value"));
        System.out.println("Driver Amount: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.driverAmountSA).getAttribute("value"));
        System.out.println("Tractor: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.tractorSA).getAttribute("value"));
        System.out.println("No of Splits: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfSplitsSA).getAttribute("value"));
        System.out.println("Effective date: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.effectivedateSA).getAttribute("value"));
        System.out.println("No of Days: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.noOfDaysSA).getAttribute("value"));
        System.out.println("Notes: " + driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).getAttribute("value"));
        System.out.println("=========================================");
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();
        Thread.sleep(4000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        Thread.sleep(4000);
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]/div[2]")).getText());
        Thread.sleep(2000);
        System.out.println("=========================================");
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click();
        Thread.sleep(2000);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.crossCloseDR).click();
    }

    @Then("Verify Agent Status = SplitAmount and Corp Status = CorpReview in Main Form")
    public void verify_agent_status_split_amount_and_corp_status_corp_review_in_main_form() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("=========================================");
        System.out.println(driver.findElement(equipmentConsoleTractorAdjustmentsPage.dataTable).getText());
        System.out.println("");
        String agentStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[1]/a")).getText();
        String corpStatus = driver.findElement(By.xpath("//*[@id=\"griddata\"]/tbody/tr[1]/td[2]/a")).getText();
        System.out.println("Agent Status: " + agentStatus);
        System.out.println("Corp Status: " + corpStatus);
        System.out.println("=========================================");
    }


    //............................................/ #99 @SplitAmount/TestForTractorNotCurrentlyAssignedToAgentOnSplitAmountForm /................................................//


    @Given("Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go {string} for Split Amount")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_select_go_for_split_amount(String notes) throws InterruptedException {

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();

        System.out.println("=========================================");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[14]/div[3]/div/button[1]/span")).click(); //yes  /html/body/div[14]/div[3]/div/button[1]/span
      //  driver.findElement(By.xpath("/html/body/div[14]/div[3]/div/button[1]/span")).click(); //yes
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[13]/div[11]/div/button/span")).click(); //ok  /html/body/div[13]/div[11]/div/button
        System.out.println("=========================================");
    }


    @Given("Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go {string} for SPLIT AMOUNT on CorpReview Split Amount Form")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_select_go_for_split_amount_on_corp_review_split_amount_form(String notes) throws InterruptedException {

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();

        System.out.println("=========================================");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[13]/div[3]/div/button[1]/span")).click(); //yes  /html/body/div[13]/div[3]/div/button[1]/span
        driver.findElement(By.xpath("/html/body/div[14]/div[3]/div/button[1]/span")).click(); //yes  /html/body/div[14]/div[3]/div/button[1]/span
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[12]/div[11]/div/button/span")).click(); //ok
        System.out.println("=========================================");
    }


    //............................................/ #100 @SplitAmount/TractorThatDoesNotHaveTractorRecordWithStatusOfCurrent /................................................//

    @Given("Enter a message into the Notes Column and make sure to enter a comma, Select Ok, Select Go {string} for Split Amount Tractor that does NOT have a Tractor record with a Status of Current")
    public void enter_a_message_into_the_notes_column_and_make_sure_to_enter_a_comma_select_ok_select_go_for_split_amount_Tractor_that_does_NOT_have_a_Tractor_record_with_a_Status_of_Current(String notes) throws InterruptedException {

        driver.findElement(equipmentConsoleTractorAdjustmentsPage.notesSA).sendKeys(notes);
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.alertNotesSA).isDisplayed();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.okSA).click();
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.goSA).click();

        System.out.println("=========================================");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.splitAmountAlert).isDisplayed();
        System.out.println("ALERT MESSAGE: ");
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-confirmation-form\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[13]/div[3]/div/button[1]/span")).click(); //yes
        driver.findElement(By.xpath("/html/body/div[14]/div[3]/div/button[1]/span")).click(); //yes
        System.out.println("=========================================");
        System.out.println("ERROR MESSAGE: ");
        Thread.sleep(2000);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"dialog-message\"]")).getText());
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[12]/div[11]/div/button/span")).click(); //ok
        System.out.println("=========================================");
        driver.findElement(equipmentConsoleTractorAdjustmentsPage.crossCloseSA).isDisplayed();
    }

}




















