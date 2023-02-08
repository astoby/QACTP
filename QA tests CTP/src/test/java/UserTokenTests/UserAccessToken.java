package UserTokenTests;

import config.ODPConfig;
import config.ODPParams;
import com.auth0.jwt.JWT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;

import static org.hamcrest.Matchers.containsString;

public class UserAccessToken extends ODPParams {

    // Проверка получения токена Пользователя (метод Post /employees/{employeeId}/token/webview).
    // Происходит Assert значений. Проверяется правильной заполнения JSON ответа
    @Test
    public void getUserTokenTest() {
        RestAssured.given().accept(ContentType.JSON)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("accesstoken", serviceToken)
                .when().post("/employees/" + ODPConfig.EMPLOYEE_ID + "/token/webview")
                .then().contentType(ContentType.JSON)
                .assertThat().statusCode(200)
                .assertThat().body(containsString("userAccessToken"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"192ef830-8ef1-4155-a332-ef7c9f5b9916", "76ed518b-37d3-4bb6-8941-dde1fca78923"})
    public void getEmployee(String currEmp) {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("accesstoken", userAccessToken)
                .when().get("/employees/" + currEmp)
                .then().contentType(ContentType.JSON)
                .extract().response();

        String empId = JWT.decode(userAccessToken).getClaims().get("employeeId").asString();

        if (currEmp.equals(empId)) {
            Assert.assertEquals(response.statusCode(), 200);
        } else {
            Assert.assertEquals(response.statusCode(), 400);
            Assert.assertEquals(response.path("errorCode"), "EMPLOYEE_NOT_FOUND");
            Assert.assertEquals(response.path("errorMessage"), "Пользователь не найден в системе");

        }

        response.prettyPrint();
    }

}