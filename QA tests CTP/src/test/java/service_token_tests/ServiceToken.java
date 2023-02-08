package service_token_tests;

import config.ODPParams;
import db.EmoployeeDB.EmployeeDBWork;
import db.EmployerDB.EmployerDBService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;


public class ServiceToken extends ODPParams {
    @Test
    public void serviceAuthTest() {
        RestAssured.given().accept(ContentType.JSON)
                .header("Authorization", "Basic c2VydmljZTp0ZXN0")
                .header("Accept-Encoding", "gzip, deflate, br")
                .when().post("/auth")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().statusCode(200)
                .assertThat().body(containsString("serviceAccessToken"));

    }

    //region employee-info

    //Проверка метода Employees для обновлнеия пользователя с Account_number = 11112222333344448888
    // (ID пользователя = 192ef830-8ef1-4155-a332-ef7c9f5b9916)
    @Test
    public void postEmployeesFroUpdate() {
        JSONObject requestBody = new JSONObject()
                .put("bankAccountNumber", "11112222333344448888")
                .put("employerInn", "0000000000")
                .put("firstName", "Vladimir")
                .put("lastName", "Komarov")
                .put("passportIssued", "ОВД г. МоскваTestApi")
                .put("passportIssuedDate", "2022-11-22")
                .put("passportIssuerCode", "106-209")
                .put("passportNumber", "100100")
                .put("passportSeries", "10 10")
                .put("patronymicName", "Valerevich")
                .put("phone", "9991112233")
                .put("registrationAddress", "Москва ул Федора Полетаева 56 кв10");

        Response response = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .header("Authorization", "Basic c2VydmljZTp0ZXN0")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("accesstoken", ODPParams.serviceToken)
                .body(requestBody.toString())
                .when().post("/employees")
                .then().contentType(ContentType.JSON)
                //Проверяем наличие значений в Body
                .body("firstName", containsString("Vladimir"))
                .assertThat().statusCode(200)
                .extract().response();
        System.out.println("Полученный ответ на запрос EMPLOYEES");
        response.prettyPrint();

        //Получаем объект работодателя по INN
        Map<String, Object> employer = EmployerDBService.getEmployerByINN(requestBody.get("employerInn").toString());

        //Получаем объект работника по accountNumber
        Map<String, Object> employee = EmployeeDBWork.getEmployeeByAccountNumber(requestBody.get("bankAccountNumber").toString());

        //Сравнение значений
        //Проверка блока Employee в ответе employees
        Assert.assertEquals(response.path("employeeId"), employee.get("employee.id").toString());
        Assert.assertEquals(response.path("patronymicName"), employee.get("requisites.patronymic_name"));
        Assert.assertEquals(response.path("firstName"), employee.get("requisites.first_name"));
        Assert.assertEquals(response.path("lastName"), employee.get("requisites.last_name"));
        // TODO: Разобраться с таймзоной
        // Assert.assertEquals(response.path("updatedAt"), employee.get("employee.updated_at"));
        Assert.assertEquals(response.path("status"), employee.get("employee.status"));

        //Проверка блока Employer в ответе employees
        Assert.assertEquals(response.path("employer.employerInn"), employer.get("requisites.inn"));
        Assert.assertEquals(response.path("employer.employerName"), employer.get("employer.name"));
        Assert.assertEquals(response.path("employer.employerId"), employer.get("employer.id").toString());

    }


    //Проверка метода /employees/{employeeId}. Получение информации по работнику
    @ParameterizedTest
    @ValueSource(strings = {"192ef830-8ef1-4155-a332-ef7c9f5b9916", "76ed518b-37d3-4bb6-8941-dde1fca78923"})
    public void getEmployeesEmployeeID(String currEmp) {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Authorization", "Basic c2VydmljZTp0ZXN0")
                .header("accesstoken", ODPParams.serviceToken)
                .when().get("/employees/" + currEmp)
                .then().contentType(ContentType.JSON)
                .assertThat().statusCode(200)
                .extract().response();

        // Получение объекта Работника по ID
        Map<String, Object> employee = EmployeeDBWork.getEmployeeByID(currEmp);

        //Получаем объект работодателя по ID работодателя из карточки работника
        Map<String, Object> employer = EmployerDBService.getEmployerByID(employee.get("employee.employer_id").toString());

        //Проверяем заполенение полей в ответе employees
        Assert.assertEquals(response.path("employeeId"), employee.get("employee.id").toString());
        Assert.assertEquals(response.path("patronymicName"), employee.get("requisites.patronymic_name"));
        Assert.assertEquals(response.path("firstName"), employee.get("requisites.first_name"));
        Assert.assertEquals(response.path("lastName"), employee.get("requisites.last_name"));
        Assert.assertEquals(response.path("status"), employee.get("employee.status"));

        //Проверка блока Employer в ответе employees
        Assert.assertEquals(response.path("employer.employerInn"), employer.get("requisites.inn"));
        Assert.assertEquals(response.path("employer.employerName"), employer.get("employer.name"));
        Assert.assertEquals(response.path("employer.employerId"), employer.get("employer.id").toString());
        response.prettyPrint();
    }

