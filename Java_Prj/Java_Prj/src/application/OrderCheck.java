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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OrderCheck {

    @FXML
    private Button noButton; // 아니요 버튼

    @FXML
    private Button yesButton; // 예 버튼
    
    @FXML
    private Label menuNameLabel; // 메뉴 이름 라벨

    @FXML
    private Label countLabel; // 수량 라벨

    @FXML
    private Label totalPriceLabel; // 메뉴별 총 금액 라벨
    
    @FXML
    private Label ALLtotalPriceLabel; // 모든 금액을 합친 금액 라벨
    
    private Stage previousStage; // 이전 Stage를 저장할 변수
    private int allTotalPrice = 0; // 모든 메뉴의 총 금액을 저장할 변수
    


	Socket socket;
	ObjectOutputStream out;
    ObjectInputStream in;
    
    // 이전 Stage를 설정하는 메서드
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    // 장바구니에서 데이터를 받아 설정하는 메서드
    public void setOrderDetails(String menuName, int count, int totalPrice) {
        menuNameLabel.setText(menuName);
        countLabel.setText(String.valueOf(count + "개"));
        totalPriceLabel.setText(String.format("%,d원", totalPrice));
        // 모든 메뉴의 총 금액에 이번 메뉴의 총 금액을 더함
        allTotalPrice += totalPrice;
        // 합산한 총 금액을 ALLtotalPriceLabel에 표시
        ALLtotalPriceLabel.setText(String.format("%,d원", allTotalPrice));
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
    	
		/*
		 * if(sock==null) { try { sock = new Socket(Main.SERVER_ADDRESS,
		 * Main.SERVER_PORT); is=this.sock.getInputStream();
		 * os=this.sock.getOutputStream(); oos=new ObjectOutputStream(os); ois=new
		 * ObjectInputStream(is); } catch (Exception e) { e.printStackTrace(); } }
		 * DataType data=new DataType(); data.protocol=Protocol.order;
		 * AppData.order.setGuest_ID(AppData.guest.getGuest_ID());
		 * AppData.order.setUser_ID(AppData.guest.getUser_ID()); data.obj =
		 * AppData.order;
		 * 
		 * try { oos.writeObject(data);
		 * 
		 * 
		 * oos.close(); ois.close(); os.close(); is.close(); sock.close();
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
    	
        try {
        	if(sendOrder()) {
            // 현재 열려 있는 모든 창(Window)들 중에서 화면에 보이는 것만 선택하여 닫기
            List<Stage> openStages = Stage.getWindows().stream()
                    .filter(window -> window instanceof Stage && window.isShowing())
                    .map(window -> (Stage) window)
                    .toList();

            // 모든 열려 있는 창을 닫음
            for (Stage stage : openStages) {
                stage.close();
            }

            Parent requestRoot = FXMLLoader.load(getClass().getResource("OrderComplete.fxml"));
            Scene requestScene = new Scene(requestRoot);
                    
            // 새로운 Stage 생성
            Stage orderCompleteStage = new Stage();
            orderCompleteStage.setScene(requestScene);
            orderCompleteStage.setTitle("E-ORDER");
            
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
        	}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean sendOrder() {
    	try {
    		socket = new Socket(Main.SERVER_ADDRESS, Main.SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            DataType data=new DataType();
        	data.protocol=Protocol.order;
        	AppData.order.setGuest_ID(AppData.guest.getGuest_ID());
        	AppData.order.setUser_ID(AppData.guest.getUser_ID());
        	data.obj = AppData.order;
            
        	out.writeObject(data);
        	out.flush();
        	
            // 서버로부터 응답 대기 및 처리
            DataType response = null;
            while(response == null) {
            	response = (DataType) in.readObject();
            }
            
            out.close();
            in.close();
            
            return true;
        	
		} catch (Exception e) {

		}
    	
		return false;
    }
    
}
