package won.model;

import java.util.List;

/**
 * 数据模型BorrowRecord与数据库表对应
 */
public class BorrowRecord {
    private long id;
    private long bookid;
    private long userid;
    private String borrowdate;
    private String backdate;
    private String state;
    public BorrowRecord() {
    }
    public static void display(List<BorrowRecord> borrowRecordList){
        for(int i=0;i<borrowRecordList.size();i++){
            display(borrowRecordList.get(i),i==0);
        }
    }
    public static void display(BorrowRecord borrowRecord,boolean isExistTitle){
        if(isExistTitle){
            System.out.println("记录ID\t图书ID\t用户ID\t借书日期\t应还日期\t状态");
        }
        System.out.println(borrowRecord.getId() + "\t" + borrowRecord.getBookid() + "\t"
                + borrowRecord.getUserid() + "\t" + borrowRecord.getBorrowdate() + "\t"
                + borrowRecord.getBackdate() + "\t" + borrowRecord.getState());

    }

    public BorrowRecord(long id, long bookid, long userid, String borrowdate, String backdate, String state) {
        this.id = id;
        this.bookid = bookid;
        this.userid = userid;
        this.borrowdate = borrowdate;
        this.backdate = backdate;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(String borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getBackdate() {
        return backdate;
    }

    public void setBackdate(String backdate) {
        this.backdate = backdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");

    }
}
