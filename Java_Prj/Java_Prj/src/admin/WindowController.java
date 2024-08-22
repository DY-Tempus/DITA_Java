package admin;

import java.util.Vector;

import entity.AppData;
import entity.Order;
import entity.OrderViewItem;
import entity.Order_detail;
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
    private TableView<OrderViewItem> orderView;

    @FXML
    private TableColumn<OrderViewItem, String> tblCol;  // Guest_ID
    @FXML
    private TableColumn<OrderViewItem, String> menuCol; // Menu_Name
    @FXML
    private TableColumn<OrderViewItem, Integer> quanCol; // Order_Num

    @FXML
    private Button clearBtn;

    @FXML
    private Button checkBtn;

    private ObservableList<OrderViewItem> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
    	tblCol.setCellValueFactory(new PropertyValueFactory<>("guestId"));
    	menuCol.setCellValueFactory(new PropertyValueFactory<>("menuName"));
    	quanCol.setCellValueFactory(new PropertyValueFactory<>("orderNum"));
        
        updateOrderList();
        orderView.setItems(list);
    }

    private void updateOrderList() {
    
        Vector<OrderViewItem> orview = new Vector<OrderViewItem>();
        for (Order order : AppData.orderq) {
            for (Order_detail detail : order.getOrder_detail()) {
            	orview.add(new OrderViewItem(order.getGuest_ID(), detail.getMenu_Name(), detail.getOrder_Num()));
            }
        }
        list.clear();
        list.addAll(orview);
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