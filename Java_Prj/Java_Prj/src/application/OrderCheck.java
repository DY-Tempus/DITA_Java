package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import entity.AppData;
import entity.DataType;
import entity.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderCheck {

	Socket sock;
	OutputStream os;
	InputStream is;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
    @FXML
    private Button noButton; // 아니요 버튼

    @FXML
    private Button yesButton; // 예 버튼
    
    private Stage previousStage; // 이전 Stage를 저장할 변수

    // 이전 Stage를 설정하는 메서드
    public void setPreviousStage(Stage stage) {	
        this.previousStage = stage;
    }

    // 아니요 버튼
    @FXML
    private void handlenoButtonAction(ActionEvent event) {
        // 현재 창(Stage) 닫기
        Stage currentStage = (Stage) noButton.getScene().getWindow();
        currentStage.close();
        
        // 이전 Stage가 존재하는 경우 다시 보여주기
        if (previousStage != null) {
            previousStage.show();
        }
    }

    // 예 버튼
    @FXML
    private void handleyesButtonAction(ActionEvent event) {
    	if(sock==null) {
			try {
				sock = new Socket(Main.SERVER_ADDRESS, Main.SERVER_PORT);
				is=this.sock.getInputStream();
				os=this.sock.getOutputStream();
				oos=new ObjectOutputStream(os);
				ois=new ObjectInputStream(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	DataType data=new DataType();
    	data.protocol=Protocol.order;

    	data.obj=AppData.order;
    	try {
			oos.writeObject(data);
			oos.close();
			ois.close();
			os.close();
			is.close();
			sock.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        try {
            // 현재 열려 있는 모든 창(Window)들 중에서 화면에 보이는 것만 선택하여 닫기
            List<Stage> openStages = Stage.getWindows().stream()
                    .filter(window -> window instanceof Stage && window.isShowing())
                    .map(window -> (Stage) window)
                    .toList();

            // 모든 열려 있는 창을 닫음
            for (Stage stage : openStages) {
                stage.close();
            }

            // OrderComplete.fxml 로드 및 중앙에 위치시키기
            Parent requestRoot = FXMLLoader.load(getClass().getResource("OrderComplete.fxml"));
            Scene requestScene = new Scene(requestRoot);

            // 새로운 Stage 생성
            Stage orderCompleteStage = new Stage();
            orderCompleteStage.setScene(requestScene);

            // 화면 중앙에 위치시키기
            orderCompleteStage.setOnShown(e -> {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                double primaryStageX = primaryStage.getX();
                double primaryStageY = primaryStage.getY();
                double primaryStageWidth = primaryStage.getWidth();
                double primaryStageHeight = primaryStage.getHeight();
                double stageWidth = orderCompleteStage.getWidth();
                double stageHeight = orderCompleteStage.getHeight();
                orderCompleteStage.setX(primaryStageX + (primaryStageWidth - stageWidth) / 2);
                orderCompleteStage.setY(primaryStageY + (primaryStageHeight - stageHeight) / 2);
            });

            // OrderComplete 창을 보여줌
            orderCompleteStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
