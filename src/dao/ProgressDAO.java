package dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import dto.ProgressDTO;
import entity.ProgressBean;

public class ProgressDAO {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs;

    public void connect() {
        con = DatabaseSettings.getConnection();
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

    public ProgressDTO select(int userId, int bookId) {
        ProgressDTO pdto = new ProgressDTO();
        String sql = "SELECT * FROM bookmap.progress WHERE user_id = ? AND book_id = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgressBean pb = new ProgressBean();
                pb.setBookId(rs.getInt("book_id"));
                pb.setProgressId(rs.getInt("progress_id"));
                pb.setUserId(rs.getInt("user_id"));
                pb.setTodayProgress(rs.getInt("today_progress"));
                pb.setCreatedAt(rs.getTimestamp("created_at"));
                pdto.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return pdto;
    }

    /*
     * 本のタイトル名から本のIDを取得
     * （要修正）BookDAOに移動
     */
    public int searchBookId(int userId, String bookTitle) {
        int id = 0;
        String sql = "SELECT Distinct b.book_id FROM books b "
                + "LEFT OUTER JOIN user_books ub ON b.book_id = ub.book_id "
                + "LEFT OUTER JOIN progress p ON ub.user_id = p.user_id "
                + "WHERE ub.user_id = ? AND b.title = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, bookTitle);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("book_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return id;
    }

    /*
     * 最新データ5件取得
     */
    public List<String[]> getProgressData(int userId, int bookId) {
        String sql = "SELECT today_progress, created_at FROM bookmap.progress "
                + "WHERE user_id = ? AND book_id = ? ORDER BY created_at ASC";
        connect();
        List<String[]> data = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp timestampFromProgress = rs.getTimestamp("created_at");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM/dd");
                String data1 = String.valueOf(rs.getInt("today_progress")) + "P";
                String data2 = dateFormat.format(timestampFromProgress);
                data.add(new String[] { data1, data2 });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return data;
    }

    /*
     * 読んだ合計のページ数
     */
    public int selectCurrentPages(int userId, int bookId) throws Exception {
        int result = 0;
        String sql = "SELECT today_progress FROM bookmap.progress WHERE user_id = ? AND book_id = ?";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProgressBean pb = new ProgressBean();
                pb.setTodayProgress(rs.getInt("today_progress"));
                result += pb.getTodayProgress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
        return result;
    }

    /*
     * 今日のページ数登録
     */
    public void insertTodayPage(int userId, int bookId, int todayProgress) {
        String sql = "INSERT INTO bookmap.progress(user_id, book_id, today_progress) VALUES(?, ?, ?)";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setInt(3, todayProgress);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }

    /*
     * 最新データ1件削除
     */
    public void delete(int userId, int bookId) {
        String sql = "DELETE FROM bookmap.progress WHERE user_id = ? AND book_id = ? ORDER by progress_id DESC LIMIT 1";
        connect();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        disconnect();
    }
}
