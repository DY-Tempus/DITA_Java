package application;

import control.AccountMgr;
import entity.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MLogin {

	@FXML
	private Button loginButton;
	
	@FXML
	private Button signupButton;
	
	@FXML
	private TextField userID;
	
	@FXML
	private PasswordField userPW;
	
	// 로그인 버튼 -> 게스트 목록 페이지로 이동 / 주석 처리된 곳 수정해야함.
	// fxml 파일의 해당 버튼에 onAction="#이벤트 핸들러 함수명" 추가해야함.
	@FXML
    private void handleLoginButtonAction(ActionEvent event) {
        try {
        	Account account = new Account();
        	account.setUser_ID(userID.getText());
        	account.setUser_PW(userPW.getText());
        	AccountMgr accMgr = new AccountMgr();
        	
	        	if(accMgr.selectAccount(account)) {
	        	
		        // Personnel.fxml 파일 로드
		        Parent MTableRoot = FXMLLoader.load(getClass().getResource("MTable.fxml"));
		            
		        // 새로운 장면(Scene) 생성
		        Scene MTableScene = new Scene(MTableRoot);
		            
		        // 현재 스테이지 가져오기
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		            
		        // 새로운 장면으로 설정
		        stage.setScene(MTableScene);
		        stage.show();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 회원가입 버튼
	@FXML
    private void handleSignupButtonAction(ActionEvent event) {
        try {
            // MSignup.fxml 파일 로드
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
