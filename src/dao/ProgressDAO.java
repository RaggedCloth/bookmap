package dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;


import dto.ProgressDTO;
import entity.ProgressBean;

public class ProgressDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/swing";
    private static final String USER = "devuser01";
    private static final String PASS = "devuser01";
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs;

    public void connect() {
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver");
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

    public ProgressDTO select(int bookId) {
        ProgressDTO pdto = new ProgressDTO();
        String sql = "SELECT * FROM swing.progress WHERE bookid = " + bookId;
        try {
            connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                ProgressBean pb = new ProgressBean();
                pb.setBookid(rs.getInt("bookid"));
                pb.setId(rs.getInt("id"));
                pb.setTodayPages(rs.getInt("today_pages"));
                pb.setCreatedAt(rs.getTimestamp("created_at"));
                pdto.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
        return pdto;
    }

    public List<String[]> select5RecentData(int bookId) throws Exception {
        String sql = "SELECT today_pages AS 'ページ数', created_at AS '作成日時' " +
                "FROM (SELECT * FROM swing.progress " +
                "WHERE bookid = " + bookId + " " +
                "ORDER BY created_at " +
                "DESC LIMIT 5) AS subquery ORDER BY created_at ASC";
        connect();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery(sql);
        List<String[]> data = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            while (rs.next()) {
                Timestamp timestampFromProgress = rs.getTimestamp("作成日時");                // StringをTimestampに変換
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM/dd");
                String data1 = String.valueOf(rs.getInt("ページ数"));
                String data2 = dateFormat.format(timestampFromProgress);
                data.add(new String[] { data1, data2 });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return data;
    }

    public int selectCurrentPages(int bookId) throws Exception {
        int result = 0;
        String sql = "SELECT today_pages FROM swing.progress WHERE bookid = " + bookId;
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                ProgressBean pb = new ProgressBean();
                pb.setTodayPages(rs.getInt("today_pages"));
                result += pb.getTodayPages();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return result;

    }

    // public int countDateTime(int bookId) {
    // String sql = "SELECT COUNT(datetime) FROM swing.progress WHERE bookid =
    // 1";
    // return executeSql(sql);
    // }

    public int insertTodayPage(int todayPages, int bookId) {
        String sql = "INSERT INTO swing.progress(today_pages, bookid) VALUES(" + todayPages + ", " + bookId + ")";
        return updateSql(sql);
    }

    public int update(int id, int bookid, int todayPages) {
        String sql = "UPDATE swing.progress SET id = " + id + ", bookid = " + bookid + ", today_pages = " + todayPages;
        return updateSql(sql);
    }

    public int delete(int bookId) {
        String sql = "DELETE FROM swing.progress WHERE bookid = "+ bookId + " ORDER by id DESC LIMIT 1";
        return updateSql(sql);
    }

    public int updateSql(String sql) {
        rs = null;
        int result = 0;
        try {
            connect();
            ps = con.prepareStatement(sql);
            result = ps.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        disconnect();
        return result;
    }

}
