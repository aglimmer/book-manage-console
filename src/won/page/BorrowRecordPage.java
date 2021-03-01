package won.page;

import won.db.DBConnect;
import won.model.Book;
import won.model.BorrowRecord;
import won.model.QueryMode;
import won.model.User;
import won.query.BookQuery;
import won.query.BorrowRecordQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static java.lang.System.out;

/**
 * 对于BorrowRecord的操作窗口，主要调用query.BorrowRecordQuery里面的查询方法
 */
public class BorrowRecordPage {
    static Scanner input = new Scanner(System.in);

    public BorrowRecordPage() {
    }


    public static void borrowBookDetail(User user) {
        System.out.println("********************借阅图书********************");
        while(true) {
            System.out.println("功能选项：【1】用户借书\t【2】查看借阅记录");
            System.out.print("请选择功能：");
            String selectItem = input.nextLine();
            switch (selectItem) {
                case "1":
                    borrowBook(user);
                    break;
                case "2":
                    queryBorrowRecord(user);
                    break;
            }
            out.println("是否继续当前任务：【1】继续，请输入1\t 【2】返回，请输入Enter");
            out.print("请选择：");
            String msg = input.nextLine();
            if(!msg.equals("1")){
                break;
            }
        }
    }
    public static void borrowBook(User user){
        List<Book> bookList = new LinkedList<>();
        boolean isAvailable = false;
        QueryMode queryMode = null;
        out.println("--------------------用户借书--------------------");
        out.println("查找图书方式：【1】图书ID\t 【2】书名");
        out.print("请选择方式：");
        String selectItem = input.nextLine();
        switch (selectItem) {
            case "1":
                System.out.print("请输入图书ID：");
                int id = Integer.parseInt(input.nextLine());
                bookList = BookQuery.queryAvailableBook(QueryMode.ById, Book.byId(id));
                isAvailable = !bookList.isEmpty();
                queryMode = QueryMode.ById;
                break;
            case "2":
                System.out.print("请输入书名：");
                String bookname = input.nextLine();
                bookList = BookQuery.queryAvailableBook(QueryMode.ByBookname, Book.byBookname(bookname));
                isAvailable = !bookList.isEmpty();
                queryMode = QueryMode.ByBookname;
                break;
        }
        if (isAvailable) {
            Book book = bookList.get(0);
            Book.display(book,true);
            System.out.println("提示：【1】借阅【2】取消");
            System.out.print("选择：");
            int select = Integer.parseInt(input.nextLine());
            if (select == 1) {
                //减少可借数量
                BorrowRecordQuery.decreaseBookSurplus(book, queryMode);
                //添加借阅记录
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                //可借30天
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                String backdate = String.format("%tF", calendar);
                String borrowdate = String.format("%tF", date);
                BorrowRecord borrowRecord = new BorrowRecord();
                borrowRecord.setBookid(book.getId());
                borrowRecord.setUserid(user.getId());
                borrowRecord.setBorrowdate(borrowdate);
                borrowRecord.setBackdate(backdate);
                BorrowRecordQuery.addBorrowRecord(borrowRecord);
                out.println("借书成功！");
            } else {
                out.println("借书失败！");
            }
        } else {
            out.println("没有查询到相应图书");
        }

    }

    public static void queryBorrowRecord(User user) {
        System.out.println("--------------------借阅记录--------------------");
        out.println("功能：【1】查看当前借阅记录\t【2】查看所有记录");
        out.print("请选择：");
        int select = Integer.parseInt(input.nextLine());
        Connection connection = DBConnect.getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = null;
        List<BorrowRecord> borrowRecords = new LinkedList<>();
        if (select == 1) {
            borrowRecords = BorrowRecordQuery.queryBorrowRecordByUserId(user.getId());
        } else if (select == 2) {
            borrowRecords = BorrowRecordQuery.queryAllBorrowRecord();
        }
        if (!borrowRecords.isEmpty()) {
            out.println("查询到如下记录：");
            for (int i = 0; i < borrowRecords.size(); i++) {
                BorrowRecord.display(borrowRecords.get(i), i == 0);
            }
            out.println("操作：【1】删除记录，请输入1 \t【2】返回，请按其他任意键");
            out.print("请输入选项：");
            String msg = input.nextLine();
            long recordId = 0;
            if (msg.equals("1")) {
                out.print("请输入要删除记录的ID：");
                recordId = Long.parseLong(input.nextLine());
                for (BorrowRecord borrowRecord : borrowRecords) {
                    if (borrowRecord.getId() == recordId) {
                        //找到则删除
                        BorrowRecordQuery.deleteRecordByRecordId(recordId);
                        break;
                    }
                }
            }

        } else {
            out.println("没有查到相关记录！");
        }
    }

    public static void returnBook(User user) {
        System.out.println("********************图书归还********************");
        List<BorrowRecord> records = BorrowRecordQuery.queryBorrowRecordByUserId(user.getId());
        if (!records.isEmpty()) {
            for (int i = 0; i < records.size(); i++) {
                BorrowRecord.display(records.get(i), i == 0);
            }
            out.print("请选择记录ID：");
            long recordId = Long.parseLong(input.nextLine());
            for (BorrowRecord borrowRecord : records) {
                if (borrowRecord.getId() == recordId) {
                    if (!BorrowRecordQuery.isOverDate(borrowRecord)) {
                        BookQuery.increaseBookSurplus(borrowRecord.getBookid());
                        borrowRecord.setState("已还");

                        BorrowRecordQuery.updateBorrowRecord(borrowRecord);
                    } else {
                        out.println("借阅逾期，请到服务台归还" + user);
                    }
                }
            }
        } else {
            out.println("没有找到相应记录！");
        }
    }

}
