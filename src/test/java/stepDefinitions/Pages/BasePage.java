package stepDefinitions.Pages;

import org.openqa.selenium.WebDriver;


public class BasePage {
    public Object driver;
  //  protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }


}
