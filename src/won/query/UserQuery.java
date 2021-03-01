package won.query;

import won.db.DBConnect;
import won.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.System.out;

/**
 * 提供操作数据库表user的方法，提供各种查询，将数据库数据转为User对象模型
 */
public class UserQuery {
    public UserQuery() {
    }

    public static void resetPassword(long userid,String newpassword) {
        PreparedStatement prestmt = null;
        try {
            String sql = "update user SET  password=? where id=?";
            prestmt = DBConnect.getConnect().prepareStatement(sql);
            prestmt.setString(1, newpassword);
            prestmt.setLong(2, userid);
            int n=prestmt.executeUpdate();
            if (prestmt != null) {
                prestmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("修改密码成功！");
    }

    public static User queryUser(String username, String password) {
        ResultSet resultSet = null;
        PreparedStatement prestmt = null;
        User user = null;
        try {
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            prestmt = DBConnect.getConnect().prepareStatement(sql);
            prestmt.setString(1, username);
            prestmt.setString(2, password);
            resultSet = prestmt.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                user = new User(id, username, password);
            }

            if (resultSet != null) {
                resultSet.close();
            }
            if (prestmt != null) {
                prestmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 用户账号已存在或注册失败返回 null，成功则返回User对象
     **/
    public static User registerUser(String username, String password) {
        out.println("正在进行注册...");
        User user = null;
        if (queryUser(username, password) != null) {
            out.println("用户已存在，请直接登录！");
            return null;
        }
        PreparedStatement preparedStatement = null;
        String sql = null;
        boolean isSuccess = false;
        try {
            sql = "insert into user (username,password) values(?,?)";
            preparedStatement = DBConnect.getConnect().prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int n = preparedStatement.executeUpdate();
            if (n == 1) {
                user = queryUser(username, password);
                System.out.println("注册成功！");
            } else {
                System.out.println("注册失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
