import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBaseTest extends DataBaseHelper {

    @Test
    public void query01(){}

    @Test
    public void query02(){}

    @Test
    public void query03(){}


    @Test  //5. Calculate the average salary of all employees with gender "F"
    //-- "Kadın" cinsiyetindeki tüm çalışanların ortalama maaşını hesapla.
    public void query05() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("select avg(salary) as kadınCalsOrtMas\n" +
                "from employees\n" +
                "left join salaries ON employees.emp_no = salaries.emp_no\n" +
                "where gender='F';");
         QueryResults(rs);

         DBConnectionClose();

    }

    @Test  //6. List all employees in the "Sales" department with a salary greater than 70,000.
    //-- Maaşı 70.000'den yüksek olan "Satış" departmanındaki tüm çalışanları listele.
    public void query06() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("select departments.dept_name, employees.emp_no, employees.first_name, employees.last_name, gender, salaries.salary\n" +
                "from employees\n" +
                "left join dept_emp ON employees.emp_no = dept_emp.emp_no\n" +
                "left join salaries ON employees.emp_no = salaries.emp_no\n" +
                "left join departments ON dept_emp.dept_no = departments.dept_no\n" +
                "where (departments.dept_name='Sales') and (salaries.salary > 70000)");

        DBConnectionClose();

    }

    @Test  //7. This query retrieves employees who have salaries between 50000 and 100000.
    // -- Bu sorgu, maaşı 50.000 ile 100.000 arasında olan çalışanları getirir.
    public void query07() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("select *\n" +
                " from employees\n" +
                " left join salaries ON employees.emp_no = salaries.emp_no\n" +
                " where salaries.salary between 50000 and 100000\n" +
                " order by salary");
        DBConnectionClose();

    }


    @Test  //8. Calculate the average salary for each department (by department number or department name)
    // -- Her departmanın ortalama maaşını hesapla (departman numarasına veya departman adına göre)
    public void query08() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("select dept_name ,avg(salary) as ortMaas\n" +
                "from departments\n" +
                "left join dept_emp ON departments.dept_no = dept_emp.dept_no\n" +
                "left join salaries ON dept_emp.emp_no = salaries.emp_no\n" +
                "group by dept_name");

        QueryResults(rs);

        DBConnectionClose();

    }

    @Test  // 29. List all employees working in the "Sales" department with the title "Manager"
    // -- "Satış" bölümünde "Yönetici" unvanıyla çalışan tüm çalışanları listele
    public void query29() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("SELECT employees.*, departments.dept_name\n" +
                "FROM employees\n" +
                "LEFT JOIN dept_manager ON dept_manager.emp_no = employees.emp_no\n" +
                "LEFT JOIN departments ON departments.dept_no = dept_manager.dept_no\n" +
                "WHERE departments.dept_no = 'd007'");

        DBConnectionClose();

    }


    @Test  //30. Find the department where employee with '10102' has worked the longest
    // -- '10102' numaralı çalışanın en uzun süre çalıştığı departmanı bul
    public void query30() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("SELECT employees.emp_no,employees.first_name,employees.last_name,dept_emp.dept_no, departments.dept_name,\n" +
                "       SUM(DATEDIFF(IFNULL(dept_emp.to_date, CURRENT_DATE), dept_emp.from_date)) AS total_duration\n" +
                "FROM employees\n" +
                "JOIN dept_emp ON dept_emp.emp_no=employees.emp_no\n" +
                "JOIN departments ON dept_emp.dept_no = departments.dept_no\n" +
                "WHERE dept_emp.emp_no = '10102'\n" +
                "GROUP BY dept_emp.dept_no, departments.dept_name\n" +
                "ORDER BY total_duration DESC\n" +
                "LIMIT 1");

        DBConnectionClose();

    }


    @Test  //31. Find the highest paid employee in department D004
    // -- D004 bölümünde en yüksek maaş alan çalışanı bulun
    public void query31() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("select employees.*,salaries.salary\n" +
                "from employees\n" +
                "LEFT JOIN salaries ON salaries.emp_no=employees.emp_no\n" +
                "order by salary desc\n" +
                "LIMIT 1");

        DBConnectionClose();

    }



    @Test  //32. Find the entire position history for employee with emp. no '10102'
    // -- '10102' numaralı çalışanın tüm pozisyon geçmişini bulun
    public void query32() throws SQLException {

        DBConnectionOpen();
        ResultSet rs=statement.executeQuery("select * from titles\n" +
                "where emp_no='10102'");

        DBConnectionClose();

    }




}
