package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class SampleController2 {

    @FXML
    private TextField txtPassword;
    
    @FXML
    private Label lblInfo;
    
    @FXML
    private TextField txtUser;

    public void Dangnhap(String username, String password) {
        try (Connection conn = DataConnection.getConnection()) {
            
            // SQL query to get the hashed password for the given username
            String query = "SELECT id, username, password FROM tbl_User WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                String inputHashedPassword = hashPassword(password);
                
                // Compare the stored hashed password with the hashed version of the input password
                if (storedHashedPassword.equals(inputHashedPassword)) {
                    lblInfo.setText(rs.getString("username") + " đã đăng nhập");
                    Main.setRoot("Sample");
                } else {
                    lblInfo.setText("Đăng nhập không thành công: Sai mật khẩu");
                }
            } else {
                lblInfo.setText("Đăng nhập không thành công: Người dùng không tồn tại");
            }
        } catch (Exception ex) {
            lblInfo.setText("Lỗi đăng nhập: " + ex.getMessage());
        }
    }

    // Method to hash the input password using SHA-256 and Base64 encoding
    public String hashPassword(String plainTextPassword) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(plainTextPassword.getBytes());

        // Convert the hashed bytes into a readable string using Base64 encoding
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    @FXML
    public void dk() throws IOException {
        Main.setRoot("Sample3");
    }

    @FXML
    public void dangnhap_clicked() {
        Dangnhap(txtUser.getText(), txtPassword.getText());
    }
}
