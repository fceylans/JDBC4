import java.sql.*;

public class DataBaseHelper {//Bu sınıf, veritabanına bağlanmak için gerekli bağlantı işlemlerini yapar. Bağlantıyı kurmak ve kapatmak için yardımcı metotlar içerir.
    private static Connection connection;
    public static Statement statement;

    public static void DBConnectionOpen() {
        String url = "jdbc:mysql://demo.mersys.io:33906/employees";
        String username = "admin";
        String password = "Techno24Study.%=";

        // Veritabanına bağlantı kuran metod
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Bağlantıyı kapatma işlemi
    public static void DBConnectionClose() {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Sorgu sonuçlarını yazdır
    public static void QueryResults(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i) + " ");// Her sütunun değerini yazdırır
            }
            System.out.println();
        }
    }

}