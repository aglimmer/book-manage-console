package won.model;


import java.util.List;

import static java.lang.System.out;
/**
 * Book数据模型，与数据库表book对应
 **/
public class Book {
    private long id;
    private String bookname;
    private String putaway;
    private int tatal;
    private int surplus;


    public Book() {
    }

    public Book(String bookname, String putaway, int tatal) {
        this.bookname = bookname;
        this.putaway = putaway;
        this.tatal = tatal;
        this.surplus = tatal;
    }

    public static Book byId(long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }

    public static void diaplay(List<Book> bookList) {
        if (bookList != null) {
            for (int i = 0; i < bookList.size(); i++) {
                display(bookList.get(i), i==0);
            }
        }
    }


    public static void display(Book book, boolean isExistTitle) {
        if (isExistTitle) {
            out.println("ID\t书名\t上架日期\t总数\t可借数量");
        }
        out.println(book.getId()+"\t"+book.getBookname()+"\t"+book.getPutaway()+"\t"+book.getTatal()+"\t"+book.getSurplus());
    }

    public static Book byBookname(String bookName) {
        Book book = new Book();
        book.setBookname(bookName);
        return book;
    }

    public Book(long id, String bookname, String putaway, int tatal, int surplus) {
        this.id = id;
        this.bookname = bookname;
        this.putaway = putaway;
        this.tatal = tatal;
        this.surplus = surplus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPutaway() {
        return putaway;
    }

    public void setPutaway(String putaway) {
        this.putaway = putaway;
    }

    public int getTatal() {
        return tatal;
    }

    public void setTatal(int tatal) {
        this.tatal = tatal;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }


    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
