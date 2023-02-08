package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.Selenium;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminAttributePage {
    private WebDriver driver;

    @FindBy(className = "css-gg4vpm")
    private WebElement adminHeaderButtons;
    @FindBy(xpath = "//*[@class=\"css-117purm\"]/div")
    private WebElement attributeTable;

    public AdminAttributePage(WebDriver driver) {
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void AdminAttributePageAddAttribute(WebDriver driver) {
        //Текущая дата, необходимая для заполнения полей
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-YY HH:mm:00"));

        //Переходим на вкладку с Атрибутами
        adminHeaderButtons.findElement(By.xpath("div/div/div/button[2]")).click();

        //Проверяем присутствие необходимых элементов
        WebElement attributesTable = driver.findElement(By.className("css-117purm"));
        Assert.assertNotNull(attributesTable);
        Assert.assertEquals(10, attributesTable.findElements(By.xpath("div")).size());

        //Нажмиаем кнопку "Добавить аттрибут"
        adminHeaderButtons.findElement(By.xpath("button[1]")).click();

        //Заполняем поле "Название атрибута (например: Attribute_1.2)", заодно проверяем его пристутвие
        WebElement attributeName = driver.findElement(By.xpath("//input[@id=\":r15:\"]"));
        Assert.assertNotNull(attributeName);
        attributeName.sendKeys("Autotest_name_" + currentDate);

        //Заполняем поле "Описание", заодно проверяем его пристутвие
        WebElement attributeDescription = driver.findElement(By.xpath("//textarea[@id=\":r16:\"]"));
        Assert.assertNotNull(attributeDescription);
        attributeDescription.sendKeys("Autotests_description_" + currentDate);

        //Ищем элемент "Тип атрибута"
        WebElement attributeType = driver.findElement(By.xpath("//*[@id=\":r17:\"]"));
        Assert.assertNotNull(attributeType);
        attributeType.click();

        //Смотрим выпадающий список и сравниваем, что всего 5 элементов присутствует
        List optionsDDList = driver.findElements(By.xpath("//*[@id=\"menu-dataType\"]/div[3]/ul/li"));
        Assert.assertEquals(5, optionsDDList.size());
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Выбираем 3й элемент "Дата"
        WebElement opt3 = driver.findElement(By.xpath("//*[@id=\"menu-dataType\"]/div[3]/ul/li[3]"));
        opt3.click();

        //Проверяем, что элемент "Тип атрибута" заполнен
        Assert.assertEquals("Дата", attributeType.getText());

        WebElement saveButton = driver.findElement(By.xpath("//*[@type=\"submit\"]"));
        saveButton.click();

        //Даём время, чтобы форма прогрузилась
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Получаем текст "Атрибут создан". Проверяем, что при создании никаких ошибок не произошло
        WebElement confirmLabel = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/h2"));
        Assert.assertEquals("Атрибут создан", confirmLabel.getText());

        //Нажимаем кнопку "Продолжить"
        WebElement confirmButton = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/button"));
        confirmButton.click();

        //Проверяем, что аттрибут создался и все поля заполнены корректно
        Assert.assertEquals("Autotest_name_" + currentDate, driver.findElement(By.className("//*[@id=\":r2r:\"]")));
        Assert.assertEquals("Autotests_description_" + currentDate, driver.findElement(By.xpath("//*[@id=\":r2s:\"]")).getText());
        Assert.assertEquals("Дата", driver.findElement(By.xpath("//*[@id=\":r2t:\"]")).getText());

        System.out.println("1234");
    }

}
