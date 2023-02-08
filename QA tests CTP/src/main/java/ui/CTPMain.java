package ui;

import pages.MainLoginPage;

import java.util.concurrent.TimeUnit;

public class CTPMain {
    private static String urlMain = "https://devops.эффективность.рф/idm/Accounts/Login";

    public static void loginMain() {
        Selenium.driver.get(urlMain);

        MainLoginPage loginPage = new MainLoginPage(Selenium.driver);
        loginPage.Login_to_system();

        Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


}
