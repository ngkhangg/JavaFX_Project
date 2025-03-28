package application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SampleController {
	
	@FXML
	private TextField txtProductName,txtPrice,txtProductId,searchField;
	
	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<Product, Integer> columnProductId;
	@FXML
	private TableColumn<Product, String> columnProductName;
	@FXML
	private TableColumn<Product, Double> columnPrice;
	
	private ObservableList<Product> productList =
	FXCollections.observableArrayList();
	
	private Product selectedProduct;
	
	
	@FXML
	public void initialize()
	{
		//Hàm initialize: Khởi tạo dữ liệu cho form
		
		columnProductId.setCellValueFactory(new
				PropertyValueFactory<>("ProductID"));
				
				columnProductName.setCellValueFactory(new
				PropertyValueFactory<>("ProductName"));
				
				columnPrice.setCellValueFactory(new
				PropertyValueFactory<>("UnitPrice"));
				productTable.setItems(productList);				
				//Load dữ liệu từ CSDL, đổ vào giao diện
				loadProducts();	
	
	}
	
	
	
	public void loadProducts() 
		{
		//Xóa sạch dữ liệu của giao diện trước khi add
		productTable.getSelectionModel().clearSelection();
		productTable.getItems().clear();
		
		String query = "SELECT ProductID, ProductName, UnitPrice FROM Products";
		try (Connection conn = DataConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery()) 
		{
				
				while (rs.next()) {
					Product product = new Product();
					product.setProductID(rs.getInt("ProductID"));					
					product.setProductName(rs.getString("ProductName"));
					product.setUnitPrice(rs.getDouble("UnitPrice"));								
										
					productList.add(product);
				}
				productTable.setItems(productList);
		}
		catch (SQLException e) {
		e.printStackTrace();
		}
	}
	
	@FXML
	public void addProduct() {
		String query = "INSERT INTO Products (ProductName, UnitPrice) VALUES (?, ?)";
		try (Connection conn = DataConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) 
		{
				pstmt.setString(1, txtProductName.getText());
				pstmt.setDouble(2, Double.parseDouble(txtPrice.getText()));
				pstmt.executeUpdate();
				loadProducts();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
	}
	
	@FXML
	public void deleteProduct() {
	    String query = "DELETE FROM Products WHERE ProductID = ?";
	    try (Connection conn = DataConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) 
	    {
	        pstmt.setInt(1, Integer.parseInt(txtProductId.getText()));  
	        int affectedRows = pstmt.executeUpdate();
	        
	        if (affectedRows > 0) {
	        	pstmt.executeUpdate();
	        	loadProducts();
	            System.out.println("Product deleted successfully.");
	        } else {
	            System.out.println("Product not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (NumberFormatException e) {
	        System.out.println("Invalid Product ID format.");
	    }
	}

	
	@FXML
	public void searchProduct() 
	{
		String searchQuery = searchField.getText().toLowerCase();
		
		ObservableList<Product> filteredList =
		FXCollections.observableArrayList();
		
		for (Product product : productList)
		{
			if (product.getProductName().toLowerCase().contains(searchQuery)) 
			{
				filteredList.add(product);
			}
		}
		productTable.setItems(filteredList);
	}
	
	
	@FXML
	public void selectProduct() {
	selectedProduct = productTable.getSelectionModel().getSelectedItem();
	if (selectedProduct != null) {
		txtProductName.setText(selectedProduct.getProductName());
		txtPrice.setText(String.valueOf(selectedProduct.getUnitPrice()));
		txtProductId.setText(String.valueOf(selectedProduct.getProductID()));
	
	}
	}
	@FXML
	public void updateProduct() {
		String query = "UPDATE Products SET ProductName = ?, UnitPrice = ? WHERE ProductID = ?";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, txtProductName.getText());
            pstmt.setDouble(2, Double.parseDouble(txtPrice.getText()));
            pstmt.setInt(3, Integer.parseInt(txtProductId.getText()));
            pstmt.executeUpdate();
            loadProducts();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        	loadProducts();
        	productTable.refresh(); // Cập nhật lại hiển thị TableView
	}
	
	
	}
	
	
	

