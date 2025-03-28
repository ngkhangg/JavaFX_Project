package application;
	
import java.io.IOException;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static Scene scene;
	@Override
	public void start(Stage primaryStage) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample2.fxml"));
	        Parent root = loader.load();
	        scene = new Scene(root, 400, 300); // Khởi tạo biến scene
	        primaryStage.setTitle("My JavaFX Application");
	        primaryStage.setScene(scene); // Sử dụng biến scene
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace(); // In ra lỗi chi tiết
	    }
	}
	static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
	public static void main(String[] args) {
		launch(args);
	}
}
