package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import dto.BooksDTO;
import entity.BooksBean;
import entity.ProgressBean;

public class BooksDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/swing";
    private static final String USER = "devuser01";
    private static final String PASS = "devuser01";
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs;

    public void connect() {
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BooksDTO selectAll() throws Exception {
        BooksDTO bdto = new BooksDTO();
        String sql = "SELECT * FROM bookmap.books";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                BooksBean bb = new BooksBean();
                bb.setBookId(rs.getInt("book_id"));
                bb.setTitle(rs.getString("title"));
                bb.setAuthorId(rs.getInt("author_id"));
                bb.setGenreId(rs.getInt("genre_id"));
                bb.setTotalPages(rs.getInt("total_pages"));
                bdto.add(bb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return bdto;
    }

    /*
     * 本のtotalPage取得
     */
    public int selectTotalPages(int bookId) throws Exception {
        int result = 0;
        String sql = "SELECT total_pages FROM bookmap.books WHERE book_id = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            while (rs.next()) {
                BooksBean bb = new BooksBean();
                bb.setTotalPages(rs.getInt("total_pages"));
                result = bb.getTotalPages();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return result;
    }

    /*
     * BooksテーブルをBeanに入れて返す
     * Jtable用
     */
    public List<String[]> getBooksTable(int userId) {
        List<String[]> bookData = new ArrayList<>();
        String sql = "SELECT DISTINCT b.title, b.author_id, b.genre_id, b.total_pages " +
                "FROM bookmap.user_books u " +
                "RIGHT OUTER JOIN bookmap.books b ON u.book_id = b.book_id " +
                "WHERE u.user_id = ? ";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                String booksTitle = rs.getString("title");
                String authorId = String.valueOf(rs.getInt("author_id"));
                String genreId = String.valueOf(rs.getInt("genre_id"));
                String totalPages = String.valueOf(rs.getInt("total_pages"));

                bookData.add(new String[] { booksTitle, authorId, genreId, totalPages });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return bookData;
    }
    /*
     * 本の管理に登録された1冊の情報を追加する
     * （実装予定）userbooksテーブルを追加し、追加された情報を1件返し、ManageBooksウィンドウのテーブに表示
     */
    
    /*
     * 本棚取得
     * LinkedHashMapに入れるので重複していてもOK
     */
    public List<String> searchBookList(int userId) {
        List<String> allBookTitle = new ArrayList<>();
        List<String> bookTitle;
        String sql = "SELECT b.title, u.created_at FROM bookmap.user_books u " +
                "RIGHT OUTER JOIN bookmap.books b ON u.book_id = b.book_id " +
                "WHERE u.user_id = ? ORDER BY u.created_at DESC;";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                allBookTitle.add(rs.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bookTitle = new ArrayList<>(new LinkedHashSet<>(allBookTitle));
        disconnect();
        return bookTitle;
    }

    /*
     * 本の検索
     */
    public boolean searchBook(String title) {
        boolean hasBook = false;
        String sql = "SELECT book_id FROM bookmap.books WHERE title = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            rs = ps.executeQuery();
            if (rs.next()) {
                hasBook = true;
                rs.getInt("book_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return hasBook;
    }

    /*
     * 本の追加
     * booksテーブルに追加後、progressテーブルにもuser_idとbook_idを追加する
     * (progressにも登録しないとuser_idと紐付けできず、本棚に追加されない)
     */
    public void registerBook(int userId, String title, String authorName, String genreName, int totalPages) {
        String authors = "INSERT INTO bookmap.authors(author_name) VALUES(?)";
        String genres = "INSERT INTO bookmap.genres(genre_name) VALUES(?)";
        String books = "INSERT INTO bookmap.books(title, total_pages, author_id, genre_id) " +
                "VALUES (?, ?, (SELECT author_id FROM bookmap.authors WHERE author_name = ? LIMIT 1), LAST_INSERT_ID())";
        String progress = "INSERT INTO bookmap.user_books(user_id, book_id) " +
                "VALUES (?, (SELECT book_id FROM bookmap.books WHERE title = ? LIMIT 1))";
        connect();
        try {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(authors);
                    PreparedStatement ps2 = con.prepareStatement(genres);
                    PreparedStatement ps3 = con.prepareStatement(books);
                    PreparedStatement ps4 = con.prepareStatement(progress)) {
                ps1.setString(1, authorName);

                ps2.setString(1, genreName);

                ps3.setString(1, title);
                ps3.setInt(2, totalPages);
                ps3.setString(3, authorName);

                ps4.setInt(1, userId);
                ps4.setString(2, title);

                ps1.addBatch();
                ps2.addBatch();
                ps3.addBatch();
                ps4.addBatch();

                ps1.executeBatch();
                ps2.executeBatch();
                ps3.executeBatch();
                ps4.executeBatch();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
    // String sql = "START TRANSACTION;" +
    // "INSERT INTO bookmap.books(title, total_pages) " +
    // "VALUES(?, ?);" + // 1:title, 2:totalPaegs

    // "INSERT INTO bookmap.authors(author_name) " +
    // "VALUES(?);" + // 3:authorName

    // "INSERT INTO bookmap.genres(genre_name) " +
    // "VALUES(?);" + // 4:genreName

    // "INSERT INTO bookmap.progress(user_id, book_id) " +
    // "VALUES (?, (SELECT book_id FROM bookmap.books b " + // 5:userId
    // "WHERE b.title = ?));" + // 6:title

    // "COMMIT";
    // connect();
    // try (PreparedStatement ps = con.prepareStatement(sql)) {
    // ps.setString(1, title);
    // ps.setInt(2, totalPages);
    // ps.setString(3, authorName);
    // ps.setString(4, genreName);
    // ps.setInt(5, userId);
    // ps.setString(6, title);
    // ps.executeUpdate();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // disconnect();
    // }
}
