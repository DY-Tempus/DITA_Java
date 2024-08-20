package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GLogin {
	@FXML
	private Button loginButton; // 로그인 버튼
	
	
	// 로그인 버튼
	@FXML
    private void handleLoginButtonAction(ActionEvent event) {
		try {
            Parent StartRoot = FXMLLoader.load(getClass().getResource("Start.fxml"));
            Scene StartScene = new Scene(StartRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(StartScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
