package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Selenium {
    public static WebDriver driver;
    public static void setup()  {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriverMac.exe");
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        //driver.get("https://habr.com/ru/post/502292/");

    }
}