package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
        // инициируем начальыне элементы и сохраняем в драйвер
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void AdminAttributePageAdd(WebDriver driver) throws InterruptedException {
        //Текущая дата, необходимая для заполнения полей
        // и проверке уникальности некоторых полей (таких как: Description / Summary)
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMYYHHmm00"));

        //Переходим на вкладку с Атрибутами
        adminHeaderButtons.findElement(By.xpath("div/div/div/button[2]")).click();

        //Проверяем присутствие необходимых элементов
        WebElement attributesTable = driver.findElement(By.className("css-5qiaer"));
        Assert.assertNotNull(attributesTable);
        Assert.assertEquals(10, attributesTable.findElements(By.xpath("div")).size());

        //Нажмиаем кнопку "Добавить аттрибут"
        adminHeaderButtons.findElement(By.xpath("button[1]")).click();

        //Заполняем поле "Название атрибута (например: Attribute_1.2)", заодно проверяем его пристутвие
        WebElement attributeName = driver.findElement(By.xpath("//div[contains (@class,'css-5icxko')]/input"));
        Assert.assertNotNull(attributeName);
        attributeName.sendKeys("Autotest_name_" + currentDate);

        //Заполняем поле "Описание", заодно проверяем его пристутвие
        WebElement attributeDescription = driver.findElement(By.className("css-10oer18"));
        Assert.assertNotNull(attributeDescription);
        attributeDescription.sendKeys("Autotests_description_" + currentDate);

        //Ищем элемент "Тип атрибута"
        WebElement attributeType = driver.findElement(By.className("css-1kphvoe"));
        Assert.assertNotNull(attributeType);
        attributeType.click();

        //Смотрим выпадающий список и сравниваем, что всего 5 элементов присутствует
        List<WebElement> optionsDDList = driver.findElements(By.xpath("//div[contains(@class,'css-1ka94wp')]/ul/li"));
        Assert.assertEquals(5, optionsDDList.size());
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Выбираем 3й (в массивах счёт начинается с 0) элемент "Дата"
        optionsDDList.get(2).click();

        //Проверяем, что элемент "Тип атрибута" заполнен
        Assert.assertEquals("Дата", attributeType.getText());

        WebElement saveButton = driver.findElement(By.xpath("//*[@type=\"submit\"]"));
        saveButton.click();

        //Даём время, чтобы форма прогрузилась
        Thread.sleep(4000);
        Selenium.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //Получаем текст "Атрибут создан". Проверяем, что при создании никаких ошибок не произошло
        WebElement confirmLabel = driver.findElement(By.className("css-jo6shj"));
        Assert.assertEquals("Атрибут создан", confirmLabel.getText());

        //Нажимаем кнопку "Продолжить"
        WebElement confirmButton = driver.findElement(By.className("css-6ilz3t"));
        confirmButton.click();

        //Проверяем, что аттрибут создался и все поля заполнены корректно
        Assert.assertEquals("Autotest_name_" + currentDate, driver.findElement(By.className("css-mnn31")).getAttribute("Value"));
        Assert.assertEquals("Autotests_description_" + currentDate, driver.findElement(By.className("css-10oer18")).getText());
        Assert.assertEquals("Дата", driver.findElement(By.className("css-1kphvoe")).getText());

        System.out.println("1234");
    }
}
