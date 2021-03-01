package won.page;

import won.model.Book;
import won.model.QueryMode;
import won.query.BookQuery;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * 对book的操作窗口，提供粗粒度的调用功能
 */
public class BookPage {
    static Scanner input = new Scanner(System.in);
    public BookPage() {
    }


    public static void queryBook() {
        out.println("********************查询图书********************");
        out.println("                【1】查询所有");
        out.println("                【2】按书名查询");
        out.println("                【3】按 ID 查询");
        out.print("请选择方式:");
        List<Book> bookList = null;
        String selectItem = input.nextLine();
        switch (selectItem) {
            case "1":
                bookList = BookQuery.queryAllBook();
                break;
            case "2":
                out.print("请输入书名：");
                String bookname = input.nextLine();
                bookList = BookQuery.queryBook(Book.byBookname(bookname),QueryMode.ByBookname);
                break;
            case "3":
                out.print("请输入ID：");
                long id = Long.parseLong(input.nextLine());
                bookList = BookQuery.queryBook(Book.byId(id), QueryMode.ById);
                break;
        }
        Book.diaplay(bookList);
    }
    public static void deleteBook() {
        System.out.println("********************删除旧书********************");
        out.println("提示：请谨慎执行删除操作！");
        out.print("请输入删除的图书ID：");
        long bookid = Long.parseLong(input.nextLine());
        BookQuery.deleteBookById(bookid);
    }
    public static void addBook() {
        System.out.println("********************新书添加********************");
        Date date=new Date();
        String putaway = String.format("%tF", date);
        out.print("请输入书名：");
        String bookname = input.nextLine();
        out.print("请输入数量：");
        int total = Integer.parseInt(input.nextLine());
        Book book = new Book(bookname,putaway,total);
        BookQuery.addBook(book);

    }
    public static void getLatestBook(){
        System.out.println("********************今日新书********************");
        System.out.println("正在查询今日新书...");
        List<Book> bookList = BookQuery.queryTodayBook();
        if(!bookList.isEmpty()){
           Book.diaplay(bookList);
        }
        System.out.println("提示：今日暂无新书!");
    }

    public static void modifyBook() {
        System.out.println("********************修改图书********************");
        //修改 ：1.编号--书名 2.编号--日期 3.编号--总数量 4.编号--借出剩余的数量
        System.out.println("正在进行图书编辑修改...");
        Scanner input = new Scanner(System.in);
        System.out.print("请输入图书ID:");
        long bookid = Integer.parseInt(input.nextLine());
        System.out.println("正在查询...");
        Book book = null;
        List<Book> bookList = BookQuery.queryBook(Book.byId(bookid), QueryMode.ById);
        if (bookList.isEmpty()) {
            out.println("该图书不存在");
        } else {
            book = bookList.get(0);
            while (true) {
                Book.display(book, true);
                System.out.println("替换提示：【1】书名【2】日期【3】总数量【4】可借数量");
                System.out.print("选择:");
                int n = Integer.parseInt(input.nextLine());
                switch (n) {
                    case 1:
                        out.print("替换书名为:");
                        book.setBookname(input.nextLine());
                        break;
                    case 2:
                        out.print("替换日期为（yyyy-MMM-ddd)：");
                        book.setPutaway(input.nextLine());
                        break;
                    case 3:
                        out.print("替换总数量为:");
                        book.setTatal(Integer.parseInt(input.nextLine()));
                        break;
                    case 4:
                        out.print("替换可借数量为:");
                        book.setSurplus(Integer.parseInt(input.nextLine()));
                        break;
                }
                out.println("是否继续修改：【1】继续，请输入1 【2】返回，请按其他任意键");
                out.print("请选择：");
                String isContinue = input.nextLine();
                if (!isContinue.equals("1")) {
                    break;
                }
            }
            BookQuery.updateBook(book);

        }
    }

}
