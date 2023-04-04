package pages;

import junit.framework.Assert;
import ui.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class AdminTemplatePage {

    private WebDriver driver;

    @FindBy(className = "css-117purm")
    private WebElement Table;
    @FindBy(xpath = "//*[@class=\"css-117purm\"]/div")
    private WebElement templateTable;

    @FindBy(className = "css-gg4vpm")
    private WebElement templateHeaderButtons;


    public AdminTemplatePage(WebDriver driver) {
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void checkTable(){
        //Assert.assertEquals(10,Table.findElements(By.xpath("div")).size());
    }

    public void AdminTemplateAdd(WebDriver driver){
        //Текущая дата, необходимая для заполнения полей
        // и проверке уникальности некоторых полей (таких как: Description / Summary)
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMYYHHmm00"));
        //Нажмиаем кнопку "Добавить шаблон"
        templateHeaderButtons.findElement(By.xpath("button[1]")).click();


        //Проверяем, что все поля для вкладки "Настройки" присутствуют
        //Название анкеты, Описание анкеты, Код анкеты, Выбор подсистем
        WebElement templateMainSettingsPage = driver.findElement(By.className("css-ovnx7g"));

        //Все импуты: Назваение анкеты, Код анкеты, Диагностика
        List<WebElement> inputs = templateMainSettingsPage.findElements(By.xpath(".//input"));
        //Описание анкеты локатор
        WebElement textarea = templateMainSettingsPage.findElement(By.xpath(".//textarea"));


        //Заоплняем форму "Настройки"
        // Название анкеты
        inputs.get(0).sendKeys("Autotests_Name_" + currentDate);
        //Описание анкеты
        textarea.sendKeys("Autotests_Description_\" + currentDate");
        // Код анкеты
        inputs.get(1).sendKeys("Aututests_Code");
        //Диагностика
        inputs.get(2).click();
        WebElement option1 = driver.findElement(By.className("css-kh258y"));
        option1.findElement(By.xpath("li[1]")).click();
        System.out.println("1234");

    }
}
