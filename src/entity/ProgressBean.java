package entity;

import java.io.Serializable;
import java.sql.Timestamp;
public class ProgressBean implements Serializable{
    private int id;
    private int today_pages;
    private int bookId;
    private Timestamp dateTime;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTodayPages() {
        return today_pages;
    }
    public void setTodayPages(int todayPages) {
        this.today_pages = todayPages;
    }
    public int getBookid() {
        return bookId;
    }
    public void setBookid(int bookid) {
        this.bookId = bookid;
    }
    public Timestamp getDatetime() {
        return dateTime;
    }
    public void setDatetime(Timestamp datetime) {
        this.dateTime = datetime;
    }
}
