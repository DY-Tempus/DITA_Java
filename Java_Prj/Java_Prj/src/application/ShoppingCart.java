package application;

import java.util.Vector;

import entity.Order_detail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShoppingCart {
	
	
	@FXML
	private Button closeButton; // 닫기 버튼
	
	@FXML
	private Button orderButton; // 주문하기 버튼
	
	// 닫기 버튼
    @FXML
    private void handlecloseButtonAction(ActionEvent event) {
    	Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
 // 주문하기 버튼
    @FXML
    private void handleorderButtonAction(ActionEvent event) {
        try {
            // 현재 창(Stage)을 가져옴
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // ShoppingCart 창을 닫음
            currentStage.close();

            // OrderCheck.fxml 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderCheck.fxml"));
            Parent root = loader.load();

            // 새로운 Stage 설정
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED); // 타이틀 바 제거
            stage.setScene(new Scene(root));

            // 모달 윈도우로 설정하여 메인 창의 앞에 뜨도록 함
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(currentStage);

            // 메인 창의 위치와 크기 가져오기
            Stage mainStage = Main.getPrimaryStage();
            double mainStageX = mainStage.getX();
            double mainStageY = mainStage.getY();
            double mainStageWidth = mainStage.getWidth();
            double mainStageHeight = mainStage.getHeight();

            // OrderCheck 창의 크기를 먼저 계산해야 중앙에 배치 가능
            stage.setOnShown(e -> {
                double stageWidth = stage.getWidth();
                double stageHeight = stage.getHeight();
                stage.setX(mainStageX + (mainStageWidth - stageWidth) / 2);
                stage.setY(mainStageY + (mainStageHeight - stageHeight) / 2);
            });

            // 창을 최상위로 설정
            stage.toFront();
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
