package won.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 设置连接属性，返回连接对象
 */
public class DBConnect {
    static String DB = "bookmanagesys";
    static String username = "root";
    static String password = "666666";
    static Connection connection = null;

    public static Connection getConnect() {
        // 1.加载驱动
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                // 2.创建连接，%s是数据库名称，后面的参数会替换
                String url = String.format("jdbc:mysql://localhost:3306/%s?useUnicode=true&characterEncoding=utf8", DB);
                connection = DriverManager.getConnection(url, username, password);
                //System.out.println("connect is running...");
                return connection;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("connect mysql success...");
        return connection;
    }

    public static void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        DBConnect.getConnect();
//    }
}
