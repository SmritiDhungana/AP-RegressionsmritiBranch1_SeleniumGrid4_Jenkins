package stepDefinitions.CommonUtils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BrowserDriverInitialization {

    String URL = "";
    public WebDriver driver;
    public DesiredCapabilities cap = new DesiredCapabilities();


    //database connection details
    static Connection con = null;
    public static String DB_URL_STAGING = "jdbc:sqlserver://192.168.6.22";
    public static String DB_URL_LAUNCH = "jdbc:sqlserver://192.168.6.194\\launch";
    public static String DB_URL_TRANSFLO = "jdbc:sqlserver://192.168.6.166";
    public static String DB_URL_PROD = "jdbc:sqlserver://192.168.6.167";
    public static String DB_URL_DRIVER360 = "jdbc:sqlserver://192.168.16.51";
    public static String DB_USER = "svc_automation";
    public static String DB_PASSWORD = "svc_@u30m@tiOn";
    public static String DB_PASSWORD_PROD = "svc_@u30m@tiOnPr0d";


    public String getDataFromPropertiesFileForAgentPortal(String environment, String browser) throws MalformedURLException {
        if (environment.equals("launch")) {
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
            URL = "http://agentlaunch.evansdelivery.com/";
        } else if (environment.equals("staging")){
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
            URL = "https://staging.evansdelivery.com/login.aspx";
        }
        return URL;
    }


    public String getDataFromPropertiesFileForEBH(String environment, String browser) throws MalformedURLException {
        switch (environment) {
            case "ebhlaunch":
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
                URL = "http://ebhlaunch.evansdelivery.com:8089/";
                break;
            case "ebhprod":
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
                URL = "http://ebh.evansdelivery.com/";
                break;
            case "ebhstaging":
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
                URL = "http://ebhstaging.evansdelivery.com:83/";
                break;
        }
        return URL;
    }


    public Connection getConnectionToDatabase(String environment) throws
            ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(dbClass).newInstance();
        switch (environment) {
            case "ebhstaging":
                con = DriverManager.getConnection(DB_URL_STAGING, DB_USER, DB_PASSWORD);
                break;
            case "ebhlaunch":
                con = DriverManager.getConnection(DB_URL_LAUNCH, DB_USER, DB_PASSWORD);
                break;
            case "transflo":
                con = DriverManager.getConnection(DB_URL_TRANSFLO, DB_USER, DB_PASSWORD);
                break;
            case "ebhprod":
                con = DriverManager.getConnection(DB_URL_PROD, DB_USER, DB_PASSWORD_PROD);
                break;
            case "driver360staging":
                con = DriverManager.getConnection(DB_URL_DRIVER360, DB_USER, DB_PASSWORD);
                break;
        }
        return con;
    }


    public Connection getConnectionToDatabase1(String environment1) throws
            ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(dbClass).newInstance();
        if (environment1.equals("transflo")) {
            con = DriverManager.getConnection(DB_URL_TRANSFLO, DB_USER, DB_PASSWORD);
        } else if (environment1.equals("driver360staging")) {
            con = DriverManager.getConnection(DB_URL_DRIVER360, DB_USER, DB_PASSWORD);
        }
        return con;
    }


    public String getDataFromPropertiesFileForEquipmentConsole(String environment, String browser) throws MalformedURLException {
        if (environment.equals("eclaunch")) {
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
            URL = "https://chassislaunch.evansdelivery.com";
        } else if (environment.equals("ecstaging")) {
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
            URL = "https://chassisstaging.evansdelivery.com";
        }
        return URL;
    }


}
