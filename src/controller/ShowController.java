package controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.BooksDAO;
import dao.ProgressDAO;
import dto.BooksDTO;
import dto.ProgressDTO;
import entity.BooksBean;
import entity.ProgressBean;
import java.text.SimpleDateFormat;

public class ShowController {
    private ProgressDTO pdto;
    private ProgressDAO pdao;
    private ProgressBean pb;
    private BooksDTO bdto;
    private BooksDAO bdao;
    private BooksBean bb;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM/dd");
    private Timestamp timestampFromProgress;

    public ShowController() {
        this.pdao = new ProgressDAO();
        this.bdao = new BooksDAO();
        try {
            pdto = pdao.select(1); // refact
            bdto = bdao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bb = bdto.get(0);
        pb = pdto.get(0);
    }

    // 修正 select()でbookidを指定
    public ShowController(int bookId) {
        this.pdao = new ProgressDAO();
        this.bdao = new BooksDAO();
        try {
            pdto = pdao.select(bookId);
            bdto = bdao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bb = bdto.get(bookId);
        pb = pdto.get(bookId);
    }

    /*
     * DTO更新
     */
    public ProgressDTO updateDTO(int bookId) {
        try {
            this.pdto = pdao.select(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.pdto;
    }

    /*
     * 
     * 過去5日間のデータ
     */
    public List<String[]> RecentData(int bookId) {
        List<String[]> tableData = new ArrayList<>();
        try {
            tableData = pdao.select5RecentData(bookId);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableData;
    }

    /*
     * 1日の平均ページ
     */
    public String average(int bookId) {
        updateDTO(bookId);
        int sumPage = 0;
        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            sumPage += pb.getTodayPages();
        }

        return String.valueOf(sumPage / sumDays(bookId));
    }

    /*
     * 読んだ日の合計
     * 
     */
    public int sumDays(int bookId) {
        updateDTO(bookId);
        int sum = 0;
        String previousDate = null;
        String nowDate = null;

        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            timestampFromProgress = pb.getCreatedAt();
            nowDate = dateFormat.format(timestampFromProgress);
            if (pb.getTodayPages() == 0) {
                continue;
            }
            if (nowDate.equals(previousDate)) {
                continue;
            }
            previousDate = dateFormat.format(timestampFromProgress);
            sum++;
        }
        return sum;
    }

    /*
     * 現在のページ
     */
    public int currentPages(int bookId) {
        int cPages = 0;
        try {
            cPages = pdao.selectCurrentPages(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cPages;
    }
    /*
     * 本の総ページ数
     */

    public int totalPages(int bookId) {
        int total = 0;
        try {
            total = bdao.selectTotalPages(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /*
     * 残りのページ数
     */
    public int remainPages(int bookId) {
        int total = 0;
        int today = 0;
        try {
            total = bdao.selectTotalPages(bookId);
            today = pdao.selectCurrentPages(bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int remain = total - today;
        if (remain < 0) {
            return 0;
        } else {
            return remain;
        }
    }

    public int progress(int bookId) {
        return (currentPages(bookId) * 100) / totalPages(bookId);
    }

    /*
     * 最新データ1件削除
     */
    public void deleteRecentData(int bookId) {
        pdao.delete(bookId);
    }

    public void updateDate(int bookId) {
        sumDays(bookId);
        remainPages(bookId);
        average(bookId);
        RecentData(bookId);
        progress(bookId);
    }

    public void changeBook(int bookId) {
        pdto = pdao.select(bookId);
    }
}
