package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public ProgressDTO select() {
        ProgressDTO pdto = new ProgressDTO();
        String sql = "SELECT * FROM swing.progress";
        try {
            connect();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                ProgressBean pb = new ProgressBean();
                pb.setBookid(rs.getInt("bookid"));
                pb.setId(rs.getInt("id"));
                pb.setTodayPages(rs.getInt("today_pages"));
                pb.setDatetime(rs.getTimestamp("datetime"));
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

    public int selectTodayPages(int bookId) {
        int result = 0;
        String sql = "SELECT today_pages FROM swing.progress WHERE bookid = " + bookId;
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            rs = ps.executeQuery(sql);
                while(rs.next()) {
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

    public int insertTodayPage(int todaypage) {
        String sql = "INSERT INTO swing.progress(today_pages, bookid) VALUES(" + todaypage + ", 1)";
        return updateSql(sql);
    }

    public int update(int id, int bookid, int todaypage) {
        String sql = "UPDATE swing.progress SET id = " + id + ", bookid = " + bookid + ", today_pages = " + todaypage;
        return updateSql(sql);
    }

    public int delete(int id) {
        String sql = "DELETE FROM swing.progress WHERE id = " + id;
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
