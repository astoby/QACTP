package front;

import io.qameta.allure.Step;
import ui.CTPAdmin;
import ui.Selenium;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AdminFrontTests {

    //region Before/After tests
    @BeforeAll
    public static void beforeTests() {
        //Открывем Chome браузер
        Selenium.setup();
        CTPAdmin.loginAdmin();

    }

    @AfterAll
    public static void afterTests() {
        //Закрываем браузер, чтобы очистить память
        //Selenium.driver.close();
        Selenium.driver.quit();
    }
    //endregion

    //@Test
    public void loginAdminTest() {
        try {
            CTPAdmin.loginAdmin();
        } catch (Exception e) {
            System.out.println("______________________________________________________________");
            e.printStackTrace();
        }
    }

    //@Test
    public void checkAdminTable(){
        try {
            CTPAdmin.templateChecks();
        }catch (Exception e){
            System.out.println("______________________________________________________________");
            e.printStackTrace();
        }
    }

    @Test
    public void checkAttributeTable(){
        try {
            CTPAdmin.attributeChecks();
        } catch (Exception e) {
            System.out.println("______________________________________________________________");
            e.printStackTrace();
        }
    }

    @Test
    @Step
    public void templateAdd(){
        try {
            CTPAdmin.templateAdd();
        } catch (Exception e) {
            System.out.println("______________________________________________________________");
            e.printStackTrace();
        }
    }
}
