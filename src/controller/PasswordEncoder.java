package controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import dao.UserAccountsDAO;
import dto.UserAccountsDTO;
import entity.UserAccountsBean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordEncoder {
    private UserAccountsDTO udto;
    private UserAccountsDAO udao;
    private UserAccountsBean ub;

    public static String sha256Encode(String password, byte[] salt) {
        int iterateCount = 1000;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);

            // パスワードをハッシュ化
            byte[] hashedPassword = password.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < iterateCount; i++) {
                hashedPassword = md.digest(hashedPassword);
                md.reset();
            }
            // ハッシュ値を16進数の文字列に変換
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedPassword) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * salt
     */
    public static byte[] getSHA256salt() {
        byte[] hashedSalt = null;
        byte[] salt = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        // ソルトをハッシュ化
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hashedSalt = md.digest(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedSalt;
    }

    // hashingメソッド内でsaltを生成せず、呼び出し元で生成してそれを引数としてhashingメソッドに渡す方が管理しやすい？
    public static String hashing(String password) {
        byte[] salt = getSHA256salt();
        return sha256Encode(password, salt);
    }

    /*
     * ログイン
     */
    public boolean userCheck(String loginId, String password) {
        boolean user = false;
        ub = new UserAccountsBean();
        udao = new UserAccountsDAO();
        udto = new UserAccountsDTO();
        try {
            udto = udao.selectPassWithSalt(loginId);
            for (int i = 0; i < udto.size(); i++) {
                ub = udto.get(i);
            }
            // ユーザーIDでsqlを検索してuserに代入
            if (ub != null) {
                // beanからpasswordを取り出し、入力されたpasswordと照合
                String hashedPassword = ub.getHashedpassword();
                byte[] salt = ub.getSalt();
                System.out.println(salt.toString());
                // 入力されたpasswordをsaltを使いhash化
                String inputPassword = sha256Encode(password, salt);
                // passwordと照合
                if (inputPassword.equals(hashedPassword)) {
                    // trueならログイン falseならポップアップで警告
                    System.out.println("ログインしました。");
                    user = true;
                } else {
                    System.out.println("ログインできませんでした。");
                }
            } else {
                System.out.println("ログインIDが存在しません。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /*
     * userNameとハッシュ化したpasswordをデータベースに保存
     */
    public void subscribe(String loginId, String password) {
        String sql = null;
        byte[] salt = getSHA256salt();
        String hashedPassword = sha256Encode(password, salt);
        udao = new UserAccountsDAO();
        udao.subscribeUser(loginId, hashedPassword, salt);
    }

    /*
     * byteをhexに変換（可読性を上げるため）
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
