package ui;

import pages.AdminAttributePage;
import pages.AdminLoginPage;
import pages.AdminTemplatePage;

import java.util.concurrent.TimeUnit;

public class CTPAdmin {
    private static String urlAdmin = "https://axis.inx.devops.эффективность.рф/admin/login";

    public static void loginAdmin() {
        Selenium.driver.get(urlAdmin);

        AdminLoginPage loginPage = new AdminLoginPage(Selenium.driver);
        loginPage.LoginToSystem();
        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static void templateChecks(){
        AdminTemplatePage templatePage = new AdminTemplatePage(Selenium.driver);
        templatePage.checkTable();
    }

    public static void attributeChecks(){
        AdminAttributePage attributePage = new AdminAttributePage(Selenium.driver);
        attributePage.AdminAttributePageAddAttribute(Selenium.driver);
    }

}
