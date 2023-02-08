package db.EmployerDB;

import db.DbConnection;

import java.sql.SQLException;
import java.util.Map;

public class EmployerDBService {

    public static Map<String,Object> getTariffByPaymenProviderAndEmployerID(String tariffPaymentProvider, String employerID){
        try {
            return DbConnection.executeQuery(
                    "select * from tariff " +
                            "where payment_provider = '"+ tariffPaymentProvider +"' " +
                            "and  employer_id ='"+employerID+"'\n").get(0);
        }
        catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static Map<String, Object> getEmployerByID(String employerID) {
        try {
            return DbConnection.executeQuery(
                    "select * from employer er\n" +
                            "left join requisites req on req.id = er.requisites_id\n" +
                            "where er.id = '" + employerID + "'").get(0);

        } catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> getEmployerByINN(String employerINN) {
        try {
            return DbConnection.executeQuery(
                    "select * from employer er\n" +
                            "left join requisites req on req.id = er.requisites_id\n" +
                            "where req.inn = '" + employerINN + "'").get(0);

        } catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
