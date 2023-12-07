package controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.BookShelfDAO;
import dao.BooksDAO;
import dao.ProgressDAO;
import dto.BooksDTO;
import dto.ProgressDTO;
import entity.ProgressBean;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class ShowController {
    private ProgressDTO pdto;
    private ProgressDAO pdao;
    private ProgressBean pb;
    private BooksDTO bdto;
    private BooksDAO bdao;
    private BookShelfDAO bsdao = new BookShelfDAO();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM/dd");
    private Timestamp timestampFromProgress;

    public ShowController() {
        this.pdao = new ProgressDAO();
        this.bdao = new BooksDAO();
    }

    /*
     * コンストラクタ
     */
    public ShowController(int userId, int bookId) {
        this.pdao = new ProgressDAO();
        this.bdao = new BooksDAO();
        try {
            pdto = pdao.select(userId, bookId);
            bdto = bdao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * DTO更新（bookIdを指定）
     */
    public ProgressDTO updateDTO(int userId, int bookId) {
        try {
            this.pdto = pdao.select(userId, bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.pdto;
    }

    /*
     * 本棚を取得 (userid別のbookbox取得)
     * JComboBox用
     */
    public List<String> getBookList(int userId, int bookId) {
        List<String> bookList = new ArrayList<>();
        bookList = bdao.searchBookList(userId);
        return bookList;
    }

    /*
     * 本の管理をするための情報取得 (useridが持つ本のリスト)
     * JTable用
     */
    public List<String[]> getBookTable(int userId) {
        return bdao.getBooksTable(userId);
    }

    /*
     * 本のタイトル名から本のIDを取得
     */
    public int getBookId(int userId, String bookTitle) {
        return pdao.searchBookId(userId, bookTitle);
    }

    /*
     * 本棚に本を追加
     */
    public String addBook(int userId, String title, String authorName, String genreName, int totalPages) {
        // boolean hasBook = false;
        String result;
        // hasBook = bdao.searchBook(title);
        // if (!hasBook) {
        bdao.registerBook(userId, title, authorName, genreName, totalPages);
        result = "登録しました。";
        // } else {
        // result = "既に登録されている本です。";
        // }
        return result;
    }

    /*
     * 
     * 過去5日間のデータ（JTable用）
     */
    public List<String[]> RecentData(int bookId, int userId) {
        List<String[]> tableData = new ArrayList<>();
        try {
            tableData = pdao.select5RecentData(bookId, userId);
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
    public String average(int userId, int bookId) {
        updateDTO(userId, bookId);
        int sumPage = 0;
        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            sumPage += pb.getTodayProgress();
        }
        if (sumDays(userId, bookId) == 0) {
            return String.valueOf(sumPage);
        }
        return String.valueOf(sumPage / sumDays(userId, bookId));
    }

    /*
     * 読んだ日の合計
     * 
     */
    public int sumDays(int userId, int bookId) {
        updateDTO(userId, bookId);
        int sum = 0;
        String previousDate = null;
        String nowDate = null;

        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            timestampFromProgress = pb.getCreatedAt();
            nowDate = dateFormat.format(timestampFromProgress);
            if (pb.getTodayProgress() == 0) {
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
    public int currentPages(int userId, int bookId) {
        int cPages = 0;
        try {
            cPages = pdao.selectCurrentPages(userId, bookId);
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
    public int remainPages(int userId, int bookId) {
        int total = 0;
        int today = 0;
        try {
            total = bdao.selectTotalPages(bookId);
            today = pdao.selectCurrentPages(userId, bookId);
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

    /*
     * 進捗率
     * （例外を防ぐため先に100を掛けて％を出す）
     */
    public int progress(int userId, int bookId) {
        return (currentPages(userId, bookId) * 100) / totalPages(bookId);
    }
    /*
     * progressデータ1件追加
     */
    public void addRecentData(int userId, int bookId, int totalPages) {
        pdao.insertTodayPage(userId, bookId, totalPages);
    }
    /*
     * progressデータ1件削除
     */
    public void deleteRecentData(int userId, int bookId) {
        pdao.delete(userId, bookId);
    }

    /*
     * BookShelfのTableModelの受け取り
     */
    public DefaultTableModel getBookShelfModel(int userId) {
        return bsdao.createManageBooksList(userId);
    }
    /*
     * 本の編集をDBに反映
     */
    public String editBookData(int originalRow, int column, String editedData) {
        return bsdao.updateBookData(originalRow, column, editedData);
    }
    public void changeBook(int userId, int bookId) {
        pdto = pdao.select(userId, bookId);
    }
}
