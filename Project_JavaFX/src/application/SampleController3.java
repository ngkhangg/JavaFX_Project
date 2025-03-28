package application;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Base64;
public class SampleController3 {
	 @FXML
	    private Label lblInfo;

	    @FXML
	    private TextField txtPassword;
	    
	    @FXML
	    private TextField txtEmail;
	    
	    @FXML
	    private TextField txtAddress;

	    @FXML
	    private TextField txtUser;
	    
	    public boolean checkUser(String username) {
	        String query = "SELECT id, username FROM tbl_user WHERE username = ?";
	        try (Connection conn = DataConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	             
	            pstmt.setString(1, username); // Đặt tham số cho truy vấn
	            ResultSet rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                return true; // Người dùng đã tồn tại
	            } else {
	                return false; // Người dùng không tồn tại
	            }
	        } catch (SQLException e) {
	            lblInfo.setText("Lỗi kiểm tra người dùng: " + e.getMessage());
	            return false;
	        }
	    }

	    
	    public void DangKy(String userName) throws Exception
		{
	    	if(checkUser(userName)==false) {
	    		String query = "INSERT INTO tbl_user (username, password,email) VALUES (?, ?, ?) ";
				try (Connection conn=DataConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query))
				{		
						pstmt.setString(1, userName);
						pstmt.setString(2, hashText());
						pstmt.setString(3, txtEmail.getText());
				
						pstmt.executeUpdate();
						Main.setRoot("Sample2");
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
	    	}else {
	    		lblInfo.setText("User name đã tồn tại");
	    	}
	    	
			
			
		}
	    public String hashText() throws Exception {
			String plainText = txtPassword.getText();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(plainText.getBytes());
			return Base64.getEncoder().encodeToString(hashedBytes);
		}
		
		@FXML
		public void dangKy_clicked() throws Exception 
		{	
			DangKy(txtUser.getText());

		}
}
