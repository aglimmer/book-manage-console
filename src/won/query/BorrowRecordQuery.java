package won.query;

import won.db.DBConnect;
import won.model.Book;
import won.model.BorrowRecord;
import won.model.QueryMode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;
/**
 * 提供查询数据库表borrowrecord的方法，将对应的数据转为对象BorrowRecord模型
 **/
public class BorrowRecordQuery {
    Scanner input = new Scanner(System.in);

    public static void decreaseBookSurplus(Book book, QueryMode queryMode) {
        PreparedStatement preparedStatement = null;
        String sql = null;
        try {
            switch (queryMode) {
                case ById:
                    sql = "update book set surplus=surplus-1 and id=?";
                    preparedStatement = DBConnect.getConnect().prepareStatement(sql);
                    preparedStatement.setLong(1, book.getId());
                    preparedStatement.executeUpdate();
                    break;
                case ByBookname:
                    sql = "update book set surplus=surplus-1 and bookname=?";
                    preparedStatement = DBConnect.getConnect().prepareStatement(sql);
                    preparedStatement.setString(1, book.getBookname());
                    preparedStatement.executeUpdate();
                    break;
            }
        } catch (SQLException e) {
            out.println(e);
        }
    }


    public static void updateBorrowRecord(BorrowRecord borrowRecord) {
        Connection connection = DBConnect.getConnect();
        try {
            String sql = "UPDATE borrowrecord SET borrowdate=?,backdate=?,state=? WHERE id=?";
            PreparedStatement prestmt = connection.prepareStatement(sql);
            prestmt.setString(1, borrowRecord.getBorrowdate());
            prestmt.setString(2, borrowRecord.getBackdate());
            prestmt.setString(3, borrowRecord.getState());
            prestmt.setLong(4, borrowRecord.getId());
            prestmt.executeUpdate();
        } catch (SQLException e) {
            out.println(e);
        }
        out.println("修改成功！ ");

    }



    public static void addBorrowRecord(BorrowRecord borrowRecord) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement prestmt = null;
        try {
            connection = DBConnect.getConnect();
            //id,bookid,userid,borrowdate,backdate,state
            String sql = "insert into borrowrecord (bookid,userid,borrowdate,backdate,state) values(?,?,?,?,?)";
            prestmt = connection.prepareStatement(sql);
            prestmt.setLong(1, borrowRecord.getBookid());
            prestmt.setLong(2, borrowRecord.getUserid());
            prestmt.setString(3, borrowRecord.getBorrowdate());
            prestmt.setString(4,borrowRecord.getBackdate());
            prestmt.setString(5, "已借");
            prestmt.executeUpdate();
            System.out.println("已添加借阅记录----");
            if (prestmt != null) {
                prestmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<BorrowRecord> queryBorrowRecordByUserId(long userId) {
        Connection connection = DBConnect.getConnect();
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = null;
            String sql = "select * from borrowrecord where userid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            out.println(e);
        }
        return getBorrowRecordByResultSet(resultSet);
    }
    public static List<BorrowRecord> queryAllBorrowRecord(){
        Connection connection = DBConnect.getConnect();
        ResultSet resultSet = null;
        try {
            String sql = "select * from borrowrecord";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            out.println(e);
        }
        return getBorrowRecordByResultSet(resultSet);
    }


    public static List<BorrowRecord> getBorrowRecordByResultSet(ResultSet resultSet) {
        List<BorrowRecord> borrowRecords = new LinkedList<>();
        if (resultSet == null) {
            return borrowRecords;
        }
        try {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long bookid = resultSet.getInt("bookid");
                long userid = resultSet.getLong("userid");
                String borrowdate = resultSet.getString("borrowdate");
                String backdate = resultSet.getString("backdate");
                String state = resultSet.getString("state");
                borrowRecords.add(new BorrowRecord(id, bookid, userid, borrowdate, backdate, state));
            }
        } catch (SQLException e) {
            out.println(e);
        }
        return borrowRecords;

    }

    public static void deleteRecordByRecordId(long recordId) {
        // TODO Auto-generated method stub
        out.println("正在删除该条记录...");
        Connection connection = null;
        String sql = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnect.getConnect();
            sql = "delete FROM borrowrecord WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, recordId);
            preparedStatement.executeUpdate();
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            out.println(e);
        }
        out.println("删除成功！");
    }


    public static boolean isOverDate(BorrowRecord borrowRecord) {
        //算两个日期间隔多少天
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = new Date();
        //把当前日期转为yyy-MM-dd格式
        //String currDateStr = String.format("%tF", currDate);
        Date backDate = null;
        try {
            backDate = simpleDateFormat.parse(borrowRecord.getBackdate());
        } catch (Exception e) {
            out.println(e);
        }
        int day = (int) ((currDate.getTime() - backDate.getTime()) / (1000 * 3600 * 24));
        return day > 0;
    }

}
