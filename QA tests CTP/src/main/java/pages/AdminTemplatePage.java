package pages;

import ui.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class AdminTemplatePage {

    private WebDriver driver;

    @FindBy(className = "css-117purm")
    private WebElement Table;
    @FindBy(xpath = "//*[@class=\"css-117purm\"]/div")
    private WebElement templateTable;

    public AdminTemplatePage(WebDriver driver) {
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void checkTable(){
        Assert.assertEquals(10,Table.findElements(By.xpath("div")).size());
    }
}
