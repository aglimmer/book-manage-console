package won.query;

import won.db.DBConnect;
import won.model.QueryMode;
import won.model.Book;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;
/**
 * 提供查询数据库表book的操作方法，将数据转为Book对象模型
 **/
public class BookQuery {

    Scanner input = new Scanner(System.in);

    public static List<Book> queryAllBook(){
        ResultSet resultSet = null;
        List<Book> bookList = null;
        try {
            Statement statement = DBConnect.getConnect().createStatement();
            String sql = "SELECT * FROM book";
            resultSet = statement.executeQuery(sql);
            bookList = getBookByResultSet(resultSet);
        }catch (SQLException e){
            out.println(e);
        }
        return bookList;
    }


    public static List<Book> queryBook(Book book, QueryMode queryMode) {
        PreparedStatement prestmt = null;
        ResultSet resultSet = null;
        String sql = null;
        try {
            switch (queryMode) {
                case ById:
                    sql = "SELECT * FROM book WHERE id = ?";
                    prestmt = DBConnect.getConnect().prepareStatement(sql);
                    prestmt.setLong(1, book.getId());
                    break;
                case ByBookname:
                    sql = "SELECT * FROM book WHERE bookname like ?";
                    prestmt = DBConnect.getConnect().prepareStatement(sql);
                    prestmt.setString(1, "%" + book.getBookname() + "%");
                    break;
            }
            resultSet = prestmt.executeQuery();
        } catch (SQLException e) {
            out.println(e);
        }
        return getBookByResultSet(resultSet);
    }

    public static List<Book> getBookByResultSet(ResultSet resultSet){
        List<Book> bookList = new LinkedList<>();
        if(resultSet==null){
            return bookList;
        }
        try {
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String bookname = resultSet.getString("bookname");
                int total = resultSet.getInt("total");
                String indate = resultSet.getString("putaway");
                int surplus = resultSet.getInt("surplus");
                bookList.add(new Book(id, bookname, indate, total, surplus));
            }
        }catch (SQLException e){
            out.println(e);
        }
        return bookList;
    }
    public static void updateBook(Book book) {
        Connection connection = DBConnect.getConnect();
        String sql = "UPDATE book SET bookname=?,total=?,putaway=?,surplus=? WHERE id = ?";
        try {
            PreparedStatement prestmt = connection.prepareStatement(sql);
            prestmt.setString(1, book.getBookname());
            prestmt.setInt(2, book.getTatal());
            prestmt.setString(3, book.getPutaway());
            prestmt.setInt(4, book.getSurplus());
            prestmt.setLong(5, book.getId());
            prestmt.executeUpdate();
            if (prestmt != null) {
                prestmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException e){
            out.println(e);
        }
        out.println("修改成功！");
    }
    public static void addBook(Book book) {
        Connection connection = DBConnect.getConnect();
        PreparedStatement prestmt = null;
        try {
            String sql = "insert into book (bookname,putaway,total,surplus) values(?,?,?,?)";
            prestmt = connection.prepareStatement(sql);
            prestmt.setString(1, book.getBookname());
            prestmt.setString(2, book.getPutaway());
            prestmt.setInt(3,book.getTatal());
            prestmt.setInt(4,book.getSurplus());
            prestmt.executeUpdate();
            if (prestmt != null) {
                prestmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("添加成功！");
    }

    public static void deleteBookById(long id) {
        Connection connection = DBConnect.getConnect();
        ResultSet resultSet = null;
        PreparedStatement prestmt = null;
        int delState = -1;
        try {
            String sql = "DELETE FROM book WHERE id = ?";
            prestmt = connection.prepareStatement(sql);
            prestmt.setLong(1, id);
            delState = prestmt.executeUpdate();
            if (prestmt != null) {
                prestmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(delState==1){
            out.println("删除成功！");
        }else{
            out.println("该记录不存在，没有删除任何记录！");
        }

    }
    public static List<Book> queryTodayBook() {
        List<Book> bookList = new LinkedList<>();
        String sql="select * from book where to_days(putaway) = to_days(now())";
        ResultSet resultSet = null;
        try {
            Connection connection = DBConnect.getConnect();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            bookList = BookQuery.getBookByResultSet(resultSet);
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return bookList;
    }
    public void updateBook(long bookId) {
        Connection connection = DBConnect.getConnect();
        String sql = "update book set surplus=surplus+1 where id=?";
        try {
            PreparedStatement prestmt = connection.prepareStatement(sql);
            prestmt.setLong(1, bookId);
            prestmt.executeUpdate();
            if (prestmt != null) {
                prestmt.close();
            }
        } catch (SQLException e) {
            out.println(e);
        }
        out.println("图书已更新！");
    }

    public static List<Book> queryAvailableBook(QueryMode queryMode, Book book) {
        PreparedStatement prestmt = null;
        ResultSet resultSet = null;
        List<Book> bookList = new LinkedList<>();
        try {
            switch (queryMode) {
                case ById:
                    String sql = "select * from book where id=? and surplus>0";
                    prestmt = DBConnect.getConnect().prepareStatement(sql);
                    prestmt.setLong(1, book.getId());
                    break;
                case ByBookname:
                    //SELECT * FROM books WHERE bookname ='离散数学';
                    sql = "SELECT * FROM book WHERE bookname like ? and surplus>0";
                    prestmt = DBConnect.getConnect().prepareStatement(sql);
                    prestmt.setString(1, "%" + book.getBookname() + "%");
                    break;
            }
            resultSet = prestmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookname = resultSet.getString("bookname");
                int total = resultSet.getInt("total");
                String putaway = resultSet.getString("putaway");
                int surplus = resultSet.getInt("surplus");
                bookList.add(new Book(id, bookname, putaway, total, surplus));
            }
        } catch (SQLException e) {
            out.println(e);
        }
        return bookList;
    }
    public static void increaseBookSurplus(long bookid) {
        PreparedStatement preparedStatement = null;
        String sql = null;
        try {
            sql = "update book set surplus=surplus+1 and id=?";
            preparedStatement = DBConnect.getConnect().prepareStatement(sql);
            preparedStatement.setLong(1, bookid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            out.println(e);
        }
        out.println("图书数量已更新！ = ");
    }



}

