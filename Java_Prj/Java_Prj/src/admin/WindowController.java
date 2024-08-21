package admin;

import java.util.Vector;

import control.OrderMgr;
import entity.AppData;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class WindowController {

    @FXML
    private Label title;
    
    @FXML
    private TableView<Order> orderView;
    
    @FXML
    private TableColumn<Order, String> tblCol;
    
    @FXML
    private TableColumn<Order, String> menuCol;
    
    @FXML
    private TableColumn<Order, Integer> quanCol;
    
    @FXML
    private Button clearBtn;
    
    @FXML
    private Button checkBtn;
    
    private ObservableList<Order> list = FXCollections.observableArrayList();
    
    @FXML
    private void initialize() {
        tblCol.setCellValueFactory(new PropertyValueFactory<>("tblno"));
        menuCol.setCellValueFactory(new PropertyValueFactory<>("menu"));
        quanCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        orderView.setItems(list);
        updateOrderList();
    }
    
    private void updateOrderList() {
        list.clear();
        list.addAll(AppData.orderq);
    }

    @FXML
    private void handleCheckButtonAction(ActionEvent event) {
        Stage stage = (Stage) checkBtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        list.clear();  // Clear the list of orders
    }
}
