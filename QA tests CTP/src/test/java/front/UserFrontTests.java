package front;

import ui.CTPMain;
import ui.Selenium;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserFrontTests {

    @BeforeAll
    public static void beforeTests() {
        //Открывем Chome браузер
        Selenium.setup();
    }

    @AfterAll
    public static void afterTests() {
        //Закрываем браузер, чтобы очистить память
        Selenium.driver.quit();
    }

    @Test
    public static void login(String[] args) {
        try {
            CTPMain.loginMain();
        } catch (Exception e) {
            System.out.println("______________________________________________________________");
            e.printStackTrace();
        } finally {
            //Selenium.driver.close();
        }
    }


}