    //endregion

    //region employer-info

    @ParameterizedTest
    @ValueSource(strings = {"0000000000", "5032046575"})
    public void getEmployerInfo(String currEmployerINN) {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Authorization", "Basic c2VydmljZTp0ZXN0")
                .header("accesstoken", ODPParams.serviceToken)
                .when().get("/employer/info?employerInn=" + currEmployerINN)
                .then().contentType(ContentType.JSON)
                .assertThat().statusCode(200)
                .extract().response();

        response.prettyPrint();

        //Получаем объект работодателя по INN
        Map<String, Object> employer = EmployerDBService.getEmployerByINN(currEmployerINN);

        //Получаем объект Тариф по Активному PaymentProvide и ID работодателя
        Map<String, Object> tariffInfo = EmployerDBService
                .getTariffByPaymenProviderAndEmployerID(employer.get("employer.payment_provider").toString(), employer.get("employer.id").toString());

        //Проверка блока Employer в ответе employees
        Assert.assertEquals(Float.parseFloat(response.path("payoutPercentageAvailable").toString())
                , Float.parseFloat(tariffInfo.get("tariff.withdrawal_percentage").toString()));
        Assert.assertEquals((response.path("commission").hashCode())
                , tariffInfo.get("tariff.commission_amount").hashCode());
        Assert.assertEquals(response.path("specTariffCommissionAmount").hashCode()
                , tariffInfo.get("tariff.spec_tariff_commission_amount").hashCode());
        Assert.assertEquals(Float.parseFloat(response.path("commissionPercentage").toString())
                , Float.parseFloat(tariffInfo.get("tariff.commission_percentage").toString()));
        Assert.assertEquals(Float.parseFloat(response.path("specTariffCommissionPercentage").toString())
                , Float.parseFloat(tariffInfo.get("tariff.spec_tariff_commission_percentage").toString()));
        Assert.assertEquals(response.path("preferentialPaymentCount").hashCode(), tariffInfo.get("tariff.preferential_payment_count").hashCode());
        Assert.assertEquals(response.path("preferentialPaymentDays").hashCode()
                , tariffInfo.get("tariff.preferential_payment_days").hashCode());
        Assert.assertEquals(response.path("specTariffCondition"), tariffInfo.get("tariff.spec_tariff_condition"));
        //Почему в БД EMPLOYEE_PAYER, а в ответе API EMPLOYEE??
        //Assert.assertEquals(response.path("commissionPayer"), tariffInfo.get("tariff.commission_payer"));
        Assert.assertFalse(response.path("commissionPayer").toString().isEmpty());

        response.prettyPrint();
    }

    //endregion

    //region salary-info

    //Проверка метода /employees/{employeeId}/salary. Баланс по работнику
    @ParameterizedTest
    @ValueSource(strings = {"192ef830-8ef1-4155-a332-ef7c9f5b9916", "76ed518b-37d3-4bb6-8941-dde1fca78923"})
    public void getSalaryInfo(String currEmp) {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("accesstoken", serviceToken)
                .when().get("/employees/" + currEmp + "/salary")
                .then().contentType(ContentType.JSON)
                .extract().response();

        //Получаем объект Работника
        Map<String, Object> employee = EmployeeDBWork.getEmployeeByID(currEmp);
        //Получаем объект Работодателя
        Map<String, Object> employer = EmployerDBService.getEmployerByID(employee.get("employee.employer_id").toString());
        //Получаем объект Тариф по Активному PaymentProvide и ID работодателя
        Map<String, Object> tariffInfo = EmployerDBService
                .getTariffByPaymenProviderAndEmployerID(employer.get("employer.payment_provider").toString(), employer.get("employer.id").toString());

        response.prettyPrint();

        Assert.assertEquals(response.statusCode(), 200);

        //Проверяем заполнение полей
        //Проверка бизнес логики пока не требуется. Проверяем только что поля заполнены
        Assert.assertFalse(response.path("updatedAt").toString().isEmpty());
        Assert.assertFalse(response.path("availableCash").toString().isEmpty());
        Assert.assertFalse(response.path("cashOnAccount").toString().isEmpty());
        Assert.assertFalse(response.path("earnedForMonth").toString().isEmpty());
        Assert.assertFalse(response.path("serviceAvailable").toString().isEmpty());
        Assert.assertFalse(response.path("payoutPercentageAvailable").toString().isEmpty());
        Assert.assertEquals(Float.parseFloat(response.path("payoutPercentageAvailable").toString())
                , Float.parseFloat(tariffInfo.get("tariff.withdrawal_percentage").toString()));
        Assert.assertFalse(response.path("salaryDetailization.otherDeduction").toString().isEmpty());
        Assert.assertFalse(response.path("salaryDetailization.employeesDebt").toString().isEmpty());
        Assert.assertFalse(response.path("salaryDetailization.vacationDeduction").toString().isEmpty());
    }

    //endregion


}
