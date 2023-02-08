package db.EmoployeeDB;
import db.DbConnection;
import java.sql.SQLException;
import java.util.Map;

public class EmployeeDBWork {

    public static Map<String, Object> getEmployeeByID(String ID) {
        try {
            return DbConnection.executeQuery(
                    "select *,employee.id from employee\n" +
                            "left join requisites req on req.id = employee.requisites_id\n" +
                            "where employee.id = '" + ID + "'").get(0);
        } catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> getEmployeeByAccountNumber(String accountNumber) {
        try {
            return DbConnection.executeQuery(
                    "select *,employee.id from employee\n" +
                            "left join requisites req on req.id = employee.requisites_id\n" +
                            "where req.account_number = '" + accountNumber + "'").get(0);
        } catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String,Object> getSalaryByEmployeeId(String employeeID){
        try {
            return DbConnection.executeQuery(
                    "select sum(available_Cash) available_Cash" +
                            ", max(sal.updated_at) updated_at" +
                            ", sum(earned_For_Month) earned_For_Month" +
                            ", sum (other_Deduction) other_Deduction" +
                            ", sum (employees_Debt) employees_Debt" +
                            ", sum (vacation_Deduction) vacation_Deduction \n" +
                            "from position pos\n" +
                            "left join salary sal on pos.id = sal.position_id\n" +
                            "where employee_id = '"+employeeID+"'\n" +
                            "and is_checked_by_list").get(0);
            /*if (queryResult.size()>1)
            {
                Map<String, Object> result = new HashMap<>();
                int availableCash = 0, earnedForMonth= 0, otherDeduction= 0, employeesDebt= 0, vacationDeduction= 0;
                for (Map<String, Object> row: queryResult) {
                    availableCash += Integer.parseInt(row.get("salary.available_cash").toString());
                    earnedForMonth += Integer.parseInt(row.get("salary.earned_for_month").toString());
                    otherDeduction += Integer.parseInt(row.get("salary.other_deduction").toString());
                    employeesDebt += Integer.parseInt(row.get("salary.employees_debt").toString());
                    vacationDeduction += Integer.parseInt(row.get("salary.vacation_deduction").toString());
                }
                for (int i = 1; i < queryResult.size(); i++){
                    queryResult.get(0).put("salary.available_cash",100);
                }
                return null;
            }
            else 
                return queryResult.get(0);*/
        }
        catch (SQLException | ClassNotFoundException | IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
