import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBaseTest extends DataBaseHelper {

    @Test
    public void query01() {
    }

    @Test
    public void query02() {
    }

    @Test
    public void query03() {
    }


    @Test  //5. Calculate the average salary of all employees with gender "F"
    //-- "Kadın" cinsiyetindeki tüm çalışanların ortalama maaşını hesapla.
    public void query05() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select avg(salary) as kadınCalsOrtMas\n" +
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
        ResultSet rs = statement.executeQuery("select departments.dept_name, employees.emp_no, employees.first_name, employees.last_name, gender, salaries.salary\n" +
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
        ResultSet rs = statement.executeQuery("select *\n" +
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
        ResultSet rs = statement.executeQuery("select dept_name ,avg(salary) as ortMaas\n" +
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
        ResultSet rs = statement.executeQuery("SELECT employees.*, departments.dept_name\n" +
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
        ResultSet rs = statement.executeQuery("SELECT employees.emp_no,employees.first_name,employees.last_name,dept_emp.dept_no, departments.dept_name,\n" +
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
        ResultSet rs = statement.executeQuery("select employees.*,salaries.salary\n" +
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
        ResultSet rs = statement.executeQuery("select * from titles\n" +
                "where emp_no='10102'");

        DBConnectionClose();

    }

    @Test //17
    //Identify the employees in each department who have the highest average salary.
    //List the department name, employee's first name, last name, and the average salary.
    //Order the results by average salary in descending order, showing only those with the
    //highest average salary within their department.
    public void query17() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT employees.first_name,employees.last_name,avg(salary)" + " " +
                        "from employees" + " " +
                        "JOIN salaries on employees.emp_no = salaries.emp_no" + " " +
                        "JOIN dept_emp on employees.emp_no = dept_emp.emp_no" + " " +
                        "JOIN departments on dept_emp.dept_no = departments.dept_no" + " " +
                        "WHERE departments.dept_no='d008'" + " " +
                        "GROUP BY employees.first_name,employees.last_name" + " " +
                        "ORDER BY avg(salary) desc;");
        DBConnectionClose();
    }

    @Test // 18
    //List the names, last names, and hire dates in alphabetical order of all employees
    // hired before January 01, 1990
    public void query18() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT employees.first_name, employees.last_name, employees.hire_date " +
                        "FROM employees " +
                        "WHERE hire_date < '1990-01-01' " +
                        "ORDER BY last_name ASC, first_name ASC;");
        DBConnectionClose();
    }

    @Test //19
    //List the names, last names, hire dates of all employees hired between January 01, 1985
    //and December 31, 1989, sorted by hire date.
    public void query19() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT employees.first_name, employees.last_name, employees.hire_date " +
                        "FROM employees " +
                        "WHERE hire_date BETWEEN '1985-01-01' AND '1989-12-31' " +
                        "ORDER BY hire_date ASC;");
        DBConnectionClose();
    }

    @Test //20
    //List the names, last names, hire dates, and salaries of all employees in the Sales department
    //who were hired between January 01, 1985 and December 31, 1989, sorted by salary in descending order.
    public void query20() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT employees.first_name, employees.last_name, employees.hire_date, salaries.salary" + " " +
                "FROM employees" + " " +
                "JOIN salaries on employees.emp_no = salaries.emp_no" + " " +
                "JOIN dept_emp on employees.emp_no = dept_emp.emp_no" + " " +
                "JOIN departments on dept_emp.dept_no = departments.dept_no" + " " +
                "WHERE departments.dept_name = 'Sales'" + " " +
                "AND employees.hire_date BETWEEN '1985-01-01' AND '1989-12-31'" + " " +
                "ORDER BY salaries.salary DESC;");
        DBConnectionClose();
    }

    @Test //21
    //a: Find the count of male employees (179973)
    public void query21a() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) AS male_employee_count " +
                        "FROM employees " +
                        "WHERE gender = 'M';");
        DBConnectionClose();
    }

    @Test //21
    //b: Determine the count of female employees (120050)
    public void query21b() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) AS female_employee_count " +
                        "FROM employees " +
                        "WHERE gender = 'F';");
        DBConnectionClose();
    }

    @Test //21
    //c: Find the number of male and female employees by grouping
    public void query21c() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT gender, COUNT(*) AS employee_count " +
                        "FROM employees " +
                        "GROUP BY gender;");
        DBConnectionClose();
    }

    @Test //21
    //d: Calculate the total number of employees in the company (300023)
    public void query21d() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) " +
                "FROM employees;");
        DBConnectionClose();
    }

    @Test //22
    //a:Find out how many employees have unique first names (1275)
    public void query22a() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*)" +
                "FROM (" +
                "    SELECT first_name" +
                "    FROM employees" +
                "    GROUP BY first_name" +
                "    HAVING COUNT(*) = 1" +
                ") AS unique_names;");
        DBConnectionClose();
    }

    @Test //22
    //b: Identify the number of distinct department names (9)
    public void query22b() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT COUNT(DISTINCT departments.dept_name)" +
                "FROM departments;");
        DBConnectionClose();
    }

    @Test //23
    // List the number of employees in each department
    public void query23() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT  departments.dept_name, count(dept_emp.emp_no)" + " " +
                "FROM departments" + " " +
                "JOIN dept_emp on departments.dept_no=dept_emp.dept_no" + " " +
                "GROUP BY departments.dept_name;");
        DBConnectionClose();
    }

    @Test //24
    // List all employees hired within the last 5 years from February 20, 1990
    public void query24() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT employees.first_name, employees.last_name, employees.hire_date" + " " +
                "FROM employees" + " " +
                "WHERE employees.hire_date between '1990-02-20' and '1995-02-20';");
        DBConnectionClose();
    }
}