import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseTest extends DataBaseHelper {


    @Test
    // 1. List all employees in department D001.
    public void query01() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select * from dept_emp\n" +
                "where dept_no='d001' \n" +
                "order by emp_no;\n");

        DBConnectionClose();

    }


    @Test
    // 2. List all employees in 'Human Resources' department.
    public void query02() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select * from dept_emp\n" +
                "where dept_no='d003' \n" +
                "order by emp_no;");

        DBConnectionClose();
    }


    @Test
    // 3. Calculate the average salary of all employees
    public void query03() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select avg(salary) as ortalamaMaas from salaries;");

        DBConnectionClose();
    }


    @Test
    //  4. Calculate the average salary of all employees with gender "M"
    public void query04() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select employees.employees.gender, avg(salaries.salary) as ortalamaMaas\n" +
                "from salaries\n" +
                "join employees.employees ON salaries.emp_no = employees.employees.emp_no\n" +
                "where employees.employees.gender = 'M' ;");

        DBConnectionClose();
    }


    @Test
    //5. Calculate the average salary of all employees with gender "F"
    public void query05() throws SQLException {

        DBConnectionOpen();

        ResultSet rs = statement.executeQuery("select avg(salary) as kadınCalsOrtMas\n" +
                "from employees\n" +
                "left join salaries ON employees.emp_no = salaries.emp_no\n" +
                "where gender='F';");

        QueryResults(rs);
        DBConnectionClose();

    }


    @Test
    //6. List all employees in the "Sales" department with a salary greater than 70,000.
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


    @Test
    //7. This query retrieves employees who have salaries between 50000 and 100000.
    public void query07() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select *\n" +
                " from employees\n" +
                " left join salaries ON employees.emp_no = salaries.emp_no\n" +
                " where salaries.salary between 50000 and 100000\n" +
                " order by salary");
        DBConnectionClose();

    }


    @Test
    //8. Calculate the average salary for each department (by department number or department name)
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


    @Test
    //9.Calculate the average salary for each department, including department names
    public void query09() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT d.dept_no, d.dept_name, AVG(s.salary) AS average_salary " +
                "FROM employees e " +
                "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                "join salaries s ON e.emp_no = s.emp_no " +
                "join departments d ON de.dept_no = d.dept_no " +
                "GROUP BY d.dept_no, d.dept_name");

        while (rs.next()) {
            String deptNo = rs.getString("dept_no");
            String deptName = rs.getString("dept_name");
            double averageSalary = rs.getDouble("average_salary");
            System.out.println("Department Details - Dept No: " + deptNo + ", Dept Name: "
                    + deptName + ", Average Salary: " + Math.round(averageSalary * 100.0) / 100.0);
        }
        DBConnectionClose();
    }


    @Test
    //10. Find all salary changes for employee with emp. no '10102'
    public void query10() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT emp_no, salary, from_date, to_date " +
                "FROM salaries " +
                "WHERE emp_no = '10102' " +
                "ORDER BY from_date");

        while (rs.next()) {
            int empNo = rs.getInt("emp_no");
            double salary = rs.getDouble("salary");
            String fromDate = rs.getString("from_date");
            String toDate = rs.getString("to_date");
            System.out.println("Salary Details - Emp No: " + empNo + ", Salary: "
                    + Math.round(salary * 100.0) / 100.0 + ", From Date: " + fromDate + ", To Date: " + toDate);
        }
        DBConnectionClose();
    }


    @Test
    //Find the salary increases for employee with employee number '10102' (using the to_date colum
    // in salaries)

    public void query11() throws SQLException {

        {
            DBConnectionOpen();
            ResultSet rs = statement.executeQuery("SELECT emp_no, salary, to_date " +
                    "FROM salaries " +
                    "WHERE emp_no = '10102' " +
                    "ORDER BY to_date");

            while (rs.next()) {
                int empNo = rs.getInt("emp_no");
                double salary = rs.getDouble("salary");
                String toDate = rs.getString("to_date");
                System.out.println("Salary Details - Emp No: " + empNo + ", Salary: "
                        + String.format("%.2f", salary) + ", To Date: " + toDate);
            }
            DBConnectionClose();

        }
    }


    @Test
    //12. Find the employee with the highest salary
    public void query12() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT * FROM employees " +
                "JOIN salaries ON employees.emp_no = salaries.emp_no " +
                "ORDER BY salary DESC " +
                "LIMIT 1");

        if (rs.next()) {
            int empNo = rs.getInt("emp_no");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            double salary = rs.getDouble("salary");
            System.out.println("Employee Details - Emp No: " + empNo + ", First Name: " + firstName + ", Last Name: " + lastName + ", Salary: " + Math.round(salary * 100.0) / 100.0);
        }

        DBConnectionClose();
    }


    @Test
    //Find the latest salaries for each employee
    public void query13() throws SQLException {

        DBConnectionOpen();

        System.out.println("Executing Task 13: Find the latest salaries for each employee.");
        ResultSet result = statement.executeQuery("SELECT emp_no, salary, to_date " +
                "FROM salaries " +
                "WHERE (emp_no, to_date) IN (SELECT emp_no, MAX(to_date) FROM salaries GROUP BY emp_no)");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            int empNo = result.getInt("emp_no");
            double salary = result.getDouble("salary");
            String toDate = result.getString("to_date");
            System.out.println("Salary Details " + "- Emp No: " + empNo +
                    ", Salary: " + Math.round(salary * 100.0) / 100.0 +
                    ", To Date: " + toDate);
        }

        DBConnectionClose();
    }


    @Test
    // List the first name, last name, and highest salary of employees in the "Sales" department.
    //Order the list by highest salary descending and only show the employee with the highest salary.

    public void query14() throws SQLException {

        DBConnectionOpen();

        System.out.println("Executing Task 14: List the first name, last name, and highest salary of employees in the 'Sales' department.");
        ResultSet result = statement.executeQuery("SELECT e.first_name, e.last_name, MAX(s.salary) AS highest_salary " +
                "FROM employees e " +
                "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE d.dept_name = 'Sales' " +
                "GROUP BY e.first_name, e.last_name " +
                "ORDER BY highest_salary DESC " +
                "LIMIT 1");

        if (result.next()) {
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            double highestSalary = result.getDouble("highest_salary");
            System.out.println("Employee Details - First Name: " + firstName
                    + ", Last Name: " + lastName + ", Highest Salary: " + Math.round(highestSalary * 100.0) / 100.0);
        }

        DBConnectionClose();
    }


    @Test
    // Find the Employee with the Highest Salary Average in the Research Department
    public void query15() throws SQLException {

        DBConnectionOpen();

        System.out.println("Executing Task 15: Find the employee with the highest salary in the Research department.");
        ResultSet result = statement.executeQuery("SELECT e.first_name, e.last_name, MAX(s.salary) AS max_salary " +
                "FROM employees e " +
                "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE d.dept_name = 'Research' " +
                "GROUP BY e.first_name, e.last_name " +
                "ORDER BY max_salary DESC " +
                "LIMIT 1");

        if (result.next()) {
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            double maxSalary = result.getDouble("max_salary");
            System.out.println("Employee Details - First Name: " + firstName + ", Last Name: "
                    + lastName + ", Max Salary: " + Math.round(maxSalary * 100.0) / 100.0);
        }

        DBConnectionClose();
    }


    @Test
    //For each department, identify the employee with the highest single salary ever recorded. List the
    //department name, employee's first name, last name, and the peak salary amount. Order the results
    //by the peak salary in descending order.

    public void query16() throws SQLException {

        DBConnectionOpen();

        System.out.println("Executing Task 16: For each department, identify the employee with the highest single salary ever recorded.");
        ResultSet result = statement.executeQuery("SELECT d.dept_name AS department, e.first_name, e.last_name, s.salary AS max_salary " +
                "FROM employees e " +
                "INNER JOIN dept_emp de ON e.emp_no = de.emp_no " +
                "INNER JOIN departments d ON de.dept_no = d.dept_no " +
                "INNER JOIN salaries s ON e.emp_no = s.emp_no " +
                "WHERE s.salary = (SELECT MAX(salary) FROM salaries WHERE emp_no = e.emp_no) " +
                "GROUP BY d.dept_name, e.first_name, e.last_name, s.salary " +
                "ORDER BY max_salary DESC");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            String department = result.getString("department");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            double maxSalary = result.getDouble("max_salary");
            System.out.println("Department Details - Department: " + department
                    + ", First Name: " + firstName + ", Last Name: " + lastName + ", Max Salary: "
                    + Math.round(maxSalary * 100.0) / 100.0);
        }

        DBConnectionClose();
    }


    @Test
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


    @Test
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


    @Test
    //List the names, last names, hire dates of all employees hired between 01 01 1985
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


    @Test
    //List the names, last names, hire dates, and salaries of all employees in the Sales department
    //who were hired between 01 01 1985 and December 31, 1989, sorted by salary in descending order.
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


    @Test
    //a: Find the count of male employees (179973)
    public void query21a() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) AS male_employee_count " +
                        "FROM employees " +
                        "WHERE gender = 'M';");

        DBConnectionClose();
    }


    @Test
    //b: Determine the count of female employees (120050)
    public void query21b() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) AS female_employee_count " +
                        "FROM employees " +
                        "WHERE gender = 'F';");

        DBConnectionClose();
    }


    @Test
    //c: Find the number of male and female employees by grouping
    public void query21c() throws SQLException {
        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT gender, COUNT(*) AS employee_count " +
                        "FROM employees " +
                        "GROUP BY gender;");

        DBConnectionClose();
    }


    @Test
    //d: Calculate the total number of employees in the company (300023)
    public void query21d() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) " +
                "FROM employees;");

        DBConnectionClose();
    }


    @Test
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


    @Test
    //b: Identify the number of distinct department names (9)
    public void query22b() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT COUNT(DISTINCT departments.dept_name)" +
                "FROM departments;");

        DBConnectionClose();
    }


    @Test
    // List the number of employees in each department
    public void query23() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT  departments.dept_name, count(dept_emp.emp_no)" + " " +
                "FROM departments" + " " +
                "JOIN dept_emp on departments.dept_no=dept_emp.dept_no" + " " +
                "GROUP BY departments.dept_name;");

        DBConnectionClose();
    }


    @Test
    // List all employees hired within the last 5 years from February 20, 1990
    public void query24() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT employees.first_name, employees.last_name, employees.hire_date" + " " +
                "FROM employees" + " " +
                "WHERE employees.hire_date between '1990-02-20' and '1995-02-20';");
        DBConnectionClose();
    }


    @Test
    // 25 - Annemarie Redmiles" adlı çalışanın bilgilerini (çalışan numarası, doğum tarihi, ilk adı, soyadı,
    // cinsiyet, işe alınma tarihi listele.
    public void query25() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select emp_no,birth_date,first_name,last_name,gender,hire_date\n" +
                "from employees\n" +
                "where first_name='Annemarie' and last_name='Redmiles';");

        DBConnectionClose();
    }


    @Test
    // 26 - "Annemarie Redmiles" adlı çalışanın tüm bilgilerini (çalışan numarası, doğum tarihi, ilk adı,
    //     soyadı, cinsiyet, işe alınma tarihi, maaş, departman ve unvan) listele.
    public void query26() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select employees.emp_no, birth_date, first_name, last_name, gender, hire_date, salaries.salary, departments.dept_name, titles.title\n" +
                "from dept_emp\n" +
                "left join employees on employees.emp_no= dept_emp.emp_no\n" +
                "left join departments on departments.dept_no= dept_emp.dept_no\n" +
                "left join titles on titles.emp_no= dept_emp.emp_no\n" +
                "left join salaries on salaries.emp_no= dept_emp.emp_no\n" +
                "where first_name='Annemarie' and last_name='Redmiles';");

        DBConnectionClose();
    }


    @Test
    // 27 - D005 bölümündeki tüm çalışanları ve yöneticileri listele
    public void query27() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select* \n" +
                "from dept_emp\n" +
                "left join dept_manager ON dept_emp.dept_no=dept_manager.dept_no\n" +
                "where dept_emp.dept_no='d005' and dept_manager.dept_no='d005';");

        DBConnectionClose();
    }


    @Test
    //'1994-02-24' tarihinden sonra işe alınan ve 50.000'den fazla kazanan tüm çalışanları listele
    public void query28() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select* \n" +
                "from employees\n" +
                "left join salaries on employees.emp_no=salaries.emp_no\n" +
                "where employees.hire_date >'1994-02-24'\n" +
                "and salaries.salary >'50000';");

        DBConnectionClose();
    }


    @Test
    // 29. List all employees working in the "Sales" department with the title "Manager"
    public void query29() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT employees.*, departments.dept_name\n" +
                "FROM employees\n" +
                "LEFT JOIN dept_manager ON dept_manager.emp_no = employees.emp_no\n" +
                "LEFT JOIN departments ON departments.dept_no = dept_manager.dept_no\n" +
                "WHERE departments.dept_no = 'd007'");

        DBConnectionClose();

    }


    @Test
    //30. Find the department where employee with '10102' has worked the longest
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


    @Test
    //31. Find the highest paid employee in department D004
    public void query31() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select employees.*,salaries.salary\n" +
                "from employees\n" +
                "LEFT JOIN salaries ON salaries.emp_no=employees.emp_no\n" +
                "order by salary desc\n" +
                "LIMIT 1");

        DBConnectionClose();

    }


    @Test
    //32. Find the entire position history for employee with emp. no '10102'
    public void query32() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("select * from titles\n" +
                "where emp_no='10102'");

        DBConnectionClose();
    }


    @Test
    //Finding the average "employee age"
    public void query33() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery("SELECT AVG(TIMESTAMPDIFF(YEAR, birth_date, CURDATE())) AS average_age FROM employees");

        if (rs.next()) {
            double avgAge = rs.getDouble("average_age");
            System.out.println("Average Employee Age: " + avgAge + " years");
        }

        DBConnectionClose();
    }


    @Test
    //Finding the number of employees per department
    public void query34() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT d.dept_name, COUNT(de.emp_no) AS num_employees " +
                        "FROM employees.dept_emp de " +
                        "JOIN employees.departments d ON de.dept_no = d.dept_no " +
                        "GROUP BY d.dept_name"
        );

        while (rs.next()) {
            String deptName = rs.getString("dept_name");
            int numEmployees = rs.getInt("num_employees");
            System.out.println("Department: " + deptName + ", Number of Employees: " + numEmployees);
        }

        DBConnectionClose();
    }


    @Test
    //Finding the managerial history of employee with ID (emp. no) 110022
    public void query35() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT dept_no, from_date, to_date " +
                        "FROM employees.dept_manager " +
                        "WHERE emp_no = '110022' " +
                        "ORDER BY from_date ASC"
        );

        while (rs.next()) {
            String deptNo = rs.getString("dept_no");
            String fromDate = rs.getString("from_date");
            String toDate = rs.getString("to_date");
            System.out.println("Managerial History - Dept No: " + deptNo + ", From Date: " + fromDate + ", To Date: " + toDate);
        }

        DBConnectionClose();
    }


    @Test
    //Find the duration of employment for each employee.
    public void query36() throws SQLException {

        DBConnectionOpen();
        ResultSet rs = statement.executeQuery(
                "SELECT emp_no, first_name, last_name, DATEDIFF(CURDATE(), hire_date) AS employment_duration " +
                        "FROM employees.employees " +
                        "ORDER BY employment_duration DESC");


        while (rs.next()) {
            int empNo = rs.getInt("emp_no");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            int duration = rs.getInt("employment_duration");

            System.out.println("Employee - Emp No: " + empNo + ", Name: "
                    + firstName + " " + lastName + ", Duration: " + duration + " days");
        }

        DBConnectionClose();
    }


    @Test
    //Find the latest title information for each employee.
    public void query37() throws SQLException {

        DBConnectionOpen();
        ResultSet result = statement.executeQuery("SELECT emp_no, title, from_date " +
                "FROM employees.titles " +
                "WHERE to_date = '9999-01-01'");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            int empNo = result.getInt("emp_no");
            String title = result.getString("title");
            String fromDate = result.getString("from_date");
            System.out.println("Employee Details - Emp No: " + empNo
                    + ", Title: " + title + ", From Date: " + fromDate);
        }

        DBConnectionClose();
    }


    @Test
    // Find the first and last names of managers in department 'D005'.
    public void query38() throws SQLException {

        DBConnectionOpen();
        ResultSet result = statement.executeQuery("SELECT e.first_name, e.last_name " +
                "FROM employees.employees e " +
                "JOIN employees.dept_manager dm ON e.emp_no = dm.emp_no " +
                "WHERE dm.dept_no = 'd005'");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            System.out.println("Manager Details - First Name: " + firstName + ", Last Name: " + lastName);
        }

        DBConnectionClose();
    }


    @Test
    //Sort employees by their birth dates.
    public void query39() throws SQLException {

        DBConnectionOpen();
        ResultSet result = statement.executeQuery("SELECT * " +
                "FROM employees.employees " +
                "ORDER BY birth_date");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            int empNo = result.getInt("emp_no");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String birthDate = result.getString("birth_date");
            System.out.println("Employee Details - Emp No: " + empNo + ", First Name: " + firstName + ", Last Name: "
                    + lastName + ", Birth Date: " + birthDate);
        }

        DBConnectionClose();
    }


    @Test
    //List employees hired in April 1992
    public void query40() throws SQLException {

        DBConnectionOpen();
        ResultSet result = statement.executeQuery("SELECT emp_no, first_name, last_name, gender, hire_date " +
                "FROM employees.employees " +
                "WHERE hire_date BETWEEN '1992-04-01' AND '1992-04-30' " +
                "ORDER BY hire_date ASC");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            int empNo = result.getInt("emp_no");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String gender = result.getString("gender");
            String hireDate = result.getString("hire_date");
            System.out.println("Employee Details - Emp No: " + empNo + ", First Name: "
                    + firstName + ", Last Name: " + lastName + ", Gender: " + gender + ", Hire Date: " + hireDate);
        }

        DBConnectionClose();
    }


    @Test
    //Find all departments that employee '10102' has worked in.
    public void query41() throws SQLException {

        DBConnectionOpen();
        ResultSet result = statement.executeQuery("SELECT DISTINCT e.first_name, e.last_name, d.dept_name " +
                "FROM employees.dept_emp de " +
                "JOIN employees.employees e ON de.emp_no = e.emp_no " +
                "JOIN employees.departments d ON de.dept_no = d.dept_no " +
                "WHERE de.emp_no = '10102'");

        int rowCount = 0;
        while (result.next() && rowCount < 10) {
            rowCount++;
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String deptName = result.getString("dept_name");
            System.out.println("Department Details - First Name: " + firstName + ", Last Name: "
                    + lastName + ", Department Name: " + deptName);
        }

        DBConnectionClose();
    }

}