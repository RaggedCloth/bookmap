package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.BooksDTO;
import entity.BooksBean;

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
                bb.setTotalPages(rs.getInt("total_pages"));
                bdto.add(bb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return bdto;
    }

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

    public int executeSql(String sql) {
        rs = null;
        int result = 0;
        try {
            connect();
            ps = con.prepareStatement(sql);
            result = ps.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return result;
    }
}
