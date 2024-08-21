package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    @FXML
    private Button minusButton; // 마이너스 버튼

    @FXML
    private Button plusButton; // 플러스 버튼

    @FXML
    private Button deleteButton; // 삭제 버튼

    @FXML
    private Button closeButton; // 닫기 버튼

    @FXML
    private Button orderButton; // 주문하기 버튼

    @FXML
    private Label menuNameLabel; // 메뉴 이름+금액 라벨
    
    @FXML
    private Label countLabel; // 수량 라벨
    
    @FXML
    private Label emptyLabel; // 장바구니 비었을 때 라벨

    private static List<CartItem> cartItems = new ArrayList<>();  // 장바구니 항목들을 저장하는 static 리스트

    private int count;
    private int pricePerItem;

    @FXML
    private void initialize() {
        // 장바구니에 항목이 없으면 "장바구니 비어 있음"을 표시
        if (cartItems.isEmpty()) {
            emptyLabel.setVisible(true);
            hideItemControls();
        } else {
            displayCartItems();
        }
    }

    // 장바구니 항목을 UI에 표시
    private void displayCartItems() {
        // 현재 데모를 위해 첫 번째 항목만 표시
        CartItem item = cartItems.get(0);
        setMenuDetails(item.getMenuName(), item.getPrice(), item.getCount());
    }

    private void hideItemControls() {
        countLabel.setVisible(false);
        minusButton.setVisible(false);
        plusButton.setVisible(false);
        deleteButton.setVisible(false);
        menuNameLabel.setVisible(false);
    }

    public void setMenuDetails(String menuName, int price, int initialCount) {
        this.menuNameLabel.setText(menuName);
        this.pricePerItem = price;
        this.count = initialCount;
        countLabel.setText(String.valueOf(count));
        updateMenuLabel(menuName);

        // 장바구니에 아이템이 추가되면 요소들을 보이도록 설정
        emptyLabel.setVisible(false);
        countLabel.setVisible(true);
        minusButton.setVisible(true);
        plusButton.setVisible(true);
        deleteButton.setVisible(true);
        menuNameLabel.setVisible(true);
    }

    public void addMenuItem(String menuName, int price, int count) {
        // 기존 항목인지 확인
        for (CartItem item : cartItems) {
            if (item.getMenuName().equals(menuName)) {
                item.setCount(item.getCount() + count);
                displayCartItems();
                return;
            }
        }
        // 새로운 항목 추가
        cartItems.add(new CartItem(menuName, price, count));
        displayCartItems();
    }

    private void updateMenuLabel(String menuName) {
        int totalPrice = count * pricePerItem;
        menuNameLabel.setText(String.format("%s\n%,d원", menuName, totalPrice));
    }

    @FXML
    private void handleMinusButtonAction(ActionEvent event) {
        if (count > 1) {
            count--;
            countLabel.setText(String.valueOf(count));
            updateMenuLabel(menuNameLabel.getText().split("\n")[0]);

            // 장바구니의 실제 데이터도 업데이트
            CartItem item = cartItems.get(0);  // 데모를 위해 첫 번째 항목만 업데이트
            item.setCount(count);
        }
    }

    @FXML
    private void handlePlusButtonAction(ActionEvent event) {
        if (count < 10) {
            count++;
            countLabel.setText(String.valueOf(count));
            updateMenuLabel(menuNameLabel.getText().split("\n")[0]);

            // 장바구니의 실제 데이터도 업데이트
            CartItem item = cartItems.get(0);  // 데모를 위해 첫 번째 항목만 업데이트
            item.setCount(count);
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        // 첫 번째 항목을 제거 (데모를 위해 첫 번째 항목만 사용)
        cartItems.remove(0);

        // 장바구니가 비어 있으면 UI 업데이트
        if (cartItems.isEmpty()) {
            emptyLabel.setVisible(true);
            hideItemControls();
        } else {
            displayCartItems();
        }
    }

    @FXML
    private void handlecloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // 주문하기 버튼 처리
    @FXML
    private void handleorderButtonAction(ActionEvent event) {
        if (emptyLabel.isVisible()) {
            showAlert("장바구니 확인", "장바구니가 비었습니다.");
        } else {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderCheck.fxml"));
                Parent root = loader.load();

                OrderCheck orderCheckController = loader.getController();

                // 장바구니에서 받은 메뉴 이름, 수량, 총 금액을 전달
                String menuName = menuNameLabel.getText().split("\n")[0]; // 메뉴 이름만 추출
                int totalPrice = count * pricePerItem;
                orderCheckController.setOrderDetails(menuName, count, totalPrice);

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED); // 타이틀 바 제거
                stage.setScene(new Scene(root));

                // 모달 윈도우로 설정하여 메인 창의 앞에 뜨도록 함
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(currentStage);

                Stage mainStage = Main.getPrimaryStage();
                double mainStageX = mainStage.getX();
                double mainStageY = mainStage.getY();
                double mainStageWidth = mainStage.getWidth();
                double mainStageHeight = mainStage.getHeight();

                stage.setOnShown(e -> {
                    double stageWidth = stage.getWidth();
                    double stageHeight = stage.getHeight();
                    stage.setX(mainStageX + (mainStageWidth - stageWidth) / 2);
                    stage.setY(mainStageY + (mainStageHeight - stageHeight) / 2);
                });

                stage.toFront();
                stage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(orderButton.getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // 장바구니 항목을 표현하기 위한 클래스
    private static class CartItem {
        private String menuName;
        private int price;
        private int count;

        public CartItem(String menuName, int price, int count) {
            this.menuName = menuName;
            this.price = price;
            this.count = count;
        }

        public String getMenuName() {
            return menuName;
        }

        public int getPrice() {
            return price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
