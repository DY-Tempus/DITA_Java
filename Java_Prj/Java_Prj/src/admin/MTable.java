package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MTable {
	
	@FXML
	private Button logOutButton;
	
	@FXML
	private Button createButton;
	
	@FXML
	private Button salesButton;
	
	@FXML
	private Button tbl1_Btn;
	
	@FXML
	private Button tbl2_Btn;
	
	@FXML
	private Button tbl3_Btn;
	
	@FXML
	private Button tbl4_Btn;
	
	@FXML
	private Button tbl5_Btn;
	
	@FXML
	private Button tbl6_Btn;
	
	// 테이블 버튼 -> 주문 목록(MOrder) 페이지로 이동
	// 각 테이블의 번호를 이용해 해당 계정의 주문 목록을 표시해야함.
	@FXML
    private void handleTableButtonAction(ActionEvent event) {
        try {
            // Personnel.fxml 파일 로드
            Parent MOrderRoot = FXMLLoader.load(getClass().getResource("MOrder_detail.fxml"));
            
            // 새로운 장면(Scene) 생성
            Scene MOrderScene = new Scene(MOrderRoot);
            
            // 현재 이벤트의 소스에서 현재 스테이지 가져오기
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            
            // 현재 스테이지와 동일한 위치로 설정
            stage.setX(currentStage.getX());
            stage.setY(currentStage.getY());
            
            // 새로운 장면으로 설정
            stage.setScene(MOrderScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// 로그아웃 버튼
	@FXML
    private void handleLogOutButtonAction(ActionEvent event) {
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
	
	// 계정 생성 버튼
	@FXML
    private void handleCreateButtonAction(ActionEvent event) {
		try {
            // MIntro.fxml 파일 로드
            Parent MIntroRoot = FXMLLoader.load(getClass().getResource("GSignup.fxml"));
            
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
	
	// 전체 매출 버튼
	@FXML
    private void handleSalesButtonAction(ActionEvent event) {
		try {
            // MIntro.fxml 파일 로드
            Parent MIntroRoot = FXMLLoader.load(getClass().getResource("TotalSales.fxml"));
            
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
