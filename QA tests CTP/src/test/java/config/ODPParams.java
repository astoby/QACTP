package config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class ODPParams{
    public static String serviceToken, userAccessToken;

    private static String generateServiceToken() {
        return RestAssured.given().accept(ContentType.JSON)
                .header("Authorization", "Basic c2VydmljZTp0ZXN0")
                .header("Accept-Encoding", "gzip, deflate, br")
                .when().post("/auth")
                .then()
                .extract()
                .response().path("serviceAccessToken");
    }

    private static String generateUserAccessToken() {
        return RestAssured.given().accept(ContentType.JSON)
                .header("Authorization", "Basic c2VydmljZTp0ZXN0")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("accesstoken", serviceToken)
                .when().post("/employees/" + ODPConfig.EMPLOYEE_ID + "/token/webview")
                .then()
                .extract()
                .response().path("userAccessToken");
    }


    @BeforeAll
    public static void beforeTests() {
        //Подставляем значение базового пути
        RestAssured.baseURI = ODPConfig.BASE_URI;
        // Получение сервисного токена.
        // В методе generateServiceToken() выполняется запрос по апи в /auth
        // Полученный токен записывается в глобальную переменную serviceToken
        serviceToken = generateServiceToken();

        //Получение Токена для работника. Полученный токен записывается в глобальную переменную userAccessToken
        userAccessToken = generateUserAccessToken();
    }
}
