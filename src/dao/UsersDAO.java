package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.UsersDTO;
import entity.UsersBean;

public class UsersDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/bookmap";
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

    /*
     * passwordとsaltをudtoに格納
     */
    public UsersDTO selectPassWithSalt(String loginId) throws Exception {
        UsersDTO udto = new UsersDTO();
        String sql = "SELECT hashed_password, salt FROM bookmap.users WHERE login_id = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsersBean ub = new UsersBean();
                ub.setHashedPassword(rs.getString("hashed_password"));
                ub.setSalt(rs.getString("salt"));
                udto.add(ub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return udto;
    }
    /*
     * loginIdからuserIdとbookIdを取得
     */
    public int[] searchIds(String loginId) {
        int[] ids = new int[2];
        String sql = "SELECT p.user_id, p.book_id FROM progress p "+ 
                "INNER JOIN users u ON p.user_id = u.user_id " + 
                "WHERE u.login_id = ? " + 
                "ORDER BY p.created_at DESC LIMIT 1";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ids[0] = rs.getInt("user_id");
                ids[1] = rs.getInt("book_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return ids;
    }
    /*
     * User登録
     */
    public void saveUserToDB(String loginId, String password, String salt) {
        String sql = "INSERT INTO bookmap.users(login_id, hashed_password, salt) VALUES(?, ?, ?)";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, password);
            ps.setString(3, salt);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }
}
