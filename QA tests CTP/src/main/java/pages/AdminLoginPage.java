package pages;

import ui.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminLoginPage {

    private WebDriver driver;

    @FindBy(name = "username")
    private WebElement userName;
    @FindBy(name = "password")
    private WebElement password;
    @FindBy(className = "MuiButton-root")
    private WebElement loginButton;

    @FindBy(className = "css-117purm")
    private WebElement table;

    public AdminLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void LoginToSystem() {
        userName.sendKeys("axis-superadmin");
        password.sendKeys("ax1s");
        loginButton.click();

        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List newTable = driver.findElements(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/div/div[3]/div[1]/div[2]/div"));
    }
}
