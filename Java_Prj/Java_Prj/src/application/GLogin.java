package application;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import entity.AppData;
import entity.Call;
import entity.DataType;
import entity.Guest;
import entity.Protocol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GLogin {
	@FXML
	private Button loginButton; // 로그인 버튼
	
	@FXML
	private TextField guestID;
	
	private DataType dt;
	
	private Guest guest;
	// 로그인 버튼
	@FXML
    private void handleLoginButtonAction(ActionEvent event) {
		try {
			if(requestLogin()) {
            Parent StartRoot = FXMLLoader.load(getClass().getResource("Start.fxml"));
            Scene StartScene = new Scene(StartRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(StartScene);
            stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private boolean requestLogin() {		
		try {
			Socket socket = new Socket(Main.SERVER_ADDRESS, Main.SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            
            dt = new DataType();
            dt.protocol = Protocol.loginGuest;
            AppData.guest.setGuest_ID(guestID.getText());
        	
        	dt.obj = AppData.guest;
            out.writeObject(dt);
            out.flush();
            
            // 서버로부터 응답 대기 및 처리
            DataType response = null;
            while(response == null) {
            	response = (DataType) in.readObject();
            }
            
            // 게스트 정보 받아오기.
            AppData.guest = (Guest) response.obj;
            
            return true;
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
