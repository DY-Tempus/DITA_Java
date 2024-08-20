package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MSignup {
	@FXML
	private Button signupButton;
	
	// 가입하기 버튼
	@FXML
    private void handleSignupButtonAction(ActionEvent event) {
        try {
            // MIntro.fxml 파일 로드
            Parent MIntroRoot = FXMLLoader.load(getClass().getResource("MIntro.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene MIntroScene = new Scene(MIntroRoot);
            
            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // 새로운 장면으로 설정
            stage.setScene(MIntroScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}