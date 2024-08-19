package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MIntro {

	@FXML
	private Button loginButton;
	
	@FXML
	private Button signupButton;
	
	// 로그인 버튼
	@FXML
    private void handleLoginButtonAction(ActionEvent event) {
        try {
            // MLogin.fxml 파일 로드
            Parent MLoginRoot = FXMLLoader.load(getClass().getResource("MLogin.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene MLoginScene = new Scene(MLoginRoot);
            
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 장면으로 설정
            stage.setScene(MLoginScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 회원가입 버튼
	@FXML
    private void handleSignupButtonAction(ActionEvent event) {
        try {
            // Personnel.fxml 파일 로드
            Parent MSignupRoot = FXMLLoader.load(getClass().getResource("MSignup.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene MSignupScene = new Scene(MSignupRoot);
            
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 장면으로 설정
            stage.setScene(MSignupScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
