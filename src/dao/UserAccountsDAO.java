package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.UserAccountsDTO;
import entity.UserAccountsBean;

public class UserAccountsDAO {
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

    public UserAccountsDTO selectPassWithSalt(String loginId) throws Exception {
        UserAccountsDTO udto = new UserAccountsDTO();
        String sql = "SELECT hashed_password, salt FROM swing.user_accounts WHERE login_id = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId);
            rs = ps.executeQuery();
            while (rs.next()) {
                UserAccountsBean ub = new UserAccountsBean();
                ub.setHashedpassword(rs.getString("hashed_password"));
                ub.setSalt(rs.getBytes("salt"));
                udto.add(ub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return udto;
    }

    /*
     * User登録
     */
    public void subscribeUser(String loginId, String password, byte[] salt) {
        rs = null;
        int result = 0;
        String sql = "INSERT INTO swing.user_accounts(login_id, hashed_password, salt) VALUES(?, ?, ?)";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, password);
            ps.setBytes(3, salt);
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }
}
