package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainLoginPage {
    private WebDriver driver;
    @FindBy(name = "Login")
    private WebElement login;
    @FindBy(name = "Password")
    private WebElement password;
    @FindBy(className = "new-reg-btn")
    private WebElement loginButton;

    public MainLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void Login_to_system() {
        login.sendKeys("KSkubnikov@inno.tech");
        password.sendKeys("anti7it");
        loginButton.click();
    }
}
